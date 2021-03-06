package com.focusx.service;

import java.util.List;
import java.util.Map;

import com.focusx.entity.TMbActAward;
import com.focusx.util.Page;

public interface ActAwardManagerService {
	
	/**
	 *  保存新增的奖品信息
	 *  @param actaward 奖品对象
	 *  @return 奖品对象ID
	 */
	public Integer addActAward(TMbActAward actaward);
	
	/**
	 *  根据ID获取奖品信息
	 *  @param id 奖品对象ID
	 *  @return 奖品对象
	 */
	public TMbActAward getActAwardById(Integer id);
	
	/**
	 *  根据条件获取奖品信息
	 *  @param page
	 *  @param data
	 *  @return 奖品信息集合
	 */
	public List<TMbActAward> getActAwards(Page<TMbActAward> page, Map<String, Object> data);
	
	/**
	 *  更新奖品信息
	 *  @param actaward 奖品对象
	 */
	public void saveActAward(TMbActAward actaward);
	
	/**
	 *  根据activityId获取属于该活动的奖品
	 *  @param activityId 活动对象ID
	 *  @return 奖品信息集合
	 */
	public List<TMbActAward> getActAwardsByActivityId(Integer activityId);
	
	/**
	 *  获取没有任何活动占用的奖品信息
	 *  @return 奖品信息集合
	 */
	public List<TMbActAward> getActAwardsOutAllActivity(Page<TMbActAward> page, Map<String, Object> data);
	
	/**
	 *  更改奖品所属的活动
	 *  @param actawards 奖品对象ID集合字符串
	 *  @param activityId 活动对象ID
	 */
	public void updateActAwardByActivityId(String actawards, Integer activityId);
	
	/**
	 *  删除奖品和活动的关联
	 *  @param actawardId 奖品对象ID
	 */
	public void deleteActAwardByActivityId(Integer actawardId);
	
	/**
	 *  删除奖品信息
	 *  @param id 奖品对象ID
	 */
	public void deleteActAwardById(Integer id);
}
