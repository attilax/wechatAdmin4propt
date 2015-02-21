/**
 * @author attilax 老哇的爪子
	@since  2014-6-17 上午09:28:00$
 */
package com.attilax.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.attilax.net.cookieUtil;
import com.attilax.util.tryX;
//import com.focusx.interceptor.promise;

import m.secury.callbackItfs;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-6-17 上午09:28:00$
 */
public class loginUtil {

	/**
	@author attilax 老哇的爪子
		@since  2014-6-17 上午09:28:00$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  上午09:28:00   2014-6-17 

	}
	//  attilax 老哇的爪子 上午09:28:00   2014-6-17   

	/**
	@author attilax 老哇的爪子
		@since  2014-6-17 上午09:30:39$
	
	 * @param callbackItfs
	 */
	public static void setToken(callbackItfs callbackItfs) {
		// attilax 老哇的爪子  上午09:30:39   2014-6-17 
		callbackItfs.callMethod("");
		
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-6-17 上午09:37:37$
	
	 * @param name
	 * @param id
	 * @param httpServletResponse 
	 */
	public static void setToken(final String name, final Integer id,final HttpServletResponse httpServletResponse) {
		// attilax 老哇的爪子  上午09:37:37   2014-6-17 
		new tryX<Object>(){

			@Override
			public Object item(Object t) throws Exception {
				// attilax 老哇的爪子  上午09:50:43   2014-6-17 
				cookieUtil.add("uname",name,httpServletResponse);
				cookieUtil.add("uid",id.toString(),httpServletResponse);
				return null;
			}}.$("");
		
		
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-6-17 上午09:57:08$
	
	 * @param object
	 */
	public static void exTok(final com.attilax.promise object) {
		// attilax 老哇的爪子  上午09:57:08   2014-6-17 
		new tryX<Object>(){

			@Override
			public Object item(Object t) throws Exception {
				// attilax 老哇的爪子  上午10:45:39   2014-6-17 
				object.$();
				return null;
			}}.$("");
		
		
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-6-17 上午10:13:13$
	
	 * @return
	 */
	public static String getuid(HttpServletRequest request) {
		// attilax 老哇的爪子  上午10:13:13   2014-6-17 
		
		return cookieUtil.getCookie("uid", request);
	}
	
	public static String getuname(HttpServletRequest request) {
		// attilax 老哇的爪子  上午10:13:13   2014-6-17 
		
		return cookieUtil.getCookie("uname", request);
	}
}

//  attilax 老哇的爪子