package edu.ualberta.med.biosamplescan.gui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.View;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.PlateSet;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scanlib.ScanLibFactory;

public class ViewComposite extends org.eclipse.swt.widgets.Composite {
	private Button loadFromFile;
	private Button reScanPlateBtn;
	private Button scanPlateBtn;
	private Button clearPlateBtn;
	private Button[] plateBtn;
	private Table[] tables;
	private TableColumn[][] tableColumns;
	private String lastSaveSelectLocation;
	private TableItem[][] tableItems;

	public ViewComposite(Composite parent, int style) {
		super(parent, style);
		SWTManager.registerResourceUser(this);
		initGUI();
	}

	public Shell getActiveShell() {
		return this.getShell();
	}

	private void initGUI() {
		try {
			this.setLayout(null);
			this.layout();
			pack();
			{
				plateBtn = new Button[ConfigSettings.PLATENUM];
				tables = new Table[ConfigSettings.PLATENUM];
				tableColumns = new TableColumn[ConfigSettings.PLATENUM][ConfigSettings.PLATENUM * 13];
				tableItems = new TableItem[ConfigSettings.PLATENUM][ConfigSettings.PLATENUM * 8];

				for (int table = 0; table < ConfigSettings.PLATENUM; table++) {

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
			for (int i = 0; i < ConfigSettings.PLATENUM; i++) {
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
			{//TODO this button is only for debugging purposes
				loadFromFile = new Button(this, SWT.PUSH | SWT.CENTER);
				loadFromFile.setText("Load From File");
				loadFromFile.setBounds(380, 6, 90, 40);
				loadFromFile.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						PlateSet plateSet = ((View) PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage()
								.getActivePart()).getPlateSet();
						for (int i = 0; i < ConfigSettings.PLATENUM; i++) {
							plateSet.loadFromScanlibFile(i + 1, false);
							fillTablesFromPlateSet(i + 1);
						}
					}
				});
			}
			pack();
			this.setSize(900, 700);

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
		PlateSet plateSet = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getPlateSet();

		for (int r = 0; r < 8; r++) {
			tableItems[plate - 1][r].setText(plateSet.getPlate(String.format(
					"Plate %d", plate))[r]);
		}

	}

	public void clearPlateBtnWidgetSelected(SelectionEvent evt) {
		if (confirmMsg("Clear Table(s)",
				"Do you want to clear the selected tables?")) {
			for (int p = 0; p < ConfigSettings.PLATENUM; p++) {
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

	public void scanPlateBtnWidgetSelected(SelectionEvent evt, boolean append) {
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

		PlateSet plateSet = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getPlateSet();

		for (int plate = 0; plate < ConfigSettings.PLATENUM; plate++) {
			ConfigSettings configSettings = ((View) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActivePart())
					.getConfigSettings();

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
				plateSet.loadFromScanlibFile(plate + 1, append);
				this.fillTablesFromPlateSet(plate + 1);

			}
		}
	}

	public void clearTables() {
		for (int p = 0; p < ConfigSettings.PLATENUM; p++) {
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 12; c++) {
					tableItems[p][r].setText(c + 1, "");
				}
			}
		}
	}

	public void setPlateMode(int platecount) {
		boolean set = false;
		for (int table = 0; table < ConfigSettings.PLATENUM; table++) {
			set = (table < platecount);
			tables[table].setEnabled(set);
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

	public boolean getPlateBtnSelection(int platenum) {
		return plateBtn[platenum].getSelection();
	}

	public void setLastSaveSelectLocation(String lastSaveSelectLocation) {
		this.lastSaveSelectLocation = lastSaveSelectLocation;
	}

	public String getLastSaveSelectLocation() {
		return lastSaveSelectLocation;
	}
}
