package com.focusx.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class TMbAwardWeixinResult implements Serializable{
	
	private Integer actawardId;  //奖品ID
	private Integer activityId; //活动ID
	private String awardName;	//奖品名称
	private Integer awardCount; //奖品数量
	private Integer rate;		//中将比率
	private Integer type;       //奖品类型，目前只有积分、实物、现金劵、优惠劵和会员卡几种
	private String awardRemark;      //奖品说明
	private String actName;	//活动名称
	private Date beginTime;//开始时间
	private Date endTime;  //结束时间
	private String activityRemark;   //活动说明
	private String ruleUrl;  //规则说明url
	private String awardTime;     //中奖时间
    private Integer userId;
    private Integer subscribe;
    private String openid;
    private Integer sex;
    private String city;
    private String country;
    private String province;
    private String language;
    private String headimgurl;
    private Timestamp subscribeTime;
    private String nickname;
    private Integer insertFlag;
    private Integer fromtype;//标记用户是主动如何关注，0搜索关注1扫描关注
    private Integer groupid;//分组ID
    
	/**
	 * @return the awardPhone
	 */
	public String getAwardPhone() {
		return awardPhone;
	}

	/**
	 * @param awardPhone the awardPhone to set
	 */
	public void setAwardPhone(String awardPhone) {
		this.awardPhone = awardPhone;
	}

	/**
	 * @return the awardUserName
	 */
	public String getAwardUserName() {
		return awardUserName;
	}

	/**
	 * @param awardUserName the awardUserName to set
	 */
	public void setAwardUserName(String awardUserName) {
		this.awardUserName = awardUserName;
	}

	/**
	 * @return the awardAddress
	 */
	public String getAwardAddress() {
		return awardAddress;
	}

	/**
	 * @param awardAddress the awardAddress to set
	 */
	public void setAwardAddress(String awardAddress) {
		this.awardAddress = awardAddress;
	}

	/**
	 * @return the memcardno
	 */
	public String getMemcardno() {
		return memcardno;
	}

	/**
	 * @param memcardno the memcardno to set
	 */
	public void setMemcardno(String memcardno) {
		this.memcardno = memcardno;
	}

	private String  	awardPhone; //中奖联系人手机号码
	private String  	awardUserName;// 中奖联系人姓名
	private String  	awardAddress; //中奖人地址
	private String  	memcardno; //中奖联系人手机号码
 
    
    public TMbAwardWeixinResult(){}
    
    public TMbAwardWeixinResult(Integer actawardId, Integer activityId, String awardName, Integer awardCount, Integer rate,
    		Integer type, String awardRemark, String actName, String awardTime, String nickname){
    	this.actawardId = actawardId;
    	this.activityId = activityId;
    	this.awardName = awardName;
    	this.awardCount = awardCount;
    	this.rate = rate;
    	this.type = type;
    	this.awardRemark = awardRemark;
    	this.actName = actName;
    	this.awardTime = awardTime;
    	this.nickname = nickname;
    }

	public Integer getActawardId() {
		return actawardId;
	}

	public void setActawardId(Integer actawardId) {
		this.actawardId = actawardId;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public Integer getAwardCount() {
		return awardCount;
	}

	public void setAwardCount(Integer awardCount) {
		this.awardCount = awardCount;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAwardRemark() {
		return awardRemark;
	}

	public void setAwardRemark(String awardRemark) {
		this.awardRemark = awardRemark;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getActivityRemark() {
		return activityRemark;
	}

	public void setActivityRemark(String activityRemark) {
		this.activityRemark = activityRemark;
	}

	public String getRuleUrl() {
		return ruleUrl;
	}

	public void setRuleUrl(String ruleUrl) {
		this.ruleUrl = ruleUrl;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Timestamp getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Timestamp subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getInsertFlag() {
		return insertFlag;
	}

	public void setInsertFlag(Integer insertFlag) {
		this.insertFlag = insertFlag;
	}

	public Integer getFromtype() {
		return fromtype;
	}

	public void setFromtype(Integer fromtype) {
		this.fromtype = fromtype;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getAwardTime() {
		return awardTime;
	}

	public void setAwardTime(String awardTime) {
		this.awardTime = awardTime;
	}

}