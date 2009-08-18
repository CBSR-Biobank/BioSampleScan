
package edu.ualberta.med.biosamplescan.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.runtime.Assert;

public class PalletSet {
    private Pallet [] pallets;

    public PalletSet() {
        pallets = new Pallet [ConfigSettings.PALLET_NUM];
        for (int i = 0; i < pallets.length; ++i) {
            pallets[i] = new Pallet();
        }
    }

    public Pallet getPallet(Integer id) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        return pallets[id];
    }

    public void setPalletId(Integer id, String plateid) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        pallets[id].setPalleteBarcode(plateid);
    }

    public String getPalletId(Integer id) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        return pallets[id].getPlateBarcode();
    }

    public void setPalletTimestampNOW(Integer id) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        pallets[id].setPlateTimestampNOW();
    }

    public long getPalletTimestamp(Integer id) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        return pallets[id].getPlateTimestamp();
    }

    public void clearPallet(Integer id) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        pallets[id].clear();
    }

    public void loadFromScanlibFile(int id, boolean append) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        pallets[id].loadFromScanlibFile(append);
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
                Pallet pallet = pallets[pi];
                if (pallet == null) continue;
                out.write(pallet.toString());
            }
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearTable(int id) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        pallets[id].clear();
    }
}
