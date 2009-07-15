package edu.ualberta.med.biosamplescan;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import edu.ualberta.med.biosamplescan.gui.Main;

public class View extends ViewPart {
	public static final String ID = "edu.ualberta.med.biosamplescan.view";

	Composite client;

	public void createPartControl(Composite parent) {
		client = new Composite(parent, SWT.NONE);
		new Main(client, SWT.BORDER);

	}

	public void setFocus() {
		client.setFocus();
	}

}