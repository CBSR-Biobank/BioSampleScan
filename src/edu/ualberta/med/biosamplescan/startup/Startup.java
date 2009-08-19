
package edu.ualberta.med.biosamplescan.startup;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.editors.PlateSetInput;
import edu.ualberta.med.scanlib.ScanLib;

public class Startup implements IStartup {

    @Override
    public void earlyStartup() {
        final IWorkbench workbench = PlatformUI.getWorkbench();
        workbench.getDisplay().asyncExec(new Runnable() {
            public void run() {
                String osname = System.getProperty("os.name");
                if (osname.startsWith("Windows")) {
                    final int result = ScanLib.getInstance().slIsTwainAvailable();
                    if (result != ScanLib.SC_SUCCESS) {
                        Display.getDefault().asyncExec(new Runnable() {
                            public void run() {
                                BioSampleScanPlugin.openError(
                                    "TWAIN Driver Error",
                                    ScanLib.getErrMsg(result));
                                PlatformUI.getWorkbench().getActiveWorkbenchWindow().close();
                            }
                        });
                        return;
                    }
                }
                IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();

                try {
                    window.getActivePage().openEditor(new PlateSetInput(),
                        PlateSetEditor.ID, true);
                }
                catch (PartInitException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (window != null) {
                    // do something
                }
            }
        });

    }
}
