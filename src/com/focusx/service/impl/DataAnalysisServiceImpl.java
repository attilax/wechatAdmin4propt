package com.focusx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import m.datepkg.LOGGER;

import com.focusx.dao.DataAnalysisDao;
import com.focusx.dao.impl.DataAnalysisDaoImpl;
import com.focusx.entity.THdMember;
import com.focusx.entity.TMbActivity;
import com.focusx.entity.dataAnalysis.Activity;
import com.focusx.entity.dataAnalysis.Member;
import com.focusx.entity.dataAnalysis.Menu;
import com.focusx.entity.dataAnalysis.News;
import com.focusx.entity.dataAnalysis.Sign;
import com.focusx.entity.dataAnalysis.WeixinUser;
import com.focusx.service.DataAnalysisService;
import com.focusx.util.Page;

public class DataAnalysisServiceImpl implements DataAnalysisService{

	private DataAnalysisDao dataAnalysisDao;
	
	public WeixinUser getWeixin(Integer groupid) {
		return dataAnalysisDao.getWeixins(groupid);
	}

	public DataAnalysisDao getDataAnalysisDao() {
		return dataAnalysisDao;
	}

	public void setDataAnalysisDao(DataAnalysisDao dataAnalysisDao) {
		this.dataAnalysisDao = dataAnalysisDao;
	}

	public List<WeixinUser> getWeixins(Page page, Map<String, Object> data) {
		List<WeixinUser> weixinUsers = dataAnalysisDao.getWeixins(data);
		page.setResult(weixinUsers);
		int defaultCount = (Integer) data.get("defaultCount");
		if(defaultCount > 0){
			page.setTotalCount(defaultCount);
		}else {
			int totalcount = dataAnalysisDao.getWeixinsCount(data).intValue();
			page.setTotalCount(totalcount);
		}
		return weixinUsers;
	}
	
	

	public List<WeixinUser> getCsvData(Map<String, Object> data) {
		return dataAnalysisDao.getWeixins(data);
	}

	public News getNews(Integer groupid, Integer newsId) {
		return dataAnalysisDao.getNews(groupid, newsId);
	}

	public List<News> getNewses(Page page, Map<String, Object> data) {
		List<News> newses = dataAnalysisDao.getNewses(data);
		page.setResult(newses);
		int defaultCount = (Integer) data.get("defaultCount");
		if(defaultCount > 0){
			page.setTotalCount(defaultCount);
		}else {
			int totalcount = dataAnalysisDao.getNewsCount(data).intValue();
			page.setTotalCount(totalcount);
		}
		return newses;
	}

	public List<News> getCsvDataToNews(Map<String, Object> data) {
		return dataAnalysisDao.getNewses(data);
	}
	public Member getMember(Integer groupid) {
		return dataAnalysisDao.getMember(groupid);
	}

	public List<Member> getMembers(Page page, Map<String, Object> data) {
		List<Member> members = dataAnalysisDao.getMembers(data);
		page.setResult(members);
		int defaultCount = (Integer) data.get("defaultCount");
		if(defaultCount > 0){
			page.setTotalCount(defaultCount);
		}else {
			int totalcount = dataAnalysisDao.getMembersCount(data).intValue();
			page.setTotalCount(totalcount);
		}
		return members;
	}

	public List<Member> getCsvDataToMember(Map<String, Object> data) {
		return dataAnalysisDao.getMembers(data);
	}

	public Sign getSign(Integer groupid, Integer storesId) {
		return dataAnalysisDao.getSign(groupid, storesId);
	}

	public List<Sign> getSigns(Page page, Map<String, Object> data) {
		List<Sign> signs = dataAnalysisDao.getSigns(data);
		page.setResult(signs);
		int defaultCount = (Integer) data.get("defaultCount");
		if(defaultCount > 0){
			page.setTotalCount(defaultCount);
		}else {
			int totalcount = dataAnalysisDao.getSignsCount(data).intValue();
			page.setTotalCount(totalcount);
		}
		return signs;
	}

	public List<Sign> getCsvDataToSign(Map<String, Object> data) {
		return dataAnalysisDao.getSigns(data);
	}

