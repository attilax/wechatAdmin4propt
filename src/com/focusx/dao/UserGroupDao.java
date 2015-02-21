package com.focusx.dao;

import java.util.List;
import java.util.Map;

import com.focusx.entity.TUserGroup;
import com.focusx.entity.TUserUserToGroup;
import com.focusx.entity.TUserUsers;

public interface UserGroupDao {
	
	/**
	 *  获取所有用户分组信息
	 *  @param data 查询条件
	 *  @return List<TMbGroup> 分组信息集合
	 */
	public List<TUserGroup> getGroups(Map<String, Object> data);
	
	/**
	 *  获取当前分组的总和
	 *  @param data 查询条件
	 *  @return number 总和
	 */
	public Number getGroupCount(Map<String, Object> data);
	
	/**
	 *  根据分组名称查找分组
	 *  @param name 分组名称
	 *  @return true or false
	 */
	public String getGroupByName(String name);

	/**
	 *  保存新增的用户分组
	 *  @param userGroup 用户分组对象
	 */
	public Number save(TUserGroup userGroup);
	
	/**
	 *  获取单个用户分组
	 *  @param id 用户分组ID
	 */
	public TUserGroup getGroupById(Integer id);
	
	/**
	 *  判断某个分组下是否有用户
	 *  @param id 分组ID
	 */
	public boolean checkGroup(Integer id);
	
	/**
	 *  根据ID删除分组
	 *  @param id 分组ID
	 */
	public void delete(Integer id);
	
	/**
	 *  更新分组
	 *  @prarm usergroup 分组对象
	 */
	public void update(TUserGroup usergroup);
	
	/**
	 *  获取用户信息，列表显示某个分组下的用户
	 *  @param data 查询条件
	 */
	public List<TUserUsers> getUsersByGroupid(Map<String, Object> data);
	
	/**
	 *  获取用户信息，列表显示在某个分组中可以选择的用户
	 *  @param page 分页对象
	 *  @param data 查询条件
	 */
	public Number getUsersGroupCount(Map<String, Object> data);
	
	/**
	 *  保存选择的用户到用户分组表
	 *  @param usergroup 用户分组对象
	 */
	public void saveGroupToUser(TUserUserToGroup usergroup);
	
	/**
	 *  在某个用户分组中删除用户（除了选择的用户）
	 *  @param ids 选择的用户ID
	 *  @param id 用户分组ID
	 */
	public void deleteGroupToUser(String ids, Integer id);
}

