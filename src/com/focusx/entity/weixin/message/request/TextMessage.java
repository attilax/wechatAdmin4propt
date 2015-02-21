package com.focusx.entity.weixin.message.request;

/** 
 * 文本消息
 * TextMessage.java 
 * author:vincente  2013-11-1 
 */
public class TextMessage extends BaseMessage{
	// 消息内容
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}  
}
