package com.focusx.entity.dataAnalysis;

import java.util.List;

/**
 *  图文信息分析实体
 */
public class News {
	
	private Integer deliveryTime;       //送达次数
	private Integer deliveryWeixin;     //送达人数
	private Integer readingTime;        //阅读次数
	private Integer readingWeixin;      //阅读人数
	private Integer readingCount;       //阅读总人数
	private Double conversionRate;      //图文转化率
	private List<Double> deliveryscale; //图文送达次数的日、周、月概率
	private List<Double> readingscale;  //阅读次数的日、周、月概率
	private List<Double> readingWeixinscale;     //阅读人数的日、周、月概率
	private List<Double> deliveryWeixinscale;    //图文送达人数的日、周、月概率
	private List<Double> conversionRatescale;    //图文转化率的日、周、月概率
	private String time;                //时间
	
	public News(){}
	
	public News(Integer deliveryTime, Integer readingTime, Integer shareTime, Integer deliveryWeixin, Double conversionRate,
			List<Double> deliveryscale, List<Double> readingscale, List<Double> sharescale, String time, Integer readingWeixin,
			List<Double> conversionRatescale, List<Double> deliveryWeixinscale, Integer readingCount, List<Double> readingWeixinscale){
		this.deliveryTime = deliveryTime;
		this.readingTime = readingTime;
		this.deliveryscale = deliveryscale;
		this.readingscale = readingscale;
		this.time = time;
		this.readingWeixin = readingWeixin;
		this.deliveryWeixin = deliveryWeixin;
		this.conversionRate = conversionRate;
		this.conversionRatescale = conversionRatescale;
		this.deliveryWeixinscale = deliveryWeixinscale;
		this.readingCount = readingCount;
		this.readingWeixinscale = readingWeixinscale;
	}

	public Integer getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Integer deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getReadingTime() {
		return readingTime;
	}

	public void setReadingTime(Integer readingTime) {
		this.readingTime = readingTime;
	}

	public List<Double> getDeliveryscale() {
		return deliveryscale;
	}

	public void setDeliveryscale(List<Double> deliveryscale) {
		this.deliveryscale = deliveryscale;
	}

	public List<Double> getReadingscale() {
		return readingscale;
	}

	public void setReadingscale(List<Double> readingscale) {
		this.readingscale = readingscale;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getReadingWeixin() {
		return readingWeixin;
	}

	public void setReadingWeixin(Integer readingWeixin) {
		this.readingWeixin = readingWeixin;
	}

	public Integer getDeliveryWeixin() {
		return deliveryWeixin;
	}

	public void setDeliveryWeixin(Integer deliveryWeixin) {
		this.deliveryWeixin = deliveryWeixin;
	}

	public List<Double> getConversionRatescale() {
		return conversionRatescale;
	}

	public void setConversionRatescale(List<Double> conversionRatescale) {
		this.conversionRatescale = conversionRatescale;
	}

	public List<Double> getDeliveryWeixinscale() {
		return deliveryWeixinscale;
	}

	public void setDeliveryWeixinscale(List<Double> deliveryWeixinscale) {
		this.deliveryWeixinscale = deliveryWeixinscale;
	}

	public Integer getReadingCount() {
		return readingCount;
	}

	public void setReadingCount(Integer readingCount) {
		this.readingCount = readingCount;
	}

	public List<Double> getReadingWeixinscale() {
		return readingWeixinscale;
	}

	public void setReadingWeixinscale(List<Double> readingWeixinscale) {
		this.readingWeixinscale = readingWeixinscale;
	}

	public Double getConversionRate() {
		return conversionRate;
	}

	public void setConversionRate(Double conversionRate) {
		this.conversionRate = conversionRate;
	}
	
}
