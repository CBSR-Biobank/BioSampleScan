package edu.ualberta.med.biosamplescan;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class View extends ViewPart {
	public static final String ID = "edu.ualberta.med.biosamplescan.view";
	
	Composite client;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
	    client = new Composite(parent, SWT.BORDER);
	    GridLayout gd = new GridLayout(1, true);
	    client.setLayout(gd);
	    Button btn = new Button(client, SWT.NONE);
	    btn.setText("Acquire");
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	    client.setFocus();
	}
}