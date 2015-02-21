package com.focusx.entity.weixin.sendMessage;

/** 
 * 发送文本消息
 * com.focusx.entity.weixin.sendMessage
 * TextMessage.java 
 * author:vincente  2013-11-18 
 */
public class TextMessage extends BaseMessage{
	//@SerializedName("bir")  
	public Text text;

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
}
