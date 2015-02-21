package com.focusx.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.attilax.SafeVal;
import com.focusx.dao.impl.DataAnalysisDaoImpl;
import com.focusx.entity.THdMember;
import com.focusx.entity.TMbActivity;
import com.focusx.entity.TMbNews;
import com.focusx.entity.TUserUsers;
import com.focusx.entity.dataAnalysis.Activity;
import com.focusx.entity.dataAnalysis.Member;
import com.focusx.entity.dataAnalysis.Menu;
import com.focusx.entity.dataAnalysis.News;
import com.focusx.entity.dataAnalysis.Sign;
import com.focusx.entity.dataAnalysis.WeixinUser;
import com.focusx.service.ActivityManagerService;
import com.focusx.service.BranchManagerService;
import com.focusx.service.DataAnalysisService;
import com.focusx.service.MaterialManagerService;
import com.focusx.service.WeixinUserManagerService;
import com.focusx.util.CSVUtils;
import com.focusx.util.Constant;
import com.focusx.util.DateUtil;
import com.focusx.util.JsonUtil;
import com.focusx.util.MyCacher;
import com.focusx.util.OperLogUtil4gialen;
import com.focusx.util.Page;
import com.focusx.util.PageUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 *  数据分析类，包含了粉丝分析 、会员分析、图文分析、活动分析、门店签到分析
 *  @日期 2014-03-28
 */
public class DataAnalysisAction extends ActionSupport{
	
	protected static Logger logger = Logger.getLogger("DataAnalysisAction");
	private static final long serialVersionUID = 1L;
	private DataAnalysisService dataAnalysisService;
	
	private BranchManagerService branchManagerService;
	private WeixinUserManagerService weixinUserManagerService;
	private ActivityManagerService activityManagerService;
	private MaterialManagerService materialManagerService;
	
	private Page page;// 分页对象
	private String p;
	private String ps;
	
	//粉丝分析
	private Integer weixinDateType;   //时间类型
	private Integer groupid;          //分公司ID
	private String weixinStartTime;   //开始时间
	private String weixinEndTime;     //结束时间
	private WeixinUser weixinUser;
	private List<WeixinUser> weixinUsers;
	private Integer defaultCount;     //默认数量
	
	//图文分析
	private Integer newsDateType;  //时间类型
	private Integer newsGroupid;  //分公司ID
	private String newsStartTime;  //开始时间
	private String newsEndTime;    //结束时间
	private News news;
	private List<News> newses;
	private Integer newsDefaultCount;//默认数量
	private Integer newsId;           //图文主键ID
	private TMbNews tmbnews;
	
	//会员绑定
	private Integer memberDateType;   //时间类型
	private Integer memberGroupid;    //分公司ID
	private String memberStartTime;   //开始时间
	private String memberEndTime;     //结束时间
	private Member member;
	private List<Member> members;
	private Integer memberDefaultCount;     //默认数量
	
	//门店签到
	private Integer signDateType;   //时间类型
	private Integer signGroupid;    //分公司ID
	private String signStartTime;   //开始时间
	private String signEndTime;     //结束时间
	private Sign sign;
	private List<Sign> signs;
	private Integer signDefaultCount;     //默认数量
	private Integer storesId;       //门店ID
	
	//活动分析
	private Integer activityDateType;   //时间类型
	private Integer activityGroupid;    //分公司ID
	private String activityStartTime;   //开始时间
	private String activityEndTime;     //结束时间
	private Activity activity;
	private List<Activity> activitys;    
	private Integer activityDefaultCount;  //默认数量
	private Integer activityId;          //活动ID     
	
	//板块点击分析
	private Integer menuDateType;   //时间类型
	private Integer menuGroupid;    //分公司ID
	private String menuStartTime;   //开始时间
	private String menuEndTime;     //结束时间
	private Menu menu;
	private List<Menu> menus;    
	private Integer menuDefaultCount;  //默认数量
	private String eventKey;          //菜单key值
	
	public String cellPhone;           //会员手机号码
	public String cardNo;              //会员卡号
	public List<THdMember> tHdMembers; 
	
	private Map<String,Integer>  countMap;
	private Map<String,Double>   precentMap;
	
	/**
	 *  粉丝分析 
	 *  规则：根据登陆账号所属分公司来展示该分公司的数据，如果是管理员可以看到全部分公司
	 */
	public String listWeixins(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			defaultCount = 0;
			List<String> days = null;
			//默认情况下显示30天记录
			if((weixinDateType == null || weixinDateType == 0) && (weixinStartTime == null || weixinStartTime.equals("")) 
					&& (weixinEndTime == null || weixinEndTime.equals(""))){
				defaultCount = 30;
				days = DateUtil.getTimeToDay();
			}
			
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getIsSystem() == Constant.ONE){//判断是否是管理员
				if(groupid == null){
					groupid = 0;
				}
			}else{
				groupid = user.getDefaultGroup();
			}

			weixinUser = dataAnalysisService.getWeixin(groupid);
			
