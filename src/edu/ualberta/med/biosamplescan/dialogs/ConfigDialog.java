package edu.ualberta.med.biosamplescan.dialogs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.PalletScanCoordinates;
import edu.ualberta.med.scanlib.ScanLib;

public class ConfigDialog extends Dialog {

    private Text textDpi;
    private Text textBrightness;
    private Text textContrast;
    private Text platesText[][];

    private Button[] buttonEdit;
    private Button[] buttonClear;
    private Button[] ratioBtns;
    private Button twainBtn;
    private Button wiaBtn;

    private Label labels[];
    private Label platelabels[];

    private Group[] groups;

    private List<Integer> platesToCalibrate;

    public ConfigDialog(Shell parent) {
        super(parent);
        platesToCalibrate = new ArrayList<Integer>();
    }

    @Override
    protected Control createContents(Composite parent) {
        Control contents = super.createContents(parent);
        return contents;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite parentComposite = (Composite) super.createDialogArea(parent);
        Composite contents = new Composite(parentComposite, SWT.NONE);

        final int maxPallets = ConfigSettings.getInstance().getPalletMax();

        int label_it = 0;
        int groups_it = 0;
        groups = new Group[maxPallets + 6];
        platesText = new Text[maxPallets + 1][4];// left,top,right,bottom
        labels = new Label[maxPallets * 5 + 11];
        platelabels = new Label[maxPallets * 4];
        buttonEdit = new Button[maxPallets];
        buttonClear = new Button[maxPallets];

        contents.setLayout(new GridLayout(1, false));

        ratioBtns = new Button[maxPallets];
        {

            groups[++groups_it] = new Group(contents, SWT.NONE);
            RowLayout group1Layout =
                new RowLayout(org.eclipse.swt.SWT.HORIZONTAL);
            groups[groups_it].setLayout(group1Layout);
            GridData group1LData = new GridData();
            group1LData.widthHint = 180;
            group1LData.heightHint = 21;
            groups[groups_it].setLayoutData(group1LData);
            groups[groups_it].setText("Set Plate Mode:");
            {
                for (int i = 0; i < ratioBtns.length; i++) {
                    ratioBtns[i] =
                        new Button(groups[groups_it], SWT.RADIO | SWT.LEFT);
                    RowData button1LData = new RowData();
                    ratioBtns[i].setLayoutData(button1LData);
                    ratioBtns[i].setText(String.valueOf(i + 1));
                    ratioBtns[i].setSelection(false);
                    ratioBtns[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseUp(MouseEvent evt) {
                            for (int i = 0; i < maxPallets; i++) {
                                setPlateTextSettings(i);
                            }
                        }
                    });
                }
                if (maxPallets > 0) {
                    ratioBtns[maxPallets - 1].setSelection(true);
                }
            }
        }
        {
            groups[++groups_it] = new Group(contents, SWT.NONE);
            RowLayout group1Layout =
                new RowLayout(org.eclipse.swt.SWT.HORIZONTAL);
            groups[groups_it].setLayout(group1Layout);
            GridData group1LData = new GridData();
            group1LData.widthHint = 140;
            group1LData.heightHint = 21;
            groups[groups_it].setLayoutData(group1LData);
            groups[groups_it].setText("Driver Type:");
            RowData rd = new RowData();
            {
                twainBtn = new Button(groups[groups_it], SWT.RADIO | SWT.LEFT);
                twainBtn.setLayoutData(rd);
                twainBtn.setText("Twain");
                twainBtn.setSelection(true);
                twainBtn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseUp(MouseEvent evt) {
                        for (int i = 0; i < maxPallets; i++) {
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
                        for (int i = 0; i < maxPallets; i++) {
                            platelabels[4 * i + 2].setText("Height:");
                            platelabels[4 * i + 3].setText("Width:");
                        }
                    }
                });
            }
        }
        {
            groups[++groups_it] = new Group(contents, SWT.NONE);
            FillLayout group1Layout =
                new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
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
        for (int plate = 0; plate < maxPallets; plate++) {
            groups[++groups_it] = new Group(contents, SWT.HORIZONTAL);
            FillLayout group2Layout = new FillLayout(SWT.HORIZONTAL);
            groups[groups_it].setLayout(group2Layout);
            GridData group2LData = new GridData();
            group2LData.heightHint = 16;
            group2LData.horizontalAlignment = GridData.FILL;
            groups[groups_it].setLayoutData(group2LData);
            groups[groups_it].setText(String.format("Plate %d Position",
                plate + 1));
            {
                platelabels[4 * plate + 0] =
                    new Label(groups[groups_it], SWT.NONE);
                platelabels[4 * plate + 0].setText("Top:");
                platelabels[4 * plate + 0].setAlignment(SWT.RIGHT);
            }
            {
                platesText[plate][1] = new Text(groups[groups_it], SWT.BORDER);
                platesText[plate][1].setText("0");
                platesText[plate][1].setTextLimit(6);
                platesText[plate][1].setOrientation(SWT.HORIZONTAL);
                platesText[plate][1].setDoubleClickEnabled(false);

            }
            {
                platelabels[4 * plate + 1] =
                    new Label(groups[groups_it], SWT.NONE);
                platelabels[4 * plate + 1].setText("Left:");
                platelabels[4 * plate + 1].setAlignment(SWT.RIGHT);
            }
            {
                platesText[plate][0] = new Text(groups[groups_it], SWT.BORDER);
                platesText[plate][0].setText("0");
                platesText[plate][0].setTextLimit(6);
            }
            {
                platelabels[4 * plate + 2] =
                    new Label(groups[groups_it], SWT.NONE);
                platelabels[4 * plate + 2].setText("Bottom:");
                platelabels[4 * plate + 2].setAlignment(SWT.RIGHT);
            }
            {
                platesText[plate][3] = new Text(groups[groups_it], SWT.BORDER);
                platesText[plate][3].setText("0");
                platesText[plate][3].setTextLimit(6);
            }
            {
                platelabels[4 * plate + 3] =
                    new Label(groups[groups_it], SWT.NONE);
                platelabels[4 * plate + 3].setText("Right:");
                platelabels[4 * plate + 3].setAlignment(SWT.RIGHT);
            }
            {
                platesText[plate][2] = new Text(groups[groups_it], SWT.BORDER);
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
        this.loadFromConfigSettings();
        contents.pack();

        return contents;
    }

    private void readPlatesTextToArray(double plateArray[][]) {
        int maxPallets = ConfigSettings.getInstance().getPalletMax();
        for (int plate = 0; plate < maxPallets; plate++) {
            for (int side = 0; side < 4; side++) {
                plateArray[plate][side] =
                    Double.valueOf(platesText[plate][side].getText());
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
        if (MessageDialog
            .openConfirm(
                getShell(),
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
        int maxPallets = ConfigSettings.getInstance().getPalletMax();

        if (configSettings.getDriverType().equals("WIA")) {
            twainBtn.setSelection(false);
            wiaBtn.setSelection(true);

            for (int i = 0; i < maxPallets; i++) {
                platelabels[4 * i + 2].setText("Height:");
                platelabels[4 * i + 3].setText("Width:");
            }

        }
        else {
            twainBtn.setSelection(true);
            wiaBtn.setSelection(false);

            for (int i = 0; i < maxPallets; i++) {
                platelabels[4 * i + 2].setText("Bottom:");
                platelabels[4 * i + 3].setText("Right:");
            }
        }

        textDpi.setText(String.valueOf(configSettings.getDpi()));
        textBrightness.setText(String.valueOf(configSettings.getBrightness()));
        textContrast.setText(String.valueOf(configSettings.getContrast()));

        for (int plate = 0; plate < maxPallets; plate++) {
            PalletScanCoordinates coords = configSettings.getPallet(plate + 1);
            if (coords != null) {
                platesText[plate][0].setText(String.valueOf(coords.left));
                platesText[plate][1].setText(String.valueOf(coords.top));
                platesText[plate][2].setText(String.valueOf(coords.right));
                platesText[plate][3].setText(String.valueOf(coords.bottom));
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

        int maxPallets = ConfigSettings.getInstance().getPalletMax();
        if (plateMode > 0 && plateMode <= maxPallets) {
            for (int i = 0; i < 4; i++) {
                if (plate >= plateMode) {
                    platesText[plate][i].setBackground(new Color(Display
                        .getDefault(), 0, 0, 0));
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
    }

    private void buttonPlateImageDialog(int plate) {

        String osname = System.getProperty("os.name");
        if (!osname.startsWith("Windows")) {
            return;
        }

        int maxPallets = ConfigSettings.getInstance().getPalletMax();
        double nplates[][] = new double[maxPallets][4];
        readPlatesTextToArray(nplates);
        boolean msgDlg =
            MessageDialog
                .openConfirm(getShell(), "Re-Scan Plate Image?",
                    "If you have moved the plates since the last calibration press OK.");
        if (!(new File(PalletImageDialog.alignFile).exists()) || msgDlg) {
            ScanLib.getInstance().slScanImage((int) PalletImageDialog.alignDpi,
                0, 0, 0, 0, PalletImageDialog.alignFile);
        }

        PalletImageDialog pid = new PalletImageDialog(getShell(), SWT.NONE);
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

        double plateset[] =
            pid.open(nplates[plate - 1], twainBtn.getSelection(), c);

        for (int i = 0; i < 4; i++) {
            platesText[plate - 1][i].setText(String.valueOf(plateset[i]));
        }
        getShell().redraw();
    }

    protected void okPressed() {
        int configSettingsReturn;
        ConfigSettings configSettings = ConfigSettings.getInstance();
        int maxPallets = configSettings.getPalletMax();

        /* =================Set Plate Mode================ */
        int plateMode = getActivePlateMode();
        if (plateMode > 0 && plateMode <= maxPallets) {
            ConfigSettings.getInstance().setPalletsMax(plateMode);
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
                MessageDialog.openError(getShell(),
                    "configSettings.setDriverType",
                    "Please enter a valid input");
                return;

            case (ConfigSettings.FILE_ERROR):
                MessageDialog.openError(getShell(),
                    "configSettings.setDriverType",
                    "Could not find scanlib.ini file");
                getShell().dispose();
                return;
        }
        /* =================Set Driver Type================ */

        /* =================Set DPI ================ */
        configSettingsReturn = configSettings.setDpi(textDpi.getText());
        switch (configSettingsReturn) {
            case (ConfigSettings.SUCCESS):
                break;
            case (ConfigSettings.FILE_ERROR):
                MessageDialog.openError(getShell(), "configSettings.setDpi",
                    "Could not find scanlib.ini file");
                getShell().dispose();
                return;
            case (ConfigSettings.INVALID_INPUT):
                MessageDialog.openError(getShell(), "configSettings.setDpi",
                    "Dpi must be greater than 0 and no greater than 600");
                return;
            case (ConfigSettings.NOCHANGE):
                break;
        }
        /* =================Set DPI ================ */

        /* =================Set Brightness ================ */
        configSettingsReturn =
            configSettings.setBrightness(textBrightness.getText());
        switch (configSettingsReturn) {
            case (ConfigSettings.SUCCESS):
                int scanlibReturn =
                    ScanLib.getInstance().slConfigScannerBrightness(
                        configSettings.getBrightness());
                if (scanlibReturn != ScanLib.SC_SUCCESS) {
                    BioSampleScanPlugin.openError("Set Brightness", ScanLib
                        .getErrMsg(scanlibReturn));
                    getShell().dispose();
                }
                break;
            case (ConfigSettings.INVALID_INPUT):
                MessageDialog.openError(getShell(),
                    "configSettings.setBrightness",
                    "Brightness ranges from -1000 to 1000\n"
                        + "Please enter a valid input value");
                return;
            case (ConfigSettings.NOCHANGE):
                break;
        }
        /* =================Set Brightness ================ */

        /* =================Set Contrast ================ */
        configSettingsReturn =
            configSettings.setContrast(textContrast.getText());
        switch (configSettingsReturn) {
            case (ConfigSettings.SUCCESS):
                int scanlibReturn =
                    ScanLib.getInstance().slConfigScannerContrast(
                        configSettings.getContrast());
                if (scanlibReturn != ScanLib.SC_SUCCESS) {
                    BioSampleScanPlugin.openError("Set Contrast", ScanLib
                        .getErrMsg(scanlibReturn));
                    getShell().dispose();

                }
                break;
            case (ConfigSettings.INVALID_INPUT):
                MessageDialog.openError(getShell(),
                    "configSettings.setContrast",
                    "Contrast ranges from -1000 to 1000\n"
                        + "Please enter a valid input value");
                return;
            case (ConfigSettings.NOCHANGE):
                break;
        }
        /* =================Set Contrast ================ */

        /* =================Set Plate Settings ================ */
        double nplates[][] = new double[maxPallets][4];
        readPlatesTextToArray(nplates); /* Reads Plate Config Settings */

        for (int plate = 0; plate < maxPallets; plate++) {
            int setPlateReturn =
                configSettings.setPallet(plate + 1, nplates[plate][0],
                    nplates[plate][1], nplates[plate][2], nplates[plate][3]);
            if (setPlateReturn == ConfigSettings.SUCCESS
                || setPlateReturn == ConfigSettings.CLEARDATA) {

                PalletScanCoordinates coords =
                    configSettings.getPallet(plate + 1);

                if (coords == null)
                    continue;

                int scanlibReturn =
                    ScanLib.getInstance().slConfigPlateFrame(plate + 1,
                        coords.left, coords.top, coords.right, coords.bottom);
                if (scanlibReturn == ScanLib.SC_SUCCESS) {
                    if (setPlateReturn == ConfigSettings.SUCCESS) {
                        platesToCalibrate.add(plate + 1);

                        /*
                         * Only calibrates to plate if: the text fields changed
                         * AND they are not all equal to zero AND
                         * slConfigPlateFrame returns SC_SUCCESS
                         */
                    }
                    else {
                        BioSampleScanPlugin.openError("Plate Settings", ScanLib
                            .getErrMsg(scanlibReturn));
                        getShell().dispose();
                    }
                }
            }
            else if (setPlateReturn == ConfigSettings.INVALID_INPUT) {
                MessageDialog.openError(getShell(), "configSettings.setPlate",
                    "Please enter a valid input");
                return;
            }

        }
        /* =================Set Plate Settings ================ */

        // reset our model since pallet set may have changed
        BioSampleScanPlugin plugin = BioSampleScanPlugin.getDefault();
        plugin.getPalletSetView().refresh();

        getShell().dispose();
        super.okPressed();
    }

    public List<Integer> getPlatesToCalibrate() {
        return platesToCalibrate;
    }
}
