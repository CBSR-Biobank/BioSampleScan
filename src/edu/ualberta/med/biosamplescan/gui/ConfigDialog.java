package edu.ualberta.med.biosamplescan.gui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.ualberta.med.biosamplescan.singleton.ConfigSettings;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scanlib.ScanLibFactory;

public class ConfigDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Button buttonCancle;
	private Button buttonConfig;
	private Text textDpi;
	private Text textBrightness;
	private Text textContrast;
	/*Plate Arrays*/
	private Group[] groups;
	private Text platesText[][];
	private Label labels[];

	public ConfigDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL);
			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.makeColumnsEqualWidth = true;
			SWTManager.registerResourceUser(dialogShell);
			groups = new Group[2 + ConfigSettings.PLATENUM + 1];
			platesText = new Text[ConfigSettings.PLATENUM + 1][4];//left,top,right,bottom
			labels = new Label[ConfigSettings.PLATENUM * 5 + 10 + 1];
			int label_it = 0;
			int groups_it = 0;

			dialogShell.setLayout(dialogShellLayout);
			dialogShell.setText("Configuration");
			dialogShell.setFont(SWTManager.getFont("Tahoma", 10, 1, false,
					false));
			{
				groups[++groups_it] = new Group(dialogShell, SWT.NONE);
				FillLayout group1Layout = new FillLayout(
						org.eclipse.swt.SWT.HORIZONTAL);
				GridData group1LData = new GridData();
				group1LData.widthHint = 421;
				group1LData.heightHint = 13;
				groups[groups_it].setLayoutData(group1LData);
				groups[groups_it].setLayout(group1Layout);
				groups[groups_it].setText("Scanner Settings");
				{
					labels[++label_it] = new Label(groups[groups_it], SWT.NONE);
					labels[label_it].setText("Dots Per Inch:");
				}
				{
					textDpi = new Text(groups[groups_it], SWT.NONE);
					textDpi.setTextLimit(3);
					textDpi.setText("300");
				}
				{
					labels[++label_it] = new Label(groups[groups_it], SWT.NONE);
					labels[label_it].setText("    Brightness:");
					labels[label_it].setBounds(12, 21, 58, 13);
				}
				{
					textBrightness = new Text(groups[groups_it], SWT.NONE);
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
					textContrast = new Text(groups[groups_it], SWT.NONE);
					textContrast.setText("0");
					textContrast.setTextLimit(5);
					textContrast.setBounds(153, 22, 35, 14);
				}
			}
			for (int plate = 0; plate < ConfigSettings.PLATENUM; plate++) {
				groups[++groups_it] = new Group(dialogShell, SWT.NONE);
				FillLayout group2Layout = new FillLayout(
						org.eclipse.swt.SWT.HORIZONTAL);
				groups[groups_it].setLayout(group2Layout);
				GridData group2LData = new GridData();
				group2LData.heightHint = 13;
				group2LData.horizontalAlignment = GridData.FILL;
				groups[groups_it].setLayoutData(group2LData);
				groups[groups_it].setText(String.format("Plate %d Position",
						plate + 1));
				{
					labels[++label_it] = new Label(groups[2 + plate], SWT.NONE);
					labels[label_it].setText("Top:");
				}
				{
					platesText[plate][1] = new Text(groups[2 + plate], SWT.NONE);
					platesText[plate][1].setText("0");
					platesText[plate][1].setTextLimit(6);
					platesText[plate][1].setOrientation(SWT.HORIZONTAL);
					platesText[plate][1].setDoubleClickEnabled(false);
				}
				{
					labels[++label_it] = new Label(groups[2 + plate], SWT.NONE);
					labels[label_it].setText("      Left:");
				}
				{
					platesText[plate][0] = new Text(groups[2 + plate], SWT.NONE);
					platesText[plate][0].setText("0");
					platesText[plate][0].setTextLimit(6);
				}
				{
					labels[5 + plate] = new Label(groups[2 + plate], SWT.NONE);
					labels[5 + plate].setText("   Bottom:");
				}
				{
					platesText[plate][3] = new Text(groups[2 + plate], SWT.NONE);
					platesText[plate][3].setText("0");
					platesText[plate][3].setTextLimit(6);
				}
				{
					labels[++label_it] = new Label(groups[2 + plate], SWT.NONE);
					labels[label_it].setText("    Right:");
				}
				{
					platesText[plate][2] = new Text(groups[2 + plate], SWT.NONE);
					platesText[plate][2].setText("0");
					platesText[plate][2].setTextLimit(6);
				}
			}
			{
				groups[++groups_it] = new Group(dialogShell, SWT.NONE);
				RowLayout group5Layout = new RowLayout(
						org.eclipse.swt.SWT.HORIZONTAL);
				groups[groups_it].setLayout(group5Layout);
				GridData group5LData = new GridData();
				groups[groups_it].setLayoutData(group5LData);
				{
					buttonCancle = new Button(groups[groups_it], SWT.PUSH
							| SWT.CENTER);
					buttonCancle.setText("Cancle");
					RowData button1LData = new RowData();
					buttonCancle.setLayoutData(button1LData);
					buttonCancle.setFont(SWTManager.getFont("Tahoma", 10, 0,
							false, false));
					buttonCancle.addMouseListener(new MouseAdapter() {
						public void mouseUp(MouseEvent evt) {
							buttonCancleMouseUp(evt);
						}
					});
				}
				{
					buttonConfig = new Button(groups[groups_it], SWT.PUSH
							| SWT.CENTER);
					buttonConfig.setText("Configure");
					buttonConfig.setFont(SWTManager.getFont("Tahoma", 10, 1,
							false, false));
					RowData buttonConfigLData = new RowData();
					buttonConfig.setLayoutData(buttonConfigLData);
					buttonConfig.addMouseListener(new MouseAdapter() {
						public void mouseUp(MouseEvent evt) {
							buttonConfigMouseUp(evt);
						}
					});
				}
				{
					labels[++label_it] = new Label(groups[groups_it],
							SWT.CENTER);
					RowData label1LData = new RowData();
					label1LData.width = 263;
					label1LData.height = 19;
					labels[label_it].setLayoutData(label1LData);
					labels[label_it]
							.setText("\tNote: Wia: bottom=height, right=width");
				}
			}
			this.loadFromConfigSettings();
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

	void readPlatesIntoArray(double plateArray[][]) {
		for (int plate = 0; plate < ConfigSettings.PLATENUM; plate++) {
			for (int side = 0; side < 4; side++) {
				plateArray[plate][side] = Double
						.valueOf(platesText[plate][side].getText());
			}
		}
	}

	private int loadFromConfigSettings() {
		ConfigSettings configSettings = ConfigSettings.getInstance();
		configSettings.loadFromFile();

		textDpi.setText(String.valueOf(configSettings.getDpi()));
		textBrightness.setText(String.valueOf(configSettings.getBrightness()));
		textContrast.setText(String.valueOf(configSettings.getContrast()));

		for (int plate = 0; plate < ConfigSettings.PLATENUM; plate++) {
			for (int side = 0; side < 4; side++) {
				platesText[plate][side].setText(String.valueOf(configSettings
						.getPlate(plate + 1)[side]));
			}
		}
		return 0;
	}

	private void buttonCancleMouseUp(MouseEvent evt) {
		dialogShell.dispose();
	}

	private void errorMsg(String Identifier, int code) {
		MessageDialog.openError(dialogShell, "Error", String.format(
				"%s\nReturned Error Code: %d\n", Identifier, code));
	}

	private void buttonConfigMouseUp(MouseEvent evt) {
		try {
			ConfigSettings configSettings = ConfigSettings.getInstance();
			int configSettingsReturn = configSettings.setDpi(textDpi.getText());
			switch (configSettingsReturn) {
				case (ConfigSettings.CS_SUCCESS):
					break;
				case (ConfigSettings.CS_FILE_ERROR):
					MessageDialog.openError(dialogShell,
							"configSettings.setDpi",
							"Could not find scanlib.ini file");
					dialogShell.dispose();
					return;
				case (ConfigSettings.CS_INVALID_INPUT):
					MessageDialog.openError(dialogShell,
							"configSettings.setDpi", "Dpi must be 300 or 600");
					dialogShell.dispose();
					return;
				case (ConfigSettings.CS_NOCHANGE):
					break;
			}
			configSettingsReturn = configSettings.setBrightness(textBrightness
					.getText());
			switch (configSettingsReturn) {
				case (ConfigSettings.CS_SUCCESS):
					int scanlibReturn = ScanLibFactory.getScanLib()
							.slConfigScannerBrightness(
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
							this
									.errorMsg(
											"Brightness: Could not find scanlib.ini file",
											scanlibReturn);
							dialogShell.dispose();
							return;
					}
					break;
				case (ConfigSettings.CS_INVALID_INPUT):
					MessageDialog.openError(dialogShell,
							"configSettings.setBrightness",
							"Brightness: Invalid Value");
					dialogShell.dispose();
					return;
				case (ConfigSettings.CS_NOCHANGE):
					break;
			}

			configSettingsReturn = configSettings.setContrast(textContrast
					.getText());
			switch (configSettingsReturn) {
				case (ConfigSettings.CS_SUCCESS):
					int scanlibReturn = ScanLibFactory.getScanLib()
							.slConfigScannerContrast(
									configSettings.getContrast());
					switch (scanlibReturn) {
						case (ScanLib.SC_SUCCESS):
							break;
						case (ScanLib.SC_INVALID_VALUE):
							this.errorMsg("Conrast: Invalid Value",
									scanlibReturn);
							dialogShell.dispose();
							return;

						case (ScanLib.SC_INI_FILE_ERROR):
							this
									.errorMsg(
											"Contrast: Could not find scanlib.ini file",
											scanlibReturn);
							dialogShell.dispose();
							return;
					}
					break;
				case (ConfigSettings.CS_INVALID_INPUT):
					MessageDialog.openError(dialogShell,
							"configSettings.setContrast",
							"Contrast: Invalid input");
					dialogShell.dispose();
					return;
				case (ConfigSettings.CS_NOCHANGE):
					break;
			}

			double nplates[][] = new double[ConfigSettings.PLATENUM][4];
			readPlatesIntoArray(nplates);

			for (int plate = 0; plate < ConfigSettings.PLATENUM; plate++) {
				int setPlateReturn = configSettings.setPlate(plate + 1,
						nplates[plate][0], nplates[plate][1],
						nplates[plate][2], nplates[plate][3]);
				if (setPlateReturn == ConfigSettings.CS_SUCCESS
						|| setPlateReturn == ConfigSettings.CS_CLEARDATA) {
					int scanlibReturn = ScanLibFactory.getScanLib()
							.slConfigPlateFrame(plate + 1,
									configSettings.getPlate(plate + 1)[0],
									configSettings.getPlate(plate + 1)[1],
									configSettings.getPlate(plate + 1)[2],
									configSettings.getPlate(plate + 1)[3]);
					switch (scanlibReturn) {
						case (ScanLib.SC_SUCCESS):
							break;
						case (ScanLib.SC_INI_FILE_ERROR):
							this
									.errorMsg(
											"ConfigPlateFrame: Could not find scanlib.ini file",
											scanlibReturn);
							dialogShell.dispose();
							return;
					}

					if (setPlateReturn == ConfigSettings.CS_SUCCESS) {
						scanlibReturn = ScanLibFactory.getScanLib()
								.slCalibrateToPlate(configSettings.getDpi(),
										plate + 1);
						switch (scanlibReturn) {
							case (ScanLib.SC_SUCCESS):
								MessageDialog
										.openInformation(
												dialogShell,
												"Successful Calibration",
												String
														.format(
																"Plate %d has been successfully setup",
																plate + 1));
								break;
							case (ScanLib.SC_INVALID_DPI):
								this.errorMsg("Calibratation: Invalid Dpi",
										scanlibReturn);
								dialogShell.dispose();
								return;
							case (ScanLib.SC_INVALID_PLATE_NUM):
								this.errorMsg(
										"Calibratation: Invalid Plate Number",
										scanlibReturn);
								dialogShell.dispose();
								return;

							case (ScanLib.SC_INI_FILE_ERROR):
								this
										.errorMsg(
												"Calibratation: Plate dimensions not found inside scanlib.ini",
												scanlibReturn);
								dialogShell.dispose();
								return;
							case (ScanLib.SC_CALIBRATOR_ERROR):
								this
										.errorMsg(
												"Calibration: Could not find 8 rows and 12 columns",
												scanlibReturn);
								dialogShell.dispose();
								return;
							case (ScanLib.SC_CALIBRATOR_NO_REGIONS):
								this
										.errorMsg(
												"menuConfigurationWidgetSelected, Calibratation",
												scanlibReturn);
								dialogShell.dispose();
								return;
							case (ScanLib.SC_FAIL):
								this.errorMsg("Calibratation: Failed to scan",
										scanlibReturn);
								dialogShell.dispose();
								return;

						}
					}
				}
				else if (setPlateReturn == ConfigSettings.CS_INVALID_INPUT) {
					MessageDialog.openError(dialogShell,
							"configSettings.setPlate", "Invalid Input");
					dialogShell.dispose();
					return;
				}

			}
			dialogShell.dispose();

		}
		catch (Exception e) {
			MessageDialog.openError(dialogShell, "Error", e.getMessage());
		}

	}
}
