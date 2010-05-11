package edu.ualberta.med.biosamplescan.widgets;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import edu.ualberta.med.scannerconfig.scanlib.ScanCell;

public class PalletLabelProvider extends LabelProvider implements
    ITableLabelProvider {

    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    @Override
    public String getColumnText(Object element, int columnIndex) {
        if (element instanceof PalletModel) {
            PalletModel item = (PalletModel) element;
            if (columnIndex == 0)
                return item.rowLabel;

            if (item.o == null)
                return "";

            Assert.isTrue((item.o instanceof ScanCell[]), "invalid class: "
                + item.o.getClass());

            ScanCell[] rowBarcodes = (ScanCell[]) item.o;

            Assert.isTrue(columnIndex <= rowBarcodes.length,
                "Invalid size for row barcodes: " + rowBarcodes.length);
            if (rowBarcodes[columnIndex - 1] == null) {
                return "";
            }
            return rowBarcodes[columnIndex - 1].getValue();
        } else {
            Assert.isTrue(false, "invalid object type: " + element.getClass());
        }
        return "";
    }

}
