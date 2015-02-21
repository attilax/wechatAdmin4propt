package com.focusx.dao;

import java.util.List;
import java.util.Map;

import com.focusx.entity.TUserGrantPower;
import com.focusx.entity.TUserPower;
import com.focusx.entity.TUserRole;
import com.focusx.util.Page;
/** 
 * com.focusx.dao
 * RoleManagerDao.java 
 * author:vincente  2013-8-16 
 */
public interface RoleManageDao {
	
	
	/**删除角色的权限
	 * 
	 * @param grantpower  角色权限中间表
	 */
	public boolean deleteUserGrantpower(TUserGrantPower grantpower);
	/**
	 * 删除角色的权限
	 * @param id  角色ID
	 * @param powerId 角色所属权限的ID
	 * @param ownerType 拥有者类型(1,2,3)角色,部门,人
	 * @return boolean
	 */
	public boolean deleteRolePower(int id,String[] powerId,int ownerType);
	
	
	/**
	 * 给角色赋权限
	 * @param id 角色ID
	 * @param powerId 角色所属权限的ID
	 * @param ownerType 拥有者类型(1,2,3)角色,部门,人
	 * @return boolean
	 * 
	 */
	public boolean insertGrantPower(int id,String[] powerId,int ownerType);
	
	/*
	 * 获取权限中间表--通过权限所属对象ID ownerId
	 *  @param ownerId 权限所属ID----角色ID
	 * 
	 */
	public List<TUserGrantPower> getGrantpowerByOwner(int ownerId);
	
	/*
	 * 获取权限类型是角色的所有权限
	 */
	public List<TUserPower> getAllPower();
	/*
	 * 获取所有角色
	 */
	public List<TUserRole> getAllRoles();
		
	/**
	 *  根据查询条件获取角色信息
	 *  @param page 分页
	 *  @param data 查询条件
	 *  @return list 角色列表
	 */
	public List<TUserRole> getRoles(Map<String, Object> data);
	
	/**
	 *  获取指定条件的角色总数
	 *  @param 查询条件
	 *  @return Number 根据条件查询的总记录数
	 */
	public Number getRoleNum(Map<String, Object> data);
		
	/*
	 * 通过角色ID获取角色信息
	 * 
	 * @roleId 角色ID
	 * @return TUserRole 角色对象
	 */
	public TUserRole getRoleById(int roleId);
		
	/*
	 * 修改角色信息
	 * 
	 * @role 角色对象
	 * @return boolean true-成功;false-失败
	 */
	public boolean updateRole(TUserRole role);
	
	/*
	 * 新增角色
	 * 
	 * @role 角色对象
	 * @return Number 成功后返回保存主键ID
	 */
	public Number insertRole(TUserRole role);
	
	/*
	 * 删除角色
	 * 
	 * @roleIds 角色ID数组
	 * @return boolean true-成功;false-失败
	 */
	public boolean deleteRoles(String[] roleIds);
}
