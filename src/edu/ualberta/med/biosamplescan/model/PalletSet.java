
package edu.ualberta.med.biosamplescan.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.Assert;

public class PalletSet {
    private Pallet [] pallets;

    public PalletSet() {
        pallets = new Pallet [ConfigSettings.PALLET_NUM];
    }

    public int getPalletCount() {
        int result = 0;
        for (int i = 0; i < pallets.length; ++i) {
            if ((pallets[i] != null)
                && (pallets[i].getPlateBarcode().length() > 0)) {
                ++result;
            }
        }
        return result;
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
            pallets[id] = new Pallet(id);
        }
        pallets[id].loadFromScanlibFile(append);
    }

    public String savePallet(String fileLocation, Pallet pallet) {
        try {
            String filename = fileLocation
                + "/"
                + pallet.getPlateBarcode()
                + "_"
                + new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(new Date())
                + ".csv";

            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            out.write("#PlateId,Row,Col,Barcode,Date\r\n");
            out.write(pallet.toString());
            out.close();
            return filename;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clearTable(int id) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        if (pallets[id] == null) return;
        pallets[id].clear();
    }
}
