package com.attilax.util;


import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class testC3p0 {
	
	
	public static void main(String[] args) {

//		ComboPooledDataSource c=	new ComboPooledDataSource();
//		System.out.println(c);
//		System.out.println("testC3p0.main()");
		
		method1();
		
		
	}

	private static void method1() {
		Dbcontroller dbc=new DbNdsController();
		System.out.println(dbc);
		
  Logger logger = Logger
		.getLogger("full_cb20");
  logger.info("nbpos");
	} 
	

}
