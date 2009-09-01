package edu.ualberta.med.biosamplescan.handler.scannermenu;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.dialogs.CalibrateDialog;
import edu.ualberta.med.biosamplescan.dialogs.ConfigDialog;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;

public class ScannerConfiguration extends AbstractHandler implements IHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {

        BioSampleScanPlugin plugin = BioSampleScanPlugin.getDefault();

        if (plugin.getPalletSet().getPalletCount() > 0) {
            if (!BioSampleScanPlugin.openConfirm("Pallet Decode Information",
                "Erase decode information and proceed to configuration?")) {
                return null;
            }

            plugin.getPalletSetEditor().getPalletSetWidget().clearPallets();
        }

        ConfigDialog configDialog = new ConfigDialog(PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow().getShell());
        configDialog.open();

        // reset our model since pallet set may have changed
        plugin.createNewPelletSet();
        plugin.getPalletSetEditor().refresh();

        if (ConfigSettings.getInstance().getSimulateScanning())
            return null;

        BioSampleScanPlugin.getDefault().updateStatusBar("Configuring scanner");

        List<Integer> platesToCalibrate = configDialog.getPlatesToCalibrate();
        if (platesToCalibrate.size() > 0) {
            new CalibrateDialog(platesToCalibrate);
        }

        return null;
    }
}
