package com.focusx.entity;

public class TUserUserToGroup  implements java.io.Serializable {

     // Fields    
     private Integer id;
     private Integer userID;
     private Integer groupID;
     private Integer autoCheckIn;
     private Integer ifMain;
     private Integer ifShow;

    // Constructors

    /** default constructor */
    public TUserUserToGroup() {
    }

    public TUserUserToGroup(Integer id, Integer userID, Integer groupID, 
    		Integer autoCheckIn, Integer ifMain, Integer ifShow){
    	this.id = id;
    	this.userID = userID;
    	this.groupID = groupID;
    	this.autoCheckIn = autoCheckIn;
    	this.ifMain = ifMain;
    	this.ifShow = ifShow;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getGroupID() {
		return groupID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public Integer getAutoCheckIn() {
		return autoCheckIn;
	}

	public void setAutoCheckIn(Integer autoCheckIn) {
		this.autoCheckIn = autoCheckIn;
	}

	public Integer getIfMain() {
		return ifMain;
	}

	public void setIfMain(Integer ifMain) {
		this.ifMain = ifMain;
	}

	public Integer getIfShow() {
		return ifShow;
	}

	public void setIfShow(Integer ifShow) {
		this.ifShow = ifShow;
	}
    
}