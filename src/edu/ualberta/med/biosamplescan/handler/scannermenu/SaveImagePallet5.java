package edu.ualberta.med.biosamplescan.handler.scannermenu;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;

public class SaveImagePallet5 extends SaveImagePalletBase {
    @Override
    public boolean isEnabled() {
        return BioSampleScanPlugin.getDefault().getPalletEnabled(5);
    }

    public SaveImagePallet5() {
        palletId = 1;
    }
}
