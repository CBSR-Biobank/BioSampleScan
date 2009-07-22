package edu.ualberta.med.biosamplescan.handler.filemenu;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.View;
import edu.ualberta.med.biosamplescan.gui.ViewComposite;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;

public class SaveBarcodesFromTableX {
	public static final Object execute(ExecutionEvent event, int platenum)
			throws ExecutionException {
		ViewComposite viewComposite = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getMain();
		FileDialog dlg = new FileDialog(viewComposite.getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.csv", "*.*" });
		dlg.setText(String.format("Save Barcodes For Plate %d", platenum));
		String saveLocation = dlg.open();

		if (saveLocation != null) {
			boolean[] tablesCheck = new boolean[ConfigSettings.PLATENUM];
			for (int i = 0; i < ConfigSettings.PLATENUM; i++) {
				tablesCheck[i] = false;
			}
			tablesCheck[platenum - 1] = true;

			viewComposite.saveTables(saveLocation, tablesCheck);
		}
		return null;
	}

}
