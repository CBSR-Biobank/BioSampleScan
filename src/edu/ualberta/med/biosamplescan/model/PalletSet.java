
package edu.ualberta.med.biosamplescan.model;

import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.eclipse.core.runtime.Assert;

import edu.ualberta.med.scanlib.ScanCell;

public class PalletSet {
    private HashMap<Integer, Pallet> pallets;

    public PalletSet() {
        pallets = new HashMap<Integer, Pallet>();
    }

    /* Integer id is the plate number */
    public void initPallet(Integer id, int w, int h) {
        Pallet dummyPlate = new Pallet(w, h);
        pallets.put(id, dummyPlate);
    }

    public boolean setPallet(Integer id, String barcodes[][]) {
        if (pallets.containsKey(id)) {
            pallets.get(id).setBarcode(barcodes);
            return true;
        }
        else {
            return false;
        }
    }

    public String [][] getPallet(Integer id) {
        if (pallets.containsKey(id)) {
            return pallets.get(id).getBarcode();
        }
        else {
            return null;
        }
    }

    public Dimension getPalletDim(Integer id) {
        if (pallets.containsKey(id)) {
            return pallets.get(id).getDim();
        }
        else {
            return null;
        }
    }

    public boolean setPalletId(Integer id, String plateid) {
        if (pallets.containsKey(id)) {
            pallets.get(id).setPlateIdText(plateid);
            return true;
        }
        else {
            return false;
        }
    }

    public String getPalletId(Integer id) {
        if (pallets.containsKey(id)) {
            if (pallets.get(id).getPlateIdText() == null) {
                return "";
            }
            else {
                return pallets.get(id).getPlateIdText();
            }
        }
        else {
            return null;
        }
    }

    public boolean setPalletTimestampNOW(Integer id) {
        if (pallets.containsKey(id)) {
            pallets.get(id).setPlateTimestampNOW();
            return true;
        }
        else {
            return false;
        }
    }

    public long getPalletTimestamp(Integer id) {
        if (pallets.containsKey(id)) {
            return pallets.get(id).getPlateTimestamp();
        }
        else {
            return 0;
        }
    }

    private String nulltoblankString(String in) {
        if (in == null || in.isEmpty()) {
            return "";
        }
        else {
            return in;
        }
    }

    public int loadFromScanlibFile(int table, boolean append) {
        ScanCell [][] sc = null;
        try {
            sc = ScanCell.getScanLibResults();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String [][] data = null;
        if (append) {
            data = this.getPallet(table);
        }
        else {
            data = new String [8] [13];
        }

        for (int r = 0; r < 8; r++) {
            data[r][0] = Character.toString((char) (65 + r));
            for (int c = 0; c < 12; c++) {
                if (data[r][c + 1] == null || data[r][c + 1].isEmpty()) {
                    data[r][c + 1] = nulltoblankString(sc[r][c].getValue());
                }
            }
        }
        this.setPallet(table, data);
        return 0;
    }

    public void saveTables(String fileLocation, boolean [] tables,
        boolean appendFile) {
        Integer [] plateids = new Integer [tables.length]; // wrapper
        for (int i = 0; i < plateids.length; i++) {
            if (tables[i]) {
                plateids[i] = i + 1;
            }
        }
        this.savePallets(fileLocation, plateids, appendFile);
    }

    public void savePallets(String fileLocation, Integer [] plateids,
        boolean appendFile) {

        appendFile = false; // TODO depreciated, remove all references

        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(fileLocation, appendFile));
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            if (appendFile) {
                if (new File(fileLocation).length() <= 0) {
                    out.write("#PlateId,Row,Col,Barcode,Date\r\n");
                }
            }
            else {
                out.write("#PlateId,Row,Col,Barcode,Date\r\n");
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        for (int pi = 0; pi < plateids.length; pi++) {
            if (pallets.containsKey(plateids[pi])) {
                for (int r = 0; r < 8; r++) {
                    for (int c = 1; c < 13; c++) {
                        if (pallets.get(plateids[pi]).getBarcode() == null) {
                            return;
                        }
                        try {
                            String barcode = pallets.get(plateids[pi]).getBarcode()[r][c];
                            if (barcode != null && !barcode.isEmpty()) {
                                try {
                                    out.write(String.format(
                                        "%s,%s,%d,%s,%s\r\n",
                                        pallets.get(plateids[pi]).getPlateIdText(),
                                        Character.toString((char) (65 + r)),
                                        c,
                                        barcode,
                                        new SimpleDateFormat(
                                            "E dd/MM/yyyy HH:mm:ss").format(pallets.get(
                                            plateids[pi]).getPlateTimestamp())));
                                }
                                catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
        try {
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearTable(int p) {
        Assert.isTrue(p < pallets.size(), "invalid pallet number: " + p);
        Pallet pallet = pallets.get(p);
        Assert.isNotNull(pallet, "pallet not initialized: " + p);
        pallet.clear();
    }
}
