package com.focusx.entity.weixin.message.request;

/** 
 * 链接消息
 * LinkMessage.java 
 * author:vincente  2013-11-1 
 */
public class LinkMessage extends BaseMessage{
	// 消息标题
	private String title;  
	// 消息描述
	private String description;  
	// 消息链接
	private String url;
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public String getUrl() {
		return url;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
