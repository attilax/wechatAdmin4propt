/**
 * 
 */
package com.attilax.hb;

import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

/**
 * @author ASIMO
 *
 */
public class HbX {

	/**
	@author attilax 老哇的爪子
	@since   p2k l_7_49
	 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	public static boolean ifMysql(Session session) {
		// attilax 老哇的爪子 上午10:24:49 2014年5月10日
		String dialect = ((SessionImpl) session).getFactory().getDialect()
				.getClass().getName();
		// org.hibernate.dialect.MySQLDialect
		System.out.println(dialect);
		if (dialect.trim().toLowerCase().contains("MySQLDialect".toLowerCase()) || dialect.trim().toLowerCase().contains("MySQL5Dialect".toLowerCase()))
			return true;
		else
			return false;
	}

}
