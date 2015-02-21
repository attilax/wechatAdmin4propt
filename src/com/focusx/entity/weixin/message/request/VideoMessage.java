package com.focusx.entity.weixin.message.request;

/** 
 * 视频消息
 * author:vincente  2013-11-5 
 */
public class VideoMessage extends BaseMessage{
	
	//视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
	private String mediaId;
	
	//视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
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
