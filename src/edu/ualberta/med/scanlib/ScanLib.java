
package edu.ualberta.med.scanlib;

public abstract class ScanLib {
    // Error codes returned by methods
    public static final int SC_SUCCESS = 0;
    public static final int SC_FAIL = -1;
    public static final int SC_TWAIN_UAVAIL = -2;
    public static final int SC_CALIBRATOR_NO_REGIONS = -3;
    public static final int SC_CALIBRATOR_ERROR = -4;
    public static final int SC_INI_FILE_ERROR = -5;
    public static final int SC_INVALID_DPI = -6;
    public static final int SC_INVALID_PLATE_NUM = -7;
    public static final int SC_FILE_SAVE_ERROR = -8;
    public static final int SC_INVALID_VALUE = -9;
    public static final int SC_INVALID_IMAGE = -10;

    public static final int DPI_300 = 300;

    public static final int DPI_400 = 400;

    public static final int DPI_600 = 600;
    
    public abstract int slIsTwainAvailable();

    public abstract int slSelectSourceAsDefault();

    public abstract int slConfigScannerBrightness(int brightness);

    public abstract int slConfigScannerContrast(int contrast);

    public abstract int slConfigPlateFrame(long plateNum, double left,
        double top, double right, double bottom);

    public abstract int slScanImage(long dpi, double left, double top,
        double right, double bottom, String filename);

    public abstract int slScanPlate(long dpi, long plateNum, String filename);

    public abstract int slCalibrateToPlate(long dpi, long plateNum);

    public abstract int slDecodePlate(long dpi, long plateNum);

    public abstract int slDecodeImage(long plateNum, String filename);

}
