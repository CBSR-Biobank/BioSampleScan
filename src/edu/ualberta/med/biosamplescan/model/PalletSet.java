
package edu.ualberta.med.biosamplescan.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.runtime.Assert;

public class PalletSet {
    private Pallet [] pallets;

    public PalletSet() {
        pallets = new Pallet [ConfigSettings.PALLET_NUM];
    }

    public Pallet getPallet(Integer id) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        return pallets[id];
    }

    public void setPalletId(Integer id, String plateid) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        Assert.isTrue(pallets[id] != null, "invalid pallet number: " + id);
        pallets[id].setPalleteBarcode(plateid);
    }

    public String getPalletId(Integer id) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        Assert.isTrue(pallets[id] != null, "invalid pallet number: " + id);
        return pallets[id].getPlateBarcode();
    }

    public void setPalletTimestampNow(Integer id) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        pallets[id].setPlateTimestampNow();
    }

    public long getPalletTimestamp(Integer id) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        Assert.isTrue(pallets[id] != null, "invalid pallet number: " + id);
        return pallets[id].getPlateTimestamp();
    }

    public void loadFromScanlibFile(int id, boolean append) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        if (pallets[id] == null) {
            pallets[id] = new Pallet();
        }
        pallets[id].loadFromScanlibFile(append);
    }

    public void saveTables(String fileLocation, boolean [] tables) {
        Integer [] plateids = new Integer [tables.length]; // wrapper
        for (int i = 0; i < plateids.length; i++) {
            if (pallets[i] == null) continue;
            if (tables[i]) {
                plateids[i] = i + 1;
            }
        }
        savePallets(fileLocation, plateids);
    }

    public void savePallets(String fileLocation, Integer [] plateids) {
        try {
            BufferedWriter out = null;
            out = new BufferedWriter(new FileWriter(fileLocation));
            out.write("#PlateId,Row,Col,Barcode,Date\r\n");
            for (int pi = 0; pi < plateids.length; pi++) {
                if (pallets[pi] == null) continue;
                Pallet pallet = pallets[pi];
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
        if (pallets[id] == null) return;
        pallets[id].clear();
    }
}
