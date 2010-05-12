package edu.ualberta.med.biosamplescan.handler.scannermenu;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;

public class SaveImagePallet4 extends SaveImagePalletBase {
    @Override
    public boolean isEnabled() {
        return BioSampleScanPlugin.getDefault().getPalletEnabled(4);
    }

    public SaveImagePallet4() {
        palletId = 4;
    }

}
