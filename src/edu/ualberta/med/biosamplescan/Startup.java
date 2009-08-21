package edu.ualberta.med.biosamplescan;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.scanlib.ScanLib;

public class Startup implements IStartup {

    @Override
    public void earlyStartup() {
        final IWorkbench workbench = PlatformUI.getWorkbench();
        workbench.getDisplay().asyncExec(new Runnable() {
            public void run() {
                String osname = System.getProperty("os.name");

                if (ConfigSettings.getInstance().getSimulateScanning())
                    return;

                if (osname.startsWith("Windows")) {
                    final int result =
                        ScanLib.getInstance().slIsTwainAvailable();
                    if (result != ScanLib.SC_SUCCESS) {
                        Display.getDefault().asyncExec(new Runnable() {
                            public void run() {
                                BioSampleScanPlugin.openError(
                                    "TWAIN Driver Error", ScanLib
                                        .getErrMsg(result));
                                PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow().close();
                            }
                        });
                        return;
                    }
                }
            }
        });

    }
}
