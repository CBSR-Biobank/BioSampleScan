package edu.ualberta.med.biosamplescan.model;

import java.awt.Dimension;

public class Plate {
	private int Width;
	private int Height;
	private String[][] BarcodeTable;
	private String plateIdText;

	public Plate() {
		this.init(-1, -1);
	}

	public Plate(int widthTestTubes, int heightTestTubes) {
		this.init(heightTestTubes, heightTestTubes);
	}

	public void init(int widthTestTubes, int heightTestTubes) {
		Width = widthTestTubes;
		Height = heightTestTubes;
		BarcodeTable = new String[Width][Height];
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
		return new Dimension(this.Width, this.Height);
	}

}
