
package edu.ualberta.med.biosamplescan.handler.aboutmenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.gui.CreditsDialog;
import edu.ualberta.med.biosamplescan.gui.SimpleSynth;
import edu.ualberta.med.biosamplescan.gui.ViewComposite;

public class Credits extends AbstractHandler implements IHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
        ViewComposite viewComposite = ((PlateSetEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart()).getViewComposite();
        CreditsDialog cd = new CreditsDialog(
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
            SWT.NONE);

        new SimpleSynth();
        cd.open();
        return null;
    }

}
