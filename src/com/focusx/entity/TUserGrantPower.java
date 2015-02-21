package com.focusx.entity;

public class TUserGrantPower implements java.io.Serializable {

    // Fields    
    private Integer id;
    private Integer ownerType;
    private Integer ownerId;
    private Integer powerId;
    private Short isOwn;
    private String description;

    // Constructors

    /** default constructor */
    public TUserGrantPower() {
    }

	/** minimal constructor */
    public TUserGrantPower(Integer id, Integer ownerType, Integer ownerId, Integer powerId,
    		Short isOwn, String description) {
    	this.id = id;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.powerId = powerId;
        this.isOwn = isOwn;
        this.description = description;
    }
    
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerType() {
        return this.ownerType;
    }
    
    public void setOwnerType(Integer ownerType) {
        this.ownerType = ownerType;
    }

    public Integer getOwnerId() {
        return this.ownerId;
    }
    
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getPowerId() {
        return this.powerId;
    }
    
    public void setPowerId(Integer powerId) {
        this.powerId = powerId;
    }

    public Short getIsOwn() {
        return this.isOwn;
    }
    
    public void setIsOwn(Short isOwn) {
        this.isOwn = isOwn;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}