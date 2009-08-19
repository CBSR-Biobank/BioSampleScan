
package edu.ualberta.med.biosamplescan.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import edu.ualberta.med.biosamplescan.model.Pallet;
import edu.ualberta.med.scanlib.ScanCell;

public class PalletTableWidget extends Composite {

    private static final String [] headings = {
        "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };

    // private static final int [] bounds = {
    // 10, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };

    private List<PalletModel> model;

    private TableViewer tableViewer;

    private Table table;

    private TableColumn [] tableColumns;

    private Color colorBackground;

    private int firstColWidth;

    public PalletTableWidget(Composite parent, int style) {
        super(parent, style);

        setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        setLayout(new GridLayout(1, false));

        tableViewer = new TableViewer(this, SWT.BORDER | SWT.H_SCROLL
            | SWT.V_SCROLL | SWT.VIRTUAL);
        tableViewer.setLabelProvider(new PalletLabelProvider());
        tableViewer.setContentProvider(new ArrayContentProvider());

        table = tableViewer.getTable();
        table.setLayout(new TableLayout());
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        // gd.heightHint = 160;
        table.setLayoutData(gd);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        tableColumns = new TableColumn [headings.length];

        GC gc = new GC(parent);
        FontMetrics fm = gc.getFontMetrics();
        firstColWidth = 4 * fm.getAverageCharWidth();

        int index = 0;
        for (String name : headings) {
            final TableColumn col = new TableColumn(table, SWT.NONE);
            col.setText(name);
            if (index == 0) {
                col.setWidth(firstColWidth);
            }
            if (index != 0) col.setResizable(true);
            tableColumns[index] = col;
            index++;
        }
        tableViewer.setColumnProperties(headings);
        tableViewer.setUseHashlookup(true);

        model = new ArrayList<PalletModel>();
        for (int i = 0; i < 8; ++i) {
            model.add(new PalletModel(String.valueOf((char) ('A' + i))));
        }
        tableViewer.setInput(model);

        colorBackground = getDisplay().getSystemColor(SWT.COLOR_DARK_CYAN);

        table.addListener(SWT.EraseItem, new Listener() {
            public void handleEvent(Event event) {
                event.detail &= ~SWT.HOT;
                if ((event.detail & SWT.SELECTED) == 0) {
                    // / item not selected
                    return;
                }

                Table table = (Table) event.widget;
                // TableItem item = (TableItem) event.item;
                int clientWidth = table.getClientArea().width;

                GC gc = event.gc;
                Color oldBackground = gc.getBackground();

                gc.setBackground(colorBackground);
                gc.fillRectangle(0, event.y, clientWidth, event.height);

                gc.setBackground(oldBackground);
                event.detail &= ~SWT.SELECTED;
            }
        });

        addControlListener(new ControlAdapter() {
            public void controlResized(ControlEvent e) {
                Rectangle area = PalletTableWidget.this.getClientArea();
                Point size = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
                ScrollBar vBar = table.getVerticalBar();
                int width = area.width - table.computeTrim(0, 0, 0, 0).width
                    - vBar.getSize().x - firstColWidth;
                if (size.y > area.height + table.getHeaderHeight()) {
                    // Subtract the scrollbar width from the total column width
                    // if a vertical scrollbar will be required
                    Point vBarSize = vBar.getSize();
                    width -= vBarSize.x;
                }
                Point oldSize = table.getSize();
                int colWidth = width / (tableColumns.length - 1);

                if (oldSize.x > area.width) {
                    // table is getting smaller so make the columns
                    // smaller first and then resize the table to
                    // match the client area width
                    for (int i = 1; i < tableColumns.length; ++i) {
                        tableColumns[i].setWidth(colWidth);
                    }
                    table.setSize(area.width, area.height);
                }
                else {
                    // table is getting bigger so make the table
                    // bigger first and then make the columns wider
                    // to match the client area width
                    table.setSize(area.width, area.height);
                    for (int i = 1; i < tableColumns.length; ++i) {
                        tableColumns[i].setWidth(colWidth);
                    }
                }
            }
        });

    }

    public TableViewer getTableViewer() {
        return tableViewer;
    }

    public void setPalletBarcodes(final Pallet pallet) {
        if (pallet == null) return;

        Thread t = new Thread() {
            @Override
            public void run() {
                if (getTableViewer().getTable().isDisposed()) return;

                PalletModel modelItem;

                for (int r = 0; r < ScanCell.ROW_MAX; ++r) {
                    modelItem = model.get(r);
                    modelItem.o = pallet.getBarcodesRow(r);
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
