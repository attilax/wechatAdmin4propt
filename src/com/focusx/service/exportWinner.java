/**
 * @author attilax 老哇的爪子
	@since  2014-6-11 上午10:29:50$
 */
package com.focusx.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.attilax.office.excelUtil;
import com.attilax.spri.SpringUtil;
import com.focusx.entity.TMbAwardWeixinResult;

/**
 * @author attilax 老哇的爪子
 *@since 2014-6-11 上午10:29:50$
 */
public class exportWinner {

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-6-11 上午10:29:50$
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子 上午10:29:50 2014-6-11

	}

	public static void exp(HttpServletResponse response, String idx) {
		String titles = "奖品名称,中奖粉丝昵称,中奖时间,姓名,电话,地址,会员卡号";
		String filds = "awardName,nickname,awardTime,awardUserName,awardPhone,awardAddress,memcardno";
		int id = Integer.parseInt(idx);
		AwardWeixinManagerService awardWeixinManagerService = (AwardWeixinManagerService) SpringUtil
				.getBean("awardWeixinManagerService");
		List<TMbAwardWeixinResult> li = awardWeixinManagerService
				.getAwardWeixinByActivity(id);
		excelUtil.toExcel(titles, filds, li, response);
		
		try {
			System.out.println("--ok");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	// attilax 老哇的爪子 上午10:29:50 2014-6-11
}

// attilax 老哇的爪子