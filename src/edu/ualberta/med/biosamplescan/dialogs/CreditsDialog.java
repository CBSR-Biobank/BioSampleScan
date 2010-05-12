package edu.ualberta.med.biosamplescan.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;

public class CreditsDialog extends Dialog implements KeyListener {

    private Shell dialogShell;

    public CreditsDialog(Shell parent, int style) {
        super(parent, style);
    }

    public void open() {
        try {
            Shell parent = getParent();
            dialogShell = new Shell(parent, SWT.NONE);

            GridLayout dialogShellLayout = new GridLayout();
            dialogShellLayout.makeColumnsEqualWidth = true;
            dialogShellLayout.numColumns = 1;
            dialogShell.setLayout(dialogShellLayout);
            dialogShell.setText("Credits");
            dialogShell.setBackground(new Color(Display.getDefault(), 0, 0, 0));
            dialogShell.setAlpha(0xFF);
            dialogShell.setCursor(new Cursor(Display.getDefault(),
                SWT.CURSOR_CROSS));
            dialogShell.setSize(parent.getSize());

            dialogShell.addKeyListener(this);
            /*--------------------------------------------------------------*/
            /*-----------------WARNING READ TERMS BELOW---------------------*/

            Label dummy = new Label(dialogShell, SWT.LEFT);
            dummy.setFont(new Font(Display.getDefault(), "Lucida Console", 10,
                SWT.NORMAL));
            dummy.setBackground(new Color(Display.getDefault(), 0, 0, 0));
            dummy.setForeground(new Color(Display.getDefault(), 0xFF, 0, 0));
            dummy
                .setText("       ___                       ___           ___           ___ \n"
                    + "      /\\  \\          ___        /\\  \\         /\\__\\         /\\__\\\n"
                    + "     /::\\  \\        /\\  \\      /::\\  \\       /::|  |       /:/  /\n"
                    + "    /:/\\:\\  \\       \\:\\  \\    /:/\\:\\  \\     /:|:|  |      /:/  / \n"
                    + "   /::\\~\\:\\  \\      /::\\__\\  /:/  \\:\\  \\   /:/|:|__|__   /:/  /  \n"
                    + "  /:/\\:\\ \\:\\__\\  __/:/\\/__/ /:/__/ \\:\\__\\ /:/ |::::\\__\\ /:/__/   \n"
                    + "  \\/__\\:\\/:/  / /\\/:/  /    \\:\\  \\  \\/__/ \\/__/~~/:/  / \\:\\  \\   \n"
                    + "       \\::/  /  \\::/__/      \\:\\  \\             /:/  /   \\:\\  \\  \n"
                    + "       /:/  /    \\:\\__\\       \\:\\  \\           /:/  /     \\:\\  \\ \n"
                    + "      /:/  /      \\/__/        \\:\\__\\         /:/  /       \\:\\__\\\n"
                    + "      \\/__/                     \\/__/         \\/__/         \\/__/\n");
            dummy = new Label(dialogShell, SWT.LEFT);
            dummy.setText("Nelson Loyla");
            dummy.setFont(new Font(Display.getDefault(), "Segoe Print", 10,
                SWT.NORMAL));
            dummy.setBackground(new Color(Display.getDefault(), 0, 0, 0));
            dummy.setForeground(new Color(Display.getDefault(), 0xFF, 0, 0));
            dummy = new Label(dialogShell, SWT.LEFT);
            dummy.setText("Thomas Polasek");
            dummy.setFont(new Font(Display.getDefault(), "Segoe Print", 10,
                SWT.NORMAL));
            dummy.setBackground(new Color(Display.getDefault(), 0, 0, 0));
            dummy.setForeground(new Color(Display.getDefault(), 0xFF, 0, 0));

            dummy = new Label(dialogShell, SWT.LEFT);
            dummy.setText("Delphine Degris-Dard");
            dummy.setFont(new Font(Display.getDefault(), "Segoe Print", 10,
                SWT.NORMAL));
            dummy.setBackground(new Color(Display.getDefault(), 0, 0, 0));
            dummy.setForeground(new Color(Display.getDefault(), 0xFF, 0, 0));

            Link website = new Link(dialogShell, SWT.LEFT);
            website.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
                | GridData.FILL_HORIZONTAL));

            website.setText("Icons: Mark James [Hyperlink]");
            website.setFont(new Font(Display.getDefault(), "Segoe Print", 10,
                SWT.NORMAL));
            website.setForeground(new Color(Display.getDefault(), 0x00, 0xFF,
                0xFF));
            website.setBackground(new Color(Display.getDefault(), 0, 0, 0));
            website.addMouseListener(new MouseListener() {
                public void mouseDown(MouseEvent arg0) {
                    Program.launch("http://www.famfamfam.com/lab/icons/silk/");
                    // ^^^^^^^^The hyper link above is MANDATORY^^^^^^
                    // SilkIcons license agreement states that a hyper link ( to
                    // their web-page)
                    // must be provided.
                }

                @Override
                public void mouseDoubleClick(MouseEvent e) {
                }

                @Override
                public void mouseUp(MouseEvent e) {
                }
            });
            Label exitmsg = new Label(dialogShell, SWT.LEFT);
            exitmsg
                .setText("---------------Press any key to close this dialog---------------\n");
            exitmsg.setFont(new Font(Display.getDefault(), "Lucida Console", 6,
                SWT.NORMAL));
            exitmsg.setBackground(new Color(Display.getDefault(), 0, 0, 0));
            exitmsg
                .setForeground(new Color(Display.getDefault(), 0xFF, 0xFF, 0));

            dialogShell.layout();
            dialogShell.pack();
            dialogShell.setLocation(getParent().toDisplay(0, 0));
            dialogShell.open();
            Display display = dialogShell.getDisplay();
            while (!dialogShell.isDisposed()) {
                if (!display.readAndDispatch())
                    display.sleep();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        dialogShell.dispose();

    }
}
