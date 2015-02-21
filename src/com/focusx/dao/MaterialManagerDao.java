package com.focusx.dao;

import java.util.List;
import java.util.Map;

import com.focusx.entity.TMbNews;

public interface MaterialManagerDao {
	
	/**
	 *  获取图文对象列表
	 *  @param data 查询条件
	 *  @return List<TMbNews> 图文对象数组
	 */
	public List<TMbNews> getMaterials(Map<String, Object> data);
	
	/**
	 *  获取图文对象数量
	 *  @param data 查询条件
	 *  @return Number 数量
	 */
	public Number getMaterialsCount(Map<String, Object> data);
	
	/**
	 *  保存新增图文信息
	 *  @param news 图文对象
	 *  @return 图文对象ID
	 */
	public Integer saveMaterial(TMbNews news);

	/**
	 *  根据主键ID获取图文信息
	 *  @param id 主键ID
	 *  @return 图文对象
	 */
	public TMbNews getTMbNewsById(Integer id);
	
	/**
	 *  保存更改后的图文信息
	 *  @param news 图文对象
	 *  @return 图文对象ID
	 */
	public Integer update(TMbNews news);
	
	/**
	 *  删除图文信息
	 *  @param id 图文对象ID
	 */
	public void delete(Integer id);
	
	/**
	 *  删除图文信息，非物理删除
	 *  @pramati id 图文对象ID
	 */
	public void deleteMaterialByState(Integer id);
	
	/**
	 *  判断某个分公司、某个类型的图文信息所属类型是否有启用的图文信息
	 *  @param groupid 分公司对象ID
	 *  @param newsType 图文对象类型
	 */
	public boolean checkNewsTypeCount(Integer groupid, Integer newsType);
	
	/**
	 *  改变图文信息启用或暂停
	 *  @param id 图文对象ID
	 *  @param type 修改类型  0 启用 1 暂停
	 */
	public void updateFlag(Integer id, Integer type);
	
	/**
	 *  获取多图文
	 *  @param newsId 多图文第一个图文的ID
	 *  @return 多个图文对象
	 */
	public List<TMbNews> getMoreMaterial(Integer newsId);
	
	/**
	 *  保存编辑后的多图文信息
	 *  @param news 图文对象
	 *  @return true/false
	 */
	public boolean saveMoreMaterial(TMbNews news);
	
	/**
	 *  更改图文类型
	 *  @param newsType 图文类型值
	 *  @param groupid  分公司ID
	 *  @param id       图文对象ID
	 *  @param keyword     关键字
	 *  @param activityId  活动ID
	 *  @return true/false
	 */
	public boolean updateNewsType(Integer newsType, Integer groupid, Integer id, String keyword, Integer activityId);

	/**
	 *  判断数据库是否已存在该关键字对应的图文
	 *  @param keyword 关键字
	 *  @return boolean 是/否
	 */
	public boolean checkKeyword(String keyword);
	
	/**
	 *  判断数据库是否已存在该活动对应的图片
	 *  @param newsType 图文类型
	 *  @param activityId 活动对象ID
	 *  @return boolean 是/否
	 */
	public boolean checkActivityNewsType(Integer newsType, Integer activityId);
	
	/**
	 *  根据分公司ID获取对应的所有图文
	 *  @param groupid  分公司ID
	 *  @return List<TMbNews>
	 */
	public List<TMbNews> getMaterialByGroupid(Integer groupid);
	
	/**
	 *  根据分公司ID获取所有所属的图文，用于缓存更新
	 *  @param groupid  分公司ID
	 *  @return List<TMbNews>
	 */
	public List<TMbNews> getMaterialsByGroupid(Integer groupid);

}

