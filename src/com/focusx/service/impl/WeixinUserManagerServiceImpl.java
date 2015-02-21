package com.focusx.service.impl;

import java.util.List;
import java.util.Map;

import com.focusx.dao.WeixinUserManagerDao;
import com.focusx.entity.TMbWeixinUser;
import com.focusx.service.WeixinUserManagerService;
import com.focusx.util.Page;

public class WeixinUserManagerServiceImpl implements WeixinUserManagerService{

	private WeixinUserManagerDao weixinUserManagerDao;
	
	public WeixinUserManagerDao getWeixinUserManagerDao() {
		return weixinUserManagerDao;
	}

	public void setWeixinUserManagerDao(WeixinUserManagerDao weixinUserManagerDao) {
		this.weixinUserManagerDao = weixinUserManagerDao;
	}
	
	public List<TMbWeixinUser> getWeixinusersByGroupid(
			Page<TMbWeixinUser> page, Map<String, Object> data) {
		List<TMbWeixinUser> weixinuserList = weixinUserManagerDao.getWeixinusersByGroupid(data);
		page.setResult(weixinuserList);
		int totalcount = weixinUserManagerDao.getWeixinusersCountByGroupid(data).intValue();
		page.setTotalCount(totalcount);
		return weixinuserList;
	}

	public List<TMbWeixinUser> getWeixinUsers(Page<TMbWeixinUser> page,
			Map<String, Object> data) {
		List<TMbWeixinUser> weixinuserList = weixinUserManagerDao.getWeixinUsers(data);
		page.setResult(weixinuserList);
		int totalcount = weixinUserManagerDao.getWeixinUsersCount(data).intValue();
		page.setTotalCount(totalcount);
		return weixinuserList;
	}

	public TMbWeixinUser getWeixinUserByUserId(Integer userId) {
		return weixinUserManagerDao.getWeixinUserByUserId(userId);
	}

	public Integer getWeixinUserWithNullBranchCount() {
		return weixinUserManagerDao.getWeixinUserWithNullBranchCount();
	}

	public List<TMbWeixinUser> getWeixinUserByTime(Integer time) {
		return weixinUserManagerDao.getWeixinUserByTime(time);
	}

	public void updateToGroupId(Map<Integer, Integer> data) {
		weixinUserManagerDao.updateToGroupId(data);
	}

	public List<TMbWeixinUser> getNullGroupList(Page<TMbWeixinUser> page,
			Map<String, Object> data) {
		List<TMbWeixinUser> list = weixinUserManagerDao.getNullGroupList(data);
		page.setResult(list);
		int totalCount = weixinUserManagerDao.getNullGroupListCount(data);
		page.setTotalCount(totalCount);
		return list;
	}

	public void setGroupToWeixinUser(Integer groupid, String userIds) {
		weixinUserManagerDao.setGroupToWeixinUser(groupid, userIds);
	}

	public List<TMbWeixinUser> getWeixinUserByNickName(String nickname) {
		return weixinUserManagerDao.getWeixinUserByNickName(nickname);
	}

	public List<TMbWeixinUser> getWeixinUsersByNickName(
			Page<TMbWeixinUser> page, Map<String, Object> data) {
		List<TMbWeixinUser> list = weixinUserManagerDao.getWeixinUsersByNickName(data);
		page.setResult(list);
		int totalCount = weixinUserManagerDao.getWeixinUsersByNickNameCount(data);
		page.setTotalCount(totalCount);
		return list;
	}

	public boolean checkWeixinUser(String openid) {
		return weixinUserManagerDao.checkWeixinUser(openid);
	}

	public void save(TMbWeixinUser user) {
		weixinUserManagerDao.save(user);
	}

}
