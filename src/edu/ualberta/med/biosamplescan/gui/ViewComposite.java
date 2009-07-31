package edu.ualberta.med.biosamplescan.gui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
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

public class ViewComposite extends Composite {

	private static final int fontSize = 7;
	private Button loadFromFile;
	private Button reScanPlateBtn;
	private Button scanPlateBtn;
	private Button clearPlateBtn;
	private Button[] plateBtn;
	private Table[] tables;
	private TableColumn[][] tableColumns;
	private TableItem[][] tableItems;
	private Text[] plateIdText;

	public ViewComposite(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	public Shell getActiveShell() {
		return this.getShell();
	}

	public boolean setFocus() { /*reload global ui states*/
		setPlateMode();
		return true;
	}

	private void initGUI() {
		try {
			this.setLayout(null);
			this.layout();
			{

				plateBtn = new Button[ConfigSettings.PLATENUM];
				tables = new Table[ConfigSettings.PLATENUM];
				tableColumns = new TableColumn[ConfigSettings.PLATENUM][ConfigSettings.PLATENUM * 13];
				tableItems = new TableItem[ConfigSettings.PLATENUM][ConfigSettings.PLATENUM * 8];
				plateIdText = new Text[ConfigSettings.PLATENUM];

				//TODO make this work
				for (int i = 0; i < ConfigSettings.PLATENUM; i++) {
					plateBtn[i] = new Button(this, SWT.CHECK);
					plateBtn[i].setText(String.format("Plate %d", i + 1));
					plateBtn[i].setBounds(5 + 63 * i, 5, 63, 18);
					plateBtn[i].setSelection(true);
				}

				Label l = new Label(this, SWT.NONE);
				l.setText("Plate Id:");
				l.setBounds(8, 32, 40, 18);

				for (int i = 0; i < ConfigSettings.PLATENUM; i++) {
					plateIdText[i] = new Text(this, SWT.BORDER);
					plateIdText[i].setTextLimit(15);
					plateIdText[i].setBounds(5 + 100 * i + 50, 30, 90, 18);
					plateIdText[i].addKeyListener(new KeyListener() {

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
				}

				for (int table = 0; table < ConfigSettings.PLATENUM; table++) {

					tables[table] = new Table(this, SWT.FULL_SELECTION
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

					int w = this.getShell().getBounds().width - 43;
					int h = (tables[table].getItemHeight()) * 8
							+ tables[table].getHeaderHeight() * 3;
					int x = 5;
					int y = 63 + table * h;

					tables[table].setBounds(x, y, w, h);
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
			{//TODO this button is only for debugging purposes
				loadFromFile = new Button(this, SWT.PUSH | SWT.CENTER);
				loadFromFile.setText("Load From File");
				loadFromFile.setBounds(380, 6, 90, 40);
				loadFromFile.setVisible(false); //TODO REMOVE
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
			pack();
			this.setPlateMode();

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
				plateIdText[table].setText("");
				plateBtn[table].setSelection(false);

				for (int r = 0; r < 8; r++) {
					for (int c = 0; c < 12; c++) {
						tableItems[table][r].setText(c + 1, "");
					}
				}
			}
		}
	}

	public boolean getPlateBtnSelection(int platenum) {
		return plateBtn[platenum].getSelection();
	}

}
