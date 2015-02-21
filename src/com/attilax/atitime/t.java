/**
 * @author attilax 老哇的爪子
	@since  2014-7-2 下午6:14:55$
 */
package com.attilax.atitime;
 
import java.util.*;
import java.net.*;
import java.io.*;
/**
 * @author  attilax 老哇的爪子
 *@since  2014-7-2 下午6:14:55$
 */
public class t {

	//   izu o72  老哇的爪子  Attilax
	/**
	@author attilax 老哇的爪子
		@since  2014-7-2 下午6:14:55$
	
	 * @param args
	 */
	 
		  public static void main(String[] args) {
		    Calendar cal = Calendar.getInstance();
		    int day = cal.get(Calendar.DATE);
		    int month = cal.get(Calendar.MONTH) + 1;
		    int year = cal.get(Calendar.YEAR);
		    int dow = cal.get(Calendar.DAY_OF_WEEK);
		    int dom = cal.get(Calendar.DAY_OF_MONTH);
		    int doy = cal.get(Calendar.DAY_OF_YEAR);

		    System.out.println("Current Date: " + cal.getTime());
		    System.out.println("Day: " + day);
		    System.out.println("Month: " + month);
		    System.out.println("Year: " + year);
		    System.out.println("Day of Week: " + dow);
		    System.out.println("Day of Month: " + dom);
		    System.out.println("Day of Year: " + doy);
		 
		}
	//  attilax 老哇的爪子 下午6:14:55   2014-7-2   
}

//  attilax 老哇的爪子