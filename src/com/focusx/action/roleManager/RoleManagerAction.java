package com.focusx.action.roleManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;


import com.focusx.entity.TUserPower;
import com.focusx.entity.TUserRole;
import com.focusx.service.RoleMamageService;
import com.focusx.util.Page;
import com.focusx.util.PageUtil;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

public class RoleManagerAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	public RoleMamageService roleManageService;// 服务层对象
	
	private TUserRole role; //角色对象

	private Page page;// 分页
	
	private String p;// 第几页
	
	private String ps;// 每页显示记录数 这两个参数 在本Action中没用到 在pageutil中用到了
	
	private Integer id;//角色ID
	private String name = "";
	private Short isSystem;
	private Integer vdnid;
	private Integer subSystemType;
	private String remark;
	
	//要删除的角色ID   ajax获取
	private String roleList="";
	
	//权限列表ID  ajax获取
	private String powerIdList="";
	
	//角色列表
	private List<TUserRole> rolesLst;
	
	//角色--权限(全部)列表
	private List<TUserPower> rolePowerLst;

	/*
	 * 重新赋权限，先删除原来的
	 */
	public String grantPower(){
		
		System.out.println(id);
		System.out.println(powerIdList);
		
		//更新权限
		boolean result = roleManageService.insertGrantPower(id, powerIdList, 3);
		
		
		//创建HttpServletResponse  
        HttpServletResponse response = ServletActionContext.getResponse();  
        
        //设置编码格式,以及信息类型  及 是否有缓存的设置  
        response.setContentType("application/json;charset=utf-8");  
          
        response.setHeader("caChe-Control", "no-cache");  
        
        //创建PrintWriter 对象 将信息写入到Reponse中  
        PrintWriter out;
		try {
			out = response.getWriter();
			if(result){
				out.print(id);  
			}else{
				out.print(result);
			}
			
	        //清空缓存  
	        out.flush();  
	        //关闭  
	        out.close();  
		} catch (IOException e) {
			e.printStackTrace();
		}  
		

		//Ajax 传递参数 没返回值
		return null;
	}
	
	/*
	 * 列出所有权限和角色所属权限
	 */
	public String getRolePower(){
		//角色列表
		rolesLst = roleManageService.getAllRoles();
		
		//权限列表
		rolePowerLst = roleManageService.getRolePower(id);
		return "rolePower";
	}
	
	/*
	 * 列出所有角色,不包含分页
	 */
	public String listAllRoles(){
		rolesLst = roleManageService.getAllRoles();
		return "rolePower";
	}
	
	/**
	 *  角色列表
	 */
	public String listRoles() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			page = new Page(PageUtil.PAGE_SIZE);
			Map<String, Object> data = new HashMap<String, Object>();
			int[] pageParams = PageUtil.init(page, request);
			int pageNumber = pageParams[0];// 第几页
			int pageSize = pageParams[1];// 每页记录数
			// 分页条件
			data.put("pageNumber", pageNumber);
			data.put("pageSize", pageSize);
			
			data.put("name", StringUtils.trim(name));
			roleManageService.getRoles(page, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listRoles";
	}
	/*
	 * 直接转向到 角色新增界面addRole.jsp
	 */
	public String showAddPage(){
		return "showAddPage";
	}
	
	/*
	 * 角色新增
	 */
	public String addRole(){
		Date now = new Date();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("name", name);
		dataMap.put("remark", remark);
		dataMap.put("createTime", now);
		dataMap.put("alterTime", now);
		dataMap.put("isSystem", "0");//是否是系统定义的角色(0不是,1是)
		
		id = roleManageService.insertRole(dataMap).intValue();
		
		return "showRole";
	}
	/*
	 * 角色信息展示
	 */
	public String showViewPage(){
		role = roleManageService.getRoleById(id);
		return "viewRole";
	}
	/*
	 * 编辑界面展示
	 */
	public String showEditPage(){
		role = roleManageService.getRoleById(id);
		return "editRolePage";
	}
	/*
	 * 编辑角色信息
	 */
	public String editRole(){
		
		boolean result = false;
		Date now = new Date();
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("id", id);
		dataMap.put("name", name);
		dataMap.put("alterTime", now);
		dataMap.put("remark", remark);
		
		result = roleManageService.updateRole(dataMap);
		
		if(result == false){
			return "error";
		}
		return "showRole";
	}
	/*
	 * 删除角色
	 */
	public String deleteRoles(){
		
		boolean result = false;
		
		result = roleManageService.deleteRoles(roleList);
		
		
		//创建Gson对象  
        Gson gson  =  new Gson();  
       
        String jsonData = gson.toJson(result);
        
      //创建HttpServletResponse  
        HttpServletResponse response = ServletActionContext.getResponse();  
        
      //设置编码格式,以及信息类型  及 是否有缓存的设置  
        response.setContentType("application/json;charset=utf-8");  
          
        response.setHeader("caChe-Control", "no-cache");  
        
        //创建PrintWriter 对象 将信息写入到Reponse中  
        PrintWriter out;
		try {
			out = response.getWriter();
			out.print(result);  
	        //清空缓存  
	        out.flush();  
	        //关闭  
	        out.close();  
		} catch (IOException e) {
			e.printStackTrace();
		}  

		/*if(result == false){
			return "error";
		}*/
		
		return null;
	}
	
	public Page getPage() {
		return page;
	}

	public String getP() {
		return p;
	}

	public String getPs() {
		return ps;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public void setP(String p) {
		this.p = p;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public RoleMamageService getRoleManageService() {
		return roleManageService;
	}

	public void setRoleManageService(RoleMamageService roleManageService) {
		this.roleManageService = roleManageService;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Short getIsSystem() {
		return isSystem;
	}

	public Integer getVdnid() {
		return vdnid;
	}

	public Integer getSubSystemType() {
		return subSystemType;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIsSystem(Short isSystem) {
		this.isSystem = isSystem;
	}

	public void setVdnid(Integer vdnid) {
		this.vdnid = vdnid;
	}

	public void setSubSystemType(Integer subSystemType) {
		this.subSystemType = subSystemType;
	}

	public TUserRole getRole() {
		return role;
	}
	public void setRole(TUserRole role) {
		this.role = role;
	}
	public List<TUserRole> getRolesLst() {
		return rolesLst;
	}

	public void setRolesLst(List<TUserRole> rolesLst) {
		this.rolesLst = rolesLst;
	}

	public String getRoleList() {
		return roleList;
	}
	public void setRoleList(String roleList) {
		this.roleList = roleList;
	}

	public List<TUserPower> getRolePowerLst() {
		return rolePowerLst;
	}

	public void setRolePowerLst(List<TUserPower> rolePowerLst) {
		this.rolePowerLst = rolePowerLst;
	}

	public String getPowerIdList() {
		return powerIdList;
	}

	public void setPowerIdList(String powerIdList) {
		this.powerIdList = powerIdList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
