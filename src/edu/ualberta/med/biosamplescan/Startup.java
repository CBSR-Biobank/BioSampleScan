package edu.ualberta.med.biosamplescan;

import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.Option;
import jargs.gnu.CmdLineParser.OptionException;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.State;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.services.ISourceProviderService;

import edu.ualberta.med.biosamplescan.editors.PalletSetEditor;
import edu.ualberta.med.biosamplescan.editors.PalletSetInput;
import edu.ualberta.med.biosamplescan.sourceproviders.DebugState;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scannerconfig.ScannerConfigPlugin;

public class Startup implements IStartup {

    @Override
    public void earlyStartup() {
        final IWorkbench workbench = PlatformUI.getWorkbench();
        workbench.getDisplay().asyncExec(new Runnable() {
            public void run() {
                if (BioSampleScanPlugin.getDefault().getSimulateScanning())
                    return;

                String osname = System.getProperty("os.name");

                if (osname.startsWith("Windows")) {
                    final int result = ScanLib.getInstance()
                        .slIsTwainAvailable();
                    if (result != ScanLib.SC_SUCCESS) {
                        stopApplication("Driver Error", ScanLib
                            .getErrMsg(result));
                        return;
                    }
                }

                final String err = parseCommandLine();
                if (err != null) {
                    stopApplication("Command Line Arguments", err);
                    return;
                }

                ScannerConfigPlugin.getDefault().initialize();

                IWorkbenchWindow window = PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow();
                ISourceProviderService service = (ISourceProviderService) window
                    .getService(ISourceProviderService.class);

                DebugState debugStateSourceProvider = (DebugState) service
                    .getSourceProvider(DebugState.SESSION_STATE);
                debugStateSourceProvider.setState(BioSampleScanPlugin
                    .getDefault().isDebugging());

                // reads the persisted state for the menu contribution
                ICommandService cmdService = (ICommandService) PlatformUI
                    .getWorkbench().getService(ICommandService.class);
                Command command = cmdService
                    .getCommand("edu.ualberta.med.biosamplescan.menu.debug.simulateScanning");
                State state = command
                    .getState("org.eclipse.ui.commands.toggleState");
                BioSampleScanPlugin.getDefault().setSimulateScanning(
                    (Boolean) state.getValue());

                try {
                    workbench.getActiveWorkbenchWindow().getActivePage()
                        .openEditor(new PalletSetInput(), PalletSetEditor.ID,
                            true);

                    String msg = new String();
                    if (ScannerConfigPlugin.getDefault().getPalletCount() == 0) {
                        msg = "Please configure scanner.";
                    } else {
                        msg = "Configuration loaded.";
                    }
                    BioSampleScanPlugin.getDefault().updateStatusBar(msg);
                } catch (PartInitException e) {
                    e.printStackTrace();
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
        } catch (OptionException e) {
            return e.getMessage();
        }

        String filename = (String) parser.getOptionValue(outputOpt);
        if (filename != null) {
            if (filename.length() == 0) {
                return "Invalid save location";
            }
            BioSampleScanPlugin.getDefault().setSaveFileName(filename);
        }

        Integer palletsMax = (Integer) parser.getOptionValue(palletsMaxOpt);
        if (palletsMax != null) {
            if ((palletsMax <= 0)
                || (palletsMax > BioSampleScanPlugin.getDefault()
                    .getPalletsMax())) {
                return "invalid value. palletsmax should be between 1 and "
                    + ScannerConfigPlugin.getPalletsMax();
            }
            BioSampleScanPlugin.getDefault().setPalletsMax(palletsMax);
        }

        if ((filename != null) && (palletsMax != null) && (palletsMax != 1)) {
            return "palletsmax should be 1 when using --output option";
        }
        return null;
    }

    public static void stopApplication(final String title, final String msg) {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                BioSampleScanPlugin.openError(title, msg);
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().close();
            }
        });
    }
}