	public Activity getActivity(Integer groupid, Integer activityId) {
		return dataAnalysisDao.getActivity(groupid, activityId);
	}

	public List<Activity> getActivitys(Page page, Map<String, Object> data) {
		List<Activity> activitys = dataAnalysisDao.getActivitys(data);
		page.setResult(activitys);
		int defaultCount = (Integer) data.get("defaultCount");
		if(defaultCount > 0){
			page.setTotalCount(defaultCount);
		}else {
			int totalcount = dataAnalysisDao.getActivitysCount(data).intValue();
			page.setTotalCount(totalcount);
		}
		return activitys;
	}

	public Menu getMenu(Integer groupid, String eventKey) {
		return dataAnalysisDao.getMenu(groupid, eventKey);
	}

	public List<Menu> getMenus(Page page, Map<String, Object> data) {
		List<Menu> menus = dataAnalysisDao.getMenus(data);
		page.setResult(menus);
		int defaultCount = (Integer) data.get("defaultCount");
		if(defaultCount > 0){
			page.setTotalCount(defaultCount);
		}else {
			int totalcount = dataAnalysisDao.getMenusCount(data).intValue();
			page.setTotalCount(totalcount);
		}
		return menus;
	}

	public List<Menu> getCsvDataToMenu(Map<String, Object> data) {
		return dataAnalysisDao.getMenus(data);
	}

	public List<Activity> getCsvDataToActivity(Map<String, Object> data) {
		return dataAnalysisDao.getActivitys(data);
	}

	public List<THdMember> getTHdMembers(Page page, Map<String, Object> data) {
		List<THdMember> members = dataAnalysisDao.getTHdMembers(data);
		if(page != null){
			page.setResult(members);
			int totalcount = dataAnalysisDao.getTHdMembersCount(data).intValue();
			page.setTotalCount(totalcount);
		}
		return members;
	}
	
	
public Map<String,Integer> getWeixinCount(Map<String, Object> data){
		
		if(!data.containsKey("groupid")){
			return null;
		}
		
		int groupId = Integer.parseInt(data.get("groupid").toString());
		
		List<Object[]>  countList = dataAnalysisDao.getWeixinCountProc(groupId);
		
		Map<String,Integer> countMap = new HashMap<String,Integer>();

		
		if(countList != null && countList.size() > 0){
		
		
			Object obj0 = countList.get(0)[0];
			countMap.put("new_focus_count",Integer.parseInt(obj0.toString()));
			Object obj1 = countList.get(0)[1];
			countMap.put("cancel_focus_count", Integer.parseInt(obj1.toString()));
			Object obj2 = countList.get(0)[2];
			countMap.put("pure_focus_count", Integer.parseInt(obj2.toString()));
			Object obj3 = countList.get(0)[3];
			countMap.put("total_focus_count",Integer.parseInt(obj3.toString()));
			
			
			//昨天
			Object obj4 = countList.get(0)[4];
			countMap.put("new_focus_yesterday",Integer.parseInt(obj4.toString()));
			
			Object obj5 = countList.get(0)[5];
			countMap.put("cancel_focus_yesterday",Integer.parseInt(obj5.toString()));
			
			Object obj6 = countList.get(0)[6];
			countMap.put("pure_focus_yesterday", Integer.parseInt(obj6.toString()));
			
			Object obj7 = countList.get(0)[7];
			countMap.put("total_focus_yesterday", Integer.parseInt(obj7.toString()));
			
			
			//前天
			Object obj8 = countList.get(0)[8];
			countMap.put("new_focus_preyesterday",Integer.parseInt(obj8.toString()));
			
			Object obj9 = countList.get(0)[9];
			countMap.put("cancel_focus_preyesterday", Integer.parseInt(obj9.toString()));
			
			Object obj10 = countList.get(0)[10];
			countMap.put("pure_focus_preyesterday",Integer.parseInt(obj10.toString()));
			
			Object obj11 = countList.get(0)[11];
			countMap.put("total_focus_preyesterday",Integer.parseInt(obj11.toString()));
			
			//上周
			Object obj12 = countList.get(0)[12];
			countMap.put("new_focus_week",Integer.parseInt(obj12.toString()));
			
			Object obj13 = countList.get(0)[13];
			countMap.put("cancel_focus_week", Integer.parseInt(obj13.toString()));
			
			Object obj14 = countList.get(0)[14];
			countMap.put("pure_focus_week", Integer.parseInt(obj14.toString()));
			
			Object obj15 = countList.get(0)[15];
			countMap.put("total_focus_week", Integer.parseInt(obj15.toString()));
	
			
			//前周
			Object obj16 = countList.get(0)[16];
			countMap.put("new_focus_preweek",Integer.parseInt(obj16.toString()));
			
			Object obj17 = countList.get(0)[17];
			countMap.put("cancel_focus_preweek", Integer.parseInt(obj17.toString()));
			
			Object obj18 = countList.get(0)[18];
			countMap.put("pure_focus_preweek", Integer.parseInt(obj18.toString()));
			
			Object obj19 = countList.get(0)[19];
			countMap.put("total_focus_preweek", Integer.parseInt(obj19.toString()));
			
			//上月
			Object obj20 = countList.get(0)[20];
			countMap.put("new_focus_month",Integer.parseInt(obj20.toString()));
			
			Object obj21 = countList.get(0)[21];
			countMap.put("cancel_focus_month", Integer.parseInt(obj21.toString()));
			
			Object obj22 = countList.get(0)[22];
			countMap.put("pure_focus_month", Integer.parseInt(obj22.toString()));
			
			Object obj23 = countList.get(0)[23];
			countMap.put("total_focus_month", Integer.parseInt(obj23.toString()));
			
			//前月
			Object obj24 = countList.get(0)[24];
			countMap.put("new_focus_premonth",Integer.parseInt(obj24.toString()));
			
			Object obj25 = countList.get(0)[25];
			countMap.put("cancel_focus_premonth", Integer.parseInt(obj25.toString()));
			
			Object obj26 = countList.get(0)[26];
			countMap.put("pure_focus_premonth", Integer.parseInt(obj26.toString()));
			
			Object obj27 = countList.get(0)[27];
			countMap.put("total_focus_premonth", Integer.parseInt(obj27.toString()));
			
		}else{
			
		}
		
		return countMap;
	}

