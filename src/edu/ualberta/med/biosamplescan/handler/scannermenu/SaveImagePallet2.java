package edu.ualberta.med.biosamplescan.handler.scannermenu;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;

public class SaveImagePallet2 extends SaveImagePalletBase {
    @Override
    public boolean isEnabled() {
        return BioSampleScanPlugin.getDefault().getPalletEnabled(2);
    }

    public SaveImagePallet2() {
        palletId = 2;
    }

}
