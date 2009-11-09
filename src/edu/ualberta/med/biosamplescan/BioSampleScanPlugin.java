package edu.ualberta.med.biosamplescan;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import edu.ualberta.med.biosamplescan.editors.PalletSetEditor;
import edu.ualberta.med.scannerconfig.ScannerConfigPlugin;

/**
 * The activator class controls the plug-in life cycle
 */
public class BioSampleScanPlugin extends AbstractUIPlugin implements
    IPropertyChangeListener {
    // The plug-in ID
    public static final String PLUGIN_ID = "BioSampleScan";

    private static final String LAST_SAVE_DIR = PLUGIN_ID + ".lastsavedir";

    private static final String SAVE_FILENAME = PLUGIN_ID + ".savefilename";

    public static final String IMG_FORM_BG = "formBg";

    // The shared instance
    private static BioSampleScanPlugin plugin;

    private int palletsMax;

    private boolean simulateScanning;

    public BioSampleScanPlugin() {
        palletsMax = ScannerConfigPlugin.getPalletsMax();
    }

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;

        ScannerConfigPlugin.getDefault().getPreferenceStore()
            .addPropertyChangeListener(this);
    }

    public void propertyChange(PropertyChangeEvent event) {
        for (int i = 0; i < edu.ualberta.med.scannerconfig.preferences.PreferenceConstants.SCANNER_PALLET_ENABLED.length; ++i) {
            if (!event
                .getProperty()
                .equals(
                    edu.ualberta.med.scannerconfig.preferences.PreferenceConstants.SCANNER_PALLET_ENABLED[i]))
                continue;

            PalletSetEditor editor = getPalletSetEditor();
            if (editor != null)
                editor.getPalletSetWidget().refreshPallet(i);
        }
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

    public void updateStatusBar(String string) {
        getPalletSetEditor().updateStatusBar(string);
    }

    public PalletSetEditor getPalletSetEditor() {
        return (PalletSetEditor) PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow().getActivePage().getActivePart();
    }

    public String getLastSaveDir() {
        return getPreferenceStore().getString(LAST_SAVE_DIR);
    }

    public void setLastSaveDir(String dir) {
        getPreferenceStore().setValue(LAST_SAVE_DIR, dir);
    }

    public void setSaveFileName(String filename) {
        getPreferenceStore().setValue(SAVE_FILENAME, filename);
    }

    public String getSaveFileName() {
        return getPreferenceStore().getString(SAVE_FILENAME);
    }

    public void setSimulateScanning(boolean enable) {
        simulateScanning = enable;
    }

    public boolean getSimulateScanning() {
        return simulateScanning;
    }

    public int getPalletsMax() {
        return palletsMax;
    }

    public void setPalletsMax(int max) {
        palletsMax = max;
    }

    public int getPalletCount() {
        return ScannerConfigPlugin.getDefault().getPalletCount();
    }

    public boolean getPalletEnabled(int palletId) {
        return ScannerConfigPlugin.getDefault().getPalletEnabled(palletId);
    }

    public int getDpi() {
        return ScannerConfigPlugin.getDefault().getDpi();
    }

    public int getBrightness() {
        return ScannerConfigPlugin.getDefault().getBrightness();
    }

    public int getContrast() {
        return ScannerConfigPlugin.getDefault().getContrast();
    }

}
