package edu.ualberta.med.biosamplescan.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import edu.ualberta.med.biosamplescan.model.Pallet;

public class PalletTableWidget extends Composite {

    private static final String[] headings =
        { "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };

    private static final int[] bounds =
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
    // { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };

    private List<PalletTableModel> model;

    private TableViewer tableViewer;

    public PalletTableWidget(Composite parent, int style) {
        super(parent, style);

        setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        setLayout(new GridLayout(1, false));

        tableViewer =
            new TableViewer(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL
                | SWT.VIRTUAL);
        tableViewer.setLabelProvider(new PalletLabelProvider());
        tableViewer.setContentProvider(new ArrayContentProvider());

        Table table = tableViewer.getTable();
        table.setLayout(new TableLayout());
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.heightHint = 160;
        table.setLayoutData(gd);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        int index = 0;
        for (String name : headings) {
            final TableColumn col = new TableColumn(table, SWT.NONE);
            col.setText(name);
            if (bounds == null || bounds[index] == -1) {
                col.pack();
            }
            else {
                col.setWidth(bounds[index]);
            }
            col.setResizable(true);
            col.addListener(SWT.SELECTED, new Listener() {
                public void handleEvent(Event event) {
                    col.pack();
                }
            });
            index++;
        }
        tableViewer.setColumnProperties(headings);
        tableViewer.setUseHashlookup(true);

        model = new ArrayList<PalletTableModel>();
        for (int i = 0; i < 8; ++i) {
            model.add(new PalletTableModel(String.valueOf((char) ('A' + i))));
        }
        tableViewer.setInput(model);
    }

    public TableViewer getTableViewer() {
        return tableViewer;
    }

    public void setPalletBarcodes(final Pallet pallet) {
        if (pallet == null)
            return;

        Thread t = new Thread() {
            @Override
            public void run() {
                if (getTableViewer().getTable().isDisposed())
                    return;

                PalletTableModel modelItem;
                String[][] barcodes = pallet.getBarcode();
                int rowCount = 0;

                for (String[] row : barcodes) {
                    modelItem = model.get(rowCount);
                    modelItem.o = row;
                    ++rowCount;
                }

                getTableViewer().getTable().getDisplay().asyncExec(
                    new Runnable() {
                        public void run() {
                            getTableViewer().refresh();
                        }
                    });
            }
        };
        t.start();
    }
}
