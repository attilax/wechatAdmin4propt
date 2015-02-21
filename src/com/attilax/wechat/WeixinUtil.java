package com.attilax.wechat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.attilax.core;
import com.attilax.net.websitex;
import com.attilax.util.god;
import com.focusx.entity.TMbNews;
import com.focusx.entity.weixin.UserInfo;
import com.focusx.entity.weixin.UserList;
import com.focusx.entity.weixin.pojo.AccessToken;
import com.focusx.entity.weixin.pojo.Menu;
import com.focusx.entity.weixin.sendMessage.BaseMessage;
import com.focusx.entity.weixin.sendMessage.Image;
import com.focusx.entity.weixin.sendMessage.ImageMessage;
import com.focusx.entity.weixin.sendMessage.News;
import com.focusx.entity.weixin.sendMessage.NewsMessage;
import com.focusx.entity.weixin.sendMessage.Text;
import com.focusx.entity.weixin.sendMessage.TextMessage;
import com.focusx.entity.weixin.sendMessage.Video;
import com.focusx.entity.weixin.sendMessage.VideoMessage;
import com.focusx.entity.weixin.sendMessage.Voice;
import com.focusx.entity.weixin.sendMessage.VoiceMessage; //import com.focusx.thread.TokenThread;
import com.focusx.listener.TokenThread;
import com.focusx.util.Constant; //import com.focusx.util.MessageUtil;
import com.focusx.util.MyX509TrustManager;

/**
 * 
 * 公众平台通用接口工具类 author:vincente 2013-11-5
 */
public class WeixinUtil {

	private static Logger logger = Logger.getLogger(WeixinUtil.class);

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	// 公众号向微信推送消息 开发者在一段时间内（目前为24小时）可以调用客服消息接口
	public static String custom_send_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	// 获取关注者列表
	public static String user_get_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";

	// 获取用户基本信息
	public static String user_info_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";

