package com.focusx.entity;

import java.util.Date;

/**
 * TMbNews entity. @author MyEclipse Persistence Tools
 */

public class TMbNews  implements java.io.Serializable { 
	
	private Integer id;//主键ID
    private String title;//标题
    private String author;//作者
    private String coverPage;//封面
    private String description;//摘要
    private String mainText;//正文信息，存放HTML代码
    private String htmlName;//HTML文件名
    private Integer groupid;//分公司ID
    private Integer newsType;//图文类型 1 最新促销  2top热卖 3新品上市 4群发消息
    private Integer flag;//标志是否启用，默认0为不启用  1为启用
    private Date createTime;//创建时间
    private Integer showimg;//封面图片是否显示在正文中，1 是 0 否
    private String groupname;//分公司名称，数据库不存在，只是用来显示
    private Integer state;//图文信息状态， 0 有效  1 删除
    private Integer parentId;//用于多图文，0表示每个多图文的第一个，0以后是表示该图文是跟着哪个图文下面的
    private Integer rank;//多图文排序序号，0开始
    private String keyword;//关键字
    
    private String actName;//活动名称
    private Integer activityId;//活动ID

    public TMbNews() {}

    public TMbNews(String title, String author, String coverPage, String description, String mainText, 
    		String htmlName, Integer groupid, Integer newsType, Integer flag, Date createTime, Integer showimg, Integer state
    		,Integer rank, Integer parentId, String keyword, Integer activityId) {
        this.title = title;
        this.author = author;
        this.coverPage = coverPage;
        this.description = description;
        this.mainText = mainText;
        this.htmlName = htmlName;
        this.groupid = groupid;
        this.newsType = newsType;
        this.flag = flag;
        this.createTime = createTime;
        this.showimg = showimg;
        this.state = state;
        this.rank = rank;
        this.parentId = parentId;
        this.keyword = keyword;
        this.activityId = activityId;
    }
   

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverPage() {
        return this.coverPage;
    }
    
    public void setCoverPage(String coverPage) {
        this.coverPage = coverPage;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainText() {
        return this.mainText;
    }
    
    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getHtmlName() {
        return this.htmlName;
    }
    
    public void setHtmlName(String htmlName) {
        this.htmlName = htmlName;
    }

    public Integer getGroupid() {
        return this.groupid;
    }
    
    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

	public Integer getNewsType() {
		return newsType;
	}

	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}
	
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getShowimg() {
		return showimg;
	}

	public void setShowimg(Integer showimg) {
		this.showimg = showimg;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	
}