package com.focusx.entity.weixin.message.response;

/** 
 * 回复图片消息
 * author:vincente  2013-11-5 
 */
public class ImageMessage extends BaseMessage{
	
	//通过上传多媒体文件，得到的id
	private String mediaId;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
}
