package edu.ualberta.med.biosamplescan.dialogs;

import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.model.PalletSet;
import edu.ualberta.med.scanlib.ScanLib;

public class DecodeDialog extends ProgressMonitorDialog {

    public DecodeDialog(final Map<Integer, String> palletsToDecode,
        final PalletSet palletSet, final boolean rescan) {
        super(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());

        try {
            run(true, true, new IRunnableWithProgress() {
                public void run(IProgressMonitor monitor) {
                    try {
                        monitor.beginTask("Decoding plates...",
                            IProgressMonitor.UNKNOWN);

                        int res = ScanLib.getInstance()
                            .slConfigScannerBrightness(
                                BioSampleScanPlugin.getDefault()
                                    .getBrightness());
                        if (res < ScanLib.SC_SUCCESS) {
                            BioSampleScanPlugin.openAsyncError(
                                "Decoding Error", ScanLib.getErrMsg(res));
                            return;
                        }

                        res = ScanLib.getInstance().slConfigScannerContrast(
                            BioSampleScanPlugin.getDefault().getContrast());
                        if (res < ScanLib.SC_SUCCESS) {
                            BioSampleScanPlugin.openAsyncError(
                                "Decoding Error", ScanLib.getErrMsg(res));
                            return;
                        }

                        for (Integer pallet : palletsToDecode.keySet()) {
                            final int p = pallet;

                            if (!BioSampleScanPlugin.getDefault()
                                .getSimulateScanning()) {
                                int scanlibReturn = ScanLib.getInstance()
                                    .slDecodePlate(
                                        BioSampleScanPlugin.getDefault()
                                            .getDpi(), p, 0);
                                if (scanlibReturn != ScanLib.SC_SUCCESS) {
                                    BioSampleScanPlugin.openAsyncError(
                                        "Decoding Error", ScanLib
                                            .getErrMsg(scanlibReturn));
                                    return;
                                }
                            }

                            palletSet.loadFromScanlibFile(p - 1, rescan);
                            palletSet.setPalletTimestampNow(p - 1);
                            palletSet.setPalletBarocode(p - 1, palletsToDecode
                                .get(pallet));
                        }

                        Display.getDefault().asyncExec(new Runnable() {
                            public void run() {
                                String msg;
                                if (rescan) {
                                    msg = "Pallets re-scanned and decoded.";
                                } else {
                                    msg = "Pallets scanned and decoded.";
                                }

                                BioSampleScanPlugin.getDefault()
                                    .updateStatusBar(msg);

                                BioSampleScanPlugin.getDefault()
                                    .getPalletSetEditor().updateModel();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        monitor.done();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
