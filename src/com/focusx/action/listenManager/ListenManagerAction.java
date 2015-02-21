package com.focusx.action.listenManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.focusx.entity.TMbListen;
import com.focusx.entity.TMbTask;
import com.focusx.service.ListenService;
import com.focusx.service.TaskManagerService;
import com.focusx.util.JsonUtil;
import com.focusx.util.Page;
import com.focusx.util.PageUtil;
import com.opensymphony.xwork2.ActionSupport;

public class ListenManagerAction extends ActionSupport{
	
	protected static Logger logger = Logger.getLogger("ListenManagerAction");
	
	private ListenService listenService;
	private TaskManagerService taskManagerService;
	private List<TMbListen> listens;
	private TMbListen listen;
	private Page page;// 分页对象
	private Page<TMbTask> pageTask;//聊天记录分页对象
	private String p;// 第几页
	private String ps;// 每页显示记录数
	
	private Integer listenId;   //主键ID
	private String listenName; //监听名称
	private String listenKey;	//监听关键字或者竞争对手
	private String description; //描述
	
	private String listenIds;//删除ID集合字符串
	private List<TMbTask> tasks;//聊天记录信息集合
	
	//列表页
	public String listListens(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> data = new HashMap<String, Object>();
		
		page = new Page<TMbListen>(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(page, request);
		int pageNumber = pageParams[0];// 第几页
		int pageSize = pageParams[1];// 每页记录数
		
		// 分页条件
		data.put("pageNumber", pageNumber);
		data.put("pageSize", pageSize);
		
		// 查询条件
		data.put("listenName", StringUtils.trim(listenName));
		
		try {
			listens = listenService.getListens(page, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	//跳转到新增页面
	public String showAddPage(){
		return "add";
	}

	/**
	 *  保存新增关键词监听
	 */
	public String save(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(listenName != null && !StringUtils.trim(listenName).equals("")){
				listenName = StringUtils.trim(listenName);
			}else {
				JsonUtil.getInstance().write(response, "null");
			}
			if(listenKey != null && !StringUtils.trim(listenKey).equals("")){
				listenKey = StringUtils.trim(listenKey);
			}else {
				JsonUtil.getInstance().write(response, "null");
			}
			TMbListen listen = new TMbListen();
			listen.setListenName(listenName);
			listen.setListenKey(listenKey);
			listen.setDescription(StringUtils.trim(description));
			listenId = listenService.save(listen);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "showListen";
	}
	
	//跳转到详细界面
	public String view(){
		try {
			listen = listenService.getListenByListenId(listenId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "view";
	}
	
	//跳转到编辑界面
	public String edit(){
		try {
			listen = listenService.getListenByListenId(listenId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "edit";
	}
	
	//保存编辑的关键词监听信息
	public void editSave(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			TMbListen listen = listenService.getListenByListenId(listenId);
			if(listen != null){
				listen.setListenName(StringUtils.trim(listenName));
				listen.setListenKey(StringUtils.trim(listenKey));
				listen.setDescription(StringUtils.trim(description));
				listenService.update(listen);
				JsonUtil.getInstance().write(response, "success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JsonUtil.getInstance().write(response, "error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	//删除关键词监听
	public void delete(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(listenIds != null && !StringUtils.trim(listenIds).equals("")){
				listenIds = StringUtils.trim(listenIds);
				if(listenService.delete(listenIds)){
					JsonUtil.getInstance().write(response, "success");
				}else {
					JsonUtil.getInstance().write(response, "false");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//获取关键词对应的列表
	public String listenToTask(){
		try {
			if(listenKey != null && !StringUtils.trim(listenKey).equals("")){
				HttpServletRequest request = ServletActionContext.getRequest();
				Map<String, Object> data = new HashMap<String, Object>();
				
				pageTask = new Page<TMbTask>(PageUtil.PAGE_SIZE);
				int[] pageParams = PageUtil.init(pageTask, request);
				int pageNumber = pageParams[0];// 第几页
				int pageSize = pageParams[1];// 每页记录数
				
				// 分页条件
				data.put("pageNumber", pageNumber);
				data.put("pageSize", pageSize);
				
				// 查询条件
				data.put("listenKey", StringUtils.trim(listenKey));
				
				tasks = taskManagerService.getTaskForListen(pageTask, data);
			}else {
				pageTask = new Page<TMbTask>(PageUtil.PAGE_SIZE);
				pageTask.setResult(null);
				pageTask.setTotalCount(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listenToTask";
	}
	
	
	public List<TMbListen> getListens() {
		return listens;
	}

	public void setListens(List<TMbListen> listens) {
		this.listens = listens;
	}

	public TMbListen getListen() {
		return listen;
	}

	public void setListen(TMbListen listen) {
		this.listen = listen;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getListenId() {
		return listenId;
	}

	public void setListenId(Integer listenId) {
		this.listenId = listenId;
	}

	public String getListenName() {
		return listenName;
	}

	public void setListenName(String listenName) {
		this.listenName = listenName;
	}

	public String getListenKey() {
		return listenKey;
	}

	public void setListenKey(String listenKey) {
		this.listenKey = listenKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ListenService getListenService() {
		return listenService;
	}

	public void setListenService(ListenService listenService) {
		this.listenService = listenService;
	}

	public String getListenIds() {
		return listenIds;
	}

	public void setListenIds(String listenIds) {
		this.listenIds = listenIds;
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

	public TaskManagerService getTaskManagerService() {
		return taskManagerService;
	}

	public void setTaskManagerService(TaskManagerService taskManagerService) {
		this.taskManagerService = taskManagerService;
	}

	public Page<TMbTask> getPageTask() {
		return pageTask;
	}

	public void setPageTask(Page<TMbTask> pageTask) {
		this.pageTask = pageTask;
	}

	public List<TMbTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<TMbTask> tasks) {
		this.tasks = tasks;
	}

}
