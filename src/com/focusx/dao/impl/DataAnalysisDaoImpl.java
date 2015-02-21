package com.focusx.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;

import com.focusx.dao.DataAnalysisDao;
import com.focusx.entity.THdMember;
import com.focusx.entity.TMbActivity;
import com.focusx.entity.TMbNewsHistory;
import com.focusx.entity.dataAnalysis.Activity;
import com.focusx.entity.dataAnalysis.Member;
import com.focusx.entity.dataAnalysis.Menu;
import com.focusx.entity.dataAnalysis.News;
import com.focusx.entity.dataAnalysis.Sign;
import com.focusx.entity.dataAnalysis.WeixinUser;
import com.focusx.util.Constant;
import com.focusx.util.DateUtil;

public class DataAnalysisDaoImpl implements DataAnalysisDao{
	
public static SimpleDateFormat sdf_YMd = new SimpleDateFormat("yyyy-MM-dd");
	
	public static SimpleDateFormat sdf_YM = new SimpleDateFormat("yyyy-MM");

	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	//比较两个数的增长比例
	public static double getDouble(Integer dayBeforeYesterday, Integer yesterday){
		int p = (int) Math.pow(10, 1);
		double num_double = 0;
		if(dayBeforeYesterday == 0 && yesterday == 0){
			num_double = (double) 0;
		}else if(dayBeforeYesterday != 0 && yesterday == 0){
			num_double = -100;
		}else if(dayBeforeYesterday == 0 && yesterday != 0){
			num_double = 100;
		}else if(dayBeforeYesterday != 0 && yesterday != 0){
			if(dayBeforeYesterday > yesterday){
				num_double = - (double) ((int) ((double)(dayBeforeYesterday-yesterday)*100/dayBeforeYesterday * p)) / p;
			}else {
				num_double = (double) ((int) ((((double)yesterday-dayBeforeYesterday)*100/dayBeforeYesterday) * p)) / p;
			}
		}
		return num_double;
	}
	
	//图文转化率算法
	public static double getDoubleToNews(Integer one, Integer two){
		int p = (int) Math.pow(10, 1);
		double num_double = 0;
		if(one == 0 || two == 0){
			num_double = (double) 0;
		}else {
			num_double = (double) ((int) (((double)one*100/two) * p)) / p;
		}
		return num_double;
	}
	
	//粉丝分析：昨日指标
	public WeixinUser getWeixins(Integer groupid) {
		String yesterday = DateUtil.getDayBefore(1);
		
		List<Integer> list = getWeixinDataByTime(groupid, yesterday+" 00:00:00", yesterday+" 23:59:59", Constant.ONE);//获取数据
		
		List<Double> dayScale = getAnalysisByDay(list.get(0), list.get(1), list.get(2), groupid); //日概率
		List<Double> weekScale = getAnalysisByWeek(yesterday, groupid);//周概率
		List<Double> monthScale = getAnalysisByMonth(yesterday, groupid);//月概率
		
		List<Double> newsscale = new ArrayList<Double>(); //新关注人数的日、周、月概率
		List<Double> cancelscale = new ArrayList<Double>(); //取消关注人数的日、周、月概率
		List<Double> netscale = new ArrayList<Double>(); //净增关注人数的日、周、月概率
		List<Double> allscale = new ArrayList<Double>(); //累积关注人数的日、周、月概率

		allscale.add(dayScale.get(0));
		newsscale.add(dayScale.get(1));
		cancelscale.add(dayScale.get(2));
		netscale.add(dayScale.get(3));
		
		allscale.add(weekScale.get(0));
		newsscale.add(weekScale.get(1));
		cancelscale.add(weekScale.get(2));
		netscale.add(weekScale.get(3));
		
		allscale.add(monthScale.get(0));
		newsscale.add(monthScale.get(1));
		cancelscale.add(monthScale.get(2));
		netscale.add(monthScale.get(3));
		
		WeixinUser weixinUser = new WeixinUser();
		weixinUser.setNewsubscribe(list.get(1));
		weixinUser.setCancelsubscribe(list.get(2));
		weixinUser.setAllsubscribe(list.get(0));
		weixinUser.setNetsubscribe(list.get(1)-list.get(2));
		weixinUser.setAllscale(allscale);
		weixinUser.setNewsscale(newsscale);
		weixinUser.setCancelscale(cancelscale);
		weixinUser.setNetscale(netscale);
		
		return weixinUser;
	}
	
	//粉丝分析：通用查询方法，时间格式=xxxx-xx-xx，type：1为总，2为分
	public List<Integer> getWeixinDataByTime(Integer groupid, String startTime, String endTime, int type){
		List<Integer> list = new ArrayList<Integer>();
		
		StringBuffer sql = new StringBuffer();
		StringBuffer sql_new = new StringBuffer();
		StringBuffer sql_cancel = new StringBuffer();
		
		sql.append("select COUNT(*)as alls,");
		sql_new.append("(select COUNT(*) from t_mb_weixinuser where subscribe_time between '"+startTime+"' and '"+endTime+"' and subscribe=1 ");
		sql_cancel.append("(select COUNT(*) from t_mb_weixinuser where subscribe_time between '"+startTime+"' and '"+endTime+"' and subscribe=0");
		
		if(groupid > 0){
			sql_new.append(" and groupid="+groupid);
			sql_cancel.append(" and groupid="+groupid);
		}
		sql_new.append(")as news,");
		sql_cancel.append(")as cancel ");
		sql.append(sql_new.toString());
		sql.append(sql_cancel);
		if(type == Constant.ONE){
			sql.append(" from t_mb_weixinuser where subscribe=1 and subscribe_time between '2011-01-21 00:00:00' and '"+endTime+"'");
		}else {
			sql.append(" from t_mb_weixinuser where subscribe=1 and subscribe_time between '"+startTime+"' and '"+endTime+"'");
		}
		if(groupid > 0){
			sql.append(" and groupid="+groupid);
		}
		List<Object> temp = getSession().createSQLQuery(sql.toString()).addScalar("alls", StandardBasicTypes.INTEGER)
				.addScalar("news", StandardBasicTypes.INTEGER).addScalar("cancel", StandardBasicTypes.INTEGER).list(); 
		if(temp != null){
			Object[] object = (Object[])temp.get(0);
			list.add((Integer)object[0]);
			list.add((Integer)object[1]);
			list.add((Integer)object[2]);
		}
		return list;
	}

	//粉丝分析：日
	public List<Double> getAnalysisByDay(Integer all, Integer news, Integer cancel, Integer groupid){
		List<Double> list = new ArrayList<Double>();
		String dayBeforeYesterday = DateUtil.getDayBefore(2);//前天
		
		List<Integer> result = getWeixinDataByTime(groupid, dayBeforeYesterday+" 00:00:00", dayBeforeYesterday+" 23:59:59", Constant.ONE);//获取数据
		
		Integer net_before = result.get(1)-result.get(2);
		Integer net = news-cancel;
		
		list.add(getDouble(result.get(0), all));
		list.add(getDouble(result.get(1), news));
		list.add(getDouble(result.get(2), cancel));
		list.add(getDouble(net_before, net));
		return list;
	}
	
	//粉丝分析：周
	public List<Double>  getAnalysisByWeek(String yesterday, Integer groupid){
		List<String> day = DateUtil.getWeekMonSun();
		
		List<Integer> result = getWeixinDataByTime(groupid, day.get(2), day.get(3), Constant.TWO);//上周
		List<Integer> result_before = getWeixinDataByTime(groupid, day.get(0), day.get(1), Constant.TWO);//上上周
		
		Integer net_now = result.get(1) - result.get(2);
		Integer net_before = result_before.get(1)-result_before.get(2);
		
		List<Double> list = new ArrayList<Double>();
		list.add(getDouble(result_before.get(0), result.get(0)));
		list.add(getDouble(result_before.get(1), result.get(1)));
		list.add(getDouble(result_before.get(2), result.get(2)));
		list.add(getDouble(net_before, net_now));
		return list;
	}
	
	//粉丝分析：月
	public List<Double> getAnalysisByMonth(String yesterday, Integer groupid){
		List<String> months = DateUtil.getMonth();
		
		List<Integer> result = getWeixinDataByTime(groupid, months.get(2), months.get(3), Constant.TWO);//上月
		List<Integer> result_before = getWeixinDataByTime(groupid, months.get(0), months.get(1), Constant.TWO);//上上月
		
		Integer net_before = result_before.get(1) - result_before.get(2);
		Integer net = result.get(1) - result.get(2);
		
		List<Double> list = new ArrayList<Double>();
		list.add(getDouble(result_before.get(0), result.get(0)));
		list.add(getDouble(result_before.get(1), result.get(1)));
		list.add(getDouble(result_before.get(2), result.get(2)));
		list.add(getDouble(net_before, net));
		return list;
	}
	
	//粉丝分析：列表、导出csv
	public List<WeixinUser> getWeixins(Map<String, Object> data) {
		//查询条件
		Integer weixinDateType = (Integer)data.get("weixinDateType");
		Integer groupid = (Integer)data.get("groupid");
		String weixinStartTime = (String)data.get("weixinStartTime");
		String weixinEndTime = (String)data.get("weixinEndTime");
		int defaultCount = (Integer) data.get("defaultCount");
		List<String> days = (List<String>) data.get("days");
		
		//分页条件
		int pageNumber = (Integer)data.get("pageNumber");
		int pageSize = (Integer)data.get("pageSize");
		
		List<WeixinUser> list = new ArrayList<WeixinUser>();
		
		if(defaultCount > 0){//默认状态下显示前30天记录
			list = getWeixinsDefault(days, groupid);
		}else {
			if(weixinDateType == 0 && !weixinStartTime.equals("") && !weixinEndTime.equals("")){//自定义时间
				list = getWeixinsCustom(weixinStartTime, weixinEndTime, groupid, pageNumber, pageSize);
				
			}else if(weixinDateType == 1){//周
				list = getWeixinsWeek(groupid, pageNumber, pageSize);
				
			}else if(weixinDateType == 2){//月
				list = getWeixinsMonth(weixinStartTime, weixinEndTime, groupid, pageNumber, pageSize);
			}
		}
	    return list;
	}

