package com.focusx.entity.weixin.sendMessage;

/** 
 * 发送视频消息
 * com.focusx.entity.weixin.sendMessage
 * VideoMessage.java 
 * author:vincente  2013-11-18 
 */
public class VideoMessage extends BaseMessage {

	private Video video;

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}
}
