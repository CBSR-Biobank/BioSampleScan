
package edu.ualberta.med.biosamplescan.handler.optionsmenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.gui.ViewComposite;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;

public class AppendSetting extends AbstractHandler implements IHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
        ViewComposite viewComposite = ((PlateSetEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart()).getViewComposite();

        if (ConfigSettings.getInstance().getAppendSetting() == false) {
            if (MessageDialog.openConfirm(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                "Append to File",
                "Do you want to ENABLE appending when you save through 'Save Selected Barcodes'?")) {
                ConfigSettings.getInstance().setAppendSetting("TRUE");
            }
        }
        else {
            if (MessageDialog.openConfirm(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                "Append to File",
                "Do you want to DISABLE appending when you save through 'Save Selected Barcodes'?")) {
                ConfigSettings.getInstance().setAppendSetting("FALSE");
            }
        }

        return null;
    }

}
