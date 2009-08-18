
package edu.ualberta.med.biosamplescan.handler.filemenu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.PalletSet;
import edu.ualberta.med.biosamplescan.widgets.AllPalletsWidget;

public class SaveBarcodesFromTableX {
    public static final Object execute(ExecutionEvent event, int platenum)
        throws ExecutionException {
        if (ConfigSettings.getInstance().getPalletCount() < platenum) {
            return null;
        }// TODO actually disable menu items

        AllPalletsWidget viewComposite = ((PlateSetEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart()).getPalletsWidget();
        PalletSet plateSet = BioSampleScanPlugin.getDefault().getPalletSet();
        FileDialog dlg = new FileDialog(viewComposite.getShell(), SWT.SAVE);
        dlg.setFilterExtensions(new String [] { "*.csv", "*.*" });
        dlg.setText(String.format("Save Barcodes For Plate %d", platenum));
        if (plateSet.getPalletTimestamp(platenum) != 0) {
            Date d = new Date();
            d.setTime(plateSet.getPalletTimestamp(platenum));
            dlg.setFileName(new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(d));
        }
        String saveLocation = dlg.open();

        if (saveLocation != null) {
            if (new File(saveLocation).exists()
                && !MessageDialog.openConfirm(
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                    "Save over existing file?",
                    "A file already exists at the selected location are you sure you want to save over it?")) {
                return null;
            }

            boolean [] tablesCheck = new boolean [ConfigSettings.PALLET_NUM];
            for (int i = 0; i < ConfigSettings.PALLET_NUM; i++) {
                tablesCheck[i] = false;
            }
            tablesCheck[platenum - 1] = true;

            plateSet.saveTables(saveLocation, tablesCheck, false);
        }
        return null;
    }

}
