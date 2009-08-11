package edu.ualberta.med.biosamplescan.model;

import java.awt.Dimension;
import java.util.Date;

public class Plate {
	private final int width;
	private final int height;
	private String[][] BarcodeTable;
	private String plateIdText;
	private long timestamp;

	//TODO place all properties into a hash map

	public Plate(int widthTestTubes, int heightTestTubes) {
		this.width = widthTestTubes;
		this.height = heightTestTubes;
		BarcodeTable = new String[this.width][this.height];
	}

	public String[][] getBarcode() {
		return this.BarcodeTable;
	}

	public void setBarcode(String[][] barcodes) {
		this.BarcodeTable = barcodes;
	}

	public void setPlateIdText(String plateIdText) {
		this.plateIdText = plateIdText;
	}

	public String getPlateIdText() {
		return plateIdText;
	}

	public Dimension getDim() {
		return new Dimension(this.width, this.height);
	}

	public void setPlateTimestampNOW() {
		this.timestamp = (new Date()).getTime();

	}

	public long getPlateTimestamp() {
		return this.timestamp;

	}

}
