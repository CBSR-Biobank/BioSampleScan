package edu.ualberta.med.biosamplescan.handler.scannermenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scanlib.ScanLibFactory;

public class SelectSource extends AbstractHandler implements IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		int scanlibReturn = ScanLibFactory.getScanLib()
				.slSelectSourceAsDefault();
		switch (scanlibReturn) {
		case (ScanLib.SC_SUCCESS):
			break;
		case (ScanLib.SC_INVALID_VALUE): // user canceled dialog box
			break;
		}
		return null;
	}

}
