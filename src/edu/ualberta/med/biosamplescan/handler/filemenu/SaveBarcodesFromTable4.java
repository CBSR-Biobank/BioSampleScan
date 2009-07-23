package edu.ualberta.med.biosamplescan.handler.filemenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import edu.ualberta.med.biosamplescan.singleton.ConfigSettings;

public class SaveBarcodesFromTable4 extends AbstractHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		SaveBarcodesFromTableX.execute(event, 4);
		return null;
	}

	public boolean isEnabled() {
		System.out.println(ConfigSettings.getInstance().getPlatemode());
		return (ConfigSettings.getInstance().getPlatemode() >= 4);
	}
}
