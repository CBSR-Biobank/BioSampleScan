package edu.ualberta.med.biosamplescan.handler.filemenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;

public class SaveBarcodesFromTable3 extends AbstractHandler implements IHandler {
    @Override
    public boolean isEnabled() {
        return BioSampleScanPlugin.getDefault().getPalletEnabled(3);
    }

    public Object execute(ExecutionEvent event) throws ExecutionException {
        SaveBarcodesFromTableX.execute(3);
        return null;
    }

}
