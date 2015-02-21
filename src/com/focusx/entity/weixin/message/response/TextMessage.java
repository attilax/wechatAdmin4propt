package com.focusx.entity.weixin.message.response;

//import com.thoughtworks.xstream.annotations.XStreamAlias;


/** 
 * 文本消息
 * TextMessage.java 
 * author:vincente  2013-11-1 
 */
public class TextMessage extends BaseMessage{

	// 回复的消息内容
	//@XStreamAlias("message") 
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
