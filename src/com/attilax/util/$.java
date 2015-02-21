package com.attilax.util;

import java.io.File;

import com.attilax.core;

public class $ {

	public static void log(String string) {
		core.log(string);
		
	}

	public static boolean file_exists(String newPath) {
		   File file=new File(newPath);    
		    return( file.exists())  ;  
	}

}
