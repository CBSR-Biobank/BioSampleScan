package edu.ualberta.med.biosamplescan.rcp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IStartup;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;

public class BioSampleScanStartup implements IStartup {

    private static final String ABOUT_MAPPINGS_FILE = "about.mappings";

    private static final String ABOUT_MAPPINGS_VERSION_ENTRY = "0";

    @Override
    public void earlyStartup() {
        try {
            // check the about version is the same that the application.
            // the file is modified is the version is not correct
            // The result is displayed on the Help > About dialog

            String manifestVersion = getManifestVersion();

            // FIXME this doesn't work when the application is exported into a
            // final product.
            // to retrieve a file, a temporary folder is created to unzip the
            // file. The modification of this file doesn't affect the real file
            // used for the about dialog
            URL urlAbout = FileLocator.find(BioSampleScanPlugin.getDefault()
                .getBundle(), new Path(ABOUT_MAPPINGS_FILE), null);
            String fileAbout = FileLocator.toFileURL(urlAbout).getFile();
            // Read properties file.
            Properties properties = new Properties();
            properties.load(new FileInputStream(fileAbout));
            String aboutVersion = properties
                .getProperty(ABOUT_MAPPINGS_VERSION_ENTRY);
            if (!manifestVersion.equals(aboutVersion)) {
                properties.setProperty(ABOUT_MAPPINGS_VERSION_ENTRY,
                    manifestVersion);
                properties.store(new FileOutputStream(fileAbout), null);
            }

        } catch (Exception e) {
            System.out.println("Error while checking and/or modifying the "
                + ABOUT_MAPPINGS_FILE + " file." + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getManifestVersion() throws Exception {
        URL urlManifest = FileLocator.find(BioSampleScanPlugin.getDefault()
            .getBundle(), new Path("META-INF/MANIFEST.MF"), null);
        urlManifest = FileLocator.toFileURL(urlManifest);

        Manifest manifest = new Manifest(new FileInputStream(
            urlManifest.getFile()));
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue("Bundle-Version");
    }

}
