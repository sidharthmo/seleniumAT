package com.at.automation.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
	//private Properties localProperties=null;
	static Properties localProp;
	
	 private PropertiesReader() throws FileNotFoundException{
		String str = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"config.properties";
		InputStream localInputStream = new FileInputStream(str);
		if (localInputStream != null) {
			localProp = new Properties();
			try {
				localProp.load(localInputStream);
			} catch (Exception e) {
			}
		}
		
	}
/*	public void getConfigration(){
		String str = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"config.properties";
		InputStream localInputStream = ClassLoader.getSystemResourceAsStream(str);
		if (localInputStream == null) {
			localInputStream = WebConnector.class.getResourceAsStream(str);
			
		}
		if (localInputStream != null) {
			localProp = new Properties();
			try {
				localProp.load(localInputStream);
			} catch (Exception e) {
			}
		}
	}*/
	
/*	public Properties getLocalProperties() {
		return localProperties;
	}*/
	
	
	public static Properties getLocalPropertiesInstance(){
		if(localProp==null)
			try {
				new PropertiesReader();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}		
		return localProp;
	}
}
