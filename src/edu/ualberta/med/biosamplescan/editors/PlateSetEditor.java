package edu.ualberta.med.biosamplescan.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.gui.ViewComposite;
import edu.ualberta.med.biosamplescan.model.PlateSet;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scanlib.ScanLibFactory;

public class PlateSetEditor extends EditorPart {

	private PlateSet plateSet;

	private ViewComposite viewComposite;

	public static final String ID = "edu.ualberta.med.biosamplescan.editors.plateset";

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		String osname = System.getProperty("os.name");
		if (!osname.startsWith("Windows")) {
			if (ScanLibFactory.getScanLib().slIsTwainAvailable() != ScanLib.SC_SUCCESS) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						BioSampleScanPlugin.openError("TWAIN Driver Error",
								"TWAIN driver not installed on this computer.");
						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.close();
					}
				});
				return;
			}
		}
		//TODO MOVE ABOVE CODE TO TREE VIEWER VIEW WHEN READY

		plateSet = new PlateSet();
		plateSet.initPlate("Plate 1", 13, 8);
		plateSet.initPlate("Plate 2", 13, 8);
		plateSet.initPlate("Plate 3", 13, 8);
		plateSet.initPlate("Plate 4", 13, 8);
		viewComposite = new ViewComposite(parent, SWT.BORDER);
	}

	public ViewComposite getViewComposite() {
		if (viewComposite == null) {
			System.out.printf("FAIL!");
		}
		return viewComposite;
	}

	public PlateSet getPlateSet() {
		return plateSet;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
