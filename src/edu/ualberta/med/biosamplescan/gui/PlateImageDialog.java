package edu.ualberta.med.biosamplescan.gui;

import org.eclipse.swt.SWT;
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

import edu.ualberta.med.biosamplescan.singleton.ConfigSettings;

public class PlateImageDialog extends Dialog {

	private Shell dialogShell;
	private Canvas canvas;

	public PlateImageDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open(final double plates[][], final boolean isTwain) {
		try {
			Shell parent = getParent();

			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL);
			dialogShell.setLayout(new FormLayout());
			dialogShell.setLocation(new Point(0, 0));
			{
				FormData canvasLData = new FormData();
				canvasLData.width = 425;
				canvasLData.height = 585;
				canvasLData.top = new FormAttachment(0, 1000, 0);
				canvasLData.left = new FormAttachment(0, 1000, 0);
				canvas = new Canvas(dialogShell, SWT.NONE);
				canvas.setLayoutData(canvasLData);

				canvas.addPaintListener(new PaintListener() {
					public void paintControl(PaintEvent e) {
						Image img = new Image(Display.getDefault(),
								"align100.bmp");
						Rectangle bounds = img.getBounds();
						GC gc = new GC(canvas);
						gc.drawImage(img, 0, 0, bounds.width, bounds.height, 0,
								0, 425, 585);

						for (int i = 0; i < ConfigSettings.PLATENUM; i++) {
							switch (i) {
								case (0):
									gc.setForeground(new Color(Display
											.getDefault(), 0, 0xFF, 0));
									break;
								case (1):
									gc.setForeground(new Color(Display
											.getDefault(), 0xFF, 0, 0));
									break;
								case (2):
									gc.setForeground(new Color(Display
											.getDefault(), 0xFF, 0xFF, 0));
									break;
								case (3):
									gc.setForeground(new Color(Display
											.getDefault(), 0, 0xFF, 0xFF));
									break;
								default:
									gc.setForeground(new Color(Display
											.getDefault(), 0xFF, 0xFF, 0xFF));
									break;
							}

							double x1 = plates[i][0] * 100 /*at 100 dpi*/
									* (425.0 / bounds.width);
							double y1 = plates[i][1] * 100
									* (585.0 / bounds.height);
							double x2 = plates[i][2] * 100
									* (425.0 / bounds.width);
							double y2 = plates[i][3] * 100
									* (585.0 / bounds.height);

							if (isTwain) {
								/*perform sanity checks*/
								gc.drawRectangle((int) x1, (int) y1,
										(int) (x2 - x1), (int) (y2 - y1));
							}
							else {//WIA x,y,w,h
								gc.drawRectangle((int) x1, (int) y1, (int) x2,
										(int) y2);
							}

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
	}
}
