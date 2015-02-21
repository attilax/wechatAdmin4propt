package com.focusx.service.impl;

import java.util.List;

import com.focusx.dao.TagDao;
import com.focusx.entity.TMbTag;
import com.focusx.service.TagService;

public class TagServiceImpl implements TagService{
    private TagDao tagDao;
	
	public TagDao getTagDao() {
		return tagDao;
	}

	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}

	public List<TMbTag> getTagByGroupid(Integer groupId) {
		return tagDao.getTagByGroupid(groupId);
	}

}
