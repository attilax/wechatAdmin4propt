/**
 * @author attilax 老哇的爪子
	@since  2014-5-26 上午10:58:29$
 */
package com.focusx.util;

import javax.servlet.http.HttpServletRequest;

import com.attilax.util.tryX;
import com.foksda.mass.groupsendSvs;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-5-26 上午10:58:29$
 */
public class ajaxSvs {

	/**
	@author attilax 老哇的爪子
		@since  2014-5-26 上午10:58:29$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  上午10:58:29   2014-5-26 
		

	}
	
	/**
	 * struts can intterupt excep info  ...cant show ..so ,zihao  err return str
	@author attilax 老哇的爪子
		@since  2014-5-28 上午11:18:11$
	
	 * @param req
	 * @return
	 */
	public static String $(final HttpServletRequest req) {
		final groupsendSvs c = new groupsendSvs();
		return new tryX<String>() {

			@Override
			public String item(Object t) throws Exception {
				// attilax 老哇的爪子 上午10:56:11 2014-5-28
				return c.groupSend_MsgID_GrpID(req);
			}
			// struts can intterupt excep info ...cant show ..so ,zihao err
			// return str
			// so must $defValIsErrmsg

		

		}.$defValIsErrmsgSmp();

	}
	//  attilax 老哇的爪子 上午10:58:29   2014-5-26   
}

//  attilax 老哇的爪子