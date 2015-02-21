package com.focusx.entity;

import java.util.Date;

public class TMbEventHistory implements java.io.Serializable { 
	
	private Integer id;       //主键ID
    private String eventKey;  //自定义菜单key
    private String openid;    //微信粉丝唯一标识
    private String memberId;  //会员ID
    private Date clickTime;   //点击时间

    public TMbEventHistory() {}

    public TMbEventHistory(Integer id, String eventKey, String openid, String memberId, Date clickTime) {
    	this.id = id;
    	this.eventKey = eventKey;
    	this.openid = openid;
    	this.memberId = memberId;
    	this.clickTime = clickTime;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getClickTime() {
		return clickTime;
	}

	public void setClickTime(Date clickTime) {
		this.clickTime = clickTime;
	}
	
}