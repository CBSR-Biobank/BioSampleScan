package edu.ualberta.med.biosamplescan.handler.debug;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.handlers.HandlerUtil;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;

public class SimulateScannng extends AbstractHandler implements IHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        Command command = event.getCommand();
        boolean oldValue = HandlerUtil.toggleCommandState(command);
        BioSampleScanPlugin.getDefault().setSimulateScanning(!oldValue);
        return null;
    }
}
