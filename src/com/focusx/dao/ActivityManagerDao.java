package com.focusx.dao;

import java.util.List;
import java.util.Map;

import com.focusx.entity.TMbActivity;

public interface ActivityManagerDao {
	
	/**
	 *  根据查询条件获取活动信息
	 *  @param data 查询条件
	 *  @return List<TMbActivity> 活动信息集合
	 */
	public List<TMbActivity> getActivitys(Map<String, Object> data);
	
	/**
	 *  根据查询条件统计活动信息数量
	 *  @param data 查询条件
	 *  @return 活动信息集合数量
	 */
	public Integer getActivitysCount(Map<String, Object> data);
	
	/**
	 *  保存新增的活动信息
	 *  @param activity 活动对象
	 *  @return 活动对象ID
	 */
	public Integer addActivity(TMbActivity activity);
	
	/**
	 *  根据ID获取活动信息
	 *  @param id 活动对象ID
	 *  @return 活动信息
	 */
	public TMbActivity getActivityById(Integer id);
	
	/**
	 *  保存编辑后的活动信息
	 *  @param activity 活动对象
	 */
	public void saveActivity(TMbActivity activity);
	
	/**
	 *  获取在某个时间前未结束的所有活动
	 *  @param date 时间（字符串，年月日）
	 *  @return List<TMbActivity>
	 */
	public List<TMbActivity> getAllActivityNotFinish(String date);
	
	/**
	 *  根据id删除指定活动
	 *  @param id
	 */
	public void delete(Integer id);
	
	/**
	 *  获取在某个时间前未结束或已结束的所有活动
	 *  @param date 时间（字符串，年月日）
	 *  @return List<TMbActivity>
	 */
	public List<TMbActivity> getAllActivity(String date);
	
	
	public boolean updateActivity(TMbActivity activity);

}
