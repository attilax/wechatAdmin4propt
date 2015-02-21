package com.focusx.entity;



/**
 * TUserUserToDepart entity. @author MyEclipse Persistence Tools
 */

public class TUserUserToDepart  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String name;
     private String mobile;
     private String zhiwu;
     private String qq;
     private String email;
     private Integer departid;


    // Constructors

    /** default constructor */
    public TUserUserToDepart() {
    }

    
    /** full constructor */
    public TUserUserToDepart(String name, String mobile, String zhiwu, String qq, String email, Integer departid) {
        this.name = name;
        this.mobile = mobile;
        this.zhiwu = zhiwu;
        this.qq = qq;
        this.email = email;
        this.departid = departid;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getZhiwu() {
        return this.zhiwu;
    }
    
    public void setZhiwu(String zhiwu) {
        this.zhiwu = zhiwu;
    }

    public String getQq() {
        return this.qq;
    }
    
    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDepartid() {
        return this.departid;
    }
    
    public void setDepartid(Integer departid) {
        this.departid = departid;
    }
   








}