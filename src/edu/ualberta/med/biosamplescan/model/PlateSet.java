package edu.ualberta.med.biosamplescan.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import edu.ualberta.med.scanlib.ScanCell;

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

	private String nulltoblankString(String in) {
		if (in == null || in.isEmpty()) {
			return "";
		}
		else {
			return in;
		}
	}

	public int loadFromScanlibFile(int table, boolean append) {
		ScanCell[][] sc = null;
		try {
			sc = ScanCell.getScanLibResults();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		String[][] data = new String[8][13];
		for (int r = 0; r < 8; r++) {
			data[r][0] = Character.toString((char) (65 + r));
			for (int c = 0; c < 12; c++) {

				if (append) {
					if (this.getPlate(String.format("Plate %d", table), c + 1,
							r).isEmpty()) {
						data[r][c + 1] = nulltoblankString(sc[r][c].getValue());
					}
					else {
						data[r][c + 1] = this.getPlate(String.format(
								"Plate %d", table), c + 1, r);
					}
				}
				else {
					data[r][c + 1] = nulltoblankString(sc[r][c].getValue());
				}
			}
		}
		this.setPlate(String.format("Plate %d", table), data);
		return 0;
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void saveTables(String fileLocation, boolean[] tables) {
		String[] plateids = new String[tables.length]; //wrapper
		for (int i = 0; i < plateids.length; i++) {
			if (tables[i]) {
				plateids[i] = String.format("Plate %d", i + 1);
			}
		}
		this.savePlates(fileLocation, plateids);
	}

	public void savePlates(String fileLocation, String[] plateids) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(fileLocation));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			out.write("#Plate,Row,Col,Barcode,Date\r\n");
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		for (int pi = 0; pi < plateids.length; pi++) {
			if (plates.containsKey(plateids[pi])) {
				for (int r = 0; r < 8; r++) {
					for (int c = 0; c < 12; c++) {
						String barcode = plates.get(plateids[pi]).getBarcode()[r][c];
						if (!barcode.isEmpty()) {
							try {
								out.write(String.format("%d,%s,%d,%s,%s\r\n",
										pi + 1,
										//TODO platenumber depends on position in string[] 
										Character.toString((char) (65 + r)),
										c + 1, barcode, getDateTime()));
							}
							catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

		}
		try {
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
