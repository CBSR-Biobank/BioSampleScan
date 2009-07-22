package edu.ualberta.med.biosamplescan.model;

import java.io.File;
import java.io.IOException;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import edu.ualberta.med.scanlib.ScanLib;

public class ConfigSettings {
	private int brightness = 0;
	private int contrast = 0;
	private int dpi = ScanLib.DPI_300;
	private double plates[][] = new double[PLATENUM][4];

	public static int PLATENUM = 4;

	public static final int CS_SUCCESS = 0;
	public static final int CS_NOCHANGE = 1;
	public static final int CS_CLEARDATA = 2;
	public static final int CS_INVALID_INPUT = -1;
	public static final int CS_FILE_ERROR = -2;

	public ConfigSettings() {
		this.loadFromFile();
	}

	public int setBrightness(String strBrightness) {
		if (strBrightness == null || strBrightness.isEmpty()) return CS_INVALID_INPUT;
		int intBrightness = Integer.parseInt(strBrightness);
		if (intBrightness > 1000 || intBrightness < -1000) {
			return CS_INVALID_INPUT;
		}
		else if (intBrightness == this.brightness) {
			return CS_NOCHANGE;
		}
		else {
			this.brightness = intBrightness;
			return CS_SUCCESS;
		}
	}

	public int getBrightness() {
		return brightness;
	}

	public int setContrast(String strContrast) {
		if (strContrast == null || strContrast.isEmpty()) return CS_INVALID_INPUT;
		int intContrast = Integer.parseInt(strContrast);
		if (intContrast > 1000 || intContrast < -1000) {
			return CS_INVALID_INPUT;
		}
		else if (intContrast == this.contrast) {
			return CS_NOCHANGE;
		}
		else {
			this.contrast = intContrast;
			return CS_SUCCESS;
		}
	}

	public int getContrast() {
		return contrast;
	}

	public int setDpi(String strDpi) {
		if (strDpi == null || strDpi.isEmpty()) return CS_INVALID_INPUT;
		int intDpi = Integer.parseInt(strDpi);

		if (intDpi == ScanLib.DPI_300 || intDpi == ScanLib.DPI_600) {
			if (intDpi == this.dpi) {
				return CS_NOCHANGE;
			}
			else {
				this.dpi = intDpi;
				return CS_SUCCESS;
			}
		}
		else {
			return CS_INVALID_INPUT;
		}
	}

	public int getDpi() {
		return dpi;
	}

	public int setPlate(int plate, double left, double top, double right,
			double bottom) {
		if (plate - 1 >= PLATENUM) {
			return CS_INVALID_INPUT;
		}
		if (left == this.plates[plate - 1][0]
				&& top == this.plates[plate - 1][1]
				&& right == this.plates[plate - 1][2]
				&& bottom == this.plates[plate - 1][3]) {
			return CS_NOCHANGE;

		}
		if (left > right) {
			return CS_INVALID_INPUT;
		}

		this.plates[plate - 1] = new double[4];
		this.plates[plate - 1][0] = left;
		this.plates[plate - 1][1] = top;
		this.plates[plate - 1][2] = right;
		this.plates[plate - 1][3] = bottom;

		if (left == top && top == right && right == bottom && bottom == 0) {
			return CS_CLEARDATA;
		}
		else {
			return CS_SUCCESS;
		}
	}

	public double[] getPlate(int plate) {
		if (plate - 1 < PLATENUM) {
			return this.plates[plate - 1];
		}
		else {
			return null;
		}
	}

	private String sfix(String in) {
		if (in == null || in.isEmpty()) {
			return "0";
		}
		else {
			return in;
		}
	}

	public int loadFromFile() {
		Wini ini;
		try {
			ini = new Wini(new File("scanlib.ini"));
		}
		catch (InvalidFileFormatException e) {
			e.printStackTrace();
			return CS_FILE_ERROR;
		}
		catch (IOException e) {
			e.printStackTrace();
			return CS_FILE_ERROR;
		}
		this.setDpi(String.valueOf(ScanLib.DPI_300));
		this.setBrightness(sfix(ini.get("scanner", "brightness")));
		this.setContrast(sfix(ini.get("scanner", "contrast")));

		for (int plate = 0; plate < ConfigSettings.PLATENUM; plate++) { // TODO
			// check
			// for
			// errors
			this.setPlate(plate + 1, Double.valueOf(sfix(ini.get(String.format(
					"plate-%d", plate + 1), "left"))), Double.valueOf(sfix(ini
					.get(String.format("plate-%d", plate + 1), "top"))), Double
					.valueOf(sfix(ini.get(String.format("plate-%d", plate + 1),
							"right"))), Double.valueOf(sfix(ini.get(String
					.format("plate-%d", plate + 1), "bottom"))));
			/* curse eclipse auto-formatting */
		}
		return CS_SUCCESS;
	}
}
