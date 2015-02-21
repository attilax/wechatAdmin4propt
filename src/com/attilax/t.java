/**
 * @author attilax 老哇的爪子
	@since  o72 m532$
 */
package com.attilax;
import com.attilax.core;
import com.attilax.util.DateUtil;
 import static  com.attilax.core.*;
import java.util.*;
import java.net.*;
import java.io.*;
/**
 * @author  attilax 老哇的爪子
 *@since  o72 m532$
 */
public class t {

	/**
	@author attilax 老哇的爪子
		@since  o72 m532$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  m532   o72 
		String sqlString =String.format(  "SELECT   *   FROM  t_mb_awardweixin   where activityId=" + "3" + " and openId='" + "5"
				+ "' and   createTime='%s'",DateUtil.today_Start());
	System.out.println(sqlString);

	}
	//  attilax 老哇的爪子 m532   o72   
}

//  attilax 老哇的爪子