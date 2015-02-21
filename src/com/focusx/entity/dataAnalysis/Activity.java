package com.focusx.entity.dataAnalysis;

import java.util.List;

public class Activity {
	private Integer activityscribe;     //微信粉丝参与数
	private Integer readingscribe;      //阅读人数
	private Integer increasescribe;     //粉丝增长人数
	private Integer singlescribe;       //单次参与个数
	private Integer repeatscribe;       //重复扫描个数
	private Integer memberscribe;       //绑定会员参与数
	private Integer activitysscribe;     //微信粉丝参与总数
	
	private List<Double> activityScale;  //微信粉丝参与数的日、周、月概率
	private List<Double> readingScale;   //阅读人数的日、周、月概率
	private List<Double> increaseScale;  //粉丝增长的日、周、月概率
	private List<Double> memberScale;    //绑定会员参与数的日、周、月概率
 	private String time;                 //时间
	
	public Activity(){}
	
	public Activity(Integer activityscribe, Integer readingscribe, Integer increasescribe, List<Double> activityScale,
			List<Double> readingScale, List<Double> increaseScale, String time, Integer singlescribe, Integer repeatscribe, 
			Integer memberscribe, Integer activitysscribe, List<Double> memberScale){
		this.activityscribe = activityscribe;
		this.readingscribe = readingscribe;
		this.increasescribe = increasescribe;
		this.activityScale = activityScale;
		this.readingScale = readingScale;
		this.increaseScale = increaseScale;
		this.time = time;
		this.singlescribe = singlescribe;
		this.repeatscribe = repeatscribe;
		this.memberscribe = memberscribe;
		this.activitysscribe = activitysscribe;
		this.memberScale = memberScale;
	}

	public Integer getActivityscribe() {
		return activityscribe;
	}

	public void setActivityscribe(Integer activityscribe) {
		this.activityscribe = activityscribe;
	}

	public Integer getReadingscribe() {
		return readingscribe;
	}

	public void setReadingscribe(Integer readingscribe) {
		this.readingscribe = readingscribe;
	}

	public Integer getIncreasescribe() {
		return increasescribe;
	}

	public void setIncreasescribe(Integer increasescribe) {
		this.increasescribe = increasescribe;
	}

	public List<Double> getActivityScale() {
		return activityScale;
	}

	public void setActivityScale(List<Double> activityScale) {
		this.activityScale = activityScale;
	}

	public List<Double> getReadingScale() {
		return readingScale;
	}

	public void setReadingScale(List<Double> readingScale) {
		this.readingScale = readingScale;
	}

	public List<Double> getIncreaseScale() {
		return increaseScale;
	}

	public void setIncreaseScale(List<Double> increaseScale) {
		this.increaseScale = increaseScale;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getSinglescribe() {
		return singlescribe;
	}

	public void setSinglescribe(Integer singlescribe) {
		this.singlescribe = singlescribe;
	}

	public Integer getRepeatscribe() {
		return repeatscribe;
	}

	public void setRepeatscribe(Integer repeatscribe) {
		this.repeatscribe = repeatscribe;
	}

	public Integer getMemberscribe() {
		return memberscribe;
	}

	public void setMemberscribe(Integer memberscribe) {
		this.memberscribe = memberscribe;
	}

	public Integer getActivitysscribe() {
		return activitysscribe;
	}

	public void setActivitysscribe(Integer activitysscribe) {
		this.activitysscribe = activitysscribe;
	}

	public List<Double> getMemberScale() {
		return memberScale;
	}

	public void setMemberScale(List<Double> memberScale) {
		this.memberScale = memberScale;
	}

}
