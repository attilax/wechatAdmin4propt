package com.focusx.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author 张春雨
 * @模块 用户签入信息
 * @日期 2013-12-3 时间：上午09:11:39
 */
public class LoginUserInfo {

	private Integer userId;// 用户id
	private String userNo;// 用户编号
	private String userName;// 用户名称
	private Integer checkInType;// 1组 2技能
	private Integer checkInId;// 组或者技能的id
	private String checkInName;// 组或者技能的名称
	private String checkInTime;// 签入时间
	private String checkInTypeName;// 签入类型 1组 2技能

	public LoginUserInfo() {

	}

	public String getCheckInTypeName() {
		if (this.checkInType != null) {
			switch (this.checkInType.intValue()) {
			case 1:
				this.checkInTypeName = "组";
				break;
			case 2:
				this.checkInTypeName = "技能";
				break;
			}
		}
		return checkInTypeName;
	}

	public void setCheckInTypeName(String checkInTypeName) {
		this.checkInTypeName = checkInTypeName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCheckInType() {
		return checkInType;
	}

	public void setCheckInType(Integer checkInType) {
		this.checkInType = checkInType;
	}

	public Integer getCheckInId() {
		return checkInId;
	}

	public void setCheckInId(Integer checkInId) {
		this.checkInId = checkInId;
	}

	public String getCheckInName() {
		return checkInName;
	}

	public void setCheckInName(String checkInName) {
		this.checkInName = checkInName;
	}

	public String getCheckInTime() {

		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.checkInTime = df.format(new Date());
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
