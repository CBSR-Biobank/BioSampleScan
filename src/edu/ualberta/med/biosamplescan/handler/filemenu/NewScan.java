package edu.ualberta.med.biosamplescan.handler.filemenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.editors.PalletSetEditor;
import edu.ualberta.med.biosamplescan.editors.PalletSetInput;
import edu.ualberta.med.biosamplescan.widgets.PalletSetWidget;

public class NewScan extends AbstractHandler implements IHandler {
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        // is there an editor open?
        PalletSetEditor editor = BioSampleScanPlugin.getDefault()
            .getPalletSetEditor();

        if (editor != null) {
            // there is an open editor
            PalletSetWidget widget = editor.getPalletSetWidget();

            if (MessageDialog.openConfirm(PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell(), "New Scan",
                "Do you want to clear all the tables?")) {
                widget.clearPallets();
                BioSampleScanPlugin.getDefault().getPalletSetEditor()
                    .clearPalletSet();
            }
        } else {
            // no editor currently open
            try {
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getActivePage()
                    .openEditor(new PalletSetInput(), PalletSetEditor.ID, true);
            } catch (PartInitException e) {
                e.printStackTrace();
            }

        }

        return null;
    }
}
