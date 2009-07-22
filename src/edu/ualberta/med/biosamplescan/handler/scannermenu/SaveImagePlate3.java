package edu.ualberta.med.biosamplescan.handler.scannermenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

public class SaveImagePlate3 extends AbstractHandler implements IHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		SaveImagePlateX.execute(event, 3);
		return null;
	}

}
