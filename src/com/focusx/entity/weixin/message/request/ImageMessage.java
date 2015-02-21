package com.focusx.entity.weixin.message.request;

/** 
 * 图片消息
 * ImageMessage.java 
 * author:vincente  2013-11-1 
 */
public class ImageMessage extends BaseMessage{
	// 图片链接
	private String picUrl;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}  

}