	@Override
	public Map<String, Integer> getNewsCount(
			Map<String, Object> data) {
		
		if(!data.containsKey("groupid")){
			return null;
		}
		int newsId = 0;
		if(data.containsKey("newsId")){
			newsId = Integer.parseInt(data.get("newsId").toString());
		}
		
		
		int groupId = Integer.parseInt(data.get("groupid").toString());
		List<Object[]>  countList = dataAnalysisDao.getNewsCountProc(groupId,newsId);
		
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		
		
		if(countList != null && countList.size() > 0){
			
			
			Object obj0 = countList.get(0)[0];
			countMap.put("news_send_count",Integer.parseInt(obj0.toString()));
			Object obj1 = countList.get(0)[1];
			countMap.put("news_people_count", Integer.parseInt(obj1.toString()));
			Object obj2 = countList.get(0)[2];
			countMap.put("news_read_count", Integer.parseInt(obj2.toString()));
			Object obj3 = countList.get(0)[3];
			countMap.put("news_read_people_count",Integer.parseInt(obj3.toString()));
			
			
			Object obj4 = countList.get(0)[4];
			countMap.put("news_send_yesterday",Integer.parseInt(obj4.toString()));
			Object obj5 = countList.get(0)[5];
			countMap.put("news_people_yesterday", Integer.parseInt(obj5.toString()));
			Object obj6 = countList.get(0)[6];
			countMap.put("news_read_yesterday", Integer.parseInt(obj6.toString()));
			Object obj7 = countList.get(0)[7];
			countMap.put("news_read_people_yesterday",Integer.parseInt(obj7.toString()));
			
			
			Object obj8 = countList.get(0)[8];
			countMap.put("news_send_preyesterday",Integer.parseInt(obj8.toString()));
			Object obj9 = countList.get(0)[9];
			countMap.put("news_people_preyesterday", Integer.parseInt(obj9.toString()));
			Object obj10 = countList.get(0)[10];
			countMap.put("news_read_preyesterday", Integer.parseInt(obj10.toString()));
			Object obj11 = countList.get(0)[11];
			countMap.put("news_read_people_preyesterday",Integer.parseInt(obj11.toString()));
			
			
			Object obj12 = countList.get(0)[12];
			countMap.put("news_send_week",Integer.parseInt(obj12.toString()));
			Object obj13 = countList.get(0)[13];
			countMap.put("news_people_week", Integer.parseInt(obj13.toString()));
			Object obj14 = countList.get(0)[14];
			countMap.put("news_read_week", Integer.parseInt(obj14.toString()));
			Object obj15 = countList.get(0)[15];
			countMap.put("news_read_people_week",Integer.parseInt(obj15.toString()));
			
			
			Object obj16 = countList.get(0)[16];
			countMap.put("news_send_preweek",Integer.parseInt(obj16.toString()));
			Object obj17 = countList.get(0)[17];
			countMap.put("news_people_preweek", Integer.parseInt(obj17.toString()));
			Object obj18 = countList.get(0)[18];
			countMap.put("news_read_preweek", Integer.parseInt(obj18.toString()));
			Object obj19 = countList.get(0)[19];
			countMap.put("news_read_people_preweek",Integer.parseInt(obj19.toString()));
			
			
			
			Object obj20 = countList.get(0)[20];
			countMap.put("news_send_month",Integer.parseInt(obj20.toString()));
			Object obj21 = countList.get(0)[21];
			countMap.put("news_people_month", Integer.parseInt(obj21.toString()));
			Object obj22 = countList.get(0)[22];
			countMap.put("news_read_month", Integer.parseInt(obj22.toString()));
			Object obj23 = countList.get(0)[23];
			countMap.put("news_read_people_month",Integer.parseInt(obj23.toString()));
			
			
			
			Object obj24 = countList.get(0)[24];
			countMap.put("news_send_premonth",Integer.parseInt(obj24.toString()));
			Object obj25 = countList.get(0)[25];
			countMap.put("news_people_premonth", Integer.parseInt(obj25.toString()));
			Object obj26 = countList.get(0)[26];
			countMap.put("news_read_premonth", Integer.parseInt(obj26.toString()));
			Object obj27 = countList.get(0)[27];
			countMap.put("news_read_people_premonth",Integer.parseInt(obj27.toString()));
			
			
			Object obj28 = countList.get(0)[28];
			countMap.put("news_read_total",Integer.parseInt(obj28.toString()));
			
			
		}
		
		
		
		return countMap;
	}

