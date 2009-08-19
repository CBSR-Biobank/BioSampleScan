
package edu.ualberta.med.biosamplescan.handler.scannermenu;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.PlateSetView;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.widgets.PalletSetWidget;
import edu.ualberta.med.scanlib.ScanLib;

public class SaveImagePlateX {
    public static final Object execute(ExecutionEvent event, int platenum)
        throws ExecutionException {
        PalletSetWidget viewComposite = ((PlateSetView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart()).getPalletsWidget();
        ConfigSettings configSettings = ConfigSettings.getInstance();

        if (configSettings.getPalletCount() < platenum) {
            return null;
        } // TODO actually disable menu items

        if (!configSettings.palletIsSet(platenum)) { // TODO apply this code to
            // all applicable routines
            MessageDialog.openError(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                "Error", String.format("plate %d:\n%s", platenum,
                    "Has no dimensions set"));
            return null;
        }

        FileDialog dlg = new FileDialog(viewComposite.getShell(), SWT.SAVE);
        dlg.setFilterExtensions(new String [] { "*.bmp", "*.*" });
        dlg.setText(String.format("Scan Plate %d and Save to...", platenum));
        String saveLocation = dlg.open();
        if (saveLocation == null) {
            return null;
        }

        int scanlibReturn = ScanLib.getInstance().slScanPlate(
            configSettings.getDpi(), platenum, saveLocation);

        if (scanlibReturn != ScanLib.SC_SUCCESS) {
            MessageDialog.openError(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                "Scanning Error", ScanLib.getErrMsg(scanlibReturn));
        }
        return null;
    }
}
