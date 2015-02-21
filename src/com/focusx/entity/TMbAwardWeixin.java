package com.focusx.entity;

import java.io.Serializable;
import java.util.Date;

public class TMbAwardWeixin implements Serializable{
	
	private Integer id;         //主键ID
	private Integer activityId; //所属活动ID
	private Integer awardId;    //奖品ID
	private String openId;		//微信粉丝唯一标示
	private Date awardTime;     //中奖时间
	private Integer awardCount;  //剩余抽奖次数
	private Date createTime;   //记录生成时间

	public TMbAwardWeixin(){}
	
	public TMbAwardWeixin(Integer id, Integer activityId, Integer awardId, String openId, 
			Date awardTime, Integer awardCount, Date createTime){
		this.id = id;
		this.activityId = activityId;
		this.awardId = awardId;
		this.openId = openId;
		this.awardTime = awardTime;
		this.awardCount = awardCount;
		this.createTime = createTime;
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

	public Integer getAwardId() {
		return awardId;
	}

	public void setAwardId(Integer awardId) {
		this.awardId = awardId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getAwardTime() {
		return awardTime;
	}

	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}

	public Integer getAwardCount() {
		return awardCount;
	}

	public void setAwardCount(Integer awardCount) {
		this.awardCount = awardCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
