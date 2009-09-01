package edu.ualberta.med.biosamplescan.editors;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import edu.ualberta.med.biosamplescan.model.PalletSet;
import edu.ualberta.med.biosamplescan.widgets.PalletSetWidget;

public class PalletSetEditor extends EditorPart {

    private PalletSetWidget palletSetWidget;

    public static final String ID = "edu.ualberta.med.biosamplescan.editors.plateset";

    @Override
    public void doSave(IProgressMonitor monitor) {
    }

    @Override
    public void doSaveAs() {
    }

    @Override
    public void init(IEditorSite site, IEditorInput input)
        throws PartInitException {
        setSite(site);
        setInput(input);
        this.setPartName(new SimpleDateFormat("E dd/MM/yyyy HH:mm:ss")
            .format(new Date()));

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
        palletSetWidget = new PalletSetWidget(parent, SWT.NONE);
    }

    public PalletSet getPalletSet() {
        return null;
    }

    public PalletSetWidget getPalletSetWidget() {
        return palletSetWidget;
    }

    @Override
    public void setFocus() {
        palletSetWidget.setFocus();
    }

    public void updateStatusBar(String msg) {
        IStatusLineManager statusLine = getEditorSite().getActionBars()
            .getStatusLineManager();
        statusLine.setMessage(msg);
    }

    public void refresh() {
        palletSetWidget.refresh();
    }

}
