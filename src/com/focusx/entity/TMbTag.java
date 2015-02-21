package com.focusx.entity;

import java.io.Serializable;

public class TMbTag implements Serializable {
	
	private Integer id;
	private Integer groupid;
	private String tagName;
	private Integer tagType;
	
	public TMbTag(){}
	
	public TMbTag(Integer id, Integer groupid, String tagName, Integer tagType){
		this.id = id;
		this.groupid = groupid;
		this.tagName = tagName;
		this.tagType = tagType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Integer getTagType() {
		return tagType;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}
}
