package com.focusx.entity;

import java.io.Serializable;
import java.util.Date;

public class TMbActivity implements Serializable{
	
	private Integer id; //主键ID
	private String actName;	//活动名称
	private Date beginTime;//开始时间
	private Date endTime;  //结束时间
	
	private String actUrl; 
	private Integer ruleNewsId;
	private String ruleRemark;
	private Integer joinCount;
	private Integer joinType;
	private Integer flag;
	private Integer isCredit;
	private Integer credit;
	private Integer isOnlyMember;
	private Integer isShare;
	private String remark;
	private Integer type;//活动类型
	
	private String regur;
	private String awd;
	private String cash;
//	private Integer joincount;
//	/**
//	 * @return the joincount
//	 */
//	public Integer getJoincount() {
//		return joincount;
//	}
//
//	/**
//	 * @param joincount the joincount to set
//	 */
//	public void setJoincount(Integer joincount) {
//		this.joincount = joincount;
//	}

	/**
	 * @return the regur
	 */
	public String getRegur() {
		return regur;
	}

	/**
	 * @param regur the regur to set
	 */
	public void setRegur(String regur) {
		this.regur = regur;
	}

	/**
	 * @return the awd
	 */
	public String getAwd() {
		return awd;
	}

	/**
	 * @param awd the awd to set
	 */
	public void setAwd(String awd) {
		this.awd = awd;
	}

	/**
	 * @return the cash
	 */
	public String getCash() {
		return cash;
	}

	/**
	 * @param cash the cash to set
	 */
	public void setCash(String cash) {
		this.cash = cash;
	}

	public TMbActivity(){}
	
	public TMbActivity(Integer id, String actName, Date beginTime, Date endTime, String actUrl, Integer ruleNewsId
			, String ruleRemark, Integer joinCount, Integer joinType, Integer flag, Integer isCredit, Integer credit,
			Integer isOnlyMember, Integer isShare, Integer type, String remark){
		this.id = id;
		this.actName = actName;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.actUrl = actUrl;
		this.ruleNewsId = ruleNewsId;
		this.ruleRemark = ruleRemark;
		this.joinCount = joinCount;
		this.joinType = joinType;
		this.flag = flag;
		this.isCredit = isCredit;
		this.credit = credit;
		this.isOnlyMember = isOnlyMember;
		this.isShare = isShare;
		this.type = type;
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getActUrl() {
		return actUrl;
	}

	public void setActUrl(String actUrl) {
		this.actUrl = actUrl;
	}

	public Integer getRuleNewsId() {
		return ruleNewsId;
	}

	public void setRuleNewsId(Integer ruleNewsId) {
		this.ruleNewsId = ruleNewsId;
	}

	public String getRuleRemark() {
		return ruleRemark;
	}

	public void setRuleRemark(String ruleRemark) {
		this.ruleRemark = ruleRemark;
	}

	public Integer getJoinCount() {
		return joinCount;
	}

	public void setJoinCount(Integer joinCount) {
		this.joinCount = joinCount;
	}

	public Integer getJoinType() {
		return joinType;
	}

	public void setJoinType(Integer joinType) {
		this.joinType = joinType;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getIsCredit() {
		return isCredit;
	}

	public void setIsCredit(Integer isCredit) {
		this.isCredit = isCredit;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Integer getIsOnlyMember() {
		return isOnlyMember;
	}

	public void setIsOnlyMember(Integer isOnlyMember) {
		this.isOnlyMember = isOnlyMember;
	}

	public Integer getIsShare() {
		return isShare;
	}

	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
