package com.focusx.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author 张春雨
 * @模块 存放在线用户信息
 * @日期 2013-12-3 时间：下午04:34:54
 */
public class OnLineUser {
	private String sessionId;
	private Integer userId;// 用户ID
	private String userNo;// 用户编号
	private String userName;// 用户名称
	private Integer state;// 用户登录状态 0登录未签入 1空闲 2处理中 3离线 4繁忙
	private String loginTime;// 登录时间
	private String stateName;// 状态名称

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.loginTime = df.format(new Date());
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStateName() {
		if (this.state != null) {
			switch (state.intValue()) {
			case 0:
				this.stateName = "登录未签入";
				break;
			case 1:
				this.stateName = "空闲";
				break;
			case 2:
				this.stateName = "处理中";
				break;
			case 3:
				this.stateName = "离线";
				break;
			case 4:
				this.stateName = "繁忙";
				break;
			}
		}
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
