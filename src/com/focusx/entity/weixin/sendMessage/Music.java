package com.focusx.entity.weixin.sendMessage;

/** 
 * com.focusx.entity.weixin.sendMessage
 * Music.java 
 * author:vincente  2013-11-18 
 */
public class Music {
	
	private String title;//音乐标题
	
	private String description;//音乐描述
	
	private String musicurl;//音乐链接
	
	private String hqmusicurl;//高品质音乐链接，wifi环境优先使用该链接播放音乐
	
	private String thumb_media_id;//视频缩略图的媒体ID

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getMusicurl() {
		return musicurl;
	}

	public String getHqmusicurl() {
		return hqmusicurl;
	}

	public String getThumb_media_id() {
		return thumb_media_id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMusicurl(String musicurl) {
		this.musicurl = musicurl;
	}

	public void setHqmusicurl(String hqmusicurl) {
		this.hqmusicurl = hqmusicurl;
	}

	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}
	
}
