package com.focusx.action.activityManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.attilax.SafeVal;
import com.attilax.core;
import com.focusx.entity.TMbActAward;
import com.focusx.entity.TMbActivity;
import com.focusx.entity.TMbAwardWeixin;
import com.focusx.entity.TMbAwardWeixinResult;
import com.focusx.entity.TMbTask;
import com.focusx.entity.weixin.sendMessage.Text;
import com.focusx.entity.weixin.sendMessage.TextMessage;
import com.focusx.service.ActAwardManagerService;
import com.focusx.service.ActivityManagerService;
import com.focusx.service.AwardWeixinManagerService;
import com.focusx.service.TaskManagerService;
import com.focusx.util.Constant;
import com.focusx.util.JsonUtil;
import com.focusx.util.OperLogUtil4gialen;
import com.focusx.util.Page;
import com.focusx.util.PageUtil;
import com.focusx.util.WeixinUtil;
import com.opensymphony.xwork2.ActionSupport;

public class ActivityManagerAction extends ActionSupport{
	protected static Logger logger = Logger.getLogger("ActivityManagerAction");
	
	private ActivityManagerService activityManagerService;
	private ActAwardManagerService actAwardManagerService;//服务层对象：奖品
	private TaskManagerService taskManagerService;//服务层对象：聊天
	private AwardWeixinManagerService awardWeixinManagerService;
	
	private List<TMbActivity> activitys;
	private List<TMbActAward> actawards;//奖品信息数组
	private List<TMbTask> taskList;//聊天记录数组
	private List<TMbAwardWeixinResult> awardweixins;//中奖名单数组
	
	private TMbActivity activity;
	private Integer id; //主键ID
	private String actName;	//活动名称
	private String beginTime;//开始时间
	private String endTime;  //结束时间
	private String remark;   //说明
	private Integer type;   //活动类型
	private Integer flag;
	
	private String awardName;//奖品名称
	private String addActAwards;//新添加的奖品ID集合字符串
	private Integer actawardId;//奖品ID
	
	private Page<TMbActivity> page;// 活动分页
	private Page<TMbActAward> pageActAward;// 奖品分页
	private Page<TMbTask> pageTask;// 抽奖审核分页
	private String p;// 第几页
	private String ps;// 每页显示记录数
	
	private String openid;//微信唯一标识
	private Integer taskId;//聊天对象ID
	private String deleteMediaUrls;//批量删除链接集合
	
