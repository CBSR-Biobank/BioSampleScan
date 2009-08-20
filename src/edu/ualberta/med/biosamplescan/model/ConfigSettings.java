package edu.ualberta.med.biosamplescan.model;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.Assert;
import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.scanlib.ScanLib;

/**
 * Singleton class that stores the configuration settings for the application.
 */
public class ConfigSettings {

    public static final int PALLET_NUM = 4;

    private String saveFileName;

    private int brightness = 0;
    private int contrast = 0;
    private int dpi = ScanLib.DPI_300;
    private PalletScanCoordinates[] palletScanCoordinates;
    private int palletsMax = PALLET_NUM;
    private String driverType = "TWAIN";
    private String lastSaveDir = null;

    public static final int SUCCESS = 0;
    public static final int NOCHANGE = 1;
    public static final int CLEARDATA = 2;
    public static final int INVALID_INPUT = -1;
    public static final int FILE_ERROR = -2;

    private static ConfigSettings instance = null;

    private ConfigSettings() {
        saveFileName = null;
        palletScanCoordinates = new PalletScanCoordinates[PALLET_NUM];
        loadFromFile();

        String msg = new String();
        if (getPalletCount() == 0) {
            msg = "Please configure scanner.";
        }
        else {
            msg = "Configuration loaded.";
        }
        BioSampleScanPlugin.getDefault().getPalletSetView()
            .updateStatusBar(msg);
    }

    public int getPalletCount() {
        int result = 0;
        for (int i = 0; i < palletScanCoordinates.length; ++i) {
            if (palletScanCoordinates[i] != null)
                ++result;
        }
        return result;
    }

    public static ConfigSettings getInstance() {
        if (instance != null)
            return instance;
        instance = new ConfigSettings();
        return instance;
    }

