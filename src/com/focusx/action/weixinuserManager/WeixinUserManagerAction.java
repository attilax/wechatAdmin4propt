package com.focusx.action.weixinuserManager;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.attilax.SafeVal;
import com.focusx.entity.TMbWeixinUser;
import com.focusx.entity.weixin.UserInfo;
import com.focusx.entity.weixin.UserList;
import com.focusx.service.WeixinUserManagerService;
import com.focusx.util.Constant;
import com.focusx.util.JsonUtil;
import com.focusx.util.OperLogUtil4gialen;
import com.focusx.util.Page;
import com.focusx.util.PageUtil;
import com.focusx.util.WeixinUtil;

public class WeixinUserManagerAction {

	protected static Logger logger = Logger.getLogger("WeixinUserManagerAction");
	private WeixinUserManagerService weixinUserManagerService;
	private List<TMbWeixinUser> weixinuserList;//微信粉丝数组
	private List<TMbWeixinUser> nullGroupList;//未分组的微信粉丝数组
	private TMbWeixinUser weixinUser; //微信粉丝对象
	private Page<TMbWeixinUser> page;// 分页
	private String p;// 第几页
	private String ps;// 每页显示记录数
	
    private Integer userId;
    private Integer insertFlag; //判断粉丝是否首次关注，0 代表首次关注
    private Integer fromtype;//标记用户是主动如何关注，0搜索关注1扫描关注
    private Integer groupid;//分组ID
	private String nickname;//呢称
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String deleteids;//删除的微信客户ID集合
	private Integer sex;//客户性别
	private Integer subscribe;//用户是否订阅该公众号标识
	private String openid;//用户的标识
	private String city;//用户所在城市
	private String country;//用户所在国家
	private String province;//用户所在省份
	private String language;//用户的语言
	private String headimgurl;//用户头像
	private Date subscribeTime;//用户关注时间
	private Integer memberType;//用于列表页，表示是否是会员，0 会员、1 非会员、 -1 全部
	
	private String userIds;//手动分组的粉丝ID集合字符串
	
