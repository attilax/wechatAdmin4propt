package com.focusx.dao;

import java.util.List;
import java.util.Map;

import com.focusx.entity.TMbListen;

public interface ListenDao {
	
	/**
	 *  保存新增的关键词监听
	 *  @param listen 关键词监听对象
	 *  @return id 关键词监听对象ID
	 */
	public Integer save(TMbListen listen);
	
	/**
	 *  获取关键词监听信息
	 *  @param page 分页对象
	 *  @param data 查询条件
	 *  @return List<TMbListen> 关键词监听数组
	 */
	public List<TMbListen> getListens(Map<String, Object> data);
	
	/**
	 *  统计关键词监听信息数量
	 *  @param data 查询条件
	 *  @return 数量
	 */
	public Integer getListensCount(Map<String, Object> data);
	
	/**
	 *  根据ID获取关键词监听信息
	 *  @param listenId 主键ID
	 *  @return TMbListen 关键词监听对象
	 */
	public TMbListen getListenByListenId(Integer listenId);
	
	/**
	 *  更新关键词监听信息
	 *  @param listen 关键词监听对象
	 *  @param 更新结果
	 */
	public boolean update(TMbListen listen);
	
	/**
	 *  删除关键词监听信息
	 *  @param listenIds 要删除的ID集合字符串
	 *  @return 删除结果
	 */
	public boolean delete(String listenIds);
}
