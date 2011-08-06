package edu.ualberta.med.biosamplescan.dialogs;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.model.PalletBarcodeHistory;
import edu.ualberta.med.biosamplescan.model.PalletSet;
import edu.ualberta.med.scannerconfig.ScannerConfigPlugin;
import edu.ualberta.med.scannerconfig.dmscanlib.ScanCell;

public class DecodeDialog extends ProgressMonitorDialog {

    public DecodeDialog(final Map<Integer, String> palletsToDecode,
        final PalletSet palletSet, final boolean rescan, final String profile) {
        super(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());

        try {
            run(true, true, new IRunnableWithProgress() {
                @Override
                public void run(IProgressMonitor monitor) {
                    try {
                        monitor.beginTask("Decoding plates...",
                            IProgressMonitor.UNKNOWN);

                        for (Integer pallet : palletsToDecode.keySet()) {
                            List<ScanCell> readBarcodes = ScannerConfigPlugin
                                .decodePlate(pallet, profile);

                            if (monitor.isCanceled()) {
                                BioSampleScanPlugin.openAsyncInformation(
                                    "Operation Canceled",
                                    "Canceled the scan at pallet #" + pallet
                                        + ".");
                                break;
                            }

                            if (!palletSet.loadFromArray(pallet - 1,
                                readBarcodes, rescan)) {
                                BioSampleScanPlugin
                                    .openAsyncError("Different Plate",
                                        "Error: You must use the same pallet when rescanning a pallet.");
                                continue;
                            }

                            palletSet.setPalletTimestampNow(pallet - 1);
                            palletSet.setPalletBarocode(pallet - 1,
                                palletsToDecode.get(pallet));

                            // add barcode to history
                            // (plate must contain atleast one tube)
                            if (!palletSet.isEmptyPalletBarcode(pallet - 1)) {
                                PalletBarcodeHistory.getInstance().addBarcode(
                                    palletSet.getPalletBarcode(pallet - 1));
                            }
                        }

                        Display.getDefault().asyncExec(new Runnable() {
                            @Override
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
                        BioSampleScanPlugin.openAsyncError("Decode Error",
                            e.getMessage());
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
