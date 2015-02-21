package com.focusx.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.focusx.dao.RoleManageDao;
import com.focusx.entity.TUserGrantPower;
import com.focusx.entity.TUserPower;
import com.focusx.entity.TUserRole;
import com.focusx.service.RoleMamageService;
import com.focusx.util.Page;

/** 
 * com.focusx.service.impl
 * RoleManageServiceImpl.java 
 * author:vincente  2013-8-16 
 */
public class RoleManageServiceImpl implements RoleMamageService {

	private RoleManageDao roleManageDao;
	
	
	public boolean deleteRolePower(int id, String powerIds, int ownerType) {
		boolean result = true;
		
		String[] powerId = null;
		
		if(powerIds.length()>0){
			powerId = powerIds.split(",");
		}
		result = roleManageDao.deleteRolePower(id, powerId, ownerType);
		
		return result;
	}

	public boolean insertGrantPower(int id, String powerIds, int ownerType) {
		boolean result = true;
		
		String[] powerId = null;
		
		if(powerIds.length()>0){
			powerId = powerIds.split(",");
		}
		
		//先判断删除是否成功
		//result = roleManageDao.deleteRolePower(id, powerId, ownerType);
		
		//先删除用户角色中间表
		short isOwn = 1;
		TUserGrantPower grantpower = new TUserGrantPower();
		grantpower.setOwnerType(3);//拥有者类型(1,2,3)角色,部门,人
		grantpower.setOwnerId(id);//拥有者ID
		grantpower.setIsOwn(isOwn);//是否拥有此权限(0没有,1有)
		
		roleManageDao.deleteUserGrantpower(grantpower);
		
		if(result){
			roleManageDao.insertGrantPower(id, powerId, ownerType);
		}
		
		return result;
	}
	
	
	public List<TUserRole> getAllRoles(){
		return roleManageDao.getAllRoles();
	}
	
	public List<TUserPower> getRolePower(int ownerId){
		List<TUserPower> powerLst = roleManageDao.getAllPower();
		List<TUserGrantPower> grantpowerLst = roleManageDao.getGrantpowerByOwner(ownerId);
		TUserPower power = null;
		TUserGrantPower grantpower = null;
		for(int i = 0;i<grantpowerLst.size();i++){
			grantpower = grantpowerLst.get(i);
			for(int j = 0;j<powerLst.size();j++){
				power = powerLst.get(j);
				if(power.getId().equals(grantpower.getPowerId())){
					power.setRoleOwerId(grantpower.getOwnerId());
				}
			}
		}
		return powerLst;
	}
	
	
	public boolean deleteRoles(String deleteList) {
		boolean result = false;
		if(deleteList.length()>0){
			String[] roleIds = deleteList.split(",");
			result = roleManageDao.deleteRoles(roleIds);
		}
		return result;
	}

	public TUserRole getRoleById(int roleId) {
		TUserRole role = roleManageDao.getRoleById(roleId);
		return role;
	}

	public List<TUserRole> getRoles(Page page, Map<String, Object> data) {
		List<TUserRole> roleLst = roleManageDao.getRoles(data);
		page.setResult(roleLst);
		int totalCount = roleManageDao.getRoleNum(data).intValue();
		page.setTotalCount(totalCount);
		return roleLst;
	}

	public Number insertRole(Map<String, Object> dataMap) {
		TUserRole role = new TUserRole();
		
		String name = (String)dataMap.get("name");//角色名称
		String remark = (String)dataMap.get("remark");//备注
		
		Date createTime =(Date)dataMap.get("createTime");//创建日期
		Date alterTime =(Date)dataMap.get("alterTime");//修改日期
		
		String isSystem =(String)dataMap.get("isSystem");//是否是系统定义的角色(0不是,1是)
		
		role.setRemark(remark);
		role.setAlterTime(alterTime);
		role.setCreateTime(createTime);
		role.setName(name);
		role.setIsSystem(Short.parseShort(isSystem));
		
		Number id = roleManageDao.insertRole(role);
		
		return id;
	}

	public boolean updateRole(Map<String, Object> dataMap) {
		boolean result = true;
		
		TUserRole role = new TUserRole();
		
		int id = (Integer) dataMap.get("id"); 
		String name = (String)dataMap.get("name");//角色名称
		String remark = (String)dataMap.get("remark");//备注
		
		Date alterTime =(Date)dataMap.get("alterTime");//修改日期
		
		role.setId(id);
		role.setRemark(remark);
		role.setAlterTime(alterTime);
		role.setName(name);
		
		result = roleManageDao.updateRole(role);
		
		return result;
	}

	public RoleManageDao getRoleManageDao() {
		return roleManageDao;
	}

	public void setRoleManageDao(RoleManageDao roleManageDao) {
		this.roleManageDao = roleManageDao;
	}

	

}
