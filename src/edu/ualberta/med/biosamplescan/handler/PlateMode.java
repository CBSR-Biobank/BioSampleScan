package edu.ualberta.med.biosamplescan.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.View;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.Main;

public class PlateMode extends AbstractHandler implements IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Main main = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getMain();
		InputDialog dlg = new InputDialog(main.getShell(), "Plate Mode",
				"Please enter the plate mode:\nNote: The range is (1,4)", "4",
				new IInputValidator() {
					public String isValid(String newText) {
						int len = newText.length();
						if (len < 0 || len > 1)
							return "(1 <= digit <= 4)";
						int val = 0;
						try {
							val = Integer.valueOf(newText);
						} catch (NumberFormatException e) {
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
		boolean set = false;
		for (int table = 0; table < ConfigSettings.PLATENUM; table++) {
			set = (table < Integer.valueOf(ret));
			main.tables[table].setEnabled(set);
			main.plateBtn[table].setEnabled(set);
			// main.menuSaveBarcodes[table].setEnabled(set);
			// main.menuPlates[table].setEnabled(set); //TODO now in a different
			// menu
			main.plateBtn[table].setEnabled(set);
			if (!set) {
				main.plateBtn[table].setSelection(false);
				for (int r = 0; r < 8; r++) {
					for (int c = 0; c < 12; c++) {
						main.tableItems[table][r].setText(c + 1, "");
					}
				}
			}
		}
		return null;
	}

}
