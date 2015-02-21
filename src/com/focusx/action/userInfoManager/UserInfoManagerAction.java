package com.focusx.action.userInfoManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import cn.freeteam.util.OperLogUtil;

import com.attilax.SafeVal;
import com.focusx.entity.TMbGroup;
import com.focusx.entity.TUserUsers;
import com.focusx.service.BranchManagerService;
import com.focusx.service.UserInfoManagerService;
import com.focusx.util.Constant;
import com.focusx.util.EncryptUtil;
import com.focusx.util.JsonUtil;
import com.focusx.util.OperLogUtil4gialen;
import com.focusx.util.Page;
import com.focusx.util.PageUtil;
import com.opensymphony.xwork2.ActionSupport;

public class UserInfoManagerAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	// 服务层对象 -- 用户
	private UserInfoManagerService userInfoManagerService;
	private BranchManagerService branchManagerService;
	// 分页
	private Page page;
	// 第几页
	private String p;
	// 每页显示记录数 这两个参数 在本Action中没用到 在pageutil中用到了
	private String ps;

	// 删除用户ID列表  ----用户ID列表  用Ajax传回
	private String deleteUsersList="";

	// 用户信息
	private Integer id;// 主键
	private String workNo = "";// 座作员工号(座席类必须为数字组合)
	private String name = "";// 操作员真实姓名
	private String shortName;// 操作员简称
	private String passWord;// 操作员密码
	private String tel;// 电话号码
	private String extensionTel;// 分机号码
	private String mobileTel;// 移动电话
	private String email;// 电子邮箱地址
	private String address;// 详细地址
	
    private String groupname;//分公司名称
	private List<TMbGroup> groups;//分公司对象数组
    private Integer groupid;

	// 用户对象
	private TUserUsers user;

	// 用户提示信息
	private String message;
	
	//用户列表
	private List<TUserUsers> usersList;

	
	/**
	 * 获取所有用户信息
	 * 
	 */
	public String listUsers() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();

		page = new Page(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(page, request);
		TUserUsers user = (TUserUsers) session.getAttribute(session.getId());
		List<TUserUsers> lst = new ArrayList<TUserUsers>();
		if(user.getIsSystem() == Constant.ONE){
			lst = userInfoManagerService.getPagedUsers(page,pageParams);
		}else {
			lst.add(user);
			page.setResult(lst);
			page.setTotalCount(Constant.ONE);
		}
	//	OperLogUtil4gialen.log(   "查询用户信息:"+SafeVal.val(""), ServletActionContext.getRequest());
		return "listUsers";
	}

	public String deleteUsers() {
		HttpServletRequest request = ServletActionContext.getRequest();
		// System.out.println(request.getParameter("deleteList"));
		// 创建Gson对象
		// Gson gson = new Gson();
		System.out.println("返回数据" + deleteUsersList);
		userInfoManagerService.deleteUsers(deleteUsersList);
		OperLogUtil4gialen.log(   "删除用户"+deleteUsersList, ServletActionContext.getRequest());	
		// 用Ajax 返回结果
		return null;
	}

	/*
	 * 根据编码和姓名查找用户，姓名可模糊查找,添加电话号码和电子邮箱查找
	 */
	public String searchUsers() {

		String condition = "";
		HttpServletRequest request = ServletActionContext.getRequest();

		
		if (workNo.length() > 0 && name.trim().length() == 0) {
			condition = " and workNo like '%" + workNo + "%'";
		} else if (workNo.length() == 0 && name.trim().length() > 0) {
			condition = " and name like '%" + name.trim() + "%'";
		} else if (workNo.length() > 0 && name.trim().length() > 0) {
			condition = " and (workNo like '%" + workNo + "%'"
					+ " or name like '%" + name.trim() + "%')";
		} 

		StringBuffer data = new StringBuffer();
		if(tel != null && !tel.trim().equals("")){
			data.append(" and tel='"+tel.trim()+"'");
		}
		if(email != null && !email.trim().equals("")){
			data.append(" and email='"+email.trim()+"'");
		}
		data.append(condition);
		page = new Page(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(page, request);
		userInfoManagerService.getSearchUsers(data.toString(), page, pageParams);

		return "listUsers";
	}

	/*
	 * 直接转向到 用户新增界面addUser.jsp
	 */
	public String showAddPage() {
		return "showAddPage";
	}

	/**
	 * 根据用户id查找用户
	 * 
	 * @return json obj
	 */
	public String getUserById() {
		user = userInfoManagerService.getUserById(id);
		return "getUserById";
	}

	/*
	 * 获取用户ID，转向编辑界面editUser.jsp
	 */
	public String showEditUserPage() {
		user = userInfoManagerService.getUserById(id);
		return "showEditUserPage";
	}

	/**
	 *  修改用户信息
	 */
	public String editUserInfo() {
		boolean checked = false;
		try {
			TUserUsers user = userInfoManagerService.getUserById(id);

			if(passWord != null && !StringUtils.trim(passWord).equals("")){
				//密码加密处理
				passWord = EncryptUtil.md5Encrypt(passWord);
				user.setPassWord(passWord);
			}
			if(name != null && !StringUtils.trim(name).equals("")){
				name = StringUtils.trim(name);
				user.setName(name);
			}
			if(shortName != null && !StringUtils.trim(shortName).equals("")){
				shortName = StringUtils.trim(shortName);
				user.setShortName(shortName);
			}
			user.setTel(tel);
			user.setExtensionTel(extensionTel);
			user.setMobileTel(mobileTel);
			user.setEmail(email);
			user.setAddress(address);
			checked = userInfoManagerService.updateUserInfo(user);
			OperLogUtil4gialen.log(   "修改用户"+name, ServletActionContext.getRequest());	
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (checked) {
			return "editUserInfo";
		} else {
			return "error";
		}
	}

	/**
	 * 新增用户信息
	 * @return
	 */
	public String addUserInfo() {
		// 先判断用户编码是否唯一
		TUserUsers temp = userInfoManagerService.getUserByWorkNo(workNo);
		if (temp != null) {
			message = "用户编号已重复,请重新填写！";
			return "showAddPage";
		}
		TUserUsers temp2 = userInfoManagerService.getUserByName(StringUtils.trim(name));
		if (temp2 != null) {
			message = "用户姓名已重复,请重新填写！";
			return "showAddPage";
		}

		TUserUsers user = new TUserUsers();
		//密码加密处理
		passWord = EncryptUtil.md5Encrypt(passWord);
		user.setPassWord(passWord);
		user.setWorkNo(workNo);
		user.setName(name);
		user.setAddress(address);
		user.setEmail(email);
		user.setShortName(shortName);
		user.setTel(tel);
		user.setExtensionTel(extensionTel);
		user.setMobileTel(mobileTel);
		user.setDefaultGroup(groupid);
		user.setIsSystem(Constant.ZERO);
		
		// 插入是否成功
		Number id = userInfoManagerService.insertUserInfo(user);
		this.id = id.intValue();
		if (id.intValue() == -1) {
			return "error";
		} else {
			OperLogUtil4gialen.log(   "新增用户"+name, ServletActionContext.getRequest());	
			return "addUserInfo";
		}
	}
	
	/**
	 *  跳转到分公司选择界面
	 */ 
	public String chooseGroup(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			Map<String, Object> data = new HashMap<String, Object>();

			page = new Page<TMbGroup>(PageUtil.PAGE_SIZE);
			int[] pageParams = PageUtil.init(page, request);
			int pageNumber = pageParams[0];// 第几页
			int pageSize = pageParams[1];// 每页记录数
			// 分页条件
			data.put("pageNumber", pageNumber);
			data.put("pageSize", pageSize);

			// 查询条件
			data.put("groupname", StringUtils.trim(groupname));
		    groups = branchManagerService.getAllOneGroup(page, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "groups";
	}
	
	//检测是否已存在该用户姓名
	public void checkName(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(name != null && !StringUtils.trim(name).equals("")){
				TUserUsers temp = userInfoManagerService.getUserByName(StringUtils.trim(name));
				if(temp != null){
					JsonUtil.getInstance().write(response, "exist");
				}else {
				    JsonUtil.getInstance().write(response, "noexist");
				}
			}else {
				JsonUtil.getInstance().write(response, "null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public UserInfoManagerService getUserInfoManagerService() {
		return userInfoManagerService;
	}

	public void setUserInfoManagerService(
			UserInfoManagerService userInfoManagerService) {
		this.userInfoManagerService = userInfoManagerService;
	}

	public String getDeleteUsersList() {
		return deleteUsersList;
	}

	public void setDeleteUsersList(String deleteUsersList) {
		this.deleteUsersList = deleteUsersList;
	}

	public String getP() {
		return p;
	}

	public String getPs() {
		return ps;
	}

	public void setP(String p) {
		this.p = p;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public Integer getId() {
		return id;
	}

	public String getWorkNo() {
		return workNo;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public String getPassWord() {
		return passWord;
	}

	public String getTel() {
		return tel;
	}

	public String getExtensionTel() {
		return extensionTel;
	}

	public String getMobileTel() {
		return mobileTel;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setExtensionTel(String extensionTel) {
		this.extensionTel = extensionTel;
	}

	public void setMobileTel(String mobileTel) {
		this.mobileTel = mobileTel;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public TUserUsers getUser() {
		return user;
	}

	public void setUser(TUserUsers user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public List<TUserUsers> getUsersList() {
		return usersList;
	}
	public void setUsersList(List<TUserUsers> usersList) {
		this.usersList = usersList;
	}

	public BranchManagerService getBranchManagerService() {
		return branchManagerService;
	}

	public void setBranchManagerService(BranchManagerService branchManagerService) {
		this.branchManagerService = branchManagerService;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public List<TMbGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<TMbGroup> groups) {
		this.groups = groups;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

}
