package com.focusx.entity.weixin.message.request;

/** 
 * 消息基类（普通用户 -> 公众帐号）
 * BaseMessage.java 
 * author:vincente  2013-11-1 
 */
public class BaseMessage {
	// 开发者微信号
	private String toUserName;  
	// 发送方帐号（一个OpenID）
	private String fromUserName;  
	// 消息创建时间 （整型）
	private long createTime;  
	// 消息类型（text/image/location/link）
	private String msgType;  
	// 消息id，64位整型
	private long msgId;
	
	public String getToUserName() {
		return toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public long getCreateTime() {
		return createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public long getMsgId() {
		return msgId;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}  
}
