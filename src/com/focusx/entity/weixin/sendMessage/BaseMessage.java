package com.focusx.entity.weixin.sendMessage;

/** 
 * 发送消息基类
 * com.focusx.entity.weixin.sendMessage
 * BaseMessage.java 
 * author:vincente  2013-11-18 
 */
public class BaseMessage {
	
	private String touser;//普通用户openid
	
	private String msgtype;//消息类型
	
	public String getTouser() {
		return touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
}
