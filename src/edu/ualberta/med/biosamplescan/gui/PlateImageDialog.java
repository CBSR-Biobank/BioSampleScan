package edu.ualberta.med.biosamplescan.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class PlateImageDialog extends Dialog {

	private Shell dialogShell;
	private Canvas canvas;
	private boolean isFirst;

	public PlateImageDialog(Shell parent, int style) {
		super(parent, style);
	}

	public double[] open(final double plate[], final boolean isTwain,
			final Color mycolor) {
		try {
			Shell parent = getParent();

			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL);
			dialogShell.setLayout(new FormLayout());
			dialogShell.setLocation(new Point(0, 0));
			dialogShell.setText("Set Plate Coordinates");
			isFirst = true;
			{
				FormData canvasLData = new FormData();
				canvasLData.width = 425;
				canvasLData.height = 585;
				canvasLData.top = new FormAttachment(0, 1000, 0);
				canvasLData.left = new FormAttachment(0, 1000, 0);
				canvas = new Canvas(dialogShell, SWT.NONE);
				canvas.setLayoutData(canvasLData);

				canvas.addMouseListener(new MouseListener() {
					@Override
					public void mouseDoubleClick(MouseEvent e) {
					}

					@Override
					public void mouseDown(MouseEvent e) {
						Image img = new Image(Display.getDefault(),
								"align100.bmp");
						Rectangle bounds = img.getBounds();
						if (isFirst) {
							isFirst = false;
							double x1 = ((double) e.x / 100.0 / (425.0 / bounds.width));
							double y1 = ((double) e.y / 100.0 / (425.0 / bounds.width));
							plate[0] = x1;
							plate[1] = y1;

						}
						else {
							double x2 = ((double) e.x / 100.0 / (425.0 / bounds.width));
							double y2 = ((double) e.y / 100.0 / (425.0 / bounds.width));
							if (isTwain) {
								if (x2 > plate[0] && y2 > plate[0]) {
									plate[2] = x2;
									plate[3] = y2;
									isFirst = true;
								}
								else {
									isFirst = false;

									double x1 = ((double) e.x / 100.0 / (425.0 / bounds.width));
									double y1 = ((double) e.y / 100.0 / (425.0 / bounds.width));
									plate[0] = x1;
									plate[1] = y1;
								}
							}
							else {//WIA
								if (x2 - plate[0] > 0 && y2 - plate[1] > 0) {
									plate[2] = x2 - plate[0];
									plate[3] = y2 - plate[1];
									isFirst = true;
								}
								else {
									isFirst = false;

									double x1 = ((double) e.x / 100.0 / (425.0 / bounds.width));
									double y1 = ((double) e.y / 100.0 / (425.0 / bounds.width));
									plate[0] = x1;
									plate[1] = y1;
								}
							}

						}
						canvas.redraw();
					}

					@Override
					public void mouseUp(MouseEvent e) {
					}

				});

				canvas.addPaintListener(new PaintListener() {
					public void paintControl(PaintEvent e) {
						Image img = new Image(Display.getDefault(),
								"align100.bmp");
						Rectangle bounds = img.getBounds();
						GC gc = new GC(canvas);
						gc.drawImage(img, 0, 0, bounds.width, bounds.height, 0,
								0, 425, 585);
						gc.setForeground(mycolor);
						double x1 = plate[0] * 100 /*at 100 dpi*/
								* (425.0 / bounds.width);
						double y1 = plate[1] * 100 * (585.0 / bounds.height);
						double x2 = plate[2] * 100 * (425.0 / bounds.width);
						double y2 = plate[3] * 100 * (585.0 / bounds.height);

						if (isTwain) {
							/*perform sanity checks*/
							gc.drawRectangle((int) x1, (int) y1,
									(int) (x2 - x1), (int) (y2 - y1));
						}
						else {//WIA x,y,w,h
							gc.drawRectangle((int) x1, (int) y1, (int) x2,
									(int) y2);
						}

						gc.dispose();
						img.dispose();
					}
				});

			}
			canvas.update();
			dialogShell.layout();
			dialogShell.pack();
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch()) display.sleep();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return plate;
	}
}
