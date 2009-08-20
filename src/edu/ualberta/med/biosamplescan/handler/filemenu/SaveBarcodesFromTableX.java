
package edu.ualberta.med.biosamplescan.handler.filemenu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Assert;
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

public class SaveBarcodesFromTableX {
    public static final Object execute(ExecutionEvent event, int palletId)
        throws ExecutionException {
        if (ConfigSettings.getInstance().getPalletCount() < palletId) {
            return null;
        }

        // TODO actually disable menu items

        PalletSetWidget viewComposite = ((PlateSetView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart()).getPalletsWidget();
        PalletSet palletSet = BioSampleScanPlugin.getDefault().getPalletSet();
        FileDialog dlg = new FileDialog(viewComposite.getShell(), SWT.SAVE);
        dlg.setFilterExtensions(new String [] { "*.csv", "*.*" });
        dlg.setText(String.format("Save Barcodes For Plate %d", palletId));
        if (palletSet.getPalletTimestamp(palletId - 1) != 0) {
            Date d = new Date();
            d.setTime(palletSet.getPalletTimestamp(palletId - 1));
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

            List<Integer> palletIds = new ArrayList<Integer>();
            palletIds.add(palletId - 1);

            Pallet pallet = palletSet.getPallet(palletId - 1);
            Assert.isNotNull(pallet, "No pallet for pallet id: " + palletId);

            palletSet.savePallet(saveLocation, pallet);
        }
        return null;
    }

}