			if(weixinDateType == null){
				weixinDateType = 0;
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			page = new Page<WeixinUser>(PageUtil.PAGE_SIZE);
			int[] pageParams = PageUtil.init(page, request);
			int pageNumber = pageParams[0];// 第几页
			int pageSize = pageParams[1];// 每页记录数
			// 分页条件
			data.put("pageNumber", pageNumber);
			data.put("pageSize", pageSize);
			
			// 查询条件
			data.put("weixinDateType", weixinDateType);
			data.put("groupid", groupid);
			data.put("weixinStartTime", weixinStartTime);
			data.put("weixinEndTime", weixinEndTime);
			data.put("defaultCount", defaultCount);
			data.put("days", days);
			//countMap = (Map<String, Integer>) MyCacher.getInstance().getCache("getWeixinCount");
			if(countMap == null){
				countMap = dataAnalysisService.getWeixinCount(data);
				
				//缓存12小时
				MyCacher.getInstance().putCache("getWeixinCount",countMap,60*60*12);
			}
			//precentMap = (Map<String,Double>) MyCacher.getInstance().getCache("getWeixinCountPre");
			if(countMap != null && precentMap == null){
				
				precentMap = new HashMap<String,Double>();
				
				precentMap.put("new_day_pre",DataAnalysisDaoImpl.getDouble(countMap.get("new_focus_preyesterday"),countMap.get("new_focus_yesterday")));
				
				precentMap.put("cancel_day_pre",DataAnalysisDaoImpl.getDouble(countMap.get("cancel_focus_preyesterday"),countMap.get("cancel_focus_yesterday")));
				
				precentMap.put("pure_day_pre",DataAnalysisDaoImpl.getDouble(countMap.get("pure_focus_preyesterday"),countMap.get("pure_focus_yesterday")));
				
				precentMap.put("total_day_pre",DataAnalysisDaoImpl.getDouble(countMap.get("total_focus_preyesterday"),countMap.get("total_focus_yesterday")));
				
				
				precentMap.put("new_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("new_focus_preweek"),countMap.get("new_focus_week")));
				
				precentMap.put("cancel_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("cancel_focus_preweek"),countMap.get("cancel_focus_week")));
				
				precentMap.put("pure_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("pure_focus_preweek"),countMap.get("pure_focus_week")));
				
				precentMap.put("total_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("total_focus_preweek"),countMap.get("total_focus_week")));
				
				
				precentMap.put("new_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("new_focus_premonth"),countMap.get("new_focus_month")));
				
				precentMap.put("cancel_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("cancel_focus_premonth"),countMap.get("cancel_focus_month")));
				
				precentMap.put("pure_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("pure_focus_premonth"),countMap.get("pure_focus_month")));
				
				precentMap.put("total_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("total_focus_premonth"),countMap.get("total_focus_month")));
				
				
				//缓存12小时
				MyCacher.getInstance().putCache("getWeixinCountPre",precentMap,60*60*12);
				
			}
			
			weixinUsers = dataAnalysisService.getWeixins(page, data);
			
			 OperLogUtil4gialen.log(   "粉丝分析查询:"+SafeVal.val( ""), ServletActionContext.getRequest());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listWeixins";
	}
	
	/**
	 *  根据条件查找数据并导出到csv
	 */
	public void exportcsv(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			defaultCount = 0;
			List<String> days = null;
			//默认情况下显示30天记录
			if((weixinDateType == null || weixinDateType == 0) && (weixinStartTime == null || weixinStartTime.equals("")) 
					&& (weixinEndTime == null || weixinEndTime.equals(""))){
				defaultCount = 30;
				days = DateUtil.getTimeToDay();
			}
			
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getIsSystem() == Constant.ONE){//判断是否是管理员
				if(groupid == null){
					groupid = 0;
				}
			}else{
				groupid = user.getDefaultGroup();
			}
			if(weixinDateType == null){
				weixinDateType = 0;
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("weixinDateType", weixinDateType);
			data.put("groupid", groupid);
			data.put("weixinStartTime", weixinStartTime);
			data.put("weixinEndTime", weixinEndTime);
			data.put("days", days);
			data.put("defaultCount", defaultCount);
			data.put("pageNumber", Constant.ZERO);
			data.put("pageSize", Constant.ZERO);
			
			List<WeixinUser> dataList = dataAnalysisService.getCsvData(data);
	       
			List<String> dataListString = new ArrayList<String>();
			dataListString.add("时间, 新关注人数, 取消关注人数, 净增关注人数, 累积关注人数");
			
			int newsubscribe = 0;
			int cancelsubscribe = 0;
			int netsubscribe = 0;
			
			for(WeixinUser weixinUser : dataList){
				StringBuffer buf = new StringBuffer();
				buf.append(weixinUser.getTime()+",");
				buf.append(weixinUser.getNewsubscribe()+",");
				buf.append(weixinUser.getCancelsubscribe()+",");
				buf.append(weixinUser.getNewsubscribe()-weixinUser.getCancelsubscribe()+",");
				buf.append(weixinUser.getAllsubscribe());
				dataListString.add(buf.toString());
				newsubscribe += weixinUser.getNewsubscribe();
				cancelsubscribe += weixinUser.getCancelsubscribe();
				netsubscribe +=(weixinUser.getNewsubscribe()-weixinUser.getCancelsubscribe());
			}
			dataListString.add("总计, "+newsubscribe+", "+cancelsubscribe+", "+netsubscribe+", ");
			String fileName = "";

			String groupname = "";
			if(groupid > 0){
				groupname = branchManagerService.getGroup(groupid).getGroupname();
			}
			if(!groupname.equals("")){
				fileName = DateUtil.sdf_YMd.format(new Date())+"_"+groupname+"_粉丝数据.csv";
			}else {
				fileName = DateUtil.sdf_YMd.format(new Date())+"_总公司_粉丝数据.csv";
			}

	        CSVUtils.exportCsv(new File(Constant.otherPath+fileName), dataListString);
	        
	        response.setContentType("application/csv");
	        response.setHeader("Location",fileName);
			response.setCharacterEncoding("UTF-8");
	        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO_8859_1")); 
	        OutputStream outputStream = response.getOutputStream();
	        InputStream inputStream = new FileInputStream(Constant.otherPath + fileName);
	        byte[] buffer = new byte[2048];
	        int i = -1;
	        while ((i = inputStream.read(buffer)) != -1) {
	        	outputStream.write(buffer, 0, i);
	        }
	        outputStream.flush();
	        outputStream.close();
	        inputStream.close();
	        File file = new File(Constant.otherPath + fileName);
	        if(file.exists()){
	        	file.delete();
	        }
	   	 OperLogUtil4gialen.log(   "导出csv:"+SafeVal.val( ""), ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发生异常位置:DataAnalysisAction.exportcsv()", e);
		}
	}
	
	/**
	 *  会员绑定分析
	 */
	public String listMembers(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			memberDefaultCount = 0;
			List<String> days = null;
			//默认情况下显示30天记录
			if((memberDateType == null || memberDateType == 0) && (memberStartTime == null || memberStartTime.equals("")) 
					&& (memberEndTime == null || memberEndTime.equals(""))){
				memberDefaultCount = 30;
				days = DateUtil.getTimeToDay();
			}
			
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getIsSystem() == Constant.ONE){//判断是否是管理员
				if(memberGroupid == null){
					memberGroupid = 0;
				}
			}else{
				memberGroupid = user.getDefaultGroup();
			}

			member = dataAnalysisService.getMember(memberGroupid);
			
			if(memberDateType == null){
				memberDateType = 0;
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			page = new Page<Member>(PageUtil.PAGE_SIZE);
			int[] pageParams = PageUtil.init(page, request);
			int pageNumber = pageParams[0];// 第几页
			int pageSize = pageParams[1];// 每页记录数
			// 分页条件
			data.put("pageNumber", pageNumber);
			data.put("pageSize", pageSize);
			
			// 查询条件
			data.put("dateType", memberDateType);
			data.put("groupid", memberGroupid);
			data.put("startTime", memberStartTime);
			data.put("endTime", memberEndTime);
			data.put("defaultCount", memberDefaultCount);
			data.put("days", days);
			
			
			//countMap = (Map<String, Map<String, Double>>) MyCacher.getInstance().getCache("getMembers");
			if(countMap == null){
				countMap = dataAnalysisService.getMembersCount(data);
				
				if(countMap != null && precentMap == null){
					
					precentMap = new HashMap<String,Double>();
					
					NumberFormat numberFormat = NumberFormat.getInstance();
					numberFormat.setMaximumFractionDigits(2);
					double d = ((double)countMap.get("activity_member_count") / (double)countMap.get("total_bind_member_count")) * 100;
					precentMap.put("bind_member_pre",Double.valueOf(numberFormat.format(d)));
					
					precentMap.put("bind_member_yesterday_pre",DataAnalysisDaoImpl.getDouble(countMap.get("bind_member_preyesterday"),countMap.get("bind_member_yesterday")));
					
					precentMap.put("bind_member_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("bind_member_preweek"),countMap.get("bind_member_week")));
					
					precentMap.put("bind_member_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("bind_member_premonth"),countMap.get("bind_member_month")));
					
					
					//
					precentMap.put("activity_member_yesterday_pre",DataAnalysisDaoImpl.getDouble(countMap.get("activity_member_preyesterday"),countMap.get("activity_member_yesterday")));

					precentMap.put("activity_member_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("activity_member_preweek"),countMap.get("activity_member_week")));
					
					precentMap.put("activity_member_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("activity_member_premonth"),countMap.get("activity_member_month")));
					
					//缓存12小时
					MyCacher.getInstance().putCache("getMemberCountPre",precentMap,60*60*12);
					
				}
				
				//缓存12小时
				MyCacher.getInstance().putCache("getMembers",countMap,60*60*12);
				
				
				
			}
			
			members = dataAnalysisService.getMembers(page, data);
			
			 OperLogUtil4gialen.log(   "查询会员绑定分析:"+SafeVal.val( ""), ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listMembers";
	}
	
	/**
	 *  根据条件查找数据并导出到csv
	 */
	public void exportcsvToMember(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			memberDefaultCount = 0;
			List<String> days = null;
			//默认情况下显示30天记录
			if((memberDateType == null || memberDateType == 0) && (memberStartTime == null || memberStartTime.equals("")) 
					&& (memberEndTime == null || memberEndTime.equals(""))){
				memberDefaultCount = 30;
				days = DateUtil.getTimeToDay();
			}
			
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getIsSystem() == Constant.ONE){//判断是否是管理员
				if(memberGroupid == null){
					memberGroupid = 0;
				}
			}else{
				memberGroupid = user.getDefaultGroup();
			}
			
			if(memberDateType == null){
				memberDateType = 0;
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("dateType", memberDateType);
			data.put("groupid", memberGroupid);
			data.put("startTime", memberStartTime);
			data.put("endTime", memberEndTime);
			data.put("defaultCount", memberDefaultCount);
			data.put("days", days);
			data.put("pageNumber", Constant.ZERO);
			data.put("pageSize", Constant.ZERO);
			
			List<Member> dataList = dataAnalysisService.getCsvDataToMember(data);
	       
			List<String> dataListString = new ArrayList<String>();
			dataListString.add("时间, 会员绑定数量, 活跃会员数量, 活跃会员比例");
			
			int memberBinding = 0;
			int activeMember = 0;
			
			for(Member member : dataList){
				StringBuffer buf = new StringBuffer();
				buf.append(member.getTime()+",");
				buf.append(member.getMemberBinding()+",");
				buf.append(member.getActiveMember()+",");
				buf.append(member.getActiveScale()+",");
				dataListString.add(buf.toString());
				memberBinding += member.getMemberBinding();
				activeMember += member.getActiveMember();
			}
			dataListString.add("总计, "+memberBinding+", "+activeMember+", ");
			
			String fileName = "";

			String groupname = "";
			if(memberGroupid > 0){
				groupname = branchManagerService.getGroup(memberGroupid).getGroupname();
			}
			if(!groupname.equals("")){
				fileName = DateUtil.sdf_YMd.format(new Date())+"_"+groupname+"_会员绑定数据.csv";
			}else {
				fileName = DateUtil.sdf_YMd.format(new Date())+"_总公司_会员绑定数据.csv";
			}

	        CSVUtils.exportCsv(new File(Constant.otherPath+fileName), dataListString);
	        
	        response.setContentType("application/csv");
	        response.setHeader("Location",fileName);
			response.setCharacterEncoding("UTF-8");
	        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO_8859_1")); 
	        OutputStream outputStream = response.getOutputStream();
	        InputStream inputStream = new FileInputStream(Constant.otherPath + fileName);
	        byte[] buffer = new byte[2048];
	        int i = -1;
	        while ((i = inputStream.read(buffer)) != -1) {
	        	outputStream.write(buffer, 0, i);
	        }
	        outputStream.flush();
	        outputStream.close();
	        inputStream.close();
	        File file = new File(Constant.otherPath + fileName);
	        if(file.exists()){
	        	file.delete();
	        }
	        
	   	 OperLogUtil4gialen.log(   "导出会员绑定分析数据csv:"+SafeVal.val( ""), ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发生异常位置:DataAnalysisAction.exportcsvToMember()", e);
		}
	}
	
	/**
	 *  图文分析
	 */
	public String listNews(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			newsDefaultCount = 0;
			List<String> days = null;
			//默认情况下显示30天记录
			if((newsDateType == null ||newsDateType == 0) && (newsStartTime == null || newsStartTime.equals("")) 
					&& (newsEndTime == null || newsEndTime.equals(""))){
				newsDefaultCount = 30;
				days = DateUtil.getTimeToDay();
			}
			
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getIsSystem() == Constant.ONE){//判断是否是管理员
				if(newsGroupid == null){
					newsGroupid = 0;
				}
			}else{
				newsGroupid = user.getDefaultGroup();
			}
			if(newsId == null){
				newsId = 0;
			}else if(tmbnews == null && newsId > 0){
				tmbnews = materialManagerService.getTMbNewsById(newsId);
			}
			
			news = dataAnalysisService.getNews(newsGroupid, newsId);
			
			if(newsDateType == null){
				newsDateType = 0;
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			page = new Page<News>(PageUtil.PAGE_SIZE);
			int[] pageParams = PageUtil.init(page, request);
			int pageNumber = pageParams[0];// 第几页
			int pageSize = pageParams[1];// 每页记录数
			// 分页条件
			data.put("pageNumber", pageNumber);
			data.put("pageSize", pageSize);
			
			// 查询条件
			data.put("dateType", newsDateType);
			data.put("groupid", newsGroupid);
			data.put("newsStartTime", newsStartTime);
			data.put("newsEndTime", newsEndTime);
			data.put("defaultCount", newsDefaultCount);
			data.put("days", days);
			data.put("newsId", newsId);
			
			//countMap = (Map<String, Map<String, Double>>) MyCacher.getInstance().getCache("getNewsesCount");
			if(countMap == null){
				countMap = dataAnalysisService.getNewsCount(data);
				
					if(countMap != null && precentMap == null){
					
								precentMap = new HashMap<String,Double>();
								
								
								precentMap.put("news_convert_rate_pre", DataAnalysisDaoImpl.getDoubleToNews(countMap.get("news_read_people_count"), countMap.get("news_people_count")));
								
								precentMap.put("news_send_yesterday_pre",DataAnalysisDaoImpl.getDouble(countMap.get("news_send_preyesterday"),countMap.get("news_send_yesterday")));
								
								//图文阅读人数/送达人数
								precentMap.put("news_convert_rate_yesterday_pre", DataAnalysisDaoImpl.getDoubleToNews(countMap.get("news_read_people_yesterday"),countMap.get("news_people_yesterday")));
								precentMap.put("news_convert_rate_preyesterday_pre", DataAnalysisDaoImpl.getDoubleToNews(countMap.get("news_read_people_preyesterday"),countMap.get("news_people_preyesterday")));
								
								precentMap.put("news_convert_yesterday_pre", DataAnalysisDaoImpl.getDouble(precentMap.get("news_convert_rate_preyesterday_pre").intValue(),precentMap.get("news_convert_rate_yesterday_pre").intValue()));
								
								precentMap.put("news_send_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("news_send_preweek"),countMap.get("news_send_week")));
								
								precentMap.put("news_convert_rate_week_pre", DataAnalysisDaoImpl.getDoubleToNews(countMap.get("news_read_people_week"), countMap.get("news_people_week")));
								precentMap.put("news_convert_rate_preweek_pre", DataAnalysisDaoImpl.getDoubleToNews(countMap.get("news_read_people_preweek"),countMap.get("news_people_preweek")));
								
								precentMap.put("news_convert_week_pre", DataAnalysisDaoImpl.getDouble(precentMap.get("news_convert_rate_preweek_pre").intValue(),precentMap.get("news_convert_rate_week_pre").intValue()));
								
								precentMap.put("news_send_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("news_send_month"),countMap.get("news_send_premonth")));
								
								precentMap.put("news_convert_rate_month_pre", DataAnalysisDaoImpl.getDoubleToNews(countMap.get("news_read_people_month"), countMap.get("news_people_month")));
								precentMap.put("news_convert_rate_premonth_pre", DataAnalysisDaoImpl.getDoubleToNews(countMap.get("news_read_people_premonth"), countMap.get("news_people_premonth")));
								
								precentMap.put("news_convert_month_pre", DataAnalysisDaoImpl.getDouble(precentMap.get("news_convert_rate_premonth_pre").intValue(),precentMap.get("news_convert_rate_month_pre").intValue()));
								
								//
								precentMap.put("news_people_yesterday_pre",DataAnalysisDaoImpl.getDouble(countMap.get("news_people_preyesterday"),countMap.get("news_people_yesterday")));

								precentMap.put("news_people_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("news_people_preweek"),countMap.get("news_people_week")));
								
								precentMap.put("news_people_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("news_people_premonth"),countMap.get("news_people_month")));
								
								//
								precentMap.put("news_read_yesterday_pre",DataAnalysisDaoImpl.getDouble(countMap.get("news_read_preyesterday"),countMap.get("news_read_yesterday")));

								precentMap.put("news_read_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("news_read_preweek"),countMap.get("news_read_week")));
								
								precentMap.put("news_read_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("news_read_premonth"),countMap.get("news_read_month")));
								
								//
								precentMap.put("news_read_people_yesterday_pre",DataAnalysisDaoImpl.getDouble(countMap.get("news_read_people_preyesterday"),countMap.get("news_read_people_yesterday")));

								precentMap.put("news_read_people_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("news_read_people_preweek"),countMap.get("news_read_people_week")));
								
								precentMap.put("news_read_people_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("news_read_people_premonth"),countMap.get("news_read_people_month")));
								
								
								//缓存12小时
								MyCacher.getInstance().putCache("getNewsCountPre",precentMap,60*60*12);
								
								
					}
				
				//缓存12小时
				MyCacher.getInstance().putCache("getNewsesCount",countMap,60*60*12);
			}
			
			newses = dataAnalysisService.getNewses(page, data);
			 OperLogUtil4gialen.log(   "图文分析查询:"+SafeVal.val( ""), ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listNews";
	}
	
	public void getAllNews(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			List<TMbNews> news = materialManagerService.getMaterialByGroupid(newsGroupid);
			JSONArray jsonArray = JSONArray.fromObject(news);			
			String json_data = jsonArray != null ? jsonArray.toString() : "";
			JsonUtil.getInstance().write(response, json_data);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  根据条件查找数据并导出到csv
	 */
	public void exportcsvToNews(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			newsDefaultCount = 0;
			List<String> days = null;
			//默认情况下显示30天记录
			if((newsDateType == null ||newsDateType == 0) && (newsStartTime == null || newsStartTime.equals("")) 
					&& (newsEndTime == null || newsEndTime.equals(""))){
				newsDefaultCount = 30;
				days = DateUtil.getTimeToDay();
			}
			
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getIsSystem() == Constant.ONE){//判断是否是管理员
				if(newsGroupid == null){
					newsGroupid = 0;
				}
			}else{
				newsGroupid = user.getDefaultGroup();
			}
			
			if(newsDateType == null){
				newsDateType = 0;
			}
			
			if(newsId == null){
				newsId = 0;
			}else if(tmbnews == null && newsId > 0){
				tmbnews = materialManagerService.getTMbNewsById(newsId);
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("dateType", newsDateType);
			data.put("groupid", newsGroupid);
			data.put("newsStartTime", newsStartTime);
			data.put("newsEndTime", newsEndTime);
			data.put("days", days);
			data.put("defaultCount", newsDefaultCount);
			data.put("pageNumber", Constant.ZERO);
			data.put("pageSize", Constant.ZERO);
			data.put("newsId", newsId);
			
			List<News> dataList = dataAnalysisService.getCsvDataToNews(data);
	       
			List<String> dataListString = new ArrayList<String>();
			if(tmbnews != null){
				dataListString.add("图文名称："+tmbnews.getTitle()+",,");
			}
			dataListString.add("时间, 送达次数, 阅读次数, 送达人数, 阅读人数, 图文转化率");
			
			int deliveryTime = 0;
			int readingTime = 0;			
			int deliveryWeixin = 0;
			int readingWeixin = 0;
			for(News news : dataList){
				StringBuffer buf = new StringBuffer();
				buf.append(news.getTime()+",");
				buf.append(news.getDeliveryTime()+",");
				buf.append(news.getReadingTime()+",");
				buf.append(news.getDeliveryWeixin()+",");
				buf.append(news.getReadingWeixin()+",");
				buf.append(news.getConversionRate()+"%");
				dataListString.add(buf.toString());
				deliveryTime += news.getDeliveryTime();
				readingTime += news.getReadingTime();
				deliveryWeixin += news.getDeliveryWeixin();
				readingWeixin += news.getReadingWeixin();
			}
			dataListString.add("总计, "+deliveryTime+", "+readingTime+", "+deliveryWeixin+", "+readingWeixin+", ");
			
			String fileName = "";

			String groupname = "";
			if(newsGroupid > 0){
				groupname = branchManagerService.getGroup(newsGroupid).getGroupname();
			}
			if(!groupname.equals("")){
				fileName = DateUtil.sdf_YMd.format(new Date())+"_"+groupname+"_图文数据.csv";
			}else {
				fileName = DateUtil.sdf_YMd.format(new Date())+"_总公司_图文数据.csv";
			}

	        CSVUtils.exportCsv(new File(Constant.otherPath+fileName), dataListString);
	        
	        response.setContentType("application/csv");
	        response.setHeader("Location",fileName);
			response.setCharacterEncoding("UTF-8");
	        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO_8859_1")); 
	        OutputStream outputStream = response.getOutputStream();
	        InputStream inputStream = new FileInputStream(Constant.otherPath + fileName);
	        byte[] buffer = new byte[2048];
	        int i = -1;
	        while ((i = inputStream.read(buffer)) != -1) {
	        	outputStream.write(buffer, 0, i);
	        }
	        outputStream.flush();
	        outputStream.close();
	        inputStream.close();
	        File file = new File(Constant.otherPath + fileName);
	        if(file.exists()){
	        	file.delete();
	        }
	        OperLogUtil4gialen.log(   "导出图文分析数据csv:"+SafeVal.val( ""), ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发生异常位置:DataAnalysisAction.exportcsvToNews()", e);
		}
	}
	
	/**
	 *  活动分析
	 */
	public String listActivitys(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			activityDefaultCount = 0;
			List<String> days = null;
			//默认情况下显示30天记录
			if((activityDateType == null || activityDateType == 0) && (activityStartTime == null || activityStartTime.equals("")) 
					&& (activityEndTime == null || activityEndTime.equals(""))){
				activityDefaultCount = 30;
				days = DateUtil.getTimeToDay();
			}
			
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getIsSystem() == Constant.ONE){//判断是否是管理员
				if(activityGroupid == null){
					activityGroupid = 0;
				}
			}else{
				activityGroupid = user.getDefaultGroup();
			}
			if(activityDateType == null){
				activityDateType = 0;
			}
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			List<TMbActivity> list = activityManagerService.getAllActivity(format.format(new Date()));
			if(activityId == null && list != null){
				activityId = list.get(0).getId();
			}else if(activityId == null){
				activityId = 0; 
			}

			activity = dataAnalysisService.getActivity(activityGroupid, activityId);
			
			Map<String, Object> data = new HashMap<String, Object>();
			page = new Page<Activity>(PageUtil.PAGE_SIZE);
			int[] pageParams = PageUtil.init(page, request);
			int pageNumber = pageParams[0];// 第几页
			int pageSize = pageParams[1];// 每页记录数
			// 分页条件
			data.put("pageNumber", pageNumber);
			data.put("pageSize", pageSize);
			
			// 查询条件
			data.put("dateType", activityDateType);
			data.put("groupid", activityGroupid);
			data.put("startTime", activityStartTime);
			data.put("endTime", activityEndTime);
			data.put("defaultCount", activityDefaultCount);
			data.put("days", days);
			data.put("activityId", activityId);
			
			
			//countMap = (Map<String, Integer>) MyCacher.getInstance().getCache("getActivitysCount"+activityId);
			if(countMap == null){
				countMap = dataAnalysisService.getActivitysCount(data);
				
				if(countMap != null && precentMap == null){
					
					precentMap = new HashMap<String,Double>();
					
					precentMap.put("join_yesterday_pre",DataAnalysisDaoImpl.getDouble(countMap.get("join_preyesterday"),countMap.get("join_yesterday")));
					
					precentMap.put("read_yesterday_pre",DataAnalysisDaoImpl.getDouble(countMap.get("read_preyesterday"),countMap.get("read_yesterday")));
					
					precentMap.put("increase_yesterday_pre",DataAnalysisDaoImpl.getDouble(countMap.get("increase_preyesterday"),countMap.get("increase_yesterday")));
					
					precentMap.put("member_join_yesterday_pre",DataAnalysisDaoImpl.getDouble(countMap.get("member_join_preyesterday"),countMap.get("member_join_yesterday")));
					
					precentMap.put("total_join_yesterday_pre",DataAnalysisDaoImpl.getDouble(countMap.get("total_join_preyesterday"),countMap.get("total_join_yesterday")));
					
					
					//
					precentMap.put("join_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("join_preweek"),countMap.get("join_week")));
					
					precentMap.put("read_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("read_preweek"),countMap.get("read_week")));
					
					precentMap.put("increase_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("increase_preweek"),countMap.get("increase_week")));
					
					precentMap.put("member_join_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("member_join_preweek"),countMap.get("member_join_week")));
					
					precentMap.put("total_join_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("total_join_preweek"),countMap.get("total_join_week")));					
					
					
					//
					
					precentMap.put("join_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("join_premonth"),countMap.get("join_month")));
					
					precentMap.put("read_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("read_premonth"),countMap.get("read_month")));
					
					precentMap.put("increase_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("increase_premonth"),countMap.get("increase_month")));
					
					precentMap.put("member_join_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("member_join_premonth"),countMap.get("member_join_month")));
					
					precentMap.put("total_join_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("total_join_premonth"),countMap.get("total_join_month")));
					//缓存12小时
					MyCacher.getInstance().putCache("getMemberCountPre",precentMap,60*60*12);
					
				}
				
				//缓存12小时
				MyCacher.getInstance().putCache("getActivitysCount"+activityId,countMap,60*60*12);
			}
			
			activitys = dataAnalysisService.getActivitys(page, data);
			 OperLogUtil4gialen.log(   "查询活动分析:"+SafeVal.val( ""), ServletActionContext.getRequest());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listActivitys";
	}

	/**  根据条件查找数据并导出到csv
	 */
	public void exportcsvToActivity(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			activityDefaultCount = 0;
			List<String> days = null;
			//默认情况下显示30天记录
			if((activityDateType == null || activityDateType == 0) && (activityStartTime == null || activityStartTime.equals("")) 
					&& (activityEndTime == null || activityEndTime.equals(""))){
				activityDefaultCount = 30;
				days = DateUtil.getTimeToDay();
			}
			
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getIsSystem() == Constant.ONE){//判断是否是管理员
				if(activityGroupid == null){
					activityGroupid = 0;
				}
			}else{
				activityGroupid = user.getDefaultGroup();
			}
			if(activityDateType == null){
				activityDateType = 0;
			}
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			List<TMbActivity> list = activityManagerService.getAllActivity(format.format(new Date()));
			if(activityId == null && list != null){
				activityId = list.get(0).getId();
			}else if(activityId == null){
				activityId = 0; 
			}
			TMbActivity tmbactivity = new TMbActivity();
			if(activityId > 0){
				tmbactivity = activityManagerService.getActivityById(activityId);
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("dateType", activityDateType);
			data.put("groupid", activityGroupid);
			data.put("startTime", activityStartTime);
			data.put("endTime", activityEndTime);
			data.put("defaultCount", activityDefaultCount);
			data.put("days", days);
			data.put("activityId", activityId);
			data.put("pageNumber", Constant.ZERO);
			data.put("pageSize", Constant.ZERO);
			
			List<Activity> dataList = dataAnalysisService.getCsvDataToActivity(data);
	       
			List<String> dataListString = new ArrayList<String>();
			dataListString.add("时间, 微信粉丝参与数, 阅读人数, 粉丝增长人数, 单次参与个数, 重复扫描个数, 绑定会员参与数, 微信粉丝参与总数");
			
			int activityscribe = 0;
			int readingscribe = 0;
			int increasescribe = 0;
			int singlescribe = 0;
			int repeatscribe = 0;
			int memberscribe = 0;
			
			for(Activity act : dataList){
				StringBuffer buf = new StringBuffer();
				buf.append(act.getTime()+",").append(act.getActivityscribe()+",").append(act.getReadingscribe()+",").append(act.getIncreasescribe()+",")
				   .append(act.getSinglescribe()+",").append(act.getRepeatscribe()+",").append(act.getMemberscribe()+",").append(act.getActivitysscribe()+",");
				dataListString.add(buf.toString());
				activityscribe += act.getActivityscribe();
				readingscribe += act.getReadingscribe();
				increasescribe += act.getIncreasescribe();
				singlescribe += act.getSinglescribe();
				repeatscribe += act.getRepeatscribe();
				memberscribe += act.getMemberscribe();
			}
			dataListString.add("总计, "+activityscribe+", "+readingscribe+", "+increasescribe+", "+singlescribe+", "+repeatscribe+", "+memberscribe+", ");
			String fileName = "";

			String groupname = "";
			if(activityGroupid > 0){
				groupname = branchManagerService.getGroup(activityGroupid).getGroupname();
			}
			if(!groupname.equals("")){
				if(tmbactivity != null){
					fileName = DateUtil.sdf_YMd.format(new Date())+"_"+groupname+"_"+tmbactivity.getActName()+"_活动数据.csv";
				}else {
					fileName = DateUtil.sdf_YMd.format(new Date())+"_"+groupname+"_活动数据.csv";
				}
			}else {
				if(tmbactivity != null){
					fileName = DateUtil.sdf_YMd.format(new Date())+"_总公司_"+tmbactivity.getActName()+"_活动数据.csv";
				}else {
					fileName = DateUtil.sdf_YMd.format(new Date())+"_总公司_活动数据.csv";
				}
			}

	        CSVUtils.exportCsv(new File(Constant.otherPath+fileName), dataListString);
	        
	        response.setContentType("application/csv");
	        response.setHeader("Location",fileName);
			response.setCharacterEncoding("UTF-8");
	        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO_8859_1")); 
	        OutputStream outputStream = response.getOutputStream();
	        InputStream inputStream = new FileInputStream(Constant.otherPath + fileName);
	        byte[] buffer = new byte[2048];
	        int i = -1;
	        while ((i = inputStream.read(buffer)) != -1) {
	        	outputStream.write(buffer, 0, i);
	        }
	        outputStream.flush();
	        outputStream.close();
	        inputStream.close();
	        File file = new File(Constant.otherPath + fileName);
	        if(file.exists()){
	        	file.delete();
	        }
	   	 OperLogUtil4gialen.log(   "导出csv:"+SafeVal.val( ""), ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发生异常位置:DataAnalysisAction.exportcsvToActivity()", e);
		}
	}
	
	/**
	 *  门店签到分析
	 */
	public String listSigns(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			signDefaultCount = 0;
			List<String> days = null;
			//默认情况下显示30天记录
			if((signDateType == null || signDateType == 0) && (signStartTime == null || signStartTime.equals("")) 
					&& (signEndTime == null || signEndTime.equals(""))){
				signDefaultCount = 30;
				days = DateUtil.getTimeToDay();
			}
			
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getIsSystem() == Constant.ONE){//判断是否是管理员
				if(signGroupid == null){
					signGroupid = 0;
				}
			}else{
				signGroupid = user.getDefaultGroup();
			}
			if(storesId == null){
				storesId = 0;
			}

			sign = dataAnalysisService.getSign(signGroupid, storesId);
			
			if(signDateType == null){
				signDateType = 0;
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			page = new Page<Sign>(PageUtil.PAGE_SIZE);
			int[] pageParams = PageUtil.init(page, request);
			int pageNumber = pageParams[0];// 第几页
			int pageSize = pageParams[1];// 每页记录数
			// 分页条件
			data.put("pageNumber", pageNumber);
			data.put("pageSize", pageSize);
			
			// 查询条件
			data.put("dateType",signDateType);
			data.put("groupid", signGroupid);
			data.put("startTime", signStartTime);
			data.put("endTime", signEndTime);
			data.put("defaultCount", signDefaultCount);
			data.put("days", days);
			data.put("storesId", storesId);
			
			
			//countMap = (Map<String, Map<String, Double>>) MyCacher.getInstance().getCache("getSignsCount");
			if(countMap == null){
				countMap = dataAnalysisService.getSignsCount(data);
				
				
				if(countMap != null && precentMap == null){
					
					precentMap = new HashMap<String,Double>();
					
					precentMap.put("sign_yesterday_pre",DataAnalysisDaoImpl.getDouble(countMap.get("sign_preyesterday"),countMap.get("sign_yesterday")));
					
					precentMap.put("sign_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("sign_preweek"),countMap.get("sign_week")));
					
					precentMap.put("sign_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("sign_premonth"),countMap.get("sign_month")));
					
					//缓存12小时
					MyCacher.getInstance().putCache("getSignsCountPre",precentMap,60*60*12);
					
					
				}
				
				
				//缓存12小时
				MyCacher.getInstance().putCache("getSignsCount",countMap,60*60*12);
			}
			
			
			signs = dataAnalysisService.getSigns(page, data);
			 OperLogUtil4gialen.log(   "门店签到分析查询:"+SafeVal.val( ""), ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listSigns";
	}
	
	/**
	 *  根据条件查找数据并导出到csv
	 */
	public void exportcsvToSign(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			signDefaultCount = 0;
			List<String> days = null;
			//默认情况下显示30天记录
			if((signDateType == null || signDateType == 0) && (signStartTime == null || signStartTime.equals("")) 
					&& (signEndTime == null || signEndTime.equals(""))){
				signDefaultCount = 30;
				days = DateUtil.getTimeToDay();
			}
			
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getIsSystem() == Constant.ONE){//判断是否是管理员
				if(signGroupid == null){
					signGroupid = 0;
				}
			}else{
				signGroupid = user.getDefaultGroup();
			}
			
			if(signDateType == null){
				signDateType = 0;
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("dateType", signDateType);
			data.put("groupid", signGroupid);
			data.put("startTime", signStartTime);
			data.put("endTime", signEndTime);
			data.put("days", days);
			data.put("defaultCount", signDefaultCount);
			data.put("pageNumber", Constant.ZERO);
			data.put("pageSize", Constant.ZERO);
			
			List<Sign> dataList = dataAnalysisService.getCsvDataToSign(data);
	       
			List<String> dataListString = new ArrayList<String>();

			dataListString.add("时间, 会员签到数量");
			int signscribe = 0;
			for(Sign sign : dataList){
				StringBuffer buf = new StringBuffer();
				buf.append(sign.getTime()+",").append(sign.getSignscribe());
				dataListString.add(buf.toString());
				signscribe += sign.getSignscribe();
			}
			dataListString.add("总计,"+signscribe);
			String fileName = "";

			String groupname = "";
			if(signGroupid > 0){
				groupname = branchManagerService.getGroup(signGroupid).getGroupname();
			}
			if(!groupname.equals("")){
				fileName = DateUtil.sdf_YMd.format(new Date())+"_"+groupname+"_门店签到数据.csv";
			}else {
				fileName = DateUtil.sdf_YMd.format(new Date())+"_总公司_门店签到数据.csv";
			}

	        CSVUtils.exportCsv(new File(Constant.otherPath+fileName), dataListString);
	        
	        response.setContentType("application/csv");
	        response.setHeader("Location",fileName);
			response.setCharacterEncoding("UTF-8");
	        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO_8859_1")); 
	        OutputStream outputStream = response.getOutputStream();
	        InputStream inputStream = new FileInputStream(Constant.otherPath + fileName);
	        byte[] buffer = new byte[2048];
	        int i = -1;
	        while ((i = inputStream.read(buffer)) != -1) {
	        	outputStream.write(buffer, 0, i);
	        }
	        outputStream.flush();
	        outputStream.close();
	        inputStream.close();
	        File file = new File(Constant.otherPath + fileName);
	        if(file.exists()){
	        	file.delete();
	        }
	        
	        OperLogUtil4gialen.log(   "导出签到数据csv:"+SafeVal.val( ""), ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发生异常位置:DataAnalysisAction.exportcsvToNews()", e);
		}
	}
	
	/**
	 *  板块点击分析
	 */
	public String listMenus(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			menuDefaultCount = 0;
			List<String> days = null;
			//默认情况下显示30天记录
			if((menuDateType == null || menuDateType == 0) && (menuStartTime == null || menuStartTime.equals("")) 
					&& (menuEndTime == null || menuEndTime.equals(""))){
				menuDefaultCount = 30;
				days = DateUtil.getTimeToDay();
			}
			
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getIsSystem() == Constant.ONE){//判断是否是管理员
				if(menuGroupid == null){
					menuGroupid = 0;
				}
			}else{
				menuGroupid = user.getDefaultGroup();
			}
			if(eventKey == null){
				eventKey = "0";
			}

			menu = dataAnalysisService.getMenu(menuGroupid, eventKey);
			
			if(menuDateType == null){
				menuDateType = 0;
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			page = new Page<Sign>(PageUtil.PAGE_SIZE);
			int[] pageParams = PageUtil.init(page, request);
			int pageNumber = pageParams[0];// 第几页
			int pageSize = pageParams[1];// 每页记录数
			// 分页条件
			data.put("pageNumber", pageNumber);
			data.put("pageSize", pageSize);
			
			// 查询条件
			data.put("dateType",menuDateType);
			data.put("groupid", menuGroupid);
			data.put("startTime", menuStartTime);
			data.put("endTime", menuEndTime);
			data.put("defaultCount", menuDefaultCount);
			data.put("days", days);
			data.put("eventKey", eventKey);
			
			
			//countMap = (Map<String, Map<String, Double>>) MyCacher.getInstance().getCache("getMenusCount");
			if(countMap == null){
				countMap = dataAnalysisService.getMenusCount(data);
				
				if(countMap != null && precentMap == null){
					
					precentMap = new HashMap<String,Double>();
					
					precentMap.put("menu_click_yesterday_pre",DataAnalysisDaoImpl.getDouble(countMap.get("menu_click_preyesterday"),countMap.get("menu_click_yesterday")));
					
					precentMap.put("menu_click_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("menu_click_preweek"),countMap.get("menu_click_week")));
					
					precentMap.put("menu_click_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("menu_click_premonth"),countMap.get("menu_click_month")));
					
					
					precentMap.put("menu_member_click_yesterday_pre",DataAnalysisDaoImpl.getDouble(countMap.get("menu_member_click_preyesterday"),countMap.get("menu_member_click_yesterday")));
					
					precentMap.put("menu_member_click_week_pre",DataAnalysisDaoImpl.getDouble(countMap.get("menu_member_click_preweek"),countMap.get("menu_member_click_week")));
					
					precentMap.put("menu_member_click_month_pre",DataAnalysisDaoImpl.getDouble(countMap.get("menu_member_click_premonth"),countMap.get("menu_member_click_month")));
					
					//缓存12小时
					MyCacher.getInstance().putCache("getMenuCountPre",precentMap,60*60*12);
					
					
				}
				
				//缓存12小时
				MyCacher.getInstance().putCache("getMenusCount",countMap,60*60*12);
			}
			
			menus = dataAnalysisService.getMenus(page, data);
			 OperLogUtil4gialen.log(   "板块点击分析查询:"+SafeVal.val( ""), ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listMenus";
	}

	//获取自定义菜单的数据
	public void getMenuJson(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(Constant.path).append("menu.properties");
			HashMap<String,String> map = new HashMap<String,String>();
			File f = new File(builder.toString());
			if(f.exists()){
				FileInputStream fis = new FileInputStream(f);
				Properties properties =  new Properties();
				properties.load(fis);
				Set set = properties.keySet();
				Iterator<String> it = set.iterator();  
				while (it.hasNext()) {
					String key = it.next();
					map.put(key, properties.getProperty(key));
				}
			}
			JSONArray jsonArray = JSONArray.fromObject(map);
			String json_data = jsonArray != null ? jsonArray.toString() : "";
			JsonUtil.getInstance();
			JsonUtil.write(response, json_data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//根据key获取value
	public static String getMenuOne(String key){
		String value = "";
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(Constant.path).append("menu.properties");
			HashMap<String,String> map = new HashMap<String,String>();
			File f = new File(builder.toString());
			if(f.exists()){
				FileInputStream fis = new FileInputStream(f);
				Properties properties =  new Properties();
				properties.load(fis);
				value = properties.getProperty(key);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 *  根据条件查找数据并导出到csv
	 */
	public void exportcsvToMenu(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			menuDefaultCount = 0;
			List<String> days = null;
			//默认情况下显示30天记录
			if((menuDateType == null || menuDateType == 0) && (menuStartTime == null || menuStartTime.equals("")) 
					&& (menuEndTime == null || menuEndTime.equals(""))){
				menuDefaultCount = 30;
				days = DateUtil.getTimeToDay();
			}
			
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getIsSystem() == Constant.ONE){//判断是否是管理员
				if(menuGroupid == null){
					menuGroupid = 0;
				}
			}else{
				menuGroupid = user.getDefaultGroup();
			}
			
			if(menuDateType == null){
				menuDateType = 0;
			}
			if(eventKey == null){
				eventKey = "0";
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("dateType", menuDateType);
			data.put("groupid", menuGroupid);
			data.put("startTime", menuStartTime);
			data.put("endTime", menuEndTime);
			data.put("days", days);
			data.put("defaultCount", menuDefaultCount);
			data.put("pageNumber", Constant.ZERO);
			data.put("pageSize", Constant.ZERO);
			data.put("eventKey", eventKey);
			List<Menu> dataList = dataAnalysisService.getCsvDataToMenu(data);
	       
			List<String> dataListString = new ArrayList<String>();

			dataListString.add("时间, 微信粉丝点击数, 会员绑定后点击数, 微信粉丝点击总数");
			
			int menuWeixin = 0;
			int menuMember = 0;
			for(Menu menu : dataList){
				StringBuffer buf = new StringBuffer();
				buf.append(menu.getTime()+",").append(menu.getMenuWeixin()+",").append(menu.getMenuMember()+",").append(menu.getMenuSum()+",");
				dataListString.add(buf.toString());
				menuWeixin += menu.getMenuWeixin();
				menuMember += menu.getMenuMember();
			}
			dataListString.add("总计, "+menuWeixin+", "+menuMember+",");
			StringBuffer fileName = new StringBuffer();

			String groupname = "";
			String value = getMenuOne(eventKey);
			if(menuGroupid > 0){
				groupname = branchManagerService.getGroup(menuGroupid).getGroupname();
			}
			if(!groupname.equals("")){
				fileName.append(DateUtil.sdf_YMd.format(new Date())+"_"+groupname);
			}else {
				fileName.append(DateUtil.sdf_YMd.format(new Date())+"_总公司");
			}
			if(value != null && !value.equals("")){
				fileName.append("_"+value);
			}
			fileName.append("_板块点击数据.csv");
	        CSVUtils.exportCsv(new File(Constant.otherPath+fileName.toString()), dataListString);
	        
	        response.setContentType("application/csv");
	        response.setHeader("Location",fileName.toString());
			response.setCharacterEncoding("UTF-8");
	        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.toString().getBytes("GB2312"), "ISO_8859_1")); 
	        OutputStream outputStream = response.getOutputStream();
	        InputStream inputStream = new FileInputStream(Constant.otherPath + fileName.toString());
	        byte[] buffer = new byte[2048];
	        int i = -1;
	        while ((i = inputStream.read(buffer)) != -1) {
	        	outputStream.write(buffer, 0, i);
	        }
	        outputStream.flush();
	        outputStream.close();
	        inputStream.close();
	        File file = new File(Constant.otherPath + fileName.toString());
	        if(file.exists()){
	        	file.delete();
	        }
	        OperLogUtil4gialen.log(   "导出分析数据csv:"+SafeVal.val( ""), ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发生异常位置:DataAnalysisAction.exportcsvToMenu()", e);
		}
	}
	
	/**
	 *  会员信息查询
	 */
	public String memberDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> data = new HashMap<String, Object>();
		page = new Page<THdMember>(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(page, request);
		int pageNumber = pageParams[0];// 第几页
		int pageSize = pageParams[1];// 每页记录数
		// 分页条件
		data.put("pageNumber", pageNumber);
		data.put("pageSize", pageSize);
		
		// 查询条件
		data.put("cellPhone",StringUtils.trim(cellPhone));
		data.put("cardNo", StringUtils.trim(cardNo));
		data.put("startTime", memberStartTime);
		data.put("endTime", memberEndTime);
		try {
			tHdMembers = dataAnalysisService.getTHdMembers(page, data);
			 OperLogUtil4gialen.log(   "会员信息查询:"+SafeVal.val( ""), ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "memberDetail";
	}
	
	/**
	 *  会员信息导出csv
	 */
	public void exportexcel(){
		Map<String, Object> data = new HashMap<String, Object>();
		// 分页条件
		data.put("pageNumber", Constant.ZERO);
		data.put("pageSize", Constant.ZERO);
		
		// 查询条件
		data.put("cellPhone",StringUtils.trim(cellPhone));
		data.put("cardNo", StringUtils.trim(cardNo));
		data.put("startTime", memberStartTime);
		data.put("endTime", memberEndTime);
		
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			int groupid = 0;
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getIsSystem() != Constant.ONE){//判断是否是管理员
				groupid = user.getDefaultGroup();
			}
			
			List<THdMember> result = dataAnalysisService.getTHdMembers(page, data);
			
			List<String> dataListString = new ArrayList<String>();

			dataListString.add("会员卡号, 会员姓名, 手机号码, 会员生日, 积分, 地址, 邮箱");
			
			for(THdMember member : result){
				StringBuffer buf = new StringBuffer();
				buf.append(member.getCardNo()+",");
				buf.append(member.getName()+",");
				buf.append(member.getCellPhone()+",");                                  
				buf.append(DateUtil.sdf_YMd_China.format(member.getBirthday())+",");
				buf.append(member.getCredit()+",");
				buf.append(member.getAddress()+",");
				buf.append(member.getEmail()+",");
				dataListString.add(buf.toString());
			}
			String fileName = "";

			String groupname = "";
			if(groupid > 0){
				groupname = branchManagerService.getGroup(groupid).getGroupname();
			}
			if(!groupname.equals("")){
				fileName = DateUtil.sdf_YMd.format(new Date())+"_"+groupname+"_会员信息数据.csv";
			}else {
				fileName = DateUtil.sdf_YMd.format(new Date())+"_总公司_会员信息数据.csv";
			}

	        CSVUtils.exportCsv(new File(Constant.otherPath+fileName), dataListString);
	        
	        response.setContentType("application/csv");
	        response.setHeader("Location",fileName);
			response.setCharacterEncoding("UTF-8");
	        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO_8859_1")); 
	        OutputStream outputStream = response.getOutputStream();
	        InputStream inputStream = new FileInputStream(Constant.otherPath + fileName);
	        byte[] buffer = new byte[2048];
	        int i = -1;
	        while ((i = inputStream.read(buffer)) != -1) {
	        	outputStream.write(buffer, 0, i);
	        }
	        outputStream.flush();
	        outputStream.close();
	        inputStream.close();
	        File file = new File(Constant.otherPath + fileName);
	        if(file.exists()){
	        	file.delete();
	        }
	        OperLogUtil4gialen.log(   "导出会员信息:"+SafeVal.val( ""), ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String listAll(){
		
		newsGroupid = 0;
		
		return "listAll";
	}
	
	
	public void exportcsvToAll(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		OutputStream os = null;
		
		try {
			
			 os = response.getOutputStream();
			 WritableWorkbook wwb = Workbook.createWorkbook(os);
			 WritableSheet sheet = wwb.createSheet("分析导出", 0);
			 
			 
			 int rowStart = 0;
			 
			 int column = 0;
			 
			 Label label0_0 = new Label(rowStart, column, "粉丝分析");
		     sheet.addCell(label0_0);
		     
		     ++rowStart;
		     
		     Label label0_1 = new Label(rowStart, column, "累计粉丝数");
		     sheet.addCell(label0_1);
		     
		     ++rowStart;
		     
		     Label label0_2 = new Label(rowStart, column, "新增粉丝数");
		     sheet.addCell(label0_2);
		     
		     ++rowStart;
			 
		     Label label0_3 = new Label(rowStart, column, "新增粉丝比例");
		     sheet.addCell(label0_3);
		     
		     ++rowStart;
		     
		     Label label0_4 = new Label(rowStart, column, "绑定会员数");
		     sheet.addCell(label0_4);
		     
		     ++rowStart;
		     
		     Label label0_5 = new Label(rowStart, column, "绑定会员比例");
		     sheet.addCell(label0_5);
		     
		     column ++;
		     
		     rowStart = 0;
		     
		     
		     //获取会员统计信息
		     List<Map>  memberCountMap = null;
		     
		     if(memberCountMap != null && memberCountMap.size() > 0){
		    	 
		     }
		     
		     column ++;
		     
		     column ++;
		     
		     
		     Label label1_0 = new Label(rowStart, column, "会员分析");
		     sheet.addCell(label1_0);
		     
		     ++rowStart;
		     
		     Label label1_1 = new Label(rowStart, column, "绑定会员数量");
		     sheet.addCell(label1_1);
		     
		     ++rowStart;
		     
		     Label label1_2 = new Label(rowStart, column, "活跃会员数量");
		     sheet.addCell(label1_2);
		     
		     ++rowStart;
			 
		     Label label1_3 = new Label(rowStart, column, "活跃会员比例");
		     sheet.addCell(label1_3);
		     
		     ++rowStart;
		     
		     Label label1_4 = new Label(rowStart, column, "会员签到数");
		     sheet.addCell(label1_4);
		     
		     ++rowStart;
		     
		     Label label1_5 = new Label(rowStart, column, "会员签到比例");
		     sheet.addCell(label1_5);
		     
		     
		     
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getIsSystem() == Constant.ONE){//判断是否是管理员
				if(menuGroupid == null){
					menuGroupid = 0;
				}
			}else{
				menuGroupid = user.getDefaultGroup();
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("groupid", menuGroupid);
			data.put("startTime", menuStartTime);
			data.put("endTime", menuEndTime);
			data.put("defaultCount", menuDefaultCount);

			
			StringBuffer fileName = new StringBuffer();

			String groupname = "";
			if(!"".equals(groupname)){
				fileName.append(DateUtil.sdf_YMd.format(new Date())+"_"+groupname);
			}else {
				fileName.append(DateUtil.sdf_YMd.format(new Date())+"_总公司");
			}
			fileName.append("_all.xls");
	        response.setContentType("application/octet-stream;charset=UTF-8");
	        response.setHeader("Location",fileName.toString());
			response.setCharacterEncoding("UTF-8");
	        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.toString().getBytes("GB2312"), "ISO_8859_1")); 
	        wwb.write();
	        wwb.close();
	   	 OperLogUtil4gialen.log(   "导出所有数据csv:"+SafeVal.val( ""), ServletActionContext.getRequest());
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发生异常位置:DataAnalysisAction.exportcsvToAll()", e);
		}finally{
			try{
				if(os != null){
					os.close();
				}
				}catch(Exception e){
					e.printStackTrace();
				}
		}
	}
	
	
	
	public WeixinUserManagerService getWeixinUserManagerService() {
		return weixinUserManagerService;
	}
	public void setWeixinUserManagerService(
			WeixinUserManagerService weixinUserManagerService) {
		this.weixinUserManagerService = weixinUserManagerService;
	}
	public ActivityManagerService getActivityManagerService() {
		return activityManagerService;
	}
	public void setActivityManagerService(
			ActivityManagerService activityManagerService) {
		this.activityManagerService = activityManagerService;
	}
	public MaterialManagerService getMaterialManagerService() {
		return materialManagerService;
	}
	public void setMaterialManagerService(
			MaterialManagerService materialManagerService) {
		this.materialManagerService = materialManagerService;
	}

	public Integer getWeixinDateType() {
		return weixinDateType;
	}

	public void setWeixinDateType(Integer weixinDateType) {
		this.weixinDateType = weixinDateType;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getWeixinStartTime() {
		return weixinStartTime;
	}
	
	public void setWeixinStartTime(String weixinStartTime) {
		this.weixinStartTime = weixinStartTime;
	}

	public String getWeixinEndTime() {
		return weixinEndTime;
	}

	public void setWeixinEndTime(String weixinEndTime) {
		this.weixinEndTime = weixinEndTime;
	}

	public DataAnalysisService getDataAnalysisService() {
		return dataAnalysisService;
	}

	public void setDataAnalysisService(DataAnalysisService dataAnalysisService) {
		this.dataAnalysisService = dataAnalysisService;
	}

	public WeixinUser getWeixinUser() {
		return weixinUser;
	}

	public void setWeixinUser(WeixinUser weixinUser) {
		this.weixinUser = weixinUser;
	}

	public List<WeixinUser> getWeixinUsers() {
		return weixinUsers;
	}

	public void setWeixinUsers(List<WeixinUser> weixinUsers) {
		this.weixinUsers = weixinUsers;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public Integer getDefaultCount() {
		return defaultCount;
	}

	public void setDefaultCount(Integer defaultCount) {
		this.defaultCount = defaultCount;
	}

	public BranchManagerService getBranchManagerService() {
		return branchManagerService;
	}

	public void setBranchManagerService(BranchManagerService branchManagerService) {
		this.branchManagerService = branchManagerService;
	}

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public Integer getNewsDateType() {
		return newsDateType;
	}

	public void setNewsDateType(Integer newsDateType) {
		this.newsDateType = newsDateType;
	}

	public String getNewsStartTime() {
		return newsStartTime;
	}

	public void setNewsStartTime(String newsStartTime) {
		this.newsStartTime = newsStartTime;
	}

	public String getNewsEndTime() {
		return newsEndTime;
	}

	public void setNewsEndTime(String newsEndTime) {
		this.newsEndTime = newsEndTime;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public List<News> getNewses() {
		return newses;
	}

	public void setNewses(List<News> newses) {
		this.newses = newses;
	}

	public Integer getNewsGroupid() {
		return newsGroupid;
	}

	public void setNewsGroupid(Integer newsGroupid) {
		this.newsGroupid = newsGroupid;
	}

	public Integer getNewsDefaultCount() {
		return newsDefaultCount;
	}

	public void setNewsDefaultCount(Integer newsDefaultCount) {
		this.newsDefaultCount = newsDefaultCount;
	}

	public Integer getMemberDateType() {
		return memberDateType;
	}

	public void setMemberDateType(Integer memberDateType) {
		this.memberDateType = memberDateType;
	}

	public Integer getMemberGroupid() {
		return memberGroupid;
	}

	public void setMemberGroupid(Integer memberGroupid) {
		this.memberGroupid = memberGroupid;
	}

	public String getMemberStartTime() {
		return memberStartTime;
	}

	public void setMemberStartTime(String memberStartTime) {
		this.memberStartTime = memberStartTime;
	}

	public String getMemberEndTime() {
		return memberEndTime;
	}

	public void setMemberEndTime(String memberEndTime) {
		this.memberEndTime = memberEndTime;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public Integer getMemberDefaultCount() {
		return memberDefaultCount;
	}

	public void setMemberDefaultCount(Integer memberDefaultCount) {
		this.memberDefaultCount = memberDefaultCount;
	}

	public TMbNews getTmbnews() {
		return tmbnews;
	}

	public void setTmbnews(TMbNews tmbnews) {
		this.tmbnews = tmbnews;
	}

	public Integer getSignDateType() {
		return signDateType;
	}

	public void setSignDateType(Integer signDateType) {
		this.signDateType = signDateType;
	}

	public Integer getSignGroupid() {
		return signGroupid;
	}

	public void setSignGroupid(Integer signGroupid) {
		this.signGroupid = signGroupid;
	}

	public String getSignStartTime() {
		return signStartTime;
	}

	public void setSignStartTime(String signStartTime) {
		this.signStartTime = signStartTime;
	}

	public String getSignEndTime() {
		return signEndTime;
	}

	public void setSignEndTime(String signEndTime) {
		this.signEndTime = signEndTime;
	}

	public Sign getSign() {
		return sign;
	}

	public void setSign(Sign sign) {
		this.sign = sign;
	}

	public List<Sign> getSigns() {
		return signs;
	}

	public void setSigns(List<Sign> signs) {
		this.signs = signs;
	}

	public Integer getSignDefaultCount() {
		return signDefaultCount;
	}

	public void setSignDefaultCount(Integer signDefaultCount) {
		this.signDefaultCount = signDefaultCount;
	}

	public Integer getActivityDateType() {
		return activityDateType;
	}

	public void setActivityDateType(Integer activityDateType) {
		this.activityDateType = activityDateType;
	}

	public Integer getActivityGroupid() {
		return activityGroupid;
	}

	public void setActivityGroupid(Integer activityGroupid) {
		this.activityGroupid = activityGroupid;
	}

	public String getActivityStartTime() {
		return activityStartTime;
	}

	public void setActivityStartTime(String activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	public String getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(String activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public List<Activity> getActivitys() {
		return activitys;
	}

	public void setActivitys(List<Activity> activitys) {
		this.activitys = activitys;
	}

	public Integer getActivityDefaultCount() {
		return activityDefaultCount;
	}

	public void setActivityDefaultCount(Integer activityDefaultCount) {
		this.activityDefaultCount = activityDefaultCount;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getMenuDateType() {
		return menuDateType;
	}

	public void setMenuDateType(Integer menuDateType) {
		this.menuDateType = menuDateType;
	}

	public Integer getMenuGroupid() {
		return menuGroupid;
	}

	public void setMenuGroupid(Integer menuGroupid) {
		this.menuGroupid = menuGroupid;
	}

	public String getMenuStartTime() {
		return menuStartTime;
	}

	public void setMenuStartTime(String menuStartTime) {
		this.menuStartTime = menuStartTime;
	}

	public String getMenuEndTime() {
		return menuEndTime;
	}

	public void setMenuEndTime(String menuEndTime) {
		this.menuEndTime = menuEndTime;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Integer getMenuDefaultCount() {
		return menuDefaultCount;
	}

	public void setMenuDefaultCount(Integer menuDefaultCount) {
		this.menuDefaultCount = menuDefaultCount;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public List<THdMember> gettHdMembers() {
		return tHdMembers;
	}

	public void settHdMembers(List<THdMember> tHdMembers) {
		this.tHdMembers = tHdMembers;
	}

	public Integer getStoresId() {
		return storesId;
	}

	public void setStoresId(Integer storesId) {
		this.storesId = storesId;
	}

	public Map<String, Integer> getCountMap() {
		return countMap;
	}

	public void setCountMap(Map<String, Integer> countMap) {
		this.countMap = countMap;
	}

	public Map<String, Double> getPrecentMap() {
		return precentMap;
	}

	public void setPrecentMap(Map<String, Double> precentMap) {
		this.precentMap = precentMap;
	}


	
	
	
	
}
