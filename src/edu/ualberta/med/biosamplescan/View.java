package edu.ualberta.med.biosamplescan;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import edu.ualberta.med.biosamplescan.model.Main;

public class View extends ViewPart {
	public static final String ID = "edu.ualberta.med.biosamplescan.view";

	private Main main;

	public View() {
		BioSampleScanPlugin.getDefault().addView(this);
	}

	@Override
	public void dispose() {
		BioSampleScanPlugin.getDefault().removeView(this);

	}

	public void createPartControl(Composite parent) {
		main = new Main(parent, SWT.BORDER);
	}

	public Main getMain() {
		return main;
	}

	@Override
	public void setFocus() {
		main.setFocus();
	}

}