	public static void main(String[] args) {
		
	System.out.println(queryMenu("accessToken"));	
		if("a"!="b")return;

		JSONObject o1 = new com.attilax.corePkg.JSONObject() {
			{
				put("touser", "oFhS5jvZ61Rl7jgg8wV42aH8SnP4");
				put("msgtype", "news");

				put("news", new com.attilax.corePkg.JSONObject() {
					{
						put("articles", new com.attilax.corePkg.JSONArray() {
							{
								add(new com.attilax.corePkg.JSONObject() {
									{
										put("title", "Happy Day"+god.getUUid_MMdd_HHmmss());
										put("description", "description123");
									//	put("url", "haha.htm");
										put("picurl", "images/wrong.gif");

									}
								}.$());
							}
						}.$()

						);
					}
				}.$());

			}

		}.$();
	int r=	sendMessage(o1.toString(), ACCESS_TOKEN(), new Icallback<JSONObject>() {

			@Override
			public void $(JSONObject jsonObject) {
				// attilax 老哇的爪子 下午05:12:04 2014-5-19
				core.log(jsonObject.toString());

			}

		});
		System.out.println("---");
	}
	private static String ACCESS_TOKEN() {
		// attilax 老哇的爪子 下午03:49:22 2014-5-16
		return new TokenThread().loadTokenByFile();
	}
	
	
	/**
	 * {"errcode":40001,"errmsg":"invalid credential"}
	@author attilax 老哇的爪子
		@since  2014年6月9日 下午11:39:55$
	
	 * @param accessToken
	 * @return
	 */
		public static String  queryMenu( String accessToken) {
		//int result = 0;

		// 拼装创建菜单的url
		String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN".replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		core.log(url);
		String r = websitex.WebpageContent(url);
		core.log(r);
		return r;
	}
	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
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
				logger.error("创建菜单失败 errcode:{} errmsg:{} "
						+ jsonObject.getInt("errcode") + " "
						+ jsonObject.getString("errmsg"));
			}
		}

		return result;
	}

	/**
	 * 公共账户号向微信用户发送消息
	 * 
	 * @param jsonData
	 *            要发送的数据的json格式
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int sendMessage(String jsonData, String accessToken) {
		int result = 0;

		// 拼装url
		String url = custom_send_url.replace("ACCESS_TOKEN", accessToken);
		// 调用接口发送消息
		JSONObject jsonObject = httpsRequest(url, "POST", jsonData);
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				logger.error("发送消息失败 errcode:{} errmsg:{} "
						+ jsonObject.getInt("errcode") + " "
						+ jsonObject.getString("errmsg"));
				if (jsonObject.getInt("errcode") == 40001) { // access_token失效
					// 马上启动一次access_token获取，并更新
					CountDownLatch countDown = new CountDownLatch(1);
					TokenThread tokenThread = new TokenThread();
					tokenThread.setCountDown(countDown);
					tokenThread.start();

					try {
						// 设置超时的时间响应
						countDown.await(30, TimeUnit.SECONDS);
					} catch (InterruptedException e) {
						e.printStackTrace();

						try {
							// 异常情况休眠5秒
							Thread.sleep(5000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					result = sendMessage(jsonData, Constant.token.getToken());
				}
			} else {
				logger.info("发送消息成功  >>> [" + jsonData + "]");
			}
		}

		return result;
	}
	
	/**
	 * 公共账户号向微信用户发送消息
	 * 
	 * @param jsonData
	 *            要发送的数据的json格式
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static <callbackType> int sendMessage(String jsonData, String accessToken,Icallback<callbackType> callbk) {
		int result = 0;

		// 拼装url
		String url = custom_send_url.replace("ACCESS_TOKEN", accessToken);
		// 调用接口发送消息
		JSONObject jsonObject = httpsRequest(url, "POST", jsonData);
		callbk.$((callbackType) jsonObject);
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				logger.error("发送消息失败 errcode:{} errmsg:{} "
						+ jsonObject.getInt("errcode") + " "
						+ jsonObject.getString("errmsg"));
				if (jsonObject.getInt("errcode") == 40001) { // access_token失效
					// 马上启动一次access_token获取，并更新
//					CountDownLatch countDown = new CountDownLatch(1);
//					TokenThread tokenThread = new TokenThread();
//					tokenThread.setCountDown(countDown);
//					tokenThread.start();

//					try {
//						// 设置超时的时间响应
//						countDown.await(30, TimeUnit.SECONDS);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//
//						try {
//							// 异常情况休眠5秒
//							Thread.sleep(5000);
//						} catch (InterruptedException e1) {
//							e1.printStackTrace();
//						}
//					}
					//result = sendMessage(jsonData, Constant.token.getToken());
					logger.info("发送消息fail  >>> [" + jsonData + "]");
				}
			} else {
				logger.info("发送消息成功  >>> [" + jsonData + "]");
			}
		}

		return result;
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
			logger.info("关注者列表返回的JSON>>>>>	" + jsonObject);
			if (jsonObject.containsValue("errcode")) {
				if (0 != jsonObject.getInt("errcode")) {
					logger.error("获取关注者列表失败 errcode:{} errmsg:{} "
							+ jsonObject.getInt("errcode") + " "
							+ jsonObject.getString("errmsg"));
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
			logger.info("用户信息表返回的JSON>>>>>	" + jsonObject);
			if (jsonObject.containsValue("errcode")) {
				if (0 != jsonObject.getInt("errcode")) {
					logger.error("获取用户信息失败 errcode:{} errmsg:{} "
							+ jsonObject.getInt("errcode") + " "
							+ jsonObject.getString("errmsg"));
					return null;
				}
			} else {
				userInfo = new UserInfo(jsonObject);
			}
		}
		return userInfo;
	}

	/**
	 * 上传多媒体文件
	 * 
	 * @param accessToken
	 *            调用接口凭证
	 * @param type
	 *            媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @param filePath
	 *            文件绝对路径
	 * @return
	 */
	public static String upload(String accessToken, String type, String filePath) {
		String result = null;
		BufferedReader reader = null;
		HttpURLConnection con = null;

		String uploadUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=";
		StringBuilder builder = new StringBuilder();
		builder.append(uploadUrl).append(accessToken).append("&type=").append(
				type);

		try {
			File file = new File(filePath);
			if (!file.exists() || !file.isFile()) {
				throw new IOException("文件不存在");
			}

			// 连接
			URL urlObj = new URL(builder.toString());
			con = (HttpURLConnection) urlObj.openConnection();
			con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false); // post方式不能使用缓存

			// 设置请求头信息
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");

			// 设置边界
			String BOUNDARY = "----------" + System.currentTimeMillis();
			con.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);

			// 请求正文信息
			StringBuilder sb = new StringBuilder();
			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb
					.append("Content-Disposition: form-data;name=\"file\";filename=\""
							+ file.getName() + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");

			byte[] head = sb.toString().getBytes("utf-8");
			// 获得输出流
			OutputStream out = new DataOutputStream(con.getOutputStream());
			// 输出表头
			out.write(head);

			// 文件正文部分
			// 把文件已流文件的方式 推入到url中
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();

			// 结尾部分
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
			out.write(foot);
			out.flush();
			out.close();
			StringBuffer buffer = new StringBuffer();

			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con
					.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (con != null)
				con.disconnect();
			con = null;
		}
		return result;
	}

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;
		String requestUrl = access_token_url.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		core.log(requestUrl);
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
				logger.info("获取token失败 errcode:{} errmsg:{} "
						+ jsonObject.getInt("errcode") + " "
						+ jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		HttpsURLConnection httpUrlConn = null;
		BufferedReader bufferedReader = null;

		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			/*
			 * if ("GET".equalsIgnoreCase(requestMethod)){
			 * httpUrlConn.connect(); }
			 */
			httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			inputStream = httpUrlConn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);

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
			String r = buffer.toString();
			core.log(r);
			jsonObject = JSONObject.fromObject(r);
		} catch (ConnectException ce) {
			logger.error("Weixin server connection timed out.");
		} catch (Exception e) {
			logger.error("https request error:{}", e);
		} finally {

			try {
				if (httpUrlConn != null) {
					httpUrlConn.disconnect();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return jsonObject;
	}

	/**
	 * 发起http请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static String httpRequest(String requestUrl, String requestMethod,
			String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		HttpURLConnection httpUrlConn = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;

		InputStream inputStream = null;
		try {
			URL url = new URL(requestUrl);
			httpUrlConn = (HttpURLConnection) url.openConnection();

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
			inputStream = httpUrlConn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);

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

			/*
			 * if(!"".equals(buffer.toString().trim())){ jsonObject =
			 * JSONObject.fromObject(buffer.toString()); }
			 */
		} catch (ConnectException ce) {
			logger.error("Weixin server connection timed out.");
		} catch (Exception e) {
			logger.error("https request error:{}", e);
		} finally {

			try {
				if (httpUrlConn != null) {
					httpUrlConn.disconnect();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return buffer.toString();
	}

	/***
	 * ，单位米
	 * 
	 * @param lon1
	 * @param lat1
	 * @param lon2
	 * @param lat2
	 * @return
	 */
	private static final double EARTH_RADIUS = 6378137;

	public static double getDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = getRad(lat1);
		double radLat2 = getRad(lat2);
		double a = radLat1 - radLat2;
		double b = getRad(lng1) - getRad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;

		logger.info("计算结果   >>> " + s);

		return s;
	}

	private static double getRad(double data) {
		return data / 180 * Math.PI;
	}

	/*
	 * public static int getDistance(double lon1, double lat1, double lon2,
	 * double lat2){
	 * 
	 * double radLat1 = getRad(lat1); double radLat2 = getRad(lat2); double
	 * radLon1 = getRad(lon1); double radLon2 = getRad(lon2);
	 * 
	 * 
	 * if (radLat1 < 0) radLat1 = Math.PI / 2 + Math.abs(radLat1);// south if
	 * (radLat1 > 0) radLat1 = Math.PI / 2 - Math.abs(radLat1);// north if
	 * (radLon1 < 0) radLon1 = Math.PI 2 - Math.abs(radLon1);// west if (radLat2
	 * < 0) radLat2 = Math.PI / 2 + Math.abs(radLat2);// south if (radLat2 > 0)
	 * radLat2 = Math.PI / 2 - Math.abs(radLat2);// north if (radLon2 < 0)
	 * radLon2 = Math.PI 2 - Math.abs(radLon2);// west
	 * 
	 * double x1 = EARTH_RADIUS Math.cos(radLon1) Math.sin(radLat1); double y1 =
	 * EARTH_RADIUS Math.sin(radLon1) Math.sin(radLat1); double z1 =
	 * EARTH_RADIUS Math.cos(radLat1);
	 * 
	 * double x2 = EARTH_RADIUS Math.cos(radLon2) Math.sin(radLat2); double y2 =
	 * EARTH_RADIUS Math.sin(radLon2) Math.sin(radLat2); double z2 =
	 * EARTH_RADIUS Math.cos(radLat2);
	 * 
	 * double d = Math.sqrt((x1 - x2) (x1 - x2) + (y1 - y2) (y1 - y2)+ (z1 - z2)
	 * (z1 - z2));
	 * 
	 * double theta = Math.acos((EARTH_RADIUS EARTH_RADIUS + EARTH_RADIUS
	 * EARTH_RADIUS - d d) / (2 EARTH_RADIUS EARTH_RADIUS));
	 * 
	 * double dist = theta EARTH_RADIUS;
	 * 
	 * return new Double(dist).intValue(); }
	 */

	/*
	 * public static int getDistance(double lon1, double lat1, double lon2,
	 * double lat2){
	 * 
	 * double x = getRad(lon1); double y = getRad(lat1); double a =
	 * getRad(lon2); double b = getRad(lat2);
	 * 
	 * //double R = 6371000; double rad = Math.acos(Math.cos(y) Math.cos(b)
	 * Math.cos(x - a) + Math.sin(y) Math.sin(b)); if (rad > Math.PI){ rad =
	 * Math.PI 2 - rad; }else{ rad = EARTH_RADIUS rad; }
	 * 
	 * return new Double(rad).intValue(); }
	 */

	public static TextMessage buildTextMessage(String openId, String content) {
		if (openId != null && !"".equals(openId)) {
			com.focusx.entity.weixin.sendMessage.TextMessage textMsg = new com.focusx.entity.weixin.sendMessage.TextMessage();
			Text txt = new Text();
			txt.setContent(content);
			textMsg.setText(txt);
			textMsg.setTouser(openId);
			textMsg.setMsgtype(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			return textMsg;
		}
		return null;

	}

	/***
	 * 
	 * @param openId
	 * @param newsList
	 * @return
	 */
	public static NewsMessage buildLocationMessage(String openId,
			List<TMbNews> newsList, TMbNews bestNews) {

		if (openId != null && !"".equals(openId) && newsList != null
				&& newsList.size() > 0) {

			// 构建图文消息
			com.focusx.entity.weixin.sendMessage.NewsMessage clientMsg = new com.focusx.entity.weixin.sendMessage.NewsMessage();
			clientMsg.setTouser(openId);
			clientMsg.setMsgtype(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			News news = new News();
			List<com.focusx.entity.weixin.sendMessage.Article> artList = new ArrayList<com.focusx.entity.weixin.sendMessage.Article>();

			// 这是固定的，以后要修改，需要到指定目录信息这里面的规则图片
			com.focusx.entity.weixin.sendMessage.Article topArt = new com.focusx.entity.weixin.sendMessage.Article();
			topArt.setTitle("【附近门店】");
			topArt.setDescription("");
			StringBuilder newsUrl = new StringBuilder();
			if (bestNews != null) {
				newsUrl.append(Constant.NEWS_WEB_SITE).append(
						Constant.NEWS_HTML_PATH).append(bestNews.getHtmlName());
			}
			topArt.setUrl(newsUrl.toString());
			StringBuilder topPicUrl = new StringBuilder();
			topPicUrl.append(Constant.WEB_URL).append(
					"weixin/images/sign/daysigntop.jpg");
			topArt.setPicurl(topPicUrl.toString());

			artList.add(topArt);

			// 小图图片
			StringBuilder smallPicUrl = new StringBuilder();
			smallPicUrl.append(Constant.WEB_URL).append(
					"weixin/images/sign/loc");
			int index = 0;

			for (TMbNews oneNews : newsList) {
				++index;
				com.focusx.entity.weixin.sendMessage.Article oneArt = new com.focusx.entity.weixin.sendMessage.Article();

				// 统一的路径
				oneArt.setUrl(newsUrl.toString());
				// 显示位置与距离
				oneArt.setTitle(oneNews.getDescription());
				// 统一图片
				oneArt.setPicurl(smallPicUrl + "" + index + ".jpg");

				artList.add(oneArt);
				if (index == 4) {
					index = 0;
				}
			}
			news.setArticles(artList);
			clientMsg.setNews(news);

			return clientMsg;

		} else {
			logger.info("");
		}

		return null;

	}

	/***
	 * 
	 * @param openId
	 * @param newsList
	 * @return
	 */
	public static NewsMessage builderSignMessage(String openId,
			List<TMbNews> newsList) {

		if (openId != null && !"".equals(openId) && newsList != null
				&& newsList.size() > 0) {

			// 构建图文消息
			com.focusx.entity.weixin.sendMessage.NewsMessage clientMsg = new com.focusx.entity.weixin.sendMessage.NewsMessage();
			clientMsg.setTouser(openId);
			clientMsg.setMsgtype(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			News news = new News();
			List<com.focusx.entity.weixin.sendMessage.Article> artList = new ArrayList<com.focusx.entity.weixin.sendMessage.Article>();

			// 这是固定的，以后要修改，需要到指定目录信息这里面的规则图片
			com.focusx.entity.weixin.sendMessage.Article topArt = new com.focusx.entity.weixin.sendMessage.Article();
			topArt.setTitle("【门店签到】活动规则");
			topArt.setDescription("");
			StringBuilder ruleUrl = new StringBuilder();
			ruleUrl.append(Constant.WEB_URL).append(
					"weixin/mobile/daysignrules.jsp");
			topArt.setUrl(ruleUrl.toString());
			StringBuilder topPicUrl = new StringBuilder();
			topPicUrl.append(Constant.WEB_URL).append(
					"weixin/images/sign/daysigntop.jpg");
			topArt.setPicurl(topPicUrl.toString());

			artList.add(topArt);

			/*
			 * System.out.println(topArt.getUrl());
			 * System.out.println(topArt.getTitle());
			 * System.out.println(topArt.getPicurl());
			 * System.out.println(topArt.getDescription());
			 */

			StringBuilder smallPicUrl = new StringBuilder();
			smallPicUrl.append(Constant.WEB_URL).append(
					"weixin/images/sign/loc");
			int index = 0;
			for (TMbNews oneNews : newsList) {
				++index;
				com.focusx.entity.weixin.sendMessage.Article oneArt = new com.focusx.entity.weixin.sendMessage.Article();

				// System.out.println(oneNews.getDescription());
				// System.out.println(oneNews.getTitle());
				// System.out.println(oneNews.getMainText());

				// oneArt.setDescription(oneNews.getDescription());
				oneArt.setTitle(oneNews.getDescription());
				oneArt.setDescription("");
				// oneArt.setTitle("title");
				// html路径
				StringBuilder url = new StringBuilder();
				// 图片路径
				url.append(Constant.WEB_URL);

				// 点击签到地址
				url.append("weixin");
				url.append(oneNews.getHtmlName());

				// logger.info("签到url  >>> "+url.toString());
				// logger.info(smallPicUrl+""+index+".jpg");
				oneArt.setUrl(url.toString());
				oneArt.setPicurl(smallPicUrl + "" + index + ".jpg");
				artList.add(oneArt);
			}
			news.setArticles(artList);
			clientMsg.setNews(news);

			return clientMsg;

		} else {
			logger.info("openId >> " + openId + "   签到图文  >>> " + newsList);
		}

		return null;
	}

	/**
	 * 构建图文消息
	 * 
	 * @author attilax 老哇的爪子
	 * @since 2014-5-19 下午02:08:14$
	 * 
	 * @param openId
	 * @param newsList
	 * @return
	 */
	public static NewsMessage buildNewsMessage(String openId,
			List<TMbNews> newsList) {

		if (openId != null && !"".equals(openId) && newsList != null) {

			// "https://open.weixin.qq.com/connect/oauth2/authorize?appid=xxxx&redirect_uri=&response_type=code&scope=snsapi_base&state=1#wechat_redirect"
			// ;

			// 构建图文消息
			com.focusx.entity.weixin.sendMessage.NewsMessage clientMsg = new com.focusx.entity.weixin.sendMessage.NewsMessage();
			clientMsg.setTouser(openId);
			clientMsg.setMsgtype(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			News news = new News();
			List<com.focusx.entity.weixin.sendMessage.Article> artList = new ArrayList<com.focusx.entity.weixin.sendMessage.Article>();
			for (TMbNews oneNews : newsList) {
				com.focusx.entity.weixin.sendMessage.Article oneArt = new com.focusx.entity.weixin.sendMessage.Article();
				oneArt.setDescription(oneNews.getDescription());
				oneArt.setTitle(oneNews.getTitle());
				// html路径
				StringBuilder url = new StringBuilder();
				// 图片路径
				StringBuilder picUrl = new StringBuilder();

				url
						.append(
								"https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
						.append(Constant.APPID).append("&redirect_uri=");

				url.append(Constant.WEB_URL).append("weixin/").append(
						"readServlet?").append("newsId=").append(
						oneNews.getId());
				// 无法获取
				// .append("&newsType=").append(oneNews.getNewsType()).append(
				// "&htmlName=").append(oneNews.getHtmlName());

				url
						.append("&response_type=code&scope=snsapi_base&state=1#wechat_redirect");

				logger.info(url.toString());

				picUrl.append(Constant.NEWS_WEB_SITE);
				picUrl.append(Constant.NEWS_IMAGE_PATH);
				picUrl.append(oneNews.getCoverPage());

				oneArt.setUrl(url.toString());
				// logger.info("图文 url >>> " + url.toString());
				oneArt.setPicurl(picUrl.toString());
				artList.add(oneArt);
			}
			news.setArticles(artList);
			clientMsg.setNews(news);

			return clientMsg;
		}
		return null;
	}

	public static BaseMessage buildMediaMessage(String openId, String mediaId,
			String msgType) {

		BaseMessage mediaMsg = null;

		if (MessageUtil.RESP_MESSAGE_TYPE_IMAGE.equals(msgType)) {
			ImageMessage imgMsg = new ImageMessage();
			Image img = new Image();
			img.setMedia_id(mediaId);

			imgMsg.setImage(img);

			mediaMsg = imgMsg;

		} else if (MessageUtil.RESP_MESSAGE_TYPE_VOICE.equals(msgType)) {
			VoiceMessage voiceMsg = new VoiceMessage();
			Voice voice = new Voice();
			voice.setMedia_id(mediaId);

			voiceMsg.setVoice(voice);

		} else if (MessageUtil.RESP_MESSAGE_TYPE_VIDEO.equals(msgType)) {
			VideoMessage videoMsg = new VideoMessage();
			Video video = new Video();
			video.setMedia_id(mediaId);

			videoMsg.setVideo(video);

			mediaMsg = videoMsg;
		}

		if (mediaMsg != null) {
			mediaMsg.setMsgtype(msgType);
			mediaMsg.setTouser(openId);
		}

		return mediaMsg;
	}

}
