package edu.ualberta.med.biosamplescan.dialogs;

import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.model.PalletSet;
import edu.ualberta.med.scanlib.ScanCell;
import edu.ualberta.med.scannerconfig.ScannerConfigPlugin;

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

                        for (Integer pallet : palletsToDecode.keySet()) {
                            ScanCell[][] readBarcodes = ScannerConfigPlugin
                                .scan(pallet);

                            palletSet.loadFromArray(pallet - 1, readBarcodes,
                                rescan);
                            palletSet.setPalletTimestampNow(pallet - 1);
                            palletSet.setPalletBarocode(pallet - 1,
                                palletsToDecode.get(pallet));
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
                        BioSampleScanPlugin.openAsyncError("Decode Error", e
                            .getMessage());
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
