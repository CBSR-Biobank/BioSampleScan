package edu.ualberta.med.biosamplescan.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class ToolbarX extends org.eclipse.swt.widgets.Composite {

	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = display.getActiveShell();
		ToolBar toolbar = new ToolBar(shell, SWT.FLAT | SWT.BORDER);
		Point size = toolbar.getSize();
		for (int i = 0; i < 12; i++) {
			ToolItem item = new ToolItem(toolbar, SWT.DROP_DOWN);
			//item.setImage(null);
			item.setText("A");
		}
		if (size.x == 0 && size.y == 0) {
			toolbar.pack();
			shell.pack();
		}
		else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}

	}

	public ToolbarX(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(new FormLayout());
			this.layout();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
