package com.focusx.entity.weixin.sendMessage;

/** 
 * com.focusx.entity.weixin.sendMessage
 * Video.java 
 * author:vincente  2013-11-18 
 */
public class Video {
	
	private String media_id;//发送的视频的媒体ID
	
	private String thumb_media_id;//视频缩略图的媒体ID

	public String getMedia_id() {
		return media_id;
	}

	public String getThumb_media_id() {
		return thumb_media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}
}