	@Override
	public Map<String, Integer> getMembersCount(
			Map<String, Object> data) {
		
		if(!data.containsKey("groupid")){
			return null;
		}
		
		int groupId = Integer.parseInt(data.get("groupid").toString());
		List<Object[]>  countList = dataAnalysisDao.getMemberCountProc(groupId);
		
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		
		if(countList != null && countList.size() > 0){
			
			
			Object obj0 = countList.get(0)[0];
			countMap.put("bind_member_count",Integer.parseInt(obj0.toString()));
			Object obj1 = countList.get(0)[1];
			countMap.put("activity_member_count", Integer.parseInt(obj1.toString()));
			Object obj2 = countList.get(0)[2];
			countMap.put("total_bind_member_count", Integer.parseInt(obj2.toString()));
			
			
			
			Object obj3 = countList.get(0)[3];
			countMap.put("bind_member_yesterday",Integer.parseInt(obj3.toString()));
			Object obj4 = countList.get(0)[4];
			countMap.put("activity_member_yesterday",Integer.parseInt(obj4.toString()));
			Object obj5 = countList.get(0)[5];
			countMap.put("total_bind_member_yesterday",Integer.parseInt(obj5.toString()));
			
			Object obj6 = countList.get(0)[6];
			countMap.put("bind_member_preyesterday",Integer.parseInt(obj6.toString()));
			Object obj7 = countList.get(0)[7];
			countMap.put("activity_member_preyesterday",Integer.parseInt(obj7.toString()));
			Object obj8 = countList.get(0)[8];
			countMap.put("total_bind_member_preyesterday",Integer.parseInt(obj8.toString()));
			
			
			Object obj9 = countList.get(0)[9];
			countMap.put("bind_member_week",Integer.parseInt(obj9.toString()));
			Object obj10 = countList.get(0)[10];
			countMap.put("activity_member_week",Integer.parseInt(obj10.toString()));
			Object obj11 = countList.get(0)[11];
			countMap.put("total_bind_member_week",Integer.parseInt(obj11.toString()));
			
			
			Object obj12 = countList.get(0)[12];
			countMap.put("bind_member_preweek",Integer.parseInt(obj12.toString()));
			Object obj13 = countList.get(0)[13];
			countMap.put("activity_member_preweek",Integer.parseInt(obj13.toString()));
			Object obj14 = countList.get(0)[14];
			countMap.put("total_bind_member_preweek",Integer.parseInt(obj14.toString()));
			
			Object obj15 = countList.get(0)[15];
			countMap.put("bind_member_month",Integer.parseInt(obj15.toString()));
			Object obj16 = countList.get(0)[16];
			countMap.put("activity_member_month",Integer.parseInt(obj16.toString()));
			Object obj17 = countList.get(0)[17];
			countMap.put("total_bind_member_month",Integer.parseInt(obj17.toString()));
			
			
			Object obj18 = countList.get(0)[18];
			countMap.put("bind_member_premonth",Integer.parseInt(obj18.toString()));
			Object obj19 = countList.get(0)[19];
			countMap.put("activity_member_premonth",Integer.parseInt(obj19.toString()));
			Object obj20 = countList.get(0)[20];
			countMap.put("total_bind_member_premonth",Integer.parseInt(obj20.toString()));
			
			
		}
		
		
		
		
		return countMap;
	}

