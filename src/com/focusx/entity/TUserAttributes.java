package com.focusx.entity;


public class TUserAttributes  implements java.io.Serializable {
	
	private Integer id;
	private Integer clientReleaseMode;
	private Integer agentReleaseMode;
	private Integer autoAnswerCall;
	private Integer isCheckEstNum;
	private Integer notPlayWorkNo;
	
	public TUserAttributes(){}
	
	public TUserAttributes(Integer id, Integer clientReleaseMode, Integer agentReleaseMode, 
			Integer autoAnswerCall, Integer isCheckEstNum, Integer notPlayWorkNo){
		this.id = id;
		this.clientReleaseMode = clientReleaseMode;
		this.agentReleaseMode = agentReleaseMode;
		this.autoAnswerCall = autoAnswerCall;
		this.isCheckEstNum = isCheckEstNum;
		this.notPlayWorkNo = notPlayWorkNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientReleaseMode() {
		return clientReleaseMode;
	}

	public void setClientReleaseMode(Integer clientReleaseMode) {
		this.clientReleaseMode = clientReleaseMode;
	}

	public Integer getAgentReleaseMode() {
		return agentReleaseMode;
	}

	public void setAgentReleaseMode(Integer agentReleaseMode) {
		this.agentReleaseMode = agentReleaseMode;
	}

	public Integer getAutoAnswerCall() {
		return autoAnswerCall;
	}

	public void setAutoAnswerCall(Integer autoAnswerCall) {
		this.autoAnswerCall = autoAnswerCall;
	}

	public Integer getIsCheckEstNum() {
		return isCheckEstNum;
	}

	public void setIsCheckEstNum(Integer isCheckEstNum) {
		this.isCheckEstNum = isCheckEstNum;
	}

	public Integer getNotPlayWorkNo() {
		return notPlayWorkNo;
	}

	public void setNotPlayWorkNo(Integer notPlayWorkNo) {
		this.notPlayWorkNo = notPlayWorkNo;
	}

}