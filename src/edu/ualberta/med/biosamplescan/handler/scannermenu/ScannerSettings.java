
package edu.ualberta.med.biosamplescan.handler.scannermenu;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.dialogs.CalibrateDialog;
import edu.ualberta.med.biosamplescan.dialogs.ConfigDialog;

public class ScannerSettings extends AbstractHandler implements IHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
        ConfigDialog configDialog = new ConfigDialog(
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
        configDialog.open();
        List<Integer> platesToCalibrate = configDialog.getPlatesToCalibrate();
        if (platesToCalibrate.size() > 0) {
            new CalibrateDialog(platesToCalibrate);
        }
        return null;
    }
}
