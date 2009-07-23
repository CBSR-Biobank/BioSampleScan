package edu.ualberta.med.biosamplescan;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import edu.ualberta.med.biosamplescan.gui.ViewComposite;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.PlateSet;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scanlib.ScanLibFactory;

public class View extends ViewPart {
	public static final String ID = "edu.ualberta.med.biosamplescan.view";

	private ViewComposite viewComposite = null;
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
		//TODO MOVE ABOVE CODE TO TREE VIEWER VIEW WHEN READY

		plateSet = new PlateSet();
		plateSet.initPlate("Plate 1", 13, 8);
		plateSet.initPlate("Plate 2", 13, 8);
		plateSet.initPlate("Plate 3", 13, 8);
		plateSet.initPlate("Plate 4", 13, 8);

		configSettings = new ConfigSettings(); // parses scanlib.ini file
		viewComposite = new ViewComposite(parent, SWT.BORDER);

		if (viewComposite != null && configSettings != null && plateSet != null) {
			try {
				File f = new File("scanlibGUI.ini");
				if (!f.exists()) {
					f.createNewFile();
				}
				Wini ini = new Wini(f);
				configSettings.setDpi(ini.get("settings", "dpi"));
				if (ini.get("settings", "platemode") != null
						&& !ini.get("settings", "platemode").isEmpty()) {
					configSettings.setPlatemode(Integer.parseInt(ini.get(
							"settings", "platemode")));
				}
			}
			catch (InvalidFileFormatException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public ViewComposite getMain() {
		return viewComposite;
	}

	public ConfigSettings getConfigSettings() {
		return configSettings;
	}

	public PlateSet getPlateSet() {
		return plateSet;
	}

	public void setFocus() {
		if (viewComposite == null || configSettings == null || plateSet == null) {
			return;
		}

		viewComposite.setFocus();
	}

}