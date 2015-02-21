package com.focusx.service;

import java.util.List;
import java.util.Map;

import com.focusx.entity.THdMember;
import com.focusx.entity.dataAnalysis.Activity;
import com.focusx.entity.dataAnalysis.Member;
import com.focusx.entity.dataAnalysis.Menu;
import com.focusx.entity.dataAnalysis.News;
import com.focusx.entity.dataAnalysis.Sign;
import com.focusx.entity.dataAnalysis.WeixinUser;
import com.focusx.util.Page;

public interface DataAnalysisService {
	
	/**
	 *  根据条件获取粉丝数据分析
	 *  @param groupid
	 *  @return WeixinUser
	 */
	public WeixinUser getWeixin(Integer groupid);
	
	/**
	 *  根据条件获取粉丝数据分析
	 *  @param page 分页对象
	 *  @param data 查询条件
	 *  @return List<WeixinUser>
	 */
	public List<WeixinUser> getWeixins(Page page, Map<String, Object> data);
	
	public Map<String,Integer> getWeixinCount(Map<String, Object> data);
	
	/**
	 *  根据条件获取对应数据
	 *  @param data 查询条件
	 *  @return List<WeixinUser> 数据集合
	 */
	public List<WeixinUser> getCsvData(Map<String, Object> data);
	
	/**
	 *  根据条件获取图文数据分析
	 *  @param groupid
	 *  @param newsId
	 *  @return News
	 */
	public News getNews(Integer groupid, Integer newsId);
	
	/**
	 *  根据条件获取图文数据分析
	 *  @param page 分页对象
	 *  @param data 查询条件
	 *  @return List<News>
	 */
	public List<News> getNewses(Page page, Map<String, Object> data);
	
	public Map<String,Integer> getNewsCount(Map<String,Object> data);
	
	/**
	 *  根据条件获取对应数据
	 *  @param data 查询条件
	 *  @return List<WeixinUser> 数据集合
	 */
	public List<News> getCsvDataToNews(Map<String, Object> data);
	
	/**
	 *  获取会员绑定昨日指标数据
	 *  @param groupid 分公司ID 
	 *  @return Member
	 */
	public Member getMember(Integer groupid);
	
	/**
	 *  根据条件获取会员绑定数据分析
	 *  @param page 分页对象
	 *  @param data 查询条件
	 *  @return List<Member>
	 */
	public List<Member> getMembers(Page page, Map<String, Object> data);
	
	
	public Map<String,Integer>  getMembersCount(Map<String,Object> data);
	
	/**
	 *  根据条件获取对应数据
	 *  @param data 查询条件
	 *  @return List<Member> 数据集合
	 */
	public List<Member> getCsvDataToMember(Map<String, Object> data);
	
	/**
	 *  根据分公司ID获取对应数据
	 *  @param groupid  分公司ID
	 *  @param storesId 门店ID
	 *  @return Sign
	 */
	public Sign getSign(Integer groupid, Integer storesId);
	
	/**
	 *  根据条件获取门店签到数据分析
	 *  @param page 分页对象
	 *  @param data 查询条件
	 *  @return List<Sign>
	 */
	public List<Sign> getSigns(Page page, Map<String, Object> data);
	
	public Map<String,Integer>  getSignsCount(Map<String,Object> data);
	
	/**
	 *  根据条件获取对应数据
	 *  @param data 查询条件
	 *  @return List<Member> 数据集合
	 */
	public List<Sign> getCsvDataToSign(Map<String, Object> data);
	
	/**
	 *   根据分公司ID和活动ID获取对应信息
	 *   @param groupid 分公司ID
	 *   @param activityId 活动ID
	 *   @return Activity 
	 */
	public Activity getActivity(Integer groupid,Integer activityId);
	
	/**
	 *  根据条件获取活动数据分析
	 *  @param page 分页对象
	 *  @param data 查询条件
	 *  @return List<Activity>
	 */
	public List<Activity> getActivitys(Page page, Map<String, Object> data);
	
	public Map<String,Integer>  getActivitysCount(Map<String,Object> data);
	
	/**
	 *  根据条件获取活动数据分析
	 *  @param data 查询条件
	 *  @return List<Activity>
	 */
	public List<Activity> getCsvDataToActivity(Map<String, Object> data);
	
	/**
	 *  根据分公司ID获取对应数据
	 *  @param groupid
	 *  @param eventKey
	 *  @return Menu
	 */
	public Menu getMenu(Integer groupid, String eventKey);
	
	/**
	 *  根据条件获取板块数据分析
	 *  @param page 分页对象
	 *  @param data 查询条件
	 *  @return List<Menu>
	 */
	public List<Menu> getMenus(Page page, Map<String, Object> data);
	
	public Map<String,Integer> getMenusCount(Map<String,Object> data);
	
	/**
	 *  根据条件获取对应数据
	 *  @param data 查询条件
	 *  @return List<Menu>
	 */
	public List<Menu> getCsvDataToMenu(Map<String, Object> data);
	
	/**
	 *  根据条件获取对应数据
	 *  @param page 分页对象
	 *  @param data 查询条件
	 *  @return List<THdMember>
	 */
	public List<THdMember> getTHdMembers(Page page, Map<String, Object> data);
	
}
