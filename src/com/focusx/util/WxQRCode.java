package com.focusx.util;


public class WxQRCode{
	
	public final static String REQ_QR_TICKET = "https://api.weixin.qq.com/cgi-bin/qrcode/create?";

	public final static String REQ_QR_SHOW = "https://mp.weixin.qq.com/cgi-bin/showqrcode?";
	
	public final static int QR_TMP = 1; //请求临时二维码ticket
	public final static int QR_FOREVER = 2; //请求永久二维码ticket
	public final static int QR_SHOW = 3;  //请求二维码
	
	private String reqMethod;
	
	private transient int qrType;
	
	private  String ticket;
	private long expire_seconds;
	
	private transient String accessToken;
	
	public WxQRCode(){
		qrType = QR_SHOW;
		reqMethod = "GET";
	}
	
	public WxQRCode(int inQrType){
		this();
		qrType = inQrType;
	}
	
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public long getExpire_seconds() {
		return expire_seconds;
	}
	public void setExpire_seconds(long expire_seconds) {
		this.expire_seconds = expire_seconds;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		switch(qrType){
		case QR_TMP:
		case QR_FOREVER:
				builder.append(REQ_QR_TICKET);
				builder.append("access_token=");
				if(accessToken == null || "".equals(accessToken)) throw new RuntimeException("缺少accessToken参数");
				builder.append(accessToken);
				reqMethod = "POST";
			break;
		case QR_SHOW:
				builder.append(REQ_QR_SHOW);
				builder.append("ticket=");
				if(ticket == null || "".equals(ticket)) throw new RuntimeException("缺少ticket参数");
				builder.append(ticket);
				reqMethod = "GET";
			break;
		}
		return builder.toString();
	}

	public int getQrType() {
		return qrType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getReqMethod() {
		return reqMethod;
	}

	public void setReqMethod(String reqMethod) {
		this.reqMethod = reqMethod;
	}
	
	
}
