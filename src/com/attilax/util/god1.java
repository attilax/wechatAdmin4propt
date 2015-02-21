package com.attilax.util;

public class god1 {

	public static boolean isInt(String filename) {

 
		return isNumeric(filename);
	}
	
	public static boolean isNumeric(String str){
		  for (int i = str.length();--i>=0;){   
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
		 }

}
