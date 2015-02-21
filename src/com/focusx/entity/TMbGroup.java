package com.focusx.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 *  分公司对象
 */
public class TMbGroup  implements java.io.Serializable {

    // Fields    
     private Integer groupid;// 主键ID
     private String groupname;// 分组名称
     private Date createtime;// 创建时间
     private String remark;//店铺地址
     private Integer parentId;// 上一级分组ID
     private Double longitude;//纬度
     private Double latitude;//经度
     private Integer weixinuserCount;//分公司的微信粉丝数量，数据库不存在，只为了显示
     private String createtimeString;//创建时间(以字符串形式，数据库不存在，只为了显示)
     
     private List tagList;

    // Constructors

    /** default constructor */
    public TMbGroup() {
    }

    
    /** full constructor */
    public TMbGroup(String groupname, Date createtime, Integer parentId, String remark, Double longitude, Double latitude) {
        this.groupname = groupname;
        this.createtime = createtime;
        this.parentId = parentId;
        this.remark = remark;
        this.longitude = longitude;
        this.latitude = latitude;
    }

   
    // Property accessors

    public Integer getGroupid() {
        return this.groupid;
    }
    
    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return this.groupname;
    }
    
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
    
    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Integer getParentId() {
        return this.parentId;
    }
    
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }


	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getWeixinuserCount() {
		return weixinuserCount;
	}

	public void setWeixinuserCount(Integer weixinuserCount) {
		this.weixinuserCount = weixinuserCount;
	}

	public String getCreatetimeString() {
		return createtimeString;
	}

	public void setCreatetimeString(String createtimeString) {
		this.createtimeString = createtimeString;
	}


	public Double getLongitude() {
		return longitude;
	}


	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}


	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public List getTagList() {
		return tagList;
	}


	public void setTagList(List tagList) {
		this.tagList = tagList;
	}
	
    
}