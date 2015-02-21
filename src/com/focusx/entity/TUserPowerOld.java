package com.focusx.entity;

public class TUserPowerOld  implements java.io.Serializable {
	
	// Fields    
    private Integer id;
    private Short subSystemType;
    private Integer value;
    private String name;
    private Short isSystem;
    private String groupName;


    // Constructors

    /** default constructor */
    public TUserPowerOld() {
    }

	/** minimal constructor */
    public TUserPowerOld(Integer id, Short subSystemType, Integer value, String name, Short isSystem, String groupName) {
        this.id = id;
        this.subSystemType = subSystemType;
        this.value = value;
        this.name = name;
        this.isSystem = isSystem;
        this.groupName = groupName;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Short getIsSystem() {
        return this.isSystem;
    }
    
    public void setIsSystem(Short isSystem) {
        this.isSystem = isSystem;
    }

    public String getGroupName() {
        return this.groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Short getSubSystemType() {
		return subSystemType;
	}

	public void setSubSystemType(Short subSystemType) {
		this.subSystemType = subSystemType;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}