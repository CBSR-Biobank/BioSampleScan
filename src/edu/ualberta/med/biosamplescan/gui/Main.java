package edu.ualberta.med.biosamplescan.gui;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import edu.ualberta.med.scanlib.*;


public class Main extends org.eclipse.swt.widgets.Composite {
	private Menu mainmenu;
	private Menu menu1;
	private MenuItem menuSave;
	private Button container3Btn;
	private Button container1Btn;
	private Label label1;
	private Button ScanBtn;
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
	private Button container2Btn;
	private MenuItem sep3;
	private MenuItem sep2;
	private MenuItem menuAuto;
	private MenuItem menuAppend;
	private MenuItem menuScan;
	private MenuItem menuAlign;
	private Menu menu6;
	private MenuItem menuBitmap;
	private MenuItem menuSource;
	private Menu menu5;
	private MenuItem menuScanner;
	private MenuItem menuQuit;
	private MenuItem menuSaveAs;
	private MenuItem Sep1;
	private TableColumn tableColumn19;
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
    DebugDialog debugDialog = new DebugDialog(getShell(),SWT.NONE);

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
				mainmenu = new Menu(getShell(), SWT.BAR);
				getShell().setMenuBar(mainmenu);
				{
					filemenu = new MenuItem(mainmenu, SWT.CASCADE);
					filemenu.setText("File");
					{
						menu1 = new Menu(filemenu);
						filemenu.setMenu(menu1);
						{
							menuAppend = new MenuItem(menu1, SWT.CASCADE);
							menuAppend.setText("Append");
							menuAppend.setSelection(true);
							menuAppend.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									menuAppendWidgetSelected(evt);
								}
							});
						}
						{
							menuAuto = new MenuItem(menu1, SWT.CASCADE);
							menuAuto.setText("Auto Save");
							menuAuto.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									menuAutoWidgetSelected(evt);
								}
							});
						}
						{
							Sep1 = new MenuItem(menu1, SWT.SEPARATOR);
						}
						{
							menuSave = new MenuItem(menu1, SWT.CASCADE);
							menuSave.setText("Save");
							menuSave.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									menuSaveWidgetSelected(evt);
								}
							});
						}
						{
							menuSaveAs = new MenuItem(menu1, SWT.CASCADE);
							menuSaveAs.setText("Save As ...");
							menuSaveAs.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									menuSaveAsWidgetSelected(evt);
								}
							});
						}
						{
							sep2 = new MenuItem(menu1, SWT.SEPARATOR);
							sep2.setText("sep2");
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
							sep3 = new MenuItem(menu5, SWT.SEPARATOR);
							sep3.setText("sep3");
						}
						{
							menuScan = new MenuItem(menu5, SWT.CASCADE);
							menuScan.setText("Scan");
							menuScan.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									menuScanWidgetSelected(evt);
								}
							});
						}
					}
				}
				{
					menuBitmap = new MenuItem(mainmenu, SWT.CASCADE);
					menuBitmap.setText("Bitmap");
					{
						menu6 = new Menu(menuBitmap);
						menuBitmap.setMenu(menu6);
						{
							menuAlign = new MenuItem(menu6, SWT.CASCADE);
							menuAlign.setText("Align");
							menuAlign.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									menuAlignWidgetSelected(evt);
								}
							});
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
				table1.setBounds(7, 61, 787, 172);

				
				
				{
					tableColumn1 = new TableColumn(table1, SWT.NONE);
					tableColumn1.setWidth(60);
					tableColumn1.setResizable(false);
					
					
				}
				{
					A = new TableColumn(table1, SWT.NONE);
					A.setText("1");
					A.setWidth(60);
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
					tableItem1 = new TableItem(table1, SWT.NONE);
					tableItem1.setText("A");
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
				container1Btn = new Button(this, SWT.CHECK | SWT.LEFT);
				container1Btn.setText("1");
				container1Btn.setBounds(61, 12, 27, 15);
				container1Btn.addMouseListener(new MouseAdapter() {
					public void mouseDown(MouseEvent evt) {
						container1BtnMouseDown(evt);
					}
				});
			}
			{
				label1 = new Label(this, SWT.NONE);
				label1.setText("Container:");
				label1.setBounds(0, 12, 55, 16);
			}
			{
				ScanBtn = new Button(this, SWT.PUSH | SWT.CENTER);
				ScanBtn.setText("Scan Containers");
				ScanBtn.setBounds(653, 12, 86, 27);
			}
			{
				container2Btn = new Button(this, SWT.CHECK | SWT.LEFT);
				container2Btn.setText("2");
				container2Btn.setBounds(100, 13, 30, 13);
			}
			{
				container3Btn = new Button(this, SWT.CHECK | SWT.LEFT);
				container3Btn.setBounds(137, 12, 32, 15);
				container3Btn.setText("3");
			}
			{
				table2 = new Table(this, SWT.FULL_SELECTION | SWT.EMBEDDED | SWT.V_SCROLL | SWT.BORDER);
				table2.setHeaderVisible(true);
				table2.setLinesVisible(true);
				table2.setBounds(7, 247, 787, 172);
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
					tableItem9 = new TableItem(table2, SWT.NONE);
					tableItem9.setText("A");
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
				table3.setBounds(7, 430, 787, 172);
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
					tableItem17 = new TableItem(table3, SWT.NONE);
					tableItem17.setText("A");
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
			pack();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	private void menuSaveAsWidgetSelected(SelectionEvent evt) {
		System.out.println("menuSaveAs.widgetSelected, event="+evt);
	}
	
	private void menuQuitWidgetSelected(SelectionEvent evt) {
		System.out.println("menuQuit.widgetSelected, event="+evt);
		System.exit(1);
	}
	
	private void menuSourceWidgetSelected(SelectionEvent evt) {
		System.out.println("menuSource.widgetSelected, event="+evt);
	}
	
	private void menuScanWidgetSelected(SelectionEvent evt) {
		//System.out.printf("Twain Avaliable:%i\n",scanlib.slIsTwainAvailable());
		//ProcessingDialog pd = new ProcessingDialog();
		//pd.showMessage();
		String msg = String.format("%1d,%1d,%1d", scanlib.slIsTwainAvailable(),scanlib.slSelectSourceAsDefault(),scanlib.slScanImage(75,0,0,0,0, "scan.bmp"));
		//pd.done();
		
		debugDialog.open(msg);
	}
	
	private void menuAlignWidgetSelected(SelectionEvent evt) {
		System.out.println("menuAlign.widgetSelected, event="+evt);
		
	}
	
	
	private void container1BtnMouseDown(MouseEvent evt) {
		System.out.println("container1Btn.mouseDown, event="+evt);
		container1Btn.setText("1");
	}

}
