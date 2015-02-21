package com.focusx.service;

import java.util.List;
import java.util.Map;

import com.focusx.entity.TMbTask;
import com.focusx.util.Page;

public interface TaskManagerService {
	
	/**
	 *  获取有图片地址的聊天记录信息
	 *  @param page 分页对象	
	 *  @param data 查询条件
	 *  @return List<TMbTask> 聊天记录信息
	 */
	public List<TMbTask> getTaskForMediaUrl(Page<TMbTask> page, Map<String, Object> data);
	
	/**
	 *  置空MediaUrl字段信息
	 *  @param id 聊天记录对象ID
	 */
	public void updateTaskForMediaUrl(Integer id);
	
	/**
	 *  根据关键词获取聊天记录
	 *  @param page 分页对象	
	 *  @param data 查询条件
	 *  @return List<TMbTask> 聊天记录信息
	 */
	public List<TMbTask> getTaskForListen(Page<TMbTask> page, Map<String, Object> data);
}
