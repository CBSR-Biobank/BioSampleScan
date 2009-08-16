package edu.ualberta.med.biosamplescan.handler.filemenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.gui.ViewComposite;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.PalletSet;

public class NewScan extends AbstractHandler implements IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ViewComposite viewComposite = ((PlateSetEditor) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart()).getViewComposite();
		if (MessageDialog.openConfirm(viewComposite.getActiveShell(),
				"New Scan", "Do you want to clear all the tables?")) {
			viewComposite.clearTables();
			PalletSet plateSet = ((PlateSetEditor) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActivePart())
					.getPlateSet();
			for (int p = 0; p < ConfigSettings.PLATENUM; p++) {
				plateSet.initPlate(p + 1, 13, 8);
			}
		}

		return null;
	}
}
