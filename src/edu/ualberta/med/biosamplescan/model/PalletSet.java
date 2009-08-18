
package edu.ualberta.med.biosamplescan.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.eclipse.core.runtime.Assert;

public class PalletSet {
    private HashMap<Integer, Pallet> pallets;

    public PalletSet() {
        pallets = new HashMap<Integer, Pallet>();
    }

    /* Integer id is the plate number */
    public void initPallet(Integer id) {
        pallets.put(id, new Pallet());
    }

    public Pallet getPallet(Integer id) {
        Pallet pallet = pallets.get(id);
        Assert.isNotNull(pallet);
        return pallet;
    }

    public void setPalletId(Integer id, String plateid) {
        Pallet pallet = pallets.get(id);
        Assert.isNotNull(pallet);
        pallets.get(id).setPalleteBarcode(plateid);
    }

    public String getPalletId(Integer id) {
        Pallet pallet = pallets.get(id);
        Assert.isNotNull(pallet);
        return pallet.getPlateBarcode();
    }

    public void setPalletTimestampNOW(Integer id) {
        Pallet pallet = pallets.get(id);
        Assert.isNotNull(pallet);
        pallet.setPlateTimestampNOW();
    }

    public long getPalletTimestamp(Integer id) {
        Pallet pallet = pallets.get(id);
        Assert.isNotNull(pallet);
        return pallet.getPlateTimestamp();
    }

    public void clearPallet(Integer id) {
        Pallet pallet = pallets.get(id);
        Assert.isNotNull(pallet);
        pallet.clear();
    }

    public void loadFromScanlibFile(int id, boolean append) {
        Pallet pallet = pallets.get(id);
        Assert.isNotNull(pallet);
        pallet.loadFromScanlibFile(append);
    }

    public void saveTables(String fileLocation, boolean [] tables,
        boolean appendFile) {
        Integer [] plateids = new Integer [tables.length]; // wrapper
        for (int i = 0; i < plateids.length; i++) {
            if (tables[i]) {
                plateids[i] = i + 1;
            }
        }
        savePallets(fileLocation, plateids, appendFile);
    }

    public void savePallets(String fileLocation, Integer [] plateids,
        boolean appendFile) {

        appendFile = false; // TODO depreciated, remove all references

        try {
            BufferedWriter out = null;
            out = new BufferedWriter(new FileWriter(fileLocation, appendFile));
            if (appendFile) {
                if (new File(fileLocation).length() <= 0) {
                    out.write("#PlateId,Row,Col,Barcode,Date\r\n");
                }
            }
            else {
                out.write("#PlateId,Row,Col,Barcode,Date\r\n");
            }
            for (int pi = 0; pi < plateids.length; pi++) {
                Pallet pallet = pallets.get(pi);
                if (pallet == null) continue;
                out.write(pallet.toString());
            }
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
