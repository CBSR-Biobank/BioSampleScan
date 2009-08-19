
package edu.ualberta.med.biosamplescan.handler.scannermenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.scanlib.ScanLib;

public class SelectSource extends AbstractHandler implements IHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
        int scanlibReturn = ScanLib.getInstance().slSelectSourceAsDefault();

        if (scanlibReturn != ScanLib.SC_SUCCESS) {
            BioSampleScanPlugin.openAsyncError("Source Selection Error",
                ScanLib.getErrMsg(scanlibReturn));
        }
        return null;
    }
}
