package com.attilax.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class cfgController {
	
	public static void main(String[] args) throws IOException {

		Properties props = new Properties();
		InputStream in = Thread.class.getResourceAsStream("/config.properties");
		props.load(in);
	 	in.close();
		System.out.println(props.getProperty("user"));

	}

}
