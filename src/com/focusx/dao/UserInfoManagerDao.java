package com.focusx.dao;

import java.util.List;
import java.util.Map;

import com.focusx.entity.TUserUsers;

public interface UserInfoManagerDao {

	
	/**
	 * 删除该角色下的用户
	 * @param roleId  角色Id
	 * @param userIds 用户ID集合
	 * @return boolean
	 * 
	 */
	public boolean deleteRoleFromUser(int roleId,String[] userIds);
	
	/**
	 * 为用户分配角色
	 * @param roleId
	 * @param userIds
	 * @return boolean
	 * 
	 */
	public boolean insertUserIntoRole(int roleId,String[] userIds);
	
	/**
	 * 获取所有用户
	 * 
	 * @return List<TUserUsers>
	 *
	 */
	public List<TUserUsers> getAllUsers();
	
	/**
	 * 获取所有用户信息
	 * @param firstResult开始记录
	 * @param maxResults每页显示记录数
	 * @return list
	 */
	public List<TUserUsers> getPagedUsers(int firstResult,int maxResults);
	
	/**
	 * 获取用户列表总记录数
	 */
	public int getUserTotalCount();
	
	/**
	 * 获取搜索条件的用户记录
	 */
	public List<TUserUsers> getSearchUsers(String condition,int firstResult,int maxResults);
	
	/**
	 * 获取搜索添加的总条数
	 */
	public int getSearchUsersCount(String condition);
	
	/**
	 * 根据用户id获取用户信息
	 * @param userId用户id
	 * @return TUserUsers
	 */
	public TUserUsers getUserById(int userId);
	
	/**
	 * 更新修改的用户信息
	 * @param user
	 * @return boolean
	 */
	public boolean updateUserInfo(TUserUsers user);
	
	/**
	 * 插入新增的用户信息
	 * @param user
	 * @return Number   返回主键ID
	 */
	public Number insertUserInfo(TUserUsers user);
	
	/**
	 * 根据用户id,批量删除用户
	 */
	public boolean deleteUsersByIds(String[] ids);
	
	/**
	 * 通过用户编码来获取用户
	 * 用户编码唯一  ,用于判断新增编码是否唯一
	 */
	public TUserUsers getUserByWorkNo(String workNo);
	
	/**
	 *  根据用户名称和用户密码获取用户
	 *  @param data 查询条件
	 */
	public TUserUsers login(Map<String, String> data);
	
	/**
	 *  根据用户姓名查找用户
	 *  @param name
	 *  @return TUserUsers
	 */
	public TUserUsers getUserByName(String name);
}
