package edu.ualberta.med.biosamplescan.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.View;
import edu.ualberta.med.biosamplescan.model.Main;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scanlib.ScanLibFactory;

public class SaveImagePlate4 extends AbstractHandler implements IHandler {

	// TODO bring up a dialog if all the settings for plate is 0,0,0,0;
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Main main = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getMain();

		int platenum = 4;
		FileDialog dlg = new FileDialog(main.getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.bmp", "*.*" });
		dlg.setText(String.format("Scan Plate %d and Save to...", platenum));
		String saveLocation = dlg.open();
		if (saveLocation == null) {
			return null;
		}
		int scanlibReturn = ScanLibFactory.getScanLib().slScanPlate(
				main.configDialog.dpi, platenum, saveLocation);
		System.out.println(scanlibReturn);
		switch (scanlibReturn) {
		case (ScanLib.SC_SUCCESS):
			break;
		case (ScanLib.SC_INVALID_DPI):
			main.errorMsg("Scanlib ScanImage", scanlibReturn);
			break;
		case (ScanLib.SC_INVALID_PLATE_NUM):
			main.errorMsg("Scanlib ScanImage", scanlibReturn);
			break;
		case (ScanLib.SC_CALIBRATOR_NO_REGIONS):
			main.errorMsg("Scanlib ScanImage", scanlibReturn);
			break;
		case (ScanLib.SC_FAIL):
			main.errorMsg("Scanlib ScanImage", scanlibReturn);
			break;
		case (ScanLib.SC_FILE_SAVE_ERROR):
			main.errorMsg("Scanlib ScanImage", scanlibReturn);
			break;
		}
		return null;
	}

}
