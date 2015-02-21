package com.focusx.entity.weixin.message.response;

/** 
 * 音乐消息
 * MusicMessage.java 
 * author:vincente  2013-11-1 
 */
public class MusicMessage extends BaseMessage{
	// 音乐
	private Music music;

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}  
}
