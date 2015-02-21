package com.attilax.wechat;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.LogXF;
import org.apache.log4j.Logger;

import com.attilax.core;
import com.focusx.entity.weixin.pojo.AccessToken;
import com.focusx.util.ConfigService;
import com.focusx.util.Constant;
import com.focusx.util.WeixinUtil;

public class TokenThread extends Thread {
	private static Logger logger = Logger.getLogger(TokenThread.class);
	
	private CountDownLatch countDown;

	public static AccessToken accTok;
	public void run() {
		
		FileOutputStream fos = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			StringBuilder builder = new StringBuilder();
			builder.append(Constant.path);
			builder.append("token.properties");
			File tmpTokenFile = new File(builder.toString());
			if(!tmpTokenFile.exists()){
				tmpTokenFile.createNewFile();
			}

				Date systemDate = new Date();
				logger.info("---------------[微信授权码定时器]任务开始运行..." + sdf.format(systemDate) + "---------------");
				AccessToken accessToken = WeixinUtil.getAccessToken(Constant.APPID, Constant.APPSECRET);
				//o50
				accTok=accessToken;
				core.log("-- get acctok:"+core.toJsonStr( accTok));
				int i = 0;
				if (accessToken == null) {
					for (;;) { //直到获致成功
						++i;
						logger.info("微信接口[获取AccessToken]发生超时连接，重新连接微信服务器 第[" + i + "]次");
						accessToken = WeixinUtil.getAccessToken(Constant.APPID, Constant.APPSECRET);
						if (accessToken != null) {
							break;
						}
						Thread.sleep(5000); //每次请求休息1秒
					}
				}
				if (accessToken != null) {
					logger.info("---------------[微信授权码获取成功]--------");
					synchronized(this){//同步更新
						Constant.token = accessToken;
					}
					Properties prop = new Properties();
					prop.put("token", accessToken.getToken());
					fos = new FileOutputStream(tmpTokenFile);
					prop.store(fos,sdf.format(systemDate));
					fos.close();
				} else {
					logger.info("---------------[微信授权码获取失败]--------");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(fos != null){
					fos.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(this.countDown != null){
				this.countDown.countDown();
			}
		}
	}

	public void setCountDown(CountDownLatch countDown) {
		this.countDown = countDown;
	}
	
	

}
