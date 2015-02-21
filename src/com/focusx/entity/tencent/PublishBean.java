package com.focusx.entity.tencent;

import java.util.Date;

import com.google.gson.annotations.Expose;

/** 
 * com.focusx.entity.tencent
 * PublishBean.java 
 * author:vincente  2013-10-24 
 * 腾讯微博发布微博对象
 * 包含账户ID 发布微博ID 发布时间等参数
 */
public class PublishBean {
	
	private String accountId;//账户ID
	@Expose
	private String accountName;//账户名
	private String weiboId;//发布成功后的微博Id
	private String content;//微博内容
	private String picUrl;//图片url
	private String videoUrl;//视频url
	private Date publishDate;//发布日期
	@Expose
	private String ret = "";//返回值，0-成功，非0-失败,
	@Expose
	private String errcode = "";//返回错误码
	@Expose
	private String msg = "";//错误信息,
	@Expose
	private String message = "";//发送是否成功的提示消息   要在jsp页面中显示的
	
	public String getAccountId() {
		return accountId;
	}
	public String getWeiboId() {
		return weiboId;
	}
	public String getContent() {
		return content;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getAccountName() {
		return accountName;
	}
	public String getRet() {
		return ret;
	}
	public String getErrcode() {
		return errcode;
	}
	public String getMsg() {
		return msg;
	}
	public String getMessage() {
		return message;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public void setRet(String ret) {
		this.ret = ret;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
