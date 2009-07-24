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
import edu.ualberta.med.biosamplescan.singleton.ConfigSettings;

public class PlateMode extends AbstractHandler implements IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ViewComposite viewComposite = ((PlateSetEditor) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart()).getViewComposite();
		/*InputDialog dlg = new InputDialog(viewComposite.getShell(),
				"Plate Mode",
				"Please enter the plate mode:\nNote: The range is (1,4)", "4",
				new IInputValidator() {
					public String isValid(String newText) {
						int len = newText.length();
						if (len < 0 || len > 1) return "(1 <= digit <= 4)";
						int val = 0;
						try {
							val = Integer.valueOf(newText);
						}
						catch (NumberFormatException e) {
						}
						if (val < 1 || val > 4) {
							return "(1 <= digit <= 4)";
						}
						return null;
					}
				});*/

		PlateModeDialog pmd = new PlateModeDialog(viewComposite
				.getActiveShell(), SWT.NONE);
		int plateMode = pmd.open();

		if (plateMode > 0 && plateMode <= ConfigSettings.PLATENUM) {
			ConfigSettings.getInstance()
					.setPlatemode(String.valueOf(plateMode));
			viewComposite.setPlateMode();
			//TODO disable some menu items (eg: save from plate #)
		}
		return null;
	}
}
