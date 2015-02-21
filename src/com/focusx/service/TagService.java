package com.focusx.service;

import java.util.List;

import com.focusx.entity.TMbTag;

public interface TagService {
	
	/**
	 *  根据groupid获取分公司区域
	 *  @param groupId  分公司ID
	 *  @return List<TMbTag>
	 */
	public List<TMbTag> getTagByGroupid(Integer groupId);

}
