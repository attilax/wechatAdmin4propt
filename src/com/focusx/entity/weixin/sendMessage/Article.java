package com.focusx.entity.weixin.sendMessage;

/** 
 * com.focusx.entity.weixin.sendMessage
 * Articles.java 
 * author:vincente  2013-11-18 
 */
public class Article {

	private String title;//标题
	
	private String description;//描述
	
	private String url;//点击后跳转的链接
	
	private String picurl;//图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}

	public String getPicurl() {
		return picurl;
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

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	
}
