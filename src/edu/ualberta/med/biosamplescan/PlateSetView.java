package edu.ualberta.med.biosamplescan;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import edu.ualberta.med.biosamplescan.widgets.PalletSetWidget;

public class PlateSetView extends ViewPart {

    private PalletSetWidget palletSetWidget;

    public static final String ID =
        "edu.ualberta.med.biosamplescan.views.plateset";

    @Override
    public void createPartControl(Composite parent) {
        BioSampleScanPlugin.getDefault().setPlateSetView(this);
        palletSetWidget = new PalletSetWidget(parent, SWT.NONE);
    }

    public PalletSetWidget getPalletsWidget() {
        return palletSetWidget;
    }

    @Override
    public void setFocus() {
        palletSetWidget.setFocus();
    }

    public void updateStatusBar(String msg) {
        IStatusLineManager statusLine =
            getViewSite().getActionBars().getStatusLineManager();
        statusLine.setMessage(msg);
    }

    public void refresh() {
        palletSetWidget.refresh();
    }

}
