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

public class SaveSelectedBarcodes extends AbstractHandler implements IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Main main = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getMain();
		if (main.lastSaveSelectLocation == null
				|| main.lastSaveSelectLocation.isEmpty()) {
			FileDialog dlg = new FileDialog(main.getShell(), SWT.SAVE);
			dlg.setFilterExtensions(new String[] { "*.cvs", "*.*" });
			dlg.setText(String.format("Save Barcodes for the Selected Plates"));
			String saveLocation = dlg.open();
			if (saveLocation != null) {
				main.lastSaveSelectLocation = saveLocation;
			} else {
				return null;
			}

		}
		boolean[] tablesCheck = new boolean[main.MAXPLATES];
		for (int i = 0; i < main.MAXPLATES; i++) {
			tablesCheck[i] = main.plateBtn[i].getSelection();
		}
		main.saveTables(main.lastSaveSelectLocation, tablesCheck);
		return null;
	}

}
