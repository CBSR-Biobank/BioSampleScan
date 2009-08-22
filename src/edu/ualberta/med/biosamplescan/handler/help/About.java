
package edu.ualberta.med.biosamplescan.handler.help;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.SimpleSynth;
import edu.ualberta.med.biosamplescan.dialogs.CreditsDialog;

public class About extends AbstractHandler implements IHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
        CreditsDialog cd = new CreditsDialog(
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
            SWT.NONE);

        new SimpleSynth();
        cd.open();
        return null;
    }

}
