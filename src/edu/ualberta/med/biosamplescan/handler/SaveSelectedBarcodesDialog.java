package edu.ualberta.med.biosamplescan.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.View;
import edu.ualberta.med.biosamplescan.gui.Main;

public class SaveSelectedBarcodesDialog extends AbstractHandler implements
		IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Main main = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getMain();
		boolean pass = false;
		for (int i = 0; i < main.MAXPLATES; i++) {
			if (main.plateBtn[i].getSelection()) {
				pass = true;
				break;
			}
		}
		if (!pass) {
			main.errorMsg("No Plates Selected", 0);
			return null;
		}

		FileDialog dlg = new FileDialog(main.getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.cvs", "*.*" });
		dlg.setText(String.format("Save Barcodes for the Selected Plates"));
		String saveLocation = dlg.open();
		if (saveLocation != null) {
			boolean[] tablesCheck = new boolean[main.MAXPLATES];
			for (int i = 0; i < main.MAXPLATES; i++) {
				tablesCheck[i] = main.plateBtn[i].getSelection();
			}
			main.saveTables(saveLocation, tablesCheck);
			main.lastSaveSelectLocation = saveLocation;
		}
		return null;
	}

}
