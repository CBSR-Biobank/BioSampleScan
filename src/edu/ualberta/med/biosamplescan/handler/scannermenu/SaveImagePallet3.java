package edu.ualberta.med.biosamplescan.handler.scannermenu;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;

public class SaveImagePallet3 extends SaveImagePalletBase {
    @Override
    public boolean isEnabled() {
        return BioSampleScanPlugin.getDefault().getPalletEnabled(3);
    }

    public SaveImagePallet3() {
        palletId = 3;
    }

}