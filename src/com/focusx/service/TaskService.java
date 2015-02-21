package com.focusx.service;

import java.util.List;
import java.util.Map;

import com.focusx.entity.TMbTask;
import com.focusx.util.Page;

public interface TaskService {
	
	/**
	 *  根据条件获取待办任务集合
	 *  @param data 查询条件
	 *  @return List<TMbTask> 待办任务集合
	 */
	public List<TMbTask> getTasksByData(Map<String, Object> data, Page<TMbTask> page);
	
	/**
	 *  更改未读信息为已读信息
	 *  @param id 信息ID
	 *  @return boolean 成功或失败
	 */
	public boolean updateToStateById(String id,String openid,String replyTxt,int userId);
	
	/**
	 *  获取相关的聊天记录
	 *  @param page 分页对象
	 *  @param data 查询条件
	 *  @return List<TMbTask> 聊天记录
	 */
	public List<TMbTask> getTasksByOpenId(Page<TMbTask> page, Map<String, Object> data);
	
	/**
	 *  保存聊天记录
	 *  @param task 聊天内容
	 */
	public void save(TMbTask task);
	
	/**
	 *  根据ID获取聊天记录
	 *  @param id 聊天对象ID
	 *  @return TMbTask 聊天对象
	 */
	public TMbTask getTaskById(String id);
	
	/**
	 *  提供给“我的主页”显示人工客服模块
	 *  @param userId 当前登录用户的userId
	 *  @return List<TMbTask> 属于当前用户的未回复的粉丝信息
	 */
	public List<TMbTask> getTaskToHome(Integer userId);

}
