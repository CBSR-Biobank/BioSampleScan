
package edu.ualberta.med.biosamplescan.dialogs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.PalletSet;
import edu.ualberta.med.biosamplescan.widgets.AllPalletsWidget;
import edu.ualberta.med.scanlib.ScanLib;

public class DecodeDialog extends ProgressMonitorDialog {

    public DecodeDialog(final List<Integer> platesToDecode, final boolean rescan) {
        super(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
        try {
            run(true, true, new IRunnableWithProgress() {
                public void run(IProgressMonitor monitor) {
                    try {
                        monitor.beginTask("Decoding plates...",
                            IProgressMonitor.UNKNOWN);
                        ConfigSettings configSettings = ConfigSettings.getInstance();

                        for (Integer plate : platesToDecode) {
                            final int p = plate;
                            int scanlibReturn = ScanLib.getInstance().slDecodePlate(
                                configSettings.getDpi(), p);
                            switch (scanlibReturn) {
                                case (ScanLib.SC_SUCCESS):
                                    break;
                                case (ScanLib.SC_INVALID_IMAGE):
                                    BioSampleScanPlugin.openAsyncError(
                                        "Decoding Error",
                                        "scanPlateBtnWidgetSelected, DecodePlate");
                                    return;
                            }
                            PalletSet palletSet = BioSampleScanPlugin.getDefault().getPalletSet();

                            palletSet.loadFromScanlibFile(p, rescan);
                            palletSet.setPalletTimestampNOW(p);

                            final AllPalletsWidget w = ((PlateSetEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart()).getPalletsWidget();
                            w.updatePalletModel(p - 1,
                                palletSet.getPallet(p - 1));

                            Display.getDefault().asyncExec(new Runnable() {
                                public void run() {
                                    w.refreshPallet(p);
                                }
                            });
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        monitor.done();
                    }

                }
            });
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
