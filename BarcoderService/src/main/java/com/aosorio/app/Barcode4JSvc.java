/** Copyright or License
 *
 */

package com.aosorio.app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.krysalis.barcode4j.BarcodeException;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.xml.sax.SAXException;

/**
 * Package: com.aosorio.app
 *
 * Class: Barcode4JSvc Barcode4JSvc.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: Barcode generation using Barcode4J
 * 
 * Implementation: [Notes on implementation]
 *
 * Created: Oct 24, 2016 4:20:00 PM
 * 
 */
public class Barcode4JSvc implements IBarcodeGenMockSvc {

	public final static String CFG_FILE = "src/main/resources/barcode-cfg.xml";
	public final static int SIZE = 12;

	SerialCode code;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aosorio.app.IBarcodeGenMockSvc#generate()
	 */
	@Override
	public void generate() {

		DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder();
		Configuration cfg;
		
		String rndSequence = getRndSequence();
		System.out.println(rndSequence);
		
		code = new SerialCode();
		
		try {

			cfg = builder.buildFromFile(new File(CFG_FILE));

			try {
				BarcodeGenerator gen = BarcodeUtil.getInstance().createBarcodeGenerator(cfg);

				OutputStream out = new java.io.FileOutputStream(new File("output.png"));
				BitmapCanvasProvider provider = new BitmapCanvasProvider(out, "image/x-png", 300,
						BufferedImage.TYPE_BYTE_GRAY, true, 0);

				String checksum = getChecksum(rndSequence);
				String msg = rndSequence + checksum;
				gen.generateBarcode(provider, msg);
				provider.finish();
				
				this.code.setCode( rndSequence );

			} catch (ConfigurationException e) {
				e.printStackTrace();
			} catch (BarcodeException e) {
				e.printStackTrace();
			}

		} catch (ConfigurationException e) {
			e.printStackTrace();

		} catch (SAXException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aosorio.app.IBarcodeGenMockSvc#getBarcode()
	 */
	
	private static String getRndSequence() {
		
		Random rng = new Random(); // Ideally just create one instance globally
		
		StringBuilder generated = new StringBuilder();
		
		while (generated.length() < SIZE) {
		    Integer next = rng.nextInt(10);
		    generated.append(next);

		}
        return generated.toString();
		
	}
	
	/**
	 * @param pCode
	 * @return
	 */
	public static String getChecksum(String pCode) {
		
		int checkSumInt = 0;		
		int sum = 0;
		
		
		for( int i = 0; i <= 5; i++) {
			int index = (i*2)+1;
			sum += ( Character.getNumericValue(pCode.charAt(index)))*3;
			index = (i*2);
			sum += Character.getNumericValue(pCode.charAt(index));
		
		}
		
        checkSumInt = sum % 10;
        checkSumInt = 10 - checkSumInt;
        if ( checkSumInt == 10 ) 
        	checkSumInt = 0;
        
        System.out.println("checksum: " + checkSumInt);

		return String.valueOf(checkSumInt);

	}

	@Override
	public Object getCode() {
		Object serialNumber = (Object) code;
		return serialNumber;
	}
}
