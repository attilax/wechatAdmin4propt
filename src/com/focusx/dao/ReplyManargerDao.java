package com.focusx.dao;

import java.util.List;
import java.util.Map;

import com.focusx.entity.TMbReply;

public interface ReplyManargerDao {
	
	/**
	 *  根据条件获取关键词信息
	 *  @param data
	 *  @return List<TMbReply>
	 */
	public List<TMbReply> getReplys(Map<String, Object> data);
	
	/**
	 *  根据条件统计关键词信息数量
	 *  @param data
	 *  @return Integer
	 */
	public Integer getReplysCount(Map<String, Object> data);
	
	/**
	 *  根据ID获取关键词信息
	 *  @param id
	 *  @return TMbReply
	 */
	public TMbReply getReply(Integer id);
	
	/**
	 *  保存新增的信息
	 *  @param reply 关键词自动回复对象
	 *  @return 对象ID
	 */
	public Integer save(TMbReply reply);
	
	/**
	 *  更新对象信息
	 *  @param reply 关键词自动回复对象
	 *  @return boolean
	 */
	public boolean update(TMbReply reply);
	
	/**
	 *  判断是否存在该关键词的记录
	 *  @param keyword
	 *  @param groupid
	 *  @return boolean
	 */
	public boolean checkKeyword(String keyword, Integer groupid);
	
	/**
	 *  根据ID删除信息
	 *  @param deleteList ID集合字符串
	 *  @return boolean
	 */
	public boolean delete(String deleteList);
	
	/**
	 *  根据newsId获取对应关键词信息
	 *  @param newsId 图文ID
	 *  @return TMbReply
	 */
	public TMbReply getReplyByNewsId(Integer newsId);

	/**
	 *  根据newsId删除对应的信息
	 *  @param newsId 图文ID
	 */
	public void deleteNews(Integer newsId);
}
