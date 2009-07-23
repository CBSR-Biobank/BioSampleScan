package edu.ualberta.med.biosamplescan.handler.aboutmenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.gui.ViewComposite;

public class Credits extends AbstractHandler implements IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ViewComposite viewComposite = ((PlateSetEditor) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart()).getViewComposite();
		MessageDialog.openInformation(viewComposite.getActiveShell(),
				"Credits", "Interface:\n\tThomas Polasek\n"
						+ "\nScanlib/Design:\n\tNelson Loyola\n"
						+ "\nVersion:\t1.00\n");//TODO set version

		return null;
	}

}
