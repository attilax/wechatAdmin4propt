package com.focusx.entity.dataAnalysis;

import java.util.List;

/**
 *  粉丝信息分析实体
 */
public class WeixinUser {
	
	private Integer newsubscribe;   //新关注人数
	private Integer cancelsubscribe;//取消关注人数
	private Integer netsubscribe;   //净增关注人数
	private Integer allsubscribe;   //累积关注人数
	private List<Double> newsscale; //新关注人数的日、周、月概率
	private List<Double> cancelscale; //取消关注人数的日、周、月概率
	private List<Double> netscale; //净增关注人数的日、周、月概率
	private List<Double> allscale; //累积关注人数的日、周、月概率
	private String time;           //时间
	
	public WeixinUser(){}
	
	public WeixinUser(Integer newsubscribe, Integer cancelsubscribe, Integer netsubscribe, Integer allsubscribe
			, List<Double> newsscale, List<Double> cancelscale, List<Double> netscale, List<Double> allscale, String time){
		this.newsubscribe = newsubscribe;
		this.netsubscribe = netsubscribe;
		this.cancelsubscribe = cancelsubscribe;
		this.allsubscribe = allsubscribe;
		this.newsscale = newsscale;
		this.cancelscale = cancelscale;
		this.netscale = netscale;
		this.allscale = allscale;
		this.time = time;
	}

	public Integer getNewsubscribe() {
		return newsubscribe;
	}

	public void setNewsubscribe(Integer newsubscribe) {
		this.newsubscribe = newsubscribe;
	}

	public Integer getCancelsubscribe() {
		return cancelsubscribe;
	}

	public void setCancelsubscribe(Integer cancelsubscribe) {
		this.cancelsubscribe = cancelsubscribe;
	}

	public Integer getNetsubscribe() {
		return netsubscribe;
	}

	public void setNetsubscribe(Integer netsubscribe) {
		this.netsubscribe = netsubscribe;
	}

	public Integer getAllsubscribe() {
		return allsubscribe;
	}

	public void setAllsubscribe(Integer allsubscribe) {
		this.allsubscribe = allsubscribe;
	}

	public List<Double> getNewsscale() {
		return newsscale;
	}

	public void setNewsscale(List<Double> newsscale) {
		this.newsscale = newsscale;
	}

	public List<Double> getCancelscale() {
		return cancelscale;
	}

	public void setCancelscale(List<Double> cancelscale) {
		this.cancelscale = cancelscale;
	}

	public List<Double> getNetscale() {
		return netscale;
	}

	public void setNetscale(List<Double> netscale) {
		this.netscale = netscale;
	}

	public List<Double> getAllscale() {
		return allscale;
	}

	public void setAllscale(List<Double> allscale) {
		this.allscale = allscale;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
