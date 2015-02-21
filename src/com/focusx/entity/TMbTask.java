package com.focusx.entity;

import java.io.Serializable;
import java.util.Date;

public class TMbTask implements Serializable{
	
	public final static int STATE_UNREAD = 0;
	public final static int STATE_READ = 1;
	public final static int STATE_FEEDBACK = 2;
	
	public final static int MSGTYPE_TEXT = 0;
	public final static int MSGTYPE_IMG = 1;
	public final static int MSGTYPE_VEDIO = 2;
	public final static int MSGTYPE_VOICE = 3;
	public final static int MSGTYPE_LOC = 4;
	public final static int MSGTYPE_LINK = 5;
	
	private Integer 	id;
	private String      openid;     //微信粉丝唯一标识
	private String 		msgContent; //消息内容
	private String 		mediaUrl;	//媒体地址
	private Date    	publishTime; //发送时间
	private Integer 	state;		//状态
	private Integer  	userId;		//操作用户id
	private Integer	 	msgType;	//消息类型
	private String nickname;        //微信粉丝呢称，表没这个字段，只是为了显示而已
	private Integer isLink;         //判断是否已发送抽奖链接，0 没有，>0 有
	private String publishTimeString; //字符串类型的发送时间
	
	private int messagetype = 0; //0客服消息  1回复消息
	
	//
	private Date        answerTime; //应答时间
	private String clientName;
	private String headimgurl;
	
	
	public TMbTask(){}
	
	public TMbTask(Integer id, String openid, String msgContent, String mediaUrl, Date publishTime, Integer	state
			,Integer userId, Integer msgTyp, String nickname){
		this.id = id;
		this.openid = openid;
		this.msgContent = msgContent;
		this.mediaUrl = mediaUrl;
		this.publishTime = publishTime;
		this.state = state;
		this.userId = userId;
		this.msgType = msgType;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getMediaUrl() {
		return mediaUrl;
	}
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getMsgType() {
		return msgType;
	}
	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getIsLink() {
		return isLink;
	}

	public void setIsLink(Integer isLink) {
		this.isLink = isLink;
	}

	public String getPublishTimeString() {
		if(this.publishTime != null){
			
		}
		return publishTimeString;
	}

	public void setPublishTimeString(String publishTimeString) {
		this.publishTimeString = publishTimeString;
	}

	public Date getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}

	public int getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(int messagetype) {
		this.messagetype = messagetype;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	
	
}
