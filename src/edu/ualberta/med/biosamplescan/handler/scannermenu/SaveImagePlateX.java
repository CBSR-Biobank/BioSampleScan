package edu.ualberta.med.biosamplescan.handler.scannermenu;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.View;
import edu.ualberta.med.biosamplescan.gui.ViewComposite;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scanlib.ScanLibFactory;

public class SaveImagePlateX {
	// TODO bring up a dialog if all the settings for plate is 0,0,0,0;
	public static final Object execute(ExecutionEvent event, int platenum)
			throws ExecutionException {
		ViewComposite viewComposite = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getMain();
		ConfigSettings configSettings = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getConfigSettings();
		FileDialog dlg = new FileDialog(viewComposite.getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.bmp", "*.*" });
		dlg.setText(String.format("Scan Plate %d and Save to...", platenum));
		String saveLocation = dlg.open();
		if (saveLocation == null) {
			return null;
		}
		int scanlibReturn = ScanLibFactory.getScanLib().slScanPlate(
				configSettings.getDpi(), platenum, saveLocation);
		System.out.println(scanlibReturn);
		switch (scanlibReturn) {
			case (ScanLib.SC_SUCCESS):
				break;
			case (ScanLib.SC_INVALID_DPI):
				MessageDialog.openError(viewComposite.getActiveShell(), "Error", String
						.format("%s\nReturned Error Code: %d\n",
								"Scanlib ScanPlate", "Invalid Dpi"));
				break;
			case (ScanLib.SC_INVALID_PLATE_NUM):
				MessageDialog.openError(viewComposite.getActiveShell(), "Error", String
						.format("%s\nReturned Error Code: %d\n",
								"Scanlib ScanPlaten", "Ivalid Plate Number"));
				break;
			case (ScanLib.SC_CALIBRATOR_NO_REGIONS):
				MessageDialog.openError(viewComposite.getActiveShell(), "Error", String
						.format("%s\nReturned Error Code: %d\n",
								"Scanlib ScanPlate", "Calibrator Error"));
				break;
			case (ScanLib.SC_FAIL):
				MessageDialog.openError(viewComposite.getActiveShell(), "Error", String
						.format("%s\nReturned Error Code: %d\n",
								"Scanlib ScanPlate", "Failure"));
				break;
			case (ScanLib.SC_FILE_SAVE_ERROR):
				MessageDialog.openError(viewComposite.getActiveShell(), "Error", String
						.format("%s\nReturned Error Code: %d\n",
								"Scanlib ScanPlate", "Failed to save to file"));
				break;
		}
		return null;
	}
}
