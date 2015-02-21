/**
 * @author attilax 老哇的爪子
	@since  2014-6-10 上午09:21:00$
 */
package com.attilax.wechat;

import net.sf.json.JSONObject;

import com.attilax.retry;
import com.focusx.entity.weixin.pojo.AccessToken;
import com.focusx.listener.TokenThread;
import com.focusx.util.ConfigService;
import com.foksda.mass.retryRzt;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-6-10 上午09:21:00$
 */
public class wechatUtil extends WeixinUtil {
	//  attilax 老哇的爪子 上午09:21:00   2014-6-10   
	

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-5-16 下午03:49:22$
	 * 
	 * @return
	 */
	private static String ACCESS_TOKEN() {
		// attilax 老哇的爪子 下午03:49:22 2014-5-16
		return new TokenThread().loadTokenByFile();
	}
	
	public static String acc_token()
	{
		retryRzt rzt=new retryRzt();	
	return	new retry<String>(5, rzt) {

			@Override
			public Boolean item(Object t) throws Exception {
				// attilax 老哇的爪子 下午11:49:37 2014年6月9日
				String tk = ACCESS_TOKEN();
				;
				String r = WeixinUtil.queryMenu(tk);
				JSONObject jo = JSONObject.fromObject(r);

				if (jo.get("errcode") == null) {
					this.setResult(tk);
					return true;
				}
				// else
				tk = fromWeb();
				String r2 = WeixinUtil.queryMenu(tk);
				JSONObject jo2 = JSONObject.fromObject(r2);
				if (jo2.get("errcode") == null) {
					this.setResult(tk);
					return true;
				}

				{
					this.setResult(r2);

					return false;
				}

			}

			private String fromWeb() {
				// attilax 老哇的爪子 上午12:08:31 2014年6月10日
				// 第三方用户唯一凭证
				ConfigService configService = new ConfigService();
				String appId = configService.getWxProperty("APPID");
				// 第三方用户唯一凭证密钥
				String appSecret = configService
						.getWxProperty("APPSECRET");
				// 调用接口获取access_token
				AccessToken accessToken = WeixinUtil.getAccessToken(
						appId, appSecret);
				return accessToken.getToken();

			}

		}.$O69();
	}
}

//  attilax 老哇的爪子