package com.attilax.secury;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class propertyReader extends yzmPropertyReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static String getProperty(String Property,String path) {
		Properties props = new Properties();
		InputStream in = Thread.class.getResourceAsStream("/"+path);
		try {
			props.load(in);
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
	 	try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (props.getProperty(Property));
	}

}
