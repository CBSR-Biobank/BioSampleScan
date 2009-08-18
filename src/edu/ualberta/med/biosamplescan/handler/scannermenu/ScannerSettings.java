
package edu.ualberta.med.biosamplescan.handler.scannermenu;

import java.util.ArrayList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.gui.ViewComposite;
import edu.ualberta.med.biosamplescan.jobs.CalibrateJob;

public class ScannerSettings extends AbstractHandler implements IHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
        ViewComposite viewComposite = ((PlateSetEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart()).getViewComposite();
        // ConfigDialog configDialog = new
        // ConfigDialog(viewComposite.getShell());
        // configDialog.open();
        // List<Integer> platesToCalibrate =
        // configDialog.getPlatesToCalibrate();
        // if (platesToCalibrate.size() > 0) {
        // CalibrateJob job = new CalibrateJob("Calibration",
        // platesToCalibrate);
        // job.schedule();
        // }
        CalibrateJob job = new CalibrateJob("Calibration",
            new ArrayList<Integer>());
        job.schedule();
        return null;
    }

}
