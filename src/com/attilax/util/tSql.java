package com.attilax.util;



public class tSql {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Dbcontroller dbc=new DbNdsController();
		dbc.getrs("select 1");
	//	dbc.close();
//		MoodUserIndexService.logger.info("aa");
//		indexControllor4auto.logger.info("aa");
		System.out.println("");

	}
	
}
