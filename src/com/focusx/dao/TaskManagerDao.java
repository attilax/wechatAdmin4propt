package com.focusx.dao;

import java.util.List;
import java.util.Map;

import com.focusx.entity.TMbTask;

public interface TaskManagerDao {
	
	/**
	 *  获取有图片地址的聊天记录信息
	 *  @return 聊天记录信息
	 */
	public List<TMbTask> getTaskForMediaUrl(Map<String, Object> data);
	
	/**
	 *  统计有图片地址的聊天记录信息数量
	 *  @return 聊天记录信息数量
	 */
	public Integer getTaskForMediaUrlCount();
	
	/**
	 *  置空MediaUrl字段信息
	 *  @param id 聊天记录对象ID
	 */
	public void updateTaskForMediaUrl(Integer id);
	
	/**
	 *  根据条件获取聊天记录信息
	 *  @param data 查询条件
	 *  @return List<TMbTask> 聊天记录信息
	 */
	public List<TMbTask> getTaskForListen(Map<String, Object> data);
	
	/**
	 *  统计根据条件查询得到的聊天记录信息数量
	 *  @param data 查询条件
	 *  @return 聊天记录信息数量
	 */
	public Integer getTaskForListenCount(Map<String, Object> data);

}
