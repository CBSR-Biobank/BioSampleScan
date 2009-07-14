package edu.ualberta.med.biosamplescan.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

	{
		SWTManager.registerResourceUser(this);
	}

	private Menu mainmenu;
	private Menu menu1;
	static private TableColumn tableColumn1;
	static private TableColumn tableColumn13;
	static private Table table2;
	private TableColumn tableColumn12;
	private TableColumn tableColumn11;
	private TableColumn tableColumn10;
	private TableColumn tableColumn9;
	private TableColumn tableColumn8;
	private TableColumn tableColumn7;
	private TableColumn tableColumn6;
	private TableColumn tableColumn5;
	private TableColumn tableColumn4;
	private TableColumn tableColumn3;
	private TableColumn tableColumn2;
	static private TableColumn A;
	static private Table table1;
	private MenuItem sep2;
	private Menu menu5;
	private MenuItem menuSource;
	private MenuItem menuScanner;
	private MenuItem menuQuit;
	private MenuItem menuSaveCvsAs;
	private MenuItem sep3;
	private TableColumn tableColumn19;
	private MenuItem menuSaveSelected;
	private Button loadFromFile;
	private Button reScanPlateBtn;
	private MenuItem menuSetMode;
	private MenuItem menuPlate3;
	private MenuItem menuPlate2;
	private MenuItem menuPlate1;
	private Menu menu3;
	private MenuItem menuNew;
	private MenuItem menuAutoSaving;
	private MenuItem menuOptions;
	private MenuItem menuScanPlateFile;
	private MenuItem menuScanImageFile;
	private Button plateBtn[];
	private Button scanPlateBtn;
	private Menu menu2;
	private MenuItem menuConfiguration;
	private TableColumn tableColumn38;
	private TableColumn tableColumn37;
	private TableColumn tableColumn36;
	private TableColumn tableColumn35;
	private TableColumn tableColumn34;
	private TableColumn tableColumn33;
	private TableColumn tableColumn32;
	private TableColumn tableColumn31;
	private TableColumn tableColumn30;
	private TableColumn tableColumn29;
	private TableColumn tableColumn28;
	static private TableColumn tableColumn27;
	static private TableColumn tableColumn26;
	static private Table table3;
	private TableColumn tableColumn25;
	private TableColumn tableColumn24;
	private TableColumn tableColumn23;
	private TableColumn tableColumn22;
	private TableColumn tableColumn21;
	private TableColumn tableColumn20;
	private TableColumn tableColumn18;
	private TableColumn tableColumn17;
	private TableColumn tableColumn16;
	private TableColumn tableColumn15;
	static private TableColumn tableColumn14;
	private TableItem[][] tableItems = new TableItem[4][4 * 8];
	private MenuItem menuSaveBarcode3;
	private MenuItem menuSaveBarcode2;
	private MenuItem menuSaveBarcode1;
	private Menu menu4;
	private MenuItem menuSaveBarcode;

	private MenuItem filemenu;
	private ScanLib scanlib = ScanLibFactory.getScanLib();
	DebugDialog debugDialog = new DebugDialog(getShell(), SWT.NONE);
	ConfigDialog configDialog = new ConfigDialog(getShell(), SWT.NONE);
	ProcessingDialog processingDialog = new ProcessingDialog(getShell(),
			SWT.NONE);

	public void main(String[] args) {
		showGUI();
	}

	protected void checkSubclass() {
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
				plateBtn = new Button[3];
				plateBtn[0] = new Button(this, SWT.CHECK | SWT.LEFT);
				plateBtn[0].setText("Plate 1");
				plateBtn[0].setBounds(20, 22, 63, 18);
			}

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
							menuSaveBarcode = new MenuItem(menu1, SWT.CASCADE);
							menuSaveBarcode.setText("Save Barcode...");
							{
								menu4 = new Menu(menuSaveBarcode);
								menuSaveBarcode.setMenu(menu4);
								{
									menuSaveBarcode1 = new MenuItem(menu4,
											SWT.CASCADE);
									menuSaveBarcode1.setText("From Plate 1");
									menuSaveBarcode1
											.addSelectionListener(new SelectionAdapter() {
												public void widgetSelected(
														SelectionEvent evt) {
													menuSaveBarcode1WidgetSelected(evt);
												}
											});
								}
								{
									menuSaveBarcode2 = new MenuItem(menu4,
											SWT.CASCADE);
									menuSaveBarcode2.setText("From Plate 2");
									menuSaveBarcode2
											.addSelectionListener(new SelectionAdapter() {
												public void widgetSelected(
														SelectionEvent evt) {
													menuSaveBarcode2WidgetSelected(evt);
												}
											});
								}
								{
									menuSaveBarcode3 = new MenuItem(menu4,
											SWT.CASCADE);
									menuSaveBarcode3.setText("From Plate 3");
									menuSaveBarcode3
											.addSelectionListener(new SelectionAdapter() {
												public void widgetSelected(
														SelectionEvent evt) {
													menuSaveBarcode3WidgetSelected(evt);
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
							sep2 = new MenuItem(menu1, SWT.SEPARATOR);
							sep2.setText("sep2");
						}
						{
							menuNew = new MenuItem(menu1, SWT.CASCADE);
							menuNew.setText("Clear Session");
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
						}
						{
							menuConfiguration = new MenuItem(menu2, SWT.CASCADE);
							menuConfiguration.setText("Settings");
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
							sep3 = new MenuItem(menu5, SWT.SEPARATOR);
							sep3.setText("sep3");
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
								{
									menuPlate1 = new MenuItem(menu3,
											SWT.CASCADE);
									menuPlate1.setText("Plate 1");
									menuPlate1
											.addSelectionListener(new SelectionAdapter() {
												public void widgetSelected(
														SelectionEvent evt) {
													menuPlate1WidgetSelected(evt);
												}
											});
								}
								{
									menuPlate2 = new MenuItem(menu3,
											SWT.CASCADE);
									menuPlate2.setText("Plate 2");
									menuPlate2
											.addSelectionListener(new SelectionAdapter() {
												public void widgetSelected(
														SelectionEvent evt) {
													menuPlate2WidgetSelected(evt);
												}
											});
								}
								{
									menuPlate3 = new MenuItem(menu3,
											SWT.CASCADE);
									menuPlate3.setText("Plate 3");
									menuPlate3
											.addSelectionListener(new SelectionAdapter() {
												public void widgetSelected(
														SelectionEvent evt) {
													menuPlate3WidgetSelected(evt);
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
				table1 = new Table(this, SWT.FULL_SELECTION | SWT.EMBEDDED
						| SWT.V_SCROLL | SWT.BORDER);
				table1.setLinesVisible(true);
				table1.setHeaderVisible(true);
				table1.setBounds(12, 63, 783, 133);
				table1.setFont(SWTManager
						.getFont("Calibri", 6, 0, false, false));

				{
					tableColumn1 = new TableColumn(table1, SWT.NONE);
					tableColumn1.setWidth(60);
					tableColumn1.setResizable(false);

				}
				{
					A = new TableColumn(table1, SWT.NONE);
					A.setText("1");
					A.setWidth(57);
				}
				{
					tableColumn2 = new TableColumn(table1, SWT.NONE);
					tableColumn2.setText("2");
					tableColumn2.setWidth(60);
				}
				{
					tableColumn3 = new TableColumn(table1, SWT.NONE);
					tableColumn3.setText("3");
					tableColumn3.setWidth(60);
				}
				{
					tableColumn4 = new TableColumn(table1, SWT.NONE);
					tableColumn4.setText("4");
					tableColumn4.setWidth(60);
				}
				{
					tableColumn5 = new TableColumn(table1, SWT.NONE);
					tableColumn5.setText("5");
					tableColumn5.setWidth(60);
				}
				{
					tableColumn6 = new TableColumn(table1, SWT.NONE);
					tableColumn6.setText("6");
					tableColumn6.setWidth(60);
				}
				{
					tableColumn7 = new TableColumn(table1, SWT.NONE);
					tableColumn7.setText("7");
					tableColumn7.setWidth(60);
				}
				{
					tableColumn8 = new TableColumn(table1, SWT.NONE);
					tableColumn8.setText("8");
					tableColumn8.setWidth(60);
				}
				{
					tableColumn9 = new TableColumn(table1, SWT.NONE);
					tableColumn9.setText("9");
					tableColumn9.setWidth(60);
				}
				{
					tableColumn10 = new TableColumn(table1, SWT.NONE);
					tableColumn10.setText("10");
					tableColumn10.setWidth(60);
				}
				{
					tableColumn11 = new TableColumn(table1, SWT.NONE);
					tableColumn11.setText("11");
					tableColumn11.setWidth(60);
				}
				{
					tableColumn12 = new TableColumn(table1, SWT.NONE);
					tableColumn12.setText("12");
					tableColumn12.setWidth(60);
				}
				{
					tableItems[0][0] = new TableItem(table1, SWT.NONE);
					tableItems[0][0].setText("A");
				}
				{
					tableItems[0][1] = new TableItem(table1, SWT.NONE);
					tableItems[0][1].setText("B");
				}
				{
					tableItems[0][2] = new TableItem(table1, SWT.NONE);
					tableItems[0][2].setText("C");
				}
				{
					tableItems[0][3] = new TableItem(table1, SWT.NONE);
					tableItems[0][3].setText("D");
				}
				{
					tableItems[0][4] = new TableItem(table1, SWT.NONE);
					tableItems[0][4].setText("E");
				}
				{
					tableItems[0][5] = new TableItem(table1, SWT.NONE);
					tableItems[0][5].setText("F");
				}
				{
					tableItems[0][6] = new TableItem(table1, SWT.NONE);
					tableItems[0][6].setText("G");
				}
				{
					tableItems[0][7] = new TableItem(table1, SWT.NONE);
					tableItems[0][7].setText("H");
				}
			}
			{
				table2 = new Table(this, SWT.FULL_SELECTION | SWT.EMBEDDED
						| SWT.V_SCROLL | SWT.BORDER);
				table2.setHeaderVisible(true);
				table2.setLinesVisible(true);
				table2.setBounds(12, 204, 787, 131);
				table2.setFont(SWTManager
						.getFont("Calibri", 6, 0, false, false));
				{
					tableColumn13 = new TableColumn(table2, SWT.NONE);
					tableColumn13.setWidth(60);
					tableColumn13.setResizable(false);
				}
				{
					tableColumn14 = new TableColumn(table2, SWT.NONE);
					tableColumn14.setText("1");
					tableColumn14.setWidth(60);
				}
				{
					tableColumn15 = new TableColumn(table2, SWT.NONE);
					tableColumn15.setText("2");
					tableColumn15.setWidth(60);
				}
				{
					tableColumn16 = new TableColumn(table2, SWT.NONE);
					tableColumn16.setText("3");
					tableColumn16.setWidth(60);
				}
				{
					tableColumn17 = new TableColumn(table2, SWT.NONE);
					tableColumn17.setText("4");
					tableColumn17.setWidth(60);
				}
				{
					tableColumn18 = new TableColumn(table2, SWT.NONE);
					tableColumn18.setText("5");
					tableColumn18.setWidth(60);
				}
				{
					tableColumn19 = new TableColumn(table2, SWT.NONE);
					tableColumn19.setText("6");
					tableColumn19.setWidth(60);
				}
				{
					tableColumn20 = new TableColumn(table2, SWT.NONE);
					tableColumn20.setText("7");
					tableColumn20.setWidth(60);
				}
				{
					tableColumn21 = new TableColumn(table2, SWT.NONE);
					tableColumn21.setText("8");
					tableColumn21.setWidth(60);
				}
				{
					tableColumn22 = new TableColumn(table2, SWT.NONE);
					tableColumn22.setText("9");
					tableColumn22.setWidth(60);
				}
				{
					tableColumn23 = new TableColumn(table2, SWT.NONE);
					tableColumn23.setText("10");
					tableColumn23.setWidth(60);
				}
				{
					tableColumn24 = new TableColumn(table2, SWT.NONE);
					tableColumn24.setText("11");
					tableColumn24.setWidth(60);
				}
				{
					tableColumn25 = new TableColumn(table2, SWT.NONE);
					tableColumn25.setText("12");
					tableColumn25.setWidth(60);
				}
				{
					tableItems[1][0] = new TableItem(table2, SWT.NONE);
					tableItems[1][0].setText("A");
				}
				{
					tableItems[1][1] = new TableItem(table2, SWT.NONE);
					tableItems[1][1].setText("B");
				}
				{
					tableItems[1][2] = new TableItem(table2, SWT.NONE);
					tableItems[1][2].setText("C");
				}
				{
					tableItems[1][3] = new TableItem(table2, SWT.NONE);
					tableItems[1][3].setText("D");
				}
				{
					tableItems[1][4] = new TableItem(table2, SWT.NONE);
					tableItems[1][4].setText("E");
				}
				{
					tableItems[1][5] = new TableItem(table2, SWT.NONE);
					tableItems[1][5].setText("F");
				}
				{
					tableItems[1][6] = new TableItem(table2, SWT.NONE);
					tableItems[1][6].setText("G");
				}
				{
					tableItems[1][7] = new TableItem(table2, SWT.NONE);
					tableItems[1][7].setText("H");
				}
			}
			{
				table3 = new Table(this, SWT.FULL_SELECTION | SWT.EMBEDDED
						| SWT.V_SCROLL | SWT.BORDER);
				table3.setHeaderVisible(true);
				table3.setLinesVisible(true);
				table3.setBounds(12, 344, 786, 131);
				table3.setFont(SWTManager
						.getFont("Calibri", 6, 0, false, false));
				{
					tableColumn26 = new TableColumn(table3, SWT.NONE);
					tableColumn26.setWidth(60);
					tableColumn26.setResizable(false);
				}
				{
					tableColumn27 = new TableColumn(table3, SWT.NONE);
					tableColumn27.setText("1");
					tableColumn27.setWidth(60);
				}
				{
					tableColumn28 = new TableColumn(table3, SWT.NONE);
					tableColumn28.setText("2");
					tableColumn28.setWidth(60);
				}
				{
					tableColumn29 = new TableColumn(table3, SWT.NONE);
					tableColumn29.setText("3");
					tableColumn29.setWidth(60);
				}
				{
					tableColumn30 = new TableColumn(table3, SWT.NONE);
					tableColumn30.setText("4");
					tableColumn30.setWidth(60);
				}
				{
					tableColumn31 = new TableColumn(table3, SWT.NONE);
					tableColumn31.setText("5");
					tableColumn31.setWidth(60);
				}
				{
					tableColumn32 = new TableColumn(table3, SWT.NONE);
					tableColumn32.setText("6");
					tableColumn32.setWidth(60);
				}
				{
					tableColumn33 = new TableColumn(table3, SWT.NONE);
					tableColumn33.setText("7");
					tableColumn33.setWidth(60);
				}
				{
					tableColumn34 = new TableColumn(table3, SWT.NONE);
					tableColumn34.setText("8");
					tableColumn34.setWidth(60);
				}
				{
					tableColumn35 = new TableColumn(table3, SWT.NONE);
					tableColumn35.setText("9");
					tableColumn35.setWidth(60);
				}
				{
					tableColumn36 = new TableColumn(table3, SWT.NONE);
					tableColumn36.setText("10");
					tableColumn36.setWidth(60);
				}
				{
					tableColumn37 = new TableColumn(table3, SWT.NONE);
					tableColumn37.setText("11");
					tableColumn37.setWidth(60);
				}
				{
					tableColumn38 = new TableColumn(table3, SWT.NONE);
					tableColumn38.setText("12");
					tableColumn38.setWidth(60);
				}
				{
					tableItems[2][0] = new TableItem(table3, SWT.NONE);
					tableItems[2][0].setText("A");
				}
				{
					tableItems[2][1] = new TableItem(table3, SWT.NONE);
					tableItems[2][1].setText("B");
				}
				{
					tableItems[2][2] = new TableItem(table3, SWT.NONE);
					tableItems[2][2].setText("C");
				}
				{
					tableItems[2][3] = new TableItem(table3, SWT.NONE);
					tableItems[2][3].setText("D");
				}
				{
					tableItems[2][4] = new TableItem(table3, SWT.NONE);
					tableItems[2][4].setText("E");
				}
				{
					tableItems[2][5] = new TableItem(table3, SWT.NONE);
					tableItems[2][5].setText("F");
				}
				{
					tableItems[2][6] = new TableItem(table3, SWT.NONE);
					tableItems[2][6].setText("G");
				}
				{
					tableItems[2][7] = new TableItem(table3, SWT.NONE);
					tableItems[2][7].setText("H");
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
				plateBtn[1] = new Button(this, SWT.CHECK | SWT.LEFT);
				plateBtn[1].setText("Plate 2");
				plateBtn[1].setBounds(83, 22, 63, 18);
			}
			{
				plateBtn[2] = new Button(this, SWT.CHECK | SWT.LEFT);
				plateBtn[2].setText("Plate 3");
				plateBtn[2].setBounds(146, 22, 63, 18);
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
				loadFromFile.setBounds(488, 6, 90, 40);
				loadFromFile.addSelectionListener(new SelectionAdapter() {
					public void widgetDefaultSelected(SelectionEvent evt) {
						System.out
								.println("loadFromFile.widgetDefaultSelected, event="
										+ evt);
						// TODO add your code for
						// loadFromFile.widgetDefaultSelected
					}

					public void widgetSelected(SelectionEvent evt) {
						loadFromFileWidgetSelected(evt);
					}
				});
			}
			pack();
			this.setSize(800, 600);
			this.checkTwain();
			this.loadSettings();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void errorMsg(String Identifier, int code) {
		MessageDialog.openError(getShell(), "Error", String.format(
				"%s\nReturned Error Code: %d\n", Identifier, code));
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
		/*
		 * new Thread() { public void run() { long a = 1; for (long i = 0; i <
		 * 1000000000L; i++) { a = i;
		 * 
		 * } } }.start();
		 */

		FileDialog dlg = new FileDialog(getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.cvs", "*.*" });
		dlg.setText(String.format("Save All Barcodes"));
		String saveLocation = dlg.open();
		if (saveLocation == null) {
			return;
		}
		saveAllTables(saveLocation);

	}

	private void menuQuitWidgetSelected(SelectionEvent evt) {
		System.exit(1);
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
			System.exit(-1);
		}
	}

	private void menuConfigurationWidgetSelected(SelectionEvent evt) {
		if (new File("scanlib.ini").exists()) {
			// int olddpi = configDialog.dpi; //accessed from here
			int oldbrightness = configDialog.brightness;
			int oldcontrast = configDialog.contrast;

			double oldplates[][] = new double[configDialog.PLATENUM][4];
			for (int plate = 0; plate < configDialog.PLATENUM; plate++) {
				for (int i = 0; i < 4; i++) {
					oldplates[plate][i] = configDialog.plates[plate][i];
				}
			}

			/*
			 * Must have atleast 1 plate that is not t=l=b=r=0 Compare old
			 * values to new values, only update the ini file & call calibrate
			 * when the configuration values have been changed.
			 */

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

			boolean platesChanged[] = new boolean[configDialog.PLATENUM];

			double sum = 0;
			for (int plate = 0; plate < configDialog.PLATENUM; plate++) {
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
			for (int plate = 0; plate < configDialog.PLATENUM; plate++) {
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
									"menuConfigurationWidgetSelected, Calibratation ",
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

	private void saveAllTables(String fileLocation) {

		try {
			BufferedWriter out = new BufferedWriter(
					new FileWriter(fileLocation));
			out.write("#Plate,Row,Col,Barcode\r\n");
			for (int p = 0; p < 3; p++) { // TODO numplates
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
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	private void scanPlateBtnWidgetSelected(SelectionEvent evt, boolean append) {
		if (plateBtn[0].getSelection() == false
				&& plateBtn[1].getSelection() == false
				&& plateBtn[2].getSelection() == false) {
			errorMsg("No Plates Selected", 0);
			return;
		}

		for (int plate = 0; plate < configDialog.PLATENUM; plate++) {
			if (configDialog.plates[plate][0] + configDialog.plates[plate][1]
					+ configDialog.plates[plate][2]
					+ configDialog.plates[plate][3] > 0
					&& plateBtn[plate].getSelection()) {
				int scanlibReturn = scanlib.slDecodePlate(configDialog.dpi,
						plate + 1);
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

	private void menuNewWidgetSelected(SelectionEvent evt) {
		System.out.println("menuNew.widgetSelected, event=" + evt);
		// TODO add your code for menuNew.widgetSelected
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
		int scanlibReturn = scanlib.slScanImage(ScanLib.DPI_300, 0, 0, 0, 0,
				saveLocation);
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

	private void menuPlateScanToFile(int platenum) {
		FileDialog dlg = new FileDialog(getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.bmp", "*.*" });
		dlg.setText(String.format("Scan and Save Plate %d", platenum));
		String saveLocation = dlg.open();
		if (saveLocation == null) {
			return;
		}
		int scanlibReturn = scanlib.slScanPlate(ScanLib.DPI_300, platenum,
				saveLocation);
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

	private void menuPlate1WidgetSelected(SelectionEvent evt) {
		menuPlateScanToFile(1);
	}

	private void menuPlate2WidgetSelected(SelectionEvent evt) {
		menuPlateScanToFile(2);
	}

	private void menuPlate3WidgetSelected(SelectionEvent evt) {
		menuPlateScanToFile(3);
	}

	private void menuSaveBarcode1WidgetSelected(SelectionEvent evt) {
		System.out.println("menuSaveBarcode1.widgetSelected, event=" + evt);
		// TODO add your code for menuSaveBarcode1.widgetSelected
	}

	private void menuSaveBarcode2WidgetSelected(SelectionEvent evt) {
		System.out.println("menuSaveBarcode2.widgetSelected, event=" + evt);
		// TODO add your code for menuSaveBarcode2.widgetSelected
	}

	private void menuSaveBarcode3WidgetSelected(SelectionEvent evt) {
		System.out.println("menuSaveBarcode3.widgetSelected, event=" + evt);
		// TODO add your code for menuSaveBarcode3.widgetSelected
	}

	private void loadFromFileWidgetSelected(SelectionEvent evt) {
		tableScanlibData(1, false);
		tableScanlibData(2, false);
		tableScanlibData(3, false);
	}

	private void menuSaveSelectedWidgetSelected(SelectionEvent evt) {
		System.out.println("menuSaveSelected.widgetSelected, event=" + evt);
		// TODO add your code for menuSaveSelected.widgetSelected
	}

}
// scanlib.slConfigScannerBrightness(0);
// scanlib.slConfigScannerContrast(0);
// scanlib.slConfigPlateFrame(3, 2.15,0.5,4.6,3.2);
// int rc = scanlib.slScanPlate(ScanLib.DPI_300, 3, "plate3.bmp");
// int rc = scanlib.slCalibrateToPlate(ScanLib.DPI_300,3);
// int rc = scanlib.slDecodePlate(configDialog.dpi,3); //scans and processes
// System.out.println("rc: " + rc);
