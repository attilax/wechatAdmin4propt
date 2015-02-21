package com.focusx.service;

import java.util.List;
import java.util.Map;

import com.focusx.entity.TUserPower;
import com.focusx.entity.TUserRole;
import com.focusx.util.Page;

public interface RoleMamageService {
	
	/**
	 * 删除角色的权限
	 * @param id  角色ID
	 * @param powerIds 角色所属权限的ID
	 * @param ownerType 拥有者类型(1,2,3)角色,部门,人
	 * @return boolean
	 */
	public boolean deleteRolePower(int id,String powerIds,int ownerType);
	
	
	/**
	 * 给角色赋权限
	 * @param id 角色ID
	 * @param powerIds 角色所属权限的ID
	 * @param ownerType 拥有者类型(1,2,3)角色,部门,人
	 * @return boolean
	 * 
	 */
	public boolean insertGrantPower(int id,String powerIds,int ownerType);
	
	/*
	 * 通过ownerId  来获取角色全部权限(通过在TUserPower中设置角色ID,来记录权限属于那个角色)----因为要取出全部权限和属于该角色的权限
	 */
	public List<TUserPower> getRolePower(int ownerId);
	/*
	 * 获取所有角色
	 */
	public List<TUserRole> getAllRoles();
	
	/**
	 *  根据查询条件获取角色信息
	 *  @param page 分页
	 *  @param data 查询条件
	 */
	public List<TUserRole> getRoles(Page page, Map<String, Object> data);
	
	/*
	 * @param roleId   角色ID
	 * @return TUserRole 角色对象
	 */
	public TUserRole getRoleById(int roleId);
	
	/*
	 * 更新修改的角色信息
	 * @param dataMap
	 * @return boolean true成功 false失败
	 */
	public boolean updateRole(Map<String,Object> dataMap);
	
	/*
	 * 新增的角色信息
	 * @param dataMap
	 * @return Number  返回插入对象的主键ID
	 */
	public Number insertRole(Map<String,Object> dataMap);
	
	/*
	 * 批量删除角色信息
	 * @param deleteList 角色id列表
	 * @return boolean  
	 */
	public boolean deleteRoles(String deleteList);
}
