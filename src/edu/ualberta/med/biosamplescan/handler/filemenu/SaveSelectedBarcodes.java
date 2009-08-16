package edu.ualberta.med.biosamplescan.handler.filemenu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.gui.ViewComposite;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.PalletSet;

public class SaveSelectedBarcodes extends AbstractHandler implements IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ViewComposite viewComposite = ((PlateSetEditor) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart()).getViewComposite();
		PalletSet plateSet = ((PlateSetEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getPlateSet();
		if (ConfigSettings.getInstance().getLastSaveLocation() == null
				|| ConfigSettings.getInstance().getLastSaveLocation().isEmpty()) {
			FileDialog dlg = new FileDialog(viewComposite.getShell(), SWT.SAVE);
			dlg.setFilterExtensions(new String[] { "*.csv", "*.*" });
			dlg.setText(String.format(
					"Save Barcodes for the Selected Plates, Append:%s",
					ConfigSettings.getInstance().getAppendSetting()));

			long largestSaveTime = 0;
			for (int i = 0; i < ConfigSettings.PLATENUM; i++) {
				if (plateSet.getPlateTimestamp(i + 1) > largestSaveTime
						&& viewComposite.getPlateBtnSelection(i)) {
					largestSaveTime = plateSet.getPlateTimestamp(i + 1);
				}
			}
			if (largestSaveTime != 0) {
				Date d = new Date();
				d.setTime(largestSaveTime);
				dlg.setFileName(new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss")
						.format(d));
			}

			String saveLocation = dlg.open();
			if (saveLocation != null) {
				ConfigSettings.getInstance().setLastSaveLocation(saveLocation);
			}
			else {
				return null;
			}

		}
		if (ConfigSettings.getInstance().getLastSaveLocation() == null) {//TODO remove later
			return null;
		}
		if (new File(ConfigSettings.getInstance().getLastSaveLocation())
				.exists()
				&& !MessageDialog
						.openConfirm(
								viewComposite.getActiveShell(),
								"Save over existing file?",
								"A file already exists at the selected location are you sure you want to save over it?")) {
			return null;
		}

		boolean[] tablesCheck = new boolean[ConfigSettings.PLATENUM];
		for (int i = 0; i < ConfigSettings.PLATENUM; i++) {
			tablesCheck[i] = viewComposite.getPlateBtnSelection(i);
		}
		plateSet.saveTables(ConfigSettings.getInstance().getLastSaveLocation(),
				tablesCheck, ConfigSettings.getInstance().getAppendSetting());
		return null;
	}

}
