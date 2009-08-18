
package edu.ualberta.med.biosamplescan.widgets;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.PalletSet;

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

        plateIdText.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {

                PalletSet plateSet = BioSampleScanPlugin.getDefault().getPalletSet();

                for (int i = 0; i < ConfigSettings.PALLET_NUM; i++) {

                    if (plateSet.getPalletId(i + 1).length() > 0) {
                        boolean emptyTable = true;
                        for (int y = 0; y < plateSet.getPalletDim(i + 1).height; y++) {
                            for (int x = 0; x < plateSet.getPalletDim(i + 1).width; x++) {
                                try {
                                    if (plateSet.getPallet(i + 1) != null
                                        && plateSet.getPallet(i + 1)[x][y] != null
                                        && !plateSet.getPallet(i + 1)[x][y].isEmpty()) {
                                        emptyTable = false;
                                        break;
                                    }
                                }
                                catch (NullPointerException e1) {
                                    continue;
                                }
                            }
                        }
                        if (!emptyTable) {

                            plateIdText.setText(plateSet.getPalletId(i + 1));
                            continue;
                        }

                    }

                    plateSet.setPalletId(i + 1, plateIdText.getText());
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}
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
        tableWidget.refresh();
    }

    public void setEnabled(boolean enabled) {
        tableWidget.getTableViewer().getTable().setEnabled(enabled);
        tableWidget.getTableViewer().getTable().setVisible(enabled);
        plateIdText.setEnabled(enabled);

        if (!enabled) {
            clearPlateTable();
        }
    }

    public void refreshPalletTable() {
        tableWidget.refresh();
    }

}
