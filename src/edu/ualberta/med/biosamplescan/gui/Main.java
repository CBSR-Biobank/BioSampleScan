package edu.ualberta.med.biosamplescan.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import edu.ualberta.med.scanlib.ScanCell;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scanlib.ScanLibFactory;

public class Main extends org.eclipse.swt.widgets.Composite {
	private Menu mainmenu;
	private Menu menu1;
	private Menu menu5;
	private MenuItem menuSource;
	private MenuItem menuScanner;
	private MenuItem menuQuit;
	private MenuItem menuSaveCvsAs;
	private MenuItem menuSaveSelected;
	private Button loadFromFile;
	private Button reScanPlateBtn;
	private MenuItem menuSetMode;

	private Menu menu3;
	private MenuItem menuNew;
	private MenuItem menuAutoSaving;
	private MenuItem menuOptions;
	private MenuItem menuScanPlateFile;
	private MenuItem menuScanImageFile;
	private MenuItem menuSaveSelectd;

	private Button scanPlateBtn;
	private Button clearPlateBtn;
	private Menu menu2;
	private MenuItem menuConfiguration;

	private static int MAXPLATES = 4;

	private MenuItem[] menuPlates = new MenuItem[MAXPLATES];
	private Button[] plateBtn = new Button[MAXPLATES];
	private MenuItem[] menuSaveBarcodes = new MenuItem[MAXPLATES];
	private Table[] tables = new Table[MAXPLATES];
	private TableColumn[][] tableColumns = new TableColumn[MAXPLATES][MAXPLATES * 13];
	private TableItem[][] tableItems = new TableItem[MAXPLATES][MAXPLATES * 8];

	private Menu menu4;
	private MenuItem menuSaveBarcode;
	private MenuItem sep6;
	private MenuItem filemenu;

	private String lastSaveSelectLocation;
	private ScanLib scanlib = ScanLibFactory.getScanLib();
	DebugDialog debugDialog = new DebugDialog(getShell(), SWT.NONE);
	ConfigDialog configDialog = new ConfigDialog(getShell(), SWT.NONE);
	ProcessingDialog processingDialog = new ProcessingDialog(getShell(),
			SWT.NONE);

	{
		SWTManager.registerResourceUser(this);
	}

	public void main(String[] args) {
		showGUI();
	}

	public void showGUI() {
	}

	public Main(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(null);
			{
				mainmenu = new Menu(getShell(), SWT.BAR);
				getShell().setMenuBar(mainmenu);
				{
					filemenu = new MenuItem(mainmenu, SWT.CASCADE);
					filemenu.setText("File");
					{
						menu1 = new Menu(filemenu);
						filemenu.setMenu(menu1);
						{
							menuSaveSelectd = new MenuItem(menu1, SWT.CASCADE);
							menuSaveSelectd.setText("Save Selected Barcodes");
							menuSaveSelectd
									.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(
												SelectionEvent evt) {
											menuSaveSelectdWidgetSelected(evt);
										}
									});
						}
						{

							sep6 = new MenuItem(menu1, SWT.SEPARATOR);
							sep6.setText("sep6");
						}
						{
							menuSaveBarcode = new MenuItem(menu1, SWT.CASCADE);
							menuSaveBarcode.setText("Save Barcode...");
							{
								menu4 = new Menu(menuSaveBarcode);
								menuSaveBarcode.setMenu(menu4);

								menuSaveBarcodes = new MenuItem[MAXPLATES];
								for (int i = 0; i < MAXPLATES; i++) {
									menuSaveBarcodes[i] = new MenuItem(menu4,
											SWT.CASCADE);
									menuSaveBarcodes[i].setText(String.format(
											"From Plate %d", i + 1));
									menuSaveBarcodes[i]
											.addSelectionListener(new SelectionAdapter() {
												public void widgetSelected(
														SelectionEvent evt) {
													menuSaveBarcodeWidgetSelected(evt);
												}
											});
								}

							}
						}
						{
							menuSaveCvsAs = new MenuItem(menu1, SWT.CASCADE);
							menuSaveCvsAs.setText("Save All Barcodes...");
							menuSaveCvsAs
									.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(
												SelectionEvent evt) {
											menuSaveCvsAsWidgetSelected(evt);
										}
									});
						}

