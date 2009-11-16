package edu.ualberta.med.biosamplescan.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.Assert;

import edu.ualberta.med.scanlib.ScanCell;

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

    public void loadFromArray(ScanCell[][] readBarcodes, boolean append) {
        if ((barcodes == null) || !append) {
            barcodes = readBarcodes;
            return;
        }

        // need to merge current with new
        for (int r = 0; r < barcodes.length; ++r) {
            for (int c = 0; c < barcodes[0].length; ++c) {
                if (((barcodes[r][c] == null) || (barcodes[r][c].getValue() == null))
                    && (readBarcodes[r][c] != null)
                    && (readBarcodes[r][c].getValue() != null)
                    && (readBarcodes[r][c].getValue().length() > 0)) {
                    barcodes[r][c] = readBarcodes[r][c];
                }
            }
        }
    }

    @Override
    public String toString() {
        Assert.isNotNull(barcodes, "barcodes is null");
        String result = new String();
        for (int r = 0; r < barcodes.length; ++r) {
            for (int c = 0; c < barcodes[0].length; ++c) {
                if ((barcodes[r][c] == null)
                    || (barcodes[r][c].getValue() == null))
                    continue;

                result = result.concat(String.format("%s,%s,%d,%s,%s\r\n",
                    palletBarcode, Character.toString((char) ('A' + r)), c + 1,
                    barcodes[r][c].getValue(), new SimpleDateFormat(
                        "E dd/MM/yyyy HH:mm:ss").format(timestamp)));
            }
        }
        return result;
    }
}
