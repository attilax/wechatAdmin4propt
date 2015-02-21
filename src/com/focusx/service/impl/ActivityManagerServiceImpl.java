package com.focusx.service.impl;

import java.util.List;
import java.util.Map;

import com.focusx.dao.ActivityManagerDao;
import com.focusx.entity.TMbActivity;
import com.focusx.service.ActivityManagerService;
import com.focusx.util.Page;

public class ActivityManagerServiceImpl implements ActivityManagerService {

	private ActivityManagerDao activityManagerDao;
	
	public ActivityManagerDao getActivityManagerDao() {
		return activityManagerDao;
	}

	public void setActivityManagerDao(ActivityManagerDao activityManagerDao) {
		this.activityManagerDao = activityManagerDao;
	}
	
	public List<TMbActivity> getActivitys(Page<TMbActivity> page,
			Map<String, Object> data) {
		List<TMbActivity> list = activityManagerDao.getActivitys(data);
		page.setResult(list);
		int totalCount = activityManagerDao.getActivitysCount(data);
		page.setTotalCount(totalCount);
		return list;
	}

	public Integer addActivity(TMbActivity activity) {
		return activityManagerDao.addActivity(activity);
	}

	public TMbActivity getActivityById(Integer id) {
		return activityManagerDao.getActivityById(id);
	}

	public void saveActivity(TMbActivity activity) {
		activityManagerDao.saveActivity(activity);
	}

	public List<TMbActivity> getAllActivityNotFinish(String date) {
		return activityManagerDao.getAllActivityNotFinish(date);
	}

	public void delete(Integer id) {
		activityManagerDao.delete(id);
	}

	public List<TMbActivity> getAllActivity(String date) {
		return activityManagerDao.getAllActivity(date);
	}

	@Override
	public boolean updateActivity(TMbActivity activity) {
		return activityManagerDao.updateActivity(activity);
	}

}