    public void setSaveFileName(String fileName) {
        saveFileName = fileName;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    private int saveToIni(String group, String key, int value) {
        return this.saveToIni(group, key, String.valueOf(value));
    }

    private int saveToIni(String group, String key, String value) {
        try {
            File f = new File("scanlib.ini");
            if (!f.exists()) {
                f.createNewFile();
            }
            else {
                Wini ini = new Wini(f);
                ini.put(group, key, value);
                ini.store();
            }
        }
        catch (InvalidFileFormatException e) {
            e.printStackTrace();
            return FILE_ERROR;
        }
        catch (IOException e) {
            e.printStackTrace();
            return FILE_ERROR;
        }
        return SUCCESS;
    }

    public int setBrightness(String strBrightness) {
        if (strBrightness == null || strBrightness.isEmpty())
            return INVALID_INPUT;
        int intBrightness = Integer.parseInt(strBrightness);
        if (intBrightness > 1000 || intBrightness < -1000) {
            return INVALID_INPUT;
        }
        else if (intBrightness == this.brightness) {
            return NOCHANGE;
        }
        this.brightness = intBrightness;
        return SUCCESS;
    }

    public int getBrightness() {
        return brightness;
    }

    public int setContrast(String strContrast) {
        if (strContrast == null || strContrast.isEmpty())
            return INVALID_INPUT;
        int intContrast = Integer.parseInt(strContrast);
        if (intContrast > 1000 || intContrast < -1000) {
            return INVALID_INPUT;
        }
        else if (intContrast == this.contrast) {
            return NOCHANGE;
        }
        this.contrast = intContrast;
        return SUCCESS;
    }

    public int getContrast() {
        return contrast;
    }

    public int setDpi(String strDpi) {
        if (strDpi == null || strDpi.isEmpty())
            return INVALID_INPUT;
        int intDpi = Integer.parseInt(strDpi);

        if (intDpi > 0 && intDpi <= 600) {
            if (intDpi == this.dpi) {
                return NOCHANGE;
            }
            this.dpi = intDpi;
            return saveToIni("settings", "dpi", intDpi);
        }
        return INVALID_INPUT;
    }

    public int getDpi() {
        return dpi;
    }

    public boolean palletIsSet(int pallet) {
        Assert.isTrue((pallet > 0) && (pallet <= PALLET_NUM),
            "invalid pallet number: " + pallet);
        return (palletScanCoordinates[pallet - 1] != null);
    }

    public int setPallet(int pallet, double left, double top, double right,
        double bottom) {
        Assert.isTrue((pallet > 0) && (pallet <= PALLET_NUM),
            "invalid pallet number: " + pallet);
        if ((left < 0) || (right < 0) || (top < 0) || (bottom < 0)) {
            return INVALID_INPUT;
        }

        if (this.getDriverType().equals("TWAIN")) {
            if ((left > right) || (top > bottom)) {
                return INVALID_INPUT;
            }
        }

        --pallet;

        if ((left == 0) && (top == 0) && (right == 0) && (bottom == 0)) {
            if (palletScanCoordinates[pallet] != null)
                palletScanCoordinates[pallet] = null;
            return CLEARDATA;
        }

        if ((palletScanCoordinates[pallet] != null)
            && (left == palletScanCoordinates[pallet].left)
            && (top == palletScanCoordinates[pallet].top)
            && (right == palletScanCoordinates[pallet].right)
            && (bottom == palletScanCoordinates[pallet].bottom)) {
            return NOCHANGE;
        }

        palletScanCoordinates[pallet] =
            new PalletScanCoordinates(pallet + 1, left, top, right, bottom);
        return SUCCESS;
    }

    public PalletScanCoordinates getPallet(int pallet) {
        Assert.isTrue((pallet > 0) && (pallet <= PALLET_NUM),
            "invalid pallet number: " + pallet);
        return palletScanCoordinates[pallet - 1];
    }

    public int loadFromFile() {
        Wini ini;
        try {
            File f = new File("scanlib.ini");
            if (!f.exists()) {
                f.createNewFile();
            }
            ini = new Wini(f);
        }
        catch (InvalidFileFormatException e) {
            e.printStackTrace();
            return FILE_ERROR;
        }
        catch (IOException e) {
            e.printStackTrace();
            return FILE_ERROR;
        }

        Ini.Section section = ini.get("scanner");
        if (section != null) {
            String brightness = section.get("brightness");
            String contrast = section.get("contrast");

            if (brightness != null)
                setBrightness(brightness);
            if (contrast != null)
                setContrast(contrast);
        }

        for (int pallet = 1; pallet <= ConfigSettings.PALLET_NUM; pallet++) {
            section = ini.get("plate-" + pallet);
            if (section == null)
                continue;

            Double left = section.get("left", Double.class);
            Double top = section.get("top", Double.class);
            Double right = section.get("right", Double.class);
            Double bottom = section.get("bottom", Double.class);

            if ((left != null) && (top != null) && (right != null)
                && (bottom != null)) {

                setPallet(pallet, left.doubleValue(), top.doubleValue(), right
                    .doubleValue(), bottom.doubleValue());
            }
        }

        section = ini.get("settings");
        if (section != null) {
            String dpi = section.get("dpi");
            if (dpi != null)
                setDpi(dpi);

            Integer palletcount = section.get("palletcount", Integer.class);
            if (palletcount != null)
                setPalletCount(palletcount);

            String lastsavedir = section.get("lastsavedir");
            if (lastsavedir != null)
                setLastSaveDir(lastsavedir);

            String drivertype = section.get("drivertype");
            if (drivertype != null)
                setDriverType(drivertype);
        }

        return SUCCESS;
    }

    public int setPalletCount(int palletcount) {
        palletsMax = palletcount;
        return saveToIni("settings", "palletcount", palletsMax);
    }

    public int getPalletMax() {
        return palletsMax;
    }

    public int setLastSaveDir(String lastSaveDir) {
        this.lastSaveDir = lastSaveDir;
        return saveToIni("settings", "lastSaveDir", lastSaveDir);
    }

    public String getLastSaveDir() {
        return lastSaveDir;
    }

    public int setDriverType(String driverType) {
        if (driverType == null)
            driverType = "";
        if (driverType.equals("TWAIN") || driverType.equals("WIA")) {
            this.driverType = driverType;
            return saveToIni("settings", "drivertype", driverType);
        }
        return INVALID_INPUT;
    }

    public String getDriverType() {
        return driverType;
    }
}
