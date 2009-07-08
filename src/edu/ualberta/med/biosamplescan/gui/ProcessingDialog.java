package edu.ualberta.med.biosamplescan.gui;

import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;

public class ProcessingDialog implements Runnable {

	private Shell processShell;
	private Text waitMsg;

	public void open(String debugMessage) {
		try {
			Shell parent = processShell;
			processShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			processShell.setLayout(new FormLayout());
			processShell.setText("Processing");
			{
				FormData waitMsgLData = new FormData();
				waitMsgLData.width = 220;
				waitMsgLData.height = 195;
				waitMsgLData.left =  new FormAttachment(0, 1000, 0);
				waitMsgLData.top =  new FormAttachment(0, 1000, 0);
				waitMsg = new Text(processShell, SWT.MULTI | SWT.WRAP);
				waitMsg.setLayoutData(waitMsgLData);
				waitMsg.setEditable(false);
				waitMsg.setText(debugMessage);
			}
			processShell.layout();
			processShell.pack();			
			processShell.setSize(234, 222);
			processShell.setLocation(processShell.toDisplay(100, 100));
			processShell.open();
			Display display = processShell.getDisplay();
			while (!processShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void showMessage() {
		Thread thread = new Thread(this);
		thread.start();
	}
	@Override
	public void run() {
		try {
			Display display = Display.getDefault();
			processShell = new Shell(display);
			this.open("Please Wait");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void done() {
		processShell.setVisible(false);
	}
}
