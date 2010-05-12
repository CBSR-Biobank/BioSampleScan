package edu.ualberta.med.biosamplescan.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.Assert;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.scannerconfig.scanlib.ScanCell;

public class PalletSet {
    private Pallet[] pallets;

    public PalletSet() {
        pallets = new Pallet[BioSampleScanPlugin.getDefault().getPalletsMax()];
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

    public int getPalletsStored() {
        return pallets.length;
    }

    public boolean existsPalletObject(Integer id) {
        return pallets[id] != null;
    }

    public boolean isEmptyPalletBarcode(Integer id) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        Assert.isNotNull(pallets[id], "invalid pallet number: " + id);
        return pallets[id].isEmpty();
    }

    public String getPalletBarcode(Integer id) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        Assert.isNotNull(pallets[id], "invalid pallet number: " + id);
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

    // returns false if the user attempts to rescan with a different pallet
    public boolean loadFromArray(int id, ScanCell[][] readBarcodes,
        boolean append) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        if (pallets[id] == null) {
            pallets[id] = new Pallet(id);
        }
        if (pallets[id].loadFromArray(readBarcodes, append)) {
            return true;
        } else {
            pallets[id] = null;
            return false;
        }
    }

    public String savePalletToDir(String dir, Pallet pallet) {
        String filename = dir + "/" + pallet.getPlateBarcode() + "_"
            + new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(new Date())
            + ".csv";
        savePalletToFile(filename, pallet);
        return filename;
    }

    public void savePalletToFile(String filename, Pallet pallet) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            out.write("#PlateId,Row,Col,Barcode,Date\r\n");
            out.write(pallet.toString());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearTable(int id) {
        Assert.isTrue((id >= 0) && (id < pallets.length),
            "invalid pallet number: " + id);
        if (pallets[id] == null)
            return;
        pallets[id].clear();
    }
}
