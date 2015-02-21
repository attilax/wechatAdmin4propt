package com.focusx.entity;

import java.io.Serializable;

public class TMbListen implements Serializable{
	
	private Integer listenId;   //主键ID
	private String listenName; //监听名称
	private String listenKey;	//监听关键字或者竞争对手
	private String description; //描述

	public TMbListen(){}
	
	public TMbListen(Integer listenId, String listenName, String listenKey, String description){
		this.listenId = listenId;
		this.listenName = listenName;
		this.listenKey = listenKey;
		this.description = description;
	}

	public Integer getListenId() {
		return listenId;
	}

	public void setListenId(Integer listenId) {
		this.listenId = listenId;
	}

	public String getListenName() {
		return listenName;
	}

	public void setListenName(String listenName) {
		this.listenName = listenName;
	}

	public String getListenKey() {
		return listenKey;
	}

	public void setListenKey(String listenKey) {
		this.listenKey = listenKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