						{

							menuSaveSelected = new MenuItem(menu1, SWT.CASCADE);
							menuSaveSelected
									.setText("Save Selected Barcodes...");
							menuSaveSelected
									.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(
												SelectionEvent evt) {
											menuSaveSelectedWidgetSelected(evt);
										}
									});
						}
						{
							new MenuItem(menu1, SWT.SEPARATOR);
						}
						{
							menuNew = new MenuItem(menu1, SWT.CASCADE);
							menuNew.setText("Clear All Tables");
							menuNew
									.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(
												SelectionEvent evt) {
											menuNewWidgetSelected(evt);
										}
									});
						}
						{
							menuQuit = new MenuItem(menu1, SWT.CASCADE);
							menuQuit.setText("Quit");
							menuQuit
									.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(
												SelectionEvent evt) {
											menuQuitWidgetSelected(evt);
										}
									});

						}
					}
				}
				{
					menuOptions = new MenuItem(mainmenu, SWT.CASCADE);
					menuOptions.setText("Options");
					{
						menu2 = new Menu(menuOptions);
						menuOptions.setMenu(menu2);
						{
							menuAutoSaving = new MenuItem(menu2, SWT.CASCADE);
							menuAutoSaving.setText("Auto Saving");
							menuAutoSaving
									.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(
												SelectionEvent evt) {
											menuAutoSavingWidgetSelected(evt);
										}
									});
						}
						{
							menuSetMode = new MenuItem(menu2, SWT.CASCADE);
							menuSetMode.setText("Plate Mode");
							menuSetMode
									.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(
												SelectionEvent evt) {
											menuSetModeWidgetSelected(evt);
										}
									});
						}
						{
							new MenuItem(menu2, SWT.SEPARATOR);
						}
						{
							menuConfiguration = new MenuItem(menu2, SWT.CASCADE);
							menuConfiguration.setText("Scanning");
							menuConfiguration
									.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(
												SelectionEvent evt) {
											menuConfigurationWidgetSelected(evt);
										}
									});
						}
					}
				}
				{
					menuScanner = new MenuItem(mainmenu, SWT.CASCADE);
					menuScanner.setText("Scanner");
					{
						menu5 = new Menu(menuScanner);
						menuScanner.setMenu(menu5);
						{
							menuSource = new MenuItem(menu5, SWT.CASCADE);
							menuSource.setText("Select Source");
							menuSource
									.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(
												SelectionEvent evt) {
											menuSourceWidgetSelected(evt);
										}
									});
						}
						{
							new MenuItem(menu5, SWT.SEPARATOR);
						}
						{
							menuScanImageFile = new MenuItem(menu5, SWT.CASCADE);
							menuScanImageFile.setText("Scan Image to File...");
							menuScanImageFile
									.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(
												SelectionEvent evt) {
											menuScanImageFileWidgetSelected(evt);
										}
									});
						}
						{
							menuScanPlateFile = new MenuItem(menu5, SWT.CASCADE);
							menuScanPlateFile.setText("Scan Plate to File...");
							{
								menu3 = new Menu(menuScanPlateFile);
								menuScanPlateFile.setMenu(menu3);

								for (int i = 0; i < MAXPLATES; i++) {
									menuPlates[i] = new MenuItem(menu3,
											SWT.CASCADE);
									menuPlates[i].setText(String.format(
											"Plate %d", i + 1));
									menuPlates[i]
											.addSelectionListener(new SelectionAdapter() {
												public void widgetSelected(
														SelectionEvent evt) {
													menuPlateScanToFile(evt);
												}
											});
								}
							}
						}
					}
				}
			}
			this.layout();
			pack();
			{

				for (int table = 0; table < MAXPLATES; table++) {

					tables[table] = new Table(this, SWT.FULL_SELECTION
							| SWT.EMBEDDED | SWT.V_SCROLL | SWT.BORDER);
					tables[table].setLinesVisible(true);
					tables[table].setHeaderVisible(true);
					tables[table].setBounds(5, 63 + table * 140,
							5 + 20 + 68 * 12, 140);
					tables[table].setFont(SWTManager.getFont("Calibri", 7, 0,
							false, false));

					tableColumns[table][0] = new TableColumn(tables[table],
							SWT.NONE);
					tableColumns[table][0].setWidth(20);
					tableColumns[table][0].setResizable(false);
					for (int i = 0; i < 12; i++) {
						tableColumns[table][i + 1] = new TableColumn(
								tables[table], SWT.NONE);
						tableColumns[table][i + 1].setText(String.format("%d",
								i + 1));
						tableColumns[table][i + 1].setWidth(68);
					}
					for (int i = 0; i < 8; i++) {
						tableItems[table][i] = new TableItem(tables[table],
								SWT.NONE);
						tableItems[table][i].setText(Character
								.toString((char) (65 + i)));
						if (i % 2 != 0) {
							tableItems[table][i].setBackground(new Color(
									getDisplay(), 200, 200, 200));
						} else if (i % 3 == 0) {
							tableItems[table][i].setBackground(new Color(
									getDisplay(), 170, 170, 170));
						} else {
							tableItems[table][i].setBackground(new Color(
									getDisplay(), 230, 230, 230));
						}

					}
				}
			}

			{
				scanPlateBtn = new Button(this, SWT.PUSH | SWT.CENTER);
				scanPlateBtn.setText("Scan Plate(s)");
				scanPlateBtn.setBounds(698, 6, 90, 40);
				scanPlateBtn.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						scanPlateBtnWidgetSelected(evt, false);
					}
				});
			}
			{
				clearPlateBtn = new Button(this, SWT.PUSH | SWT.CENTER);
				clearPlateBtn.setText("Clear Table(s)");
				clearPlateBtn.setBounds(488, 6, 90, 40);
				clearPlateBtn.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						clearPlateBtnWidgetSelected(evt);

					}
				});
			}
			for (int i = 0; i < MAXPLATES; i++) {
				plateBtn[i] = new Button(this, SWT.CHECK | SWT.LEFT);
				plateBtn[i].setText(String.format("Plate %d", i + 1));
				plateBtn[i].setBounds(20 + 63 * i, 22, 63, 18);
				plateBtn[i].setSelection(true);
			}
			{
				reScanPlateBtn = new Button(this, SWT.PUSH | SWT.CENTER);
				reScanPlateBtn.setText("Re-Scan Plate(s)");
				reScanPlateBtn.setBounds(596, 6, 90, 40);
				reScanPlateBtn.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						scanPlateBtnWidgetSelected(evt, true);
					}
				});
			}
			{
				loadFromFile = new Button(this, SWT.PUSH | SWT.CENTER);
				loadFromFile.setText("Load From File");
				loadFromFile.setBounds(380, 6, 90, 40);
				loadFromFile.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						loadFromFileWidgetSelected(evt);
					}
				});
			}
			pack();
			this.setSize(900, 700);
			this.checkTwain();
			this.loadSettings();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean confirmMsg(String title, String msg) {
		return MessageDialog.openConfirm(getShell(), title, msg);
	}

	private void errorMsg(String Identifier, int code) {
		if (code != 0) {
			MessageDialog.openError(getShell(), "Error", String.format(
					"%s\nReturned Error Code: %d\n", Identifier, code));
		} else {
			MessageDialog.openError(getShell(), "Error", Identifier);
		}
	}

	private void checkTwain() {
		int scanlibReturn = scanlib.slIsTwainAvailable();
		switch (scanlibReturn) {
		case (ScanLib.SC_SUCCESS):
			break;
		case (ScanLib.SC_INVALID_VALUE):
			errorMsg("Scanlib Twain", scanlibReturn);
			System.exit(scanlibReturn);
			break;
		}
	}

	private void loadSettings() {
		configDialog.open("LOAD SETTINGS");
		int cdReturn = configDialog.loadConfigfromIni();
		if (cdReturn != 0) {
			errorMsg("Config Settings", cdReturn);
		}

	}

	private void menuSaveCvsAsWidgetSelected(SelectionEvent evt) {// save all
		FileDialog dlg = new FileDialog(getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.cvs", "*.*" });
		dlg.setText(String.format("Save All Barcodes"));
		String saveLocation = dlg.open();
		if (saveLocation == null) {
			return;
		}
		boolean[] tablesCheck = new boolean[MAXPLATES];
		for (int i = 0; i < MAXPLATES; i++) {
			tablesCheck[i] = true;
		}

		saveTables(saveLocation, tablesCheck);

	}

	private void menuQuitWidgetSelected(SelectionEvent evt) {
		if (confirmMsg("Quit", "Do you want to quit?")) {
			System.exit(0);
		}
	}

	private void menuSourceWidgetSelected(SelectionEvent evt) {
		int scanlibReturn = scanlib.slSelectSourceAsDefault();
		switch (scanlibReturn) {
		case (ScanLib.SC_SUCCESS):
			break;
		case (ScanLib.SC_INVALID_VALUE): // user canceled dialog box
			break;
		}
	}

	private void FIXSCANLIBINIWRITINGBUG() {
		try {
			Runtime.getRuntime().exec("fixscanlibini.exe");
		} catch (IOException e) {
			errorMsg("FIXSCANLIBINIWRITINGBUG", -1);
			System.out.println("fixscanlibini.exe Error");
			System.exit(-1);
		}
	}

	private void menuConfigurationWidgetSelected(SelectionEvent evt) {
		if (new File("scanlib.ini").exists()) {
			// int olddpi = configDialog.dpi; //accessed from here
			int oldbrightness = configDialog.brightness;
			int oldcontrast = configDialog.contrast;

			double oldplates[][] = new double[MAXPLATES][4];
			for (int plate = 0; plate < MAXPLATES; plate++) {
				for (int i = 0; i < 4; i++) {
					oldplates[plate][i] = configDialog.plates[plate][i];
				}
			}
			configDialog.open("");
			if (configDialog.cancledDialog) {
				return;
			}

			if (oldbrightness != configDialog.brightness) {
				int scanlibReturn = scanlib
						.slConfigScannerBrightness(configDialog.brightness);
				FIXSCANLIBINIWRITINGBUG();
				switch (scanlibReturn) {
				case (ScanLib.SC_SUCCESS):
					break;
				case (ScanLib.SC_INVALID_VALUE):
					errorMsg("menuConfigurationWidgetSelected, Brightness ",
							scanlibReturn);
					return;

				case (ScanLib.SC_INI_FILE_ERROR):
					errorMsg("menuConfigurationWidgetSelected, Brightness ",
							scanlibReturn);
					return;
				}

			}
			if (oldcontrast != configDialog.contrast) {
				int scanlibReturn = scanlib
						.slConfigScannerContrast(configDialog.contrast);
				FIXSCANLIBINIWRITINGBUG();
				switch (scanlibReturn) {
				case (ScanLib.SC_SUCCESS):
					break;
				case (ScanLib.SC_INVALID_VALUE):
					errorMsg("menuConfigurationWidgetSelected, contrast ",
							scanlibReturn);
					return;

				case (ScanLib.SC_INI_FILE_ERROR):
					errorMsg("menuConfigurationWidgetSelected, Contrast ",
							scanlibReturn);
					return;
				}
			}

			boolean platesChanged[] = new boolean[MAXPLATES];

			double sum = 0;
			for (int plate = 0; plate < MAXPLATES; plate++) {
				for (int i = 0; i < 4; i++) {
					if (oldplates[plate][i] != configDialog.plates[plate][i]) {
						platesChanged[plate] = true;
					}
					sum += configDialog.plates[plate][i];
				}
			}
			if (sum == 0) {
				errorMsg("Must Configure Atleast One Plate", -1);
				menuConfigurationWidgetSelected(evt);
			}
			for (int plate = 0; plate < MAXPLATES; plate++) {
				if (platesChanged[plate]) {
					int scanlibReturn = scanlib.slConfigPlateFrame(plate + 1,
							configDialog.plates[plate][1],
							configDialog.plates[plate][0],
							configDialog.plates[plate][3],
							configDialog.plates[plate][2]);
					FIXSCANLIBINIWRITINGBUG();
					switch (scanlibReturn) {
					case (ScanLib.SC_SUCCESS):
						break;
					case (ScanLib.SC_FAIL):
						errorMsg(
								"menuConfigurationWidgetSelected, slConfigPlateFrame",
								scanlibReturn);
						return;
					}
					if (configDialog.plates[plate][0]
							+ configDialog.plates[plate][1]
							+ configDialog.plates[plate][2]
							+ configDialog.plates[plate][3] > 0) {
						scanlibReturn = scanlib.slCalibrateToPlate(
								configDialog.dpi, plate + 1);
						FIXSCANLIBINIWRITINGBUG();
						switch (scanlibReturn) {
						case (ScanLib.SC_SUCCESS):
							break;
						case (ScanLib.SC_INVALID_IMAGE):
							errorMsg(
									"menuConfigurationWidgetSelected, Calibratation",
									scanlibReturn);
							return;

						case (ScanLib.SC_INI_FILE_ERROR):
							errorMsg(
									"menuConfigurationWidgetSelected, tation ",
									scanlibReturn);
							return;
						}
					}
				}
			}
		} else { // first time running
			int scanlibReturn = scanlib.slConfigScannerBrightness(0);
			FIXSCANLIBINIWRITINGBUG();
			if (scanlibReturn != ScanLib.SC_SUCCESS) {
				errorMsg("Error Failure!!\n"
						+ "Failed to write to scanlib.ini\n"
						+ "Application Failure: ", scanlibReturn);
				System.exit(1);
			} else {
				if (new File("scanlib.ini").exists()) {
					menuConfigurationWidgetSelected(evt);
				} else {
					MessageDialog.openError(getShell(), "Error Failure!!",
							"UNKNOWN\n" + "Application Failure");
					System.exit(1);
				}
			}
		}

	}

	private String nulltoblankString(String in) {
		if (in == null || in.isEmpty()) {
			return "";
		} else {
			return in;
		}
	}

	private void tableScanlibData(int table, boolean append) {
		try {
			ScanCell[][] sc = ScanCell.getScanLibResults();
			String[] row = new String[13];
			for (int r = 0; r < 8; r++) {
				row[0] = Character.toString((char) (65 + r));
				for (int c = 0; c < 12; c++) {

					if (append) {
						if (tableItems[table - 1][r].getText(c + 1).isEmpty()) {
							row[c + 1] = nulltoblankString(sc[r][c].getValue());
						} else {
							row[c + 1] = tableItems[table - 1][r]
									.getText(c + 1);
						}
					} else {
						row[c + 1] = nulltoblankString(sc[r][c].getValue());
					}
				}
				tableItems[table - 1][r].setText(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveTables(String fileLocation, boolean[] tables) {
		if (tables.length < MAXPLATES) {
			System.out.print("Bad Design Error");
			System.exit(-666);
		}

		try {
			BufferedWriter out = new BufferedWriter(
					new FileWriter(fileLocation));
			out.write("#Plate,Row,Col,Barcode\r\n");
			for (int p = 0; p < MAXPLATES; p++) {
				if (!tables[p]) {
					continue;
				}
				for (int r = 0; r < 8; r++) {
					for (int c = 0; c < 12; c++) {
						if (!tableItems[p][r].getText(c + 1).isEmpty()) {
							out.write(String.format("%d,%s,%d,%s\r\n", p + 1,
									Character.toString((char) (65 + r)), c + 1,
									tableItems[p][r].getText(c + 1)));
						}
					}
				}
			}

			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	private void clearPlateBtnWidgetSelected(SelectionEvent evt) {
		if (confirmMsg("Clear Table(s)",
				"Do you want to clear the selected tables?")) {
			for (int p = 0; p < MAXPLATES; p++) {
				if (plateBtn[p].getSelection()) {
					for (int r = 0; r < 8; r++) {
						for (int c = 0; c < 12; c++) {
							tableItems[p][r].setText(c + 1, "");
						}
					}
				}

			}
		}
	}

	private void scanPlateBtnWidgetSelected(SelectionEvent evt, boolean append) {

		boolean pass = false;
		for (int i = 0; i < MAXPLATES; i++) {
			if (plateBtn[i].getSelection()) {
				pass = true;
				break;
			}
		}
		if (!pass) {
			errorMsg("No Plates Selected", 0);
			return;
		}

		for (int plate = 0; plate < MAXPLATES; plate++) {
			if (configDialog.plates[plate][0] + configDialog.plates[plate][1]
					+ configDialog.plates[plate][2]
					+ configDialog.plates[plate][3] > 0
					&& plateBtn[plate].getSelection()) {
				int scanlibReturn = scanlib.slDecodePlate(configDialog.dpi,
						plate + 1); /*
									 * TODO fix - scanlib exits the program with
									 * a return code of 1 when platenum > 3
									 */

				FIXSCANLIBINIWRITINGBUG();
				switch (scanlibReturn) {
				case (ScanLib.SC_SUCCESS):
					break;
				case (ScanLib.SC_INVALID_IMAGE):
					errorMsg("scanPlateBtnWidgetSelected, DecodePlate",
							scanlibReturn);
					return;
				}
				tableScanlibData(plate + 1, append);
			}
		}
	}

	private void menuSaveSelectdWidgetSelected(SelectionEvent evt) {
		if (lastSaveSelectLocation == null || lastSaveSelectLocation.isEmpty()) {
			menuSaveSelectedWidgetSelected(evt);
		} else {

			boolean[] tablesCheck = new boolean[MAXPLATES];
			for (int i = 0; i < MAXPLATES; i++) {
				tablesCheck[i] = plateBtn[i].getSelection();
			}
			saveTables(lastSaveSelectLocation, tablesCheck);
		}
	}

	private void menuNewWidgetSelected(SelectionEvent evt) {
		if (confirmMsg("Clear Table(s)", "Do you want to clear all the tables?")) {
			for (int p = 0; p < MAXPLATES; p++) {
				for (int r = 0; r < 8; r++) {
					for (int c = 0; c < 12; c++) {
						tableItems[p][r].setText(c + 1, "");
					}
				}
			}
		}
	}

	/* NOTE: Adjust the text if when change MAXPLATES */
	private void menuSetModeWidgetSelected(SelectionEvent evt) {// TODO
		InputDialog dlg = new InputDialog(getShell(), "Plate Mode",
				"Please enter the plate mode:\nNote: The range is (1,4)", "4",
				new IInputValidator() {
					public String isValid(String newText) {
						int len = newText.length();
						if (len < 0 || len > 1)
							return "(1 <= digit <= 4)";
						int val = 0;
						try {
							val = Integer.valueOf(newText);
						} catch (NumberFormatException e) {
						}
						if (val < 1 || val > MAXPLATES) {
							return "(1 <= digit <= 4)";
						}
						return null;
					}
				});

		dlg.open();
		String ret = dlg.getValue();
		if (ret == null || ret.isEmpty()) {
			return;
		}
		boolean set = false;
		for (int table = 0; table < MAXPLATES; table++) {
			set = (table < Integer.valueOf(ret));
			tables[table].setEnabled(set);
			plateBtn[table].setEnabled(set);
			menuSaveBarcodes[table].setEnabled(set);
			menuPlates[table].setEnabled(set);
			plateBtn[table].setEnabled(set);
			if (!set) {
				plateBtn[table].setSelection(false);
				for (int r = 0; r < 8; r++) {
					for (int c = 0; c < 12; c++) {
						tableItems[table][r].setText(c + 1, "");
					}
				}
			}
		}
	}

	private void menuAutoSavingWidgetSelected(SelectionEvent evt) {
		System.out.println("menuAutoSaving.widgetSelected, event=" + evt);
		// TODO add your code for menuAutoSaving.widgetSelected
	}

	private void menuScanImageFileWidgetSelected(SelectionEvent evt) {
		FileDialog dlg = new FileDialog(getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.bmp", "*.*" });
		dlg.setText("Scan and Save Image");
		String saveLocation = dlg.open();
		if (saveLocation == null) {
			return;
		}
		int scanlibReturn = scanlib.slScanImage(configDialog.dpi, 0, 0, 0, 0,
				saveLocation);
		FIXSCANLIBINIWRITINGBUG();
		switch (scanlibReturn) {
		case (ScanLib.SC_SUCCESS):
			break;
		case (ScanLib.SC_INVALID_DPI):
			errorMsg("Scanlib ScanImage", scanlibReturn);
			break;
		case (ScanLib.SC_INVALID_PLATE_NUM):
			errorMsg("Scanlib ScanImage", scanlibReturn);
			break;
		case (ScanLib.SC_FAIL):
			errorMsg("Scanlib ScanImage", scanlibReturn);
			break;
		case (ScanLib.SC_FILE_SAVE_ERROR):
			errorMsg("Scanlib ScanImage", scanlibReturn);
			break;
		}
	}

	private void menuPlateScanToFile(SelectionEvent evt) {
		int platenum = 0;
		if (evt.toString().indexOf("Plate ") > -1) {
			platenum = Integer.parseInt(evt.toString().substring(
					evt.toString().indexOf("Plate ") + 6,
					evt.toString().indexOf("Plate ") + 7)); /* h3x */
		}
		if (platenum == 0) {
			return;
		}

		FileDialog dlg = new FileDialog(getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.bmp", "*.*" });
		dlg.setText(String.format("Scan and Save Plate %d", platenum));
		String saveLocation = dlg.open();
		if (saveLocation == null) {
			return;
		}

		int scanlibReturn = scanlib.slScanPlate(configDialog.dpi, platenum,
				saveLocation);
		FIXSCANLIBINIWRITINGBUG();
		switch (scanlibReturn) {
		case (ScanLib.SC_SUCCESS):
			break;
		case (ScanLib.SC_INVALID_DPI):
			errorMsg("Scanlib ScanImage", scanlibReturn);
			break;
		case (ScanLib.SC_INVALID_PLATE_NUM):
			errorMsg("Scanlib ScanImage", scanlibReturn);
			break;
		case (ScanLib.SC_CALIBRATOR_NO_REGIONS):
			errorMsg("Scanlib ScanImage", scanlibReturn);
			break;
		case (ScanLib.SC_FAIL):
			errorMsg("Scanlib ScanImage", scanlibReturn);
			break;
		case (ScanLib.SC_FILE_SAVE_ERROR):
			errorMsg("Scanlib ScanImage", scanlibReturn);
			break;
		}

	}

	private void saveTablePlate(int platenum) {
		FileDialog dlg = new FileDialog(getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.cvs", "*.*" });
		dlg.setText(String.format("Save Barcodes For Plate %d", platenum));
		String saveLocation = dlg.open();

		if (saveLocation != null) {
			boolean[] tablesCheck = new boolean[MAXPLATES];
			for (int i = 0; i < MAXPLATES; i++) {
				tablesCheck[i] = false;
			}
			tablesCheck[platenum - 1] = true;

			saveTables(saveLocation, tablesCheck);

		}
	}

	private void menuSaveBarcodeWidgetSelected(SelectionEvent evt) {
		if (evt.toString().indexOf("From Plate ") > -1) {
			int platenum = Integer.parseInt(evt.toString().substring(
					evt.toString().indexOf("From Plate ") + 11,
					evt.toString().indexOf("From Plate ") + 12)); /* h3x */
			saveTablePlate(platenum);
		}

	}

	private void loadFromFileWidgetSelected(SelectionEvent evt) {// TODO remove
		for (int i = 0; i < MAXPLATES; i++) {
			tableScanlibData(i + 1, false);
		}
	}

	private void menuSaveSelectedWidgetSelected(SelectionEvent evt) {
		boolean pass = false;
		for (int i = 0; i < MAXPLATES; i++) {
			if (plateBtn[i].getSelection()) {
				pass = true;
				break;
			}
		}
		if (!pass) {
			errorMsg("No Plates Selected", 0);
			return;
		}

		FileDialog dlg = new FileDialog(getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.cvs", "*.*" });
		dlg.setText(String.format("Save Barcodes for the Selected Plates"));
		String saveLocation = dlg.open();
		if (saveLocation != null) {
			boolean[] tablesCheck = new boolean[MAXPLATES];
			for (int i = 0; i < MAXPLATES; i++) {
				tablesCheck[i] = plateBtn[i].getSelection();
			}
			saveTables(saveLocation, tablesCheck);
			lastSaveSelectLocation = saveLocation;
		}

	}

}
