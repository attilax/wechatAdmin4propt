package com.focusx.service.impl;

import java.util.List;
import java.util.Map;

import com.focusx.dao.ReplyManargerDao;
import com.focusx.entity.TMbReply;
import com.focusx.service.ReplyManagerService;
import com.focusx.util.Page;

public class ReplyManagerServiceImpl implements ReplyManagerService{

	private ReplyManargerDao replyManagerDao;
	
	public ReplyManargerDao getReplyManagerDao() {
		return replyManagerDao;
	}

	public void setReplyManagerDao(ReplyManargerDao replyManagerDao) {
		this.replyManagerDao = replyManagerDao;
	}

	public List<TMbReply> getReplys(Page page, Map<String, Object> data) {
		List<TMbReply> replys = replyManagerDao.getReplys(data);
		page.setResult(replys);
		int totalCount = replyManagerDao.getReplysCount(data);
		page.setTotalCount(totalCount);
		return replys;
	}

	public TMbReply getReply(Integer id) {
		return replyManagerDao.getReply(id);
	}

	public Integer save(TMbReply reply) {
		return replyManagerDao.save(reply);
	}

	public boolean update(TMbReply reply) {
		return replyManagerDao.update(reply);
	}

	public boolean checkKeyword(String keyword, Integer groupid) {
		return replyManagerDao.checkKeyword(keyword, groupid);
	}

	public boolean delete(String deleteList) {
		return replyManagerDao.delete(deleteList);
	}

	public TMbReply getReplyByNewsId(Integer newsId) {
		return replyManagerDao.getReplyByNewsId(newsId);
	}

	public void deleteNews(Integer newsId) {
		replyManagerDao.deleteNews(newsId);
	}
	

}
