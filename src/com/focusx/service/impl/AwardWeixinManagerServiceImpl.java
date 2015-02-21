package com.focusx.service.impl;

import java.util.List;

import com.focusx.dao.AwardWeixinManagerDao;
import com.focusx.entity.TMbAwardWeixin;
import com.focusx.entity.TMbAwardWeixinResult;
import com.focusx.service.AwardWeixinManagerService;

public class AwardWeixinManagerServiceImpl implements AwardWeixinManagerService {

	private AwardWeixinManagerDao awardWeixinManagerDao;
	
	public AwardWeixinManagerDao getAwardWeixinManagerDao() {
		return awardWeixinManagerDao;
	}

	public void setAwardWeixinManagerDao(AwardWeixinManagerDao awardWeixinManagerDao) {
		this.awardWeixinManagerDao = awardWeixinManagerDao;
	}

	public Integer addAwardWeixin(TMbAwardWeixin awardweixin) {
		return awardWeixinManagerDao.addAwardWeixin(awardweixin);
	}

	public List<TMbAwardWeixinResult> getAwardWeixinByActivity(
			Integer activityId) {
		return awardWeixinManagerDao.getAwardWeixinByActivity(activityId);
	}

	public void deleteAwardWeixinById(Integer id) {
		awardWeixinManagerDao.deleteAwardWeixinById(id);
	}
	
}
