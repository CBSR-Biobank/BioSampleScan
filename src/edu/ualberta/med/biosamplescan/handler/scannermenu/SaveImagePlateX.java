package edu.ualberta.med.biosamplescan.handler.scannermenu;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.gui.ViewComposite;
import edu.ualberta.med.biosamplescan.singleton.ConfigSettings;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scanlib.ScanLibFactory;

public class SaveImagePlateX {
	public static final Object execute(ExecutionEvent event, int platenum)
			throws ExecutionException {
		ViewComposite viewComposite = ((PlateSetEditor) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart()).getViewComposite();
		ConfigSettings configSettings = ConfigSettings.getInstance();

		if (configSettings.getPlatemode() < platenum) {
			return null;
		} //TODO actually disable menu items

		if (!configSettings.plateIsSet(platenum)) { //TODO apply this code to all applicable routines
			MessageDialog.openError(viewComposite.getActiveShell(), "Error",
					String.format("plate %d:\n%s", platenum,
							"Has no dimensions set"));
			return null;
		}

		FileDialog dlg = new FileDialog(viewComposite.getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.bmp", "*.*" });
		dlg.setText(String.format("Scan Plate %d and Save to...", platenum));
		String saveLocation = dlg.open();
		if (saveLocation == null) {
			return null;
		}

		int scanlibReturn = ScanLibFactory.getScanLib().slScanPlate(
				configSettings.getDpi(), platenum, saveLocation);
		switch (scanlibReturn) {
			case (ScanLib.SC_SUCCESS):
				break;
			case (ScanLib.SC_INVALID_DPI):
				MessageDialog.openError(viewComposite.getActiveShell(),
						"Error", String.format("%s\nReturned Error Code: %d\n",
								"Scanlib ScanPlate", "Invalid Dpi"));
				break;
			case (ScanLib.SC_INVALID_PLATE_NUM):
				MessageDialog.openError(viewComposite.getActiveShell(),
						"Error", String.format("%s\nReturned Error Code: %d\n",
								"Scanlib ScanPlaten", "Invalid Plate Number"));
				break;
			case (ScanLib.SC_CALIBRATOR_NO_REGIONS):
				MessageDialog.openError(viewComposite.getActiveShell(),
						"Error", String.format("%s\nReturned Error Code: %d\n",
								"Scanlib ScanPlate", "Calibrator Error"));
				break;
			case (ScanLib.SC_FAIL):
				MessageDialog.openError(viewComposite.getActiveShell(),
						"Error", String.format("%s\nReturned Error Code: %d\n",
								"Scanlib ScanPlate", "Failure"));
				break;
			case (ScanLib.SC_FILE_SAVE_ERROR):
				MessageDialog.openError(viewComposite.getActiveShell(),
						"Error", String.format("%s\nReturned Error Code: %d\n",
								"Scanlib ScanPlate", "Failed to save to file"));
				break;
		}
		return null;
	}
}
