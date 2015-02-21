package com.focusx.entity;

import java.util.Date;

/**
 * TMbContact entity. @author MyEclipse Persistence Tools
 */
public class TMbContact implements java.io.Serializable {

	// Fields

	private Integer contactId;
	private String contactName; // 姓名
	private String title;// 职称
	private String departMent;// 部门
	private String officePhone;// 办公电话
	private String mobile;
	private String fax;
	private String clientName;
	private String masterAddress;
	private String otherAddress;
	private String email;
	private String city;
	private String province;
	private String zip;
	private String country;
	private String wbAccount;
	private String url;
	private Integer statusesCount;
	private Integer friendsCount;
	private Integer followersCount;
	private Byte verified;
	private Integer sourceType;
	private String headAddress;
	private Date createTime;
	private Date updateTime;
	private String manager;
	private String description;
	private String openid;

	// Constructors

	/** default constructor */
	public TMbContact() {
	}

	/** full constructor */
	public TMbContact(String contactName, String title, String departMent, String officePhone, String mobile, String fax,
			String clientName, String masterAddress, String otherAddress, String email, String city, String province, String zip,
			String country, String wbAccount, String url, Integer statusesCount, Integer friendsCount, Integer followersCount,
			Byte verified, Integer sourceType, String headAddress, Date createTime, Date updateTime, String manager, String description) {
		this.contactName = contactName;
		this.title = title;
		this.departMent = departMent;
		this.officePhone = officePhone;
		this.mobile = mobile;
		this.fax = fax;
		this.clientName = clientName;
		this.masterAddress = masterAddress;
		this.otherAddress = otherAddress;
		this.email = email;
		this.city = city;
		this.province = province;
		this.zip = zip;
		this.country = country;
		this.wbAccount = wbAccount;
		this.url = url;
		this.statusesCount = statusesCount;
		this.friendsCount = friendsCount;
		this.followersCount = followersCount;
		this.verified = verified;
		this.sourceType = sourceType;
		this.headAddress = headAddress;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.manager = manager;
		this.description = description;
	}

	// Property accessors

	public Integer getContactId() {
		return this.contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDepartMent() {
		return this.departMent;
	}

	public void setDepartMent(String departMent) {
		this.departMent = departMent;
	}

	public String getOfficePhone() {
		return this.officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getMasterAddress() {
		return this.masterAddress;
	}

	public void setMasterAddress(String masterAddress) {
		this.masterAddress = masterAddress;
	}

	public String getOtherAddress() {
		return this.otherAddress;
	}

	public void setOtherAddress(String otherAddress) {
		this.otherAddress = otherAddress;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getWbAccount() {
		return this.wbAccount;
	}

	public void setWbAccount(String wbAccount) {
		this.wbAccount = wbAccount;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getStatusesCount() {
		return this.statusesCount;
	}

	public void setStatusesCount(Integer statusesCount) {
		this.statusesCount = statusesCount;
	}

	public Integer getFriendsCount() {
		return this.friendsCount;
	}

	public void setFriendsCount(Integer friendsCount) {
		this.friendsCount = friendsCount;
	}

	public Integer getFollowersCount() {
		return this.followersCount;
	}

	public void setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
	}

	public Byte getVerified() {
		return this.verified;
	}

	public void setVerified(Byte verified) {
		this.verified = verified;
	}

	public Integer getSourceType() {
		return this.sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getHeadAddress() {
		return this.headAddress;
	}

	public void setHeadAddress(String headAddress) {
		this.headAddress = headAddress;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

}