package edu.ualberta.med.biosamplescan.dialogs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.scanlib.ScanLib;

public class CalibrateDialog extends ProgressMonitorDialog {

    public CalibrateDialog(final List<Integer> platesToCalibrate) {
        super(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
        try {
            run(true, true, new IRunnableWithProgress() {
                public void run(IProgressMonitor monitor) {
                    try {
                        monitor.beginTask("Configuring pallet position...",
                            IProgressMonitor.UNKNOWN);
                        ConfigSettings configSettings = ConfigSettings
                            .getInstance();

                        for (Integer plate : platesToCalibrate) {
                            int p = plate;
                            int scanlibReturn = ScanLib.getInstance()
                                .slCalibrateToPlate(configSettings.getDpi(), p);

                            if (scanlibReturn != ScanLib.SC_SUCCESS) {
                                BioSampleScanPlugin.openAsyncError(
                                    "Calibration Error", ScanLib
                                        .getErrMsg(scanlibReturn));
                                ScanLib.getInstance().slConfigPlateFrame(p, 0,
                                    0, 0, 0);
                            }
                        }

                        Display.getDefault().asyncExec(new Runnable() {
                            public void run() {
                                BioSampleScanPlugin.getDefault()
                                    .updateStatusBar("Scanner configured");
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        monitor.done();
                    }

                }
            });
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
