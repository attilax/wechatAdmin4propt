package com.focusx.entity.weixin.pojo;

/** 
 * 普通按钮（子按钮）子菜单
 * 这里对子菜单是这样定义的：没有子菜单的菜单项，有可能是二级菜单项，也有可能是不含二级菜单的一级菜单
 * author:vincente  2013-11-5 
 */
public class CommonButton extends Button{
	private String type;  //菜单的响应动作类型，目前有click、view两种类型
	private String key;	  //菜单KEY值，用于消息接口推送，不超过128字节
	 
	public String getType() {
		return type;
	}
	public String getKey() {
		return key;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setKey(String key) {
		this.key = key;
	}  
}
