package edu.ualberta.med.biosamplescan.widgets;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.listeners.EnterKeyToNextFieldListener;

public class PalletBarcodesWidget extends Composite {

    private Text[] textPlateId;

    public PalletBarcodesWidget(Composite parent, int style) {
        super(parent, style);

        setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        setLayout(new GridLayout(1, false));

        int palletsMax = BioSampleScanPlugin.getDefault().getPalletsMax();

        Group g = new Group(this, SWT.NONE);
        g.setText("Pallet bar codes");
        g.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        g.setLayout(new GridLayout(2 * palletsMax, false));

        GC gc = new GC(this);
        FontMetrics fm = gc.getFontMetrics();

        textPlateId = new Text[palletsMax];
        for (int i = 0; i < palletsMax; ++i) {
            Label l = new Label(g, SWT.NONE);
            l.setText("Pallet " + (i + 1) + ":");
            textPlateId[i] = new Text(g, SWT.BORDER);
            GridData gd = new GridData();
            gd.widthHint = 15 * fm.getAverageCharWidth();
            textPlateId[i].setTextLimit(15);
            textPlateId[i].addKeyListener(EnterKeyToNextFieldListener.INSTANCE);
            textPlateId[i].setEnabled(BioSampleScanPlugin.getDefault()
                .getPalletEnabled(i + 1));
        }
    }

    public void setEnabled(int pallet, boolean enabled) {
        Assert.isTrue((pallet >= 0) && (pallet <= textPlateId.length),
            "pallet number is invalid: " + pallet);
        textPlateId[pallet].setEnabled(enabled);
    }

    public String getPalletBarcode(int pallet) {
        Assert.isTrue((pallet >= 0) && (pallet <= textPlateId.length),
            "pallet number is invalid: " + pallet);
        return textPlateId[pallet].getText();
    }

    public boolean isSelected(int pallet) {
        Assert.isTrue((pallet >= 0) && (pallet <= textPlateId.length),
            "pallet number is invalid: " + pallet);
        return (textPlateId[pallet].getText().length() > 0);
    }

    public void clearText() {
        for (Text t : textPlateId) {
            t.setText("");
        }
    }

}
