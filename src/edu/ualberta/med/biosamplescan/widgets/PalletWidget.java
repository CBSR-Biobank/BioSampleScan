
package edu.ualberta.med.biosamplescan.widgets;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.model.Pallet;

public class PalletWidget extends Composite {

    private Button clearBtn;

    private Text plateIdText;

    private PalletTableWidget tableWidget;

    public PalletWidget(Composite parent, int style, int palletNum) {
        super(parent, style);

        setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        setLayout(new GridLayout(1, false));

        Composite subSection = new Composite(this, SWT.NONE);
        subSection.setLayout(new GridLayout(4, false));

        Label l = new Label(subSection, SWT.NONE);
        l.setText("Pallet " + (palletNum + 1) + " bar code:");

        plateIdText = new Text(subSection, SWT.BORDER);
        plateIdText.setTextLimit(15);

        clearBtn = new Button(subSection, SWT.NONE);
        clearBtn.setText("Clear");

        clearBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent evt) {
                if (MessageDialog.openConfirm(
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                    "Clear Platetext and Clear PlateTable",
                    "Are you sure you want to clear the plate?")) {
                    clearPlateTable();
                }

            }
        });

        tableWidget = new PalletTableWidget(this, SWT.NONE);
    }

    public boolean isSelected() {
        return (plateIdText.getText().length() > 0);
    }

    public String getPalletId() {
        return plateIdText.getText();
    }

    public void clearPlateTable() {
        plateIdText.setText("");
    }

    public void setEnabled(boolean enabled) {
        tableWidget.getTableViewer().getTable().setEnabled(enabled);
        tableWidget.getTableViewer().getTable().setVisible(enabled);
        plateIdText.setEnabled(enabled);

        if (!enabled) {
            clearPlateTable();
        }
    }

    public void setPalletBarcodes(Pallet pallet) {
        tableWidget.setPalletBarcodes(pallet);
    }

}
