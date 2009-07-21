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

public class SaveAllBarcodes extends AbstractHandler implements IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Main main = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getMain();
		FileDialog dlg = new FileDialog(main.getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.cvs", "*.*" });
		dlg.setText(String.format("Save All Barcodes"));
		String saveLocation = dlg.open();
		if (saveLocation == null) {
			return null;
		}
		boolean[] tablesCheck = new boolean[ConfigSettings.PLATENUM];
		for (int i = 0; i < ConfigSettings.PLATENUM; i++) {
			tablesCheck[i] = true;
		}

		main.saveTables(saveLocation, tablesCheck);
		return null;
	}

}
