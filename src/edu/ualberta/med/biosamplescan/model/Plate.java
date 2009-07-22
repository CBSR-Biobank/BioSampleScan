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

	public String getBarcode(int x, int y) {
		if (x < Width && x >= 0 && y < Height && y >= 0) return BarcodeTable[x][y];
		else {
			return "";
		}
	}

	public String[][] getBarcode() {
		return BarcodeTable;
	}

	public void setBarcode(int x, int y, String barcode) {
		if (x < Width && x >= 0 && y < Height && y >= 0) {
			BarcodeTable[x][y] = barcode;
		}
	}

	public void setBarcode(String[][] barcodes) {
		BarcodeTable = barcodes;
	}

}
