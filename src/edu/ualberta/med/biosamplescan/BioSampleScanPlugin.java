package edu.ualberta.med.biosamplescan;

import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.Option;
import jargs.gnu.CmdLineParser.OptionException;

import java.net.URL;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.State;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.services.ISourceProviderService;
import org.osgi.framework.BundleContext;

import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.PalletSet;
import edu.ualberta.med.biosamplescan.sourceproviders.DebugState;

/**
 * The activator class controls the plug-in life cycle
 */
public class BioSampleScanPlugin extends AbstractUIPlugin {
    // The plug-in ID
    public static final String PLUGIN_ID = "BioSampleScan";

    public static final String IMG_FORM_BG = "formBg";

    // The shared instance
    private static BioSampleScanPlugin plugin;

    private PalletSet palletSet;

    private PalletSetView plateSetView;

    public BioSampleScanPlugin() {
        String osname = System.getProperty("os.name");
        if (osname.startsWith("Windows")) {
            System.loadLibrary("scanlib");
        }
    }

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry registry) {
        registerImage(registry, IMG_FORM_BG, "form_banner.bmp");
    }

    private void registerImage(ImageRegistry registry, String key,
        String fileName) {
        try {
            IPath path = new Path("icons/" + fileName);
            URL url = FileLocator.find(getBundle(), path, null);
            if (url != null) {
                ImageDescriptor desc = ImageDescriptor.createFromURL(url);
                registry.put(key, desc);
            }
        } catch (Exception e) {
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static BioSampleScanPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path
     * 
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    /**
     * Display an information message
     */
    public static void openMessage(String title, String message) {
        MessageDialog.openInformation(PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow().getShell(), title, message);
    }

    /**
     * Display an error message
     */
    public static void openError(String title, String message) {
        MessageDialog.openError(PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow().getShell(), title, message);
    }

    /**
     * Display an information message
     */
    public static boolean openConfirm(String title, String message) {
        return MessageDialog.openConfirm(PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow().getShell(), title, message);
    }

    /**
     * Display an error message asynchronously
     */
    public static void openAsyncError(final String title, final String message) {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                MessageDialog.openError(PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow().getShell(), title, message);
            }
        });
    }

    public PalletSet createNewPelletSet() {
        palletSet = new PalletSet();
        return palletSet;
    }

    public PalletSet getPalletSet() {
        return palletSet;
    }

    public void setPalletSet(PalletSet palletSet) {
        this.palletSet = palletSet;
    }

    public PalletSetView getPalletSetView() {
        return plateSetView;
    }

    public void setPlateSetView(PalletSetView plateSetEditor) {
        final String err = parseCommandLine();
        if (err != null) {
            stopApplication("Command Line Arguments", err);
            return;
        }

        palletSet = new PalletSet();
        ConfigSettings.getInstance();
        plateSetView = plateSetEditor;

        IWorkbenchWindow window = PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow();
        ISourceProviderService service = (ISourceProviderService) window
            .getService(ISourceProviderService.class);

        DebugState debugStateSourceProvider = (DebugState) service
            .getSourceProvider(DebugState.SESSION_STATE);
        debugStateSourceProvider.setState(BioSampleScanPlugin.getDefault()
            .isDebugging());

        // reads the persisted state for the menu contribution
        ICommandService cmdService = (ICommandService) PlatformUI
            .getWorkbench().getService(ICommandService.class);
        Command command = cmdService
            .getCommand("edu.ualberta.med.biosamplescan.menu.debug.simulateScanning");
        State state = command.getState("org.eclipse.ui.commands.toggleState");
        ConfigSettings.getInstance().setSimulateScanning(
            (Boolean) state.getValue());

        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                String msg = new String();
                if (ConfigSettings.getInstance().getPalletCount() == 0) {
                    msg = "Please configure scanner.";
                } else {
                    msg = "Configuration loaded.";
                }
                plateSetView.updateStatusBar(msg);
                plateSetView.refresh();
            }
        });
    }

    private String parseCommandLine() {
        CmdLineParser parser = new CmdLineParser();
        Option outputOpt = parser.addStringOption('o', "output");
        Option palletsMaxOpt = parser.addIntegerOption('p', "palletsmax");

        try {
            parser.parse(Platform.getApplicationArgs());
        } catch (OptionException e) {
            return e.getMessage();
        }

        ConfigSettings c = ConfigSettings.getInstance();

        String filename = (String) parser.getOptionValue(outputOpt);
        if (filename != null) {
            if (filename.length() == 0) {
                return "Invalid save location";
            }
            c.setSaveFileName(filename);
        }

        Integer palletsMax = (Integer) parser.getOptionValue(palletsMaxOpt);
        if (palletsMax != null) {
            if ((palletsMax <= 0) || (palletsMax > ConfigSettings.PALLET_NUM)) {
                return "nvalid value. palletsmax should be between 1 and "
                    + ConfigSettings.PALLET_NUM;
            }
            c.setPalletsMax(palletsMax);
        }

        if ((filename != null) && (palletsMax != null) && (palletsMax != 1)) {
            return "palletsmax should be 1 when using --output option";
        }
        return null;
    }

    public static void stopApplication(final String title, final String msg) {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                BioSampleScanPlugin.openError(title, msg);
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().close();
            }
        });
    }
}
