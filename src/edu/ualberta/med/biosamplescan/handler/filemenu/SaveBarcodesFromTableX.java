package edu.ualberta.med.biosamplescan.handler.filemenu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.gui.ViewComposite;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.PlateSet;

public class SaveBarcodesFromTableX {
	public static final Object execute(ExecutionEvent event, int platenum)
			throws ExecutionException {
		if (ConfigSettings.getInstance().getPlatemode() < platenum) {
			return null;
		}//TODO actually disable menu items

		ViewComposite viewComposite = ((PlateSetEditor) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart()).getViewComposite();
		PlateSet plateSet = ((PlateSetEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getPlateSet();
		FileDialog dlg = new FileDialog(viewComposite.getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.csv", "*.*" });
		dlg.setText(String.format("Save Barcodes For Plate %d", platenum));
		if (plateSet.getPlateTimestamp(platenum) != 0) {
			Date d = new Date();
			d.setTime(plateSet.getPlateTimestamp(platenum));
			dlg.setFileName(new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss")
					.format(d));
		}
		String saveLocation = dlg.open();

		if (saveLocation != null) {
			if (new File(saveLocation).exists()
					&& !MessageDialog
							.openConfirm(
									viewComposite.getActiveShell(),
									"Save over existing file?",
									"A file already exists at the selected location are you sure you want to save over it?")) {
				return null;
			}

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
