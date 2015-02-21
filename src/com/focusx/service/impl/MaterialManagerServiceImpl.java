package com.focusx.service.impl;

import java.util.List;
import java.util.Map;

import com.focusx.dao.MaterialManagerDao;
import com.focusx.entity.TMbNews;
import com.focusx.service.MaterialManagerService;
import com.focusx.util.Page;

public class MaterialManagerServiceImpl implements MaterialManagerService{
	
	private MaterialManagerDao materialManagerDao;

	public MaterialManagerDao getMaterialManagerDao() {
		return materialManagerDao;
	}

	public void setMaterialManagerDao(MaterialManagerDao materialManagerDao) {
		this.materialManagerDao = materialManagerDao;
	}

	public List<TMbNews> getMaterials(Page<TMbNews> page,
			Map<String, Object> data) {
		List<TMbNews> news = materialManagerDao.getMaterials(data);
		page.setResult(news);
		int totalcount = materialManagerDao.getMaterialsCount(data).intValue();
		page.setTotalCount(totalcount);
		return news;
	}

	public Integer saveMaterial(TMbNews news) {
		return materialManagerDao.saveMaterial(news);
	}

	public TMbNews getTMbNewsById(Integer id) {
		return materialManagerDao.getTMbNewsById(id);
	}

	public Integer update(TMbNews news) {
		return materialManagerDao.update(news);
	}

	public void delete(Integer id) {
		materialManagerDao.delete(id);
	}

	public void deleteMaterialByState(Integer id) {
		materialManagerDao.deleteMaterialByState(id);
	}

	public boolean checkNewsTypeCount(Integer groupid, Integer id) {
		return materialManagerDao.checkNewsTypeCount(groupid, id);
	}

	public void updateFlag(Integer id, Integer type) {
		materialManagerDao.updateFlag(id, type);
	}

	public List<TMbNews> getMoreMaterial(Integer newsId) {
		return materialManagerDao.getMoreMaterial(newsId);
	}

	public boolean saveMoreMaterial(TMbNews news) {
		return materialManagerDao.saveMoreMaterial(news);
	}

	public boolean updateNewsType(Integer newsType, Integer groupid, Integer id, String keyword, Integer activityId) {
		return materialManagerDao.updateNewsType(newsType, groupid, id, keyword, activityId);
	}

	public boolean checkKeyword(String keyword) {
		return materialManagerDao.checkKeyword(keyword);
	}

	public boolean checkActivityNewsType(Integer newsType, Integer activityId) {
		return materialManagerDao.checkActivityNewsType(newsType, activityId);
	}

	public List<TMbNews> getMaterialByGroupid(Integer groupid) {
		return materialManagerDao.getMaterialByGroupid(groupid);
	}

	public List<TMbNews> getMaterialsByGroupid(Integer groupid) {
		return materialManagerDao.getMaterialsByGroupid(groupid);
	}

}
