package edu.ualberta.med.biosamplescan.gui;

import org.eclipse.swt.widgets.FileDialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;

import edu.ualberta.med.scanlib.ScanCell;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scanlib.ScanLibFactory;


public class Main extends org.eclipse.swt.widgets.Composite {
	private Menu mainmenu;
	private Menu menu1;
	private Label label1;
	static private TableColumn tableColumn1;
	static private TableColumn tableColumn13;
	static private Table table2;
	private TableItem tableItem8;
	private TableItem tableItem7;
	private TableItem tableItem6;
	private TableItem tableItem5;
	private TableItem tableItem4;
	private TableItem tableItem3;
	private TableItem tableItem2;
	private TableColumn tableColumn12;
	private TableColumn tableColumn11;
	private TableColumn tableColumn10;
	private TableColumn tableColumn9;
	private TableColumn tableColumn8;
	private TableColumn tableColumn7;
	private TableColumn tableColumn6;
	private TableColumn tableColumn5;
	private TableColumn tableColumn4;
	private TableItem tableItem1;
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
	private MenuItem menuLoadPlate3;
	private MenuItem menuLoadPlate2;
	private MenuItem menuLoadPlate1;
	private Menu menu4;
	private MenuItem menuPlate3;
	private MenuItem menuPlate2;
	private MenuItem menuPlate1;
	private Menu menu3;
	private MenuItem sep;
	private Button appendDataBtn;
	private MenuItem menuNew;
	private MenuItem menuLoadPlate;
	private MenuItem menuAutoSaving;
	private MenuItem menuOptions;
	private MenuItem menuScanPlateFile;
	private MenuItem menuScanImageFile;
	private Button plate2Btn;
	private Button plate1Btn;
	private Button plate3Btn;
	private Button buttonPlate1;
	private Button scanPlateBtn;
	private MenuItem sep5;
	private Menu menu2;
	private MenuItem menuConfiguration;
	private TableItem tableItem24;
	private TableItem tableItem23;
	private TableItem tableItem22;
	private TableItem tableItem21;
	private TableItem tableItem20;
	private TableItem tableItem19;
	private TableItem tableItem18;
	private TableColumn tableColumn38;
	private TableColumn tableColumn37;
	private TableColumn tableColumn36;
	private TableColumn tableColumn35;
	private TableColumn tableColumn34;
	private TableColumn tableColumn33;
	private TableColumn tableColumn32;
	private TableColumn tableColumn31;
	private TableColumn tableColumn30;
	private TableItem tableItem17;
	private TableColumn tableColumn29;
	private TableColumn tableColumn28;
	static private TableColumn tableColumn27;
	static private TableColumn tableColumn26;
	static private Table table3;
	private TableItem tableItem16;
	private TableItem tableItem15;
	private TableItem tableItem14;
	private TableItem tableItem13;
	private TableItem tableItem12;
	private TableItem tableItem11;
	private TableItem tableItem10;
	private TableColumn tableColumn25;
	private TableColumn tableColumn24;
	private TableColumn tableColumn23;
	private TableColumn tableColumn22;
	private TableColumn tableColumn21;
	private TableColumn tableColumn20;
	private TableColumn tableColumn18;
	private TableColumn tableColumn17;
	private TableItem tableItem9;
	private TableColumn tableColumn16;
	private TableColumn tableColumn15;
	static private TableColumn tableColumn14;
	private MenuItem filemenu;
    private ScanLib scanlib = ScanLibFactory.getScanLib();
    private static int CUSTOMERRORCODE = -666;
    DebugDialog debugDialog = new DebugDialog(getShell(),SWT.NONE);
    ConfigDialog configDialog = new ConfigDialog(getShell(),SWT.NONE);
    ProcessingDialog processingDialog = new ProcessingDialog(getShell(),SWT.NONE);
    
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
			this.setEnabled(true);
			{
				plate1Btn = new Button(this, SWT.CHECK | SWT.LEFT);
				plate1Btn.setText("Plate 1");
				plate1Btn.setBounds(20, 22, 63, 18);
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
							menuLoadPlate = new MenuItem(menu1, SWT.CASCADE);
							menuLoadPlate.setText("Load Data from Plate File...");
							{
								menu4 = new Menu(menuLoadPlate);
								menuLoadPlate.setMenu(menu4);
								{
									menuLoadPlate1 = new MenuItem(menu4, SWT.CASCADE);
									menuLoadPlate1.setText("Plate 1");
								}
								{
									menuLoadPlate2 = new MenuItem(menu4, SWT.CASCADE);
									menuLoadPlate2.setText("Plate 2");
								}
								{
									menuLoadPlate3 = new MenuItem(menu4, SWT.CASCADE);
									menuLoadPlate3.setText("Plate 3");
								}
							}
							menuLoadPlate.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									menuLoadPlateWidgetSelected(evt);
								}
							});
						}
						{
							sep = new MenuItem(menu1, SWT.SEPARATOR);
							sep.setText("sep");
						}
						{
							menuSaveCvsAs = new MenuItem(menu1, SWT.CASCADE);
							menuSaveCvsAs.setText("Save Data As...");
							menuSaveCvsAs.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									menuSaveCvsAsWidgetSelected(evt);
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
							menuNew.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									menuNewWidgetSelected(evt);
								}
							});
						}
						{
							menuQuit = new MenuItem(menu1, SWT.CASCADE);
							menuQuit.setText("Quit");
							menuQuit.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
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
							menuAutoSaving.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									menuAutoSavingWidgetSelected(evt);
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
							menuSource.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									menuSourceWidgetSelected(evt);
								}
							});
						}
						{
							sep5 = new MenuItem(menu5, SWT.SEPARATOR);
							sep5.setText("sep5");
						}
						{
							menuConfiguration = new MenuItem(menu5, SWT.CASCADE);
							menuConfiguration.setText("Configuration");
							menuConfiguration.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									menuConfigurationWidgetSelected(evt);
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
							menuScanImageFile.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
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
									menuPlate1 = new MenuItem(menu3, SWT.CASCADE);
									menuPlate1.setText("Plate 1");
									menuPlate1.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(SelectionEvent evt) {
											menuPlate1WidgetSelected(evt);
										}
									});
								}
								{
									menuPlate2 = new MenuItem(menu3, SWT.CASCADE);
									menuPlate2.setText("Plate 2");
									menuPlate2.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(SelectionEvent evt) {
											menuPlate2WidgetSelected(evt);
										}
									});
								}
								{
									menuPlate3 = new MenuItem(menu3, SWT.CASCADE);
									menuPlate3.setText("Plate 3");
									menuPlate3.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(SelectionEvent evt) {
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
				table1 = new Table(this, SWT.FULL_SELECTION | SWT.EMBEDDED | SWT.V_SCROLL | SWT.BORDER);
				table1.setLinesVisible(true);
				table1.setHeaderVisible(true);
				table1.setBounds(17, 79, 783, 157);

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
					tableItem1 = new TableItem(table1, SWT.NONE);
					tableItem1.setText("A");
				}
				{
					tableItem2 = new TableItem(table1, SWT.NONE);
					tableItem2.setText("B");
				}
				{
					tableItem3 = new TableItem(table1, SWT.NONE);
					tableItem3.setText("C");
				}
				{
					tableItem4 = new TableItem(table1, SWT.NONE);
					tableItem4.setText("D");
				}
				{
					tableItem5 = new TableItem(table1, SWT.NONE);
					tableItem5.setText("E");
				}
				{
					tableItem6 = new TableItem(table1, SWT.NONE);
					tableItem6.setText("F");
				}
				{
					tableItem7 = new TableItem(table1, SWT.NONE);
					tableItem7.setText("G");
				}
				{
					tableItem8 = new TableItem(table1, SWT.NONE);
					tableItem8.setText("H");
				}
			}
			{
				table2 = new Table(this, SWT.FULL_SELECTION | SWT.EMBEDDED | SWT.V_SCROLL | SWT.BORDER);
				table2.setHeaderVisible(true);
				table2.setLinesVisible(true);
				table2.setBounds(16, 246, 787, 160);
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
					tableItem9 = new TableItem(table2, SWT.NONE);
					tableItem9.setText("A");
				}
				{
					tableItem10 = new TableItem(table2, SWT.NONE);
					tableItem10.setText("B");
				}
				{
					tableItem11 = new TableItem(table2, SWT.NONE);
					tableItem11.setText("C");
				}
				{
					tableItem12 = new TableItem(table2, SWT.NONE);
					tableItem12.setText("D");
				}
				{
					tableItem13 = new TableItem(table2, SWT.NONE);
					tableItem13.setText("E");
				}
				{
					tableItem14 = new TableItem(table2, SWT.NONE);
					tableItem14.setText("F");
				}
				{
					tableItem15 = new TableItem(table2, SWT.NONE);
					tableItem15.setText("G");
				}
				{
					tableItem16 = new TableItem(table2, SWT.NONE);
					tableItem16.setText("H");
				}
			}
			{
				table3 = new Table(this, SWT.FULL_SELECTION | SWT.EMBEDDED | SWT.V_SCROLL | SWT.BORDER);
				table3.setHeaderVisible(true);
				table3.setLinesVisible(true);
				table3.setBounds(14, 416, 786, 172);
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
					tableItem17 = new TableItem(table3, SWT.NONE);
					tableItem17.setText("A");
				}
				{
					tableItem18 = new TableItem(table3, SWT.NONE);
					tableItem18.setText("B");
				}
				{
					tableItem19 = new TableItem(table3, SWT.NONE);
					tableItem19.setText("C");
				}
				{
					tableItem20 = new TableItem(table3, SWT.NONE);
					tableItem20.setText("D");
				}
				{
					tableItem21 = new TableItem(table3, SWT.NONE);
					tableItem21.setText("E");
				}
				{
					tableItem22 = new TableItem(table3, SWT.NONE);
					tableItem22.setText("F");
				}
				{
					tableItem23 = new TableItem(table3, SWT.NONE);
					tableItem23.setText("G");
				}
				{
					tableItem24 = new TableItem(table3, SWT.NONE);
					tableItem24.setText("H");
				}
			}
			{
				scanPlateBtn = new Button(this, SWT.PUSH | SWT.CENTER);
				scanPlateBtn.setText("Scan Plate(s)");
				scanPlateBtn.setBounds(698, 6, 90, 40);
				scanPlateBtn.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						scanPlateBtnWidgetSelected(evt);
					}
				});
			}
			{
				plate2Btn = new Button(this, SWT.CHECK | SWT.LEFT);
				plate2Btn.setText("Plate 2");
				plate2Btn.setBounds(83, 22, 63, 18);
			}
			{
				plate3Btn = new Button(this, SWT.CHECK | SWT.LEFT);
				plate3Btn.setText("Plate 3");
				plate3Btn.setBounds(146, 22, 63, 18);
			}
			{
				appendDataBtn = new Button(this, SWT.CHECK | SWT.LEFT);
				appendDataBtn.setText("Append Plate Data");
				appendDataBtn.setBounds(227, 22, 120, 19);
			}
			pack();
			this.setSize(800, 600);
			checkTwain();
			loadSettings();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void errorMsg(String Identifier,int code){
		MessageDialog.openError(getShell(), "Error", 
				String.format("%s\nReturned Error Code: %d\n",Identifier,code));
	}
	

	private void checkTwain(){
		int scanlibReturn = scanlib.slIsTwainAvailable();
		switch(scanlibReturn){
			case(ScanLib.SC_SUCCESS):
				break;
			case(ScanLib.SC_INVALID_VALUE):
				errorMsg("Scanlib Twain",scanlibReturn);
				System.exit(scanlibReturn);
				break;
		}
	}
	
	private void loadSettings(){
	}
	
	
	private void menuAppendWidgetSelected(SelectionEvent evt) {
		System.out.println("menuAppend.widgetSelected, event="+evt);
	}
	
	private void menuAutoWidgetSelected(SelectionEvent evt) {
		System.out.println("menuAuto.widgetSelected, event="+evt);
	}
	
	private void menuSaveWidgetSelected(SelectionEvent evt) {
		System.out.println("menuSave.widgetSelected, event="+evt);
	}
	
	private void menuSaveCvsAsWidgetSelected(SelectionEvent evt) {
		new Thread(processingDialog).start();
		long a = 1;
		for(long i=0;i<1000000000L;i++){
			a = i;
		}
		//processingDialog.done();
	}
	
	private void menuQuitWidgetSelected(SelectionEvent evt) {
		System.exit(1);
	}
	
	private void menuSourceWidgetSelected(SelectionEvent evt) {
		int scanlibReturn = scanlib.slSelectSourceAsDefault();
		switch(scanlibReturn){
			case(ScanLib.SC_SUCCESS):
				break;
			case(ScanLib.SC_INVALID_VALUE): //user canceled dialog box
				break;
		}
	}

	private void menuConfigurationWidgetSelected(SelectionEvent evt) {
		configDialog.open("");
		if(configDialog.changedInputs){
			/*scanlib.slConfigScannerBrightness(configDialog.brightness);
			scanlib.slConfigScannerContrast(configDialog.contrast);
			scanlib.slConfigPlateFrame(1, configDialog.plates[0][0],configDialog.plates[0][1],configDialog.plates[0][2],configDialog.plates[0][3]);
			scanlib.slConfigPlateFrame(2, configDialog.plates[1][0],configDialog.plates[1][1],configDialog.plates[1][2],configDialog.plates[1][3]);
			scanlib.slConfigPlateFrame(3, configDialog.plates[2][0],configDialog.plates[2][1],configDialog.plates[2][2],configDialog.plates[2][3]);
			scanlib.slCalibrateToPlate(configDialog.dpi,1);
			scanlib.slCalibrateToPlate(configDialog.dpi,2);
			scanlib.slCalibrateToPlate(configDialog.dpi,3);*/
		}
	}
	
	private void scanPlateBtnWidgetSelected(SelectionEvent evt) {
		if(plate1Btn.getSelection() == false &&
		   plate2Btn.getSelection() == false &&
		   plate3Btn.getSelection() == false){
			errorMsg("No Plates Selected",0);
			return;
		}
		
		if(plate1Btn.getSelection()){
			scanlib.slDecodePlate(configDialog.dpi,1);
		}
		if(plate2Btn.getSelection()){
			scanlib.slDecodePlate(configDialog.dpi,2);
		}
		if(plate3Btn.getSelection()){
			//scanlib.slDecodePlate(configDialog.dpi,3);
			try {
				ScanCell[][] sc = ScanCell.getScanLibResults();
				
				String [] row = new String[13];
				row[0] = Character.toString ((char) 65);
				for(int c=0;c<12;c++){
					row[c+1] = sc[0][c].getValue();
				}
				tableItem17.setText(row);
				
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	private void menuLoadPlateWidgetSelected(SelectionEvent evt) {
		System.out.println("menuLoadPlate.widgetSelected, event="+evt);
		//TODO add your code for menuLoadPlate.widgetSelected
	}
	
	private void menuNewWidgetSelected(SelectionEvent evt) {
		System.out.println("menuNew.widgetSelected, event="+evt);
		//TODO add your code for menuNew.widgetSelected
	}
	
	private void menuAutoSavingWidgetSelected(SelectionEvent evt) {
		System.out.println("menuAutoSaving.widgetSelected, event="+evt);
		//TODO add your code for menuAutoSaving.widgetSelected
	}
	
	private void menuScanImageFileWidgetSelected(SelectionEvent evt) {
		FileDialog dlg =  new FileDialog(getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.bmp", "*.*" });
		dlg.setText("Scan and Save Image");
		String saveLocation = dlg.open();
		if(saveLocation == null){
			return;
		}
		int scanlibReturn = scanlib.slScanImage(ScanLib.DPI_300,0,0,0,0,saveLocation);
		switch(scanlibReturn){
			case(ScanLib.SC_SUCCESS):
				break;
			case(ScanLib.SC_INVALID_DPI):
				errorMsg("Scanlib ScanImage",scanlibReturn);
				break;
			case(ScanLib.SC_INVALID_PLATE_NUM): 
				errorMsg("Scanlib ScanImage",scanlibReturn);
				break;
			case(ScanLib.SC_FAIL): 
				errorMsg("Scanlib ScanImage",scanlibReturn);
				break;
			case(ScanLib.SC_FILE_SAVE_ERROR): 
				errorMsg("Scanlib ScanImage",scanlibReturn);
				break;
		}
	}
	
	private void menuPlateScanToFile(int platenum) {
		FileDialog dlg =  new FileDialog(getShell(), SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "*.bmp", "*.*" });
		dlg.setText(String.format("Scan and Save Plate %d",platenum));
		String saveLocation = dlg.open();
		if(saveLocation == null){
			return;
		}
		int scanlibReturn = scanlib.slScanPlate(ScanLib.DPI_300,platenum,saveLocation);
		switch(scanlibReturn){
			case(ScanLib.SC_SUCCESS):
				break;
			case(ScanLib.SC_INVALID_DPI):
				errorMsg("Scanlib ScanImage",scanlibReturn);
				break;
			case(ScanLib.SC_INVALID_PLATE_NUM): 
				errorMsg("Scanlib ScanImage",scanlibReturn);
				break;
			case(ScanLib.SC_CALIBRATOR_NO_REGIONS):
				errorMsg("Scanlib ScanImage",scanlibReturn);
				break;
			case(ScanLib.SC_FAIL): 
				errorMsg("Scanlib ScanImage",scanlibReturn);
				break;
			case(ScanLib.SC_FILE_SAVE_ERROR): 
				errorMsg("Scanlib ScanImage",scanlibReturn);
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

}
//scanlib.slConfigScannerBrightness(0);
//scanlib.slConfigScannerContrast(0);
//scanlib.slConfigPlateFrame(3, 2.15,0.5,4.6,3.2);
//int rc = scanlib.slScanPlate(ScanLib.DPI_300, 3, "plate3.bmp");
//int rc = scanlib.slCalibrateToPlate(ScanLib.DPI_300,3);
//int rc = scanlib.slDecodePlate(configDialog.dpi,3); //scans and processes
//System.out.println("rc: " + rc);