	@Override
	public Map<String, Integer> getSignsCount(
			Map<String, Object> data) {
		
		if(!data.containsKey("groupid")){
			return null;
		}
		
		int groupId = Integer.parseInt(data.get("groupid").toString());
		
		int storeId = Integer.parseInt(data.get("storesId").toString());
		
		List<Object[]>  countList = dataAnalysisDao.getSignCountProc(groupId,storeId);
		
		
		
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		
		
			if(countList != null && countList.size() > 0){
			
			
				Object obj0 = countList.get(0)[0];
				countMap.put("sign_total",Integer.parseInt(obj0.toString()));
				
				
				Object obj1 = countList.get(0)[1];
				countMap.put("sign_yesterday", Integer.parseInt(obj1.toString()));
				Object obj2 = countList.get(0)[2];
				countMap.put("sign_preyesterday", Integer.parseInt(obj2.toString()));
			
				Object obj3 = countList.get(0)[3];
				countMap.put("sign_week", Integer.parseInt(obj3.toString()));
				Object obj4 = countList.get(0)[4];
				countMap.put("sign_preweek", Integer.parseInt(obj4.toString()));
			
				
				Object obj5 = countList.get(0)[5];
				countMap.put("sign_month", Integer.parseInt(obj5.toString()));
				Object obj6 = countList.get(0)[6];
				countMap.put("sign_premonth", Integer.parseInt(obj6.toString()));
				
				
				Object obj7 = countList.get(0)[7];
				countMap.put("sign_count",Integer.parseInt(obj7.toString()));
				
			
			}
		
		
		return countMap;
	}

