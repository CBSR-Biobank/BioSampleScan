package edu.ualberta.med.biosamplescan.handler.filemenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.View;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;

public class SaveBarcodesFromTable4 extends AbstractHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		SaveBarcodesFromTableX.execute(event, 4);
		return null;
	}

	public boolean isEnabled() {
		ConfigSettings configSettings = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getConfigSettings();
		System.out.println((configSettings.getPlatemode() >= 4));
		return (configSettings.getPlatemode() >= 4);
	}

	public void setEnabled(Object o) {
		isEnabled();
	}
}
