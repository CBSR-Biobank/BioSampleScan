
package edu.ualberta.med.biosamplescan.widgets;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.PalletSet;
import edu.ualberta.med.scanlib.ScanLib;

public class AllPalletsWidget extends ScrolledComposite {

    private Button reScanPlateBtn;
    private Button scanPlateBtn;
    private Button clearPlateBtn;
    private PalletWidget [] palletWidgets;

    public AllPalletsWidget(Composite parent, int style) {
        super(parent, SWT.H_SCROLL | SWT.V_SCROLL);
        initGUI();
    }

    public boolean setFocus() {
        /* reload global ui states */
        setPlateMode();
        return true;
    }

    private void initGUI() {
        setExpandHorizontal(true);
        setExpandVertical(true);
        this.getVerticalBar().setIncrement(10);
        this.getHorizontalBar().setIncrement(10);

        Composite top = new Composite(this, SWT.NONE);
        top.setLayout(new GridLayout(1, false));
        top.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        createTopButtonsSection(top);
        palletWidgets = new PalletWidget [ConfigSettings.PALLET_NUM];

        for (int table = 0; table < ConfigSettings.PALLET_NUM; table++) {
            palletWidgets[table] = new PalletWidget(top, SWT.NONE, table);
        }

        setPlateMode();
        top.layout();
        top.pack();
        setContent(top);

        setMinWidth(top.getBounds().width);
        setMinHeight(top.getBounds().height);
    }

    private void createTopButtonsSection(Composite parent) {
        Composite section = new Composite(parent, SWT.NONE);
        section.setLayout(new GridLayout(4, false));
        section.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

        clearPlateBtn = new Button(section, SWT.PUSH | SWT.CENTER);
        clearPlateBtn.setText("Clear Selected Table(s)");
        // clearPlateBtn.setBounds(488, 6, 90, 40);
        clearPlateBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent evt) {
                clearPlateBtnWidgetSelected(evt);

            }
        });

        reScanPlateBtn = new Button(section, SWT.PUSH | SWT.CENTER);
        reScanPlateBtn.setText("Re-Scan Selected Plate(s)");
        // reScanPlateBtn.setBounds(596, 6, 90, 40);
        reScanPlateBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent evt) {
                scanPlateBtnWidgetSelected(evt, true);
            }
        });

        scanPlateBtn = new Button(section, SWT.PUSH | SWT.CENTER);
        scanPlateBtn.setText("Scan Selected Plate(s)");
        scanPlateBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent evt) {
                scanPlateBtnWidgetSelected(evt, false);
            }
        });

    }

    private boolean confirmMsg(String title, String msg) {
        return MessageDialog.openConfirm(getShell(), title, msg);
    }

    private void errorMsg(String Identifier, int code) {
        if (code != 0) {
            MessageDialog.openError(getShell(), "Error", String.format(
                "%s\nReturned Error Code: %d\n", Identifier, code));
        }
        else {
            MessageDialog.openError(getShell(), "Error", Identifier);
        }
    }

    public void clearPlateBtnWidgetSelected(SelectionEvent evt) {
        PalletSet palletSet = BioSampleScanPlugin.getDefault().getPalletSet();
        if (confirmMsg("Clear Table(s)",
            "Do you want to clear the selected tables?")) {
            for (int p = 0; p < ConfigSettings.PALLET_NUM; p++) {
                palletSet.clearTable(p);
                palletWidgets[p].clearPlateTable();
            }
        }
    }

    public void scanPlateBtnWidgetSelected(SelectionEvent evt, boolean rescan) {
        boolean pass = false;
        for (int i = 0; i < ConfigSettings.PALLET_NUM; i++) {
            if (palletWidgets[i].isSelected()) {
                pass = true;
                break;
            }
        }
        if (!pass) {
            errorMsg("No Plates Selected", 0);
            return;
        }

        for (int i = 0; i < ConfigSettings.PALLET_NUM; i++) {
            if (palletWidgets[i].isSelected()) {
                String plateId = palletWidgets[i].getPalletId();
                if (plateId == null || plateId.isEmpty()) {
                    errorMsg("Pallet " + (i + 1) + " must have a label", 0);
                    return;
                } // TODO check and add plateid for all save routines
            }
        }

        PalletSet palletSet = BioSampleScanPlugin.getDefault().getPalletSet();

        for (int pallet = 0; pallet < ConfigSettings.PALLET_NUM; pallet++) {
            ConfigSettings configSettings = ConfigSettings.getInstance();

            if ((configSettings.getPallet(pallet + 1)[0]
                + configSettings.getPallet(pallet + 1)[1]
                + configSettings.getPallet(pallet + 1)[2]
                + configSettings.getPallet(pallet + 1)[3] > 0)
                && palletWidgets[pallet].isSelected()) {
                int scanlibReturn = ScanLib.getInstance().slDecodePlate(
                    configSettings.getDpi(), pallet + 1);
                switch (scanlibReturn) {
                    case (ScanLib.SC_SUCCESS):
                        break;
                    case (ScanLib.SC_INVALID_IMAGE):
                        errorMsg("scanPlateBtnWidgetSelected, DecodePlate",
                            scanlibReturn);
                        return;
                }
                palletSet.loadFromScanlibFile(pallet + 1, rescan);
                palletSet.setPalletTimestampNOW(pallet + 1);
                palletWidgets[pallet].refreshPalleteTable();
            }

        }
    }

    public void clearTables() {
        for (int p = 0; p < ConfigSettings.PALLET_NUM; p++) {
            clearPalletTable(p);
        }
    }

    private void clearPalletTable(int table) {
        palletWidgets[table].clearPlateTable();
        PalletSet palletSet = BioSampleScanPlugin.getDefault().getPalletSet();
        palletSet.initPallet(table + 1, 13, 8);
    }

    public void setPlateMode() {
        int platecount = ConfigSettings.getInstance().getPalletMode();
        boolean set = false;
        for (int table = 0; table < ConfigSettings.PALLET_NUM; table++) {
            set = (table < platecount);
            palletWidgets[table].setEnabled(set);
            if (!set) {
                clearPalletTable(table);
            }
        }
    }

    public boolean getPlateBtnSelection(int platenum) {
        return palletWidgets[platenum].isSelected();
    }

}