	@Override
	public Map<String, Integer> getActivitysCount(
			Map<String, Object> data) {
		
		if(!data.containsKey("groupid") || !data.containsKey("activityId")){
			return null;
		}
		
		
		
		int groupId = Integer.parseInt(data.get("groupid").toString());
		
		int activityId = Integer.parseInt(data.get("activityId").toString());
		
		TMbActivity curActivity = dataAnalysisDao.getActivityById(activityId);
		
		List<Object[]>  countList = dataAnalysisDao.getActivityCountProc(groupId,activityId,curActivity.getBeginTime(),curActivity.getEndTime());
		
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		
		
		if(countList != null && countList.size() > 0){
			
			Object obj0 = countList.get(0)[0];
			countMap.put("join_count",Integer.parseInt(obj0.toString()));
			Object obj1 = countList.get(0)[1];
			countMap.put("read_count",Integer.parseInt(obj1.toString()));
			Object obj2 = countList.get(0)[2];
			countMap.put("increase_count",Integer.parseInt(obj2.toString()));
			Object obj3 = countList.get(0)[3];
			countMap.put("member_join_count",Integer.parseInt(obj3.toString()));
			Object obj4 = countList.get(0)[4];
			countMap.put("total_join_count",Integer.parseInt(obj4.toString()));
			
			
			
			Object obj5 = countList.get(0)[5];
			countMap.put("join_yesterday",Integer.parseInt(obj5.toString()));
			Object obj6 = countList.get(0)[6];
			countMap.put("read_yesterday",Integer.parseInt(obj6.toString()));
			Object obj7 = countList.get(0)[7];
			countMap.put("increase_yesterday",Integer.parseInt(obj7.toString()));
			Object obj8 = countList.get(0)[8];
			countMap.put("member_join_yesterday",Integer.parseInt(obj8.toString()));
			Object obj9 = countList.get(0)[9];
			countMap.put("total_join_yesterday",Integer.parseInt(obj9.toString()));
			
			
			Object obj10 = countList.get(0)[10];
			countMap.put("join_preyesterday",Integer.parseInt(obj10.toString()));
			Object obj11 = countList.get(0)[11];
			countMap.put("read_preyesterday",Integer.parseInt(obj11.toString()));
			Object obj12 = countList.get(0)[12];
			countMap.put("increase_preyesterday",Integer.parseInt(obj12.toString()));
			Object obj13 = countList.get(0)[13];
			countMap.put("member_join_preyesterday",Integer.parseInt(obj13.toString()));
			Object obj14 = countList.get(0)[14];
			countMap.put("total_join_preyesterday",Integer.parseInt(obj14.toString()));
			
			
			Object obj15 = countList.get(0)[15];
			countMap.put("join_week",Integer.parseInt(obj15.toString()));
			Object obj16 = countList.get(0)[16];
			countMap.put("read_week",Integer.parseInt(obj16.toString()));
			Object obj17 = countList.get(0)[17];
			countMap.put("increase_week",Integer.parseInt(obj17.toString()));
			Object obj18 = countList.get(0)[18];
			countMap.put("member_join_week",Integer.parseInt(obj18.toString()));
			Object obj19 = countList.get(0)[19];
			countMap.put("total_join_week",Integer.parseInt(obj19.toString()));
			
			
			Object obj20 = countList.get(0)[20];
			countMap.put("join_preweek",Integer.parseInt(obj20.toString()));
			Object obj21 = countList.get(0)[21];
			countMap.put("read_preweek",Integer.parseInt(obj21.toString()));
			Object obj22 = countList.get(0)[22];
			countMap.put("increase_preweek",Integer.parseInt(obj22.toString()));
			Object obj23 = countList.get(0)[23];
			countMap.put("member_join_preweek",Integer.parseInt(obj23.toString()));
			Object obj24 = countList.get(0)[24];
			countMap.put("total_join_preweek",Integer.parseInt(obj24.toString()));
			
			
			
			Object obj25 = countList.get(0)[25];
			countMap.put("join_month",Integer.parseInt(obj25.toString()));
			Object obj26 = countList.get(0)[26];
			countMap.put("read_month",Integer.parseInt(obj26.toString()));
			Object obj27 = countList.get(0)[27];
			countMap.put("increase_month",Integer.parseInt(obj27.toString()));
			Object obj28 = countList.get(0)[28];
			countMap.put("member_join_month",Integer.parseInt(obj28.toString()));
			Object obj29 = countList.get(0)[29];
			countMap.put("total_join_month",Integer.parseInt(obj29.toString()));
			
			
			
			
			Object obj30 = countList.get(0)[30];
			countMap.put("join_premonth",Integer.parseInt(obj30.toString()));
			Object obj31 = countList.get(0)[31];
			countMap.put("read_premonth",Integer.parseInt(obj31.toString()));
			Object obj32 = countList.get(0)[32];
			countMap.put("increase_premonth",Integer.parseInt(obj32.toString()));
			Object obj33 = countList.get(0)[33];
			countMap.put("member_join_premonth",Integer.parseInt(obj33.toString()));
			Object obj34 = countList.get(0)[34];
			countMap.put("total_join_premonth",Integer.parseInt(obj34.toString()));
			
		}
		
		return countMap;
	}

