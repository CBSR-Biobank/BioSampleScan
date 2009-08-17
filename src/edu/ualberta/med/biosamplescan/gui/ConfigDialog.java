
package edu.ualberta.med.biosamplescan.gui;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.scanlib.ScanLib;

public class ConfigDialog extends org.eclipse.swt.widgets.Dialog {

    private Shell dialogShell;
    private Composite top;

    private Text textDpi;
    private Text textBrightness;
    private Text textContrast;
    private Text platesText[][];

    private Button buttonCancel;
    private Button buttonConfig;
    private Button [] buttonEdit;
    private Button [] buttonClear;
    private Button [] ratioBtns;
    private Button twainBtn;
    private Button wiaBtn;

    private Label labels[];
    private Label platelabels[];

    private Group [] groups;

    public ConfigDialog(Shell parent, int style) {
        super(parent, style);
    }

    public void open() {
        try {

            int label_it = 0;
            int groups_it = 0;
            groups = new Group [ConfigSettings.PALLET_NUM + 6];
            platesText = new Text [ConfigSettings.PALLET_NUM + 1] [4];// left,top,right,bottom
            labels = new Label [ConfigSettings.PALLET_NUM * 5 + 11];
            platelabels = new Label [ConfigSettings.PALLET_NUM * 4];
            buttonEdit = new Button [4];
            buttonClear = new Button [4];

            dialogShell = new Shell(getParent(), SWT.DIALOG_TRIM
                | SWT.APPLICATION_MODAL);
            dialogShell.setFont(new Font(Display.getDefault(), "Tahoma", 10, 0));
            dialogShell.setText("Scanner Configuration");

            top = new Composite(dialogShell, SWT.NONE);
            top.setLayout(new GridLayout(1, false));

            ratioBtns = new Button [ConfigSettings.PALLET_NUM];
            {

                groups[++groups_it] = new Group(top, SWT.NONE);
                RowLayout group1Layout = new RowLayout(
                    org.eclipse.swt.SWT.HORIZONTAL);
                groups[groups_it].setLayout(group1Layout);
                GridData group1LData = new GridData();
                group1LData.widthHint = 180;
                group1LData.heightHint = 21;
                groups[groups_it].setLayoutData(group1LData);
                groups[groups_it].setText("Set Plate Mode:");
                {
                    for (int i = 0; i < ratioBtns.length; i++) {
                        ratioBtns[i] = new Button(groups[groups_it], SWT.RADIO
                            | SWT.LEFT);
                        RowData button1LData = new RowData();
                        ratioBtns[i].setLayoutData(button1LData);
                        ratioBtns[i].setText(String.valueOf(i + 1));
                        ratioBtns[i].setSelection(false);
                        ratioBtns[i].addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseUp(MouseEvent evt) {
                                for (int i = 0; i < ConfigSettings.PALLET_NUM; i++) {
                                    setPlateTextSettings(i);
                                }
                            }
                        });
                    }
                    if (ConfigSettings.getInstance().getPalletMode() > 0) {
                        ratioBtns[ConfigSettings.getInstance().getPalletMode() - 1].setSelection(true);
                    }
                }
            }
            {
                groups[++groups_it] = new Group(top, SWT.NONE);
                RowLayout group1Layout = new RowLayout(
                    org.eclipse.swt.SWT.HORIZONTAL);
                groups[groups_it].setLayout(group1Layout);
                GridData group1LData = new GridData();
                group1LData.widthHint = 140;
                group1LData.heightHint = 21;
                groups[groups_it].setLayoutData(group1LData);
                groups[groups_it].setText("Driver Type:");
                RowData rd = new RowData();
                {
                    twainBtn = new Button(groups[groups_it], SWT.RADIO
                        | SWT.LEFT);
                    twainBtn.setLayoutData(rd);
                    twainBtn.setText("Twain");
                    twainBtn.setSelection(true);
                    twainBtn.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseUp(MouseEvent evt) {
                            for (int i = 0; i < ConfigSettings.PALLET_NUM; i++) {
                                platelabels[4 * i + 2].setText("Bottom:");
                                platelabels[4 * i + 3].setText("Right:");
                            }
                        }
                    });

