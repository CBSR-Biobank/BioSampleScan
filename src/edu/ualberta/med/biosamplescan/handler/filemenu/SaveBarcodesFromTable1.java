package edu.ualberta.med.biosamplescan.handler.filemenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;

public class SaveBarcodesFromTable1 extends AbstractHandler implements IHandler {
    @Override
    public boolean isEnabled() {
        return BioSampleScanPlugin.getDefault().getPalletEnabled(1);
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        SaveBarcodesFromTableX.execute(1);
        return null;
    }

}
