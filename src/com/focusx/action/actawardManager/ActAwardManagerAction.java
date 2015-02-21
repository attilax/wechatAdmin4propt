package com.focusx.action.actawardManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.attilax.SafeVal;
import com.focusx.entity.TMbActAward;
import com.focusx.service.ActAwardManagerService;
import com.focusx.util.JsonUtil;
import com.focusx.util.OperLogUtil4gialen;
import com.focusx.util.Page;
import com.focusx.util.PageUtil;
import com.opensymphony.xwork2.ActionSupport;

public class ActAwardManagerAction extends ActionSupport{
	
	private ActAwardManagerService actAwardManagerService;
	private List<TMbActAward> actawardList;
	private TMbActAward actAward;
	private Integer id;         //主键ID
	private Integer activityId; //所属活动
	private String awardName;	//奖品名称
	private Integer awardCount; //奖品数量
	private Integer rate;		//中将比率
	private Integer type;       //奖品类型，目前只有积分、实物、现金劵和优惠劵几种
	private String remark;//说明
	
	private Page<TMbActAward> page;// 分页
	private String p;// 第几页
	private String ps;// 每页显示记录数
	
	//列表显示奖品
	public String listActAward(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> data = new HashMap<String, Object>();
		
		page = new Page<TMbActAward>(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(page, request);
		int pageNumber = pageParams[0];// 第几页
		int pageSize = pageParams[1];// 每页记录数
		// 分页条件
		data.put("pageNumber", pageNumber);
		data.put("pageSize", pageSize);
		
		// 查询条件
		data.put("awardName", StringUtils.trim(awardName));
		data.put("type", type);
		
		try {
			actawardList = actAwardManagerService.getActAwards(page, data);
			OperLogUtil4gialen.log(   "查询奖品信息:"+SafeVal.val(""), ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listActAward";
	}

	//跳转到新增界面
	public String showAddActAward(){
		return "showAddActAward";
	}
	
	//跳转到编辑界面
	public String showEditActAward(){
		try {
			actAward = actAwardManagerService.getActAwardById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "editActAward";
	}
	
	//保存新增奖品
	public String addActAward(){
		try {
			TMbActAward actaward = new TMbActAward();
			actaward.setActivityId(0);//默认是未选状态
			actaward.setAwardCount(awardCount);
			actaward.setAwardName(StringUtils.trim(awardName));
			actaward.setRemark(StringUtils.trim(remark));
			actaward.setType(type);
			actaward.setRate(rate);
			id = actAwardManagerService.addActAward(actaward);
			OperLogUtil4gialen.log(   "保存新增奖品:"+SafeVal.val(awardName), ServletActionContext.getRequest());	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "showActAward";
	}
	
	//保存编辑后的奖品信息
	public String saveActAward(){
		try {
			TMbActAward actaward = actAwardManagerService.getActAwardById(id);
			if(awardName != null && !awardName.equals("")){
				actaward.setAwardName(awardName);
			}
			if(awardCount != null && awardCount > 0){
				actaward.setAwardCount(awardCount);
			}
			actaward.setRemark(StringUtils.trim(remark));
			if(type != null && type > 0){
				actaward.setType(type);
			}
			actaward.setRate(rate);
			actAwardManagerService.saveActAward(actaward);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "showActAward";
	}
	
	//删除奖品信息
	public void deleteActAward(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			//1、判断该奖品关联的活动是否结束，如结束可删除，如还在进行中不能删除
			TMbActAward actaward = actAwardManagerService.getActAwardById(id);
			if(actaward != null){
				if(actaward.getActivityId() > 0){
					JsonUtil.write(response, "activity");
				}else {
					actAwardManagerService.deleteActAwardById(id);
					OperLogUtil4gialen.log(   "删除奖品信息:"+SafeVal.val(id), ServletActionContext.getRequest());
					JsonUtil.write(response, "true");
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
	
	//获取奖品信息
	public String showActAward(){
		try {
			actAward = actAwardManagerService.getActAwardById(id);
			OperLogUtil4gialen.log(   "获取奖品信息:"+SafeVal.val(id), ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "viewActAward";
	}
	
	
	
	public ActAwardManagerService getActAwardManagerService() {
		return actAwardManagerService;
	}

	public void setActAwardManagerService(
			ActAwardManagerService actAwardManagerService) {
		this.actAwardManagerService = actAwardManagerService;
	}

	public List<TMbActAward> getActawardList() {
		return actawardList;
	}

	public void setActawardList(List<TMbActAward> actawardList) {
		this.actawardList = actawardList;
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

	public Page<TMbActAward> getPage() {
		return page;
	}

	public void setPage(Page<TMbActAward> page) {
		this.page = page;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public Integer getAwardCount() {
		return awardCount;
	}

	public void setAwardCount(Integer awardCount) {
		this.awardCount = awardCount;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public TMbActAward getActAward() {
		return actAward;
	}

	public void setActAward(TMbActAward actAward) {
		this.actAward = actAward;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
