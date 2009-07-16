package edu.ualberta.med.biosamplescan.rcp;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		// layout.addView(View.ID, IPageLayout.LEFT, 0.0f,
		// layout.getEditorArea());

		// layout.getViewLayout(View.ID).setCloseable(false);
		// layout.addView(Option.ID, IPageLayout.LEFT, 0.0f, layout
		// .getEditorArea());
		// layout.getViewLayout(Option.ID).setCloseable(false);
		// layout.setEditorAreaVisible(false);
	}
}
