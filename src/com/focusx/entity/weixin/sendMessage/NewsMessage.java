package com.focusx.entity.weixin.sendMessage;

/** 
 * 发送图文消息
 * com.focusx.entity.weixin.sendMessage
 * NewsMessage.java 
 * author:vincente  2013-11-18 
 */
public class NewsMessage extends BaseMessage{
	
	private News news;

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}
	

}
