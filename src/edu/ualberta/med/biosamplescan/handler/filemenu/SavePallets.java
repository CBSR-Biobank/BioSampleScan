package edu.ualberta.med.biosamplescan.handler.filemenu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.Pallet;
import edu.ualberta.med.biosamplescan.model.PalletSet;

/**
 * Called to save the decoded bar codes for all pallets.
 */
public class SavePallets extends AbstractHandler implements IHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
        PalletSet palletSet = BioSampleScanPlugin.getDefault().getPalletSet();
        ConfigSettings configSettings = ConfigSettings.getInstance();

        String saveDir = dirDialog().open();
        List<String> filenames = new ArrayList<String>();

        if (saveDir != null) {
            configSettings.setLastSaveDir(saveDir);
            for (int i = 0; i < ConfigSettings.PALLET_NUM; ++i) {
                Pallet pallet = palletSet.getPallet(i);
                if (pallet == null)
                    continue;
                filenames.add(new File(palletSet.savePallet(saveDir, pallet))
                    .getName());
            }

            String msg = new String();

            if (filenames.size() == 1) {
                msg = "File " + filenames.get(0) + " saved.";
            }
            else if (filenames.size() > 1) {
                msg = "Files " + StringUtils.join(filenames, ", ") + " saved.";
            }
            else {
                Assert.isTrue(false, "no files saved");
            }

            BioSampleScanPlugin.getDefault().getPalletSetView()
                .updateStatusBar(msg);
        }
        return null;
    }

    public static DirectoryDialog dirDialog() {
        DirectoryDialog dlg =
            new DirectoryDialog(PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell(), SWT.SAVE);
        dlg.setText("Directory to save pallet decode information");
        dlg.setMessage("Select a directory");
        dlg.setFilterPath(ConfigSettings.getInstance().getLastSaveDir());
        return dlg;
    }

}
