/**
 * @author attilax 老哇的爪子
	@since  2014-6-11 上午10:09:59$
 */
package com.attilax.util;

import javax.servlet.http.HttpServletRequest;

import com.attilax.SafeVal;

/**
 * @author attilax 老哇的爪子
 *@since 2014-6-11 上午10:09:59$
 */
public class request {

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-6-11 上午10:09:59$
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子 上午10:09:59 2014-6-11

	}

	public static String getPara(HttpServletRequest req, String paraName) {
		return SafeVal.val(req.getParameter(paraName));
	}
	public static String getParameter(HttpServletRequest req, String paraName) {
		return SafeVal.val(req.getParameter(paraName));
	}
	
	// attilax 老哇的爪子 上午10:09:59 2014-6-11
}

// attilax 老哇的爪子