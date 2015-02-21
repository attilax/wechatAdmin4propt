package com.focusx.service.impl;

import java.util.List;
import java.util.Map;

import com.focusx.dao.ListenDao;
import com.focusx.entity.TMbListen;
import com.focusx.service.ListenService;
import com.focusx.util.Page;

public class ListenServiceImpl implements ListenService{

	private ListenDao listenDao;
	
	public Integer save(TMbListen listen) {
		return listenDao.save(listen);
	}

	public ListenDao getListenDao() {
		return listenDao;
	}

	public void setListenDao(ListenDao listenDao) {
		this.listenDao = listenDao;
	}

	public List<TMbListen> getListens(Page<TMbListen> page,
			Map<String, Object> data) {
		List<TMbListen> list = listenDao.getListens(data);
		page.setResult(list);
		int totalCount = listenDao.getListensCount(data);
		page.setTotalCount(totalCount);
		return list;
	}

	public TMbListen getListenByListenId(Integer listenId) {
		return listenDao.getListenByListenId(listenId);
	}

	public boolean update(TMbListen listen) {
		return listenDao.update(listen);
	}

	public boolean delete(String listenIds) {
		return listenDao.delete(listenIds);
	}

}
