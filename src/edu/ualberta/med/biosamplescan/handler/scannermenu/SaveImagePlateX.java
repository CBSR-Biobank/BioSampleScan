package edu.ualberta.med.biosamplescan.handler.scannermenu;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.PalletSetView;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.widgets.PalletSetWidget;
import edu.ualberta.med.scanlib.ScanLib;

public class SaveImagePlateX {
    public static final Object execute(int palletId) {
        if (palletId >= ConfigSettings.getInstance().getPalletMax()) {
            BioSampleScanPlugin.openError("Error",
                "Not configured for this pallet");
            return null;
        }

        PalletSetWidget viewComposite = ((PalletSetView) PlatformUI
            .getWorkbench().getActiveWorkbenchWindow().getActivePage()
            .getActivePart()).getPalletSetWidget();
        ConfigSettings configSettings = ConfigSettings.getInstance();

        if (!configSettings.palletIsSet(palletId)) {
            // TODO apply this code to all applicable routines
            MessageDialog.openError(PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell(), "Error", String.format(
                "plate %d:\n%s", palletId, "Has no dimensions set"));
            return null;
        }

        FileDialog dlg = new FileDialog(viewComposite.getShell(), SWT.SAVE);
        dlg.setFilterExtensions(new String[] { "*.bmp", "*.*" });
        dlg.setText(String.format("Scan Plate %d and Save to...", palletId));
        String saveLocation = dlg.open();
        if (saveLocation == null) {
            return null;
        }

        int scanlibReturn = ScanLib.getInstance().slScanPlate(
            configSettings.getDpi(), palletId, saveLocation);

        if (scanlibReturn != ScanLib.SC_SUCCESS) {
            MessageDialog.openError(PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell(), "Scanning Error",
                ScanLib.getErrMsg(scanlibReturn));
        }
        return null;
    }
}
