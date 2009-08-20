
package edu.ualberta.med.biosamplescan.handler.filemenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
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

        DirectoryDialog dlg = new DirectoryDialog(viewComposite.getShell(),
            SWT.SAVE);
        dlg.setText("Directory to save pallet decode information");
        dlg.setMessage("Select a directory");
        dlg.setFilterPath(configSettings.getLastSaveDir());
        String saveDir = dlg.open();

        if (saveDir != null) {
            configSettings.setLastSaveDir(saveDir);
            for (int i = 0; i < ConfigSettings.PALLET_NUM; ++i) {
                Pallet pallet = palletSet.getPallet(i);
                if (pallet == null) continue;
                palletSet.savePallet(saveDir, pallet);
            }
        }
        BioSampleScanPlugin.getDefault().getPalletSetView().updateStatusBar(
            "Files saved.");
        return null;
    }

}
