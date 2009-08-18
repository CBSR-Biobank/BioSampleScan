
package edu.ualberta.med.biosamplescan.editors;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import edu.ualberta.med.biosamplescan.widgets.AllPalletsWidget;

public class PlateSetEditor extends EditorPart {

    private AllPalletsWidget allPalletsWidget;

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
        this.setPartName(new SimpleDateFormat("E dd/MM/yyyy HH:mm:ss").format(new Date()));

    }

    @Override
    public boolean isDirty() {
        return false;
    }

    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    @Override
    public void createPartControl(Composite parent) {
        allPalletsWidget = new AllPalletsWidget(parent, SWT.BORDER);
    }

    public AllPalletsWidget getPalletsWidget() {
        return allPalletsWidget;
    }

    @Override
    public void setFocus() {
        allPalletsWidget.setFocus();
    }

    public void refreshPallet(int pallet) {
        allPalletsWidget.refreshPallet(pallet);
    }

}
