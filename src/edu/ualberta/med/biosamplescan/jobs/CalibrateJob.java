
package edu.ualberta.med.biosamplescan.jobs;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.progress.IProgressConstants;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;

public class CalibrateJob extends Job {

    List<Integer> platesToCalibrate;

    public CalibrateJob(String name, List<Integer> platesToCalibrate) {
        super(name);
        this.platesToCalibrate = platesToCalibrate;
        addJobChangeListener(new JobChangeAdapter() {
            @Override
            public void done(final IJobChangeEvent event) {
                Display.getDefault().asyncExec(new Runnable() {
                    public void run() {
                        try {
                            if (event.getResult().isOK()) {
                                if ((Boolean) event.getJob().getProperty(
                                    IProgressConstants.PROPERTY_IN_DIALOG)) return;
                                BioSampleScanPlugin.openMessage(
                                    "Successful Calibration",
                                    "Plates has been successfully setup");
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        setUser(true);
    }

    @Override
    protected IStatus run(IProgressMonitor monitor) {
        try {
            monitor.beginTask("Calibrating plates...", 10);
            // IProgressMonitor.UNKNOWN);

            for (int i = 0; i < 1000000; ++i) {
                System.out.println(i);
            }

            // ConfigSettings configSettings =
            // ConfigSettings.getInstance();
            //
            // int scanlibReturn =
            // ScanLib.getInstance().slCalibrateToPlate(
            // configSettings.getDpi(), plate);
            //
            // if (scanlibReturn != ScanLib.SC_SUCCESS) {
            // ScanLib.getInstance().slConfigPlateFrame(plate, 0, 0, 0,
            // 0);
            // }
            //
            // switch (scanlibReturn) {
            // case (ScanLib.SC_INVALID_PLATE_NUM):
            // BioSampleScanPlugin.openAsyncError("Calibration Error",
            // "Invalid Plate Number: " + scanlibReturn);
            // break;
            //
            // case (ScanLib.SC_INI_FILE_ERROR):
            // BioSampleScanPlugin.openAsyncError("Calibration Error",
            // "Plate dimensions not found inside scanlib.ini");
            // break;
            // case (ScanLib.SC_CALIBRATOR_ERROR):
            // BioSampleScanPlugin.openAsyncError("Calibration Error",
            // "Could not find 8 rows and 12 columns");
            // break;
            // case (ScanLib.SC_CALIBRATOR_NO_REGIONS):
            // BioSampleScanPlugin.openAsyncError("Calibration Error",
            // "menuConfigurationWidgetSelected, Calibratation");
            // break;
            // case (ScanLib.SC_FAIL):
            // BioSampleScanPlugin.openAsyncError("Calibration Error",
            // "Failed to scan");
            // break;
            // default:
            // }
        }
        catch (Exception e) {
            return Status.CANCEL_STATUS;
        }
        finally {
            monitor.done();
        }

        return Status.OK_STATUS;
    }

}
