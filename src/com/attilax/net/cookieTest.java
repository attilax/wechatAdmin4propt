/**
 * @author attilax 老哇的爪子
	@since  2014-6-17 下午04:47:09$
 */
package com.attilax.net;

import com.attilax.util.tryX;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-6-17 下午04:47:09$
 */
public class cookieTest {

	/**@category
	@author attilax 老哇的爪子
		@since  2014-6-17 下午04:47:09$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子 下午04:47:09 2014-6-17
		String encode = new tryX<String>() {

			@Override
			public String item(Object t) throws Exception {
				// attilax 老哇的爪子 下午04:35:33 2014-6-17
				return java.net.URLEncoder.encode("管理员", "utf-8");
			}
		}.$("");
		System.out.println(encode);
//		$cookie("");
//		cookieUtil.setcookie("uname","val",null);
//		cookieUtil.$_COOKIE("uname",null);

	}
	//  attilax 老哇的爪子 下午04:47:09   2014-6-17   

	/**
	@author attilax 老哇的爪子
		@since  2014-6-17 下午05:49:19$
	
	 * @param string
	 */
	private static void $cookie(String string) {
		// attilax 老哇的爪子  下午05:49:19   2014-6-17 
		
	}
}

//  attilax 老哇的爪子