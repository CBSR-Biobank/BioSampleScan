package edu.ualberta.med.biosamplescan.model;

public class Plate {
	private String Id;
	private int Width;
	private int Height;
	private double[][] BarcodeTable;

	public Plate() {
		this.init("", 0, 0);
	}

	public Plate(String id, int widthTestTubes, int heightTestTubes) {
		this.init(id, heightTestTubes, heightTestTubes);
	}

	public boolean CheckId(String searchId) {
		if (searchId != null && this.Id != null && !searchId.isEmpty()
				&& !this.Id.isEmpty() && this.Id.equals(searchId)) {
			return true;
		}
		return false;

	}

	public void init(String id, int widthTestTubes, int heightTestTubes) {
		if (id == null || id.isEmpty()) {
			Id = "";
		} else {
			Id = id;
			Width = widthTestTubes;
			Height = heightTestTubes;
			BarcodeTable = new double[Width][Height];
		}
	}

	public double getBarcode(int x, int y) {
		if (x < Width && y < Height) {
			return BarcodeTable[x][y];
		}
		return 0;
	}

	public void setBarcode(int x, int y, double value) {
		if (x < Width && y < Height) {
			BarcodeTable[x][y] = value;
		}
	}

}
