package com.focusx.service.impl;

import java.util.List;
import java.util.Map;

import com.focusx.dao.ActAwardManagerDao;
import com.focusx.entity.TMbActAward;
import com.focusx.service.ActAwardManagerService;
import com.focusx.util.Page;

public class ActAwardManagerServiceImpl implements ActAwardManagerService{
	
	private ActAwardManagerDao actAwardManagerDao;

	public ActAwardManagerDao getActAwardManagerDao() {
		return actAwardManagerDao;
	}

	public void setActAwardManagerDao(ActAwardManagerDao actAwardManagerDao) {
		this.actAwardManagerDao = actAwardManagerDao;
	}

	public Integer addActAward(TMbActAward actaward) {
		return actAwardManagerDao.addActAward(actaward);
	}

	public TMbActAward getActAwardById(Integer id) {
		return actAwardManagerDao.getActAwardById(id);
	}

	public List<TMbActAward> getActAwards(Page<TMbActAward> page,
			Map<String, Object> data) {
		List<TMbActAward> actawardList = actAwardManagerDao.getActAwards(data);
		page.setResult(actawardList);
		int totalCount = actAwardManagerDao.getActAwardsCount(data);
		page.setTotalCount(totalCount);
		return actawardList;
	}

	public void saveActAward(TMbActAward actaward) {
		actAwardManagerDao.saveActAward(actaward);
	}

	public List<TMbActAward> getActAwardsByActivityId(Integer activityId) {
		return actAwardManagerDao.getActAwardsByActivityId(activityId);
	}

	public List<TMbActAward> getActAwardsOutAllActivity(Page<TMbActAward> page, Map<String, Object> data) {
		List<TMbActAward> list = actAwardManagerDao.getActAwardsOutAllActivity(data);
		page.setResult(list);
		int totalCount = actAwardManagerDao.getActAwardsOutAllActivityCount(data);
		page.setTotalCount(totalCount);
		return list;
	}

	public void updateActAwardByActivityId(String actawards, Integer activityId) {
		actAwardManagerDao.updateActAwardByActivityId(actawards, activityId);
	}

	public void deleteActAwardByActivityId(Integer actawardId) {
		actAwardManagerDao.deleteActAwardByActivityId(actawardId);
	}

	public void deleteActAwardById(Integer id) {
		actAwardManagerDao.deleteActAwardById(id);
	}
	
}
