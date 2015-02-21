package com.focusx.entity;


/**
 * TUserSystemSite entity. @author MyEclipse Persistence Tools
 */

public class TUserSystemSite  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String skinTheme;
     private String wordHandleTime;
     private String breakTime;
     private String nowsPrompt;
     private String localNumbers;
     private String localCity;
     private String datasetDataRow;
     private Short showLoginBox;
     private Short automaticCheckIn;
     private Short agentNoLineNotice;
     private Integer agentId;
     private Short agentOffLineNotice;


    // Constructors

    /** default constructor */
    public TUserSystemSite() {
    }

	/** minimal constructor */
    public TUserSystemSite(Integer agentId) {
        this.agentId = agentId;
    }
    
    /** full constructor */
    public TUserSystemSite(String skinTheme, String wordHandleTime, String breakTime, String nowsPrompt, String localNumbers, String localCity, String datasetDataRow, Short showLoginBox, Short automaticCheckIn, Short agentNoLineNotice, Integer agentId, Short agentOffLineNotice) {
        this.skinTheme = skinTheme;
        this.wordHandleTime = wordHandleTime;
        this.breakTime = breakTime;
        this.nowsPrompt = nowsPrompt;
        this.localNumbers = localNumbers;
        this.localCity = localCity;
        this.datasetDataRow = datasetDataRow;
        this.showLoginBox = showLoginBox;
        this.automaticCheckIn = automaticCheckIn;
        this.agentNoLineNotice = agentNoLineNotice;
        this.agentId = agentId;
        this.agentOffLineNotice = agentOffLineNotice;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getSkinTheme() {
        return this.skinTheme;
    }
    
    public void setSkinTheme(String skinTheme) {
        this.skinTheme = skinTheme;
    }

    public String getWordHandleTime() {
        return this.wordHandleTime;
    }
    
    public void setWordHandleTime(String wordHandleTime) {
        this.wordHandleTime = wordHandleTime;
    }

    public String getBreakTime() {
        return this.breakTime;
    }
    
    public void setBreakTime(String breakTime) {
        this.breakTime = breakTime;
    }

    public String getNowsPrompt() {
        return this.nowsPrompt;
    }
    
    public void setNowsPrompt(String nowsPrompt) {
        this.nowsPrompt = nowsPrompt;
    }

    public String getLocalNumbers() {
        return this.localNumbers;
    }
    
    public void setLocalNumbers(String localNumbers) {
        this.localNumbers = localNumbers;
    }

    public String getLocalCity() {
        return this.localCity;
    }
    
    public void setLocalCity(String localCity) {
        this.localCity = localCity;
    }

    public String getDatasetDataRow() {
        return this.datasetDataRow;
    }
    
    public void setDatasetDataRow(String datasetDataRow) {
        this.datasetDataRow = datasetDataRow;
    }

    public Short getShowLoginBox() {
        return this.showLoginBox;
    }
    
    public void setShowLoginBox(Short showLoginBox) {
        this.showLoginBox = showLoginBox;
    }

    public Short getAutomaticCheckIn() {
        return this.automaticCheckIn;
    }
    
    public void setAutomaticCheckIn(Short automaticCheckIn) {
        this.automaticCheckIn = automaticCheckIn;
    }

    public Short getAgentNoLineNotice() {
        return this.agentNoLineNotice;
    }
    
    public void setAgentNoLineNotice(Short agentNoLineNotice) {
        this.agentNoLineNotice = agentNoLineNotice;
    }

    public Integer getAgentId() {
        return this.agentId;
    }
    
    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Short getAgentOffLineNotice() {
        return this.agentOffLineNotice;
    }
    
    public void setAgentOffLineNotice(Short agentOffLineNotice) {
        this.agentOffLineNotice = agentOffLineNotice;
    }
   








}