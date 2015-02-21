package com.focusx.entity;

/**
 * TUserPower entity. @author MyEclipse Persistence Tools
 */

public class TUserPower  implements java.io.Serializable {
    
	// Fields    
     private Integer id;
     private Integer subSystemType;
     private Integer value;
     private String name;
     private Integer isSystem;
     private String groupName;
     
 	//权限所属角色
 	private Integer roleOwerId;


    // Constructors

    /** default constructor */
    public TUserPower() {
    }

    
    /** full constructor */
    public TUserPower(Integer subSystemType, Integer value, String name, Integer isSystem, String groupName) {
        this.subSystemType = subSystemType;
        this.value = value;
        this.name = name;
        this.isSystem = isSystem;
        this.groupName = groupName;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubSystemType() {
        return this.subSystemType;
    }
    
    public void setSubSystemType(Integer subSystemType) {
        this.subSystemType = subSystemType;
    }

    public Integer getValue() {
        return this.value;
    }
    
    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsSystem() {
        return this.isSystem;
    }
    
    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
    }

    public String getGroupName() {
        return this.groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

	public Integer getRoleOwerId() {
		return roleOwerId;
	}

	public void setRoleOwerId(Integer roleOwerId) {
		this.roleOwerId = roleOwerId;
	}
   
}