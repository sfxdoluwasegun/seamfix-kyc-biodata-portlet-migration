
/**
 * @author Charles Ofoegbu
 * 
 */

package com.sf.kycmanager.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


public class BiocaptureProperties {

	private static Logger logger = Logger.getLogger(BiocaptureProperties.class);
	private static Properties properties = new Properties();
	private static String propertiesFilename = "biocapture.properties";
	private static String comments = "Properties Used to configure bta properties";
	
	public BiocaptureProperties(){
		loadProperties();
	}
	
	public synchronized static Properties getProperties(){
		loadProperties();
		return properties;
	}
	
	private static Properties loadProperties(){
		
		try {
			properties.load(new FileInputStream(propertiesFilename));
		} catch (FileNotFoundException e) {
			updateProperties();
			logger.error("File, biocapture.properties, was not found: ",e);
		} catch (IOException e) {
			logger.error("Exception : ",e);
		}
		return properties;
	}
	
	private static void updateProperties(){
		try {
			properties.store(new FileOutputStream(propertiesFilename), comments );
			System.out.println("Properties Stored");
		} catch (FileNotFoundException e) {
			logger.error("Exception : ",e);
		} catch (IOException e) {
			logger.error("Exception : ",e);
		}
	}

}
