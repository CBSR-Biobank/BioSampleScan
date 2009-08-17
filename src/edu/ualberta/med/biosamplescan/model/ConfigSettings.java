
package edu.ualberta.med.biosamplescan.model;

import java.io.File;
import java.io.IOException;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

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
    private double pallets[][] = new double [PALLET_NUM] [4];
    private int palletMode = PALLET_NUM;
    private String driverType = "TWAIN";
    private String appendSetting = "FALSE";
    private String lastSaveLocation = null;

    public static final int SUCCESS = 0;
    public static final int NOCHANGE = 1;
    public static final int CLEARDATA = 2;
    public static final int INVALID_INPUT = -1;
    public static final int FILE_ERROR = -2;

    private static ConfigSettings instance = null;

    private ConfigSettings() {
        this.loadFromFile();
        saveFileName = null;
    }

    public static ConfigSettings getInstance() {
        if (instance != null) return instance;
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
        if (strBrightness == null || strBrightness.isEmpty()) return INVALID_INPUT;
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
        if (strContrast == null || strContrast.isEmpty()) return INVALID_INPUT;
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
        if (strDpi == null || strDpi.isEmpty()) return INVALID_INPUT;
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
        for (int i = 0; i < 4; i++)
            if (this.pallets[pallet - 1][i] != 0) return true;
        return false;
    }

    public int setPallet(int pallet, double left, double top, double right,
        double bottom) {
        if (pallet - 1 >= PALLET_NUM) {
            return INVALID_INPUT;
        }
        if (left < 0 || right < 0 || top < 0 || bottom < 0) {
            return INVALID_INPUT;
        }
        if (this.getDriverType().equals("TWAIN")) {
            if (left > right || top > bottom) {
                return INVALID_INPUT;
            }
        }
        else { // WIA
        }

        if (left == this.pallets[pallet - 1][0]
            && top == this.pallets[pallet - 1][1]
            && right == this.pallets[pallet - 1][2]
            && bottom == this.pallets[pallet - 1][3]) {
            return NOCHANGE;
        }

        this.pallets[pallet - 1] = new double [4];
        this.pallets[pallet - 1][0] = left;
        this.pallets[pallet - 1][1] = top;
        this.pallets[pallet - 1][2] = right;
        this.pallets[pallet - 1][3] = bottom;

        if (left == top && top == right && right == bottom && bottom == 0) {
            return CLEARDATA;
        }
        return SUCCESS;
    }

    public double [] getPallet(int pallet) {
        if (pallet - 1 < PALLET_NUM) {
            return this.pallets[pallet - 1];
        }
        return null;
    }

    private String sfix(String in) {
        if (in == null || in.isEmpty()) {
            return "0";
        }
        return in;
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
        this.setBrightness(sfix(ini.get("scanner", "brightness")));
        this.setContrast(sfix(ini.get("scanner", "contrast")));

        for (int pallet = 0; pallet < ConfigSettings.PALLET_NUM; pallet++) {
            // TODO check for errors
            this.setPallet(pallet + 1, Double.valueOf(sfix(ini.get(
                String.format("pallet-%d", pallet + 1), "left"))),
                Double.valueOf(sfix(ini.get(String.format("pallet-%d",
                    pallet + 1), "top"))), Double.valueOf(sfix(ini.get(
                    String.format("pallet-%d", pallet + 1), "right"))),
                Double.valueOf(sfix(ini.get(String.format("pallet-%d",
                    pallet + 1), "bottom"))));
            /* curse eclipse auto-formatting */
        }
        this.setDpi(ini.get("settings", "dpi"));
        this.setPalletMode(ini.get("settings", "platemode"));
        this.setLastSaveLocation(ini.get("settings", "lastsavelocation"));
        this.setDriverType(ini.get("settings", "drivertype"));
        this.setAppendSetting(ini.get("settings", "appendsetting"));
        return SUCCESS;
    }

    public int setPalletMode(String platemode) { // TODO handle return values
        if (platemode == null || platemode.isEmpty()
            || Integer.parseInt(platemode) < 1
            || Integer.parseInt(platemode) > ConfigSettings.PALLET_NUM) {
            return INVALID_INPUT;
        }
        else {
            this.palletMode = Integer.parseInt(platemode);
            return saveToIni("settings", "platemode", platemode);
        }
    }

    public int getPalletMode() {
        return palletMode;
    }

    public int setLastSaveLocation(String lastSaveLocation) {
        this.lastSaveLocation = lastSaveLocation;
        return saveToIni("settings", "lastSaveLocation", lastSaveLocation);
    }

    public String getLastSaveLocation() {
        return lastSaveLocation;
    }

    public int setDriverType(String driverType) {
        if (driverType == null) driverType = "";
        if (driverType.equals("TWAIN") || driverType.equals("WIA")) {
            this.driverType = driverType;
            return saveToIni("settings", "drivertype", driverType);
        }
        return INVALID_INPUT;
    }

    public String getDriverType() {
        return driverType;
    }

    public int setAppendSetting(String appendSetting) {
        if (appendSetting == null) appendSetting = "";
        if (appendSetting.equals("TRUE") || appendSetting.equals("FALSE")) {
            this.appendSetting = appendSetting;
            return saveToIni("settings", "appendsetting", appendSetting);
        }
        return INVALID_INPUT;
    }

    public boolean getAppendSetting() {
        return appendSetting.equals("TRUE");
    }
}
