package edu.ualberta.med.biosamplescan.rcp;

import org.eclipse.swt.SWT;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import edu.ualberta.med.biosamplescan.View;


public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.addView(View.ID, IPageLayout.LEFT, 0.3f, layout.getEditorArea());
		layout.getViewLayout(View.ID).setCloseable(true);
		layout.setEditorAreaVisible(false);
	}
}
