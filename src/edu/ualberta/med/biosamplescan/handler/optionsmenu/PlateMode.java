package edu.ualberta.med.biosamplescan.handler.optionsmenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.View;
import edu.ualberta.med.biosamplescan.gui.ViewComposite;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;

public class PlateMode extends AbstractHandler implements IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ViewComposite viewComposite = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getMain();
		ConfigSettings configSettings = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getConfigSettings();
		InputDialog dlg = new InputDialog(viewComposite.getShell(),
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
				});

		dlg.open();
		String ret = dlg.getValue();
		if (ret == null || ret.isEmpty()) {
			return null;
		}
		//setPlatemode
		configSettings.setPlatemode(Integer.valueOf(ret));
		viewComposite.setPlateMode();

		//TODO disable some menu items (eg: save from plate #)
		return null;
	}
}
