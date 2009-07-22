package edu.ualberta.med.biosamplescan;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.Main;
import edu.ualberta.med.biosamplescan.model.PlateSet;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scanlib.ScanLibFactory;

public class View extends ViewPart {
	public static final String ID = "edu.ualberta.med.biosamplescan.view";

	private Main main = null;
	private ConfigSettings configSettings = null;
	private PlateSet plateSet = null;

	public View() {
		BioSampleScanPlugin.getDefault().addView(this);
	}

	@Override
	public void dispose() {
		BioSampleScanPlugin.getDefault().removeView(this);

	}

	public void createPartControl(Composite parent) {
		// TODO: MOVE FOLLOWING CODE TO TREE VIEWER VIEW WHEN READY
		//
		String osname = System.getProperty("os.name");
		if (!osname.startsWith("Windows")) {
			if (ScanLibFactory.getScanLib().slIsTwainAvailable() != ScanLib.SC_SUCCESS) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						BioSampleScanPlugin.openError("TWAIN Driver Error",
								"TWAIN driver not installed on this computer.");
						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.close();
					}
				});
				return;
			}
		}
		// MOVE ABOVE CODE TO TREE VIEWER VIEW WHEN READY

		plateSet = new PlateSet();
		plateSet.initPlate("Plate 1", 0, 13); //TODO Bizzarre
		plateSet.initPlate("Plate 2", 8, 13);
		plateSet.initPlate("Plate 3", 8, 13);
		plateSet.initPlate("Plate 4", 8, 13);

		configSettings = new ConfigSettings(); // parses scanlib.ini file
		main = new Main(parent, SWT.BORDER);

	}

	public Main getMain() {
		return main;
	}

	public ConfigSettings getConfigSettings() {
		return configSettings;
	}

	public PlateSet getPlateSet() {
		return plateSet;
	}

	public void setFocus() {
		if (main == null || configSettings == null || plateSet == null) {
			return;
		}
		main.setFocus();
	}

}