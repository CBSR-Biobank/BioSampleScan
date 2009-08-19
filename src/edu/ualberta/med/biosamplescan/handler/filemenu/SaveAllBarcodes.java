
package edu.ualberta.med.biosamplescan.handler.filemenu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.PalletSet;
import edu.ualberta.med.biosamplescan.widgets.PalletSetWidget;

public class SaveAllBarcodes extends AbstractHandler implements IHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
        PalletSetWidget viewComposite = ((PlateSetEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart()).getPalletsWidget();
        PalletSet plateSet = BioSampleScanPlugin.getDefault().getPalletSet();
        FileDialog dlg = new FileDialog(viewComposite.getShell(), SWT.SAVE);
        dlg.setFilterExtensions(new String [] { "*.csv", "*.*" });
        dlg.setText(String.format("Save All Barcodes"));

        long largestSaveTime = 0;
        for (int i = 0; i < ConfigSettings.PALLET_NUM; i++) {
            if (plateSet.getPalletTimestamp(i + 1) > largestSaveTime) {
                largestSaveTime = plateSet.getPalletTimestamp(i + 1);
            }
        }
        if (largestSaveTime != 0) {
            Date d = new Date();
            d.setTime(largestSaveTime);
            dlg.setFileName(new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(d));
        }

        String saveLocation = dlg.open();
        if (saveLocation == null) {
            return null;
        }
        if (new File(saveLocation).exists()
            && !MessageDialog.openConfirm(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                "Save over existing file?",
                "A file already exists at the selected location are you sure you want to save over it?")) {
            return null;
        }
        boolean [] tablesCheck = new boolean [ConfigSettings.PALLET_NUM];
        for (int i = 0; i < ConfigSettings.PALLET_NUM; i++) {
            if (i < ConfigSettings.getInstance().getPalletCount()) tablesCheck[i] = true;
        }
        plateSet.saveTables(saveLocation, tablesCheck);
        return null;
    }
}
