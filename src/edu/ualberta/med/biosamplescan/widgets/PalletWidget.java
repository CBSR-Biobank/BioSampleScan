package edu.ualberta.med.biosamplescan.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import edu.ualberta.med.biosamplescan.model.Pallet;

public class PalletWidget extends Composite {

    @SuppressWarnings("unused")
    private int palletId;

    private Label label;

    private PalletTableWidget tableWidget;

    private GridData gridData;

    public PalletWidget(Composite parent, int style, int palletId) {
        super(parent, style);

        this.palletId = palletId;

        setLayout(new GridLayout(1, false));
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        setLayoutData(gridData);

        label = new Label(this, SWT.NONE);
        label.setText("Pallet " + (palletId + 1));
        tableWidget = new PalletTableWidget(this, SWT.NONE);
    }

    public void setEnabled(boolean enabled) {
        setVisible(enabled);
        label.setVisible(enabled);
        gridData.exclude = !enabled;
    }

    public void setPalletBarcodes(Pallet pallet) {
        tableWidget.setPalletBarcodes(pallet);
    }

}
