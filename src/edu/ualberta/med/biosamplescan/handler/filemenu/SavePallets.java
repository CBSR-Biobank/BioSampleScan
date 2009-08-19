
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
import edu.ualberta.med.biosamplescan.PlateSetView;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.Pallet;
import edu.ualberta.med.biosamplescan.model.PalletSet;
import edu.ualberta.med.biosamplescan.widgets.PalletSetWidget;

/**
 * Called to save the decoded bar codes for all pallets.
 */
public class SavePallets extends AbstractHandler implements IHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
        PalletSetWidget viewComposite = ((PlateSetView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart()).getPalletsWidget();
        PalletSet palletSet = BioSampleScanPlugin.getDefault().getPalletSet();
        ConfigSettings configSettings = ConfigSettings.getInstance();

        if ((configSettings.getLastSaveLocation() == null)
            || (configSettings.getLastSaveLocation().isEmpty())) {
            FileDialog dlg = new FileDialog(viewComposite.getShell(), SWT.SAVE);
            dlg.setFilterExtensions(new String [] { "*.csv", "*.*" });
            dlg.setText(String.format(
                "Save Barcodes for the Selected Plates, Append:%s",
                configSettings.getAppendSetting()));

            long largestSaveTime = 0;
            for (int i = 0; i < ConfigSettings.PALLET_NUM; i++) {
                Pallet pallet = palletSet.getPallet(i);

                if (pallet == null) continue;

                if ((palletSet.getPalletTimestamp(i) > largestSaveTime)
                    && viewComposite.getPalletSelected(i)) {
                    largestSaveTime = palletSet.getPalletTimestamp(i);
                }
            }
            if (largestSaveTime != 0) {
                Date d = new Date();
                d.setTime(largestSaveTime);
                dlg.setFileName(new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(d));
            }

            String saveLocation = dlg.open();
            if (saveLocation == null) {
                return null;
            }
            configSettings.setLastSaveLocation(saveLocation);
        }

        if (configSettings.getLastSaveLocation() == null) {
            // TODO remove later
            return null;
        }

        if (new File(configSettings.getLastSaveLocation()).exists()
            && !MessageDialog.openConfirm(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                "Save over existing file?",
                "A file already exists at the selected location are you sure you want to save over it?")) {
            return null;
        }

        boolean [] tablesCheck = new boolean [ConfigSettings.PALLET_NUM];
        for (int i = 0; i < ConfigSettings.PALLET_NUM; i++) {
            tablesCheck[i] = viewComposite.getPalletSelected(i);
        }
        palletSet.savePallets(configSettings.getLastSaveLocation());
        return null;
    }

}
