package com.focusx.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.focusx.entity.TMbWeixinUser;


public interface WeixinUserManagerDao {
	public SessionFactory getSessionFactory();
	public Session getSession();
	/**
	 *  根据条件查询微信粉丝信息
	 *  @param data 查询条件
	 *  @return List<TMbWeixinuser> 微信粉丝信息集合
	 */
	public List<TMbWeixinUser> getWeixinusersByGroupid(Map<String, Object> data);

	/**
	 *  统计查询微信粉丝信息结果的数量
	 *  @param data 查询条件
	 *  @return Number 微信粉丝信息数量
	 */
	public Number getWeixinusersCountByGroupid(Map<String, Object> data);
	
	/**
	 *  根据条件查询微信粉丝信息，用于微信粉丝列表页
	 *  @param data 查询条件
	 *  @return List<TMbWeixinuser> 微信粉丝信息集合
	 */
	public List<TMbWeixinUser> getWeixinUsers(Map<String, Object> data);

	/**
	 *  统计查询微信粉丝信息结果的数量，用于微信粉丝列表页
	 *  @param data 查询条件
	 *  @return Number 微信粉丝信息数量
	 */
	public Number getWeixinUsersCount(Map<String, Object> data);
	
	/**
	 *  根据ID获取对应微信粉丝信息
	 *  @param userId 微信粉丝对象ID
	 *  @return TMbWeixinUser 微信粉丝对象
	 */
	public TMbWeixinUser getWeixinUserByUserId(Integer userId);
	
	/**
	 *  统计没有属于任何一个分公司的粉丝总数
	 *  @return 粉丝总数
	 */
	public Integer getWeixinUserWithNullBranchCount();
	
	/**
	 *  根据time获取对应数量的粉丝信息
	 *  @param time 循环次数
	 *  @return List<TMbWeixinUser> 粉丝信息集合
	 */
	public List<TMbWeixinUser> getWeixinUserByTime(Integer time);
	
	/**
	 *  更改粉丝的所属分公司
	 *  @param data 分公司对象ID、粉丝对象ID 集合
	 */
	public void updateToGroupId(Map<Integer, Integer> data);
	
	/**
	 *   获取未能按照程序分组或今天内还没分组的粉丝信息
	 *   @param data 查询条件
	 *   @return  List<TMbWeixinUser>
	 */
	public List<TMbWeixinUser> getNullGroupList(Map<String, Object> data);
	
	/**
	 *   统计未能按照程序分组或今天内还没分组的粉丝数量
	 *   @param data 查询条件
	 *   @return 粉丝数量
	 */
	public Integer getNullGroupListCount(Map<String, Object> data);
	
	/**
	 *  更改粉丝所属分公司
	 *  @param groupid 分公司ID
	 *  @param userIds 粉丝对象ID集合字符串
	 */
	public void setGroupToWeixinUser(Integer groupid, String userIds);
	
	/**
	 *  根据nickname获取粉丝信息
	 *  @param nickname 昵称
	 *  @return List<TMbWeixinUser> 粉丝对象数组
	 */
	public List<TMbWeixinUser> getWeixinUserByNickName(String nickname);
	
	/**
	 *  获取粉丝信息数组
	 *  @param data 查询条件
	 *  @return List<TMbWeixinUser> 粉丝对象数组
	 */
	public List<TMbWeixinUser> getWeixinUsersByNickName(Map<String, Object> data);
	
	/** 
	 *  统计获取到的粉丝信息数量
	 *  @param data 查询条件
	 *  @return 数量
	 */
	public Integer getWeixinUsersByNickNameCount(Map<String, Object> data);
	
	/**
	 *  判断数据库是否存在该openid的粉丝信息
	 *  @param openid
	 */
	public boolean checkWeixinUser(String openid);
	
	/**
	 *  保存新增的粉丝信息
	 */
	public void save(TMbWeixinUser user);
	
}
