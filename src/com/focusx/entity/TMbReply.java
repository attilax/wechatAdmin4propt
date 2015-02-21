package com.focusx.entity;

import java.io.Serializable;

public class TMbReply implements Serializable{
	
	private Integer id;       //主键ID
	private String rules;     //规则说明
	private String keyword;   //关键词
	private String md5keyword;//md5加密的关键词
	private Integer newsId;   //图文ID
	private String content;   //纯文字的回复语
	private String title;     //图文标题
	private Integer groupid;  //分公司ID
	private String multiReplyContent; //多次收到关键字时的回复
	
	public TMbReply(){}
	
	public TMbReply(Integer id, String rules, String keyword, String md5keyword, Integer newsId, String content, Integer groupid){
		this.id = id;
		this.rules = rules;
		this.keyword = keyword;
		this.md5keyword = md5keyword;
		this.newsId = newsId;
		this.content = content;
		this.groupid = groupid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMd5keyword() {
		return md5keyword;
	}

	public void setMd5keyword(String md5keyword) {
		this.md5keyword = md5keyword;
	}

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getMultiReplyContent() {
		return multiReplyContent;
	}

	public void setMultiReplyContent(String multiReplyContent) {
		this.multiReplyContent = multiReplyContent;
	}
	
	
	

}
