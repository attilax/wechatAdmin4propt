package com.focusx.service.impl;

import java.util.List;
import java.util.Map;

import com.focusx.dao.TaskManagerDao;
import com.focusx.entity.TMbTask;
import com.focusx.service.TaskManagerService;
import com.focusx.util.Page;

public class TaskManagerServiceImpl implements TaskManagerService {

	private TaskManagerDao taskManagerDao;
	
	public TaskManagerDao getTaskManagerDao() {
		return taskManagerDao;
	}

	public void setTaskManagerDao(TaskManagerDao taskManagerDao) {
		this.taskManagerDao = taskManagerDao;
	}
	
	public List<TMbTask> getTaskForMediaUrl(Page<TMbTask> page, Map<String, Object> data) {
		List<TMbTask> list = taskManagerDao.getTaskForMediaUrl(data);
		page.setResult(list);
		int totalCount = taskManagerDao.getTaskForMediaUrlCount();
		page.setTotalCount(totalCount);
		return list;
	}

	public void updateTaskForMediaUrl(Integer id) {
		taskManagerDao.updateTaskForMediaUrl(id);
	}

	public List<TMbTask> getTaskForListen(Page<TMbTask> page,
			Map<String, Object> data) {
		List<TMbTask> list = taskManagerDao.getTaskForListen(data);
		page.setResult(list);
		int totalCount = taskManagerDao.getTaskForListenCount(data);
		page.setTotalCount(totalCount);
		return list;
	}
}
