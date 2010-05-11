package edu.ualberta.med.biosamplescan.model;

import java.util.ArrayList;

public class PalletBarcodeHistory {

    private static ArrayList<String> PalletBarcodes = new ArrayList<String>();

    private static PalletBarcodeHistory instance = null;

    protected PalletBarcodeHistory() {
    }

    public static PalletBarcodeHistory getInstance() {
        if (instance == null) {
            instance = new PalletBarcodeHistory();
        }
        return instance;
    }

    public boolean addBarcode(String barcode) {
        if (!existsBarcode(barcode)) {
            PalletBarcodes.add(barcode);
            return true;
        } else {
            return false;
        }
    }

    public boolean existsBarcode(String barcode) {
        return PalletBarcodes.contains(barcode);
    }
}
