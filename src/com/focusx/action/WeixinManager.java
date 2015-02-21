package com.focusx.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.attilax.SafeVal;
import com.attilax.core;
import com.attilax.io.filex;
import com.focusx.entity.TMbTask;
import com.focusx.entity.TMbWeixinUser;
import com.focusx.entity.TUserUsers;
import com.focusx.entity.weixin.sendMessage.Text;
import com.focusx.entity.weixin.sendMessage.TextMessage;
import com.focusx.service.TaskService;
import com.focusx.util.Constant;
import com.focusx.util.JsonUtil;
import com.focusx.util.OperLogUtil4gialen;
import com.focusx.util.Page;
import com.focusx.util.PageUtil;
import com.focusx.util.WeixinUtil;

public class WeixinManager {
	
	private final static Logger log = Logger.getLogger(WeixinManager.class);
	
	private List<TMbTask> tasks;//待办任务集合
	private String openid;//微博微信用户的唯一标识
	private String id;//主键ID
	private Integer source = 0;//来源
	private String contactname = "";//名称
	private String startTime = "";//开始时间
	private String endTime = "";//结束时间
	private String content = "";//内容
	private int state = -1;//消息状态
	
	private String replyTxt;
	private TaskService taskService;
	
	private Page<TMbTask> page;// 分页对象
	private String p; 
	private String ps; 
	TUserUsers user_o5;
	public String weixinClient(){
		TUserUsers user;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		Integer userID = (Integer) session.getAttribute(Constant.Login_UserID);//从session里获取当前用户的userID
		
		  user = (TUserUsers)session.getAttribute(session.getId());
		user_o5=user;
	
		
		Map<String, Object> data = new HashMap<String, Object>();
		page = new Page<TMbTask>(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(page, request);
		int pageNumber = pageParams[0];// 第几页
		int pageSize = pageParams[1];// 每页记录数
		int groupid = 0;
		if(user.getDefaultGroup() != null){
			groupid = user.getDefaultGroup();
		}
		
		//o5d
		if(request.getParameter("subcomid")!=null)
		{
			if(! request.getParameter("subcomid").equals("-1"))
			
				groupid =Integer.parseInt(  request.getParameter("subcomid") );
		}
		
		// 分页条件
		data.put("pageNumber", pageNumber);
		data.put("pageSize", pageSize);
		
		//加入查询条件
		data.put("contactname", contactname);
		data.put("startTime", startTime);
		data.put("endTime", endTime);
		data.put("source", source);
		data.put("content", content);
		data.put("userID", userID);
		data.put("state", state);
		data.put("groupid", groupid);
		try {
			tasks = taskService.getTasksByData(data, page);
			
			 OperLogUtil4gialen.log(   "查询客户消息成功:"+SafeVal.val( ""), ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "success";
	}
	
	
	public String send(){
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");  
        response.setContentType("text/json;charset=UTF-8");  
        
    	Map<String, String> map = new HashMap<String, String>();
        if(new  File("c:\\testmode.txt").exists())
        {
        	map.put("state", "success");
			map.put("reason", "");
			//JSONArray jsonArray = JSONArray.fromObject(map);
		 
				try {
					JsonUtil.write(response, JSONObject.fromObject(map).toString());
				} catch (IOException e) {
					core.log(e);
				}
        	return null;
        }
        
	//	Map<String, String> map = new HashMap<String, String>();
		HttpSession session = ServletActionContext.getRequest().getSession();
		Integer userID = (Integer) session.getAttribute(Constant.Login_UserID);//从session里获取当前用户的userID
		
		// 构建文本发送对象
		TextMessage textMessage = new TextMessage();
		// 消息类型，text
		textMessage.setMsgtype("text");
		// 发送人
		textMessage.setTouser(openid);

		Text text = new Text();
		// 发送内容
		text.setContent(replyTxt);
		textMessage.setText(text);
		
		JSONObject json = JSONObject.fromObject(textMessage);
		String sendJsonData = json.toString();
		core.log("--o5oa sendJsonData:"+sendJsonData);
		JSONObject jsonObject = WeixinUtil.sendMessage(sendJsonData, Constant.token.getToken());
		core.log("--o5oa recive:"+jsonObject.toString());
		int code = -4444;
		FileInputStream fileInputStream = null;
		try{
				if(jsonObject != null && jsonObject.containsKey("errcode")){
					code = jsonObject.getInt("errcode");
					
					if(code == 0){
						if(taskService.updateToStateById(id,openid,replyTxt,userID)){
							log.info("更新为已读成功！");
							
							map.put("state", "success");
							map.put("reason", "");
							//JSONArray jsonArray = JSONArray.fromObject(map);
							//String jsonString = jsonArray != null ? jsonArray.toString() : "";
							
						 	 OperLogUtil4gialen.log(   "回复成功:"+SafeVal.val( replyTxt), ServletActionContext.getRequest());	
						 	 
						 	 
				        	try {
								JsonUtil.write(response, JSONObject.fromObject(map).toString());
							} catch (IOException e) {
								e.printStackTrace();
							}
							
						}else{
							log.info("更新为已读失败！");
						}
					}else{
						map.put("state", "false");
						Properties properties =  new Properties();
					    String errorPath = Constant.path + "weixincode.properties";
					    fileInputStream = new FileInputStream(errorPath);
				    	properties.load(fileInputStream);  
						String errorMessage = properties.getProperty(jsonObject.getString("errcode"));
						map.put("reason", errorMessage);
						
					//	JSONArray jsonArray = JSONArray.fromObject(map);
					//	String jsonString = jsonArray != null ? jsonArray.toString() : "";
						JsonUtil.write(response, JSONObject.fromObject(map).toString());
					}
				}
		}catch(Exception e){
			e.printStackTrace();
			log.info("--o530", e);
			//log.info
		}finally{
			try{
				if(fileInputStream != null){
					fileInputStream.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return "errCantSendExc";
	}
	
	/**
	@author attilax 老哇的爪子
		@since  2014-5-30 上午11:29:47$
	
	 * @param string
	 * @return
	 */
	private boolean File(String string) {
		// attilax 老哇的爪子  上午11:29:47   2014-5-30 
		return false;
	}


	/**
	 *  回复未读取的信息前获取相关的聊天记录
	 */
	public String reply(){
		HttpServletRequest request = ServletActionContext.getRequest();
		page = new Page<TMbTask>(PageUtil.PAGE_SIZE*2);
	    int pageNo = Integer.parseInt(request.getParameter("p") == null ?"0":request.getParameter("p"));
	    page.setPageNo(pageNo);
	    int pageSize = 20;// 每页记录数
	    page.setPageSize(pageSize);

		int pageNumber = page.getFirst();// 第几页
		
		Map<String, Object> data = new HashMap<String, Object>();
		// 分页条件
		data.put("pageNumber", pageNumber);
		data.put("pageSize", pageSize);
		data.put("openid", openid);
		try {
			if(openid != null && !("").equals(openid)){
				tasks = taskService.getTasksByOpenId(page, data);
			}else {
				tasks = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "reply";
	}


	public TaskService getTaskService() {
		return taskService;
	}


	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}


	public List<TMbTask> getTasks() {
		return tasks;
	}


	public void setTasks(List<TMbTask> tasks) {
		this.tasks = tasks;
	}


	public String getOpenid() {
		return openid;
	}


	public void setOpenid(String openid) {
		this.openid = openid;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Integer getSource() {
		return source;
	}


	public void setSource(Integer source) {
		this.source = source;
	}


	public String getContactname() {
		return contactname;
	}


	public void setContactname(String contactname) {
		this.contactname = contactname;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}


	public Page<TMbTask> getPage() {
		return page;
	}


	public void setPage(Page<TMbTask> page) {
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


	public String getReplyTxt() {
		return replyTxt;
	}


	public void setReplyTxt(String replyTxt) {
		this.replyTxt = replyTxt;
	}

	
	
	

}
