package edu.ualberta.med.biosamplescan.handler.scannermenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.widgets.PalletSetWidget;
import edu.ualberta.med.scanlib.ScanLib;

public class ScanImageToFile extends AbstractHandler implements IHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
        PalletSetWidget widget = BioSampleScanPlugin.getDefault()
            .getPalletSetEditor().getPalletSetWidget();

        FileDialog dlg = new FileDialog(widget.getShell(), SWT.SAVE);
        dlg.setFilterExtensions(new String[] { "*.bmp", "*.*" });
        dlg.setText("Scan and Save Image");
        String saveLocation = dlg.open();
        if (saveLocation == null) {
            return null;
        }
        int scanlibReturn = ScanLib.getInstance()
            .slScanImage(BioSampleScanPlugin.getDefault().getDpi(), 0, 0, 0, 0,
                saveLocation);

        if (scanlibReturn != ScanLib.SC_SUCCESS) {
            MessageDialog.openError(PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell(), "Scanning Error",
                ScanLib.getErrMsg(scanlibReturn));
        }
        return null;
    }

}
