package com.focusx.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.focusx.entity.THdMember;
import com.focusx.entity.TMbActivity;
import com.focusx.entity.dataAnalysis.Activity;
import com.focusx.entity.dataAnalysis.Member;
import com.focusx.entity.dataAnalysis.Menu;
import com.focusx.entity.dataAnalysis.News;
import com.focusx.entity.dataAnalysis.Sign;
import com.focusx.entity.dataAnalysis.WeixinUser;

public interface DataAnalysisDao {

	/**
	 *  根据条件获取粉丝数据分析
	 *  @param groupid
	 *  @return WeixinUser 
	 */
	public WeixinUser getWeixins(Integer groupid);
	
	/**
	 *  根据条件获取粉丝数据分析
	 *  @param data 查询条件
	 *  @return List<WeixinUser>
	 */
	public List<WeixinUser> getWeixins(Map<String, Object> data);
	
	
	
	/**
	 *  统计根据条件获取粉丝数据分析数量
	 *  @param data 查询条件
	 *  @return Integer 数量
	 */
	public Integer getWeixinsCount(Map<String, Object> data);
	
	/**
	 *  根据条件获取图文数据分析
	 *  @param groupid
	 *  @param newsId
	 *  @return News
	 */
	public News getNews(Integer groupid, Integer newsId);
	
	/**
	 *  根据条件获取图文数据分析
	 *  @param data 查询条件
	 *  @return List<News>
	 */
	public List<News> getNewses(Map<String, Object> data);
	
	/**
	 *  统计根据条件获取图文数据分析数量
	 *  @param data 查询条件
	 *  @return Integer 数量
	 */
	public Integer getNewsCount(Map<String, Object> data);
	
	
	
	
	/**
	 *  获取会员绑定昨日指标数据
	 *  @param groupid 分公司ID 
	 *  @return Member
	 */
	public Member getMember(Integer groupid);
	
	/**
	 *  根据条件获取会员绑定数据分析
	 *  @param data 查询条件
	 *  @return List<Member>
	 */
	public List<Member> getMembers(Map<String, Object> data);
	
	/**
	 *  统计根据条件获取会员绑定分析数量
	 *  @param data 查询条件
	 *  @return Integer 数量
	 */
	public Integer getMembersCount(Map<String, Object> data);
	
	/**
	 *  根据分公司ID获取对应数据
	 *  @param groupid  分公司ID
	 *  @param storesId 门店ID
	 *  @return Sign
	 */
	public Sign getSign(Integer groupid, Integer storesId);
	
	/**
	 *  根据条件获取门店签到数据分析
	 *  @param data 查询条件
	 *  @return List<Sign>
	 */
	public List<Sign> getSigns(Map<String, Object> data);
	
	/**
	 *  统计根据条件获取门店签到数据数量
	 *  @param data 查询条件
	 *  @return Integer 数量
	 */
	public Integer getSignsCount(Map<String, Object> data);
	
	/**
	 *   根据分公司ID和活动ID获取对应信息
	 *   @param groupid 分公司ID
	 *   @param activityId 活动ID
	 *   @return Activity 
	 */
	public Activity getActivity(Integer groupid,Integer activityId);
	
	/**
	 *  根据条件获取活动数据分析
	 *  @param data 查询条件
	 *  @return List<Activity>
	 */
	public List<Activity> getActivitys(Map<String, Object> data);
	
	/**
	 *  统计根据条件获取活动数据数量
	 *  @param data 查询条件
	 *  @return Integer 数量
	 */
	public Integer getActivitysCount(Map<String, Object> data);
	
	/**
	 *  根据分公司ID获取对应数据
	 *  @param groupid
	 *  @param eventKey
	 *  @return Menu
	 */
	public Menu getMenu(Integer groupid, String eventKey);
	
	/**
	 *  根据条件获取板块点击数据分析
	 *  @param data 查询条件
	 *  @return List<Menu>
	 */
	public List<Menu> getMenus(Map<String, Object> data);
	
	/**
	 *  统计根据条件获取活动数据数量
	 *  @param data 查询条件
	 *  @return Integer 数量
	 */
	public Integer getMenusCount(Map<String, Object> data);
	
	/**
	 *  根据条件获取会员信息
	 *  @param data 查询条件
	 *  @return List<THdMember>
	 */
	public List<THdMember> getTHdMembers(Map<String, Object> data);
	
	/**
	 *  统计根据条件获取会员数据数量
	 *  @param data 查询条件
	 *  @return Integer 数量
	 */
	public Integer getTHdMembersCount(Map<String, Object> data);
	
	
	public List<List<Integer>> getRecentTwoDays(int groupId);
	
	public List<List<Integer>> getRecentTwoWeeks(int groupId);
	
	public List<List<Integer>> getRecentTwoMonths(int groupId);
	
	
	public List<Object[]>  getWeixinCountProc(int groupId);
	
	
	public List<Object[]>  getNewsCountProc(int groupId,int newsId);
	
	public List<Object[]>  getMemberCountProc(int groupId);
	
	
	public List<Object[]>  getActivityCountProc(int groupId,int activityId,Date startDate,Date endDate);
	
	public List<Object[]>  getSignCountProc(int groupId,int storeId);
	
	
	public List<Object[]>  getMenuCountProc(int groupId);
	
	
	public TMbActivity    getActivityById(int activityId);
	
}
