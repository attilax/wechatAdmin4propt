package com.focusx.entity.weixin.message.request;

/** 
 * 音频消息
 * VoiceMessage.java 
 * author:vincente  2013-11-1 
 */
public class VoiceMessage extends BaseMessage{
	// 媒体ID
	private String mediaId;  
	// 语音格式
	private String format;
	
	public String getMediaId() {
		return mediaId;
	}
	public String getFormat() {
		return format;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public void setFormat(String format) {
		this.format = format;
	}  
}
