package edu.ualberta.med.biosamplescan.gui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.editors.PlateSetEditor;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.PlateSet;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scanlib.ScanLibFactory;

public class ViewComposite extends ScrolledComposite {

	private Composite top;

	private static final int fontSize = 7;
	private Button loadFromFile;
	private Button reScanPlateBtn;
	private Button scanPlateBtn;
	private Button clearPlateBtn;
	private Button[] plateBtn;
	private Button[] clearBtns;
	private Table[] tables;

	private TableColumn[][] tableColumns;
	private TableItem[][] tableItems;
	private Text[] plateIdText;

	public ViewComposite(Composite parent, int style) {
		super(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		this.addControlListener(new ControlListener() {

			@Override
			public void controlMoved(ControlEvent e) {
			}

			@Override
			public void controlResized(ControlEvent e) {

			}

		});
		initGUI();
	}

	public Shell getActiveShell() {
		return this.getShell();
	}

	public boolean setFocus() { /*reload global ui states*/
		setPlateMode();
		return top.setFocus();
	}

	private void initGUI() {
		try {
			setExpandHorizontal(true);
			setExpandVertical(true);
			this.getVerticalBar().setIncrement(10);
			this.getHorizontalBar().setIncrement(10);

			top = new Composite(this, SWT.NONE);
			top.setLayout(new GridLayout(1, false));

			Composite section = new Composite(top, SWT.NONE);
			section.setLayout(new GridLayout(4, false));
			section.setLayoutData(new GridData(
					GridData.HORIZONTAL_ALIGN_BEGINNING));
			{
				clearPlateBtn = new Button(section, SWT.PUSH | SWT.CENTER);
				clearPlateBtn.setText("Clear Selected Table(s)");
				//clearPlateBtn.setBounds(488, 6, 90, 40);
				clearPlateBtn.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						clearPlateBtnWidgetSelected(evt);

					}
				});
			}
			{
				reScanPlateBtn = new Button(section, SWT.PUSH | SWT.CENTER);
				reScanPlateBtn.setText("Re-Scan Selected Plate(s)");
				//reScanPlateBtn.setBounds(596, 6, 90, 40);
				reScanPlateBtn.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						scanPlateBtnWidgetSelected(evt, true);
					}
				});
			}
			{//TODO this button is only for debugging purposes
				loadFromFile = new Button(section, SWT.PUSH | SWT.CENTER);
				loadFromFile.setText("Load From File");
				//loadFromFile.setBounds(380, 6, 90, 40);
				loadFromFile.setVisible(true); //TODO REMOVE
				loadFromFile.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						PlateSet plateSet = ((PlateSetEditor) PlatformUI
								.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage().getActivePart()).getPlateSet();
						for (int i = 0; i < ConfigSettings.getInstance()
								.getPlatemode(); i++) {
							plateSet.loadFromScanlibFile(i + 1, false);
							fillTablesFromPlateSet(i + 1);
						}
					}
				});
			}

			{
				scanPlateBtn = new Button(section, SWT.PUSH | SWT.CENTER);
				scanPlateBtn.setText("Scan Selected Plate(s)");
				//scanPlateBtn.setBounds(698, 6, 90, 40);
				scanPlateBtn.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						scanPlateBtnWidgetSelected(evt, false);
					}
				});
			}
			//pack();
			{
				clearBtns = new Button[ConfigSettings.PLATENUM];
				plateBtn = new Button[ConfigSettings.PLATENUM];
				tables = new Table[ConfigSettings.PLATENUM];
				tableColumns = new TableColumn[ConfigSettings.PLATENUM][ConfigSettings.PLATENUM * 13];
				tableItems = new TableItem[ConfigSettings.PLATENUM][ConfigSettings.PLATENUM * 8];
				plateIdText = new Text[ConfigSettings.PLATENUM];

				for (int table = 0; table < ConfigSettings.PLATENUM; table++) {
					section = new Composite(top, SWT.NONE);
					section.setLayout(new GridLayout(1, false));

					Composite subSection = new Composite(section, SWT.NONE);
					subSection.setLayout(new GridLayout(4, false));

					plateBtn[table] = new Button(subSection, SWT.CHECK);
					plateBtn[table].setText(String
							.format("Plate %d", table + 1));
					//plateBtn[table].setBounds(5 + 63 * table, 5, 63, 18);
					plateBtn[table].setSelection(true);

					Label l = new Label(subSection, SWT.NONE);
					l.setText("Plate Id:");
					//l.setBounds(8, 32, 40, 18);

					plateIdText[table] = new Text(subSection, SWT.BORDER);
					plateIdText[table].setTextLimit(15);
					//plateIdText[table].setBounds(5 + 100 * table + 50, 30, 90,
					//		18);

					clearBtns[table] = new Button(subSection, SWT.NONE);
					clearBtns[table].setText("Clear");
					{
						final int ftable = table + 1;
						clearBtns[table]
								.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(
											SelectionEvent evt) {
										if (confirmMsg(
												"Clear Platetext and Clear PlateTable",
												"Are you sure you want to clear the plate?")) {
											clearPlateTable(ftable);
										}

									}
								});
					}
					plateIdText[table].addKeyListener(new KeyListener() {
						@Override
						public void keyReleased(KeyEvent e) {

							PlateSet plateSet = ((PlateSetEditor) PlatformUI
									.getWorkbench().getActiveWorkbenchWindow()
									.getActivePage().getActivePart())
									.getPlateSet();

							for (int i = 0; i < ConfigSettings.PLATENUM; i++) {

								if (plateSet.getPlateId(i + 1).length() > 0) {
									boolean emptyTable = true;
									for (int y = 0; y < plateSet
											.getPlateDim(i + 1).height; y++) {
										for (int x = 0; x < plateSet
												.getPlateDim(i + 1).width; x++) {
											try {
												if (plateSet.getPlate(i + 1) != null
														&& plateSet
																.getPlate(i + 1)[x][y] != null
														&& !plateSet
																.getPlate(i + 1)[x][y]
																.isEmpty()) {
													emptyTable = false;
													break;
												}
											}
											catch (NullPointerException e1) {
												continue;
											}
										}
									}
									if (!emptyTable) {

										plateIdText[i].setText(plateSet
												.getPlateId(i + 1));
										continue;
									}

								}

								plateSet.setPlateId(i + 1, plateIdText[i]
										.getText());
							}
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});

					tables[table] = new Table(section, SWT.FULL_SELECTION
							| SWT.EMBEDDED | SWT.V_SCROLL | SWT.BORDER);
					tables[table].setLinesVisible(true);
					tables[table].setHeaderVisible(true);

					tables[table].setBackground(new Color(getDisplay(), 0xCC,
							0xCC, 0xCC));
					tables[table].setFont(new Font(getDisplay(), "Calibri",
							ViewComposite.fontSize, SWT.NONE));
					tableColumns[table][0] = new TableColumn(tables[table],
							SWT.NONE);
					tableColumns[table][0].setWidth(ViewComposite.fontSize * 10
							- tables[table].getGridLineWidth() * 10);
					tableColumns[table][0].setResizable(true);
					for (int i = 0; i < 12; i++) {
						tableColumns[table][i + 1] = new TableColumn(
								tables[table], SWT.NONE);
						tableColumns[table][i + 1].setText(String.format("%d",
								i + 1));
						tableColumns[table][i + 1].setWidth((this.getShell()
								.getBounds().width - 25) / 12 - 6);
					}
					for (int i = 0; i < 8; i++) {
						tableItems[table][i] = new TableItem(tables[table],
								SWT.NONE);
						tableItems[table][i].setText(Character
								.toString((char) (65 + i)));
						if (i % 2 != 0) {
							tableItems[table][i].setBackground(new Color(
									getDisplay(), 200, 200, 200));
						}
						else if (i % 3 == 0) {
							tableItems[table][i].setBackground(new Color(
									getDisplay(), 170, 170, 170));
						}
						else {
							tableItems[table][i].setBackground(new Color(
									getDisplay(), 230, 230, 230));
						}

					}
				}
			}
			this.setPlateMode();
			top.layout();
			top.pack();
			setContent(top);

			setMinWidth(top.getBounds().width);
			setMinHeight(top.getBounds().height);

		}
		catch (Exception e) {
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
		}
		else {
			MessageDialog.openError(getShell(), "Error", Identifier);
		}
	}

	public void fillTablesFromPlateSet(int plate) {
		PlateSet plateSet = ((PlateSetEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getPlateSet();
		for (int r = 0; r < 8; r++) {
			tableItems[plate - 1][r].setText(plateSet.getPlate(plate)[r]);

		}

	}

	public void clearPlateBtnWidgetSelected(SelectionEvent evt) {
		PlateSet plateSet = ((PlateSetEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getPlateSet();
		if (confirmMsg("Clear Table(s)",
				"Do you want to clear the selected tables?")) {
			for (int p = 0; p < ConfigSettings.PLATENUM; p++) {
				if (plateBtn[p].getSelection()) {
					for (int r = 0; r < 8; r++) {
						for (int c = 0; c < 12; c++) {
							tableItems[p][r].setText(c + 1, "");
							plateSet.setPlate(p + 1, null); //TODO TEST THIS!!!!!
							plateIdText[p].setText("");
						}
					}
				}

			}
		}
	}

	public void scanPlateBtnWidgetSelected(SelectionEvent evt, boolean rescan) {
		boolean pass = false;
		for (int i = 0; i < ConfigSettings.PLATENUM; i++) {
			if (plateBtn[i].getSelection()) {
				pass = true;
				break;
			}
		}
		if (!pass) {
			errorMsg("No Plates Selected", 0);
			return;
		}

		for (int i = 0; i < ConfigSettings.PLATENUM; i++) {
			if (plateBtn[i].getSelection()) {
				String plateId = plateIdText[i].getText();
				if (plateId == null || plateId.isEmpty()) {
					errorMsg(String.format("Plate %d must have a plate id",
							i + 1), 0);
					return;
				} //TODO check and add plateid for all save routines
			}
		}

		PlateSet plateSet = ((PlateSetEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getPlateSet();

		for (int plate = 0; plate < ConfigSettings.PLATENUM; plate++) {
			ConfigSettings configSettings = ConfigSettings.getInstance();

			if (configSettings.getPlate(plate + 1)[0]
					+ configSettings.getPlate(plate + 1)[1]
					+ configSettings.getPlate(plate + 1)[2]
					+ configSettings.getPlate(plate + 1)[3] > 0
					&& plateBtn[plate].getSelection()) {
				int scanlibReturn = ScanLibFactory.getScanLib().slDecodePlate(
						configSettings.getDpi(), plate + 1);
				switch (scanlibReturn) {
					case (ScanLib.SC_SUCCESS):
						break;
					case (ScanLib.SC_INVALID_IMAGE):
						errorMsg("scanPlateBtnWidgetSelected, DecodePlate",
								scanlibReturn);
						return;
				}
				plateSet.loadFromScanlibFile(plate + 1, rescan);
				this.fillTablesFromPlateSet(plate + 1);

			}
		}
	}

	public void clearTables() {
		PlateSet plateSet = ((PlateSetEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getPlateSet();

		for (int p = 0; p < ConfigSettings.PLATENUM; p++) {
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 12; c++) {
					tableItems[p][r].setText(c + 1, "");
					plateSet.setPlate(p + 1, null); //TODO TEST THIS!!!!!
					plateIdText[p].setText("");
				}
			}
		}
	}

	private void clearPlateTable(int table) {
		plateIdText[table - 1].setText("");
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 12; c++) {
				tableItems[table - 1][r].setText(c + 1, "");
			}
		}
		PlateSetEditor pse = (PlateSetEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart();
		if (pse != null) { //During initialization of this program, pse = null.
			PlateSet ps = pse.getPlateSet();
			ps.initPlate(table, 13, 8);
		}

	}

	public void setPlateMode() {
		int platecount = ConfigSettings.getInstance().getPlatemode();
		boolean set = false;
		for (int table = 0; table < ConfigSettings.PLATENUM; table++) {
			set = (table < platecount);
			tables[table].setEnabled(set);
			tables[table].setVisible(set);
			plateBtn[table].setEnabled(set);
			plateIdText[table].setEnabled(set);

			if (!set) {
				clearPlateTable(table + 1);
				plateBtn[table].setSelection(false);
			}
		}
	}

	public boolean getPlateBtnSelection(int platenum) {
		return plateBtn[platenum].getSelection();
	}

}
