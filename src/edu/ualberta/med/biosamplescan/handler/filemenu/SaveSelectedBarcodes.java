package edu.ualberta.med.biosamplescan.handler.filemenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.View;
import edu.ualberta.med.biosamplescan.gui.ViewComposite;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.PlateSet;

public class SaveSelectedBarcodes extends AbstractHandler implements IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ViewComposite viewComposite = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getMain();
		PlateSet plateSet = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getPlateSet();
		if (viewComposite.getLastSaveSelectLocation() == null
				|| viewComposite.getLastSaveSelectLocation().isEmpty()) {
			FileDialog dlg = new FileDialog(viewComposite.getShell(), SWT.SAVE);
			dlg.setFilterExtensions(new String[] { "*.csv", "*.*" });
			dlg.setText(String.format("Save Barcodes for the Selected Plates"));
			String saveLocation = dlg.open();
			if (saveLocation != null) {
				viewComposite.setLastSaveSelectLocation(saveLocation);
			}
			else {
				return null;
			}

		}
		boolean[] tablesCheck = new boolean[ConfigSettings.PLATENUM];
		for (int i = 0; i < ConfigSettings.PLATENUM; i++) {
			tablesCheck[i] = viewComposite.getPlateBtnSelection(i);
		}
		plateSet.saveTables(viewComposite.getLastSaveSelectLocation(),
				tablesCheck);
		return null;
	}

}
