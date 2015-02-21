package com.focusx.entity.weixin.message.response;

/** 
 * 回复视频消息
 * author:vincente  2013-11-5 
 */
public class VideoMessage extends BaseMessage{

	//通过上传多媒体文件，得到的id
	private String mediaId;
	
	//缩略图的媒体id，通过上传多媒体文件，得到的id
	private String thumbMediaId;
	
	public String getMediaId() {
		return mediaId;
	}
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
}
