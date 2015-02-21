/**
 * @author attilax 老哇的爪子
	@since  2014-6-19 下午05:35:31$
 */
package com.focusx.dao.impl;

import com.attilax.spri.SpringUtil;
import com.attilax.util.HibernateSessionFactory;
import com.focusx.dao.WeixinUserManagerDao;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-6-19 下午05:35:31$
 */
public class weixinuserT {

	/**
	@author attilax 老哇的爪子
		@since  2014-6-19 下午05:35:31$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  下午05:35:31   2014-6-19 
		WeixinUserManagerDaoImpl c=new WeixinUserManagerDaoImpl();
			c.sess=(springUtil4backend.getSession());
	System.out.println(c.getWeixinUserByOpenId("xx"));	;
	//syso
	}
	//  attilax 老哇的爪子 下午05:35:31   2014-6-19   
}

//  attilax 老哇的爪子