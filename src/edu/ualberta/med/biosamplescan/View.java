package edu.ualberta.med.biosamplescan;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.Main;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scanlib.ScanLibFactory;

public class View extends ViewPart {
	public static final String ID = "edu.ualberta.med.biosamplescan.view";

	private Main main = null;
	private ConfigSettings configSettings = null;
	private ScanLib scanlib = null;

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

		scanlib = ScanLibFactory.getScanLib();
		configSettings = new ConfigSettings(); // parses scanlib.ini file
		main = new Main(parent, SWT.BORDER);

	}

	public Main getMain() {
		return main;
	}

	public ConfigSettings getConfigSettings() {
		return configSettings;
	}

	public ScanLib getScanLib() {
		return scanlib;
	}

	@Override
	public void setFocus() {
		if (main == null || configSettings == null || scanlib == null)
			return;
		main.setFocus();
	}

}