package com.focusx.entity.weixin.sendMessage;

/** 
 * 发送语音消息
 * com.focusx.entity.weixin.sendMessage
 * VoiceMessage.java 
 * author:vincente  2013-11-18 
 */
public class VoiceMessage extends BaseMessage {
	
	private Voice voice;

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}
	

}
