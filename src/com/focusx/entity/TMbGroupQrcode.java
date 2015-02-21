package com.focusx.entity;

public class TMbGroupQrcode {
	
	private int id;
	private int groupid;	  //所属分公司
	private int qrcode;		  //二维码参数
	
	/**
	 * @category 1） 9周年 2 ）彩妆大赛 3） 千店盛典  其它未确定
	 */
	private int codeType; 	  //二维码类型
	private String qrcodeUrl; //获取二维码的路径
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public int getQrcode() {
		return qrcode;
	}
	public void setQrcode(int qrcode) {
		this.qrcode = qrcode;
	}
	public int getCodeType() {
		return codeType;
	}
	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
	

}