                    wiaBtn = new Button(groups[groups_it], SWT.RADIO | SWT.LEFT);
                    wiaBtn.setLayoutData(rd);
                    wiaBtn.setText("Wia");
                    wiaBtn.setSelection(false);
                    wiaBtn.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseUp(MouseEvent evt) {
                            for (int i = 0; i < ConfigSettings.PALLET_NUM; i++) {
                                platelabels[4 * i + 2].setText("Height:");
                                platelabels[4 * i + 3].setText("Width:");
                            }
                        }
                    });
                }
            }
            {
                groups[++groups_it] = new Group(top, SWT.NONE);
                FillLayout group1Layout = new FillLayout(
                    org.eclipse.swt.SWT.HORIZONTAL);
                GridData group1LData = new GridData();
                group1LData.widthHint = 421;
                group1LData.heightHint = 16;
                groups[groups_it].setLayoutData(group1LData);
                groups[groups_it].setLayout(group1Layout);
                groups[groups_it].setText("Scanner Settings");
                {
                    labels[++label_it] = new Label(groups[groups_it], SWT.NONE);
                    labels[label_it].setText("Dots Per Inch:");
                }
                {
                    textDpi = new Text(groups[groups_it], SWT.BORDER);
                    textDpi.setTextLimit(3);
                    textDpi.setText("300");
                }
                {
                    labels[++label_it] = new Label(groups[groups_it], SWT.NONE);
                    labels[label_it].setText("    Brightness:");
                    labels[label_it].setBounds(12, 21, 58, 13);
                }
                {
                    textBrightness = new Text(groups[groups_it], SWT.BORDER);
                    textBrightness.setText("0");
                    textBrightness.setTextLimit(5);
                    textBrightness.setBounds(68, 22, 33, 14);
                }
                {
                    labels[++label_it] = new Label(groups[groups_it], SWT.NONE);
                    labels[label_it].setText("       Contrast:");
                    labels[label_it].setBounds(105, 22, 49, 13);
                }
                {
                    textContrast = new Text(groups[groups_it], SWT.BORDER);
                    textContrast.setText("0");
                    textContrast.setTextLimit(5);
                    textContrast.setBounds(153, 22, 35, 20);
                }
            }
            for (int plate = 0; plate < ConfigSettings.PALLET_NUM; plate++) {
                groups[++groups_it] = new Group(top, SWT.HORIZONTAL);
                FillLayout group2Layout = new FillLayout(SWT.HORIZONTAL);
                groups[groups_it].setLayout(group2Layout);
                GridData group2LData = new GridData();
                group2LData.heightHint = 16;
                group2LData.horizontalAlignment = GridData.FILL;
                groups[groups_it].setLayoutData(group2LData);
                groups[groups_it].setText(String.format("Plate %d Position",
                    plate + 1));
                {
                    platelabels[4 * plate + 0] = new Label(groups[groups_it],
                        SWT.NONE);
                    platelabels[4 * plate + 0].setText("Top:");
                    platelabels[4 * plate + 0].setAlignment(SWT.RIGHT);
                }
                {
                    platesText[plate][1] = new Text(groups[groups_it],
                        SWT.BORDER);
                    platesText[plate][1].setText("0");
                    platesText[plate][1].setTextLimit(6);
                    platesText[plate][1].setOrientation(SWT.HORIZONTAL);
                    platesText[plate][1].setDoubleClickEnabled(false);

                }
                {
                    platelabels[4 * plate + 1] = new Label(groups[groups_it],
                        SWT.NONE);
                    platelabels[4 * plate + 1].setText("Left:");
                    platelabels[4 * plate + 1].setAlignment(SWT.RIGHT);
                }
                {
                    platesText[plate][0] = new Text(groups[groups_it],
                        SWT.BORDER);
                    platesText[plate][0].setText("0");
                    platesText[plate][0].setTextLimit(6);
                }
                {
                    platelabels[4 * plate + 2] = new Label(groups[groups_it],
                        SWT.NONE);
                    platelabels[4 * plate + 2].setText("Bottom:");
                    platelabels[4 * plate + 2].setAlignment(SWT.RIGHT);
                }
                {
                    platesText[plate][3] = new Text(groups[groups_it],
                        SWT.BORDER);
                    platesText[plate][3].setText("0");
                    platesText[plate][3].setTextLimit(6);
                }
                {
                    platelabels[4 * plate + 3] = new Label(groups[groups_it],
                        SWT.NONE);
                    platelabels[4 * plate + 3].setText("Right:");
                    platelabels[4 * plate + 3].setAlignment(SWT.RIGHT);
                }
                {
                    platesText[plate][2] = new Text(groups[groups_it],
                        SWT.BORDER);
                    platesText[plate][2].setText("0");
                    platesText[plate][2].setTextLimit(6);
                }
                {
                    buttonEdit[plate] = new Button(groups[groups_it], SWT.PUSH);
                    buttonEdit[plate].setText("Edit");
                    buttonClear[plate] = new Button(groups[groups_it], SWT.PUSH);
                    buttonClear[plate].setText("Clear");
                    {
                        final int fplate = plate;// .hak
                        buttonEdit[plate].addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseUp(MouseEvent evt) {
                                buttonPlateImageDialog(fplate + 1);
                            }
                        });
                        buttonClear[plate].addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseUp(MouseEvent evt) {
                                buttonClearPlateText(fplate + 1);
                            }
                        });
                    }
                }
                this.setPlateTextSettings(plate);
            }
            {
                groups[++groups_it] = new Group(top, SWT.NONE);
                RowLayout group5Layout = new RowLayout(
                    org.eclipse.swt.SWT.HORIZONTAL);
                groups[groups_it].setLayout(group5Layout);
                GridData group5LData = new GridData();
                groups[groups_it].setLayoutData(group5LData);
                {
                    buttonConfig = new Button(groups[groups_it], SWT.PUSH
                        | SWT.CENTER);
                    buttonConfig.setText(" Apply ");
                    buttonConfig.setSize(250, 70);
                    buttonConfig.setFont(new Font(Display.getDefault(),
                        "Tahoma", 10, SWT.BOLD));
                    RowData buttonConfigLData = new RowData();
                    buttonConfig.setLayoutData(buttonConfigLData);
                    buttonConfig.addSelectionListener(new SelectionAdapter() {
                        @Override
                        public void widgetSelected(SelectionEvent e) {
                            applySettings();
                        }
                    });
                }

                {
                    buttonCancel = new Button(groups[groups_it], SWT.PUSH
                        | SWT.CENTER);
                    buttonCancel.setText(" Cancel ");
                    RowData button1LData = new RowData();
                    buttonCancel.setSize(250, 70);
                    buttonCancel.setLayoutData(button1LData);
                    buttonCancel.setFont(new Font(Display.getDefault(),
                        "Tahoma", 10, 0));
                    buttonCancel.addSelectionListener(new SelectionAdapter() {
                        @Override
                        public void widgetSelected(SelectionEvent e) {
                            buttonCancelMouseUp();
                        }
                    });
                }
            }
            this.loadFromConfigSettings();
            top.pack();
            dialogShell.layout();
            dialogShell.pack();
            dialogShell.setLocation(getParent().toDisplay(100, 100));
            dialogShell.open();
            Display display = dialogShell.getDisplay();

            while (!dialogShell.isDisposed()) {
                if (!display.readAndDispatch()) display.sleep();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ViewComposite getActiveViewComposite() {
        return ((PlateSetEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart()).getViewComposite();
    }

    private void readPlatesTextToArray(double plateArray[][]) {
        for (int plate = 0; plate < ConfigSettings.PALLET_NUM; plate++) {
            for (int side = 0; side < 4; side++) {
                plateArray[plate][side] = Double.valueOf(platesText[plate][side].getText());
            }
        }
    }

    private int getActivePlateMode() {
        for (int i = 0; i < ratioBtns.length; i++) {
            if (ratioBtns[i].getSelection()) {
                return i + 1;
            }
        }
        return 0;
    }

    private void buttonClearPlateText(int plate) {
        if (MessageDialog.openConfirm(
            this.getActiveViewComposite().getActiveShell(),
            "Save over existing file?",
            "A file already exists at the selected location are you sure you want to save over it?")) {

            for (int side = 0; side < 4; side++) {
                if (platesText[plate - 1][side].getEditable()) {
                    platesText[plate - 1][side].setText("0.0");
                }
            }
        }
    }

    private int loadFromConfigSettings() {
        ConfigSettings configSettings = ConfigSettings.getInstance();
        configSettings.loadFromFile();

        if (configSettings.getDriverType().equals("WIA")) {
            twainBtn.setSelection(false);
            wiaBtn.setSelection(true);

            for (int i = 0; i < ConfigSettings.PALLET_NUM; i++) {
                platelabels[4 * i + 2].setText("Height:");
                platelabels[4 * i + 3].setText("Width:");
            }

        }
        else {
            twainBtn.setSelection(true);
            wiaBtn.setSelection(false);

            for (int i = 0; i < ConfigSettings.PALLET_NUM; i++) {
                platelabels[4 * i + 2].setText("Bottom:");
                platelabels[4 * i + 3].setText("Right:");
            }
        }

        textDpi.setText(String.valueOf(configSettings.getDpi()));
        textBrightness.setText(String.valueOf(configSettings.getBrightness()));
        textContrast.setText(String.valueOf(configSettings.getContrast()));

        for (int plate = 0; plate < ConfigSettings.PALLET_NUM; plate++) {
            for (int side = 0; side < 4; side++) {
                platesText[plate][side].setText(String.valueOf(configSettings.getPallet(plate + 1)[side]));
            }
        }
        return 0;
    }

    private void setPlateTextSettings(int plate) {
        Color c;
        switch (plate) {
            case (0):
                c = new Color(Display.getDefault(), 185, 255, 185);
                break;
            case (1):
                c = new Color(Display.getDefault(), 255, 185, 185);
                break;
            case (2):
                c = new Color(Display.getDefault(), 255, 255, 185);
                break;
            case (3):
                c = new Color(Display.getDefault(), 170, 255, 255);
                break;
            default:
                c = new Color(Display.getDefault(), 255, 255, 255);
                break;
        }
        int plateMode = this.getActivePlateMode();

        if (plateMode > 0 && plateMode <= ConfigSettings.PALLET_NUM) {
            for (int i = 0; i < 4; i++) {
                if (plate >= plateMode) {
                    platesText[plate][i].setBackground(new Color(
                        Display.getDefault(), 0, 0, 0));
                    platesText[plate][i].setEnabled(false);
                    buttonEdit[plate].setEnabled(false);
                    buttonClear[plate].setEnabled(false);
                }
                else {
                    platesText[plate][i].setEnabled(true);
                    platesText[plate][i].setBackground(c);
                    buttonEdit[plate].setEnabled(true);
                    buttonClear[plate].setEnabled(true);
                }
            }
        }
        this.dialogShell.redraw();
    }

    private void buttonCancelMouseUp() {
        dialogShell.dispose();
    }

    private void buttonPlateImageDialog(int plate) {
        double nplates[][] = new double [ConfigSettings.PALLET_NUM] [4];
        readPlatesTextToArray(nplates);
        if (!(new File(PlateImageDialog.alignFile).exists())
            || MessageDialog.openConfirm(dialogShell, "Re-Scan Plate Image?",
                "If you have moved the plates since the last calibration press OK.\n"
                    + "")) {
            ScanLib.getInstance().slScanImage((int) PlateImageDialog.alignDpi,
                0, 0, 0, 0, PlateImageDialog.alignFile);
        }

        PlateImageDialog pid = new PlateImageDialog(dialogShell, SWT.NONE);
        Color c;
        switch (plate - 1) {
            case (0):
                c = new Color(Display.getDefault(), 0, 0xFF, 0);
                break;
            case (1):
                c = new Color(Display.getDefault(), 0xFF, 0, 0);
                break;
            case (2):
                c = new Color(Display.getDefault(), 0xFF, 0xFF, 0);
                break;
            case (3):
                c = new Color(Display.getDefault(), 0, 0xFF, 0xFF);
                break;
            default:
                c = new Color(Display.getDefault(), 0xFF, 0xFF, 0xFF);
                break;
        }

        double plateset[] = pid.open(nplates[plate - 1],
            twainBtn.getSelection(), c);

        for (int i = 0; i < 4; i++) {
            platesText[plate - 1][i].setText(String.valueOf(plateset[i]));
        }
        dialogShell.redraw();
    }

    private void errorMsg(String Identifier, int code) {
        MessageDialog.openError(dialogShell, "Error", String.format(
            "%s\nReturned Error Code: %d\n", Identifier, code));
    }

    private void calibrateToPlate(int plate) {
        ConfigSettings configSettings = ConfigSettings.getInstance();

        int scanlibReturn = ScanLib.getInstance().slCalibrateToPlate(
            configSettings.getDpi(), plate);
        if (scanlibReturn != ScanLib.SC_SUCCESS) {
            ScanLib.getInstance().slConfigPlateFrame(plate, 0, 0, 0, 0);
            dialogShell.redraw();
        }
        switch (scanlibReturn) {

            case (ScanLib.SC_SUCCESS):
                MessageDialog.openInformation(dialogShell,
                    "Successful Calibration", String.format(
                        "Plate %d has been successfully setup", plate));
                break;
            case (ScanLib.SC_INVALID_DPI):
                this.errorMsg("Calibratation: Invalid Dpi", scanlibReturn);
                return;
            case (ScanLib.SC_INVALID_PLATE_NUM):
                this.errorMsg("Calibratation: Invalid Plate Number",
                    scanlibReturn);
                dialogShell.dispose();
                return;

            case (ScanLib.SC_INI_FILE_ERROR):
                this.errorMsg(
                    "Calibratation: Plate dimensions not found inside scanlib.ini",
                    scanlibReturn);
                dialogShell.dispose();
                return;
            case (ScanLib.SC_CALIBRATOR_ERROR):
                this.errorMsg(
                    "Calibration: Could not find 8 rows and 12 columns",
                    scanlibReturn);
                for (int i = 0; i < 4; i++) {
                    platesText[plate][i].setBackground(new Color(
                        Display.getDefault(), 0x44, 0x44, 0x44));
                }
                return;
            case (ScanLib.SC_CALIBRATOR_NO_REGIONS):
                this.errorMsg("menuConfigurationWidgetSelected, Calibratation",
                    scanlibReturn);
                return;
            case (ScanLib.SC_FAIL):
                this.errorMsg("Calibratation: Failed to scan", scanlibReturn);
                dialogShell.dispose();
                return;
        }
    }

    private void applySettings() {
        int configSettingsReturn;
        try {
            ConfigSettings configSettings = ConfigSettings.getInstance();
            ViewComposite viewComposite = this.getActiveViewComposite();

            /* =================Set Plate Mode================ */
            int plateMode = this.getActivePlateMode();
            if (plateMode > 0 && plateMode <= ConfigSettings.PALLET_NUM) {
                ConfigSettings.getInstance().setPalletMode(
                    String.valueOf(plateMode));
                viewComposite.setPlateMode();
            }
            /* =================Set Plate Mode================ */

            /* =================Set Driver Type================ */
            if (twainBtn.getSelection()) {
                configSettingsReturn = configSettings.setDriverType("TWAIN");
            }
            else if (wiaBtn.getSelection()) {
                configSettingsReturn = configSettings.setDriverType("WIA");
            }
            else {
                configSettingsReturn = ConfigSettings.INVALID_INPUT;
            }
            switch (configSettingsReturn) {
                case (ConfigSettings.SUCCESS):
                    break;
                case (ConfigSettings.NOCHANGE):
                    break;
                case (ConfigSettings.INVALID_INPUT):
                    MessageDialog.openError(dialogShell,
                        "configSettings.setDriverType",
                        "Please enter a valid input");
                    return;

                case (ConfigSettings.FILE_ERROR):
                    MessageDialog.openError(dialogShell,
                        "configSettings.setDriverType",
                        "Could not find scanlib.ini file");
                    dialogShell.dispose();
                    return;
            }
            /* =================Set Driver Type================ */

            /* =================Set DPI ================ */
            configSettingsReturn = configSettings.setDpi(textDpi.getText());
            switch (configSettingsReturn) {
                case (ConfigSettings.SUCCESS):
                    break;
                case (ConfigSettings.FILE_ERROR):
                    MessageDialog.openError(dialogShell,
                        "configSettings.setDpi",
                        "Could not find scanlib.ini file");
                    dialogShell.dispose();
                    return;
                case (ConfigSettings.INVALID_INPUT):
                    MessageDialog.openError(dialogShell,
                        "configSettings.setDpi",
                        "Dpi must be greater than 0 and no greater than 600");
                    return;
                case (ConfigSettings.NOCHANGE):
                    break;
            }
            /* =================Set DPI ================ */

            /* =================Set Brightness ================ */
            configSettingsReturn = configSettings.setBrightness(textBrightness.getText());
            switch (configSettingsReturn) {
                case (ConfigSettings.SUCCESS):
                    int scanlibReturn = ScanLib.getInstance().slConfigScannerBrightness(
                        configSettings.getBrightness());
                    switch (scanlibReturn) {
                        case (ScanLib.SC_SUCCESS):
                            break;
                        case (ScanLib.SC_INVALID_VALUE):
                            this.errorMsg("Brightness: Invalid Value",
                                scanlibReturn);
                            dialogShell.dispose();
                            return;

                        case (ScanLib.SC_INI_FILE_ERROR):
                            this.errorMsg(
                                "Brightness: Could not find scanlib.ini file",
                                scanlibReturn);
                            dialogShell.dispose();
                            return;
                    }
                    break;
                case (ConfigSettings.INVALID_INPUT):
                    MessageDialog.openError(dialogShell,
                        "configSettings.setBrightness",
                        "Brightness ranges from -1000 to 1000\n"
                            + "Please enter a valid input value");
                    return;
                case (ConfigSettings.NOCHANGE):
                    break;
            }
            /* =================Set Brightness ================ */

            /* =================Set Contrast ================ */
            configSettingsReturn = configSettings.setContrast(textContrast.getText());
            switch (configSettingsReturn) {
                case (ConfigSettings.SUCCESS):
                    int scanlibReturn = ScanLib.getInstance().slConfigScannerContrast(
                        configSettings.getContrast());
                    switch (scanlibReturn) {
                        case (ScanLib.SC_SUCCESS):
                            break;
                        case (ScanLib.SC_INVALID_VALUE):
                            this.errorMsg("Contrast: Invalid Value",
                                scanlibReturn);
                            dialogShell.dispose();
                            return;

                        case (ScanLib.SC_INI_FILE_ERROR):
                            this.errorMsg(
                                "Contrast: Could not find scanlib.ini file",
                                scanlibReturn);
                            dialogShell.dispose();
                            return;
                    }
                    break;
                case (ConfigSettings.INVALID_INPUT):
                    MessageDialog.openError(dialogShell,
                        "configSettings.setContrast",
                        "Contrast ranges from -1000 to 1000\n"
                            + "Please enter a valid input value");
                    return;
                case (ConfigSettings.NOCHANGE):
                    break;
            }
            /* =================Set Contrast ================ */

            /* =================Set Plate Settings ================ */
            double nplates[][] = new double [ConfigSettings.PALLET_NUM] [4];
            readPlatesTextToArray(nplates); /* Reads Plate Config Settings */

            for (int plate = 0; plate < ConfigSettings.PALLET_NUM; plate++) {
                int setPlateReturn = configSettings.setPallet(plate + 1,
                    nplates[plate][0], nplates[plate][1], nplates[plate][2],
                    nplates[plate][3]);
                if (setPlateReturn == ConfigSettings.SUCCESS
                    || setPlateReturn == ConfigSettings.CLEARDATA) {
                    int scanlibReturn = ScanLib.getInstance().slConfigPlateFrame(
                        plate + 1, configSettings.getPallet(plate + 1)[0],
                        configSettings.getPallet(plate + 1)[1],
                        configSettings.getPallet(plate + 1)[2],
                        configSettings.getPallet(plate + 1)[3]);
                    switch (scanlibReturn) {
                        case (ScanLib.SC_SUCCESS):
                            if (setPlateReturn == ConfigSettings.SUCCESS) {
                                calibrateToPlate(plate + 1);

                                /*
                                 * Only calibrates to plate if: the text fields
                                 * changed AND they are not all equal to zero
                                 * AND slConfigPlateFrame returns SC_SUCCESS
                                 */
                            }
                            break;
                        case (ScanLib.SC_INI_FILE_ERROR):
                            this.errorMsg(
                                "ConfigPlateFrame: Could not find scanlib.ini file",
                                scanlibReturn);
                            dialogShell.dispose();
                            return;
                    }
                }
                else if (setPlateReturn == ConfigSettings.INVALID_INPUT) {
                    MessageDialog.openError(dialogShell,
                        "configSettings.setPlate", "Please enter a valid input");
                    return;
                }

            }
            /* =================Set Plate Settings ================ */
            dialogShell.dispose();
        }
        catch (Exception e) {
            MessageDialog.openError(dialogShell, "Error", e.getMessage());
        }

    }
}
