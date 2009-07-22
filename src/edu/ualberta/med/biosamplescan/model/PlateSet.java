package edu.ualberta.med.biosamplescan.model;

import java.util.HashMap;

public class PlateSet {
	private HashMap<String, Plate> plates;

	public PlateSet() {
		plates = new HashMap<String, Plate>();
	}

	public void initPlate(String id, int w, int h) {
		Plate dummyPlate = new Plate(w, h);
		plates.put(id, dummyPlate);
	}

	public boolean setPlate(String id, String barcodes[][]) {
		if (plates.containsKey(id)) {
			plates.get(id).setBarcode(barcodes);
			return true;
		}
		else {
			return false;
		}
	}

	public boolean setPlate(String id, int x, int y, String barcode) {
		if (plates.containsKey(id)) {
			plates.get(id).setBarcode(x, y, barcode);
			return true;
		}
		else {
			return false;
		}
	}

	public String[][] getPlate(String id) {
		if (plates.containsKey(id)) {
			return plates.get(id).getBarcode();
		}
		else {
			return null;
		}
	}

	public String getPlate(String id, int x, int y) {
		if (plates.containsKey(id)) {
			return plates.get(id).getBarcode(x, y);
		}
		else {
			return "";
		}
	}

}
