package edu.ualberta.med.biosamplescan.handler;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.View;
import edu.ualberta.med.biosamplescan.gui.Main;
import edu.ualberta.med.scanlib.ScanLib;
import edu.ualberta.med.scanlib.ScanLibFactory;

public class ScannerSettings extends AbstractHandler implements IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Main main = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getMain();
		if (new File("scanlib.ini").exists()) {
			// int olddpi = configDialog.dpi; //accessed from here
			int oldbrightness = main.configDialog.brightness;
			int oldcontrast = main.configDialog.contrast;

			double oldplates[][] = new double[main.MAXPLATES][4];
			for (int plate = 0; plate < main.MAXPLATES; plate++) {
				for (int i = 0; i < 4; i++) {
					oldplates[plate][i] = main.configDialog.plates[plate][i];
				}
			}
			main.configDialog.open("");
			if (main.configDialog.cancledDialog) {
				return null;
			}

			if (oldbrightness != main.configDialog.brightness) {
				int scanlibReturn = ScanLibFactory
						.getScanLib()
						.slConfigScannerBrightness(main.configDialog.brightness);
				switch (scanlibReturn) {
				case (ScanLib.SC_SUCCESS):
					break;
				case (ScanLib.SC_INVALID_VALUE):
					main.errorMsg(
							"menuConfigurationWidgetSelected, Brightness ",
							scanlibReturn);
					return null;

				case (ScanLib.SC_INI_FILE_ERROR):
					main.errorMsg(
							"menuConfigurationWidgetSelected, Brightness ",
							scanlibReturn);
					return null;
				}

			}
			if (oldcontrast != main.configDialog.contrast) {
				int scanlibReturn = ScanLibFactory.getScanLib()
						.slConfigScannerContrast(main.configDialog.contrast);
				switch (scanlibReturn) {
				case (ScanLib.SC_SUCCESS):
					break;
				case (ScanLib.SC_INVALID_VALUE):
					main.errorMsg("menuConfigurationWidgetSelected, contrast ",
							scanlibReturn);
					return null;

				case (ScanLib.SC_INI_FILE_ERROR):
					main.errorMsg("menuConfigurationWidgetSelected, Contrast ",
							scanlibReturn);
					return null;
				}
			}

			boolean platesChanged[] = new boolean[main.MAXPLATES];

			double sum = 0;
			for (int plate = 0; plate < main.MAXPLATES; plate++) {
				for (int i = 0; i < 4; i++) {
					if (oldplates[plate][i] != main.configDialog.plates[plate][i]) {
						platesChanged[plate] = true;
					}
					sum += main.configDialog.plates[plate][i];
				}
			}
			if (sum == 0) {
				main.errorMsg("Must Configure Atleast One Plate", -1);
				this.execute(event);
			}
			for (int plate = 0; plate < main.MAXPLATES; plate++) {
				if (platesChanged[plate]) {
					int scanlibReturn = ScanLibFactory.getScanLib()
							.slConfigPlateFrame(plate + 1,
									main.configDialog.plates[plate][1],
									main.configDialog.plates[plate][0],
									main.configDialog.plates[plate][3],
									main.configDialog.plates[plate][2]);
					switch (scanlibReturn) {
					case (ScanLib.SC_SUCCESS):
						break;
					case (ScanLib.SC_FAIL):
						main
								.errorMsg(
										"menuConfigurationWidgetSelected, slConfigPlateFrame",
										scanlibReturn);
						return null;
					}
					if (main.configDialog.plates[plate][0]
							+ main.configDialog.plates[plate][1]
							+ main.configDialog.plates[plate][2]
							+ main.configDialog.plates[plate][3] > 0) {
						scanlibReturn = ScanLibFactory.getScanLib()
								.slCalibrateToPlate(main.configDialog.dpi,
										plate + 1);
						switch (scanlibReturn) {
						case (ScanLib.SC_SUCCESS):
							break;
						case (ScanLib.SC_INVALID_IMAGE):
							main
									.errorMsg(
											"menuConfigurationWidgetSelected, Calibratation",
											scanlibReturn);
							return null;

						case (ScanLib.SC_INI_FILE_ERROR):
							main.errorMsg(
									"menuConfigurationWidgetSelected, tation ",
									scanlibReturn);
							return null;
						}
					}
				}
			}
		} else {
			MessageDialog.openError(main.getShell(), "Error Failure!!",
					"Failed to find scanlib.ini file");
			System.exit(1);
		}
		return null;
	}

}
