package com.attilax.util;

import java.sql.SQLException;


public class Dbcontroller_local extends Dbcontroller {

	
	public Dbcontroller_local()
	{
		super(5);
	this.conn=getConnLocalhost();
	this.MySqlClassCb9=new MySqlClass();
	try {
		this.statement=conn.createStatement();
	} catch (SQLException e) {
		 
		e.printStackTrace();
		throw new RuntimeException(e);
	}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
