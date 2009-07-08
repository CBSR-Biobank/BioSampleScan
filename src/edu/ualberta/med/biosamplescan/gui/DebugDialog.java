package edu.ualberta.med.biosamplescan.gui;

import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;

public class DebugDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Text debugMsg;
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
	

	public DebugDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open(String debugMessage) {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.setText("Debug");
			{
				FormData debugMsgLData = new FormData();
				debugMsgLData.width = 220;
				debugMsgLData.height = 195;
				debugMsgLData.left =  new FormAttachment(0, 1000, 0);
				debugMsgLData.top =  new FormAttachment(0, 1000, 0);
				debugMsg = new Text(dialogShell, SWT.MULTI | SWT.WRAP);
				debugMsg.setLayoutData(debugMsgLData);
				debugMsg.setEditable(false);
				debugMsg.setText(debugMessage);
			}
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(234, 222);
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
	
}
