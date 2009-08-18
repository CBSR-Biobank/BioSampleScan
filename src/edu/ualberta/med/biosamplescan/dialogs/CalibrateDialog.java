
package edu.ualberta.med.biosamplescan.dialogs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
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
                        monitor.beginTask("Calibrating plates...",
                            IProgressMonitor.UNKNOWN);
                        ConfigSettings configSettings = ConfigSettings.getInstance();

                        for (Integer plate : platesToCalibrate) {
                            int p = plate;
                            int scanlibReturn = ScanLib.getInstance().slCalibrateToPlate(
                                configSettings.getDpi(), p);

                            if (scanlibReturn != ScanLib.SC_SUCCESS) {
                                ScanLib.getInstance().slConfigPlateFrame(p, 0,
                                    0, 0, 0);
                            }

                            switch (scanlibReturn) {
                                case (ScanLib.SC_INVALID_PLATE_NUM):
                                    BioSampleScanPlugin.openAsyncError(
                                        "Calibration Error",
                                        "Invalid Plate Number: "
                                            + scanlibReturn);
                                    break;

                                case (ScanLib.SC_INI_FILE_ERROR):
                                    BioSampleScanPlugin.openAsyncError(
                                        "Calibration Error",
                                        "Plate dimensions not found inside scanlib.ini");
                                    break;
                                case (ScanLib.SC_CALIBRATOR_ERROR):
                                    BioSampleScanPlugin.openAsyncError(
                                        "Calibration Error",
                                        "Could not find 8 rows and 12 columns");
                                    break;
                                case (ScanLib.SC_CALIBRATOR_NO_REGIONS):
                                    BioSampleScanPlugin.openAsyncError(
                                        "Calibration Error",
                                        "menuConfigurationWidgetSelected, Calibratation");
                                    break;
                                case (ScanLib.SC_FAIL):
                                    BioSampleScanPlugin.openAsyncError(
                                        "Calibration Error", "Failed to scan");
                                    break;
                                default:
                            }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        monitor.done();
                    }

                }
            });
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
