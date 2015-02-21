package com.focusx.entity;

import java.io.Serializable;
import java.util.Date;

public class THdMember implements Serializable{
	
	private String memberId;    //
	private String cardNo;      //
	private String cellPhone;	//
	private String name;        //
	private Date birthday;		//
	private String address;     //
	private String email;       //
	private Float credit;       //
	private Date updateTime;
	private Date createTime; 
	
	private Date bindDate;

	public THdMember(){}
	
	public THdMember(String memberId, String cardNo, String cellPhone, String name, Date birthday, String address, String email,
			Float credit, Date updateTime, Date createTime){
		this.memberId = memberId;
		this.cardNo = cardNo;
		this.cellPhone = cellPhone;
		this.name = name;
		this.birthday = birthday;
		this.address = address;
		this.email = email;
		this.credit = credit;
		this.updateTime = updateTime;
		this.createTime = createTime;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Float getCredit() {
		return credit;
	}

	public void setCredit(Float credit) {
		this.credit = credit;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}

	

}
