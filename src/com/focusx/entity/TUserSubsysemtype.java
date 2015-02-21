package com.focusx.entity;


/**
 * TUserSubsysemtype entity. @author MyEclipse Persistence Tools
 */

public class TUserSubsysemtype  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String subsysemtype;
     private Integer value;


    // Constructors

    /** default constructor */
    public TUserSubsysemtype() {
    }

    
    /** full constructor */
    public TUserSubsysemtype(String subsysemtype, Integer value) {
        this.subsysemtype = subsysemtype;
        this.value = value;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubsysemtype() {
        return this.subsysemtype;
    }
    
    public void setSubsysemtype(String subsysemtype) {
        this.subsysemtype = subsysemtype;
    }

    public Integer getValue() {
        return this.value;
    }
    
    public void setValue(Integer value) {
        this.value = value;
    }
   








}