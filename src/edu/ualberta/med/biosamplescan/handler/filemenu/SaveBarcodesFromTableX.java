package edu.ualberta.med.biosamplescan.handler.filemenu;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.gui.ViewComposite;
import edu.ualberta.med.biosamplescan.model.PlateSet;
import edu.ualberta.med.biosamplescan.singleton.ConfigSettings;

public class SaveBarcodesFromTableX {
	public static final Object execute(ExecutionEvent event, int platenum)
			throws ExecutionException {
		if (ConfigSettings.getInstance().getPlatemode() < platenum) {
			return null;
		}

		ViewComposite viewComposite = ((PlateSetEditor) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart()).getViewComposite();
		PlateSet plateSet = ((PlateSetEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getPlateSet();
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

			plateSet.saveTables(saveLocation, tablesCheck, false);
		}
		return null;
	}

}
