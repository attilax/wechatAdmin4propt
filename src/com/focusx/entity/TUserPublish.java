package com.focusx.entity;


/**
 * TUserPublish entity. @author MyEclipse Persistence Tools
 */

public class TUserPublish  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String publishName;


    // Constructors

    /** default constructor */
    public TUserPublish() {
    }

    
    /** full constructor */
    public TUserPublish(String publishName) {
        this.publishName = publishName;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getPublishName() {
        return this.publishName;
    }
    
    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }
   








}