package edu.ualberta.med.biosamplescan.handler.filemenu;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Assert;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.Pallet;
import edu.ualberta.med.biosamplescan.model.PalletSet;

public class SaveBarcodesFromTableX {
    public static final Object execute(ExecutionEvent event, int palletId)
        throws ExecutionException {
        if (palletId > ConfigSettings.getInstance().getPalletMax()) {
            BioSampleScanPlugin.openError("Error",
                "Not configured for this pallet");
            return null;
        }

        if (SavePallets.singlePalletSave())
            return null;

        PalletSet palletSet = BioSampleScanPlugin.getDefault().getPalletSet();

        if (palletSet.getPallet(palletId - 1) == null) {
            BioSampleScanPlugin.openError("Save Error", "Pallet " + palletId
                + " was not decoded.");
            return null;
        }

        // TODO actually disable menu items

        String saveDir = SavePallets.dirDialog().open();

        if (saveDir != null) {
            List<Integer> palletIds = new ArrayList<Integer>();
            palletIds.add(palletId - 1);

            Pallet pallet = palletSet.getPallet(palletId - 1);
            Assert.isNotNull(pallet, "No pallet for pallet id: " + palletId);

            String filename = palletSet.savePalletToDir(saveDir, pallet);
            BioSampleScanPlugin.getDefault().getPalletSetView()
                .updateStatusBar("File " + filename + " saved.");
        }
        return null;
    }

}
