package edu.ualberta.med.biosamplescan.widgets;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class PalletLabelProvider extends LabelProvider implements
    ITableLabelProvider {

    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    @Override
    public String getColumnText(Object element, int columnIndex) {
        if (element instanceof PalletTableModel) {
            PalletTableModel item = (PalletTableModel) element;
            if (columnIndex == 0)
                return item.rowLabel;

            if (item.o == null)
                return "";

            String[] rowBarcodes = (String[]) item.o;

            Assert.isTrue(columnIndex <= rowBarcodes.length,
                "Invalid size for row barcodes: " + rowBarcodes.length);
            return rowBarcodes[columnIndex - 1];
        }
        else {
            Assert.isTrue(false, "invalid object type: " + element.getClass());
        }
        return "";
    }

}