package com.focusx.entity;

import java.util.Date;

public class TUserRole  implements java.io.Serializable {
	
	private Integer id;//主键ID
    private String name;//角色名称
    private Short isSystem;//是否是系统定义的角色(0不是,1是)
    private Integer vdnid;//所属VDNID。-1开发级
    private Integer subSystemType;//系统类型(1ICIP,2ICIP报表,3工作流,4业务报表,300=BS工作流平台,310=GIS系统,311=微博微信系统,312=IM系统)
    private Date createTime;//创建时间
    private Date alterTime;//修改时间
    private String remark;//备注

    public TUserRole() {}

    public TUserRole(String name, Short isSystem) {
        this.name = name;
        this.isSystem = isSystem;
    }
    
    public TUserRole(String name, Short isSystem, Integer vdnid, Integer subSystemType, Date createTime, Date alterTime, String remark) {
        this.name = name;
        this.isSystem = isSystem;
        this.vdnid = vdnid;
        this.subSystemType = subSystemType;
        this.createTime = createTime;
        this.alterTime = alterTime;
        this.remark = remark;
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

    public Short getIsSystem() {
        return this.isSystem;
    }
    
    public void setIsSystem(Short isSystem) {
        this.isSystem = isSystem;
    }

    public Integer getVdnid() {
        return this.vdnid;
    }
    
    public void setVdnid(Integer vdnid) {
        this.vdnid = vdnid;
    }

    public Integer getSubSystemType() {
        return this.subSystemType;
    }
    
    public void setSubSystemType(Integer subSystemType) {
        this.subSystemType = subSystemType;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getAlterTime() {
		return alterTime;
	}

	public void setAlterTime(Date alterTime) {
		this.alterTime = alterTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}