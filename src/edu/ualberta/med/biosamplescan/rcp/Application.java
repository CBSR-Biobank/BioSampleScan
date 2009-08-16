
package edu.ualberta.med.biosamplescan.rcp;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class Application implements IApplication { // controls program execution
    public static final String PLUGIN_ID = "biosamplescan2";

    public Object start(IApplicationContext context) throws Exception {
        Display display = PlatformUI.createDisplay();
        try {
            int returnCode = PlatformUI.createAndRunWorkbench(display,
                new ApplicationWorkbenchAdvisor());
            if (returnCode == PlatformUI.RETURN_RESTART) return IApplication.EXIT_RESTART;
            else
                return IApplication.EXIT_OK;
        }
        finally {
            display.dispose();
        }

    }

    public void stop() {
        final IWorkbench workbench = PlatformUI.getWorkbench();
        if (workbench == null) return;
        final Display display = workbench.getDisplay();
        display.syncExec(new Runnable() {
            public void run() {
                if (!display.isDisposed()) workbench.close();
            }
        });
    }
}
