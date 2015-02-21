package com.focusx.entity;

import java.util.Date;

public class TMbNewsHistory implements java.io.Serializable { 
	
	private Integer id;//主键ID
    private Integer newsType;//图文类型
    private Integer newsId;//图文主键ID
    private String openid;//粉丝唯一标识
    private String memberId;//会员ID
    private Date operTime;//发送时间
    private Integer operType;//记录类型，1、阅读，2、发送，3、转发

    public TMbNewsHistory() {}

    public TMbNewsHistory(Integer id, Integer newsType, Integer newsId, String openid, 
    		String memberId, Date operTime, Integer operType) {
    	this.id = id;
    	this.newsType = newsType;
    	this.newsId = newsId;
    	this.openid = openid;
    	this.memberId = memberId;
    	this.operTime = operTime;
    	this.operType = operType;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNewsType() {
		return newsType;
	}

	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
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

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public Integer getOperType() {
		return operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}
	
}