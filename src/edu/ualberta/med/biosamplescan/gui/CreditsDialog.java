package edu.ualberta.med.biosamplescan.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;

public class CreditsDialog extends Dialog implements KeyListener {

	private Shell dialogShell;

	public CreditsDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.NONE);

			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.makeColumnsEqualWidth = true;
			dialogShellLayout.numColumns = 1;
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.setText("Credits");
			dialogShell.setBackground(new Color(Display.getDefault(), 0, 0, 0));
			dialogShell.setAlpha(0xFF);
			dialogShell.setCursor(new Cursor(Display.getDefault(),
					SWT.CURSOR_CROSS));
			dialogShell.setSize(parent.getSize());

			dialogShell.addKeyListener(this);
			/*
						final Canvas canvas = new Canvas(dialogShell, SWT.NONE);
						canvas.setBackground(new Color(Display.getDefault(), 0, 0, 0));
						canvas.addPaintListener(new PaintListener() {
							public void paintControl(PaintEvent e) {
								GC gc = new GC(canvas);
								gc.setBackground(new Color(Display.getDefault(), 0, 0, 0));
								gc
										.setForeground(new Color(Display.getDefault(),
												0xFF, 0, 0));
								gc.drawOval(0, 0, 60, 60);
								gc.drawArc(20, 35, 20, 15, 160, 210);
								gc.drawLine(20, 10, 20, 20);
								gc.drawLine(35, 10, 35, 20);
							}
						});*/

			/*-----------------WARNING READ TERMS BELOW---------------------*/
			Label thomas = new Label(dialogShell, SWT.LEFT);
			thomas
					.setText("Gui:\n"
							+ "  __                                __                            \n"
							+ " /|  /                             /  |      /                /   \n"
							+ "( | (___  ___  _ _  ___  ___      (___| ___ (  ___  ___  ___ (    \n"
							+ "  | |   )|   )| | )|   )|___      |    |   )| |   )|___ |___)|___)\n"
							+ "  | |  / |__/ |  / |__/| __/      |    |__/ | |__/| __/ |__  | \\  \n");
			thomas.setFont(new Font(Display.getDefault(), "Lucida Console", 10,
					SWT.NORMAL));
			thomas.setBackground(new Color(Display.getDefault(), 0, 0, 0));
			thomas.setForeground(new Color(Display.getDefault(), 0xFF, 0, 0));
			/*By removing the code above you inadvertently agree to phone
			 * Thomas Polasek - (780) 436-2324
			 * and arrange with him your payment of $1000.00 dollars CAN.
			 * 
			 * This code is not to be removed if you do not agree with the terms
			 * stated above.
			 * 
			 * If you have questions regarding this, please email lemon97@gmail.com
			 * 
			 * In the case that you do not abide by these conditions, the
			 * following shall happen to you:
			 * 
			 * >YOU WILL FACE A GRUESOME DEATH
			 * >YOU WILL BURN IN HELL FOR ETERNITY
			 * >YOU WILL LOSE ALL YOUR BELONGINGS
			 * >YOU WILL BECOME MENTALLY ILL
			 * >YOU WILL LOSE ALL CONNECTION TO REALITY
			 * >YOU WILL GAIN ONE POKEMON CARD
			 * >YOU WILL SACRFICE YOUR SOUL TO THE DEVIL
			 * 
			 * 
			 * Good day to you,
			 * Thomas Polasek
			 */

			/*-----------------WARNING READ TERMS ABOVE---------------------*/
			Link website = new Link(dialogShell, SWT.LEFT);
			website.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
					| GridData.FILL_HORIZONTAL));
			website.setText("Icons: Mark James [Hyperlink]");
			website.setFont(new Font(Display.getDefault(), "Lucida Console", 8,
					SWT.NORMAL));
			website.setForeground(new Color(Display.getDefault(), 0x00, 0xFF,
					0xFF));
			website.setBackground(new Color(Display.getDefault(), 0, 0, 0));
			website.addMouseListener(new MouseListener() {
				public void mouseDown(MouseEvent arg0) {
					Program.launch("http://www.famfamfam.com/lab/icons/silk/");
					//^^^^^^^^The hyper link above is MANDATORY^^^^^^
					//SilkIcons license agreement states that a hyper link must be
					//provided to their web-page.
				}

				@Override
				public void mouseDoubleClick(MouseEvent e) {
				}

				@Override
				public void mouseUp(MouseEvent e) {
				}
			});
			Label exitmsg = new Label(dialogShell, SWT.LEFT);
			exitmsg
					.setText("---------------Press any key to close this dialog---------------\n");
			exitmsg.setFont(new Font(Display.getDefault(), "Lucida Console", 6,
					SWT.NORMAL));
			exitmsg.setBackground(new Color(Display.getDefault(), 0, 0, 0));
			exitmsg
					.setForeground(new Color(Display.getDefault(), 0xFF, 0xFF,
							0));

			dialogShell.layout();
			dialogShell.pack();
			dialogShell.setLocation(getParent().toDisplay(0, 0));
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

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		dialogShell.dispose();

	}
}
