package com.attilax.util;

import java.util.Date;

public class timeTester {
public	Date start ;
 String name="";
	
	public timeTester(String name) {
		 start = new Date();
		 this.name=name;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		timeTester t=new timeTester(" ca");
		for(int i=0;i<100000;i++)
		{
			 System.out.println("5");
			//String s="";
		}
		t.printUseTime();
		
	 
	

	}
	public long pasttime;
	public   void printUseTime() {
		Date end = new Date();
		  pasttime = end.getTime() - start.getTime();
		System.out.println("---timertest "+name+"检索完成，用时" + pasttime
				+ "毫秒");
	}
		

}
