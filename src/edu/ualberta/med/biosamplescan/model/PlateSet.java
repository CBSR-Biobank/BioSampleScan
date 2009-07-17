package edu.ualberta.med.biosamplescan.model;

import java.util.ArrayList;

public class PlateSet {
	private ArrayList<Plate> Plates;

	private int PlateNotFound(String id) { // returns the plate number
		int lastEmptyPlate = -2;
		for (int i = 0; i < Plates.size(); i++) {
			if (Plates.get(i).CheckId(id)) { // found plate
				return 0;
			} else {
				lastEmptyPlate = i + 1;
			}
		}
		return lastEmptyPlate; // return n+1
	}

	public PlateSet(String[] plateids, int width, int height) {
		Plates = new ArrayList<Plate>();
		this.init(plateids, width, height);
	}

	public void init(String[] plateids, int width, int height) {
		for (int i = 0; i < plateids.length; i++) {
			int pnf = PlateNotFound(plateids[i]);
			switch (pnf) {
			case (-1):
				System.out.println("Fatal: PlateNotFound return -1");
				System.exit(-1);
				break;

			case (0):
				break;

			default:
				Plates.get(pnf - 1).init(plateids[i], width, height);
				break;
			}
		}
		for (int plate = 0; plate < Plates.size(); plate++) {
			boolean foundplate = false;
			for (int idi = 0; idi < plateids.length; idi++) {
				if (Plates.get(plate).CheckId(plateids[idi])) {
					foundplate = true;
				}
			}
			if (!foundplate) {
				Plates.get(plate).init("", 0, 0);
			}
		}
	}

	public double getBarcode(String plateid, int x, int y) {
		for (int i = 0; i < Plates.size(); i++) {
			if (Plates.get(i).CheckId(plateid)) { // found plate
				return Plates.get(i).getBarcode(x, y);
			}
		}
		return -1;
	}

	public boolean setBarcode(String plateid, int x, int y, double value) {
		for (int i = 0; i < Plates.size(); i++) {
			if (Plates.get(i).CheckId(plateid)) { // found plate
				Plates.get(i).setBarcode(x, y, value);
				return true;
			}
		}
		return false;
	}

}
