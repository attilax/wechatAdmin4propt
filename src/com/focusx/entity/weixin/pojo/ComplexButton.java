package com.focusx.entity.weixin.pojo;

/** 
 * 复杂按钮（父按钮）  父菜单项
 * 这类菜单项包含有二个属性：name和sub_button，而sub_button以是一个子菜单项数组。
 * author:vincente  2013-11-5 
 */
public class ComplexButton extends Button{
	
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}  
}
