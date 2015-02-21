package com.attilax.util;

import java.util.ArrayList;

import antlr.collections.List;

public class tGC {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		java.util.List  l=  new ArrayList();
		
		for(int j=0;j<500;j++)
		{
		
			 String s=getTestStr(); 
			 l.add(s);
			 Thread.sleep(2000);
			 System.out.println(" now :"+j);
		}
		

	}

	private static String getTestStr() {
		 StringBuilder sb=new StringBuilder();
		 for(int i=0;i<99999;i++)
		 {
			 sb.append("asdfdsfffffffffffj;jkljkjl;j;klj;ljk;lj;j;j;l");
		 }
		return sb.toString();
	}

}
