
package edu.ualberta.med.biosamplescan.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import edu.ualberta.med.biosamplescan.model.Pallet;

public class PalletWidget extends Composite {

    private PalletTableWidget tableWidget;

    public PalletWidget(Composite parent, int style, int palletNum) {
        super(parent, style);

        setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        setLayout(new GridLayout(1, false));

        Composite subSection = new Composite(this, SWT.NONE);
        subSection.setLayout(new GridLayout(4, false));

        Label l = new Label(subSection, SWT.NONE);
        l.setText("Pallet " + (palletNum + 1));
        tableWidget = new PalletTableWidget(this, SWT.NONE);
    }

    public void setEnabled(boolean enabled) {
        tableWidget.getTableViewer().getTable().setEnabled(enabled);
        tableWidget.getTableViewer().getTable().setVisible(enabled);
    }

    public void setPalletBarcodes(Pallet pallet) {
        tableWidget.setPalletBarcodes(pallet);
    }

}
