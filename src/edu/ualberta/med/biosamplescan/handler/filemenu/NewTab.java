package edu.ualberta.med.biosamplescan.handler.filemenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.editors.PalletSetEditor;
import edu.ualberta.med.biosamplescan.editors.PalletSetInput;

public class NewTab extends AbstractHandler implements IHandler {
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {

        try {
            PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage()
                .openEditor(new PalletSetInput(), PalletSetEditor.ID, true);
        } catch (PartInitException e) {
            e.printStackTrace();
        }

        return null;
    }
}
