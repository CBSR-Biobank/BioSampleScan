package edu.ualberta.med.biosamplescan.handler.filemenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

public class SaveBarcodesFromTable2 extends AbstractHandler implements IHandler
{
	public Object execute(ExecutionEvent event) throws ExecutionException {
		SaveBarcodesFromTableX.execute(event, 2);
		return null;
	}

}
