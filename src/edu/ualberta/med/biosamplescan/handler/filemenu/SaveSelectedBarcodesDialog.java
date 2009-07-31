package edu.ualberta.med.biosamplescan.handler.filemenu;

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
import edu.ualberta.med.biosamplescan.model.PlateSet;

public class SaveSelectedBarcodesDialog extends AbstractHandler implements
		IHandler
{
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ViewComposite viewComposite = ((PlateSetEditor) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart()).getViewComposite();
		PlateSet plateSet = ((PlateSetEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getPlateSet();
		boolean pass = false;
		for (int i = 0; i < ConfigSettings.PLATENUM; i++) {
			if (viewComposite.getPlateBtnSelection(i)) {
				pass = true;
				break;
			}
		}
		if (!pass) {
			MessageDialog.openError(viewComposite.getActiveShell(), "Error",
					"No Plates Selected");
			return null;
		}

		FileDialog dlg = new FileDialog(viewComposite.getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.csv", "*.*" });
		dlg.setText(String.format(
				"Save Barcodes for the Selected Plates, Append:%s",
				ConfigSettings.getInstance().getAppendSetting()));
		String saveLocation = dlg.open();
		if (saveLocation != null) {
			boolean[] tablesCheck = new boolean[ConfigSettings.PLATENUM];
			for (int i = 0; i < ConfigSettings.PLATENUM; i++) {
				tablesCheck[i] = viewComposite.getPlateBtnSelection(i);
			}
			plateSet.saveTables(saveLocation, tablesCheck, ConfigSettings
					.getInstance().getAppendSetting());
			ConfigSettings.getInstance().setLastSaveLocation(saveLocation);
		}
		return saveLocation;
	}

}
