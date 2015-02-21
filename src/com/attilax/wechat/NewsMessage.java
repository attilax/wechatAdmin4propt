package com.attilax.wechat;

import java.util.List;

import com.focusx.entity.weixin.message.response.Article;
import com.focusx.entity.weixin.message.response.BaseMessage;

/**
 * 多图文消息
 * 
 * @author 张春雨
 * @模块
 * @日期 2013-12-15 时间：下午09:07:14
 */
public class NewsMessage extends BaseMessage {

	// 图文消息个数，限制为10条以内
	private int articleCount;
	// 多条图文消息信息，默认第一个item为大图
	private List<Article> articles;

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

}
