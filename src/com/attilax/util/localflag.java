/**
 * @author attilax 老哇的爪子
	@since  o8i 1_45_44$
 */
package com.attilax.util;
import com.attilax.core;
 import static  com.attilax.core.*;
import java.util.*;
import java.net.*;
import java.io.*;
/**
 * @author  attilax 老哇的爪子
 *@since  o8i 1_45_44$
 */
public class localflag {

	/**
	@author attilax 老哇的爪子
		@since  o8i 1_45_44   
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  1_45_44   o8i 
		 System.out.println(islocal());
	}
	//  attilax 老哇的爪子 1_45_44   o8i   

	/**
	@author attilax 老哇的爪子
		@since  o8i 1_46_f   
	
	 * @return
	 */
	private static String islocal() {
		// attilax 老哇的爪子  1_46_f   o8i 
		if(new File("C:\\localFlag").exists())
		return "true";else return "false";
		
	}
}

//  attilax 老哇的爪子