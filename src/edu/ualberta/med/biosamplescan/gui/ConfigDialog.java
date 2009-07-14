package edu.ualberta.med.biosamplescan.gui;

import java.io.File;
import java.io.IOException;

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
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import edu.ualberta.med.scanlib.ScanLib;

public class ConfigDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Label label11;
	private Label label1;
	private Text textDpi;
	private Label label19;
	private Group group5;
	private Button buttonCancle;
	private Button buttonConfig;
	private Text textContrast;
	private Label label18;
	private Text textBrightness;
	private Label label2;
	private Group group1;
	private Label label5;
	private Text textRight3;
	private Label label17;
	private Text textBottom3;
	private Label label16;
	private Text textLeft3;
	private Label label15;
	private Text textTop3;
	private Label label14;
	private Group group4;
	private Text textRight2;
	private Label label12;
	private Text textBottom2;
	private Text textLeft2;
	private Label label10;
	private Text textTop2;
	private Label label9;
	private Group group3;
	private Text textRight1;
	private Label label7;
	private Text textBottom1;
	private Label label6;
	private Text textLeft1;
	private Label label4;
	private Text textTop1;
	private Group group2;

	public boolean cancledDialog = false;
	public int PLATENUM = 3;
	public int dpi = ScanLib.DPI_300;
	public int brightness = 0;
	public int contrast = 0;
	public double plates[][] = new double[PLATENUM][4];

	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			DebugDialog inst = new DebugDialog(shell, SWT.NULL);
			inst.open("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ConfigDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open(String ConfigMessage) {
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
				group1.setText("Scanning Settings");
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
					label1.setText("\tNote: Twain_bottom= Wia_top+Wia_bottom");
				}
			}
			try {
				int iniConfigReturn = this.loadConfigfromIni();
				if (iniConfigReturn != 0) {
					System.exit(iniConfigReturn);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-30);
			}
			cancledDialog = false;

			if (ConfigMessage.equals("LOAD SETTINGS")) {
				return;

			} else {
				dialogShell.layout();
				dialogShell.pack();
				dialogShell.setLocation(getParent().toDisplay(100, 100));
				dialogShell.open();
				Display display = dialogShell.getDisplay();

				while (!dialogShell.isDisposed()) {
					if (!display.readAndDispatch())
						display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	int loadConfigfromIni() {
		try {
			Wini ini = new Wini(new File("scanlib.ini"));
			try {
				textBrightness.setText(ini.get("scanner", "brightness"));
			} catch (IllegalArgumentException e) {
				return -1;
			}
			try {
				textContrast.setText(ini.get("scanner", "contrast"));
			} catch (IllegalArgumentException e) {
				return -2;
			}
			try {
				textLeft1.setText(ini.get("plate-1", "left"));
			} catch (IllegalArgumentException e) {
				return -3;
			}
			try {
				textTop1.setText(ini.get("plate-1", "top"));
			} catch (IllegalArgumentException e) {
				return -4;
			}
			try {
				textBottom1.setText(ini.get("plate-1", "bottom"));
			} catch (IllegalArgumentException e) {
				return -5;
			}
			try {
				textRight1.setText(ini.get("plate-1", "right"));
			} catch (IllegalArgumentException e) {
				return -6;
			}
			try {
				textLeft2.setText(ini.get("plate-2", "left"));
			} catch (IllegalArgumentException e) {
				return -7;
			}
			try {
				textTop2.setText(ini.get("plate-2", "top"));
			} catch (IllegalArgumentException e) {
				return -8;
			}
			try {
				textBottom2.setText(ini.get("plate-2", "bottom"));
			} catch (IllegalArgumentException e) {
				return -9;
			}
			try {
				textRight2.setText(ini.get("plate-2", "right"));
			} catch (IllegalArgumentException e) {
				return -10;
			}
			try {
				textLeft3.setText(ini.get("plate-3", "left"));
			} catch (IllegalArgumentException e) {
				return -11;
			}
			try {
				textTop3.setText(ini.get("plate-3", "top"));
			} catch (IllegalArgumentException e) {
				return -12;
			}
			try {
				textBottom3.setText(ini.get("plate-3", "bottom"));
			} catch (IllegalArgumentException e) {
				return -13;
			}
			try {
				textRight3.setText(ini.get("plate-3", "right"));
			} catch (IllegalArgumentException e) {
				return -14;
			}
			dpi = ScanLib.DPI_300;
			brightness = Integer.parseInt(textBrightness.getText());
			contrast = Integer.parseInt(textContrast.getText());
			plates[0][0] = Double.valueOf(textTop1.getText());
			plates[0][1] = Double.valueOf(textLeft1.getText());
			plates[0][2] = Double.valueOf(textBottom1.getText());
			plates[0][3] = Double.valueOf(textRight1.getText());
			plates[1][0] = Double.valueOf(textTop2.getText());
			plates[1][1] = Double.valueOf(textLeft2.getText());
			plates[1][2] = Double.valueOf(textBottom2.getText());
			plates[1][3] = Double.valueOf(textRight2.getText());
			plates[2][0] = Double.valueOf(textTop3.getText());
			plates[2][1] = Double.valueOf(textLeft3.getText());
			plates[2][2] = Double.valueOf(textBottom3.getText());
			plates[2][3] = Double.valueOf(textRight3.getText());

		} catch (InvalidFileFormatException e) {
			e.printStackTrace();
			return -15;
		} catch (IOException e) {
			e.printStackTrace();
			return -16;
		}
		return 0;
	}

	private void buttonCancleMouseUp(MouseEvent evt) {
		cancledDialog = true;
		dialogShell.dispose();
	}

	private double concatDouble(double in, double lower, double upper) {// inclusive
		if (in < lower) {
			in = lower;
		}
		if (in > upper) {
			in = upper;
		}
		return in;
	}

	private int concatInt(int in, int lower, int upper) {// inclusive
		if (in < lower) {
			in = lower;
		}
		if (in > upper) {
			in = upper;
		}
		return in;
	}

	private void buttonConfigMouseUp(MouseEvent evt) {
		try {

			double nplates[][] = new double[PLATENUM][4];
			nplates[0][0] = Double.valueOf(textTop1.getText());
			nplates[0][1] = Double.valueOf(textLeft1.getText());
			nplates[0][2] = Double.valueOf(textBottom1.getText());
			nplates[0][3] = Double.valueOf(textRight1.getText());
			nplates[1][0] = Double.valueOf(textTop2.getText());
			nplates[1][1] = Double.valueOf(textLeft2.getText());
			nplates[1][2] = Double.valueOf(textBottom2.getText());
			nplates[1][3] = Double.valueOf(textRight2.getText());
			nplates[2][0] = Double.valueOf(textTop3.getText());
			nplates[2][1] = Double.valueOf(textLeft3.getText());
			nplates[2][2] = Double.valueOf(textBottom3.getText());
			nplates[2][3] = Double.valueOf(textRight3.getText());

			if ((int) Integer.parseInt(textDpi.getText()) != ScanLib.DPI_300
					&& (int) Integer.parseInt(textDpi.getText()) != ScanLib.DPI_600) {
				MessageDialog.openError(dialogShell, "Error",
						"DPI:\nMust be 300 or 600\n");
				System.out.printf("%d\n", Integer.parseInt(textDpi.getText()));
				return;
			} else {
				dpi = Integer.parseInt(textDpi.getText());
			}

			if ((int) Integer.parseInt(textBrightness.getText()) != concatInt(
					(int) Integer.parseInt(textBrightness.getText()), -1000,
					1000)) {
				MessageDialog.openError(dialogShell, "Error",
						"Brightness:\nRanges from -1000 to 1000\n");
				return;
			} else {
				brightness = Integer.parseInt(textBrightness.getText());
			}

			if ((int) Integer.parseInt(textContrast.getText()) != concatInt(
					(int) Integer.parseInt(textContrast.getText()), -1000, 1000)) {
				MessageDialog.openError(dialogShell, "Error",
						"Contrast:\nRanges from -1000 to 1000\n");
				return;
			} else {
				contrast = Integer.parseInt(textContrast.getText());
			}

			for (int plate = 0; plate < PLATENUM; plate++) {
				/*
				 * if (nplates[plate][0] > nplates[plate][2]) {// top > bottom
				 * MessageDialog.openError(dialogShell, "Error",
				 * String.format("Plate %d:\n" + "Top > Bottom\n", plate + 1));
				 * return; }
				 */// Wia driver adds bottom to top.
				if (nplates[plate][1] > nplates[plate][3]) {// left > right
					MessageDialog.openError(dialogShell, "Error",
							String.format("Plate %d:\n" + "Left > Right\n",
									plate + 1));
					return;
				}
				for (int i = 0; i < 4; i++) {
					if (nplates[plate][i] < 0) {
						MessageDialog.openError(dialogShell, "Error", String
								.format("Plate %d:\n" + "Negative Value\n",
										plate + 1));
						return;
					}
				}

			}
			for (int plate = 0; plate < PLATENUM; plate++) {
				for (int i = 0; i < 4; i++) {
					plates[plate][i] = nplates[plate][i];
				}
			}
			dialogShell.dispose();
		} catch (Exception e) {
			MessageDialog.openError(dialogShell, "Error", e.getMessage());
		}

	}
}
