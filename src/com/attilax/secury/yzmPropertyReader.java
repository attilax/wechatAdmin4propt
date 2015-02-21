package com.attilax.secury;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class yzmPropertyReader {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		String s=getProperty("softid");
System.out.println(s);
	}

	public static String getProperty(String Property) {
		Properties props = new Properties();
		InputStream in = Thread.class.getResourceAsStream("/cfg.txt");
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
