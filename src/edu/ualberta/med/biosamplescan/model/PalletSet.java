
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

    public void setPalletBarocode(Integer id, String palletBarcode) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        Assert.isTrue(pallets[id] != null, "invalid pallet number: " + id);
        pallets[id].setPalleteBarcode(palletBarcode);
    }

    public String getPalletBarcode(Integer id) {
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

    public void savePallets(String fileLocation) {
        try {
            BufferedWriter out = null;
            out = new BufferedWriter(new FileWriter(fileLocation));
            out.write("#PlateId,Row,Col,Barcode,Date\r\n");
            for (Pallet pallet : pallets) {
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
        if (pallets[id] == null) return;
        pallets[id].clear();
    }
}
