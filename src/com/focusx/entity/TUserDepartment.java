package com.focusx.entity;



/**
 * TUserDepartment entity. @author MyEclipse Persistence Tools
 */

public class TUserDepartment  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String departMentName;
     private String departMentTel;
     private String departMentFax;
     private String contact;
     private Long parentid;
     private String contactMobile;


    // Constructors

    /** default constructor */
    public TUserDepartment() {
    }

	/** minimal constructor */
    public TUserDepartment(Long parentid) {
        this.parentid = parentid;
    }
    
    /** full constructor */
    public TUserDepartment(String departMentName, String departMentTel, String departMentFax, String contact, Long parentid, String contactMobile) {
        this.departMentName = departMentName;
        this.departMentTel = departMentTel;
        this.departMentFax = departMentFax;
        this.contact = contact;
        this.parentid = parentid;
        this.contactMobile = contactMobile;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartMentName() {
        return this.departMentName;
    }
    
    public void setDepartMentName(String departMentName) {
        this.departMentName = departMentName;
    }

    public String getDepartMentTel() {
        return this.departMentTel;
    }
    
    public void setDepartMentTel(String departMentTel) {
        this.departMentTel = departMentTel;
    }

    public String getDepartMentFax() {
        return this.departMentFax;
    }
    
    public void setDepartMentFax(String departMentFax) {
        this.departMentFax = departMentFax;
    }

    public String getContact() {
        return this.contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }

    public Long getParentid() {
        return this.parentid;
    }
    
    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getContactMobile() {
        return this.contactMobile;
    }
    
    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }
   








}