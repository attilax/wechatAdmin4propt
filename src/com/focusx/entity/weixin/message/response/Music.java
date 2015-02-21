package com.focusx.entity.weixin.message.response;

/** 
 * 音乐model
 * Music.java 
 * author:vincente  2013-11-1 
 */
public class Music extends BaseMessage{
	// 音乐名称
	private String title;  
	// 音乐描述
	private String description;  
	// 音乐链接
	private String musicUrl;  
	// 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	private String hqMusicUrl;
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public String getMusicUrl() {
		return musicUrl;
	}
	public String getHqMusicUrl() {
		return hqMusicUrl;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}
	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}  
}
