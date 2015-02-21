package com.focusx.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;


import org.apache.log4j.Logger;

import com.focusx.entity.weixin.pojo.AccessToken;
import com.focusx.util.Constant;

public class TokenThread extends Thread {
	private static Logger logger = Logger.getLogger(TokenThread.class);

	private CountDownLatch countDown;
	
	private final static int MAX_RETRY = 100;
	
	private int retryCount = 0;
	
	public TokenThread(){
	}
	
	public  void run() {
		try {
			loadTokenByFile();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(countDown != null){
				countDown.countDown();
			}
		}
	}
	
	
	public String loadTokenByFile(){
		
		logger.info("[微信授权码定时从文件获取]");
		++retryCount;
		StringBuilder builder = new StringBuilder();
		builder.append(Constant.path).append("token.properties");
		
		File f = new File(builder.toString());
		
		//文件存在
		if(f.exists()){
			try {
				FileInputStream fis = new FileInputStream(f);
				
				Properties prop = new Properties();
				prop.load(fis);
				
				String token = prop.getProperty("token");
				
				if(token != null && !"".equals(token)){
					logger.info("获取到的token是"+token);
					AccessToken fileToken = new AccessToken();
					fileToken.setToken(token);
					synchronized(this){
						Constant.token = fileToken;
					}
					return token;
				}else{
					logger.info("无法从文件 获取到授权或者授权串为空 ！休息一下再获取！");
					
					Thread.sleep(2000);
					
					if(this.retryCount <= MAX_RETRY){
					
						loadTokenByFile();
					
					}else{
						logger.info("达到最大重试数！休眠5分钟后，再重试");
						Thread.sleep(300000);
						this.retryCount = 0;
						loadTokenByFile();
					}
					return null;
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{//文件不存在
		
			logger.info("文件都不存在！，怎么办？？？");
		}
		return null;
		
		
	}
	
	public CountDownLatch getCountDown() {
		return countDown;
	}

	public void setCountDown(CountDownLatch countDown) {
		this.countDown = countDown;
	}

}
