package com.focusx.entity;

import java.io.Serializable;

public class TMbActAward implements Serializable{
	
	private Integer id;         //主键ID
	private Integer activityId; //所属活动ID
	private String awardName;	//奖品名称
	private Integer awardCount; //奖品数量
	private Integer rate;		//中将比率
	private Integer type;       //奖品类型，目前只有积分、实物、现金劵、优惠劵和会员卡几种
	private String remark;      //说明
	private String actName;     //所属活动名称

	public TMbActAward(){}
	
	public TMbActAward(Integer id, Integer activityId, String awardName, Integer awardCount, Integer rate, Integer type, String remark){
		this.id = id;
		this.activityId = activityId;
		this.awardCount = awardCount;
		this.rate = rate;
		this.type = type;
		this.remark = remark;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}
	
}