	/**
	 *  列表显示微信粉丝信息
	 */
	public String listUser(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> data = new HashMap<String, Object>();
		
		page = new Page<TMbWeixinUser>(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(page, request);
		int pageNumber = pageParams[0];// 第几页
		int pageSize = pageParams[1];// 每页记录数
		// 分页条件
		data.put("pageNumber", pageNumber);
		data.put("pageSize", pageSize);
		
		// 查询条件
		data.put("startTime", startTime);
		data.put("endTime", endTime);
		data.put("nickname", nickname);
		data.put("subscribe", subscribe);
		data.put("memberType", memberType);
		data.put("insertFlag", insertFlag);
		
		if(province != null && !"".equals(province)){
			data.put("province",province);
		}
		
		if(city != null && !"".equals(city)){
			data.put("city",city);
		}
		
		try {
			weixinuserList = weixinUserManagerService.getWeixinUsers(page, data);
		   	 OperLogUtil4gialen.log("列表显示微信粉丝信息:"+SafeVal.val( ""), ServletActionContext.getRequest());	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listUser";
	}
	
	/**
	 *  获取对应的微信粉丝信息，跳转到微信粉丝详细页面
	 */
	public String view(){
		try {
			weixinUser = weixinUserManagerService.getWeixinUserByUserId(userId);
		  	 OperLogUtil4gialen.log("跳转到微信粉丝详细页面:"+SafeVal.val( userId), ServletActionContext.getRequest());	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "view";
	}
	
	//跳转到未能按照省市或现在存在未分组的粉丝列表页
	public String nullGroup(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> data = new HashMap<String, Object>();
		
		page = new Page<TMbWeixinUser>(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(page, request);
		int pageNumber = pageParams[0];// 第几页
		int pageSize = pageParams[1];// 每页记录数
		// 分页条件
		data.put("pageNumber", pageNumber);
		data.put("pageSize", pageSize);
		
		// 查询条件
		data.put("nickname", StringUtils.trim(nickname));
		
		if(province != null && !"".equals(province)){
			data.put("province",province);
		}
		
		if(city != null && !"".equals(city)){
			data.put("city",city);
		}
		
		try {
			nullGroupList = weixinUserManagerService.getNullGroupList(page, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "nullGroup";
	}
	
	//手动分组
	public void setGroupToWeixinUser(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(groupid != null && userIds != null && !StringUtils.trim(userIds).equals("")){
				System.out.println(userIds);
				weixinUserManagerService.setGroupToWeixinUser(groupid, userIds);
				JsonUtil.getInstance().write(response, "success");
			}else {
				JsonUtil.getInstance().write(response, "null");
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
	
	//获取项目运营前的粉丝数据并插入到数据库
	public void setWeixinUser(){
		String next_openid = "";
		try {
			logger.info("获取关注服务号的粉丝信息方法开始");
			long starttime = System.currentTimeMillis();//开始时间
			UserList list = WeixinUtil.getUserList(Constant.token.getToken(), next_openid);
			if(list != null && list.getTotal() > 0){
				logger.info("当前粉丝总数是"+list.getTotal());
				int num = list.getTotal()/10000;
				num = (num == 0) ? 1 : num;//防止关注的粉丝低于10000而出现num=0
				for(int i = 0; i < num; i++){
					logger.info("每次获取最大个数10000，运行次数为"+num+"，当前第"+i+"次，next_openid为"+next_openid);
					UserList userList = WeixinUtil.getUserList(Constant.token.getToken(), next_openid);
					if(userList != null){
						logger.info("当前第"+i+"次获取到"+userList.getTotal()+"粉丝个数");
						next_openid = userList.getNext_openid();
						String[] openids = userList.getOpenid();
						for(String openid : openids){
							UserInfo userInfo = WeixinUtil.getUserInfo(Constant.token.getToken(), openid);
							System.out.println(userInfo.getOpenid()+"===="+userInfo.getNickname());
//							if(weixinUserManagerService.checkWeixinUser(openid)){//判断数据库是否存在该openid
//								UserInfo userInfo = WeixinUtil.getUserInfo(Constant.token.getToken(), openid);
//								TMbWeixinUser user = new TMbWeixinUser();
//								user.setSubscribe(userInfo.getSubscribe());
//								user.setOpenid(openid);
//								user.setSex(userInfo.getSex());
//								user.setCity(userInfo.getCity());
//								user.setCountry(userInfo.getCountry());
//								user.setProvince(userInfo.getProvince());
//								user.setLanguage(userInfo.getLanguage());
//								user.setHeadimgurl(userInfo.getHeadimgurl());
//								user.setSubscribeTime(userInfo.getSubscribe_time());
//								user.setNickname(userInfo.getNickname());
//								
//								user.setInsertFlag(Constant.ONE);
//								user.setFromtype(Constant.ZERO);
//								user.setGroupid(Constant.ZERO);
//								user.setMemberId("");
//								user.setIsSign(Constant.ZERO);
//								user.setSignDate(null);
//								user.setBindDate(null);
//								user.setCreateTime(new Date());
//								weixinUserManagerService.save(user);
//							}
						}
					}else {
						break;
					}
				}
				long endtime = System.currentTimeMillis();//结束时间
				logger.info("方法运行结束，所用时间为"+(endtime-starttime)/1000+"s");
			}else {
				logger.info("该服务号不存在粉丝");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("运行setWeixinUser()方法出现异常，next_openid是"+next_openid,e);
		}
	}

	
	
	public WeixinUserManagerService getWeixinUserManagerService() {
		return weixinUserManagerService;
	}

	public void setWeixinUserManagerService(
			WeixinUserManagerService weixinUserManagerService) {
		this.weixinUserManagerService = weixinUserManagerService;
	}

	public Page<TMbWeixinUser> getPage() {
		return page;
	}

	public void setPage(Page<TMbWeixinUser> page) {
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

	public List<TMbWeixinUser> getWeixinuserList() {
		return weixinuserList;
	}

	public void setWeixinuserList(List<TMbWeixinUser> weixinuserList) {
		this.weixinuserList = weixinuserList;
	}

	public TMbWeixinUser getWeixinUser() {
		return weixinUser;
	}

	public void setWeixinUser(TMbWeixinUser weixinUser) {
		this.weixinUser = weixinUser;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getInsertFlag() {
		return insertFlag;
	}

	public void setInsertFlag(Integer insertFlag) {
		this.insertFlag = insertFlag;
	}

	public Integer getFromtype() {
		return fromtype;
	}

	public void setFromtype(Integer fromtype) {
		this.fromtype = fromtype;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
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

	public String getDeleteids() {
		return deleteids;
	}

	public void setDeleteids(String deleteids) {
		this.deleteids = deleteids;
	}

	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public List<TMbWeixinUser> getNullGroupList() {
		return nullGroupList;
	}

	public void setNullGroupList(List<TMbWeixinUser> nullGroupList) {
		this.nullGroupList = nullGroupList;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	
}
