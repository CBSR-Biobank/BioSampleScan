
package edu.ualberta.med.biosamplescan.widgets;

import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
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

public class PalletTableWidget extends Composite {

    private static final String [] headings = {
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };

    private static final int [] bounds = {
        50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };

    private List<SampleTableModel> model;

    private TableViewer tableViewer;

    public PalletTableWidget(Composite parent, int style, Object [] data) {
        super(parent, style);

        setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        setLayout(new GridLayout(1, false));

        tableViewer = new TableViewer(this, SWT.BORDER | SWT.H_SCROLL
            | SWT.V_SCROLL | SWT.VIRTUAL);
        tableViewer.setLabelProvider(new LabelProvider());
        tableViewer.setContentProvider(new ArrayContentProvider());

        Table table = tableViewer.getTable();
        table.setLayout(new TableLayout());
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.heightHint = 100;
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
        if (data != null) {
            tableViewer.setInput(data);
        }

        for (int i = 0, n = headings.length; i < n; ++i) {
            model.add(new SampleTableModel("" + (i + 1)));
        }
    }

    public TableViewer getTableViewer() {
        return tableViewer;
    }

    public void setPalletSampleIds(final Collection<T> collection) {
        if (collection == null) return;

        Thread t = new Thread() {
            @Override
            public void run() {
                if (getTableViewer().getTable().isDisposed()) return;

                BiobankCollectionModel modelItem;
                model.clear();

                for (T item : collection) {
                    modelItem = new BiobankCollectionModel();
                    model.add(modelItem);
                    modelItem.o = item;
                }

                getTableViewer().getTable().getDisplay().asyncExec(
                    new Runnable() {
                        public void run() {
                            getTableViewer().refresh();

                            // only notify listeners if collection has been
                            // assigned other than by constructor
                            if (setCollectionCount > 0) InfoTableWidget.this.notifyListeners();
                            ++setCollectionCount;
                        }
                    });
            }
        };
        t.start();
    }
}
