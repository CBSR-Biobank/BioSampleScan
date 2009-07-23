package edu.ualberta.med.biosamplescan.model;

public class Plate {
	private int Width;
	private int Height;
	private String[][] BarcodeTable;

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
		return BarcodeTable;
	}

	public void setBarcode(String[][] barcodes) {
		BarcodeTable = barcodes;
	}

}
