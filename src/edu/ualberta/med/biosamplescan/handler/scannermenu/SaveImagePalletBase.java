package edu.ualberta.med.biosamplescan.handler.scannermenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.widgets.PalletSetWidget;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scannerconfig.ScannerConfigPlugin;

public abstract class SaveImagePalletBase extends AbstractHandler implements
    IHandler {
    protected int palletId;

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        if (palletId >= BioSampleScanPlugin.getDefault().getPalletsMax()) {
            BioSampleScanPlugin.openError("Error",
                "Not configured for this pallet");
            return null;
        }

        PalletSetWidget widget = BioSampleScanPlugin.getDefault()
            .getPalletSetEditor().getPalletSetWidget();

        if (!ScannerConfigPlugin.getDefault().getPalletEnabled(palletId)) {
            // TODO apply this code to all applicable routines
            MessageDialog.openError(PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell(), "Error", String.format(
                "plate %d:\n%s", palletId, "Has no dimensions set"));
            return null;
        }

        FileDialog dlg = new FileDialog(widget.getShell(), SWT.SAVE);
        dlg.setFilterExtensions(new String[] { "*.bmp", "*.*" });
        dlg.setText(String.format("Scan Plate %d and Save to...", palletId));
        String saveLocation = dlg.open();
        if (saveLocation == null) {
            return null;
        }

        int scanlibReturn = ScanLib.getInstance().slScanPlate(
            ScannerConfigPlugin.getDefault().getDpi(), palletId, saveLocation);

        if (scanlibReturn != ScanLib.SC_SUCCESS) {
            MessageDialog.openError(PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell(), "Scanning Error",
                ScanLib.getErrMsg(scanlibReturn));
        }
        return null;
    }
}
