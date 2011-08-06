package edu.ualberta.med.biosamplescan.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.dialogs.DecodeDialog;
import edu.ualberta.med.biosamplescan.model.Pallet;
import edu.ualberta.med.biosamplescan.model.PalletBarcodeHistory;
import edu.ualberta.med.biosamplescan.model.PalletSet;
import edu.ualberta.med.scannerconfig.ScannerConfigPlugin;
import edu.ualberta.med.scannerconfig.dmscanlib.ScanLib;
import edu.ualberta.med.scannerconfig.dmscanlib.ScanLibResult;
import edu.ualberta.med.scannerconfig.preferences.PreferenceConstants;
import edu.ualberta.med.scannerconfig.preferences.scanner.profiles.ProfileManager;
import edu.ualberta.med.scannerconfig.preferences.scanner.profiles.ProfileSettings;

public class PalletSetWidget extends ScrolledComposite {

    private Composite composite;

    private PalletSet palletSet;

    private Button reScanPlateBtn;
    private Button scanPlateBtn;
    private Button clearPlateBtn;
    private PalletBarcodesWidget palletBarcodesWidget;
    private PalletWidget[] palletWidgets;
    Combo profilesCombo;

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

        int palletsMax = BioSampleScanPlugin.getDefault().getPalletsMax();
        palletWidgets = new PalletWidget[palletsMax];

