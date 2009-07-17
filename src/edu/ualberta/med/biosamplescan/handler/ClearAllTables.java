package edu.ualberta.med.biosamplescan.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.View;
import edu.ualberta.med.biosamplescan.gui.Main;

public class ClearAllTables extends AbstractHandler implements IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Main main = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getMain();
		if (main.confirmMsg("Clear Table(s)",
				"Do you want to clear all the tables?")) {
			for (int p = 0; p < main.MAXPLATES; p++) {
				for (int r = 0; r < 8; r++) {
					for (int c = 0; c < 12; c++) {
						main.tableItems[p][r].setText(c + 1, "");
					}
				}
			}
		}
		return null;
	}

}
