package edu.ualberta.med.biosamplescan.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;

public class CreditsDialog extends Dialog {

	private Shell dialogShell;

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

	public CreditsDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL);

			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.makeColumnsEqualWidth = true;
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.setText("Credits");
			dialogShell.setSize(400, 300);
			dialogShell.setBackground(new Color(Display.getDefault(), 0, 0, 0));
			dialogShell.setAlpha(220);
			dialogShell.setCursor(new Cursor(Display.getDefault(),
					SWT.CURSOR_CROSS));

			Label thomas = new Label(dialogShell, SWT.LEFT);
			thomas.setText("Gui: Thomas Polasek");
			thomas.setBackground(new Color(Display.getDefault(), 0, 0, 0));
			thomas.setForeground(new Color(Display.getDefault(), 0xFF, 0, 0));

			/*
			Silk icon set 1.3

			_________________________________________
			Mark James
			http://www.famfamfam.com/lab/icons/silk/
			_________________________________________

			This work is licensed under a
			Creative Commons Attribution 2.5 License.
			[ http://creativecommons.org/licenses/by/2.5/ ]

			This means you may use it for any purpose,
			and make any changes you like.
			All I ask is that you include a link back
			to this page in your credits.

			Are you using this icon set? Send me an email
			(including a link or picture if available) to
			mjames@gmail.com

			Any other questions about this icon set please
			contact mjames@gmail.com	 
			 */
			Link website = new Link(dialogShell, SWT.LEFT);
			website.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
					| GridData.FILL_HORIZONTAL));
			website.setText("<a>Icons: Mark James</a>"); //$NON-NLS-1$
			website.setForeground(new Color(Display.getDefault(), 0x00, 0xFF,
					0xFF));
			website.setBackground(new Color(Display.getDefault(), 0, 0, 0));
			website.addMouseListener(new MouseListener() {
				public void mouseDown(MouseEvent arg0) {
					Program.launch("http://www.famfamfam.com/lab/icons/silk/");
				}

				@Override
				public void mouseDoubleClick(MouseEvent e) {
				}

				@Override
				public void mouseUp(MouseEvent e) {
				}
			});
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
	}
}
