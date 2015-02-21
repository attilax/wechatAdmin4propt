package com.focusx.entity;

/**
 * TMbGroupToContact entity. @author MyEclipse Persistence Tools
 */

public class TMbGroupToContact  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Integer groupid;
     private Integer contactid;


    // Constructors

    /** default constructor */
    public TMbGroupToContact() {
    }

    
    /** full constructor */
    public TMbGroupToContact(Integer groupid, Integer contactid) {
        this.groupid = groupid;
        this.contactid = contactid;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupid() {
        return this.groupid;
    }
    
    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getContactid() {
        return this.contactid;
    }
    
    public void setContactid(Integer contactid) {
        this.contactid = contactid;
    }
   








}