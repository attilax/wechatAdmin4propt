package com.focusx.util;

import java.io.Serializable;

public class Tree implements Serializable{
	
	private String text;//分组的名称
	private String isexpand;//是否展开，true 展开 false 不展开
	private String url;//请求地址
	private Object children;//下一级分组
	
	public Tree(){};
	
	public Tree(String text, String isexpand, String url ,Object children){
		this.text = text;
		this.isexpand = isexpand;
		this.url = url;
		this.children = children;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIsexpand() {
		return isexpand;
	}

	public void setIsexpand(String isexpand) {
		this.isexpand = isexpand;
	}

	public Object getChildren() {
		return children;
	}

	public void setChildren(Object children) {
		this.children = children;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
