
package edu.ualberta.med.biosamplescan.handler.scannermenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.widgets.AllPalletsWidget;
import edu.ualberta.med.scanlib.ScanLib;

public class ScanImageToFile extends AbstractHandler implements IHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
        AllPalletsWidget viewComposite = ((PlateSetEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart()).getPalletsWidget();
        ConfigSettings configSettings = ConfigSettings.getInstance();

        FileDialog dlg = new FileDialog(viewComposite.getShell(), SWT.SAVE);
        dlg.setFilterExtensions(new String [] { "*.bmp", "*.*" });
        dlg.setText("Scan and Save Image");
        String saveLocation = dlg.open();
        if (saveLocation == null) {
            return null;
        }
        int scanlibReturn = ScanLib.getInstance().slScanImage(
            configSettings.getDpi(), 0, 0, 0, 0, saveLocation);
        switch (scanlibReturn) {
            case (ScanLib.SC_SUCCESS):
                break;
            case (ScanLib.SC_INVALID_DPI):
                MessageDialog.openError(
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                    "Error", String.format("%s\nReturned Error Code: %d\n",
                        "Scanlib ScanImage", "Invalid Dpi"));
                break;
            case (ScanLib.SC_INVALID_PLATE_NUM):
                MessageDialog.openError(
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                    "Error", String.format("%s\nReturned Error Code: %d\n",
                        "Scanlib ScanImagen", "Invalid plate Number"));
                break;
            case (ScanLib.SC_CALIBRATOR_NO_REGIONS):
                MessageDialog.openError(
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                    "Error", String.format("%s\nReturned Error Code: %d\n",
                        "Scanlib ScanImage", "Calibrator Error"));
                break;
            case (ScanLib.SC_FAIL):
                MessageDialog.openError(
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                    "Error", String.format("%s\nReturned Error Code: %d\n",
                        "Scanlib ScanImage", "Failure"));
                break;
            case (ScanLib.SC_FILE_SAVE_ERROR):
                MessageDialog.openError(
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                    "Error", String.format("%s\nReturned Error Code: %d\n",
                        "Scanlib ScanImage", "Failed to save to file"));
                break;
        }
        return null;
    }

}
