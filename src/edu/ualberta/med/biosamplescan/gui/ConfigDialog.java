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
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.View;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.Main;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scanlib.ScanLibFactory;

public class ConfigDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Label label11;
	private Label label20;
	private Label label13;
	private Label label8;
	private Label label3;
	private Group group6;
	private Label label1;
	private Label label19;
	private Group group5;
	private Label label18;
	private Label label2;
	private Group group1;
	private Label label5;
	private Label label17;
	private Label label16;
	private Label label15;
	private Label label14;
	private Group group4;
	private Label label12;
	private Label label10;
	private Label label9;
	private Group group3;
	private Label label7;
	private Label label6;
	private Label label4;
	private Group group2;
	private Button buttonCancle;
	private Button buttonConfig;
	private Text textDpi;
	private Text textBrightness;
	private Text textContrast;
	private Text textTop1;
	private Text textBottom1;
	private Text textLeft1;
	private Text textRight1;
	private Text textTop2;
	private Text textBottom2;
	private Text textLeft2;
	private Text textRight2;
	private Text textTop3;
	private Text textBottom3;
	private Text textLeft3;
	private Text textRight3;
	private Text textRight4;
	private Text textTop4;
	private Text textBottom4;
	private Text textLeft4;

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

			{
				SWTManager.registerResourceUser(dialogShell);
			}

			dialogShell.setLayout(dialogShellLayout);
			dialogShell.setText("Configuration");
			dialogShell.setFont(SWTManager.getFont("Tahoma", 10, 1, false,
					false));
			{
				group1 = new Group(dialogShell, SWT.NONE);
				FillLayout group1Layout = new FillLayout(
						org.eclipse.swt.SWT.HORIZONTAL);
				GridData group1LData = new GridData();
				group1LData.widthHint = 421;
				group1LData.heightHint = 13;
				group1.setLayoutData(group1LData);
				group1.setLayout(group1Layout);
				group1.setText("Scanner Settings");
				{
					label19 = new Label(group1, SWT.NONE);
					label19.setText("Dots Per Inch:");
				}
				{
					textDpi = new Text(group1, SWT.NONE);
					textDpi.setTextLimit(3);
					textDpi.setText("300");
				}
				{
					label2 = new Label(group1, SWT.NONE);
					label2.setText("    Brightness:");
					label2.setBounds(12, 21, 58, 13);
				}
				{
					textBrightness = new Text(group1, SWT.NONE);
					textBrightness.setText("0");
					textBrightness.setTextLimit(5);
					textBrightness.setBounds(68, 22, 33, 14);
				}
				{
					label18 = new Label(group1, SWT.NONE);
					label18.setText("       Contrast:");
					label18.setBounds(105, 22, 49, 13);
				}
				{
					textContrast = new Text(group1, SWT.NONE);
					textContrast.setText("0");
					textContrast.setTextLimit(5);
					textContrast.setBounds(153, 22, 35, 14);
				}
			}
			{
				group2 = new Group(dialogShell, SWT.NONE);
				FillLayout group2Layout = new FillLayout(
						org.eclipse.swt.SWT.HORIZONTAL);
				group2.setLayout(group2Layout);
				GridData group2LData = new GridData();
				group2LData.heightHint = 13;
				group2LData.horizontalAlignment = GridData.FILL;
				group2.setLayoutData(group2LData);
				group2.setText("Plate 1 Position");
				{
					label5 = new Label(group2, SWT.NONE);
					label5.setText("Top:");
				}
				{
					textTop1 = new Text(group2, SWT.NONE);
					textTop1.setText("0");
					textTop1.setTextLimit(6);
					textTop1.setOrientation(SWT.HORIZONTAL);
					textTop1.setDoubleClickEnabled(false);
				}
				{
					label4 = new Label(group2, SWT.NONE);
					label4.setText("      Left:");
				}
				{
					textLeft1 = new Text(group2, SWT.NONE);
					textLeft1.setText("0");
					textLeft1.setTextLimit(6);
				}
				{
					label6 = new Label(group2, SWT.NONE);
					label6.setText("   Bottom:");
				}
				{
					textBottom1 = new Text(group2, SWT.NONE);
					textBottom1.setText("0");
					textBottom1.setTextLimit(6);
				}
				{
					label7 = new Label(group2, SWT.NONE);
					label7.setText("    Right:");
				}
				{
					textRight1 = new Text(group2, SWT.NONE);
					textRight1.setText("0");
					textRight1.setTextLimit(6);
				}
			}
			{
				group3 = new Group(dialogShell, SWT.NONE);
				FillLayout group3Layout = new FillLayout(
						org.eclipse.swt.SWT.HORIZONTAL);
				group3.setLayout(group3Layout);
				GridData group3LData = new GridData();
				group3LData.widthHint = 421;
				group3LData.heightHint = 13;
				group3.setLayoutData(group3LData);
				group3.setText("Plate 2 Position");
				{
					label9 = new Label(group3, SWT.NONE);
					label9.setText("Top:");
					label9.setBounds(67, 17, 22, 13);
				}
				{
					textTop2 = new Text(group3, SWT.NONE);
					textTop2.setText("0");
					textTop2.setTextLimit(6);
					textTop2.setBounds(92, 17, 28, 14);
				}
				{
					label10 = new Label(group3, SWT.NONE);
					label10.setText("      Left:");
					label10.setBounds(119, 17, 35, 13);
				}
				{
					textLeft2 = new Text(group3, SWT.NONE);
					textLeft2.setText("0");
					textLeft2.setTextLimit(6);
					textLeft2.setBounds(157, 17, 27, 14);
				}
				{
					label11 = new Label(group3, SWT.NONE);
					label11.setText("   Bottom:");
					label11.setBounds(184, 17, 47, 13);
				}
				{
					textBottom2 = new Text(group3, SWT.NONE);
					textBottom2.setText("0");
					textBottom2.setTextLimit(6);
					textBottom2.setBounds(234, 17, 27, 14);
				}
				{
					label12 = new Label(group3, SWT.NONE);
					label12.setText("    Right:");
					label12.setBounds(261, 17, 41, 13);
				}
				{
					textRight2 = new Text(group3, SWT.NONE);
					textRight2.setText("0");
					textRight2.setTextLimit(6);
					textRight2.setBounds(305, 17, 28, 14);
				}
			}
			{
				group4 = new Group(dialogShell, SWT.NONE);
				FillLayout group4Layout = new FillLayout(
						org.eclipse.swt.SWT.HORIZONTAL);
				group4.setLayout(group4Layout);
				GridData group4LData = new GridData();
				group4LData.widthHint = 421;
				group4LData.heightHint = 13;
				group4.setLayoutData(group4LData);
				group4.setText("Plate 3 Position");
				{
					label14 = new Label(group4, SWT.NONE);
					label14.setText("Top:");
					label14.setBounds(67, 17, 22, 13);
				}
				{
					textTop3 = new Text(group4, SWT.NONE);
					textTop3.setText("0");
					textTop3.setTextLimit(6);
					textTop3.setBounds(92, 17, 28, 14);
				}
				{
					label15 = new Label(group4, SWT.NONE);
					label15.setText("      Left:");
					label15.setBounds(119, 17, 35, 13);
				}
				{
					textLeft3 = new Text(group4, SWT.NONE);
					textLeft3.setText("0");
					textLeft3.setTextLimit(6);
					textLeft3.setBounds(157, 17, 28, 14);
				}
				{
					label16 = new Label(group4, SWT.NONE);
					label16.setText("   Bottom:");
					label16.setBounds(184, 17, 47, 13);
				}
				{
					textBottom3 = new Text(group4, SWT.NONE);
					textBottom3.setText("0");
					textBottom3.setTextLimit(6);
					textBottom3.setBounds(234, 17, 27, 14);
				}
				{
					label17 = new Label(group4, SWT.NONE);
					label17.setText("    Right:");
					label17.setBounds(261, 17, 41, 13);
				}
				{
					textRight3 = new Text(group4, SWT.NONE);
					textRight3.setText("0");
					textRight3.setTextLimit(6);
					textRight3.setBounds(305, 17, 27, 14);
				}
			}
			{
				group6 = new Group(dialogShell, SWT.NONE);
				FillLayout group6Layout = new FillLayout(
						org.eclipse.swt.SWT.HORIZONTAL);
				group6.setLayout(group6Layout);
				GridData group6LData = new GridData();
				group6LData.widthHint = 421;
				group6LData.heightHint = 13;
				group6.setLayoutData(group6LData);
				group6.setText("Plate 4 Position");
				{
					label3 = new Label(group6, SWT.NONE);
					label3.setText("Top:");
					label3.setBounds(67, 17, 22, 13);
				}
				{
					textTop4 = new Text(group6, SWT.NONE);
					textTop4.setText("0");
					textTop4.setTextLimit(6);
					textTop4.setBounds(92, 17, 28, 14);
				}
				{
					label8 = new Label(group6, SWT.NONE);
					label8.setText("      Left:");
					label8.setBounds(119, 17, 35, 13);
				}
				{
					textLeft4 = new Text(group6, SWT.NONE);
					textLeft4.setText("0");
					textLeft4.setTextLimit(6);
					textLeft4.setBounds(157, 17, 28, 14);
				}
				{
					label13 = new Label(group6, SWT.NONE);
					label13.setText("   Bottom:");
					label13.setBounds(184, 17, 47, 13);
				}
				{
					textBottom4 = new Text(group6, SWT.NONE);
					textBottom4.setText("0");
					textBottom4.setTextLimit(6);
					textBottom4.setBounds(234, 17, 27, 14);
				}
				{
					label20 = new Label(group6, SWT.NONE);
					label20.setText("    Right:");
					label20.setBounds(261, 17, 41, 13);
				}
				{
					textRight4 = new Text(group6, SWT.NONE);
					textRight4.setText("0");
					textRight4.setTextLimit(6);
					textRight4.setBounds(305, 17, 27, 14);
				}
			}
			{
				group5 = new Group(dialogShell, SWT.NONE);
				RowLayout group5Layout = new RowLayout(
						org.eclipse.swt.SWT.HORIZONTAL);
				group5.setLayout(group5Layout);
				GridData group5LData = new GridData();
				group5.setLayoutData(group5LData);
				{
					buttonCancle = new Button(group5, SWT.PUSH | SWT.CENTER);
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
					buttonConfig = new Button(group5, SWT.PUSH | SWT.CENTER);
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
					label1 = new Label(group5, SWT.CENTER);
					RowData label1LData = new RowData();
					label1LData.width = 263;
					label1LData.height = 19;
					label1.setLayoutData(label1LData);
					label1.setText("\tNote: Wia: bottom=height, right=width");
				}
			}
			this.loadConfigSettings();
			dialogShell.layout();
			dialogShell.pack();
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();

			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void readPlatesIntoArray(double plateArray[][]) {
		plateArray[0][0] = Double.valueOf(textLeft1.getText());
		plateArray[0][1] = Double.valueOf(textTop1.getText());
		plateArray[0][2] = Double.valueOf(textRight1.getText());
		plateArray[0][3] = Double.valueOf(textBottom1.getText());
		plateArray[1][0] = Double.valueOf(textLeft2.getText());
		plateArray[1][1] = Double.valueOf(textTop2.getText());
		plateArray[1][2] = Double.valueOf(textRight2.getText());
		plateArray[1][3] = Double.valueOf(textBottom2.getText());
		plateArray[2][0] = Double.valueOf(textLeft3.getText());
		plateArray[2][1] = Double.valueOf(textTop3.getText());
		plateArray[2][2] = Double.valueOf(textRight3.getText());
		plateArray[2][3] = Double.valueOf(textBottom3.getText());
		plateArray[3][0] = Double.valueOf(textLeft4.getText());
		plateArray[3][1] = Double.valueOf(textTop4.getText());
		plateArray[3][2] = Double.valueOf(textRight4.getText());
		plateArray[3][3] = Double.valueOf(textBottom4.getText());
	}

	public int loadConfigSettings() {
		ConfigSettings configSettings = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getConfigSettings();

		configSettings.loadFromFile();

		textDpi.setText(String.valueOf(configSettings.getDpi()));
		textBrightness.setText(String.valueOf(configSettings.getBrightness()));
		textContrast.setText(String.valueOf(configSettings.getContrast()));

		textLeft1.setText(String.valueOf(configSettings.getPlates(1)[0]));
		textTop1.setText(String.valueOf(configSettings.getPlates(1)[1]));
		textRight1.setText(String.valueOf(configSettings.getPlates(1)[2]));
		textBottom1.setText(String.valueOf(configSettings.getPlates(1)[3]));

		textLeft2.setText(String.valueOf(configSettings.getPlates(2)[0]));
		textTop2.setText(String.valueOf(configSettings.getPlates(2)[1]));
		textRight2.setText(String.valueOf(configSettings.getPlates(2)[2]));
		textBottom2.setText(String.valueOf(configSettings.getPlates(2)[3]));

		textLeft3.setText(String.valueOf(configSettings.getPlates(3)[0]));
		textTop3.setText(String.valueOf(configSettings.getPlates(3)[1]));
		textRight3.setText(String.valueOf(configSettings.getPlates(3)[2]));
		textBottom3.setText(String.valueOf(configSettings.getPlates(3)[3]));

		textLeft4.setText(String.valueOf(configSettings.getPlates(4)[0]));
		textTop4.setText(String.valueOf(configSettings.getPlates(4)[1]));
		textRight4.setText(String.valueOf(configSettings.getPlates(4)[2]));
		textBottom4.setText(String.valueOf(configSettings.getPlates(4)[3]));

		return 0;
	}

	private void buttonCancleMouseUp(MouseEvent evt) {
		dialogShell.dispose();
	}

	private void buttonConfigMouseUp(MouseEvent evt) {
		try {
			ConfigSettings configSettings = ((View) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActivePart())
					.getConfigSettings();

			ScanLib scanlib = ((View) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActivePart())
					.getScanLib();

			Main main = ((View) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActivePart())
					.getMain();

			int configSettingsReturn = configSettings.setDpi(textDpi.getText());
			if (configSettingsReturn == ConfigSettings.CS_SUCCESS) {
			} else {
				if (configSettingsReturn != ConfigSettings.CS_NOCHANGE) {
					MessageDialog.openError(dialogShell,
							"configSettings.setDpi", "Invalid input");
					dialogShell.dispose();
				}
			}
			configSettingsReturn = configSettings.setBrightness(textBrightness
					.getText());
			if (configSettingsReturn == ConfigSettings.CS_SUCCESS) {
				int scanlibReturn = scanlib
						.slConfigScannerBrightness(configSettings
								.getBrightness());
				switch (scanlibReturn) {
				case (ScanLib.SC_SUCCESS):
					break;
				case (ScanLib.SC_INVALID_VALUE):
					main.errorMsg(
							"menuConfigurationWidgetSelected, Brightness ",
							scanlibReturn);

				case (ScanLib.SC_INI_FILE_ERROR):
					main.errorMsg(
							"menuConfigurationWidgetSelected, Brightness ",
							scanlibReturn);
				}
			} else {
				if (configSettingsReturn != ConfigSettings.CS_NOCHANGE) {
					MessageDialog.openError(dialogShell,
							"configSettings.setBrightness", "Invalid input");
					dialogShell.dispose();
				}
			}

			configSettingsReturn = configSettings.setContrast(textContrast
					.getText());
			if (configSettingsReturn == ConfigSettings.CS_SUCCESS) {
				int scanlibReturn = scanlib
						.slConfigScannerContrast(configSettings.getContrast());
				switch (scanlibReturn) {
				case (ScanLib.SC_SUCCESS):
					break;
				case (ScanLib.SC_INVALID_VALUE):
					main.errorMsg("menuConfigurationWidgetSelected, Contrast ",
							scanlibReturn);

				case (ScanLib.SC_INI_FILE_ERROR):
					main.errorMsg("menuConfigurationWidgetSelected, Contrast ",
							scanlibReturn);
				}
			} else {
				if (configSettingsReturn != ConfigSettings.CS_NOCHANGE) {
					MessageDialog.openError(dialogShell,
							"configSettings.setContrast", "Invalid input");
					dialogShell.dispose();
				}
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
									configSettings.getPlates(plate + 1)[0],
									configSettings.getPlates(plate + 1)[1],
									configSettings.getPlates(plate + 1)[2],
									configSettings.getPlates(plate + 1)[3]);
					switch (scanlibReturn) {
					case (ScanLib.SC_SUCCESS):
						break;
					case (ScanLib.SC_FAIL):
						main
								.errorMsg(
										"menuConfigurationWidgetSelected, slConfigPlateFrame",
										scanlibReturn);
						return;
					}

					if (setPlateReturn == ConfigSettings.CS_SUCCESS) {
						scanlibReturn = scanlib.slCalibrateToPlate(
								ScanLib.DPI_300, plate + 1);
						switch (scanlibReturn) {
						case (ScanLib.SC_SUCCESS):
							break;
						case (ScanLib.SC_INVALID_IMAGE):
							main
									.errorMsg(
											"menuConfigurationWidgetSelected, Calibratation",
											scanlibReturn);
						}
					}
				} 
//				else { //TODO left off here
//					MessageDialog.openError(dialogShell,
//							"configSettings.setPlate", "Invalid Input");
//					dialogShell.dispose();
//					return;
//				}

			}
			dialogShell.dispose();

		} catch (Exception e) {
			MessageDialog.openError(dialogShell, "Error", e.getMessage());
		}

	}
}
