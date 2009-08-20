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
import edu.ualberta.med.biosamplescan.model.PalletSet;

public class ScannerSettings extends AbstractHandler implements IHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {

        if (BioSampleScanPlugin.getDefault().getPalletSet().getPalletCount() > 0) {
            if (!BioSampleScanPlugin.openConfirm("Pallet Decode Information",
                "Erase decode information and proceed to configuration?")) {
                return null;
            }

            BioSampleScanPlugin plugin = BioSampleScanPlugin.getDefault();
            plugin.setPalletSet(new PalletSet());
            plugin.getPalletSetView().getPalletSetWidget().clearPallets();
        }

        ConfigDialog configDialog =
            new ConfigDialog(PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell());
        configDialog.open();

        String osname = System.getProperty("os.name");
        if (!osname.startsWith("Windows")) {
            return null;
        }

        List<Integer> platesToCalibrate = configDialog.getPlatesToCalibrate();
        if (platesToCalibrate.size() > 0) {
            new CalibrateDialog(platesToCalibrate);
        }
        return null;
    }
}
