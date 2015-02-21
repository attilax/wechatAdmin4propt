package com.focusx.service.impl;

import java.util.List;
import java.util.Map;

import com.focusx.dao.TaskDao;
import com.focusx.entity.TMbTask;
import com.focusx.service.TaskService;
import com.focusx.util.Page;

public class TaskServiceImpl implements TaskService{

	private TaskDao taskDao;
	
	public List<TMbTask> getTasksByData(Map<String, Object> data, Page<TMbTask> page) {
		List<TMbTask> tasks = taskDao.getTasksByData(data);
		page.setResult(tasks);
		int totalCount = taskDao.getTasksByDataCount(data).intValue();
		page.setTotalCount(totalCount);
		return tasks;
	}

	public boolean updateToStateById(String id,String openid,String replyTxt,int userId){
		return taskDao.updateToStateById(id,openid,replyTxt,userId);
	}
	
	public List<TMbTask> getTasksByOpenId(Page<TMbTask> page, Map<String, Object> data){
		List<TMbTask> tasks = taskDao.getTasksByOpenId(data);
		page.setResult(tasks);
		int totalCount = taskDao.getTasksByOpenIdCount(data).intValue();
		page.setTotalCount(totalCount);
		return tasks;
	}
	
	public TaskDao getTaskDao() {
		return taskDao;
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public void save(TMbTask task) {
		taskDao.save(task);
	}

	public TMbTask getTaskById(String id) {
		return taskDao.getTaskById(id);
	}
	
	public List<TMbTask> getTaskToHome(Integer userId){
		return taskDao.getTaskToHome(userId);
	}

}