	public String listActivity(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> data = new HashMap<String, Object>();
		
		page = new Page<TMbActivity>(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(page, request);
		int pageNumber = pageParams[0];// 第几页
		int pageSize = pageParams[1];// 每页记录数
		// 分页条件
		data.put("pageNumber", pageNumber);
		data.put("pageSize", pageSize);
		
		// 查询条件
		data.put("actName", StringUtils.trim(actName));
		
		try {
			activitys = activityManagerService.getActivitys(page, data);
			OperLogUtil4gialen.log(   "查询活动:"+SafeVal.val( ""), ServletActionContext.getRequest());	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listActivity";
	}
	
	//跳转到新增界面
	public String showAddActivity(){
		return "showAddActivity";
	}
	
	//保存新增活动
	public String addActivity(){
		core.log("--now  addActivity()");
		try {
			TMbActivity activity = new TMbActivity();
			
			HttpServletRequest request = ServletActionContext.getRequest();
			activity=core.request2obj(request, activity);
			
			activity.setActName(StringUtils.trim(actName));
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");                       
			activity.setBeginTime(format.parse(beginTime));
			activity.setEndTime(format.parse(endTime));
			activity.setRemark(StringUtils.trim(remark));
			activity.setType(type);
			activity.setJoinCount(Integer.parseInt(request.getParameter("joincount")));
			
		
			
			id = activityManagerService.addActivity(activity);
			OperLogUtil4gialen.log(   "添加活动:"+SafeVal.val( actName), ServletActionContext.getRequest());	
		} catch (Exception e) {
			e.printStackTrace();
			core.log(e);
			
		}
		return "showActivity";
	}
	
	//删除活动
	public void deleteActivity(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(id != null && id > 0){
				TMbActivity activity = activityManagerService.getActivityById(id);
				if(activity != null){
					Date starTime = activity.getBeginTime();
					Date endTime = activity.getEndTime();
					String result = checkTime(starTime, endTime);
					if(result.equals("false")){
						activityManagerService.delete(id);
						
						OperLogUtil4gialen.log(   "删除活动:"+SafeVal.val( id), ServletActionContext.getRequest());	
						
						JsonUtil.write(response, "success");
					}else {
						JsonUtil.write(response, "false");
					}
				}else {
					JsonUtil.write(response, "null");
				}
			}else {
				JsonUtil.write(response, "null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//根据ID获取活动信息
	public String showActivity(){
		try {
			activity = activityManagerService.getActivityById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "viewActivity";
	}
	
	//跳转到编辑界面
	public String showEditActivity(){
		try {
			activity = activityManagerService.getActivityById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "editActivity";
	}
	
	
	public String setupActEnable(){
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");  
        response.setContentType("text/json;charset=UTF-8");  
		
        Map<String,String> map = new HashMap<String,String>();
		if(id != null && flag != null){
		
			try {
				activity = activityManagerService.getActivityById(id);
				activity.setFlag(flag);
				
				if(activityManagerService.updateActivity(activity)){
					
					map.put("result", "ok");
					JsonUtil.write(response, JSONObject.fromObject(map).toString());
				}else{
					map.put("result", "fail");
					map.put("msg", "更新失败");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}else{
			map.put("result", "fail");
			map.put("msg", "缺少参数");
		}
		
		return null;
	}
	
	//保存编辑后的活动信息
	public String saveActivity(){
		try {
			TMbActivity activityOld = activityManagerService.getActivityById(id);
			activityOld.setActName(StringUtils.trim(actName));
			activityOld.setRemark(StringUtils.trim(remark));
			activityOld.setType(type);
			
			HttpServletRequest request = ServletActionContext.getRequest();
			activityOld=core.request2obj(request, activityOld);
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");    
			if(beginTime != null && !"".equals(beginTime)){
				activityOld.setBeginTime(format.parse(beginTime));
			}
			if(endTime != null && !"".equals(endTime)){
				activityOld.setEndTime(format.parse(endTime));
			}
			//todox  get request jsp,jspmethod,strutest2,jsf,spring mvc o528
			
			
			activityManagerService.saveActivity(activityOld);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "showActivity";
	}
	
	//跳转到属于该活动的奖品详情界面
	public String showActAward(){
		try {
			actawards = actAwardManagerService.getActAwardsByActivityId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "showActAward";
	}
	
	//获取奖品信息，规则：获取不属于任何活动的奖品
	public String chooseActAward(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> data = new HashMap<String, Object>();
		
		pageActAward = new Page<TMbActAward>(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(pageActAward, request);
		int pageNumber = pageParams[0];// 第几页
		int pageSize = pageParams[1];// 每页记录数
		// 分页条件
		data.put("pageNumber", pageNumber);
		data.put("pageSize", pageSize);
		
		// 查询条件
		data.put("awardName", StringUtils.trim(awardName));
		
		try {
			actAwardManagerService.getActAwardsOutAllActivity(pageActAward, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "chooseActAward";
	}
	
	//添加奖品
	public void addActAward(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(addActAwards != null && !StringUtils.trim(addActAwards).equals("") && id != null){
				actAwardManagerService.updateActAwardByActivityId(addActAwards, id);
				JsonUtil.write(response, "success");
			}else {
				JsonUtil.write(response, "null");
			}
			OperLogUtil4gialen.log(   "添加奖品:"+SafeVal.val( id), ServletActionContext.getRequest());	
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JsonUtil.write(response, "error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//检查活动是否在进行中
	public void checkActivity(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(id != null){
				TMbActivity activity = activityManagerService.getActivityById(id);
				if(activity != null){
					Date starTime = activity.getBeginTime();
					Date endTime = activity.getEndTime();
					String result = checkTime(starTime, endTime);
					JsonUtil.write(response, result);
				}else {
					JsonUtil.write(response, "null");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JsonUtil.write(response, "error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//判断今天是否在活动的开始时间、结束时间之间
	public static String checkTime(Date start, Date end){
		String result = "false";
		try {
			Date temp = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String todayString = format.format(temp);
			Date today = dateFormat.parse(todayString);
			if(today.equals(start) || today.equals(end)){
				result = "true";
			}else if(today.after(start) && today.before(end)){
				result = "true";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//删除奖品
	public void deleteActAward(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(actawardId != null){
				actAwardManagerService.deleteActAwardByActivityId(actawardId);
				JsonUtil.write(response, "success");
			}else {
				JsonUtil.write(response, "null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JsonUtil.write(response, "error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//获取中奖名单
	public String showWinner(){
		try {
			if(id != null){
				activity = activityManagerService.getActivityById(id);
				awardweixins = awardWeixinManagerService.getAwardWeixinByActivity(id);
				OperLogUtil4gialen.log(   "获取中奖名单:"+SafeVal.val(id), ServletActionContext.getRequest());	
			}
		} catch (Exception e) {
			e.printStackTrace();
			core.log(e);
		}
		return "showWinner";
	}
	
	//抽奖审核
	public String listDraw(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> data = new HashMap<String, Object>();
		
		pageTask = new Page<TMbTask>(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(pageTask, request);
		int pageNumber = pageParams[0];// 第几页
		int pageSize = pageParams[1];// 每页记录数
		// 分页条件
		data.put("pageNumber", pageNumber);
		data.put("pageSize", pageSize);
		
		try {
			taskList = taskManagerService.getTaskForMediaUrl(pageTask, data);
			OperLogUtil4gialen.log(   "抽奖审核:"+SafeVal.val( ""), ServletActionContext.getRequest());	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listDraw";
	}
	
	//发送抽检链接消息
	public void send(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");  
        response.setContentType("text/json;charset=UTF-8");  
		Integer awardWxId = null;
		Map<String, String> map = new HashMap<String, String>();
		String json_map = "";
		try {
			String openidString = "";
			if(openid != null){
				openidString = openid.split("/")[0];
			}
			if(id == null && taskId == null){
				map.put("state", "null");
				map.put("reason", "数据不全，不能发送");
				JSONArray jsonArray = JSONArray.fromObject(map);
				json_map = jsonArray != null ? jsonArray.toString() : "";
				JsonUtil.write(response,json_map);
			}
			
			//获取accessToken
			String accessToken = Constant.token.getToken();
			logger.info("读取到的accessToken是"+accessToken);
			
			if(accessToken.equals("") || accessToken == null){
				logger.info("发送失败，获取授权码错误");
				map.put("state", "token");
				map.put("reason", "获取授权码失败，请联系管理员");
				JSONArray jsonArray = JSONArray.fromObject(map);
				json_map = jsonArray != null ? jsonArray.toString() : "";
				JsonUtil.write(response, json_map);
			}else {
				TMbAwardWeixin awardweixin = new TMbAwardWeixin();
				
				awardweixin.setActivityId(id);
				awardweixin.setOpenId(openidString);
				awardweixin.setAwardCount(Constant.Three);
				
				awardWxId = awardWeixinManagerService.addAwardWeixin(awardweixin);
				if(awardWxId == null || awardWxId < 0){
					map.put("state", "awardWxId");
					map.put("reason", "插入数据出错，请联系管理员");
					JSONArray jsonArray = JSONArray.fromObject(map);
					json_map = jsonArray != null ? jsonArray.toString() : "";
					JsonUtil.write(response, json_map);
				}else {
					logger.info("获取到的activityId是"+id+"，awardWxId是"+awardWxId);
					String content = "亲，恭喜你获得一次抽奖机会，请点击“<a href='http://211.147.235.195/weixin/mobile/bigwheel.jsp?openid="+openidString+"&activityId="+id+"&awardWxId="+awardWxId+"'>38节晒单 抽大奖</a>”进入活动界面";
					
					// 构建文本发送对象
					TextMessage textMessage = new TextMessage();
					// 消息类型，text
					textMessage.setMsgtype("text");
					// 发送人
					textMessage.setTouser(openidString);

					Text text = new Text();
					// 发送内容
					text.setContent(content);
					textMessage.setText(text);
					
					JSONObject json = JSONObject.fromObject(textMessage);
					String sendJsonData = json.toString();
					JSONObject jsonObject = WeixinUtil.sendMessage(sendJsonData, accessToken);

					
					//成功后返回的json数据
					//{"errcode":0,"errmsg":"ok"}
					if (null != jsonObject) {  
				        if (0 == jsonObject.getInt("errcode")) {  //发送成功 
				        	//把数据库信息置空
				        	taskManagerService.updateTaskForMediaUrl(taskId);
				        	File file = new File(Constant.uploadPathDraw+"//"+openid);
				        	if(file.exists()){
				        		file.delete();
				        		logger.info("购物小票图片删除成功！名称是"+openid.split("/")[1]);
				        	}
							map.put("state", "success");
							map.put("reason", "");
							JSONArray jsonArray = JSONArray.fromObject(map);
							json_map = jsonArray != null ? jsonArray.toString() : "";
				        	logger.info("发送成功,openid是"+openidString);
				        	JsonUtil.write(response, json_map);
				        }else{//发送失败
					    	if(awardWxId != null){
					    		awardWeixinManagerService.deleteAwardWeixinById(awardWxId);
					    	}
							map.put("state", "false");
							
					    	Properties properties =  new Properties();
						    String errorPath = Constant.path + "weixincode.properties";
						    FileInputStream fileInputStream = new FileInputStream(errorPath);
					    	properties.load(fileInputStream);  
							String errorMessage = properties.getProperty(jsonObject.getString("errcode"));
							System.out.println("errcode=="+jsonObject.getInt("errcode"));
							System.out.println("errorMessage=="+errorMessage);
							map.put("reason", errorMessage+"，请联系管理员");
							JSONArray jsonArray = JSONArray.fromObject(map);
							json_map = jsonArray != null ? jsonArray.toString() : "";
				        	logger.info("发送失败,openid是"+openidString+",返回的信息是"+jsonObject.toString());
				        	JsonUtil.write(response, json_map);
				        }  
				    }else{
				    	if(awardWxId != null){
				    		awardWeixinManagerService.deleteAwardWeixinById(awardWxId);
				    	}
				    	map.put("state", "jsonObject");
				    	map.put("reason", "发送失败，返回结果为空，请联系管理员");
						JSONArray jsonArray = JSONArray.fromObject(map);
						json_map = jsonArray != null ? jsonArray.toString() : "";
			        	logger.info("发送失败,openid是"+openidString);
			        	JsonUtil.write(response, json_map);
				    }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("发送抽奖链接失败，openid是"+openid);
			if(awardWxId != null){
				awardWeixinManagerService.deleteAwardWeixinById(awardWxId);
			}
			try {
				map.put("state", "error");
		    	map.put("reason", "发送失败，运行出错，请联系管理员");
				JSONArray jsonArray = JSONArray.fromObject(map);
				json_map = jsonArray != null ? jsonArray.toString() : "";
				JsonUtil.write(response, json_map);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//删除审核图片和置空数据
	public void deleteMediaUrl(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(taskId != null){
				taskManagerService.updateTaskForMediaUrl(taskId);
	        	File file = new File(Constant.uploadPathDraw+"//"+openid);
	        	if(file.exists()){
	        		file.delete();
	        		logger.info("购物小票图片删除成功！名称是"+openid.split("/")[1]);
	        	}
	        	JsonUtil.write(response, "success");
			}else {
				JsonUtil.write(response, "nul");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JsonUtil.write(response, "error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//批量删除审核图片和置空数据
	public void deleteMediaUrls(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(deleteMediaUrls != null && !StringUtils.trim(deleteMediaUrls).equals("")){
				String[] idMediaUrl = deleteMediaUrls.split(",");
				if(idMediaUrl != null && idMediaUrl.length > 0){
					List<Integer> ids = new ArrayList<Integer>();
					List<String> mediaUrls = new ArrayList<String>();
					for(int i=0; i<idMediaUrl.length; i++){
						ids.add(Integer.parseInt(StringUtils.trim(idMediaUrl[i].split("&")[0])));
						mediaUrls.add(idMediaUrl[i].split("&")[1]);
					}
					for(int i=0; i<ids.size(); i++){
						taskManagerService.updateTaskForMediaUrl(ids.get(i));
			        	File file = new File(Constant.uploadPathDraw+"//"+mediaUrls.get(i));
			        	if(file.exists()){
			        		file.delete();
			        		logger.info("购物小票图片删除成功！名称是"+mediaUrls.get(i).split("/")[1]);
			        	}
					}
					JsonUtil.write(response, "success");
				}else {
					JsonUtil.write(response, "null");
				}
			}else {
				JsonUtil.write(response, "null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JsonUtil.write(response, "error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//获取未结束的所有活动
	public void getAllActivityNotFinish(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dataString = format.format(new Date());
			List<TMbActivity> list = activityManagerService.getAllActivityNotFinish(dataString);
			JSONArray jsonArray = JSONArray.fromObject(list);
			String json_data = jsonArray != null ? jsonArray.toString() : "";
			JsonUtil.getInstance();
			JsonUtil.write(response, json_data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//获取已结束或正开展中的所有活动
	public void getAllActivity(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dataString = format.format(new Date());
			List<TMbActivity> list = activityManagerService.getAllActivity(dataString);
			JSONArray jsonArray = JSONArray.fromObject(list);
			String json_data = jsonArray != null ? jsonArray.toString() : "";
			JsonUtil.getInstance();
			JsonUtil.write(response, json_data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<TMbActivity> getActivitys() {
		return activitys;
	}

	public void setActivitys(List<TMbActivity> activitys) {
		this.activitys = activitys;
	}

	public TMbActivity getActivity() {
		return activity;
	}

	public void setActivity(TMbActivity activity) {
		this.activity = activity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ActivityManagerService getActivityManagerService() {
		return activityManagerService;
	}

	public void setActivityManagerService(
			ActivityManagerService activityManagerService) {
		this.activityManagerService = activityManagerService;
	}

	public Page<TMbActivity> getPage() {
		return page;
	}

	public void setPage(Page<TMbActivity> page) {
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

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public ActAwardManagerService getActAwardManagerService() {
		return actAwardManagerService;
	}

	public void setActAwardManagerService(
			ActAwardManagerService actAwardManagerService) {
		this.actAwardManagerService = actAwardManagerService;
	}

	public List<TMbActAward> getActawards() {
		return actawards;
	}

	public void setActawards(List<TMbActAward> actawards) {
		this.actawards = actawards;
	}

	public Page<TMbActAward> getPageActAward() {
		return pageActAward;
	}

	public void setPageActAward(Page<TMbActAward> pageActAward) {
		this.pageActAward = pageActAward;
	}

	public String getAwardName() {
		return awardName;
	}

	public String getAddActAwards() {
		return addActAwards;
	}

	public void setAddActAwards(String addActAwards) {
		this.addActAwards = addActAwards;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public Integer getActawardId() {
		return actawardId;
	}

	public void setActawardId(Integer actawardId) {
		this.actawardId = actawardId;
	}

	public TaskManagerService getTaskManagerService() {
		return taskManagerService;
	}

	public void setTaskManagerService(TaskManagerService taskManagerService) {
		this.taskManagerService = taskManagerService;
	}

	public List<TMbTask> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<TMbTask> taskList) {
		this.taskList = taskList;
	}

	public Page<TMbTask> getPageTask() {
		return pageTask;
	}

	public void setPageTask(Page<TMbTask> pageTask) {
		this.pageTask = pageTask;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public AwardWeixinManagerService getAwardWeixinManagerService() {
		return awardWeixinManagerService;
	}

	public void setAwardWeixinManagerService(
			AwardWeixinManagerService awardWeixinManagerService) {
		this.awardWeixinManagerService = awardWeixinManagerService;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		ActivityManagerAction.logger = logger;
	}

	public List<TMbAwardWeixinResult> getAwardweixins() {
		return awardweixins;
	}

	public void setAwardweixins(List<TMbAwardWeixinResult> awardweixins) {
		this.awardweixins = awardweixins;
	}

	public String getDeleteMediaUrls() {
		return deleteMediaUrls;
	}

	public void setDeleteMediaUrls(String deleteMediaUrls) {
		this.deleteMediaUrls = deleteMediaUrls;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	
	
}
