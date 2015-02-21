package com.focusx.entity.weixin.sendMessage;

import java.util.List;

/** 
 * com.focusx.entity.weixin.sendMessage
 * News.java 
 * author:vincente  2013-11-18 
 */
public class News {
	
	private List<Article> articles;
	
	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
}
