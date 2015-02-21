package com.focusx.entity.dataAnalysis;

import java.util.List;

/**
 *  板块点击对象
 */
public class Menu {
	
	private Integer menuWeixin;   //微信粉丝点击数
	private Integer menuMember;   //会员绑定后点击数
	private Integer menuSum;      //微信粉丝点击总数
	private List<Double> menuWeixinScale;  //微信粉丝点击数的日、周、月概率
	private List<Double> menuMemberScale; //会员绑定后点击的日、周、月概率
	private String time;             //时间
	
	public Menu(){}
	
	public Menu(Integer menuWeixin, Integer menuMember, Integer menuSum, List<Double> menuWeixinScale, 
			List<Double> menuMemberScale, String time){
		this.menuSum = menuSum;
		this.menuMember = menuMember;
		this.menuWeixin = menuWeixin;
		this.menuMemberScale = menuMemberScale;
		this.menuWeixinScale = menuWeixinScale;
	}

	public Integer getMenuWeixin() {
		return menuWeixin;
	}

	public void setMenuWeixin(Integer menuWeixin) {
		this.menuWeixin = menuWeixin;
	}

	public Integer getMenuMember() {
		return menuMember;
	}

	public void setMenuMember(Integer menuMember) {
		this.menuMember = menuMember;
	}

	public Integer getMenuSum() {
		return menuSum;
	}

	public void setMenuSum(Integer menuSum) {
		this.menuSum = menuSum;
	}

	public List<Double> getMenuWeixinScale() {
		return menuWeixinScale;
	}

	public void setMenuWeixinScale(List<Double> menuWeixinScale) {
		this.menuWeixinScale = menuWeixinScale;
	}

	public List<Double> getMenuMemberScale() {
		return menuMemberScale;
	}

	public void setMenuMemberScale(List<Double> menuMemberScale) {
		this.menuMemberScale = menuMemberScale;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	

}
