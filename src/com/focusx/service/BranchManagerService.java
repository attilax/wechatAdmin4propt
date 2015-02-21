package com.focusx.service;

import java.util.List;
import java.util.Map;

import com.focusx.entity.TMbGroup;
import com.focusx.util.Page;

public interface BranchManagerService {
	
	
	/**
	 *  获取某个父分组下的所有分组信息
	 *  @param page 分页对象
	 *  @param data 查询条件
	 *  @return List<TMbGroup> 分组信息集合
	 */
	public List<TMbGroup> getGroups(Page<TMbGroup> page, Map<String, Object> data);
	
	/**
	 *  新增一个分组
	 *  @param group 包含名称和时间的分组
	 *  @return number 分组ID
	 */
	public Number add(TMbGroup group);
	
	/**
	 *  根据ID删除分组
	 *  @param groupid 分组ID
	 */
	public void delete(Integer groupid);
	
	/**
	 *  更新分组
	 *  @param group 更新分组对象
	 */
	public void update(TMbGroup group);
	
	/**
	 *  根据分组名称查找分组
	 *  @param groupname 分组名称
	 *  @return true or false
	 */
	public String getGroupByGroupname(String groupname);
	
	/**
	 *  删除已存在但取消的客户关联到某个分组中
	 *  @param ids 客户ID集合
	 *  @param groupid 分组ID
	 */
	public void deleteGTWeixinuser(String ids, Integer groupid);
	
	/**
	 *  检测分组下是否有联系人
	 *  @param groupid  分组ID
	 */
	public boolean checkGroup(Integer groupid);
	
	/**
	 *  "发布页面"的联系人列表
	 *  @param page 分页对象
	 *  @param data 查询参数
	 */
	public List<TMbGroup> getGroupsToPublish(Page<TMbGroup> page, Map<String, Integer> data);
	
	/**
	 *  根据联系人分组ID找到分组下面的所有联系人openid
	 *  @param groupids 联系人分组ID集合
	 *  @return List<String> 联系人openid集合
	 */
	public List<String> getAllContactBygroupid(String groupids);
	
	/**
	 *  根据parentId获取下一级分组
	 *  @param parentId 上一级分组ID
	 *  @return List<TMbGroup> 分组集合数组
	 */
	public List<TMbGroup> getTreeByParentId(int parentId);
	
	/**
	 *  获取某个分组下的子分组ID集合
	 *  @param groupid 父分组ID
	 *  @return List<Integer> 子分组ID集合数组
	 */
	public List<Integer> getAllGroupIdByGroupId(Integer groupid);
	
	/**
	 *  获取所有分组信息
	 */
	public List<TMbGroup> getAllGroup();
	
	/**
	 *  判断某个分组下是否有子分组
	 *  @param groupid 检查的分组ID
	 *  @return boolean true 有/false 没有
	 */
	public boolean checkParent(Integer groupid);
	
	/**
	 *  根据ID查找分组
	 *  @param 分组ID
	 *  @return 分组对象
	 */
	public TMbGroup getGroup(Integer groupid);

	/**
	 *  获取所有一级的分公司信息
	 *  @param page 分页对象
	 *  @param data 查询条件
	 *  @return List<TMbGroup> 分组信息集合
	 */
	public List<TMbGroup> getAllOneGroup(Page<TMbGroup> page, Map<String, Object> data);
	
	/**
	 *  根据groupid获取分公司集合
	 *  @param groupid 分公司ID
	 *  @return Map<Integer, String> 分公司ID和名称集合
	 */
	public Map<Integer, String> getAllGroupByGroupId(Integer groupid);
	
	/**
	 *  判断该groupid分公司下是否有子分公司
	 *  @param groupid 分公司ID
	 *  @return true/false  有/没有
	 */
	public boolean isParent(Integer groupid);
	
	/**
	 *  根据指定groupid查询对应的所有微信粉丝
	 *  @param groupid 分公司ID
	 *  @return 微信粉丝信息集合
	 */
	public List<Object> getWeixinUsersByGroupid(Integer groupid);
	
	/**
	 *  根据groupid获取分公司的粉丝总数
	 *  @param groupid 分公司ID
	 *  @return 分公司名称和粉丝数量
	 */
	public List<Object> getWeixinCountByGroupid(Integer groupid);
	
	/**
	 *  根据groupid获取分公司信息
	 *  @param groupid 分店的ID
	 *  @return TMbGroup 分公司对象
	 */
	public TMbGroup getParentGroup(Integer groupid);
	
	/**
	 *  根据分公司名称获取分公司信息
	 *  @param groupname 分公司名称
	 *  @return TMbGroup 分公司对象
	 */
	public TMbGroup getBranchByGroupName(String groupname);
	
	/**
	 *  获取所有分公司信息(不包括分店)
	 *  @return List<TMbGroup>
	 */
	public List<TMbGroup> getTopBranch();
	
	/**
	 *  根据分公司ID获取所有门店
	 *  @param parentId
	 *  @retrun List<TMbGroup>
	 */
	public List<TMbGroup> getStoresByGroupid(Integer parentId);
}
