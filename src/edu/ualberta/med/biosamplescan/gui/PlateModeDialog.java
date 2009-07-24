package edu.ualberta.med.biosamplescan.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import edu.ualberta.med.biosamplescan.singleton.ConfigSettings;

public class PlateModeDialog extends Dialog {

	private Shell dialogShell;
	private Group group1;
	private Group group2;
	private Button[] ratioBtns;

	private Button cancleBtn;
	private Button okBtn;
	private int plateMode;

	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			PlateModeDialog inst = new PlateModeDialog(shell, SWT.NULL);
			inst.open();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void placeDialogInCenter(Shell parent, Shell shell) {
		Rectangle parentSize = parent.getBounds();
		Rectangle mySize = shell.getBounds();

		int locationX, locationY;
		locationX = (parentSize.width - mySize.width) / 2 + parentSize.x;
		locationY = (parentSize.height - mySize.height) / 2 + parentSize.y;

		shell.setLocation(new Point(locationX, locationY));
	}

	public PlateModeDialog(Shell parent, int style) {
		super(parent, style);
	}

	public int open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL);

			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.makeColumnsEqualWidth = true;
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.setText("Set Plate Mode");
			placeDialogInCenter(getParent(), dialogShell);
			ratioBtns = new Button[ConfigSettings.PLATENUM];
			{
				group1 = new Group(dialogShell, SWT.NONE);
				RowLayout group1Layout = new RowLayout(
						org.eclipse.swt.SWT.HORIZONTAL);
				group1.setLayout(group1Layout);
				GridData group1LData = new GridData();
				group1LData.widthHint = 180;
				group1LData.heightHint = 21;
				group1.setLayoutData(group1LData);
				group1.setText("Mode:");
				{
					for (int i = 0; i < ratioBtns.length; i++) {
						ratioBtns[i] = new Button(group1, SWT.RADIO | SWT.LEFT);
						RowData button1LData = new RowData();
						ratioBtns[i].setLayoutData(button1LData);
						ratioBtns[i].setText(String.valueOf(i + 1));
						ratioBtns[i].setSelection(false);
					}
					if (ConfigSettings.getInstance().getPlatemode() > 0) {
						ratioBtns[ConfigSettings.getInstance().getPlatemode() - 1]
								.setSelection(true);
					}
				}
			}
			{
				GridData group2LData = new GridData();
				group2LData.widthHint = 180;
				group2LData.heightHint = 27;
				group2 = new Group(dialogShell, SWT.NONE);
				FillLayout group2Layout = new FillLayout(
						org.eclipse.swt.SWT.HORIZONTAL);
				group2.setLayout(group2Layout);
				group2.setLayoutData(group2LData);
				{
					okBtn = new Button(group2, SWT.PUSH | SWT.CENTER);
					okBtn.setText("OK");
					okBtn.setSelection(true);
					okBtn.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							okBtnWidgetSelected(evt);
						}
					});
				}
				{
					cancleBtn = new Button(group2, SWT.PUSH | SWT.CENTER);
					cancleBtn.setText("Cancle");
					cancleBtn.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							cancleBtnWidgetSelected(evt);
						}
					});
				}
			}
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
		return plateMode;
	}

	private void okBtnWidgetSelected(SelectionEvent evt) {
		for (int i = 0; i < ratioBtns.length; i++) {
			if (ratioBtns[i].getSelection()) {
				plateMode = i + 1;
				break;
			}
		}
		dialogShell.dispose();
	}

	private void cancleBtnWidgetSelected(SelectionEvent evt) {
		plateMode = 0;
		dialogShell.dispose();
	}

}
