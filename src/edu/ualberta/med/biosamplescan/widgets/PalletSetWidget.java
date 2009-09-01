package edu.ualberta.med.biosamplescan.widgets;

import java.util.HashMap;
import java.util.Map;

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
import edu.ualberta.med.biosamplescan.dialogs.DecodeDialog;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.Pallet;
import edu.ualberta.med.biosamplescan.model.PalletScanCoordinates;
import edu.ualberta.med.biosamplescan.model.PalletSet;

public class PalletSetWidget extends ScrolledComposite {

    private Composite composite;

    private PalletSet palletSet;

    private Button reScanPlateBtn;
    private Button scanPlateBtn;
    private Button clearPlateBtn;
    private PalletBarcodesWidget palletBarcodesWidget;
    private PalletWidget[] palletWidgets;

    public PalletSetWidget(Composite parent, int style) {
        super(parent, SWT.H_SCROLL | SWT.V_SCROLL | style);
        setExpandHorizontal(true);
        setExpandVertical(true);

        palletSet = new PalletSet();

        composite = new Composite(this, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        createTopButtonsSection(composite);
        palletBarcodesWidget = new PalletBarcodesWidget(composite, SWT.NONE);

        int palletsMax = ConfigSettings.getInstance().getPalletMax();
        palletWidgets = new PalletWidget[palletsMax];

        ConfigSettings config = ConfigSettings.getInstance();
        for (int table = 0; table < palletsMax; table++) {
            palletWidgets[table] = new PalletWidget(composite, SWT.NONE, table);
            palletWidgets[table].setEnabled(config.palletIsSet(table + 1));
        }

        // composite.pack();
        composite.layout();
        setContent(composite);

        setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    @Override
    public boolean setFocus() {
        return true;
    }

    private void createTopButtonsSection(Composite parent) {
        Composite section = new Composite(parent, SWT.NONE);
        section.setLayout(new GridLayout(4, false));
        section
            .setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

        clearPlateBtn = new Button(section, SWT.PUSH | SWT.CENTER);
        clearPlateBtn.setText("Clear Selected");
        // clearPlateBtn.setBounds(488, 6, 90, 40);
        clearPlateBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent evt) {
                confirmClearPallets();
            }
        });

        reScanPlateBtn = new Button(section, SWT.PUSH | SWT.CENTER);
        reScanPlateBtn.setText("Re-Scan Selected");
        reScanPlateBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent evt) {
                scanPalletBtnWidgetSelected(true);
            }
        });

        scanPlateBtn = new Button(section, SWT.PUSH | SWT.CENTER);
        scanPlateBtn.setText("Scan Selected");
        scanPlateBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent evt) {
                scanPalletBtnWidgetSelected(false);
            }
        });

    }

    private boolean confirmMsg(String title, String msg) {
        return MessageDialog.openConfirm(getShell(), title, msg);
    }

    public void scanPalletBtnWidgetSelected(boolean rescan) {
        Map<String, Integer> selected = new HashMap<String, Integer>();

        for (int i = 0, n = ConfigSettings.getInstance().getPalletMax(); i < n; i++) {
            String palletBarcode = palletBarcodesWidget.getPalletBarcode(i);

            if (palletBarcode.length() == 0)
                continue;

            if (selected.containsKey(palletBarcode)) {
                BioSampleScanPlugin.openError("Duplicate Pallet Barcodes",
                    "Pallets " + selected.get(palletBarcode) + " and "
                        + (i + 1) + " have the same barcodes");
                return;
            } else {
                selected.put(palletBarcode, i + 1);
            }
        }

        if (selected.size() == 0) {
            BioSampleScanPlugin.openError("Error", "No Pallets Selected");
            return;
        }

        ConfigSettings configSettings = ConfigSettings.getInstance();
        Map<Integer, String> palletsToDecode = new HashMap<Integer, String>();

        for (Integer pallet : selected.values()) {

            PalletScanCoordinates coords = configSettings.getPallet(pallet);

            if (coords == null) {
                BioSampleScanPlugin.openError("Configuration Error", "Pallet "
                    + pallet + " not configured");
                return;
            }

            if (coords.left + coords.top + coords.right + coords.bottom > 0) {
                palletsToDecode.put(pallet, palletBarcodesWidget
                    .getPalletBarcode(pallet - 1));
            }
        }

        if (palletsToDecode.size() > 0) {
            new DecodeDialog(palletsToDecode, palletSet, rescan);
        }
    }

    public void updatePalletModel() {
        for (int p = 0, n = ConfigSettings.getInstance().getPalletMax(); p < n; p++) {
            Pallet pallet = palletSet.getPallet(p);
            if (pallet != null)
                palletWidgets[p].setPalletBarcodes(pallet);
        }
    }

    public void confirmClearPallets() {
        if (confirmMsg("Clear Pallets",
            "Do you want to clear the selected pallets?")) {
            ConfigSettings.getInstance().setLastSaveDir("");
            clearPallets();

            BioSampleScanPlugin.getDefault().updateStatusBar(
                "Decode information cleared.");
        }
    }

    public void clearPallets() {
        palletSet = new PalletSet();

        for (int p = 0, n = ConfigSettings.getInstance().getPalletMax(); p < n; p++) {
            palletBarcodesWidget.clearText();
            palletWidgets[p].setPalletBarcodes(null);
        }
    }

    public boolean getPalletSelected(int platenum) {
        return palletBarcodesWidget.isSelected(platenum);
    }

    public void refresh() {
        ConfigSettings config = ConfigSettings.getInstance();
        for (int table = 0, n = ConfigSettings.getInstance().getPalletMax(); table < n; table++) {
            boolean isSet = config.palletIsSet(table + 1);
            palletBarcodesWidget.setEnabled(table, isSet);
            palletWidgets[table].setEnabled(isSet);
        }
        layout(true);
        setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        updatePalletModel();
    }

    public PalletSet getPalletSet() {
        return palletSet;
    }
}
