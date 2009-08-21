package edu.ualberta.med.biosamplescan.dialogs;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.PalletSet;
import edu.ualberta.med.scanlib.ScanLib;

public class DecodeDialog extends ProgressMonitorDialog {

    public DecodeDialog(final Map<Integer, String> palletsToDecode,
        final boolean rescan) {
        super(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
        try {
            run(true, true, new IRunnableWithProgress() {
                public void run(IProgressMonitor monitor) {
                    try {
                        monitor.beginTask("Decoding plates...",
                            IProgressMonitor.UNKNOWN);
                        ConfigSettings configSettings =
                            ConfigSettings.getInstance();

                        for (Integer pallet : palletsToDecode.keySet()) {
                            final int p = pallet;

                            if (!ConfigSettings.getInstance()
                                .getSimulateScanning()) {
                                int scanlibReturn =
                                    ScanLib.getInstance().slDecodePlate(
                                        configSettings.getDpi(), p);
                                if (scanlibReturn != ScanLib.SC_SUCCESS) {
                                    BioSampleScanPlugin.openAsyncError(
                                        "Decoding Error", ScanLib
                                            .getErrMsg(scanlibReturn));
                                    return;
                                }
                            }

                            PalletSet palletSet =
                                BioSampleScanPlugin.getDefault().getPalletSet();

                            palletSet.loadFromScanlibFile(p - 1, rescan);
                            palletSet.setPalletTimestampNow(p - 1);
                            palletSet.setPalletBarocode(p - 1, palletsToDecode
                                .get(pallet));

                            BioSampleScanPlugin.getDefault().getPalletSetView()
                                .getPalletSetWidget().updatePalletModel(p - 1);
                        }

                        Display.getDefault().asyncExec(new Runnable() {
                            public void run() {
                                String msg;
                                if (rescan) {
                                    msg = "Pallets re-scanned and decoded.";
                                }
                                else {
                                    msg = "Pallets scanned and decoded.";
                                }

                                BioSampleScanPlugin.getDefault()
                                    .getPalletSetView().updateStatusBar(msg);
                            }
                        });
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
