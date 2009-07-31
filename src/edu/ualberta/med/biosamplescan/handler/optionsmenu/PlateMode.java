package edu.ualberta.med.biosamplescan.handler.optionsmenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.gui.PlateModeDialog;
import edu.ualberta.med.biosamplescan.gui.ViewComposite;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;

public class PlateMode extends AbstractHandler implements IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ViewComposite viewComposite = ((PlateSetEditor) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart()).getViewComposite();
		PlateModeDialog pmd = new PlateModeDialog(viewComposite
				.getActiveShell(), SWT.NONE);
		int plateMode = pmd.open();

		if (plateMode > 0 && plateMode <= ConfigSettings.PLATENUM) {
			ConfigSettings.getInstance()
					.setPlatemode(String.valueOf(plateMode));
			viewComposite.setPlateMode();
		}
		return null;
	}
}
