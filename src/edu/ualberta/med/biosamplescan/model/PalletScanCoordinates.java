
package edu.ualberta.med.biosamplescan.model;

public class PalletScanCoordinates {
    public int id;
    public double left;
    public double top;
    public double right;
    public double bottom;

    public PalletScanCoordinates(int id, double left, double top, double right,
        double bottom) {
        this.id = id;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

}
