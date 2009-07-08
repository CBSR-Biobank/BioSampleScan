package edu.ualberta.med.biosamplescan;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

import edu.ualberta.med.biosamplescan.gui.*;

public class View extends ViewPart {
	public static final String ID = "edu.ualberta.med.biosamplescan.view";
	
	Composite client;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
	    client = new Composite(parent, SWT.NONE);
	    Main xx = new Main(client, SWT.BORDER);
	    
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	    client.setFocus();
	}

}
/*
 * 				
				TableItem item = new TableItem(table1, SWT.NONE);
			    item.setText(new String[] { "A", "", "" });
			    item.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
 */