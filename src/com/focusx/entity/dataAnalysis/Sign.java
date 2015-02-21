package com.focusx.entity.dataAnalysis;

import java.util.List;

public class Sign {
	
	private Integer signscribe;   //会员签到数量
	private Integer signSum;     //会员签到总数量
	private List<Double> signScale;  //会员签到数量的日、周、月概率
	private String time;             //时间

	public Sign(){}
	public Sign(Integer signscribe, List<Double> signScale, String time, Integer signSum){
		this.signscribe = signscribe;
		this.signScale = signScale;
		this.time = time;
		this.signSum = signSum;
	}
	public Integer getSignscribe() {
		return signscribe;
	}
	public void setSignscribe(Integer signscribe) {
		this.signscribe = signscribe;
	}
	public List<Double> getSignScale() {
		return signScale;
	}
	public void setSignScale(List<Double> signScale) {
		this.signScale = signScale;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getSignSum() {
		return signSum;
	}
	public void setSignSum(Integer signSum) {
		this.signSum = signSum;
	}
	
}