	@Override
	public Map<String, Integer> getMenusCount(
			Map<String, Object> data) {
		
		if(!data.containsKey("groupid")){
			return null;
		}
		
		int groupId = Integer.parseInt(data.get("groupid").toString());
		List<Object[]>  countList = dataAnalysisDao.getMenuCountProc(groupId);
		
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		
		
		if(countList != null && countList.size() > 0){
			
			
			Object obj0 = countList.get(0)[0];
			countMap.put("menu_click_count",Integer.parseInt(obj0.toString()));
			Object obj1 = countList.get(0)[1];
			countMap.put("menu_member_click_count",Integer.parseInt(obj1.toString()));
			Object obj2 = countList.get(0)[2];
			countMap.put("menu_click_total",Integer.parseInt(obj2.toString()));
			
			Object obj3 = countList.get(0)[3];
			countMap.put("menu_click_yesterday",Integer.parseInt(obj3.toString()));
			Object obj4 = countList.get(0)[4];
			countMap.put("menu_member_click_yesterday",Integer.parseInt(obj4.toString()));
			Object obj5 = countList.get(0)[5];
			countMap.put("menu_click_total_yesterday",Integer.parseInt(obj5.toString()));
			
			Object obj6 = countList.get(0)[6];
			countMap.put("menu_click_preyesterday",Integer.parseInt(obj6.toString()));
			Object obj7 = countList.get(0)[7];
			countMap.put("menu_member_click_preyesterday",Integer.parseInt(obj7.toString()));
			Object obj8 = countList.get(0)[8];
			countMap.put("menu_click_total_preyesterday",Integer.parseInt(obj8.toString()));
			
			
			Object obj9 = countList.get(0)[9];
			countMap.put("menu_click_week",Integer.parseInt(obj9.toString()));
			Object obj10 = countList.get(0)[10];
			countMap.put("menu_member_click_week",Integer.parseInt(obj10.toString()));
			Object obj11 = countList.get(0)[11];
			countMap.put("menu_click_total_week",Integer.parseInt(obj11.toString()));
			
			Object obj12 = countList.get(0)[12];
			countMap.put("menu_click_preweek",Integer.parseInt(obj12.toString()));
			Object obj13 = countList.get(0)[13];
			countMap.put("menu_member_click_preweek",Integer.parseInt(obj13.toString()));
			Object obj14 = countList.get(0)[14];
			countMap.put("menu_click_total_preweek",Integer.parseInt(obj14.toString()));
			
			Object obj15 = countList.get(0)[15];
			countMap.put("menu_click_month",Integer.parseInt(obj15.toString()));
			Object obj16 = countList.get(0)[16];
			countMap.put("menu_member_click_month",Integer.parseInt(obj16.toString()));
			Object obj17 = countList.get(0)[17];
			countMap.put("menu_click_total_month",Integer.parseInt(obj17.toString()));
			
			Object obj18 = countList.get(0)[18];
			countMap.put("menu_click_premonth",Integer.parseInt(obj18.toString()));
			Object obj19 = countList.get(0)[19];
			countMap.put("menu_member_click_premonth",Integer.parseInt(obj19.toString()));
			Object obj20 = countList.get(0)[20];
			countMap.put("menu_click_total_premonth",Integer.parseInt(obj20.toString()));

			
		}
		
		
		return countMap;
	}
}
