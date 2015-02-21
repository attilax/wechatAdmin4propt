package com.focusx.entity;
// default package



/**
 * TUserUsertoRole entity. @author MyEclipse Persistence Tools
 */

public class TUserUsertoRole  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Integer userId;
     private Integer roleId;
     private String description;


    // Constructors

    /** default constructor */
    public TUserUsertoRole() {
    }

	/** minimal constructor */
    public TUserUsertoRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
    
    /** full constructor */
    public TUserUsertoRole(Integer userId, Integer roleId, String description) {
        this.userId = userId;
        this.roleId = roleId;
        this.description = description;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
   








}