        for (int table = 0; table < palletsMax; table++) {
            palletWidgets[table] = new PalletWidget(composite, SWT.NONE, table);
            palletWidgets[table].setEnabled(BioSampleScanPlugin.getDefault()
                .getPalletEnabled(table + 1));
        }
        colorTables();

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
        section.setLayout(new GridLayout(5, false));
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
                try {
                    scanPalletBtnWidgetSelected(false);
                } catch (Exception e) {
                    BioSampleScanPlugin.openAsyncError("widget selected",
                        e.getMessage());
                }
            }
        });

        (new Label(section, SWT.NONE)).setText("Profile: ");
        profilesCombo = new Combo(section, SWT.DROP_DOWN | SWT.READ_ONLY);
        profilesCombo.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                colorTables();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        loadProfileCombo();

    }

    private void colorTables() {

        for (int i = 0; i < palletWidgets.length; i++) {
            if (palletWidgets[i] != null && profilesCombo.getText() != null) {

                ProfileSettings profile = ProfileManager.instance().getProfile(
                    profilesCombo.getText());

                if (profile == null)
                    continue;

                for (int y = 0; y < 8; y++) {
                    for (int x = 0; x < 12; x++) {

                        /*
                         * FIXME
                         * palletWidgets[i].tableViewer.getTable().getItems()[y]
                         * .getText() needs to be called to prevent erasing
                         * table entries.
                         */
                        palletWidgets[i].tableViewer.getTable().getItems()[y]
                            .getText();
                        if (profile.get(x + y * 12)) {

                            palletWidgets[i].tableViewer.getTable().getItems()[y]
                                .setBackground(x + 1, new Color(getDisplay(),
                                    250, 235, 215));
                        } else {
                            palletWidgets[i].tableViewer.getTable().getItems()[y]
                                .setBackground(x + 1, new Color(getDisplay(),
                                    255, 255, 255));
                        }
                    }
                }

            }
        }
    }

    public void loadProfileCombo() {
        profilesCombo.removeAll();

        ArrayList<String> profileList = new ArrayList<String>();
        for (String element : ProfileManager.instance().getProfiles().keySet()) {
            profileList.add(element);
        }
        Collections.sort(profileList); // Alphabetic sort
        for (String element : profileList) {
            profilesCombo.add(element);
        }
        profilesCombo.select(0);
    }

    private boolean confirmMsg(String title, String msg) {
        return MessageDialog.openConfirm(getShell(), title, msg);
    }

    private void infoMsg(String title, String msg) {
        MessageDialog.openInformation(getShell(), title, msg);
    }

    public void scanPalletBtnWidgetSelected(boolean rescan) {

        ScanLib.getInstance();
        ScanLibResult result = ScanLib.getInstance().getScannerCapability();
        if ((result.getValue() & ScanLib.CAP_IS_SCANNER) == 0) {
            BioSampleScanPlugin
                .openError(
                    "Scanner Source Not Found",
                    "Please plug in your scanner and select an appropiate source form the configuration->preferencs->scanner menu.");
            return;
        }

        Map<String, Integer> selected = new HashMap<String, Integer>();

        for (int i = 0, n = BioSampleScanPlugin.getDefault().getPalletsMax(); i < n; i++) {
            String palletBarcode = palletBarcodesWidget.getPalletBarcode(i);

            if (palletBarcode.length() == 0)
                continue;

            /* Checks if the barcode has been changed during a new scan */
            if (rescan == false
                && PalletBarcodeHistory.getInstance().existsBarcode(
                    palletBarcode)) {
                if (!confirmMsg("Duplicate Pallet Product ID",
                    "You are scanning a new pallet (Pallet " + (i + 1)
                        + ") with a previously used product ID."
                        + "\nDo you want to continue?")) {
                    infoMsg("Pallet Ignored", "Pallet " + (i + 1)
                        + " has been unselected form the current scan.");
                    continue;
                }
            }

            if (selected.containsKey(palletBarcode)) {
                BioSampleScanPlugin.openError("Duplicate Pallet Barcodes",
                    "Error: Pallets " + selected.get(palletBarcode) + " and "
                        + (i + 1) + " have the same barcodes!");
                return;
            } else {
                selected.put(palletBarcode, i + 1);
            }
        }

        if (selected.size() == 0) {

            boolean anyPlateEnabled = false;
            for (int i = 0; i < PreferenceConstants.SCANNER_PALLET_ENABLED.length; i++)
                if (ScannerConfigPlugin.getDefault().getPreferenceStore()
                    .getBoolean(PreferenceConstants.SCANNER_PALLET_ENABLED[0])) {
                    anyPlateEnabled = true;
                    break;
                }
            if (anyPlateEnabled) {
                BioSampleScanPlugin.openError("Error",
                    "Atleast one enabled pallet must have a barcode.");
            } else {
                BioSampleScanPlugin.openError("Error",
                    "Please configure atleast one pallet before scanning.");
            }

            return;
        }

        Map<Integer, String> palletsToDecode = new HashMap<Integer, String>();

        for (Integer pallet : selected.values()) {
            if (!BioSampleScanPlugin.getDefault().getPalletEnabled(pallet))
                continue;

            palletsToDecode.put(pallet,
                palletBarcodesWidget.getPalletBarcode(pallet - 1));
        }

        if (palletsToDecode.size() > 0) {
            new DecodeDialog(palletsToDecode, palletSet, rescan,
                profilesCombo.getItem(profilesCombo.getSelectionIndex()));
        }
    }

    public void updatePalletModel() {
        for (int p = 0, n = BioSampleScanPlugin.getDefault().getPalletsMax(); p < n; p++) {
            Pallet pallet = palletSet.getPallet(p);
            if (pallet != null) {
                palletWidgets[p].setPalletBarcodes(pallet);
            }
        }
        colorTables();
    }

    public void confirmClearPallets() {
        if (confirmMsg("Clear Pallets",
            "Do you want to clear the selected pallets?")) {
            BioSampleScanPlugin.getDefault().setLastSaveDir("");
            clearPallets();

            BioSampleScanPlugin.getDefault().updateStatusBar(
                "Decode information cleared.");
        }
    }

    public void clearPallets() {
        palletSet = new PalletSet();

        for (int p = 0, n = BioSampleScanPlugin.getDefault().getPalletsMax(); p < n; p++) {
            palletBarcodesWidget.clearText();
            palletWidgets[p].setPalletBarcodes(null);
            colorTables();
        }
    }

    public boolean getPalletSelected(int platenum) {
        return palletBarcodesWidget.isSelected(platenum);
    }

    public void refreshPallet(int palletId) {
        if ((palletId < 0)
            || (palletId >= BioSampleScanPlugin.getDefault().getPalletsMax()))
            return;

        boolean isSet = BioSampleScanPlugin.getDefault().getPalletEnabled(
            palletId + 1);
        palletBarcodesWidget.setEnabled(palletId, isSet);
        palletWidgets[palletId].setEnabled(isSet);
        layout(true, true);
        setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        updatePalletModel();
    }

    public void refresh() {

        for (int pallet = 0, n = BioSampleScanPlugin.getDefault()
            .getPalletsMax(); pallet < n; pallet++) {
            refreshPallet(pallet);
        }
    }

    public PalletSet getPalletSet() {
        return palletSet;
    }
}