	//粉丝分析：列表数量
	public Integer getWeixinsCount(Map<String, Object> data) {
		//查询条件
		Integer weixinDateType = (Integer)data.get("weixinDateType");
		String weixinStartTime = (String)data.get("weixinStartTime");
		String weixinEndTime = (String)data.get("weixinEndTime");
		
		int count = 0;
		
		if(weixinDateType == 0 && !weixinStartTime.equals("") && !weixinEndTime.equals("")){//自定义时间
			count = DateUtil.getDayCount(weixinStartTime, weixinEndTime);
		}else if(weixinDateType == 1){//周
			if(Constant.weixinuser != null){
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE,   -1);
				count = DateUtil.getWeekCount(Constant.weixinuser.getSubscribeTime(), cal.getTime()) + 2;
			}
		}else if(weixinDateType == 2){//月
			if(Constant.weixinuser != null){
		        String start = DateUtil.sdf_YMd.format(Constant.weixinuser.getSubscribeTime());
		        String end = DateUtil.sdf_YMd.format(new Date());
				count = DateUtil.getMonthCount(start, end);
			}
		}
		return count;
	}

    //粉丝数据：默认情况
	public List<WeixinUser> getWeixinsDefault(List<String> days, Integer groupid){
		List<WeixinUser> list = new ArrayList<WeixinUser>();
		int days_size = days.size();
		for(int i = 0; i < days_size; i++){
			StringBuffer sql_new = new StringBuffer();
			StringBuffer sql_cancel = new StringBuffer();
			String day = days.get(i);
			sql_new.append("select count(*)as new from t_mb_weixinuser where subscribe=1 ");
			sql_cancel.append("select count(*)as cancel from t_mb_weixinuser where subscribe=0 ");
			
			if(groupid > 0){
				sql_new.append(" and groupid="+groupid);
				sql_cancel.append(" and groupid="+groupid);
			}
			
			if(day != null){
				StringBuffer start = new StringBuffer();
				start.append(day+" 00:00:00");
				StringBuffer end = new StringBuffer();
				end.append(day+" 23:59:59");
				sql_new.append(" and subscribe_time between '"+start.toString()+"' and '"+end.toString()+"'");
				sql_cancel.append(" and subscribe_time between '"+start.toString()+"' and '"+end.toString()+"'");
			}
			Query query_new = getSession().createSQLQuery(sql_new.toString()).addScalar("new", StandardBasicTypes.INTEGER);
			Query query_cancel = getSession().createSQLQuery(sql_cancel.toString()).addScalar("cancel", StandardBasicTypes.INTEGER);
			int count_new = (Integer) query_new.uniqueResult();
			int cancel = (Integer) query_cancel.uniqueResult();
					
			StringBuffer time = new StringBuffer();
			time.append(day+" 23:59:59");
			StringBuffer sql_all = new StringBuffer();
			//sql_all.append("select COUNT(*)as alls from t_mb_weixinuser where subscribe=1 and subscribe_time between '2011-01-21 00:00:00' and '"+time.toString()+"'");
			sql_all.append("select COUNT(*)as alls from t_mb_weixinuser where subscribe=1 and subscribe_time <= '"+time.toString()+"'");
			if(groupid > 0){
				sql_all.append(" and groupid="+groupid);
			}
			Query query_all = getSession().createSQLQuery(sql_all.toString());
					
			WeixinUser weixinUser = new WeixinUser();
			weixinUser.setNewsubscribe(count_new);
			weixinUser.setCancelsubscribe(cancel);
			weixinUser.setNetsubscribe(count_new-cancel);
			weixinUser.setTime(day);
		    weixinUser.setAllsubscribe((Integer)query_all.uniqueResult());
			list.add(weixinUser);
		}
		return list;
	}

	//粉丝数据：自定义时间
	public List<WeixinUser> getWeixinsCustom(String weixinStartTime, String weixinEndTime, Integer groupid, int pageNumber, int pageSize){
		List<WeixinUser> list = new ArrayList<WeixinUser>();
		StringBuffer sql_new = new StringBuffer();
		StringBuffer sql_cancel = new StringBuffer();
		List<String> dayList;
		if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
			dayList = DateUtil.getDays(weixinStartTime, weixinEndTime, Constant.ZERO, Constant.ZERO);
		}else {
			dayList = DateUtil.getDays(weixinStartTime, weixinEndTime, pageNumber, pageSize);
		}
		
		if(dayList != null && dayList.size() > 0){
			String start = dayList.get(0);
			String end = dayList.get(dayList.size()-1);
			
			sql_new.append("select count(*)as new, CONVERT(varchar(10),subscribe_time,23) as subscribe_time from t_mb_weixinuser " +
					"where subscribe=1 and subscribe_time between '"+start+" 00:00:00' and '"+end+" 23:59:59'");
			
			sql_cancel.append("select count(*)as cancel, CONVERT(varchar(10),subscribe_time,23) as subscribe_time from t_mb_weixinuser " +
					"where subscribe=0 and subscribe_time between '"+start+" 00:00:00' and '"+end+" 23:59:59'");
			
			if(groupid > 0){
				sql_new.append(" and groupid="+groupid);
				sql_cancel.append(" and groupid="+groupid);
			}
			
			sql_new.append("group by CONVERT(varchar(10),subscribe_time,23) order by CONVERT(varchar(10),subscribe_time,23) desc");
			sql_cancel.append("group by CONVERT(varchar(10),subscribe_time,23) order by CONVERT(varchar(10),subscribe_time,23) desc");
			
			Query query_new = getSession().createSQLQuery(sql_new.toString()).addScalar("new", StandardBasicTypes.INTEGER)
					.addScalar("subscribe_time", StandardBasicTypes.STRING);
			Query query_cancel = getSession().createSQLQuery(sql_cancel.toString()).addScalar("cancel", StandardBasicTypes.INTEGER)
					.addScalar("subscribe_time", StandardBasicTypes.STRING);
			List<Object> temp_new = query_new.list();
			List<Object> temp_cancel = query_cancel.list();
			
			for(int i = 0; i < dayList.size(); i++){
				String day = dayList.get(i);
				WeixinUser weixinUser = new WeixinUser();
				weixinUser.setTime(day);
				weixinUser.setNewsubscribe(0);
				weixinUser.setCancelsubscribe(0);
				if(temp_new != null){
					for(int j =0; j < temp_new.size(); j++){
						Object[] object = (Object[])temp_new.get(j);
						String time = (String)object[1];
						if(time.equals(day)){
							weixinUser.setNewsubscribe((Integer)object[0]);
						}
					}
				}
				if(temp_cancel != null){
					for(int j =0; j < temp_cancel.size(); j++){
						Object[] object = (Object[])temp_cancel.get(j);
						String time = (String)object[1];
						if(time.equals(day)){
							weixinUser.setCancelsubscribe((Integer)object[0]);
						}
					}
				}
				
				//String sql_all = "select COUNT(*)as alls from t_mb_weixinuser where subscribe=1 and subscribe_time between '2000-01-01 00:00:00' and '"+day+" 23:59:59'";
				String sql_all = "select COUNT(*)as alls from t_mb_weixinuser where subscribe=1 and subscribe_time  <= '"+day+" 23:59:59'";
				if(groupid > 0){
					sql_all+=" and groupid="+groupid;
				}
				Query query_all = getSession().createSQLQuery(sql_all);
					
				weixinUser.setNetsubscribe(weixinUser.getNewsubscribe()-weixinUser.getCancelsubscribe());
				weixinUser.setAllsubscribe((Integer)query_all.uniqueResult());
				list.add(weixinUser);
		}
      }
	  return list;	
	}

	//粉丝数据：月份数据
	public List<WeixinUser> getWeixinsMonth(String weixinStartTime, String weixinEndTime, Integer groupid, int pageNumber, int pageSize){
		List<WeixinUser> list = new ArrayList<WeixinUser>();
		StringBuffer sql_new = new StringBuffer();
		StringBuffer sql_cancel = new StringBuffer();
		
        String starTime = DateUtil.sdf_YMd.format(Constant.weixinuser.getSubscribeTime());
        String endTime = DateUtil.sdf_YMd.format(new Date());
        
        List<String> months;
        if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
        	months = DateUtil.getMonths(starTime, endTime, Constant.ZERO, Constant.ZERO);
        }else {
        	months = DateUtil.getMonths(starTime, endTime, pageNumber, pageSize);
        }
		
		sql_new.append("select count(*)as new, CONVERT(varchar(7), subscribe_time, 120) as subscribe_time from t_mb_weixinuser " +
				"where subscribe=1 ");
		sql_cancel.append("select  count(*)as cancel, CONVERT(varchar(7), subscribe_time, 120) as subscribe_time from t_mb_weixinuser " +
				"where subscribe=0 ");

		if(groupid > 0){
			sql_new.append(" and groupid="+groupid);
			sql_cancel.append(" and groupid="+groupid);
		}
		
		sql_new.append(" group by CONVERT(varchar(7), subscribe_time, 120) order by CONVERT(varchar(7), subscribe_time, 120) desc");
		sql_cancel.append(" group by CONVERT(varchar(7), subscribe_time, 120) order by CONVERT(varchar(7), subscribe_time, 120) desc");
		
		Query query_new = getSession().createSQLQuery(sql_new.toString()).addScalar("new", StandardBasicTypes.INTEGER)
				.addScalar("subscribe_time", StandardBasicTypes.STRING);
		Query query_cancel = getSession().createSQLQuery(sql_cancel.toString()).addScalar("cancel", StandardBasicTypes.INTEGER)
				.addScalar("subscribe_time", StandardBasicTypes.STRING);
		List<Object> temp_new = query_new.list();
		List<Object> temp_cancel = query_cancel.list();
		
		for(int i = 0; i < months.size(); i++){
			String[] values = months.get(i).split("=");
			String YYMM = null;
			WeixinUser weixinUser = new WeixinUser();
			try {
				YYMM = DateUtil.sdf_YM.format(DateUtil.sdf_YM.parse(values[0]));
				weixinUser.setTime(DateUtil.sdf_YM_China.format(DateUtil.sdf_YM.parse(values[0])));
				weixinUser.setNewsubscribe(0);
				weixinUser.setCancelsubscribe(0);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(temp_new != null){
				for(int j =0; j < temp_new.size(); j++){
					Object[] object = (Object[])temp_new.get(j);
					String time = (String)object[1];
					if(time.equals(YYMM)){
						weixinUser.setNewsubscribe((Integer)object[0]);
					}
				}
			}
			if(temp_cancel != null){
				for(int j =0; j < temp_cancel.size(); j++){
					Object[] object = (Object[])temp_cancel.get(j);
					String time = (String)object[1];
					if(time.equals(YYMM)){
						weixinUser.setCancelsubscribe((Integer)object[0]);
					}
				}
			}
			
			//String sql_all = "select COUNT(*)as alls from t_mb_weixinuser where subscribe=1 and subscribe_time between '2000-01-01 00:00:00' and '"+values[1]+"'";
			String sql_all = "select COUNT(*)as alls from t_mb_weixinuser where subscribe=1 and subscribe_time <= '"+values[1]+"'";
			if(groupid > 0){
				sql_all+=" and groupid="+groupid;
			}
			Query query_all = getSession().createSQLQuery(sql_all);
				
			weixinUser.setNetsubscribe(weixinUser.getNewsubscribe()-weixinUser.getCancelsubscribe());
			weixinUser.setAllsubscribe((Integer)query_all.uniqueResult());
			list.add(weixinUser);
	    }
		return list;
	}

	//粉丝数据：周数据
	public List<WeixinUser> getWeixinsWeek(Integer groupid, int pageNumber, int pageSize){
		List<WeixinUser> list = new ArrayList<WeixinUser>();
		
		List<String> weeks = DateUtil.getWeeks(DateUtil.sdf_YMd.format(Constant.weixinuser.getSubscribeTime()), pageNumber, pageSize);//获取周
		
		if(weeks != null && weeks.size() > 0){
			for(String day : weeks){
				StringBuffer sql_new = new StringBuffer();
				StringBuffer sql_cancel = new StringBuffer();
				StringBuffer sql_all = new StringBuffer();
				String[] days = day.split("=");
				day = day.replace("=", "至");
				WeixinUser weixinUser = new WeixinUser();
				weixinUser.setTime(day);
				
				sql_new.append("select COUNT(*)as new from t_mb_weixinuser where subscribe=1 and subscribe_time between '"+days[0]+" 00:00:00' and '"+days[1]+" 23:59:59'");
				sql_cancel.append("select COUNT(*)as cancel from t_mb_weixinuser where subscribe=0 and subscribe_time between '"+days[0]+" 00:00:00' and '"+days[1]+" 23:59:59'");
				//sql_all.append("select COUNT(*)as alls from t_mb_weixinuser where subscribe=1 and subscribe_time between '2000-01-01 00:00:00' and '"+days[1]+" 23:59:59'");
				
				sql_all.append("select COUNT(*)as alls from t_mb_weixinuser where subscribe=1 and subscribe_time <= '"+days[1]+" 23:59:59'");
				
				if(groupid > 0){
					sql_new.append(" and groupid="+groupid);
					sql_cancel.append(" and groupid="+groupid);
					sql_all.append(" and groupid="+groupid);
				}
				
				Integer news = (Integer) getSession().createSQLQuery(sql_new.toString()).uniqueResult();
				Integer cancel = (Integer) getSession().createSQLQuery(sql_cancel.toString()).uniqueResult();
				Integer net = news - cancel;
				Integer all = (Integer) getSession().createSQLQuery(sql_all.toString()).uniqueResult();
				
				weixinUser.setNewsubscribe(news);
				weixinUser.setCancelsubscribe(cancel);
				weixinUser.setNetsubscribe(net);
				weixinUser.setAllsubscribe(all);
				list.add(weixinUser);
			}
		}
		return list;
	}
	
	//图文分析：昨日指标
	public News getNews(Integer groupid, Integer newsId) {
		String yesterday = DateUtil.getDayBefore(1);
		
		List<Integer> result = getNewsDataByTime(groupid, yesterday+" 00:00:00", yesterday+" 23:59:59", yesterday+" 23:59:59", newsId);
		
		Integer deliveryTime = 0; 
		Integer readingTime = 0; 
		Integer deliveryWeixin = 0;
		Integer readingWeixin = 0;
		Integer readingCount = 0;
		if(result != null){
			deliveryTime = result.get(0); 
			readingTime = result.get(1); 
			deliveryWeixin = result.get(2);
			readingWeixin = result.get(3);
			readingCount = result.get(4);
		}
		
		List<Double> dayScale = getAnalysisByDayToNews(deliveryTime, readingTime, deliveryWeixin, readingWeixin, groupid, newsId); //日概率
		List<Double> weekScale = getAnalysisByWeekToNews(yesterday, groupid, newsId);//周概率
		List<Double> monthScale = getAnalysisByMonthToNews(yesterday, groupid, newsId);//月概率
		
	    List<Double> deliveryscale = new ArrayList<Double>(); //图文送达次数的日、周、月概率
		List<Double> readingscale = new ArrayList<Double>();  //阅读次数的日、周、月概率
		List<Double> conversionRatescale = new ArrayList<Double>(); //图文转化率的日、周、月概率
		List<Double> deliveryWeixinscale = new ArrayList<Double>(); //图文送达人数的日、周、月概率
		List<Double> readingWeixinscale = new ArrayList<Double>();     //阅读人数的日、周、月概率
		
		deliveryscale.add(dayScale.get(0));
		readingscale.add(dayScale.get(1));
		deliveryWeixinscale.add(dayScale.get(2));
		readingWeixinscale.add(dayScale.get(3));
		
		deliveryscale.add(weekScale.get(0));
		readingscale.add(weekScale.get(1));
		deliveryWeixinscale.add(weekScale.get(2));
		readingWeixinscale.add(weekScale.get(3));
		
		deliveryscale.add(monthScale.get(0));
		readingscale.add(monthScale.get(1));
		deliveryWeixinscale.add(monthScale.get(2));
		readingWeixinscale.add(monthScale.get(3));
		
		News news = new News();
		news.setDeliveryTime(deliveryTime);
		news.setReadingTime(readingTime);
		news.setReadingWeixin(readingWeixin);
		news.setDeliveryWeixin(deliveryWeixin);
		news.setReadingCount(readingCount);
		news.setConversionRate(getDoubleToNews(readingWeixin, deliveryWeixin));
		
		news.setDeliveryscale(deliveryscale);
		news.setReadingscale(readingscale);
		news.setDeliveryWeixinscale(deliveryWeixinscale);
		news.setConversionRatescale(conversionRatescale);
		news.setReadingWeixinscale(readingWeixinscale);
		return news;
	}
	
	//图文分析：通用查询方法，时间格式=xxxx-xx-xx
	public List<Integer> getNewsDataByTime(Integer groupid, String startTime, String endTime, String time, Integer newsId){
		List<Integer> result = new ArrayList<Integer>();
		
		StringBuffer sql_deliveryTime = new StringBuffer();
		StringBuffer sql_deliveryWeixin = new StringBuffer();
		StringBuffer sql_readingTime = new StringBuffer();
		StringBuffer sql_readingweixin = new StringBuffer();
		
		String head = "select COUNT(distinct d1.openid) as readingCount,";
		StringBuffer tail = new StringBuffer();
		tail.append("from t_mb_news_history d1 where d1.operType=1");
		if(time != null){
			tail.append(" and d1.operTime <='"+time+"'");
		}
		
		sql_deliveryTime.append("(select COUNT(*) from t_mb_news_history d1 where d1.operType=2 and d1.operTime between '"+startTime+"' and '"+endTime+"'");
		sql_readingTime.append("(select COUNT(*) from t_mb_news_history d1 where d1.operType=1 and d1.operTime between '"+startTime+"' and '"+endTime+"'");
		sql_readingweixin.append("(select COUNT(distinct openid) from t_mb_news_history d1 where d1.operType=1 and d1.operTime between '"+startTime+"' and '"+endTime+"'");
		sql_deliveryWeixin.append("(select COUNT(distinct openid) from t_mb_news_history d1 where d1.operType=2 and d1.operTime between '"+startTime+"' and '"+endTime+"'");
		
		if(groupid > 0){
			sql_deliveryTime.append(" and d1.openid in(select d2.openid from t_mb_weixinuser d2 where d2.openid=d1.openid and d2.groupid="+groupid+")");
			sql_readingTime.append(" and d1.openid in(select d2.openid from t_mb_weixinuser d2 where d2.openid=d1.openid and d2.groupid="+groupid+")");
			sql_readingweixin.append(" and d1.openid in(select d2.openid from t_mb_weixinuser d2 where d2.openid=d1.openid and d2.groupid="+groupid+")");
			sql_deliveryWeixin.append(" and d1.openid in(select d2.openid from t_mb_weixinuser d2 where d2.openid=d1.openid and d2.groupid="+groupid+")");
			tail.append(" and d1.openid in(select d2.openid from t_mb_weixinuser d2 where d2.openid=d1.openid and d2.groupid="+groupid+")");
		}
		if(newsId > 0){
			sql_deliveryTime.append(" and d1.newsId in(select ID from t_mb_news where parentId="+newsId+" and state=0 or ID="+newsId+")");
			sql_readingTime.append(" and d1.newsId in(select ID from t_mb_news where parentId="+newsId+" and state=0 or ID="+newsId+")");
			sql_readingweixin.append(" and d1.newsId in(select ID from t_mb_news where parentId="+newsId+" and state=0 or ID="+newsId+")");
			sql_deliveryWeixin.append(" and d1.newsId in(select ID from t_mb_news where parentId="+newsId+" and state=0 or ID="+newsId+")");
			tail.append(" and d1.newsId in(select ID from t_mb_news where parentId="+newsId+" and state=0 or ID="+newsId+")");
		}
		sql_deliveryTime.append(")as deliveryTime,");
		sql_readingTime.append(")as readingTime,");
		sql_readingweixin.append(")as readingweixin,");
		sql_deliveryWeixin.append(")as deliveryWeixin ");
		
		String sql = head + sql_deliveryTime.toString() + sql_readingTime.toString() + sql_readingweixin.toString() + sql_deliveryWeixin.toString() + tail.toString();
		Query query = getSession().createSQLQuery(sql.toString()).addScalar("readingCount", StandardBasicTypes.INTEGER)
				.addScalar("deliveryTime", StandardBasicTypes.INTEGER).addScalar("readingTime", StandardBasicTypes.INTEGER)
				.addScalar("readingweixin", StandardBasicTypes.INTEGER).addScalar("deliveryWeixin", StandardBasicTypes.INTEGER);
		List temp = query.list();
		if(temp != null && temp.size() > 0){
			for(int i =0; i < temp.size(); i++){
				Object[] object = (Object[])temp.get(i);
				result.add((Integer)object[1]); //图文送达次数
				result.add((Integer)object[2]); //阅读次数
				result.add((Integer)object[4]); //图文送达人数
				result.add((Integer)object[3]); //阅读人数
				result.add((Integer)object[0]); //阅读总人数
			}
		}else {
			result.add(0); 
			result.add(0); 
			result.add(0); 
			result.add(0); 
			result.add(0); 
		}
		return result;
	}
	
	//图文分析：日
	public List<Double> getAnalysisByDayToNews(Integer deliveryTime, Integer readingTime, Integer deliveryWeixin, 
			Integer readingWeixin ,Integer groupid, Integer newsId){
		List<Double> list = new ArrayList<Double>();
		
		String dayBeforeYesterday = DateUtil.getDayBefore(2);//前天
		
		List<Integer> result = getNewsDataByTime(groupid, dayBeforeYesterday+" 00:00:00", dayBeforeYesterday+" 23:59:59", null, newsId);
		if(result != null){
			list.add(getDouble(result.get(0), deliveryTime));
			list.add(getDouble(result.get(1), readingTime));
			list.add(getDouble(result.get(2), deliveryWeixin));
			list.add(getDouble(result.get(3), readingWeixin));
		}
		return list;
	}

	//图文分析：周
	public List<Double> getAnalysisByWeekToNews(String yesterday, Integer groupid, Integer newsId){
		List<Double> list = new ArrayList<Double>();
		
		List<String> day = DateUtil.getWeekMonSun();
		
		List<Integer> result = getNewsDataByTime(groupid, day.get(2), day.get(3), null, newsId);
		
		List<Integer> result_before = getNewsDataByTime(groupid, day.get(0), day.get(1), null, newsId);
		
		if(result != null && result_before != null){
			list.add(getDouble(result_before.get(0), result.get(0)));
			list.add(getDouble(result_before.get(1), result.get(1)));
			list.add(getDouble(result_before.get(2), result.get(2)));
			list.add(getDouble(result_before.get(3), result.get(3)));
		}
		return list;
	}

	//图文分析：月
	public List<Double> getAnalysisByMonthToNews(String yesterday, Integer groupid, Integer newsId){
		List<String> months = DateUtil.getMonth();
		
		//当前月
		List<Integer> result = getNewsDataByTime(groupid, months.get(2), months.get(3), null, newsId);
		
		//上个月
		List<Integer> result_before = getNewsDataByTime(groupid, months.get(0), months.get(1), null, newsId);
		
		List<Double> list = new ArrayList<Double>();
		
		if(result != null && result_before != null){
			list.add(getDouble(result_before.get(0), result.get(0)));
			list.add(getDouble(result_before.get(1), result.get(1)));
			list.add(getDouble(result_before.get(2), result.get(2)));
			list.add(getDouble(result_before.get(3), result.get(3)));
		}
		return list;
	}

	//图文分析：列表、导出csv
	public List<News> getNewses(Map<String, Object> data) {
		//查询条件
		Integer dateType = (Integer)data.get("dateType");
		Integer groupid = (Integer)data.get("groupid");
		String newsStartTime = (String)data.get("newsStartTime");
		String newsEndTime = (String)data.get("newsEndTime");
		List<String> days = (List<String>) data.get("days");
		int defaultCount = (Integer) data.get("defaultCount");
		Integer newsId = (Integer) data.get("newsId");
		
		//分页条件
		int pageNumber = (Integer)data.get("pageNumber");
		int pageSize = (Integer)data.get("pageSize");
		
		List<News> result = new ArrayList<News>();
		
		//先判断是否默认状态
		if(defaultCount > 0){
			result = getWeixinsDefaultToNews(days, groupid, newsId);
		}else {
			if(dateType == 1){//周
				result = getWeixinsWeekToNews(groupid, pageNumber, pageSize, newsId);
		    }else if(dateType == 2){//月
		    	result = getWeixinsMonthToNews(newsStartTime, newsEndTime, groupid, pageNumber, pageSize, newsId);
		   }else if(dateType == 0 && !newsStartTime.equals("") && !newsEndTime.equals("")){//自定义时间
			   result = getWeixinsCustomToNews(newsStartTime, newsEndTime, groupid, pageNumber, pageSize, newsId);
		   }
	    }
		return result;
	}
	
    //图文分析：默认情况
	public List<News> getWeixinsDefaultToNews(List<String> days, Integer groupid, Integer newsId){
		List<News> list = new ArrayList<News>();
		int days_size = days.size();
		for(int i = 0; i < days_size; i++){		
			String day = days.get(i);
			if(day != null){
				List<Integer> result = getNewsDataByTime(groupid, day+" 00:00:00", day+" 23:59:59", null, newsId);
				
				News news = new News();
				news.setTime(day);
				news.setDeliveryTime(result.get(0));
				news.setReadingTime(result.get(1));
				news.setDeliveryWeixin(result.get(2));
				news.setReadingWeixin(result.get(3));
				news.setConversionRate(getDoubleToNews(result.get(3), result.get(2)));
				list.add(news);
			}
		}
		return list;
	}

	//图文分析：自定义时间
	public List<News> getWeixinsCustomToNews(String newsStartTime, String newsEndTime, Integer groupid, int pageNumber, int pageSize, Integer newsId){
		List<News> list = new ArrayList<News>();
		
		StringBuffer sql_deliveryTime = new StringBuffer();
		StringBuffer sql_readingTime = new StringBuffer();
		StringBuffer sql_deliveryWeixin = new StringBuffer();
		StringBuffer sql_readingWeixin = new StringBuffer();
		
		List<String> dayList;
		if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
			dayList = DateUtil.getDays(newsStartTime, newsEndTime, Constant.ZERO, Constant.ZERO);
		}else {
			dayList = DateUtil.getDays(newsStartTime, newsEndTime, pageNumber, pageSize);
		}
		if(dayList != null && dayList.size() > 0){
			String start = dayList.get(0);
			String end = dayList.get(dayList.size()-1);
			
			sql_deliveryTime.append("select count(*) as deliveryTime,CONVERT(varchar(10), d1.operTime, 23) as operTime from t_mb_news_history d1 " +
					"where d1.operType=2 and d1.operTime between '"+start+" 00:00:00' and '"+end+" 23:59:59'");
			sql_readingTime.append("select count(*) as readingTime,CONVERT(varchar(10), d1.operTime, 23) as operTime from t_mb_news_history d1 " +
					"where d1.operType=1 and d1.operTime between '"+start+" 00:00:00' and '"+end+" 23:59:59'");
			sql_deliveryWeixin.append("select count(distinct openid) as deliveryWeixin,CONVERT(varchar(10), d1.operTime, 23) as operTime from t_mb_news_history d1 " +
					"where d1.operType=2 and d1.operTime between '"+start+" 00:00:00' and '"+end+" 23:59:59'");
			sql_readingWeixin.append("select count(distinct openid) as readingWeixin,CONVERT(varchar(10), d1.operTime, 23) as operTime from t_mb_news_history d1 " +
					"where d1.operType=1 and d1.operTime between '"+start+" 00:00:00' and '"+end+" 23:59:59'");
			
			if(groupid > 0){
				sql_deliveryTime.append(" and d1.openid in(select d2.openid from t_mb_weixinuser d2 where d2.openid=d1.openid and d2.groupid="+groupid+")");
				sql_readingTime.append(" and d1.openid in(select d2.openid from t_mb_weixinuser d2 where d2.openid=d1.openid and d2.groupid="+groupid+")");
				sql_deliveryWeixin.append(" and d1.openid in(select d2.openid from t_mb_weixinuser d2 where d2.openid=d1.openid and d2.groupid="+groupid+")");
				sql_readingWeixin.append(" and d1.openid in(select d2.openid from t_mb_weixinuser d2 where d2.openid=d1.openid and d2.groupid="+groupid+")");
			}
			if(newsId > 0){
				sql_deliveryTime.append(" and d1.newsId in(select ID from t_mb_news where parentId="+newsId+" and state=0 or ID="+newsId+")");
				sql_readingTime.append(" and d1.newsId in(select ID from t_mb_news where parentId="+newsId+" and state=0 or ID="+newsId+")");
				sql_deliveryWeixin.append(" and d1.newsId in(select ID from t_mb_news where parentId="+newsId+" and state=0 or ID="+newsId+")");
				sql_readingWeixin.append(" and d1.newsId in(select ID from t_mb_news where parentId="+newsId+" and state=0 or ID="+newsId+")");
			}
			sql_deliveryTime.append(" group by CONVERT(varchar(10),d1.operTime,23) order by CONVERT(varchar(10),d1.operTime,23) desc");
			sql_readingTime.append(" group by CONVERT(varchar(10),d1.operTime,23) order by CONVERT(varchar(10),d1.operTime,23) desc");
			sql_deliveryWeixin.append(" group by CONVERT(varchar(10),d1.operTime,23) order by CONVERT(varchar(10),d1.operTime,23) desc");
			sql_readingWeixin.append(" group by CONVERT(varchar(10),d1.operTime,23) order by CONVERT(varchar(10),d1.operTime,23) desc");
			
			Query query_deliveryTime = getSession().createSQLQuery(sql_deliveryTime.toString()).addScalar("deliveryTime", StandardBasicTypes.INTEGER)
					.addScalar("operTime", StandardBasicTypes.STRING);
			Query query_readingTime = getSession().createSQLQuery(sql_readingTime.toString()).addScalar("readingTime", StandardBasicTypes.INTEGER)
					.addScalar("operTime", StandardBasicTypes.STRING);
			Query query_deliveryWeixin = getSession().createSQLQuery(sql_deliveryWeixin.toString()).addScalar("deliveryWeixin", StandardBasicTypes.INTEGER)
					.addScalar("operTime", StandardBasicTypes.STRING);
			Query query_readingWeixin = getSession().createSQLQuery(sql_readingWeixin.toString()).addScalar("readingWeixin", StandardBasicTypes.INTEGER)
					.addScalar("operTime", StandardBasicTypes.STRING);
			List<Object> temp_deliveryTime = query_deliveryTime.list();
			List<Object> temp_readingTime = query_readingTime.list();
			List<Object> temp_deliveryWeixin = query_deliveryWeixin.list();
			List<Object> temp_readingWeixin = query_readingWeixin.list();
			
			for(int i = 0; i < dayList.size(); i++){
				String day = dayList.get(i);
				News news = new News();
				news.setTime(day);
				news.setDeliveryTime(0);
				news.setReadingTime(0);
				news.setDeliveryWeixin(0);
				news.setReadingWeixin(0);
				news.setConversionRate(0.0);
				if(temp_deliveryTime != null){
					for(int j =0; j < temp_deliveryTime.size(); j++){
						Object[] object = (Object[])temp_deliveryTime.get(j);
						String time = (String)object[1];
						if(time.equals(day)){
							news.setDeliveryTime((Integer)object[0]);
						}
					}
				}
				if(temp_readingTime != null){
					for(int j =0; j < temp_readingTime.size(); j++){
						Object[] object = (Object[])temp_readingTime.get(j);
						String time = (String)object[1];
						if(time.equals(day)){
							news.setReadingTime((Integer)object[0]);
						}
					}
				}
				if(temp_deliveryWeixin != null){
					for(int j =0; j < temp_deliveryWeixin.size(); j++){
						Object[] object = (Object[])temp_deliveryWeixin.get(j);
						String time = (String)object[1];
						if(time.equals(day)){
							news.setDeliveryWeixin((Integer)object[0]);
						}
					}
				}
				if(temp_readingWeixin != null){
					for(int j =0; j < temp_readingWeixin.size(); j++){
						Object[] object = (Object[])temp_readingWeixin.get(j);
						String time = (String)object[1];
						if(time.equals(day)){
							news.setReadingWeixin((Integer)object[0]);
						}
					}
				}
				news.setConversionRate(getDoubleToNews(news.getReadingWeixin(), news.getDeliveryWeixin()));
				list.add(news);
		}
      }
	  return list;	
	}
	
	//图文分析：月份数据
	public List<News> getWeixinsMonthToNews(String newsStartTime, String newsEndTime, Integer groupid, int pageNumber, int pageSize, Integer newsId){
		List<News> list = new ArrayList<News>();
		
		StringBuffer sql_deliveryTime = new StringBuffer();
		StringBuffer sql_readingTime = new StringBuffer();
		StringBuffer sql_deliveryWeixin = new StringBuffer();
		StringBuffer sql_readingWeixin = new StringBuffer();
	
		TMbNewsHistory newsHistory = getNewsHistory(groupid, newsId);	
        String starTime = DateUtil.sdf_YMd.format(newsHistory.getOperTime());
        String endTime = DateUtil.sdf_YMd.format(new Date());
        
        List<String> months;
        if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
        	months = DateUtil.getMonths(starTime, endTime, Constant.ZERO, Constant.ZERO);
        }else {
        	months = DateUtil.getMonths(starTime, endTime, pageNumber, pageSize);
        }
        
        sql_deliveryTime.append("select count(*) as deliveryTime,CONVERT(varchar(7),  d1.operTime, 120) as operTime from t_mb_news_history d1 where d1.operType=2");
        sql_readingTime.append("select count(*) as readingTime,CONVERT(varchar(7),  d1.operTime, 120) as operTime from t_mb_news_history d1 where d1.operType=1");
        sql_deliveryWeixin.append("select count(distinct openid) as deliveryWeixin,CONVERT(varchar(7),  d1.operTime, 120) as operTime from t_mb_news_history d1 where d1.operType=2");
        sql_readingWeixin.append("select count(distinct openid) as readingWeixin,CONVERT(varchar(7),  d1.operTime, 120) as operTime from t_mb_news_history d1 where d1.operType=1");
        
		if(groupid > 0){
			sql_deliveryTime.append(" and d1.openid in(select d2.openid from t_mb_weixinuser d2 where d2.openid=d1.openid and d2.groupid="+groupid+")");
			sql_readingTime.append(" and d1.openid in(select d2.openid from t_mb_weixinuser d2 where d2.openid=d1.openid and d2.groupid="+groupid+")");
			sql_deliveryWeixin.append(" and d1.openid in(select d2.openid from t_mb_weixinuser d2 where d2.openid=d1.openid and d2.groupid="+groupid+")");
			sql_readingWeixin.append(" and d1.openid in(select d2.openid from t_mb_weixinuser d2 where d2.openid=d1.openid and d2.groupid="+groupid+")");
		}
		if(newsId > 0){
			sql_deliveryTime.append(" and d1.newsId in(select ID from t_mb_news where parentId="+newsId+" and state=0 or ID="+newsId+")");
			sql_readingTime.append(" and d1.newsId in(select ID from t_mb_news where parentId="+newsId+" and state=0 or ID="+newsId+")");
			sql_deliveryWeixin.append(" and d1.newsId in(select ID from t_mb_news where parentId="+newsId+" and state=0 or ID="+newsId+")");
			sql_readingWeixin.append(" and d1.newsId in(select ID from t_mb_news where parentId="+newsId+" and state=0 or ID="+newsId+")");
		}
		sql_deliveryTime.append(" group by CONVERT(varchar(7),  d1.operTime, 120) order by CONVERT(varchar(7),  d1.operTime, 120) desc");
		sql_readingTime.append(" group by CONVERT(varchar(7),  d1.operTime, 120) order by CONVERT(varchar(7),  d1.operTime, 120) desc");
		sql_deliveryWeixin.append(" group by CONVERT(varchar(7),  d1.operTime, 120) order by CONVERT(varchar(7),  d1.operTime, 120) desc");
		sql_readingWeixin.append(" group by CONVERT(varchar(7),  d1.operTime, 120) order by CONVERT(varchar(7),  d1.operTime, 120) desc");
		
		Query query_deliveryTime = getSession().createSQLQuery(sql_deliveryTime.toString()).addScalar("deliveryTime", StandardBasicTypes.INTEGER)
				.addScalar("operTime", StandardBasicTypes.STRING);
		Query query_readingTime = getSession().createSQLQuery(sql_readingTime.toString()).addScalar("readingTime", StandardBasicTypes.INTEGER)
				.addScalar("operTime", StandardBasicTypes.STRING);
		Query query_deliveryWeixin = getSession().createSQLQuery(sql_deliveryWeixin.toString()).addScalar("deliveryWeixin", StandardBasicTypes.INTEGER)
				.addScalar("operTime", StandardBasicTypes.STRING);
		Query query_readingWeixin = getSession().createSQLQuery(sql_readingWeixin.toString()).addScalar("readingWeixin", StandardBasicTypes.INTEGER)
				.addScalar("operTime", StandardBasicTypes.STRING);
		List<Object> temp_deliveryTime = query_deliveryTime.list();
		List<Object> temp_readingTime = query_readingTime.list();
		List<Object> temp_deliveryWeixin = query_deliveryWeixin.list();
		List<Object> temp_readingWeixin = query_readingWeixin.list();
		
		for(int i = 0; i < months.size(); i++){
			String[] values = months.get(i).split("=");
			String YYMM = null;
			News news = new News();
			try {
				YYMM = DateUtil.sdf_YM.format(DateUtil.sdf_YM.parse(values[0]));
				news.setTime(DateUtil.sdf_YM_China.format(DateUtil.sdf_YM.parse(values[0])));
				news.setDeliveryTime(0);
				news.setReadingTime(0);
				news.setDeliveryWeixin(0);
				news.setReadingWeixin(0);
				news.setConversionRate(0.0);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(temp_deliveryTime != null){
				for(int j =0; j < temp_deliveryTime.size(); j++){
					Object[] object = (Object[])temp_deliveryTime.get(j);
					String time = (String)object[1];
					if(time.equals(YYMM)){
						news.setDeliveryTime((Integer)object[0]);
					}
				}
			}
			if(temp_readingTime != null){
				for(int j =0; j < temp_readingTime.size(); j++){
					Object[] object = (Object[])temp_readingTime.get(j);
					String time = (String)object[1];
					if(time.equals(YYMM)){
						news.setReadingTime((Integer)object[0]);
					}
				}
			}
			if(temp_deliveryWeixin != null){
				for(int j =0; j < temp_deliveryWeixin.size(); j++){
					Object[] object = (Object[])temp_deliveryWeixin.get(j);
					String time = (String)object[1];
					if(time.equals(YYMM)){
						news.setDeliveryWeixin((Integer)object[0]);
					}
				}
			}
			if(temp_readingWeixin != null){
				for(int j =0; j < temp_readingWeixin.size(); j++){
					Object[] object = (Object[])temp_readingWeixin.get(j);
					String time = (String)object[1];
					if(time.equals(YYMM)){
						news.setReadingWeixin((Integer)object[0]);
					}
				}
			}
			news.setConversionRate(getDoubleToNews(news.getReadingWeixin(), news.getDeliveryWeixin()));
			list.add(news);
	    }
		return list;
	}
	
	//图文分析：周数据
	public List<News> getWeixinsWeekToNews(Integer groupid, int pageNumber, int pageSize, Integer newsId){
		List<News> list = new ArrayList<News>();
		
		TMbNewsHistory newsHistory = getNewsHistory(groupid, newsId);
		List<String> weeks = DateUtil.getWeeks(DateUtil.sdf_YMd.format(newsHistory.getOperTime()), pageNumber, pageSize);//获取周
		
		if(weeks != null && weeks.size() > 0){
			
			for(String day : weeks){
				String[] days = day.split("=");
				day = day.replace("=", "至");
				
				News news = new News();
				news.setTime(day);
				
				List<Integer> result = getNewsDataByTime(groupid, days[0]+" 00:00:00", days[1]+" 23:59:59", null, newsId);
				
				news.setDeliveryTime(result.get(0));
				news.setReadingTime(result.get(1));
				news.setDeliveryWeixin(result.get(2));
				news.setReadingWeixin(result.get(3));
				news.setConversionRate(getDoubleToNews(result.get(3), result.get(2)));
				list.add(news);
			}
		}
		return list;
	}
	
	//图文分析：获取第一个图文记录
	public TMbNewsHistory getNewsHistory(Integer groupid, Integer newsId){
		StringBuffer sql = new StringBuffer();
		sql.append("select top 1 * from t_mb_news_history d1 where 1=1");
		if(newsId != null && newsId > 0){
			sql.append(" and d1.newsId in(select ID from t_mb_news where parentId="+newsId+" and state=0 or ID="+newsId+")");
		}
		if(groupid != null && groupid > 0){
			sql.append(" and d1.openid in(select d2.openid from t_mb_weixinuser d2 where d2.openid=d1.openid and d2.groupid="+groupid+")");
		}
		sql.append(" order by d1.operTime asc");
		Query query = getSession().createSQLQuery(sql.toString()).addEntity(TMbNewsHistory.class);
		return (TMbNewsHistory) query.uniqueResult();
	}
	
	//图文分析：列表数量
	public Integer getNewsCount(Map<String, Object> data) {
		//查询条件
		Integer dateType = (Integer)data.get("dateType");
		String newsStartTime = (String)data.get("newsStartTime");
		String newsEndTime = (String)data.get("newsEndTime");
		Integer newsId = (Integer) data.get("newsId");
		Integer groupid = (Integer)data.get("groupid");
		int count = 0;
		
		if(dateType == 0 && !newsStartTime.equals("") && !newsEndTime.equals("")){//自定义时间
			count = DateUtil.getDayCount(newsStartTime, newsEndTime);
		}else if(dateType == 1){//周
			TMbNewsHistory newsHistory = getNewsHistory(groupid, newsId);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE,   -1);
			count = DateUtil.getWeekCount(newsHistory.getOperTime(), cal.getTime()) + 2;
		}else if(dateType == 2){//月
			TMbNewsHistory newsHistory = getNewsHistory(groupid, newsId);
	        String start = DateUtil.sdf_YMd.format(newsHistory.getOperTime());
	        String end = DateUtil.sdf_YMd.format(new Date());
			count = DateUtil.getMonthCount(start, end);
		}
		return count;
	}

	//会员绑定：昨日指标
	public Member getMember(Integer groupid) {
		String yesterday = DateUtil.getDayBefore(1);

		List<Object> result = getMemberDataByTime(groupid, yesterday+" 00:00:00", yesterday+" 23:59:59");			
		
		Member member = new Member();
		member.setActiveMember(0);
		member.setMemberBinding(0);
		member.setActiveScale("--");
		if(result != null && result.size() == 1){
			Object[] object = (Object[])result.get(0);
			member.setActiveMember((Integer)object[0]);
			member.setMemberBinding((Integer)object[1]);
			member.setActiveScale((String)object[2]);
			member.setMemberSum((Integer)object[3]);
			
			List<Double> dayScale = getAnalysisByDayToMember((Integer)object[0], (Integer)object[1], groupid); //日概率
			List<Double> weekScale = getAnalysisByWeekToMember(yesterday, groupid);//周概率
			List<Double> monthScale = getAnalysisByMonthToMember(yesterday, groupid);//月概率
			
		    List<Double> memberBindingScale = new ArrayList<Double>(); //会员绑定数量的日、周、月概率
			List<Double> activeMemberScale = new ArrayList<Double>(); //活跃会员数量的日、周、月概率
			
			memberBindingScale.add(dayScale.get(1));
			activeMemberScale.add(dayScale.get(0));
			
			memberBindingScale.add(weekScale.get(1));
			activeMemberScale.add(weekScale.get(0));
			
			memberBindingScale.add(monthScale.get(1));
			activeMemberScale.add(monthScale.get(0));
			
			member.setMemberBindingScale(memberBindingScale);
			member.setActiveMemberScale(activeMemberScale);
		}
		return member;
	}
	
	//会员绑定：通用查询方法，时间格式=xxxx-xx-xx
	public List<Object> getMemberDataByTime(Integer groupid, String startTime, String endTime){
		StringBuffer sql = new StringBuffer();
		if(groupid > 0){

			sql.append("select COUNT(*) as active," +
					"(select COUNT(*) from t_mb_weixinuser where bindDate is not null and bindDate between '"+startTime+"' and '"+endTime+"' and groupid="+groupid+") as memberBinding," +
					"cast(cast(100.0*COUNT(*)/(select COUNT(*) from t_mb_weixinuser where bindDate is not null and groupid="+groupid+") as decimal(18,2)) as varchar(5))+'%' scale,(select COUNT(*) from t_mb_weixinuser where bindDate is not null and groupid="+groupid+")as memberSum from (" +
					"select memberId from t_mb_weixinuser where memberId in (select d1.memberId from t_hd_member_cost_history d1 " +
					"where d1.operType=0 and d1.costTime between '"+startTime+"' and '"+endTime+"' " +
					"and d1.memberId=(select d2.memberId from t_mb_weixinuser d2 where d2.memberId=d1.memberId and d2.groupid="+groupid+") group by d1.memberId having COUNT(*) >= 3)) as member");
					
		}else {
			sql.append("select COUNT(*) as active," +
					"(select COUNT(*) from t_mb_weixinuser where bindDate is not null and bindDate between '"+startTime+"' and '"+endTime+"') as memberBinding," +
					"cast(cast(100.0*COUNT(*)/(select COUNT(*) from t_mb_weixinuser where bindDate is not null) as decimal(18,2)) as varchar(5))+'%' scale," +
					"(select COUNT(*) from t_mb_weixinuser where bindDate is not null)as memberSum from ("+
					"select memberId from t_mb_weixinuser where memberId in (select d1.memberId from t_hd_member_cost_history d1 " +
					"where d1.operType=0 and d1.costTime between '"+startTime+"' and '"+endTime+"' group by d1.memberId having COUNT(*) >= 3)) as member");
		}
		
		List<Object> result = getSession().createSQLQuery(sql.toString()).addScalar("active", StandardBasicTypes.INTEGER)
				.addScalar("memberBinding", StandardBasicTypes.INTEGER).addScalar("scale", StandardBasicTypes.STRING)
				.addScalar("memberSum", StandardBasicTypes.INTEGER).list();
		return result;
	}
	
	//会员绑定：日
	public List<Double> getAnalysisByDayToMember(Integer activeMember, Integer memberBinding, Integer groupid){
		List<Double> list = new ArrayList<Double>();
		
		String dayBeforeYesterday = DateUtil.getDayBefore(2);//前天
		
		List<Object> result = getMemberDataByTime(groupid, dayBeforeYesterday+" 00:00:00", dayBeforeYesterday+" 23:59:59");

		if(result != null && result.size() == 1){
			Object[] object = (Object[])result.get(0);
			list.add(getDouble((Integer)object[0], activeMember));
			list.add(getDouble((Integer)object[1], memberBinding));
		}else {
			list.add(0.0);
			list.add(0.0);
		}
		return list;
	}

	//会员绑定：周
	public List<Double> getAnalysisByWeekToMember(String yesterday, Integer groupid){
		List<Double> list = new ArrayList<Double>();
		
		List<String> day = DateUtil.getWeekMonSun();
		
		yesterday = yesterday+" 23:59:59";
		List<Object> result = getMemberDataByTime(groupid, day.get(2), day.get(3));
		
		List<Object> result_lastweek = getMemberDataByTime(groupid, day.get(0), day.get(1));
		
		if(result != null && result_lastweek != null && result_lastweek.size() == 1 && result.size() == 1){
			Object[] object1 = (Object[])result.get(0);
			Object[] object2 = (Object[])result_lastweek.get(0);
			
			list.add(getDouble((Integer)object2[0], (Integer)object1[0]));
			list.add(getDouble((Integer)object2[1], (Integer)object1[1]));
		}else {
			list.add(0.0);
			list.add(0.0);
		}
		return list;
	}
	
	//会员绑定：月
	public List<Double> getAnalysisByMonthToMember(String yesterday, Integer groupid){
		List<String> months = DateUtil.getMonth();
		
		List<Object> result = getMemberDataByTime(groupid, months.get(2), months.get(3));//上月
		List<Object> result_lastMonth = getMemberDataByTime(groupid, months.get(0), months.get(1));//上上月
		
		List<Double> list = new ArrayList<Double>();
		
		if(result != null && result_lastMonth != null){
			Object[] object1 = (Object[])result.get(0);
			Object[] object2 = (Object[])result_lastMonth.get(0);
			
			list.add(getDouble((Integer)object2[0], (Integer)object1[0]));
			list.add(getDouble((Integer)object2[1], (Integer)object1[1]));
		}else {
			list.add(0.0);
			list.add(0.0);
		}
		return list;
	}
	
    //会员绑定：列表、导出csv
	public List<Member> getMembers(Map<String, Object> data) {
		//查询条件
		Integer dateType = (Integer)data.get("dateType");
		Integer groupid = (Integer)data.get("groupid");
		String startTime = (String)data.get("startTime");
		String endTime = (String)data.get("endTime");
		List<String> days = (List<String>) data.get("days");
		int defaultCount = (Integer) data.get("defaultCount");
		
		//分页条件
		int pageNumber = (Integer)data.get("pageNumber");
		int pageSize = (Integer)data.get("pageSize");
		
		List<Member> result = new ArrayList<Member>();
		
		//先判断是否默认状态
		if(defaultCount > 0){
			result = getDefaultToMembers(days, groupid);
		}else {
			if(dateType == 1){//周
				result = getWeekToMembers(groupid, pageNumber, pageSize);
		    }else if(dateType == 2){//月
		    	result = getMonthToMembers(groupid, pageNumber, pageSize);
		   }else if(dateType == 0 && !startTime.equals("") && !endTime.equals("")){//自定义时间
			   result = getCustomToMembers(startTime, endTime, groupid, pageNumber, pageSize);
		   }
	    }
		return result;
	}
	
    //会员绑定：默认情况
	public List<Member> getDefaultToMembers(List<String> days, Integer groupid){
		List<Member> list = new ArrayList<Member>();
		int days_size = days.size();
		for(int i = 0; i < days_size; i++){
			String day = days.get(i);
			
			List<Object> result = getMemberDataByTime(groupid, day+" 00:00:00", day+" 23:59:59");
			
			Member member = new Member();
			member.setTime(day);
			if(result != null && result.size() == 1){
				Object[] object = (Object[])result.get(0);
				member.setActiveMember((Integer)object[0]);
				member.setMemberBinding((Integer)object[1]);
				member.setActiveScale((String)object[2]);
			}else {
				member.setActiveMember(0);
				member.setMemberBinding(0);
				member.setActiveScale("0");
			}
			list.add(member);
		}
		return list;
	}
	
	//会员绑定：周数据
	public List<Member> getWeekToMembers(Integer groupid, int pageNumber, int pageSize){
		List<Member> list = new ArrayList<Member>();
		
        List<String> weeks;
        if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
        	weeks = DateUtil.getWeeks(DateUtil.sdf_YMd.format(Constant.members.getBindDate()), Constant.ZERO, Constant.ZERO);//获取周
        }else {
        	weeks = DateUtil.getWeeks(DateUtil.sdf_YMd.format(Constant.members.getBindDate()), pageNumber, pageSize);//获取周
        }
		
		if(weeks != null && weeks.size() > 0){
			
			for(String day : weeks){		
				String[] days = day.split("=");
				day = day.replace("=", "至");
				
				Member member = new Member();
				member.setTime(day);
				
				List<Object> result = getMemberDataByTime(groupid, days[0]+" 00:00:00", days[1]+" 23:59:59");

				if(result != null && result.size() == 1){
					Object[] object = (Object[])result.get(0);
					member.setActiveMember((Integer)object[0]);
					member.setMemberBinding((Integer)object[1]);
					member.setActiveScale((String)object[2]);
				}else {
					member.setActiveMember(0);
					member.setMemberBinding(0);
					member.setActiveScale("0");
				}
				list.add(member);
			}
		}
		return list;
	}

	//会员绑定：月份数据
	public List<Member> getMonthToMembers(Integer groupid, int pageNumber, int pageSize){
		List<Member> list = new ArrayList<Member>();

        String starTime = DateUtil.sdf_YMd.format(Constant.members.getBindDate());
        String endTime = DateUtil.sdf_YMd.format(new Date());
        
        List<String> months;
        if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
        	months = DateUtil.getMonths(starTime, endTime, Constant.ZERO, Constant.ZERO);
        }else {
        	months = DateUtil.getMonths(starTime, endTime, pageNumber, pageSize);
        }
 
		for(int i = 0; i < months.size(); i++){
			String[] values = months.get(i).split("=");
			
			List<Object> result = getMemberDataByTime(groupid, values[0], values[1]);
			
			Member member = new Member();
			try {
				member.setTime(DateUtil.sdf_YM_China.format(DateUtil.sdf_YM.parse(values[0])));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(result != null && result.size() == 1){
				Object[] object = (Object[])result.get(0);
				member.setActiveMember((Integer)object[0]);
				member.setMemberBinding((Integer)object[1]);
				member.setActiveScale((String)object[2]);
			}else {
				member.setActiveMember(0);
				member.setMemberBinding(0);
				member.setActiveScale("0");
			}
			list.add(member);
	    }
		return list;
	}
	
	//会员绑定：自定义时间
	public List<Member> getCustomToMembers(String startTime, String endTime, Integer groupid, int pageNumber, int pageSize){
		List<Member> list = new ArrayList<Member>();
		
		List<String> dayList;
		if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
			dayList = DateUtil.getDays(startTime, endTime, Constant.ZERO, Constant.ZERO);
		}else {
			dayList = DateUtil.getDays(startTime, endTime, pageNumber, pageSize);
		}
		if(dayList != null && dayList.size() > 0){
			
			for(int i = 0; i < dayList.size(); i++){
				String time = dayList.get(i);

				Member member = new Member();
				member.setTime(time);
				
				List<Object> result = getMemberDataByTime(groupid, time+" 00:00:00", time+" 23:59:59");
				
				if(result != null && result.size() == 1){
					Object[] object = (Object[])result.get(0);
					member.setActiveMember((Integer)object[0]);
					member.setMemberBinding((Integer)object[1]);
					member.setActiveScale((String)object[2]);
				}else {
					member.setActiveMember(0);
					member.setMemberBinding(0);
					member.setActiveScale("0");
				}
				list.add(member);
		}
      }
	  return list;	
	}
	
	//会员绑定：列表数量
	public Integer getMembersCount(Map<String, Object> data) {
		//查询条件
		Integer dateType = (Integer)data.get("dateType");
		String startTime = (String)data.get("startTime");
		String endTime = (String)data.get("endTime");
		
		int count = 0;
		
		if(dateType == 0 && !startTime.equals("") && !endTime.equals("")){//自定义时间
			count = DateUtil.getDayCount(startTime, endTime);
		}else if(dateType == 1){//周
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE,   -1);
			count = DateUtil.getWeekCount(Constant.members.getBindDate(), cal.getTime()) + 2;
		}else if(dateType == 2){//月
	        String start = DateUtil.sdf_YMd.format(Constant.members.getBindDate());
	        String end = DateUtil.sdf_YMd.format(new Date());
			count = DateUtil.getMonthCount(start, end);
		}
		return count;
	}

	//门店签到：昨日关键指标
	public Sign getSign(Integer groupid, Integer storesId) {
		String yesterday = DateUtil.getDayBefore(1);//昨天
		String dayBeforeYesterday = DateUtil.getDayBefore(2);//前天

		Integer day = getSignDataByTime(groupid, storesId, yesterday+"00:00:00", yesterday+"23:59:59");			
		Integer day_before = getSignDataByTime(groupid, storesId, dayBeforeYesterday+"00:00:00", dayBeforeYesterday+"23:59:59");
		
		List<String> time = DateUtil.getWeekMonSun();
		
		yesterday = yesterday+"23:59:59";
		Integer week = getSignDataByTime(groupid, storesId, time.get(2), time.get(3));
		Integer week_before = getSignDataByTime(groupid, storesId, time.get(0), time.get(1));
		
		List<String> months = DateUtil.getMonth();
		
		Integer month = getSignDataByTime(groupid, storesId, months.get(2), months.get(3));//上月
		Integer month_before = getSignDataByTime(groupid, storesId, months.get(0), months.get(1));//上上月
		
		Sign sign = new Sign();
		sign.setSignscribe(day);
		
		Integer signSum = 0;
		if(groupid > 0 && storesId == 0){
			signSum = (Integer) getSession().createSQLQuery("select COUNT(*) from t_mb_sign_history d1 where " +
					"d1.groupid in(select groupid from t_mb_group d2 where d2.groupid=d1.groupid and d2.parentId="+groupid+") and signDate <='"+yesterday+"'").uniqueResult();
		}else if(storesId > 0){
			signSum = (Integer) getSession().createSQLQuery("select COUNT(*) from t_mb_sign_history d1 where d1.groupid="+storesId+" and d1.signDate <='"+yesterday+"'").uniqueResult();
		}else {
			signSum = (Integer) getSession().createSQLQuery("select COUNT(*) from t_mb_sign_history d1 where d1.signDate <='"+yesterday+"'").uniqueResult();
		}
		sign.setSignSum(signSum);
		List<Double> signScale = new ArrayList<Double>(); //会员绑定数量的日、周、月概率
		signScale.add(getDouble(day_before, day));
		signScale.add(getDouble(week_before, week));
		signScale.add(getDouble(month_before, month));
		sign.setSignScale(signScale);
		return sign;
	}

	//门店签到：通用查询方法，时间格式=xxxx-xx-xx
	public Integer getSignDataByTime(Integer groupid, Integer storesId, String startTime, String endTime){
		StringBuffer sql = new StringBuffer();
		sql.append("select COUNT(*) from t_mb_sign_history d1 where signDate between '"+startTime+"' and '"+endTime+"'");
		if(groupid > 0 && storesId == 0){ //分公司
			sql.append(" and d1.groupid in(select groupid from t_mb_group d2 where d2.groupid=d1.groupid and d2.parentId="+groupid+")");
		}
		if(storesId > 0){//门店
			sql.append(" and d1.groupid="+storesId);
		}
		return (Integer) getSession().createSQLQuery(sql.toString()).uniqueResult();
	}

	//门店签到：列表、导出csv
	public List<Sign> getSigns(Map<String, Object> data) {
		//查询条件
		Integer dateType = (Integer)data.get("dateType");
		Integer groupid = (Integer)data.get("groupid");
		String startTime = (String)data.get("startTime");
		String endTime = (String)data.get("endTime");
		List<String> days = (List<String>) data.get("days");
		int defaultCount = (Integer) data.get("defaultCount");
		Integer storesId = (Integer) data.get("storesId");
		
		//分页条件
		int pageNumber = (Integer)data.get("pageNumber");
		int pageSize = (Integer)data.get("pageSize");
		
		List<Sign> result = new ArrayList<Sign>();
		
		//先判断是否默认状态
		if(defaultCount > 0){
			int days_size = days.size();
			for(int i = 0; i < days_size; i++){
				String day = days.get(i);
				Sign sign = new Sign();
				sign.setTime(day);
				sign.setSignscribe(getSignDataByTime(groupid, storesId, day+" 00:00:00", day+" 23:59:59"));
				result.add(sign);
			}
		}else {
			if(dateType == 1){//周
				List<String> weeks;
				if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
					weeks = DateUtil.getWeeks(DateUtil.sdf_YMd.format(Constant.signHistory.getSignDate()), Constant.ZERO, Constant.ZERO);//获取周
			    }else {
			        weeks = DateUtil.getWeeks(DateUtil.sdf_YMd.format(Constant.signHistory.getSignDate()), pageNumber, pageSize);//获取周
			    }
				if(weeks != null && weeks.size() > 0){
					for(String day : weeks){		
						String[] days1 = day.split("=");
						day = day.replace("=", "至");
						Sign sign = new Sign();
						sign.setTime(day);
						sign.setSignscribe(getSignDataByTime(groupid, storesId, days1[0]+" 00:00:00", days1[1]+" 23:59:59"));
						result.add(sign);
					}
				}
		    }else if(dateType == 2){//月
		    	String starTime = DateUtil.sdf_YMd.format(Constant.signHistory.getSignDate());
			    String day = DateUtil.sdf_YMd.format(new Date());
			    
			    List<String> months;
			    if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
			        months = DateUtil.getMonths(starTime, day, Constant.ZERO, Constant.ZERO);
			    }else {
			        months = DateUtil.getMonths(starTime, day, pageNumber, pageSize);
			    }
			    
			    for(int i = 0; i < months.size(); i++){
			    	String[] values = months.get(i).split("=");
			    	
			    	Sign sign = new Sign();
					try {
						sign.setTime(DateUtil.sdf_YM_China.format(DateUtil.sdf_YM.parse(values[0])));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					sign.setSignscribe(getSignDataByTime(groupid, storesId, values[0], values[1]));
					result.add(sign);
				}
		   }else if(dateType == 0 && !startTime.equals("") && !endTime.equals("")){//自定义时间
				List<String> dayList;
				if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
					dayList = DateUtil.getDays(startTime, endTime, Constant.ZERO, Constant.ZERO);
				}else {
					dayList = DateUtil.getDays(startTime, endTime, pageNumber, pageSize);
				}
				if(dayList != null && dayList.size() > 0){
					for(int i = 0; i < dayList.size(); i++){
						String time = dayList.get(i);
						Sign sign = new Sign();
						sign.setTime(time);
						sign.setSignscribe(getSignDataByTime(groupid, storesId, time+" 00:00:00", time+" 23:59:59"));
						result.add(sign);
				    }
		        }
		   }
	    }
		return result;
	}

	//门店签到：列表数量
	public Integer getSignsCount(Map<String, Object> data) {
		//查询条件
		Integer dateType = (Integer)data.get("dateType");
		String startTime = (String)data.get("startTime");
		String endTime = (String)data.get("endTime");
		
		int count = 0;
		
		if(dateType == 0 && !startTime.equals("") && !endTime.equals("")){//自定义时间
			count = DateUtil.getDayCount(startTime, endTime);
		}else if(dateType == 1){//周
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE,   -1);
			count = DateUtil.getWeekCount(Constant.signHistory.getSignDate(), cal.getTime()) + 2;
		}else if(dateType == 2){//月
			String start = DateUtil.sdf_YMd.format(Constant.signHistory.getSignDate());
		    String end = DateUtil.sdf_YMd.format(new Date());
		    count = DateUtil.getMonthCount(start, end);
		}
		return count;
	}

	//活动分析：昨日指标
	public Activity getActivity(Integer groupid, Integer activityId) {
		String yesterday = DateUtil.getDayBefore(1);//昨天
		String dayBeforeYesterday = DateUtil.getDayBefore(2);//前天

		List<Integer> day = getActivityDataByTime(groupid, activityId, yesterday+"00:00:00", yesterday+"23:59:59");			
		List<Integer> day_before = getActivityDataByTime(groupid, activityId, dayBeforeYesterday+"00:00:00", dayBeforeYesterday+"23:59:59");
		
		Activity activity = new Activity();
		activity.setTime(yesterday);
		activity.setActivitysscribe(0);
		activity.setActivityscribe(0);
		activity.setReadingscribe(0);
		activity.setIncreasescribe(0);
		activity.setMemberscribe(0);
		activity.setSinglescribe(0);
		activity.setRepeatscribe(0);
		if(day != null && day.size() == 7){
			activity.setActivitysscribe(day.get(0));
			activity.setActivityscribe(day.get(1));
			activity.setReadingscribe(day.get(2));
			activity.setIncreasescribe(day.get(3));
			activity.setMemberscribe(day.get(4));
			activity.setSinglescribe(day.get(5));
			activity.setRepeatscribe(day.get(6));
		}
		
		List<Double> activityScale = new ArrayList<Double>();  //微信粉丝参与数的日、周、月概率
		List<Double> readingScale = new ArrayList<Double>();   //阅读人数的日、周、月概率
		List<Double> increaseScale = new ArrayList<Double>();  //粉丝增长的日、周、月概率
		List<Double> memberScale = new ArrayList<Double>();    //绑定会员参与数的日、周、月概率
		
		List<String> time = DateUtil.getWeekMonSun();
		
		yesterday = yesterday+" 23:59:59";
		List<Integer> week = getActivityDataByTime(groupid, activityId,time.get(2), time.get(3));
		
		List<Integer> week_before = getActivityDataByTime(groupid,activityId, time.get(0), time.get(1));
		
		List<String> months = DateUtil.getMonth();
		
		List<Integer> month = getActivityDataByTime(groupid, activityId, months.get(2), months.get(3));//上月
		List<Integer> month_before = getActivityDataByTime(groupid, activityId, months.get(0), months.get(1));//上上月
	
		activityScale.add(getDouble(day_before.get(1), day.get(1)));
		activityScale.add(getDouble(week_before.get(1), week.get(1)));
		activityScale.add(getDouble(month_before.get(1), month.get(1)));
		
		readingScale.add(getDouble(day_before.get(2), day.get(2)));
		readingScale.add(getDouble(week_before.get(2), week.get(2)));
		readingScale.add(getDouble(month_before.get(2), month.get(2)));
		
		increaseScale.add(getDouble(day_before.get(3), day.get(3)));
		increaseScale.add(getDouble(week_before.get(3), week.get(3)));
		increaseScale.add(getDouble(month_before.get(3), month.get(3)));
		
		memberScale.add(getDouble(day_before.get(4), day.get(4)));
		memberScale.add(getDouble(week_before.get(4), week.get(4)));
		memberScale.add(getDouble(month_before.get(4), month.get(4)));
		
		activity.setActivityScale(activityScale);
		activity.setReadingScale(readingScale);
		activity.setIncreaseScale(increaseScale);
		activity.setMemberScale(memberScale);
		return activity;
	}

	//活动分析：通用查询方法，时间格式=xxxx-xx-xx
	public List<Integer> getActivityDataByTime(Integer groupid, Integer activityId, String startTime, String endTime){
		StringBuffer sql = new StringBuffer();
		List<Integer> result = new ArrayList<Integer>();
		if(groupid > 0){
			if(activityId > 0){
				sql.append("select COUNT(distinct openid)as activitysscribe," +
						"(select COUNT(distinct openid) from t_mb_awardweixin where createTime between '"+startTime+"' and '"+endTime+"' and openId in(select openId from t_mb_weixinuser where groupid="+groupid+") and activityId="+activityId+")as activityscribe," +
						"(select COUNT(distinct openid) from t_mb_activity_history where operTime between '"+startTime+"' and '"+endTime+"' and openId in(select openId from t_mb_weixinuser where groupid="+groupid+") and activityId="+activityId+")as readingscribe," +
						"(select count(*) from t_mb_awardweixin where createTime between '"+startTime+"' and '"+endTime+"' and activityId="+activityId+" and openid " +
						"in(select distinct openid from t_mb_weixinuser where subscribe_time between '"+startTime+"' and '"+endTime+"' and subscribe=1 and groupid="+groupid+"))as increasescribe," +
						"(select COUNT(distinct d1.openid) from t_mb_awardweixin d1 join t_mb_weixinuser d2 on d1.openId=d2.openid " +
						"where d1.createTime between '"+startTime+"' and '"+endTime+"' and d2.memberId is not null and d2.groupid="+groupid+" and d1.activityId="+activityId+")as memberscribe," +
						"(select count(*) from (select openid from t_mb_awardweixin where createTime between '"+startTime+"' and '"+endTime+"' " +
						" and openId in(select openId from t_mb_weixinuser where groupid="+groupid+") and activityId="+activityId+" group by openid having COUNT(openid) = 1)as awardweixin)as singlescribe," +
						"(select count(*) from (select openid from t_mb_awardweixin where createTime between '"+startTime+"' and '"+endTime+"'" +
						" and openId in(select openId from t_mb_weixinuser where groupid="+groupid+") and activityId="+activityId+" group by openId having COUNT(openid) >= 2)as awardweixin)as repeatscribe " +
						"from t_mb_awardweixin where createTime <='"+endTime+"' and openId in(select openId from t_mb_weixinuser where groupid="+groupid+") and activityId="+activityId);
			}else {
				sql.append("select COUNT(distinct openid)as activitysscribe," +
						"(select COUNT(distinct openid) from t_mb_awardweixin where createTime between '"+startTime+"' and '"+endTime+"' and openId in(select openId from t_mb_weixinuser where groupid="+groupid+"))as activityscribe," +
						"(select COUNT(distinct openid) from t_mb_activity_history where operTime between '"+startTime+"' and '"+endTime+"' and openId in(select openId from t_mb_weixinuser where groupid="+groupid+"))as readingscribe," +
						"(select count(*) from t_mb_awardweixin where createTime between '"+startTime+"' and '"+endTime+"' and openid in" +
						"(select distinct openid from t_mb_weixinuser where subscribe_time between '"+startTime+"' and '"+endTime+"' and subscribe=1 and groupid="+groupid+"))as increasescribe," +
						"(select COUNT(distinct d1.openid) from t_mb_awardweixin d1 join t_mb_weixinuser d2 on d1.openId=d2.openid " +
						"where d1.createTime between '"+startTime+"' and '"+endTime+"' and d2.memberId is not null and d2.groupid="+groupid+")as memberscribe," +
						"(select count(*) from (select openid from t_mb_awardweixin where createTime between '"+startTime+"' and '"+endTime+"' " +
						" and openId in(select openId from t_mb_weixinuser where groupid="+groupid+") group by openid having COUNT(openid) = 1)as awardweixin)as singlescribe," +
						"(select count(*) from (select openid from t_mb_awardweixin where createTime between '"+startTime+"' and '"+endTime+"'" +
						" and openId in(select openId from t_mb_weixinuser where groupid="+groupid+") group by openId having COUNT(openid) >= 2)as awardweixin)as repeatscribe " +
						"from t_mb_awardweixin where createTime <='"+endTime+"' and openId in(select openId from t_mb_weixinuser where groupid="+groupid+")");
			}
		}else {
			if(activityId > 0){
				sql.append("select COUNT(distinct openid)as activitysscribe," +
						"(select COUNT(distinct openid) from t_mb_awardweixin where createTime between '"+startTime+"' and '"+endTime+"' and activityId="+activityId+")as activityscribe," +
						"(select COUNT(distinct openid) from t_mb_activity_history where operTime between '"+startTime+"' and '"+endTime+"' and openId in(select openId from t_mb_weixinuser) and activityId="+activityId+")as readingscribe," +
						"(select count(*) from t_mb_awardweixin where createTime between '"+startTime+"' and '"+endTime+"' and activityId="+activityId+" and openid in" +
						"(select distinct openid from t_mb_weixinuser where subscribe_time between '"+startTime+"' and '"+endTime+"' and subscribe=1))as increasescribe," +
						"(select COUNT(distinct d1.openid) from t_mb_awardweixin d1 join t_mb_weixinuser d2 on d1.openId=d2.openid " +
						"where d1.createTime between '"+startTime+"' and '"+endTime+"' and d2.memberId is not null and d1.activityId="+activityId+")as memberscribe," +
						"(select count(*) from (select openid from t_mb_awardweixin where createTime between '"+startTime+"' and '"+endTime+"' " +
						" and openId in(select openId from t_mb_weixinuser) and activityId="+activityId+" group by openid having COUNT(openid) = 1)as awardweixin)as singlescribe," +
						"(select count(*) from (select openid from t_mb_awardweixin where createTime between '"+startTime+"' and '"+endTime+"'" +
						" and openId in(select openId from t_mb_weixinuser) and activityId="+activityId+" group by openId having COUNT(openid) >= 2)as awardweixin)as repeatscribe " +
						"from t_mb_awardweixin where createTime <='"+endTime+"' and activityId="+activityId);
			}else {
				sql.append("select COUNT(distinct openid)as activitysscribe," +
						"(select COUNT(distinct openid) from t_mb_awardweixin where createTime between '"+startTime+"' and '"+endTime+"')as activityscribe," +
						"(select COUNT(distinct openid) from t_mb_activity_history where operTime between '"+startTime+"' and '"+endTime+"')as readingscribe," +
						"(select count(*) from t_mb_awardweixin where createTime between '"+startTime+"' and '"+endTime+"' and openid in" +
						"(select distinct openid from t_mb_weixinuser where subscribe_time between '"+startTime+"' and '"+endTime+"' and subscribe=1))as increasescribe," +
						"(select COUNT(distinct d1.openid) from t_mb_awardweixin d1 join t_mb_weixinuser d2 on d1.openId=d2.openid " +
						"where d1.createTime between '"+startTime+"' and '"+endTime+"' and d2.memberId is not null)as memberscribe," +
						"(select count(*) from (select openid from t_mb_awardweixin where createTime between '"+startTime+"' and '"+endTime+"' " +
						"group by openid having COUNT(openid) = 1)as awardweixin)as singlescribe," +
						"(select count(*) from (select openid from t_mb_awardweixin where createTime between '"+startTime+"' and '"+endTime+"'" +
						"group by openId having COUNT(openid) >= 2)as awardweixin)as repeatscribe " +
						"from t_mb_awardweixin where createTime <='"+endTime+"'");
			}
		}
		Query query = getSession().createSQLQuery(sql.toString()).addScalar("activitysscribe", StandardBasicTypes.INTEGER)
				.addScalar("activityscribe", StandardBasicTypes.INTEGER).addScalar("readingscribe", StandardBasicTypes.INTEGER)
				.addScalar("increasescribe", StandardBasicTypes.INTEGER).addScalar("memberscribe", StandardBasicTypes.INTEGER)
				.addScalar("singlescribe", StandardBasicTypes.INTEGER).addScalar("repeatscribe", StandardBasicTypes.INTEGER);
		List temp = query.list();
		if(temp != null && temp.size() > 0){
			Object[] object = (Object[])temp.get(0);
			result.add((Integer)object[0]);
			result.add((Integer)object[1]);
			result.add((Integer)object[2]);
			result.add((Integer)object[3]);
			result.add((Integer)object[4]);
			result.add((Integer)object[5]);
			result.add((Integer)object[6]);
		}
		return result;
	}
	
	//活动分析：列表、导出csv
	public List<Activity> getActivitys(Map<String, Object> data) {
		//查询条件
		Integer dateType = (Integer)data.get("dateType");
		Integer groupid = (Integer)data.get("groupid");
		String startTime = (String)data.get("startTime");
		String endTime = (String)data.get("endTime");
		List<String> days = (List<String>) data.get("days");
		Integer defaultCount = (Integer) data.get("defaultCount");
		Integer activityId = (Integer) data.get("activityId");
		
		//分页条件
		int pageNumber = (Integer)data.get("pageNumber");
		int pageSize = (Integer)data.get("pageSize");
		
		List<Activity> result = new ArrayList<Activity>();
		
		//先判断是否默认状态
		if(defaultCount > 0){
			result = getDefaultToActivitys(days, groupid, activityId);
		}else {
			if(dateType == 1){//周
				result = getWeekToActivitys(groupid, activityId, pageNumber, pageSize);
		    }else if(dateType == 2){//月
		    	result = getMonthToActivitys(groupid, activityId, pageNumber, pageSize);
		   }else if(dateType == 0 && !startTime.equals("") && !endTime.equals("")){//自定义时间
			    result = getCustomToActivitys(startTime, endTime, groupid, activityId, pageNumber, pageSize);
		   }
	    }
		return result;
	}

	//活动分析：列表数量
	public Integer getActivitysCount(Map<String, Object> data) {
		//查询条件
		Integer dateType = (Integer)data.get("dateType");
		Integer activityId = (Integer)data.get("activityId");
		String startTime = (String)data.get("startTime");
		String endTime = (String)data.get("endTime");
		
		int count = 0;
		
		if(dateType == 0 && !startTime.equals("") && !endTime.equals("")){//自定义时间
			count = DateUtil.getDayCount(startTime, endTime);
		}else if(dateType == 1){//周
	    	String hql = "from TMbActivity where id="+activityId;
			Query query = getSession().createQuery(hql);
			TMbActivity activity = (TMbActivity) query.uniqueResult();
			if(activity != null){
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE,   -1);
				count = DateUtil.getWeekCount(activity.getBeginTime(), cal.getTime()) + 2;
			}
		}else if(dateType == 2){//月
	    	String hql = "from TMbActivity where id="+activityId;
			Query query = getSession().createQuery(hql);
			TMbActivity activity = (TMbActivity) query.uniqueResult();
			if(activity != null){
		        String start = DateUtil.sdf_YMd.format(activity.getBeginTime());
		        String end = DateUtil.sdf_YMd.format(new Date());
				count = DateUtil.getMonthCount(start, end);
			}
		}
		return count;
	}
	
	//活动分析：默认状态
	public List<Activity> getDefaultToActivitys(List<String> days, Integer groupid, Integer activityId){
		List<Activity> list = new ArrayList<Activity>();
		int days_size = days.size();
		for(int i = 0; i < days_size; i++){
			String day = days.get(i);
			
			List<Integer> result = getActivityDataByTime(groupid, activityId, day+" 00:00:00", day+" 23:59:59");
			
			Activity activity = new Activity();
			activity.setTime(day);
			activity.setActivitysscribe(0);
			activity.setActivityscribe(0);
			activity.setReadingscribe(0);
			activity.setIncreasescribe(0);
			activity.setMemberscribe(0);
			activity.setSinglescribe(0);
			activity.setRepeatscribe(0);
			if(result != null && result.size() == 7){
				activity.setActivitysscribe(result.get(0));
				activity.setActivityscribe(result.get(1));
				activity.setReadingscribe(result.get(2));
				activity.setIncreasescribe(result.get(3));
				activity.setMemberscribe(result.get(4));
				activity.setSinglescribe(result.get(5));
				activity.setRepeatscribe(result.get(6));
			}
			list.add(activity);
		}
		return list;
	}
	
	//活动分析：周数据
	public List<Activity> getWeekToActivitys(Integer groupid, Integer activityId, Integer pageNumber, Integer pageSize){
		List<Activity> list = new ArrayList<Activity>();
		
    	String hql = "from TMbActivity where id="+activityId;
		Query query = getSession().createQuery(hql);
		TMbActivity activity = (TMbActivity) query.uniqueResult();
		if(activity != null){
	        List<String> weeks;
	        if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
	        	weeks = DateUtil.getWeeks(DateUtil.sdf_YMd.format(activity.getBeginTime()), Constant.ZERO, Constant.ZERO);//获取周
	        }else {
	        	weeks = DateUtil.getWeeks(DateUtil.sdf_YMd.format(activity.getBeginTime()), pageNumber, pageSize);//获取周
	        }
			
			if(weeks != null && weeks.size() > 0){
				for(String day : weeks){		
					String[] days = day.split("=");
					day = day.replace("=", "至");
					
					Activity act = new Activity();
					act.setTime(day);
					act.setActivitysscribe(0);
					act.setActivityscribe(0);
					act.setReadingscribe(0);
					act.setIncreasescribe(0);
					act.setMemberscribe(0);
					act.setSinglescribe(0);
					act.setRepeatscribe(0);
					
					List<Integer> result = getActivityDataByTime(groupid, activityId, days[0]+" 00:00:00", days[1]+" 23:59:59");

					if(result != null && result.size() == 7){
						act.setActivitysscribe(result.get(0));
						act.setActivityscribe(result.get(1));
						act.setReadingscribe(result.get(2));
						act.setIncreasescribe(result.get(3));
						act.setMemberscribe(result.get(4));
						act.setSinglescribe(result.get(5));
						act.setRepeatscribe(result.get(6));
					}
					list.add(act);
				}
			}
		}
		return list;
	}
	
	//活动分析：月份数据
	public List<Activity> getMonthToActivitys(Integer groupid, Integer activityId, Integer pageNumber, Integer pageSize){
		List<Activity> list = new ArrayList<Activity>();
		
    	String hql = "from TMbActivity where id="+activityId;
		Query query = getSession().createQuery(hql);
		TMbActivity activity = (TMbActivity) query.uniqueResult();
		if(activity != null){
	        String starTime = DateUtil.sdf_YMd.format(activity.getBeginTime());
	        String endTime = DateUtil.sdf_YMd.format(new Date());
			
	        List<String> months;
	        if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
	        	months = DateUtil.getMonths(starTime, endTime, Constant.ZERO, Constant.ZERO);
	        }else {
	        	months = DateUtil.getMonths(starTime, endTime, pageNumber, pageSize);
	        }
			
			if(months != null && months.size() > 0){
				for(int i = 0; i < months.size(); i++){
					String[] values = months.get(i).split("=");
					
					List<Integer> result = getActivityDataByTime(groupid, activityId, values[0], values[1]);
					
					Activity act = new Activity();
					act.setActivitysscribe(0);
					act.setActivityscribe(0);
					act.setReadingscribe(0);
					act.setIncreasescribe(0);
					act.setMemberscribe(0);
					act.setSinglescribe(0);
					act.setRepeatscribe(0);
					try {
						act.setTime(DateUtil.sdf_YM_China.format(DateUtil.sdf_YM.parse(values[0])));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(result != null && result.size() == 7){
						act.setActivitysscribe(result.get(0));
						act.setActivityscribe(result.get(1));
						act.setReadingscribe(result.get(2));
						act.setIncreasescribe(result.get(3));
						act.setMemberscribe(result.get(4));
						act.setSinglescribe(result.get(5));
						act.setRepeatscribe(result.get(6));
					}
					list.add(act);
			    }
			}
		}
		return list;
	}
	
	//活动分析：自定义时间
	public List<Activity> getCustomToActivitys(String startTime, String endTime, Integer groupid, Integer activityId, int pageNumber, int pageSize){
		List<Activity> list = new ArrayList<Activity>();
		
		List<String> dayList;
		if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
			dayList = DateUtil.getDays(startTime, endTime, Constant.ZERO, Constant.ZERO);
		}else {
			dayList = DateUtil.getDays(startTime, endTime, pageNumber, pageSize);
		}
		if(dayList != null && dayList.size() > 0){
			
			for(int i = 0; i < dayList.size(); i++){
				String time = dayList.get(i);

				Activity act = new Activity();
				act.setTime(time);
				act.setActivitysscribe(0);
				act.setActivityscribe(0);
				act.setReadingscribe(0);
				act.setIncreasescribe(0);
				act.setMemberscribe(0);
				act.setSinglescribe(0);
				act.setRepeatscribe(0);
				List<Integer> result = getActivityDataByTime(groupid, activityId, time+" 00:00:00", time+" 23:59:59");
				
				if(result != null && result.size() == 7){
					act.setActivitysscribe(result.get(0));
					act.setActivityscribe(result.get(1));
					act.setReadingscribe(result.get(2));
					act.setIncreasescribe(result.get(3));
					act.setMemberscribe(result.get(4));
					act.setSinglescribe(result.get(5));
					act.setRepeatscribe(result.get(6));
				}
				list.add(act);
		  }
      }
	  return list;	
	}

	//板块点击分析：昨日指标
	public Menu getMenu(Integer groupid, String eventKey) {
		String yesterday = DateUtil.getDayBefore(1);//昨天
		String dayBeforeYesterday = DateUtil.getDayBefore(2);//前天

		List<Integer> day = getMenuDataByTime(groupid, eventKey, yesterday+"00:00:00", yesterday+"23:59:59");			
		List<Integer> day_before = getMenuDataByTime(groupid, eventKey, dayBeforeYesterday+"00:00:00", dayBeforeYesterday+"23:59:59");
		
		List<String> time = DateUtil.getWeekMonSun();
		
		Menu menu = new Menu();
		menu.setTime(yesterday);

		if(day != null && day.size() == 3){
			menu.setMenuSum(day.get(0));
			menu.setMenuWeixin(day.get(1));
			menu.setMenuMember(day.get(2));
		}else {
			menu.setMenuSum(0);
			menu.setMenuWeixin(0);
			menu.setMenuMember(0);
		}
		
		List<Double> menuWeixinScale = new ArrayList<Double>(); //微信粉丝点击数的日、周、月概率
		List<Double> menuMemberScale = new ArrayList<Double>(); //会员绑定后点击的日、周、月概率
		
		menuWeixinScale.add(getDouble(day_before.get(1), day.get(1)));
		menuMemberScale.add(getDouble(day_before.get(2), day.get(2)));
		
		yesterday = yesterday+"23:59:59";
		List<Integer> week = getMenuDataByTime(groupid, eventKey, time.get(2), time.get(3));
		List<Integer> week_before = getMenuDataByTime(groupid, eventKey, time.get(0), time.get(1));
		menuWeixinScale.add(getDouble(week_before.get(1), week.get(1)));
		menuMemberScale.add(getDouble(week_before.get(2), week.get(2)));
		
		List<String> months = DateUtil.getMonth();
		List<Integer> month = getMenuDataByTime(groupid, eventKey, months.get(2), months.get(3));//当前月
		List<Integer> month_before = getMenuDataByTime(groupid, eventKey ,months.get(0), months.get(1));//上个月

		menuWeixinScale.add(getDouble(month_before.get(1), month.get(1)));
		menuMemberScale.add(getDouble(month_before.get(2), month.get(2)));
		menu.setMenuMemberScale(menuMemberScale);
		menu.setMenuWeixinScale(menuWeixinScale);
		return menu;
	}

	//板块点击分析：通用查询方法，时间格式=xxxx-xx-xx
	public List<Integer> getMenuDataByTime(Integer groupid, String eventKey, String startTime, String endTime){
		StringBuffer sql = new StringBuffer();
		List<Integer> result = new ArrayList<Integer>();
		if(groupid > 0){
			if(eventKey.equals("0")){
				sql.append("select COUNT(*)as menuSum,(select COUNT(*) from t_mb_event_history d1 join t_mb_weixinuser d2 on d1.openid=d2.openid " +
						"where d2.groupid=1 and d1.clickTime between '"+startTime+"' and '"+endTime+"')as  menuWeixin," +
						"(select COUNT(*) from t_mb_event_history d1 join t_mb_weixinuser d2 on d1.openid=d2.openid where d2.groupid=1 and " +
						"d1.clickTime between '"+startTime+"' and '"+endTime+"' and d1.memberId is not null)as menuMember from t_mb_event_history d1 " +
								"join t_mb_weixinuser d2 on d1.openid=d2.openid where d2.groupid="+groupid+" and d1.clickTime <= '"+endTime+"'");
			}else {
				sql.append("select COUNT(*)as menuSum,(select COUNT(*) from t_mb_event_history d1 join t_mb_weixinuser d2 on d1.openid=d2.openid " +
						"where d2.groupid=1 and d1.clickTime between '"+startTime+"' and '"+endTime+"' and d1.eventKey="+eventKey+")as  menuWeixin," +
						"(select COUNT(*) from t_mb_event_history d1 join t_mb_weixinuser d2 on d1.openid=d2.openid where d2.groupid=1 and " +
						"d1.clickTime between '"+startTime+"' and '"+endTime+"' and d1.memberId is not null and d1.eventKey="+eventKey+")as menuMember from t_mb_event_history d1 " +
								"join t_mb_weixinuser d2 on d1.openid=d2.openid where d2.groupid="+groupid+" and d1.clickTime <= '"+endTime+"' and d1.eventKey="+eventKey);
			}
		}else {
			if(eventKey.equals("0")){
				sql.append("select COUNT(*)as menuSum,(select COUNT(*) from t_mb_event_history where clickTime between '"+startTime+"' and '"+endTime+"' " +
						")as menuWeixin,(select COUNT(*) from t_mb_event_history where clickTime between '"+startTime+"' " +
						"and '"+endTime+"' and memberId is not null)as menuMember from t_mb_event_history where clickTime <= '"+endTime+"'");
			}else {
				sql.append("select COUNT(*)as menuSum,(select COUNT(*) from t_mb_event_history where clickTime between '"+startTime+"' and '"+endTime+"' " +
						"and eventKey="+eventKey+")as menuWeixin,(select COUNT(*) from t_mb_event_history where clickTime between '"+startTime+"' and " +
						"'"+endTime+"' and memberId is not null and eventKey="+eventKey+")as menuMember from t_mb_event_history where clickTime <= '"+endTime+"' and eventKey="+eventKey);
			}
		}
		Query query = getSession().createSQLQuery(sql.toString()).addScalar("menuSum", StandardBasicTypes.INTEGER)
				.addScalar("menuWeixin", StandardBasicTypes.INTEGER).addScalar("menuMember", StandardBasicTypes.INTEGER);
		List temp = query.list();
		if(temp != null && temp.size() > 0){
			Object[] object = (Object[])temp.get(0);
			result.add((Integer)object[0]);
			result.add((Integer)object[1]);
			result.add((Integer)object[2]);
		}
		return result;
	}
	
	//板块点击分析：列表、导出csv
	public List<Menu> getMenus(Map<String, Object> data) {
		//查询条件
		Integer dateType = (Integer)data.get("dateType");
		Integer groupid = (Integer)data.get("groupid");
		String startTime = (String)data.get("startTime");
		String endTime = (String)data.get("endTime");
		List<String> days = (List<String>) data.get("days");
		int defaultCount = (Integer) data.get("defaultCount");
		String eventKey = (String) data.get("eventKey");
		
		//分页条件
		int pageNumber = (Integer)data.get("pageNumber");
		int pageSize = (Integer)data.get("pageSize");
		
		List<Menu> result = new ArrayList<Menu>();
		
		//先判断是否默认状态
		if(defaultCount > 0){
			result = getDefaultToMenus(days, groupid, eventKey);
		}else {
			if(dateType == 1){//周
				result = getWeekToMenus(groupid, eventKey, pageNumber, pageSize);
		    }else if(dateType == 2){//月
		    	result = getMonthToMenus(groupid, eventKey, pageNumber, pageSize);
		   }else if(dateType == 0 && !startTime.equals("") && !endTime.equals("")){//自定义时间
			   result = getCustomMenus(startTime, endTime, groupid, eventKey, pageNumber, pageSize);
		   }
	    }
		return result;
	}
	
	//板块点击分析：默认情况
	public List<Menu> getDefaultToMenus(List<String> days, Integer groupid, String eventKey){
		List<Menu> menus = new ArrayList<Menu>();
		int days_size = days.size();
		for(int i = 0; i < days_size; i++){
			String day = days.get(i);
			List<Integer> result = getMenuDataByTime(groupid, eventKey, day+" 00:00:00", day+" 23:59:59");
			Menu menu = new Menu();
			menu.setTime(day);
			menu.setMenuSum(0);
			menu.setMenuWeixin(0);
			menu.setMenuMember(0);
			if(result != null && result.size() == 3){
				menu.setMenuSum(result.get(0));
				menu.setMenuWeixin(result.get(1));
				menu.setMenuMember(result.get(2));
			}
			menus.add(menu);
		}
		return menus;
	}

	//板块点击分析：周数据
	public List<Menu> getWeekToMenus(Integer groupid, String eventKey, Integer pageNumber, Integer pageSize){
		List<Menu> list = new ArrayList<Menu>();
		
        List<String> weeks;
        if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
        	weeks = DateUtil.getWeeks(DateUtil.sdf_YMd.format(Constant.eventHistory.getClickTime()), Constant.ZERO, Constant.ZERO);//获取周
        }else {
        	weeks = DateUtil.getWeeks(DateUtil.sdf_YMd.format(Constant.eventHistory.getClickTime()), pageNumber, pageSize);//获取周
        }
		
		if(weeks != null && weeks.size() > 0){
			for(String day : weeks){		
				String[] days = day.split("=");
				day = day.replace("=", "至");
				
				Menu menu = new Menu();
				menu.setTime(day);
				menu.setMenuSum(0);
				menu.setMenuWeixin(0);
				menu.setMenuMember(0);
				List<Integer> result = getMenuDataByTime(groupid, eventKey, days[0]+" 00:00:00", days[1]+" 23:59:59");

				if(result != null && result.size() == 3){
					menu.setMenuSum(result.get(0));
					menu.setMenuWeixin(result.get(1));
					menu.setMenuMember(result.get(2));
				}
				list.add(menu);
			}
		}
		return list;
	}

	//板块点击分析：月数据
	public List<Menu> getMonthToMenus(Integer groupid, String eventKey, Integer pageNumber, Integer pageSize){
		List<Menu> list = new ArrayList<Menu>();
        String starTime = DateUtil.sdf_YMd.format(Constant.eventHistory.getClickTime());
        String endTime = DateUtil.sdf_YMd.format(new Date());
        
        List<String> months;
        if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
        	months = DateUtil.getMonths(starTime, endTime, Constant.ZERO, Constant.ZERO);
        }else {
        	months = DateUtil.getMonths(starTime, endTime, pageNumber, pageSize);
        }
 
		for(int i = 0; i < months.size(); i++){
			String[] values = months.get(i).split("=");
			
			List<Integer> result = getMenuDataByTime(groupid, eventKey, values[0], values[1]);
			
			Menu menu = new Menu();
			menu.setMenuSum(0);
			menu.setMenuWeixin(0);
			menu.setMenuMember(0);
			try {
				menu.setTime(DateUtil.sdf_YM_China.format(DateUtil.sdf_YM.parse(values[0])));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(result != null && result.size() == 3){
				menu.setMenuSum(result.get(0));
				menu.setMenuWeixin(result.get(1));
				menu.setMenuMember(result.get(2));
			}
			list.add(menu);
	    }
		return list;
	}
	
	//板块点击分析：自定义时间
	public List<Menu> getCustomMenus(String startTime, String endTime, Integer groupid, String eventKey, Integer pageNumber, Integer pageSize){
		List<Menu> list = new ArrayList<Menu>();
		
		List<String> dayList;
		if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
			dayList = DateUtil.getDays(startTime, endTime, Constant.ZERO, Constant.ZERO);
		}else {
			dayList = DateUtil.getDays(startTime, endTime, pageNumber, pageSize);
		}
		if(dayList != null && dayList.size() > 0){
			for(int i = 0; i < dayList.size(); i++){
				String time = dayList.get(i);

				Menu menu = new Menu();
				menu.setTime(time);
				menu.setMenuSum(0);
				menu.setMenuWeixin(0);
				menu.setMenuMember(0);
				List<Integer> result = getMenuDataByTime(groupid, eventKey, time+" 00:00:00", time+" 23:59:59");
				
				if(result != null && result.size() == 3){
					menu.setMenuSum(result.get(0));
					menu.setMenuWeixin(result.get(1));
					menu.setMenuMember(result.get(2));
				}
				list.add(menu);
		   }
      }
	  return list;	
	}
	
	//板块点击分析：列表数量
	public Integer getMenusCount(Map<String, Object> data) {
		//查询条件
		Integer dateType = (Integer)data.get("dateType");
		String startTime = (String)data.get("startTime");
		String endTime = (String)data.get("endTime");
		
		int count = 0;
		
		if(dateType == 0 && !startTime.equals("") && !endTime.equals("")){//自定义时间
			count = DateUtil.getDayCount(startTime, endTime);
		}else if(dateType == 1){//周
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE,   -1);
			count = DateUtil.getWeekCount(Constant.eventHistory.getClickTime(), cal.getTime()) + 2;
		}else if(dateType == 2){//月
			String start = DateUtil.sdf_YMd.format(Constant.eventHistory.getClickTime());
		    String end = DateUtil.sdf_YMd.format(new Date());
		    count = DateUtil.getMonthCount(start, end);
		}
		return count;
	}

	//会员信息
	public List<THdMember> getTHdMembers(Map<String, Object> data) {
		
		List<THdMember> memberList = new ArrayList<THdMember>();
		
		// 查询条件
		String cellPhone = (String) data.get("cellPhone");
		String cardNo = (String) data.get("cardNo");
		
		Integer pageNumber = (Integer) data.get("pageNumber");
		Integer pageSize = (Integer) data.get("pageSize");
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from (select row_number() over(order by d1.createTime asc) as rowNum, d1.*,tmw.bindDate as bindDate from t_hd_member d1 left join t_mb_weixinuser tmw on d1.memberId = tmw.memberId where 1=1");
		if(cellPhone != null && !cellPhone.equals("")){
			sql.append(" and d1.cellPhone='"+cellPhone+"'");
		}
		if(cardNo != null && !cardNo.equals("")){
			sql.append(" and d1.cardNo='"+cardNo+"'");
		}
		
		if(pageNumber > Constant.ZERO && pageSize > Constant.ZERO){
			sql.append(") as member where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));
		}else if(pageNumber != Constant.ZERO && pageSize != Constant.ZERO){
			sql.append(") as member where rowNum between 1 and 10");
		}else {
			sql.append(") as member");
		}
		Query query = getSession().createSQLQuery(sql.toString());//.addEntity(THdMember.class);
		
		List<Object[]> objList = query.list();
		
		for(Object[] obj:objList){
			THdMember member = new THdMember();
			
			Object obj1 = obj[1];
			if(obj1 != null){
				member.setMemberId(obj1.toString());
			}
			
			Object obj2 = obj[2];
			if(obj2 != null){
				member.setCardNo(obj2.toString());
			}
			
			Object obj3 = obj[3];
			if(obj3 != null){
				member.setCellPhone(obj3.toString());
			}
			
			Object obj4 = obj[4];
			if(obj4 != null){
				member.setName(obj4.toString());
			}
			
			Object obj5 = obj[5];
			if(obj5 != null){
				member.setBirthday((Date)obj5);
			}
			
			Object obj6 = obj[6];
			if(obj6 != null){
				member.setAddress(obj6.toString());
			}
			
			Object obj7 = obj[7];
			if(obj7 != null){
				member.setEmail(obj7.toString());
			}
			
			Object obj8 = obj[8];
			if(obj8 != null && !"".equals(obj8)){
				member.setCredit(Float.valueOf(obj8.toString()));
			}
			
			Object obj9 = obj[9];
			if(obj9 != null){
				member.setUpdateTime((Date)obj9);
			}
			
			Object obj10 = obj[10];
			if(obj10 != null){
				member.setCreateTime((Date)obj10);
			}
			
			Object obj11 = obj[11];
			if(obj11 != null){
				member.setBindDate((Date)obj11);
			}
			
			if(member.getMemberId() != null){
				memberList.add(member);
			}
			
		}
		
		
		
		return memberList;
	}

	//会员信息数量
	public Integer getTHdMembersCount(Map<String, Object> data) {
		// 查询条件
		String cellPhone = (String) data.get("cellPhone");
		String cardNo = (String) data.get("cardNo");
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from t_hd_member d1 where 1=1");
		if(cellPhone != null && !cellPhone.equals("")){
			sql.append(" and d1.cellPhone='"+cellPhone+"'");
		}
		if(cardNo != null && !cardNo.equals("")){
			sql.append(" and d1.cardNo='"+cardNo+"'");
		}
		Query query = getSession().createSQLQuery(sql.toString());
		return (Integer) query.uniqueResult();
	}
	
	
	/***
	 * @category 获取最近两天
	 */
	public List<List<Integer>> getRecentTwoDays(int groupId) {
		
		List<Integer> subList = new ArrayList<Integer>();
		List<Integer> sub2List = new ArrayList<Integer>();
		
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		
		//获取星期的第几天,1是星期日,7是星期六
		//int curDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
		
		//昨天
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 1);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		
		Date d1 = cal.getTime();
		
		//前天
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(new Date());
		cal1.set(Calendar.DAY_OF_YEAR,cal1.get(Calendar.DAY_OF_YEAR) - 2);
		cal1.set(Calendar.HOUR_OF_DAY,0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND,0);
		cal1.set(Calendar.MILLISECOND,0);
		
		Date d2 = cal1.getTime();
		
		//查询昨关注与取消关注
		StringBuffer sql = new StringBuffer();
		sql.append("select (");
		sql.append("select count(a.openid) from t_mb_weixinuser a where a.subscribe = 1 and convert(varchar(10), a.subscribe_time, 120) = ? ");
		if(groupId > 0){
			sql.append(" and a.groupid  = ? ");
		}
		sql.append(" group by a.openid ) as subscribeCount,");
		sql.append("(");
		sql.append("select count(a.openid) from t_mb_weixinuser a where a.subscribe = 0 and convert(varchar(10), a.subscribe_time, 120) = ? ");
		if(groupId > 0){
			sql.append(" and a.groupid = ? ");
		}
		sql.append(" group by a.openid) as unsubscribeCount");
		
		
		Query q = getSession().createSQLQuery(sql.toString());
		
		if(groupId > 0){
			q.setParameter(0, sdf_YMd.format(d1));
			q.setParameter(1, groupId);
			q.setParameter(2, sdf_YMd.format(d1));
			q.setParameter(3, groupId);
		}else{
			q.setParameter(0, sdf_YMd.format(d1));
			q.setParameter(1, sdf_YMd.format(d1));
		}
		
		System.out.println("昨天===> " + sdf_YMd.format(d1));
		
		List<Object[]> resList = q.list();
		if(resList != null && resList.size() > 0){
				
			if(resList != null && resList.size() > 0 && resList.get(0) != null && resList.get(0)[0] != null){
				subList.add(Integer.parseInt(resList.get(0)[0].toString()));//关注
			}else{
				subList.add(0);
			}
			if(resList != null && resList.size() > 0 && resList.get(0) != null && resList.get(0)[1] != null){
				subList.add(Integer.parseInt(resList.get(0)[1].toString()));//未关注
			}else{
				subList.add(0);
			}
		}
		
		
		
		//查询前天关注与取消关注
				StringBuffer sql1 = new StringBuffer();
				sql1.append("select (");
				sql1.append("select count(a.openid) from t_mb_weixinuser a where a.subscribe = 1 and convert(varchar(10), a.subscribe_time, 120) = ? ");
				if(groupId > 0){
					sql1.append(" and a.groupid = ? ");
				}
				sql1.append(" group by a.openid ) as subscribeCount,");
				sql1.append("(");
				sql1.append("select count(a.openid) from t_mb_weixinuser a where a.subscribe = 0 and convert(varchar(10), a.subscribe_time, 120) = ? ");
				if(groupId > 0){
					sql1.append(" and a.groupid = ? ");
				}
				sql1.append(" group by a.openid) as unsubscribeCount");
				
				Query q1 = getSession().createSQLQuery(sql.toString());
				
				if(groupId > 0){
					q1.setParameter(0, sdf_YMd.format(d2));
					q1.setParameter(1,  groupId);
					q1.setParameter(2, sdf_YMd.format(d2));
					q1.setParameter(3,  groupId);
				}else{
					q1.setParameter(0, sdf_YMd.format(d2));
					q1.setParameter(1, sdf_YMd.format(d2));
				}
				
				System.out.println("昨天===> " + sdf_YMd.format(d2));
				
				List<Object[]> res1List = q1.list();
				if(res1List != null && res1List.size() > 0){
					if(res1List != null && res1List.size() > 0 && res1List.get(0) != null && res1List.get(0)[0] != null){
						sub2List.add(Integer.parseInt(res1List.get(0)[0].toString()));//关注
					}else{
						sub2List.add(0);
					}
					
					if(res1List != null && res1List.size() > 0 && res1List.get(0) != null && res1List.get(0)[1] != null){
						sub2List.add(Integer.parseInt(res1List.get(0)[1].toString()));//未关注
					}else{
						sub2List.add(0);//未关注
					}
				}	
		
		
		List<List<Integer>> returnList = new ArrayList<List<Integer>>();
		
		returnList.add(subList);
		returnList.add(sub2List);
		
		return returnList;
	}

	
	
	/***
	 * @category 获取最近两周
	 */
	public List<List<Integer>> getRecentTwoWeeks(int groupId) {
		List<Integer> subList = new ArrayList<Integer>();
		List<Integer> sub2List = new ArrayList<Integer>();
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		
		//获取星期的第几天,1是星期日,7是星期六
		int curDay = cal.get(Calendar.DAY_OF_WEEK)-1;
		
		System.out.println(cal.get(Calendar.DAY_OF_YEAR));
		System.out.println(cal.get(Calendar.DAY_OF_WEEK));
		
		//上一周结束日期
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - curDay);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		
		Date d1 = cal.getTime();
		
		StringBuffer d1Str = new StringBuffer();
		d1Str.append(sdf_YMd.format(d1)).append(" 23:59");
		
		//上一周开始日期
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(d1);
		cal1.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 6);
		cal1.set(Calendar.HOUR_OF_DAY,0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND,0);
		cal1.set(Calendar.MILLISECOND,0);
		
		Date d2 = cal1.getTime();
		
		StringBuffer d2Str = new StringBuffer();
		d2Str.append(sdf_YMd.format(d2)).append(" 00:00");
		
		System.out.println(d2Str.toString()+"=========="+d1Str.toString());
		
		
		//前一周结束日期
		Calendar cal2 = new GregorianCalendar();
		cal2.set(Calendar.DAY_OF_YEAR, cal1.get(Calendar.DAY_OF_YEAR) - 1);
		cal2.set(Calendar.HOUR_OF_DAY,0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND,0);
		cal2.set(Calendar.MILLISECOND,0);
				
		Date d3 = cal2.getTime();
		
		StringBuffer d3Str = new StringBuffer();
		d3Str.append(sdf_YMd.format(d3)).append(" 23:59");
		
		
		//前一周开始日期
		Calendar cal3 = new GregorianCalendar();
		cal3.setTime(d3);
		cal3.set(Calendar.DAY_OF_YEAR, cal2.get(Calendar.DAY_OF_YEAR) - 6);
		cal3.set(Calendar.HOUR_OF_DAY,0);
		cal3.set(Calendar.MINUTE, 0);
		cal3.set(Calendar.SECOND,0);
		cal3.set(Calendar.MILLISECOND,0);
						
		Date d4 = cal3.getTime();
		
		StringBuffer d4Str = new StringBuffer();
		d4Str.append(sdf_YMd.format(d4)).append(" 00:00");
		
		
		System.out.println(d4Str.toString()+"=========="+d3Str.toString());
		
		
		//查询昨关注与取消关注
		StringBuffer sql = new StringBuffer();
		sql.append("select (");
		sql.append("select count(a.openid) from t_mb_weixinuser a where a.subscribe = 1 and convert(varchar(10), a.subscribe_time, 120) between ? and ?");
		if(groupId > 0){
			sql.append(" and a.groupid  = ? ");
		}
		sql.append("  ) as subscribeCount,");
		sql.append("(");
		sql.append("select count(a.openid) from t_mb_weixinuser a where a.subscribe = 0 and convert(varchar(10), a.subscribe_time, 120) between ? and ? ");
		if(groupId > 0){
			sql.append(" and a.groupid = ? ");
		}
		sql.append(" ) as unsubscribeCount");
		
		
		Query q = getSession().createSQLQuery(sql.toString());
		
		if(groupId > 0){
			q.setParameter(0, d2Str.toString());
			q.setParameter(1, d1Str.toString());
			q.setParameter(2, groupId);
			q.setParameter(3, d4Str.toString());
			q.setParameter(4, d3Str.toString());
			q.setParameter(5, groupId);
		}else{
			q.setParameter(0, d2Str.toString());
			q.setParameter(1, d1Str.toString());
			q.setParameter(2, d4Str.toString());
			q.setParameter(3, d3Str.toString());
		}
		List<Object[]> resList = q.list();
		if(resList != null && resList.size() > 0){
				
			if(resList != null && resList.size() > 0 && resList.get(0) != null && resList.get(0)[0] != null){
				subList.add(Integer.parseInt(resList.get(0)[0].toString()));//关注
			}else{
				subList.add(0);
			}
			if(resList != null && resList.size() > 0 && resList.get(0) != null && resList.get(0)[1] != null){
				subList.add(Integer.parseInt(resList.get(0)[1].toString()));//未关注
			}else{
				subList.add(0);
			}
		}
		
		
		
		//查询前天关注与取消关注
				StringBuffer sql1 = new StringBuffer();
				sql1.append("select (");
				sql1.append("select count(a.openid) from t_mb_weixinuser a where a.subscribe = 1 and convert(varchar(10), a.subscribe_time, 120) between ? and ? ");
				if(groupId > 0){
					sql1.append(" and a.groupid = ? ");
				}
				sql1.append(" ) as subscribeCount,");
				sql1.append("(");
				sql1.append("select count(a.openid) from t_mb_weixinuser a where a.subscribe = 0 and convert(varchar(10), a.subscribe_time, 120) between ? and ? ");
				if(groupId > 0){
					sql1.append(" and a.groupid = ? ");
				}
				sql1.append(" ) as unsubscribeCount");
				
				Query q1 = getSession().createSQLQuery(sql.toString());
				
				if(groupId > 0){
					q1.setParameter(0, d2Str.toString());
					q1.setParameter(1, d1Str.toString());
					q1.setParameter(2, groupId);
					q1.setParameter(3, d4Str.toString());
					q1.setParameter(4, d3Str.toString());
					q1.setParameter(5, groupId);
				}else{
					q1.setParameter(0, d2Str.toString());
					q1.setParameter(1, d1Str.toString());
					q1.setParameter(2, d4Str.toString());
					q1.setParameter(3, d3Str.toString());
				}
				
				List<Object[]> res1List = q1.list();
				if(res1List != null && res1List.size() > 0){
					if(res1List != null && res1List.size() > 0 && res1List.get(0) != null && res1List.get(0)[1] != null){
						sub2List.add(Integer.parseInt(res1List.get(0)[0].toString()));//关注
					}else{
						sub2List.add(0);
					}
					
					if(res1List != null && res1List.size() > 0 && res1List.get(0) != null && res1List.get(0)[1] != null){
						sub2List.add(Integer.parseInt(res1List.get(0)[1].toString()));//未关注
					}else{
						sub2List.add(0);//未关注
					}
				}	
		
		
		List<List<Integer>> returnList = new ArrayList<List<Integer>>();
		
		returnList.add(subList);
		returnList.add(sub2List);
		
		return returnList;
	}

	
	
	
	/***
	 * @category 获取最近两月
	 */
	public List<List<Integer>> getRecentTwoMonths(int groupId) {
		List<Integer> subList = new ArrayList<Integer>();
		List<Integer> sub2List = new ArrayList<Integer>();
		
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		
		//获取星期的第几天,1是星期日,7是星期六
		//int curDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
		
		//昨天
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		
		Date d1 = cal.getTime();
		
		//前天
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(new Date());
		cal1.set(Calendar.MONTH,cal1.get(Calendar.MONTH) - 2);
		cal1.set(Calendar.HOUR_OF_DAY,0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND,0);
		cal1.set(Calendar.MILLISECOND,0);
		
		Date d2 = cal1.getTime();
		
		//查询昨关注与取消关注
		StringBuffer sql = new StringBuffer();
		sql.append("select (");
		sql.append("select count(a.openid) from t_mb_weixinuser a where a.subscribe = 1 and convert(varchar(8), a.subscribe_time, 120) = ? ");
		if(groupId > 0){
			sql.append(" and a.groupid  = ? ");
		}
		sql.append(" group by a.openid ) as subscribeCount,");
		sql.append("(");
		sql.append("select count(a.openid) from t_mb_weixinuser a where a.subscribe = 0 and convert(varchar(8), a.subscribe_time, 120) = ? ");
		if(groupId > 0){
			sql.append(" and a.groupid = ? ");
		}
		sql.append(" group by a.openid) as unsubscribeCount");
		
		
		Query q = getSession().createSQLQuery(sql.toString());
		
		if(groupId > 0){
			q.setParameter(0, sdf_YM.format(d1));
			q.setParameter(1, groupId);
			q.setParameter(2, sdf_YM.format(d1));
			q.setParameter(3, groupId);
		}else{
			q.setParameter(0, sdf_YM.format(d1));
			q.setParameter(1, sdf_YM.format(d1));
		}
		List<Object[]> resList = q.list();
		if(resList != null && resList.size() > 0){
				
			if(resList != null && resList.size() > 0 && resList.get(0) != null && resList.get(0)[0] != null){
				subList.add(Integer.parseInt(resList.get(0)[0].toString()));//关注
			}else{
				subList.add(0);
			}
			if(resList != null && resList.size() > 0 && resList.get(0) != null && resList.get(0)[1] != null){
				subList.add(Integer.parseInt(resList.get(0)[1].toString()));//未关注
			}else{
				subList.add(0);
			}
		}
		
		
		
		//查询前天关注与取消关注
				StringBuffer sql1 = new StringBuffer();
				sql1.append("select (");
				sql1.append("select count(a.openid) from t_mb_weixinuser a where a.subscribe = 1 and convert(varchar(8), a.subscribe_time, 120) = ? ");
				if(groupId > 0){
					sql1.append(" and a.groupid = ? ");
				}
				sql1.append(" group by a.openid ) as subscribeCount,");
				sql1.append("(");
				sql1.append("select count(a.openid) from t_mb_weixinuser a where a.subscribe = 0 and convert(varchar(8), a.subscribe_time, 120) = ? ");
				if(groupId > 0){
					sql1.append(" and a.groupid = ? ");
				}
				sql1.append(" group by a.openid) as unsubscribeCount");
				
				Query q1 = getSession().createSQLQuery(sql.toString());
				
				if(groupId > 0){
					q1.setParameter(0, sdf_YM.format(d2));
					q1.setParameter(1,  groupId);
					q1.setParameter(2, sdf_YM.format(d2));
					q1.setParameter(3,  groupId);
				}else{
					q1.setParameter(0, sdf_YM.format(d2));
					q1.setParameter(1, sdf_YM.format(d2));
				}
				
				List<Object[]> res1List = q1.list();
				if(res1List != null && res1List.size() > 0){
					if(res1List != null && res1List.size() > 0 && res1List.get(0) != null && res1List.get(0)[1] != null){
						sub2List.add(Integer.parseInt(res1List.get(0)[0].toString()));//关注
					}else{
						sub2List.add(0);
					}
					
					if(res1List != null && res1List.size() > 0 && res1List.get(0) != null && res1List.get(0)[1] != null){
						sub2List.add(Integer.parseInt(res1List.get(0)[1].toString()));//未关注
					}else{
						sub2List.add(0);//未关注
					}
				}	
		
		
		List<List<Integer>> returnList = new ArrayList<List<Integer>>();
		
		returnList.add(subList);
		returnList.add(sub2List);
		
		return returnList;
	}

	@Override
	public List<Object[]> getWeixinCountProc(int groupId) {
		
		Query q = getSession().createSQLQuery("exec wechatdbCount ?,?,?,?");
		
		q.setParameter(0, groupId);
		q.setParameter(1,0);
		q.setParameter(2, "");
		q.setParameter(3, "");
		
		return q.list();
	}

	@Override
	public List<Object[]> getNewsCountProc(int groupId,int newsId) {
		Query q = getSession().createSQLQuery("exec GialenNewsCount ?,?,?,?,?");
		
		q.setParameter(0, groupId);
		q.setParameter(1,newsId);
		q.setParameter(2,0);
		q.setParameter(3, "");
		q.setParameter(4, "");
		
		return q.list();
	}

	@Override
	public List<Object[]> getMemberCountProc(int groupId) {
		Query q = getSession().createSQLQuery("exec GialenMemberCount ?,?,?,?");
		
		q.setParameter(0, groupId);
		q.setParameter(1,0);
		q.setParameter(2, "");
		q.setParameter(3, "");
		
		return q.list();
	}

	@Override
	public List<Object[]> getActivityCountProc(int groupId,int activityId,Date startDate,Date endDate) {
		Query q = getSession().createSQLQuery("exec GialenActivityCount ?,?,?,?,?");
		
		
		String startDateStr = sdf_YMd.format(startDate);
		String endDateStr = sdf_YMd.format(endDate);
		
		q.setParameter(0, groupId);
		q.setParameter(1,activityId);
		q.setParameter(2, 0);
		q.setParameter(3, startDateStr);
		q.setParameter(4, endDateStr);
		
		return q.list();
	}

	@Override
	public List<Object[]> getSignCountProc(int groupId,int storeId) {
		Query q = getSession().createSQLQuery("exec GialenSignCount ?,?,?,?,?");
		
		q.setParameter(0, groupId);
		q.setParameter(1,storeId);
		q.setParameter(2, 0);
		q.setParameter(3, "");
		q.setParameter(4, "");
		
		return q.list();
	}

	@Override
	public List<Object[]> getMenuCountProc(int groupId) {
		Query q = getSession().createSQLQuery("exec GialenEventCount ?,?,?,?");
		
		q.setParameter(0, groupId);
		q.setParameter(1,0);
		q.setParameter(2, "");
		q.setParameter(3, "");
		
		return q.list();
	}

	@Override
	public TMbActivity getActivityById(int activityId) {
		return (TMbActivity) getSession().get(TMbActivity.class,activityId);
	}
	
}