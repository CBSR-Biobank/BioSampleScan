
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
import edu.ualberta.med.biosamplescan.widgets.AllPalletsWidget;

public class SaveSelectedBarcodes extends AbstractHandler implements IHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
        AllPalletsWidget viewComposite = ((PlateSetEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart()).getViewComposite();
        PalletSet plateSet = BioSampleScanPlugin.getDefault().getPalletSet();
        if (ConfigSettings.getInstance().getLastSaveLocation() == null
            || ConfigSettings.getInstance().getLastSaveLocation().isEmpty()) {
            FileDialog dlg = new FileDialog(viewComposite.getShell(), SWT.SAVE);
            dlg.setFilterExtensions(new String [] { "*.csv", "*.*" });
            dlg.setText(String.format(
                "Save Barcodes for the Selected Plates, Append:%s",
                ConfigSettings.getInstance().getAppendSetting()));

            long largestSaveTime = 0;
            for (int i = 0; i < ConfigSettings.PALLET_NUM; i++) {
                if (plateSet.getPalletTimestamp(i + 1) > largestSaveTime
                    && viewComposite.getPlateBtnSelection(i)) {
                    largestSaveTime = plateSet.getPalletTimestamp(i + 1);
                }
            }
            if (largestSaveTime != 0) {
                Date d = new Date();
                d.setTime(largestSaveTime);
                dlg.setFileName(new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(d));
            }

            String saveLocation = dlg.open();
            if (saveLocation != null) {
                ConfigSettings.getInstance().setLastSaveLocation(saveLocation);
            }
            else {
                return null;
            }

        }
        if (ConfigSettings.getInstance().getLastSaveLocation() == null) {// TODO
            // remove
            // later
            return null;
        }
        if (new File(ConfigSettings.getInstance().getLastSaveLocation()).exists()
            && !MessageDialog.openConfirm(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                "Save over existing file?",
                "A file already exists at the selected location are you sure you want to save over it?")) {
            return null;
        }

        boolean [] tablesCheck = new boolean [ConfigSettings.PALLET_NUM];
        for (int i = 0; i < ConfigSettings.PALLET_NUM; i++) {
            tablesCheck[i] = viewComposite.getPlateBtnSelection(i);
        }
        plateSet.saveTables(ConfigSettings.getInstance().getLastSaveLocation(),
            tablesCheck, ConfigSettings.getInstance().getAppendSetting());
        return null;
    }

}
