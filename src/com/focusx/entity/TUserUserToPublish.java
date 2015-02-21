package com.focusx.entity;


/**
 * TUserUserToPublish entity. @author MyEclipse Persistence Tools
 */

public class TUserUserToPublish  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Integer userId;
     private Integer publishId;


    // Constructors

    /** default constructor */
    public TUserUserToPublish() {
    }

    
    /** full constructor */
    public TUserUserToPublish(Integer userId, Integer publishId) {
        this.userId = userId;
        this.publishId = publishId;
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

    public Integer getPublishId() {
        return this.publishId;
    }
    
    public void setPublishId(Integer publishId) {
        this.publishId = publishId;
    }
   








}