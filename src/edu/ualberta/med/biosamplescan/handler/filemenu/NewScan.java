
package edu.ualberta.med.biosamplescan.handler.filemenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.PalletSetView;
import edu.ualberta.med.biosamplescan.model.PalletSet;
import edu.ualberta.med.biosamplescan.widgets.PalletSetWidget;

public class NewScan extends AbstractHandler implements IHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
        PalletSetWidget viewComposite = ((PalletSetView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart()).getPalletSetWidget();
        if (MessageDialog.openConfirm(
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
            "New Scan", "Do you want to clear all the tables?")) {
            viewComposite.clearPallets();
            PalletSet palletSet = new PalletSet();
            BioSampleScanPlugin.getDefault().setPalletSet(palletSet);
        }

        return null;
    }
}
