package com.focusx.entity;

import java.io.Serializable;
import java.util.Date;

public class TMbSignHistory implements Serializable{
	
	private Integer id;   //主键ID
	private String openid; //微信唯一标识
	private String memberId; //会员ID
	private Date signDate; //签到日期

	public TMbSignHistory(){}
	
	public TMbSignHistory(Integer id, String openid, String memberId, Date signDate){
		this.id = id;
		this.openid = openid;
		this.memberId = memberId;
		this.signDate = signDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

}
