package edu.ualberta.med.biosamplescan.handler.scannermenu;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;

public class SaveImagePallet1 extends SaveImagePalletBase {
    @Override
    public boolean isEnabled() {
        return BioSampleScanPlugin.getDefault().getPalletEnabled(1);
    }

    public SaveImagePallet1() {
        palletId = 1;
    }

}
