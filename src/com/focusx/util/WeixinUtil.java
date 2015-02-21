package com.focusx.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.focusx.entity.weixin.UserInfo;
import com.focusx.entity.weixin.UserList;
import com.focusx.entity.weixin.pojo.AccessToken;
import com.focusx.entity.weixin.pojo.Menu;
import com.focusx.listener.TokenThread;

/** 
 * 
 * 公众平台通用接口工具类 
 * author:vincente  2013-11-5 
 */@Deprecated
public class WeixinUtil {

	private static Logger log = Logger.getLogger(WeixinUtil.class);
	
	// 获取access_token的接口地址（GET） 限200（次/天）  
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	// 菜单创建（POST） 限100（次/天）  
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN"; 
	
	//公众号向微信推送消息 开发者在一段时间内（目前为24小时）可以调用客服消息接口
	public static String custom_send_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	
	//获取关注服务号的粉丝openid
	public static String user_get_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	
	// 获取用户基本信息
	public static String user_info_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
	
	/** 
	 * 创建菜单 
	 *  
	 * @param menu 菜单实例 
	 * @param accessToken 有效的access_token 
	 * @return 0表示成功，其他值表示失败 
	 */  
	public static int createMenu(Menu menu, String accessToken) {  
	    int result = 0;  
	  
	    // 拼装创建菜单的url  
	    String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);  
	    // 将菜单对象转换成json字符串  
	    String jsonMenu = JSONObject.fromObject(menu).toString();  
	    // 调用接口创建菜单  
	    JSONObject jsonObject = httpsRequest(url, "POST", jsonMenu);  
	  
	    if (null != jsonObject) {  
	        if (0 != jsonObject.getInt("errcode")) {  
	            result = jsonObject.getInt("errcode");  
	            log.error("创建菜单失败 errcode:{} errmsg:{} "+jsonObject.getInt("errcode")+" "+jsonObject.getString("errmsg"));  
	        }  
	    }  
	  
	    return result;  
	}
	
	/**
	 * 公共账户号向微信用户发送消息
	 * @param jsonData 要发送的数据的json格式
	 * @param accessToken 有效的access_token 
	 * @return 0表示成功，其他值表示失败 
	 */
	public static JSONObject sendMessage(String jsonData,String accessToken){
		//String result = 0;  
		
		//拼装url 
		String url = custom_send_url.replace("ACCESS_TOKEN", accessToken);  
		JSONObject jsonObject = null;
		try{
			 // 调用接口发送消息
		    jsonObject = httpsRequest(url, "POST", jsonData); 
		    if (null != jsonObject) {  
		    	int errcode = jsonObject.getInt("errcode");
		    	
		    	if(errcode == 42001 || errcode == 40001 || errcode == 40014){//token 过期
		    		
		    		CountDownLatch countDown = new CountDownLatch(1);
		    		TokenThread tokenThread = new TokenThread();
		    		tokenThread.setCountDown(countDown);
		    		tokenThread.start();
		    		countDown.await(30,TimeUnit.SECONDS);
		    		
		    		return sendMessage(jsonData,Constant.token.getToken());
		    		
		    	}
		    	else if (0 != jsonObject.getInt("errcode")) {  
		            log.error("发送消息失败 errcode:{} errmsg:{} "+jsonObject.getInt("errcode")+" "+jsonObject.getString("errmsg"));  
		        }  
		    } 
		}catch(Exception e){
			log.error("发送微信消息失败: ",e); 
		}
	   
	    return jsonObject;
		
	}
	
	/** 
	 * 获取access_token 
	 *  
	 * @param appid 凭证 
	 * @param appsecret 密钥 
	 * @return 
	 */ 
	public static AccessToken getAccessToken(String appid, String appsecret) {  
	   AccessToken accessToken = null;
		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{} "+ jsonObject.getInt("errcode") + " "+ jsonObject.getString("errmsg"));
			}
		}
		return accessToken;  
	}
	/** 
     * 发起https请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr){
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            //设置是否可以向httpurlConnect读入读出
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
            //建立连接
            httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            
            jsonObject = JSONObject.fromObject(buffer.toString());  
        } catch (ConnectException ce) {  
            log.error("Weixin server connection timed out.");  
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
        }  
        return jsonObject;  
    }  
	
	/** 
     * 发起http请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr){
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            URL url = new URL(requestUrl);  
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            
/*            if(!"".equals(buffer.toString().trim())){
            	jsonObject = JSONObject.fromObject(buffer.toString());  
            }*/
        } catch (ConnectException ce) {  
            log.error("Weixin server connection timed out.");  
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
        }  
        return buffer.toString();  
    }
	
	/**
	 * 获取关注者列表
	 * 
	 * @param accessToken
	 *            调用接口凭证
	 * @param nextOpenid
	 *            第一个拉取的OPENID，不填默认从头开始拉取
	 * @return
	 */
	public static UserList getUserList(String accessToken, String nextOpenid) {
		UserList user = null;
		String url = user_get_url.replace("ACCESS_TOKEN", accessToken);
		url = url.replace("NEXT_OPENID", nextOpenid);

		// 调用接口获取关注者列表
		JSONObject jsonObject = httpsRequest(url, "GET", null);
		if (jsonObject != null) {
			log.info("关注者列表返回的JSON>>>>>	" + jsonObject);
			if (jsonObject.containsValue("errcode")) {
				if (0 != jsonObject.getInt("errcode")) {
					log.error("获取关注者列表失败 errcode:{} errmsg:{} " + jsonObject.getInt("errcode") + " " + jsonObject.getString("errmsg"));
					return null;
				}
			}
			user = new UserList(jsonObject);
		}
		return user;
	}
	
	public static UserInfo getUserInfo(String accessToken, String openid) {
		UserInfo userInfo = null;
		String url = user_info_url.replace("ACCESS_TOKEN", accessToken);
		url = url.replace("OPENID", openid);

		// 调用接口获取用户信息
		JSONObject jsonObject = httpsRequest(url, "GET", null);
		if (jsonObject != null) {
			log.info("用户信息表返回的JSON>>>>>	" + jsonObject);
			if (jsonObject.containsValue("errcode")) {
				if (0 != jsonObject.getInt("errcode")) {
					log.error("获取用户信息失败 errcode:{} errmsg:{} " + jsonObject.getInt("errcode") + " " + jsonObject.getString("errmsg"));
					return null;
				}
			}
			userInfo = new UserInfo(jsonObject);
		}
		return userInfo;
	}
}
