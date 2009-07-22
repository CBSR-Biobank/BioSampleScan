package edu.ualberta.med.biosamplescan.handler.scannermenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

public class SaveImagePlate1 extends AbstractHandler implements IHandler {

	// TODO bring up a dialog if all the settings for plate is 0,0,0,0;
	public Object execute(ExecutionEvent event) throws ExecutionException {
		SaveImagePlateX.execute(event, 1);
		return null;
	}

}
