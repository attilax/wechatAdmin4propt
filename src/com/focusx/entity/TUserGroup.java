package com.focusx.entity;



/**
 * TUserGroup entity. @author MyEclipse Persistence Tools
 */

public class TUserGroup  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String name;
     private String description;
     private String calledno;
     private Integer gatewayId;
     private String callednosrc;


    // Constructors

    /** default constructor */
    public TUserGroup() {
    }

	/** minimal constructor */
    public TUserGroup(String name, Integer gatewayId) {
        this.name = name;
        this.gatewayId = gatewayId;
    }
    
    /** full constructor */
    public TUserGroup(String name, String description, String calledno, Integer gatewayId, String callednosrc) {
        this.name = name;
        this.description = description;
        this.calledno = calledno;
        this.gatewayId = gatewayId;
        this.callednosrc = callednosrc;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCalledno() {
        return this.calledno;
    }
    
    public void setCalledno(String calledno) {
        this.calledno = calledno;
    }

    public Integer getGatewayId() {
        return this.gatewayId;
    }
    
    public void setGatewayId(Integer gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getCallednosrc() {
        return this.callednosrc;
    }
    
    public void setCallednosrc(String callednosrc) {
        this.callednosrc = callednosrc;
    }
   








}