package com.attilax.wechat;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.focusx.entity.weixin.pojo.AccessToken;
import com.focusx.entity.weixin.sendMessage.Text;
import com.focusx.util.ConfigService;
import com.focusx.util.Constant;
import com.focusx.util.MessageUtil;
import com.focusx.util.WeixinUtil;

public class WeiXinMethod_del {

	private static Logger logger = Logger.getLogger("WeiXinMethod");

	/**
	 * 向用户发送消息,止方法 不使用
	 * 
	 * @param respContent
	 *            消息内容
	 */
	/*public static void send(String openid, String respContent) {
		try {
			Thread thread = Thread.currentThread();
			com.focusx.entity.weixin.sendMessage.TextMessage textMessage = new com.focusx.entity.weixin.sendMessage.TextMessage();
			textMessage.setTouser(openid);
			Text text = new Text();
			text.setContent(respContent);
			textMessage.setMsgtype(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setText(text);

			String accessToken = Constant.token.getToken();
			JSONObject json = JSONObject.fromObject(textMessage);
			String jsonData = json.toString();

			logger.info(jsonData);
			// 调用微信接口开始发送消息
			int errcode = WeixinUtil.sendMessage(jsonData, accessToken);

			if (errcode == 40001 || errcode == 40014 || errcode == 41001 || errcode == 42001) {
				logger.info("定时器生成的微信授权码无效，系统正在重新生成");
				ConfigService config = new ConfigService();
				String appid = config.getWxProperty("APPID");
				String appsecret = config.getWxProperty("APPSECRET");
				AccessToken newAccessToken = WeixinUtil.getAccessToken(appid, appsecret);
				if (newAccessToken == null) {
					for (int i = 1; i < 100; i++) {
						thread.sleep(1000 * 2);
						logger.info("微信接口[获取AccessToken]发生超时连接,每隔2秒重新连接腾讯微信服务器, 第[" + i + "]次");
						newAccessToken = WeixinUtil.getAccessToken(appid, appsecret);
						if (newAccessToken != null) {
							break;
						}
					}
				}
				if (newAccessToken != null) {
					logger.info("---------------[微信授权码重新获取成功]--------");
					logger.info("重新发送消息>>>" + jsonData);
					Constant.token = newAccessToken;
					errcode = WeixinUtil.sendMessage(jsonData, newAccessToken.getToken());
					// 如果发送失败，则每2秒钟再发一次，一共发送50次
					if (errcode != 0) {
						logger.info("重新发送消息失败,系统启动发送循环程序>>>" + jsonData);
						for (int i = 0; i < 100; i++) {
							thread.sleep(1000 * 2);
							errcode = WeixinUtil.sendMessage(jsonData, newAccessToken.getToken());
							if (errcode == 0)
								break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
