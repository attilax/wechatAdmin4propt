/**
 * 
 */
package com.focusx.service;

import com.attilax.spri.SpringUtil;

/**
 * @author ASIMO
 *
 */
public class tUserx {

	/**
	@author attilax 老哇的爪子
	@since   p2k k_a_x
	 
	 */
	public static void main(String[] args) {
		WeixinUserManagerService x=SpringUtil.getBean(WeixinUserManagerService.class);
		x.checkWeixinUser("aaa");
		System.out.println("---");
		

	}

}
