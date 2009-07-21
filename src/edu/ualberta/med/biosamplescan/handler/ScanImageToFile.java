package edu.ualberta.med.biosamplescan.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.View;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.Main;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scanlib.ScanLibFactory;

public class ScanImageToFile extends AbstractHandler implements IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Main main = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getMain();
		ConfigSettings configSettings = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getConfigSettings();

		FileDialog dlg = new FileDialog(main.getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.bmp", "*.*" });
		dlg.setText("Scan and Save Image");
		String saveLocation = dlg.open();
		if (saveLocation == null) {
			return null;
		}
		int scanlibReturn = ScanLibFactory.getScanLib().slScanImage(
				configSettings.getDpi(), 0, 0, 0, 0, saveLocation);
		switch (scanlibReturn) {
		case (ScanLib.SC_SUCCESS):
			break;
		case (ScanLib.SC_INVALID_DPI):
			main.errorMsg("Scanlib ScanImage", scanlibReturn);
			break;
		case (ScanLib.SC_INVALID_PLATE_NUM):
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
