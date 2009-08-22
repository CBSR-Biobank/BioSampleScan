
package edu.ualberta.med.biosamplescan;

import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.Option;
import jargs.gnu.CmdLineParser.OptionException;

import org.eclipse.core.runtime.Platform;
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
                final String err = parseCommandLine();
                if (err != null) {
                    stopApplication("Command Line Arguments", err);
                    return;
                }

                if (ConfigSettings.getInstance().getSimulateScanning()) return;

                String osname = System.getProperty("os.name");

                if (osname.startsWith("Windows")) {
                    final int result = ScanLib.getInstance().slIsTwainAvailable();
                    if (result != ScanLib.SC_SUCCESS) {
                        stopApplication("Driver Error",
                            ScanLib.getErrMsg(result));
                        return;
                    }
                }
            }
        });
    }

    private String parseCommandLine() {
        CmdLineParser parser = new CmdLineParser();
        Option outputOpt = parser.addStringOption('o', "output");
        Option palletsMaxOpt = parser.addIntegerOption('p', "palletsmax");

        try {
            parser.parse(Platform.getApplicationArgs());
        }
        catch (OptionException e) {
            return e.getMessage();
        }

        ConfigSettings c = ConfigSettings.getInstance();

        String filename = (String) parser.getOptionValue(outputOpt);
        if (filename != null) {
            if (filename.length() == 0) {
                return "Invalid save location";
            }
            c.setSaveFileName(filename);
        }

        Integer palletsMax = (Integer) parser.getOptionValue(palletsMaxOpt);
        if (palletsMax != null) {
            if ((palletsMax <= 0) || (palletsMax > ConfigSettings.PALLET_NUM)) {
                return "nvalid value. palletsmax should be between 1 and "
                    + ConfigSettings.PALLET_NUM;
            }
            c.setPalletsMax(palletsMax);
        }

        if ((filename != null) && (palletsMax != null) && (palletsMax != 1)) {
            return "palletsmax should be 1 when using --output option";
        }
        return null;
    }

    private void stopApplication(final String title, final String msg) {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                BioSampleScanPlugin.openError(title, msg);
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().close();
            }
        });
    }
}
