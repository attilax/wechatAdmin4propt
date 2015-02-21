package com.focusx.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.focusx.util.ConfigService;
import com.focusx.util.Constant;

/**
 *   监听器，获取token，启用线程定时执行，初始化数据
 */
public class MyListener implements ServletContextListener {

	public static ScheduledExecutorService schedulePool = Executors.newScheduledThreadPool(2);

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	// 初始化程序，容器启动后自动执行
	public void contextInitialized(ServletContextEvent arg0) {
		//设置常量
		ConfigService config = new ConfigService();
		
		String appId = config.getWxProperty("APPID");
		String appSecret = config.getWxProperty("APPSECRET");
		
		//获取图文的设置
		String webSite 		 = config.getWxProperty("NEWS_WEBSITE");
		String newsImage 	 = config.getWxProperty("NEWS_IMAGE_PATH");
		String newsHtml 	 = config.getWxProperty("NEWS_HTML_PATH");
		
		String baseUrl       = config.getWxProperty("BASE_URL");
		
		Constant.APPID = appId;
		Constant.APPSECRET = appSecret;
		
		Constant.WEB_URL = baseUrl;
		
		//初始化读取图文相关设置
		Constant.NEWS_WEB_SITE = webSite;
		Constant.NEWS_HTML_PATH = newsHtml;
		Constant.NEWS_IMAGE_PATH = newsImage;
		
		// 启动获取微信授权码的定时器
		TokenThread tokenThread  = new TokenThread();
		// 初始化数据分析的某些对象
		InitdataAnalysisThread initdataAnalysisThread = new InitdataAnalysisThread();
		
		try{
			schedulePool.scheduleWithFixedDelay(tokenThread,30,3600,TimeUnit.SECONDS);//每一小时左右，从文件更新token，延时30秒
			schedulePool.schedule(initdataAnalysisThread,1,TimeUnit.SECONDS); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
