
package edu.ualberta.med.biosamplescan.model;

import java.awt.Dimension;
import java.util.Date;

import org.eclipse.core.runtime.Assert;

public class Pallet {
    private final int width;
    private final int height;
    private String [][] barcodeTable;
    private String plateIdText;
    private long timestamp;

    // TODO place all properties into a hash map

    public Pallet(int widthTestTubes, int heightTestTubes) {
        this.width = widthTestTubes;
        this.height = heightTestTubes;
        barcodeTable = new String [this.width] [this.height];
    }

    public Pallet(Dimension dim) {
        this(dim.width, dim.height);
    }

    public String [][] getBarcode() {
        return this.barcodeTable;
    }

    public void setBarcode(String [][] barcodes) {
        Assert.isTrue(barcodes.length == barcodeTable.length,
            "invalid width dimension for barcode array: " + barcodes.length);
        Assert.isTrue(barcodes[0].length == barcodeTable[0].length,
            "invalid height dimension for barcode array: " + barcodes[0].length);
        barcodeTable = barcodes;
    }

    public void setPlateIdText(String plateIdText) {
        this.plateIdText = plateIdText;
    }

    public String getPlateIdText() {
        return plateIdText;
    }

    public Dimension getDim() {
        return new Dimension(width, height);
    }

    public void setPlateTimestampNOW() {
        timestamp = (new Date()).getTime();
    }

    public long getPlateTimestamp() {
        return timestamp;
    }

    public void clear() {
        for (String [] row : barcodeTable) {
            for (String cell : row) {
                cell = "";
            }
        }
    }

}
