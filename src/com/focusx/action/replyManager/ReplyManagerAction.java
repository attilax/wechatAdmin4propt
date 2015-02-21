package com.focusx.action.replyManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.attilax.SafeVal;
import com.focusx.entity.TMbReply;
import com.focusx.entity.TUserUsers;
import com.focusx.service.ReplyManagerService;
import com.focusx.util.JsonUtil;
import com.focusx.util.MD5;
import com.focusx.util.OperLogUtil4gialen;
import com.focusx.util.Page;
import com.focusx.util.PageUtil;
import com.opensymphony.xwork2.ActionSupport;

public class ReplyManagerAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	public ReplyManagerService replyManagerService;
	
	private TMbReply reply; //自动回复对象
	private List<TMbReply> replys;
	private Page page;// 分页
	private String p;// 第几页
	private String ps;// 每页显示记录数 这两个参数 在本Action中没用到 在pageutil中用到了
	
	private Integer id;
	private String rules;
	private String keyword;
	private Integer newsId;
	private String content;
	private String multiReplyContent;
	private String deleteList;
	
	//列表
	public String listReplys(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> data = new HashMap<String, Object>();

		HttpSession session = ServletActionContext.getRequest().getSession();
		TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
		int groupid = 0;
		if(user.getDefaultGroup() != null && user.getDefaultGroup() > 0){
			groupid = user.getDefaultGroup();
		}
		page = new Page<TMbReply>(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(page, request);
		int pageNumber = pageParams[0];// 第几页
		int pageSize = pageParams[1];// 每页记录数
		// 分页条件
		data.put("pageNumber", pageNumber);
		data.put("pageSize", pageSize);
		
		// 查询条件
		data.put("keyword", StringUtils.trim(keyword));
		data.put("content", StringUtils.trim(content));
		data.put("groupid", groupid);
		
		try {
			replys = replyManagerService.getReplys(page, data);
			// OperLogUtil4gialen.log(   "查询记录:"+SafeVal.val( deleteList), ServletActionContext.getRequest());	
			 OperLogUtil4gialen.log(   "查询记录:"+SafeVal.val( ""), ServletActionContext.getRequest());	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listReplys";
	}

	//新增跳转
	public String addReply(){
		return "addReply";
	}
	
	//保存新增的信息
	public void saveReply(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			int groupid = 0;
			if(user.getDefaultGroup() != null && user.getDefaultGroup() > 0){
				groupid = user.getDefaultGroup();
			}
			TMbReply reply = new TMbReply();
			reply.setKeyword(StringUtils.trim(keyword));
			String md5keyword = MD5.GetMD5Code(StringUtils.trim(keyword));//MD5加密
			reply.setMd5keyword(md5keyword);
			reply.setContent(StringUtils.trim(content));
			reply.setRules(StringUtils.trim(rules));
			reply.setNewsId(newsId);
			reply.setGroupid(groupid);
			reply.setMultiReplyContent(StringUtils.trim(multiReplyContent));
			replyManagerService.save(reply);
			OperLogUtil4gialen.log(   "保存新增的信息:"+SafeVal.val( keyword), ServletActionContext.getRequest());	
			JsonUtil.getInstance().write(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//保存图文类型的关键词的信息
	public void saveReplyToNews(){
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = ServletActionContext.getRequest().getSession();
		try {
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getDefaultGroup() == null && user.getDefaultGroup() < 0){
				JsonUtil.getInstance().write(response, "nullgroup");
			}else{
				int groupid = user.getDefaultGroup();
				TMbReply reply = replyManagerService.getReplyByNewsId(newsId);
				if(reply == null){
					TMbReply temp = new TMbReply();
				    temp.setKeyword(StringUtils.trim(keyword));
				    String md5keyword = MD5.GetMD5Code(StringUtils.trim(keyword));//MD5加密
				    temp.setMd5keyword(md5keyword);
				    temp.setContent(null);
				    temp.setRules(null);
				    temp.setNewsId(newsId);
				    temp.setGroupid(groupid);
				    replyManagerService.save(temp);
				    JsonUtil.getInstance().write(response, "success");
			    }else {
				  reply.setKeyword(StringUtils.trim(keyword));
				  String md5keyword = MD5.GetMD5Code(StringUtils.trim(keyword));//MD5加密
				  reply.setMd5keyword(md5keyword);
				  replyManagerService.update(reply);
				  JsonUtil.getInstance().write(response, "success");
			   }
			}
			OperLogUtil4gialen.log(   "保存新增的信息:"+SafeVal.val( keyword), ServletActionContext.getRequest());	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//删除对应的图文关键词记录
	public void deleteNews(){
		try {
			if(newsId != null && newsId > 0){
				replyManagerService.deleteNews(newsId);
			}
			 OperLogUtil4gialen.log(   "/删除对应的图文关键词记录:"+SafeVal.val( newsId), ServletActionContext.getRequest());	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//修改跳转
	public String editReply(){
		try {
			reply = replyManagerService.getReply(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "editReply";
	}
	
	//判断是否已存在该关键词的记录
	public void checkKeyword(){
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = ServletActionContext.getRequest().getSession();
		try {
			int groupid = 0;
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getDefaultGroup() != null && user.getDefaultGroup() > 0){
				groupid = user.getDefaultGroup();
			}
			if(replyManagerService.checkKeyword(StringUtils.trim(keyword), groupid)){
				JsonUtil.getInstance().write(response, "exist");
			}else {
				JsonUtil.getInstance().write(response, "noexist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//保存编辑后的信息
	public void updateReply(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			TMbReply reply = replyManagerService.getReply(id);
			reply.setKeyword(StringUtils.trim(keyword));
			String md5keyword = MD5.GetMD5Code(StringUtils.trim(keyword));//MD5加密
			reply.setMd5keyword(md5keyword);
			reply.setContent(StringUtils.trim(content));
			reply.setRules(StringUtils.trim(rules));
			reply.setMultiReplyContent(StringUtils.trim(multiReplyContent));
			replyManagerService.update(reply);
			JsonUtil.getInstance().write(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//删除
	public void deleteReply(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(deleteList != null && !StringUtils.trim(deleteList).equals("")){
				if(replyManagerService.delete(deleteList)){
					JsonUtil.getInstance().write(response, "success");
					 OperLogUtil4gialen.log(   "/删除记录:"+SafeVal.val( deleteList), ServletActionContext.getRequest());	
				}else{
					JsonUtil.getInstance().write(response, "false");
				}
			}else {
				JsonUtil.getInstance().write(response, "null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public ReplyManagerService getReplyManagerService() {
		return replyManagerService;
	}

	public void setReplyManagerService(ReplyManagerService replyManagerService) {
		this.replyManagerService = replyManagerService;
	}

	public TMbReply getReply() {
		return reply;
	}

	public void setReply(TMbReply reply) {
		this.reply = reply;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getNewsId() {
		return newsId;
	}
	
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<TMbReply> getReplys() {
		return replys;
	}

	public void setReplys(List<TMbReply> replys) {
		this.replys = replys;
	}

	public String getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(String deleteList) {
		this.deleteList = deleteList;
	}

	public String getMultiReplyContent() {
		return multiReplyContent;
	}

	public void setMultiReplyContent(String multiReplyContent) {
		this.multiReplyContent = multiReplyContent;
	}
	
	
	
}
