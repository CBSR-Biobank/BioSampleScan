
package edu.ualberta.med.biosamplescan;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import edu.ualberta.med.biosamplescan.widgets.PalletSetWidget;

public class PlateSetView extends ViewPart {

    private PalletSetWidget allPalletsWidget;

    public static final String ID = "edu.ualberta.med.biosamplescan.views.plateset";

    @Override
    public void createPartControl(Composite parent) {
        BioSampleScanPlugin.getDefault().setPlateSetView(this);
        allPalletsWidget = new PalletSetWidget(parent, SWT.NONE);
    }

    public PalletSetWidget getPalletsWidget() {
        return allPalletsWidget;
    }

    @Override
    public void setFocus() {
        allPalletsWidget.setFocus();
    }

}
