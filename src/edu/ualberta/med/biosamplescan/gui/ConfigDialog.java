package edu.ualberta.med.biosamplescan.gui;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;
import java.util.prefs.Preferences;
import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import edu.ualberta.med.biosamplescan.IniProperties;

public class ConfigDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Label label11;
	private Text textDpi;
	private Label label19;
	private Group group7;
	private Text textImageRight;
	private Label label13;
	private Text textImageBottom;
	private Label label8;
	private Text textImageLeft;
	private Label label3;
	private Text textImageTop;
	private Label label1;
	private Group group6;
	private Group group5;
	private Button buttonCancle;
	private Button buttonConfig;
	private Text textContrast;
	private Label label18;
	private Text textBrightness;
	private Label label2;
	private Group group1;
	private Label label5;
	private Text textRight3;
	private Label label17;
	private Text textBottom3;
	private Label label16;
	private Text textLeft3;
	private Label label15;
	private Text textTop3;
	private Label label14;
	private Group group4;
	private Text textRight2;
	private Label label12;
	private Text textBottom2;
	private Text textLeft2;
	private Label label10;
	private Text textTop2;
	private Label label9;
	private Group group3;
	private Text textRight1;
	private Label label7;
	private Text textBottom1;
	private Label label6;
	private Text textLeft1;
	private Label label4;
	private Text textTop1;
	private Group group2;
	
	public static int PLATENUM = 3;
	public boolean changedInputs = false; /*remove later*/
	public int dpi = 300;
	public int brightness = 0;
	public int contrast = 0;
	public double image[] = new double[4];
	public double plates[][] = new double[PLATENUM][4];
	
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			DebugDialog inst = new DebugDialog(shell, SWT.NULL);
			inst.open("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public ConfigDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open(String ConfigMessage) {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.makeColumnsEqualWidth = true;
		    changedInputs = false;

			{
				SWTManager.registerResourceUser(dialogShell);
			}
			
			
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.setText("Configuration");
			dialogShell.setFont(SWTManager.getFont("Tahoma", 10, 1, false, false));
			{
				group7 = new Group(dialogShell, SWT.NONE);
				GridData group7LData = new GridData();
				group7LData.widthHint = 421;
				group7LData.heightHint = 31;
				group7.setLayoutData(group7LData);
				group7.setText("Resolution Settings");
				group7.setLayout(null);
				{
					textDpi = new Text(group7, SWT.NONE);
					textDpi.setBounds(37, 20, 36, 16);
					textDpi.setTextLimit(3);
					textDpi.setText("300");
				}
				{
					label19 = new Label(group7, SWT.NONE);
					label19.setText("DPI:");
					label19.setBounds(12, 20, 25, 13);
				}
			}
			{
				group1 = new Group(dialogShell, SWT.NONE);
				GridData group1LData = new GridData();
				group1LData.widthHint = 421;
				group1LData.heightHint = 31;
				group1.setLayoutData(group1LData);
				group1.setLayout(null);
				group1.setText("Light Settings");
				{
					label2 = new Label(group1, SWT.NONE);
					label2.setText("Brightness:");
					label2.setBounds(12, 21, 58, 13);
				}
				{
					textBrightness = new Text(group1, SWT.NONE);
					textBrightness.setText("0");
					textBrightness.setTextLimit(5);
					textBrightness.setBounds(68, 22, 33, 14);
				}
				{
					label18 = new Label(group1, SWT.NONE);
					label18.setText("Contrast:");
					label18.setBounds(105, 22, 49, 13);
				}
				{
					textContrast = new Text(group1, SWT.NONE);
					textContrast.setText("0");
					textContrast.setTextLimit(5);
					textContrast.setBounds(153, 22, 35, 14);
				}
			}
			{
				group6 = new Group(dialogShell, SWT.NONE);
				GridData group6LData = new GridData();
				group6LData.widthHint = 421;
				group6LData.heightHint = 31;
				group6.setLayoutData(group6LData);
				group6.setText("Image Position");
				group6.setLayout(null);
				{
					label1 = new Label(group6, SWT.NONE);
					label1.setText("Top:");
					label1.setBounds(67, 17, 22, 13);
				}
				{
					textImageTop = new Text(group6, SWT.NONE);
					textImageTop.setText("0");
					textImageTop.setTextLimit(6);
					textImageTop.setBounds(92, 17, 32, 14);
				}
				{
					label3 = new Label(group6, SWT.NONE);
					label3.setText("   Left:");
					label3.setBounds(121, 17, 35, 13);
				}
				{
					textImageLeft = new Text(group6, SWT.NONE);
					textImageLeft.setText("0");
					textImageLeft.setTextLimit(6);
					textImageLeft.setBounds(157, 17, 32, 14);
				}
				{
					label8 = new Label(group6, SWT.NONE);
					label8.setText("   Bottom:");
					label8.setBounds(184, 17, 47, 13);
				}
				{
					textImageBottom = new Text(group6, SWT.NONE);
					textImageBottom.setText("0");
					textImageBottom.setTextLimit(6);
					textImageBottom.setBounds(234, 17, 30, 14);
				}
				{
					label13 = new Label(group6, SWT.NONE);
					label13.setText("    Right:");
					label13.setBounds(261, 17, 41, 13);
				}
				{
					textImageRight = new Text(group6, SWT.NONE);
					textImageRight.setText("0");
					textImageRight.setTextLimit(6);
					textImageRight.setBounds(305, 17, 29, 14);
				}
			}
			{
				group2 = new Group(dialogShell, SWT.NONE);
				group2.setLayout(null);
				GridData group2LData = new GridData();
				group2LData.widthHint = 421;
				group2LData.heightHint = 31;
				group2.setLayoutData(group2LData);
				group2.setText("Plate 1 Position");
				{
					label5 = new Label(group2, SWT.NONE);
					label5.setText("Top:");
					label5.setBounds(67, 17, 22, 13);
				}
				{
					textTop1 = new Text(group2, SWT.NONE);
					textTop1.setText("0");
					textTop1.setTextLimit(6);
					textTop1.setOrientation(SWT.HORIZONTAL);
					textTop1.setBounds(92, 17, 34, 13);
					textTop1.setDoubleClickEnabled(false);
				}
				{
					label4 = new Label(group2, SWT.NONE);
					label4.setText("  Left:");
					label4.setBounds(125, 17, 29, 13);
				}
				{
					textLeft1 = new Text(group2, SWT.NONE);
					textLeft1.setText("0");
					textLeft1.setTextLimit(6);
					textLeft1.setBounds(154, 17, 27, 14);
					textLeft1.setSize(26, 14);
				}
				{
					label6 = new Label(group2, SWT.NONE);
					label6.setText("    Bottom:");
					label6.setBounds(178, 17, 50, 13);
				}
				{
					textBottom1 = new Text(group2, SWT.NONE);
					textBottom1.setText("0");
					textBottom1.setTextLimit(6);
					textBottom1.setBounds(231, 17, 24, 14);
					textBottom1.setSize(26, 14);
				}
				{
					label7 = new Label(group2, SWT.NONE);
					label7.setText("    Right:");
					label7.setBounds(258, 17, 41, 13);
				}
				{
					textRight1 = new Text(group2, SWT.NONE);
					textRight1.setText("0");
					textRight1.setTextLimit(6);
					textRight1.setBounds(302, 17, 24, 14);
					textRight1.setSize(26, 14);
				}
			}
			{
				group3 = new Group(dialogShell, SWT.NONE);
				group3.setLayout(null);
				GridData group3LData = new GridData();
				group3LData.widthHint = 421;
				group3LData.heightHint = 31;
				group3.setLayoutData(group3LData);
				group3.setText("Plate 2 Position");
				{
					label9 = new Label(group3, SWT.NONE);
					label9.setText("Top:");
					label9.setBounds(67, 17, 22, 13);
				}
				{
					textTop2 = new Text(group3, SWT.NONE);
					textTop2.setText("0");
					textTop2.setTextLimit(6);
					textTop2.setBounds(92, 17, 24, 14);
					textTop2.setSize(26, 14);
				}
				{
					label10 = new Label(group3, SWT.NONE);
					label10.setText("    Left:");
					label10.setBounds(119, 17, 35, 13);
				}
				{
					textLeft2 = new Text(group3, SWT.NONE);
					textLeft2.setText("0");
					textLeft2.setTextLimit(6);
					textLeft2.setBounds(157, 17, 24, 14);
					textLeft2.setSize(26, 14);
				}
				{
					label11 = new Label(group3, SWT.NONE);
					label11.setText("   Bottom:");
					label11.setBounds(184, 17, 47, 13);
				}
				{
					textBottom2 = new Text(group3, SWT.NONE);
					textBottom2.setText("0");
					textBottom2.setTextLimit(6);
					textBottom2.setBounds(234, 17, 24, 14);
					textBottom2.setSize(26, 14);
				}
				{
					label12 = new Label(group3, SWT.NONE);
					label12.setText("    Right:");
					label12.setBounds(261, 17, 41, 13);
				}
				{
					textRight2 = new Text(group3, SWT.NONE);
					textRight2.setText("0");
					textRight2.setTextLimit(6);
					textRight2.setBounds(305, 17, 24, 14);
					textRight2.setSize(26, 14);
				}
			}
			{
				group4 = new Group(dialogShell, SWT.NONE);
				group4.setLayout(null);
				GridData group4LData = new GridData();
				group4LData.widthHint = 421;
				group4LData.heightHint = 31;
				group4.setLayoutData(group4LData);
				group4.setText("Plate 3 Position");
				{
					label14 = new Label(group4, SWT.NONE);
					label14.setText("Top:");
					label14.setBounds(67, 17, 22, 13);
				}
				{
					textTop3 = new Text(group4, SWT.NONE);
					textTop3.setText("0");
					textTop3.setTextLimit(6);
					textTop3.setBounds(92, 17, 24, 14);
					textTop3.setSize(26, 14);
				}
				{
					label15 = new Label(group4, SWT.NONE);
					label15.setText("    Left:");
					label15.setBounds(119, 17, 35, 13);
				}
				{
					textLeft3 = new Text(group4, SWT.NONE);
					textLeft3.setText("0");
					textLeft3.setTextLimit(6);
					textLeft3.setBounds(157, 17, 24, 14);
					textLeft3.setSize(26, 14);
				}
				{
					label16 = new Label(group4, SWT.NONE);
					label16.setText("   Bottom:");
					label16.setBounds(184, 17, 47, 13);
				}
				{
					textBottom3 = new Text(group4, SWT.NONE);
					textBottom3.setText("0");
					textBottom3.setTextLimit(6);
					textBottom3.setBounds(234, 17, 24, 14);
					textBottom3.setSize(26, 14);
				}
				{
					label17 = new Label(group4, SWT.NONE);
					label17.setText("    Right:");
					label17.setBounds(261, 17, 41, 13);
				}
				{
					textRight3 = new Text(group4, SWT.NONE);
					textRight3.setText("0");
					textRight3.setTextLimit(6);
					textRight3.setBounds(305, 17, 24, 14);
					textRight3.setSize(26, 14);
				}
			}
			{
				group5 = new Group(dialogShell, SWT.NONE);
				RowLayout group5Layout = new RowLayout(org.eclipse.swt.SWT.HORIZONTAL);
				group5.setLayout(group5Layout);
				GridData group5LData = new GridData();
				group5.setLayoutData(group5LData);
				{
					buttonCancle = new Button(group5, SWT.PUSH | SWT.CENTER);
					buttonCancle.setText("Cancle");
					RowData button1LData = new RowData();
					buttonCancle.setLayoutData(button1LData);
					buttonCancle.setFont(SWTManager.getFont("Tahoma", 10, 0, false, false));
					buttonCancle.addMouseListener(new MouseAdapter() {
						public void mouseUp(MouseEvent evt) {
							buttonCancleMouseUp(evt);
						}
					});
				}
				{
					buttonConfig = new Button(group5, SWT.PUSH | SWT.CENTER);
					buttonConfig.setText("Configure");
					buttonConfig.setFont(SWTManager.getFont("Tahoma", 10, 1, false, false));
					RowData buttonConfigLData = new RowData();
					buttonConfig.setLayoutData(buttonConfigLData);
					buttonConfig.addMouseListener(new MouseAdapter() {
						public void mouseUp(MouseEvent evt) {
							buttonConfigMouseUp(evt);
						}
					});
				}
			}
			this.initCustomRoutines();
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setLocation(getParent().toDisplay(100, 100));
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
	private void initCustomRoutines() throws InvalidFileFormatException, IOException{
		//Wini ini = new Wini(new File("scanlib.ini")); 
       // String brightness = ini.get("scanner", "brightness");
        //if(brightness == null){brightness = "5";}
        //textBrightness.setText(brightness);
        
        IniProperties ini = new IniProperties();
        ini.load(in)
        
        
		/*InputStream stream = ClassLoader.getSystemResourceAsStream("scanlib.ini");  
		     Ini ini = new Ini(stream);  
		     for (String key : ini.get("scanner").keySet())
		    	
		     {
		         System.out.println("scanner/" + key + " = " + ini.get("scanner").fetch(key));
		     }*/
			

		/*
		Ini ini = new Ini();
	    try {
			ini.load(new FileReader("scanlib"));
		} catch (InvalidFileFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//System.out.println("grumpy/homePage: " + prefs.node("grumpy").get("homePage", null));
		//Ini.Section section = ini.get("scanner");
		//textBrightness.setText(section.get("brightness"));
        //String weight = section.get("weight");
        //String homeDir = section.get("homeDir");
	
	}
	
	private void buttonCancleMouseUp(MouseEvent evt) {
		dialogShell.dispose();
	}
	private double concatDouble(double in,double lower, double upper){//inclusive
		if(in < lower){in = lower;}
		if(in > upper){in = upper;}
		return in;
	}
	private int concatInt(int in,int lower, int upper){//inclusive
		if(in < lower){in = lower;}
		if(in > upper){in = upper;}
		return in;
	}
	
	private void buttonConfigMouseUp(MouseEvent evt) {
		try{
		dpi = Integer.parseInt(textDpi.getText());
		brightness= Integer.parseInt(textBrightness.getText());
		contrast   =  Integer.parseInt(textContrast.getText());
	    image[0] = Double.valueOf(textImageTop.getText());
		image[1] = Double.valueOf(textImageLeft.getText());
		image[2] = Double.valueOf(textImageBottom.getText());
		image[3] = Double.valueOf(textImageRight.getText());
		plates[0][0] = Double.valueOf(textTop1.getText());
		plates[0][1] = Double.valueOf(textLeft1.getText());
		plates[0][2] = Double.valueOf(textBottom1.getText());
		plates[0][3] = Double.valueOf(textRight1.getText());
		plates[1][0] = Double.valueOf(textTop2.getText());
		plates[1][1] = Double.valueOf(textLeft2.getText());
		plates[1][2] = Double.valueOf(textBottom2.getText());
		plates[1][3] = Double.valueOf(textRight2.getText());
		plates[2][0] = Double.valueOf(textTop3.getText());
		plates[2][1] = Double.valueOf(textLeft3.getText());
		plates[2][2] = Double.valueOf(textBottom3.getText());
		plates[2][3] = Double.valueOf(textRight3.getText());
		
		if(dpi != concatInt(dpi,1,600)){
			MessageDialog.openError(dialogShell, "Error", 
					"DPI:\nRanges from 1 to 600\n");
			return;
		}
		if(brightness != concatInt(brightness,-1000,1000)){
			MessageDialog.openError(dialogShell, "Error", 
					"Brightness:\nRanges from -1000 to 1000\n");
			return;
		}
		if(contrast != concatInt(contrast,-1000,1000)){
			MessageDialog.openError(dialogShell, "Error", 
					"Contrast:\nRanges from -1000 to 1000\n");
			return;
		}
		if(image[0] > image[2]){
			MessageDialog.openError(dialogShell, "Error", 
					"Image Position:\nTop > Bottom\n");
			return;
		}
		if(image[1] > image[3]){
			MessageDialog.openError(dialogShell, "Error", 
					"Image Position:\nLeft > Right\n");
			return;
		}
		for(int i=0; i < 4; i++){
			if(image[i] < 0){
				MessageDialog.openError(dialogShell, "Error", 
						"Image Position:\nNegative Value\n");
				return;
			}
		}
		
		for(int plate=0;plate < PLATENUM; plate++){
			if(plates[plate][0] > plates[plate][2]){//top > bottom
				MessageDialog.openError(dialogShell, "Error", 
						String.format("Plate %d:\n" +
									  "Top > Bottom\n",plate+1));
				return;
			}
			if(plates[plate][1] > plates[plate][3]){//left > right
				MessageDialog.openError(dialogShell, "Error", 
						String.format("Plate %d:\n" + 
									  "Left > Right\n",plate+1));
				return;
			}
			for(int i=0; i < 4; i++){
				if(plates[plate][i] < 0){
					MessageDialog.openError(dialogShell, "Error", 
						String.format("Plate %d:\n" +
								      "Negative Value\n",plate+1));
					return;
				}
			}
			
		}

		changedInputs = true;
		dialogShell.dispose();
		}
		catch (Exception e) {
			MessageDialog.openError(dialogShell, "Error", e.getMessage());
		}
		
	}

}
