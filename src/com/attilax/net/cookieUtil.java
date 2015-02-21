/**
 * @author attilax 老哇的爪子
	@since  2014-6-17 上午09:33:22$
 */
package com.attilax.net;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.attilax.util.tryX;

//import cn.freeteam.util.EscapeUnescape;

/**
 * @author attilax 老哇的爪子
 *@since 2014-6-17 上午09:33:22$
 */
public class cookieUtil {

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-6-17 上午09:33:22$
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子 上午09:33:22 2014-6-17

	}

	// attilax 老哇的爪子 上午09:33:22 2014-6-17

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-6-17 上午09:38:43$
	 * 
	 * @param string
	 * @param name
	 */@Deprecated
	public static void add(String cookName, final String val,
			HttpServletResponse response) {

		{
			// attilax 老哇的爪子 上午09:38:43 2014-6-17
			// = java.net.URLDecoder.decode(uname, "UTF-8")
			String encode = new tryX<String>() {

				@Override
				public String item(Object t) throws Exception {
					// attilax 老哇的爪子 下午04:35:33 2014-6-17
					return java.net.URLEncoder.encode(val, "utf-8");
				}
			}.$("");

			Cookie cookie = new Cookie(cookName, encode);
			cookie.setPath("/");
			cookie.setMaxAge(1000 * 60 * 60 * 24 * 365);// 有效时间为一年
			response.addCookie(cookie);
		}

		String encode = new tryX<String>() {

			@Override
			public String item(Object t) throws Exception {
				// attilax 老哇的爪子 下午04:35:33 2014-6-17
				return java.net.URLEncoder.encode(val, "gbk");
			}
		}.$("");
		Cookie cookie = new Cookie(cookName + "_gbk", encode);
		cookie.setPath("/");
		cookie.setMaxAge(1000 * 60 * 60 * 24 * 365);// 有效时间为一年
		response.addCookie(cookie);

	}

	public static String $_COOKIE(String cookieName, HttpServletRequest request) 
	
	{
		return getCookie(cookieName,request);
	}
	public static String getCookie(String cookieName, HttpServletRequest request) {
		// attilax 老哇的爪子 上午09:38:43 2014-6-17
		CookieUtilO6 c = new CookieUtilO6(request, null, 1000 * 60 * 60 * 24
				* 365);
		final String cookieValue = c.getCookieValue(cookieName);
		// = java.net.URLDecoder.decode(uname, "UTF-8")

		return new tryX<String>() {

			@Override
			public String item(Object t) throws Exception {
				// attilax 老哇的爪子 下午04:35:33 2014-6-17
				return java.net.URLDecoder.decode(cookieValue, "UTF-8");
			}
		}.$("");

	}

	/**
	@author attilax 老哇的爪子
		@since  2014-6-17 下午06:06:13$
	
	 * @param string
	 * @param string2
	 * @param object
	 */
	public static void setcookie(String cookName, String val,HttpServletResponse response) {
		// attilax 老哇的爪子  下午06:06:13   2014-6-17 
		add(cookName, val, response);
	}
}

// attilax 老哇的爪子