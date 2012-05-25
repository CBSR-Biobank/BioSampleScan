package edu.ualberta.med.biosamplescan.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.Assert;

import edu.ualberta.med.scannerconfig.dmscanlib.ScanCell;
import edu.ualberta.med.scannerconfig.dmscanlib.ScanCellPos;

public class Pallet {
    private int id;
    private ScanCell[][] barcodes;
    private String palletBarcode;
    private long timestamp;

    public Pallet(int id) {
        this.id = id;
        barcodes = null;
    }

    public int getId() {
        return id;
    }

    public ScanCell[] getBarcodesRow(int row) {
        if (barcodes == null)
            return null;
        Assert.isTrue(((row >= 0) && (row < barcodes.length)), "invalid row: "
            + row);
        return barcodes[row];
    }

    public void setPalleteBarcode(String palletBarcode) {
        this.palletBarcode = palletBarcode;
    }

    public String getPlateBarcode() {
        return palletBarcode;
    }

    public void setPlateTimestampNow() {
        timestamp = (new Date()).getTime();
    }

    public long getPlateTimestamp() {
        return timestamp;
    }

    public void clear() {
        barcodes = null;
    }

    // Returns true if barcodes = null or no tubes exist
    public boolean isEmpty() {
        for (int r = 0; r < barcodes.length; ++r) {
            for (int c = 0; c < barcodes[0].length; ++c) {
                // both the previous and current bar-code entries exist
                if ((barcodes[r][c] != null)
                    && (barcodes[r][c].getValue() != null)
                    && (barcodes[r][c].getValue().length() > 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    // returns false if the user attempts to rescan using a different pallet
    public boolean loadFromArray(List<ScanCell> readBarcodes, boolean append) {
        if ((barcodes == null) || !append) {
            // first scan
            barcodes = new ScanCell[ScanCellPos.ROW_MAX][ScanCellPos.COL_MAX];
            for (ScanCell cell : readBarcodes) {
                int r = cell.getRow();
                int c = cell.getColumn();
                if ((cell.getValue() != null) && !cell.getValue().isEmpty()) {
                    barcodes[r][c] = cell;
                }
            }
            return true;
        }

        for (ScanCell cell : readBarcodes) {
            // both the previous and current barcode entries exist
            int r = cell.getRow();
            int c = cell.getColumn();
            if ((barcodes[r][c] != null) && (barcodes[r][c].getValue() != null)
                && (barcodes[r][c].getValue().length() > 0)
                && (cell.getValue() != null) && !cell.getValue().isEmpty()) {

                // entries don't match
                // user is rescanning with a different plate. (what a dummy)
                if (!barcodes[r][c].getValue().equals(cell.getValue())) {
                    return false;
                }
            }
        }

        // need to merge current with new
        for (ScanCell cell : readBarcodes) {
            // both the previous and current barcode entries exist
            int r = cell.getRow();
            int c = cell.getColumn();
            if ((barcodes[r][c] != null) && (barcodes[r][c].getValue() != null)
                && (barcodes[r][c].getValue().length() > 0)
                && (cell.getValue() != null) && !cell.getValue().isEmpty()) {
                barcodes[r][c] = cell;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        Assert.isNotNull(barcodes, "barcodes is null");
        StringBuffer result = new StringBuffer();

        for (int r = 0; r < barcodes.length; ++r) {
            for (int c = 0; c < barcodes[0].length; ++c) {
                if ((barcodes[r][c] == null)
                    || (barcodes[r][c].getValue() == null)) {
                    result.append(String.format("%s,%s,%d\r\n", palletBarcode,
                        Character.toString((char) ('A' + r)), c + 1));
                } else {
                    result.append(String.format("%s,%s,%d,%s,%s\r\n",
                        palletBarcode, Character.toString((char) ('A' + r)),
                        c + 1, barcodes[r][c].getValue(), new SimpleDateFormat(
                            "E dd/MM/yyyy HH:mm:ss").format(timestamp)));
                }
            }
        }
        return result.toString();
    }
}
