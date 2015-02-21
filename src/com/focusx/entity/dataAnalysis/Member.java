package com.focusx.entity.dataAnalysis;

import java.util.List;

/**
 *  会员绑定分析
 */
public class Member {
	
	private Integer memberBinding;   //会员绑定数量
	private Integer activeMember;    //活跃会员数量
	private String activeScale;      //活跃会员比例
	private Integer memberSum;       //会员绑定总数
	private List<Double> memberBindingScale;  //会员绑定数量的日、周、月概率
	private List<Double> activeMemberScale; //活跃会员数量的日、周、月概率
	private List<String> activeScales;       //日、周、月的活跃会员比例
	private String time;             //时间
	
	public Member(){}
	
	public Member(Integer memberBinding, Integer activeMember, String activeScale, List<Double> memberBindingScale
			, List<Double> activeMemberScale, String time, List<String> activeScales, Integer memberSum){
		this.memberBinding = memberBinding;
		this.activeMember = activeMember;
		this.activeScale = activeScale;
		this.memberBindingScale = memberBindingScale;
		this.activeMemberScale = activeMemberScale;
		this.time = time;
		this.activeMemberScale = activeMemberScale;
		this.memberSum = memberSum;
	}

	public Integer getMemberBinding() {
		return memberBinding;
	}

	public void setMemberBinding(Integer memberBinding) {
		this.memberBinding = memberBinding;
	}

	public Integer getActiveMember() {
		return activeMember;
	}

	public void setActiveMember(Integer activeMember) {
		this.activeMember = activeMember;
	}

	public String getActiveScale() {
		return activeScale;
	}

	public void setActiveScale(String activeScale) {
		this.activeScale = activeScale;
	}

	public List<Double> getMemberBindingScale() {
		return memberBindingScale;
	}

	public void setMemberBindingScale(List<Double> memberBindingScale) {
		this.memberBindingScale = memberBindingScale;
	}

	public List<Double> getActiveMemberScale() {
		return activeMemberScale;
	}

	public void setActiveMemberScale(List<Double> activeMemberScale) {
		this.activeMemberScale = activeMemberScale;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<String> getActiveScales() {
		return activeScales;
	}

	public void setActiveScales(List<String> activeScales) {
		this.activeScales = activeScales;
	}

	public Integer getMemberSum() {
		return memberSum;
	}

	public void setMemberSum(Integer memberSum) {
		this.memberSum = memberSum;
	}	

}
