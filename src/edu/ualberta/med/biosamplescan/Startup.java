package edu.ualberta.med.biosamplescan;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.editors.PalletSetEditor;
import edu.ualberta.med.biosamplescan.editors.PalletSetInput;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.scanlib.ScanLib;

public class Startup implements IStartup {

    @Override
    public void earlyStartup() {
        final IWorkbench workbench = PlatformUI.getWorkbench();
        workbench.getDisplay().asyncExec(new Runnable() {
            public void run() {
                if (ConfigSettings.getInstance().getSimulateScanning())
                    return;

                String osname = System.getProperty("os.name");

                if (osname.startsWith("Windows")) {
                    final int result = ScanLib.getInstance()
                        .slIsTwainAvailable();
                    if (result != ScanLib.SC_SUCCESS) {
                        BioSampleScanPlugin.stopApplication("Driver Error",
                            ScanLib.getErrMsg(result));
                        return;
                    }
                }

                try {
                    workbench.getActiveWorkbenchWindow().getActivePage()
                        .openEditor(new PalletSetInput(), PalletSetEditor.ID,
                            true);
                } catch (PartInitException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}
