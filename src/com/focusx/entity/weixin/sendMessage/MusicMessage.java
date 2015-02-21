package com.focusx.entity.weixin.sendMessage;

/** 
 * 发送音乐消息
 * com.focusx.entity.weixin.sendMessage
 * MusicMessage.java 
 * author:vincente  2013-11-18 
 */
public class MusicMessage extends BaseMessage{
	
	private Music music;

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}
	
}
