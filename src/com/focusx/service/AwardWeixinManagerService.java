package com.focusx.service;

import java.util.List;

import com.focusx.entity.TMbAwardWeixin;
import com.focusx.entity.TMbAwardWeixinResult;

public interface AwardWeixinManagerService {
	
	/**
	 *  插入一个初始化中奖的信息
	 *  @param awardweixin 中奖对象
	 */
	public Integer addAwardWeixin(TMbAwardWeixin awardweixin);
	
	/**
	 *  根据activityId获取相关的中奖名单信息
	 *  @param activityId 活动对象ID
	 *  @return 中奖名单信息集合
	 */
	public List<TMbAwardWeixinResult> getAwardWeixinByActivity(Integer activityId);
	
	/**
	 *  删除中奖名单信息
	 *  @param id 中奖名单对象ID
	 */
	public void deleteAwardWeixinById(Integer id);
}

