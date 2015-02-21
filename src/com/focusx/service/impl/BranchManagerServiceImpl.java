package com.focusx.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ecs.html.Option;
import org.apache.ecs.xhtml.map;
import org.apache.ecs.xhtml.option;
import org.apache.struts2.ServletActionContext;

import com.attilax.collection.listUtil;
import com.attilax.spri.SpringUtil;
import com.attilax.util.Func_4SingleObj;
import com.focusx.dao.BranchManagerDao;
import com.focusx.entity.TMbGroup;
import com.focusx.entity.TUserUsers;
import com.focusx.service.BranchManagerService;
import com.focusx.util.Constant;
import com.focusx.util.Page;

public class BranchManagerServiceImpl implements BranchManagerService {

	private BranchManagerDao branchManagerDao;

	// 用于分组列表页，列表显示
	public List<TMbGroup> getGroups(Page<TMbGroup> page,
			Map<String, Object> data) {
		List<TMbGroup> groups = branchManagerDao.getGroups(data);
		page.setResult(groups);

		int totalCount = branchManagerDao.getGroupCount(data).intValue();
		page.setTotalCount(totalCount);

		return groups;
	}

	// 新增分组
	public Number add(TMbGroup group) {
		return branchManagerDao.add(group);
	}

	// 删除分组
	public void delete(Integer groupid) {
		branchManagerDao.delete(groupid);
	}

	// 更新分组
	public void update(TMbGroup group) {
		branchManagerDao.update(group);
	}

	// 检查分组
	public String getGroupByGroupname(String groupname) {
		return branchManagerDao.getGroupByGroupname(groupname);
	}

	// //查询属于某个分组下的联系人
	// public List<TMbContact> getContactsByGroupid(Page<TMbContact>
	// pageContact,
	// Map<String, Object> data) {
	// List<TMbContact> contacts = branchManagerDao.getContactsByGroupid(data);
	// pageContact.setResult(contacts);
	//		
	// int totalCount =
	// branchManagerDao.getContactsCountByGroupid(data).intValue();
	// pageContact.setTotalCount(totalCount);
	//		
	// return contacts;
	// }

	public Number getContactsCountByGroupid(Map<String, Object> data) {
		branchManagerDao.getContactsCountByGroupid(data);
		return null;
	}

	// public void saveGroupToWeixinuser(TMbGroupToWeixinuser grouptouser) {
	// branchManagerDao.saveGroupToWeixinuser(grouptouser);
	// }

	public void deleteGTWeixinuser(String ids, Integer groupid) {
		branchManagerDao.deleteGTWeixinuser(ids, groupid);
	}

	// //查找不属于某个分组的联系人，用于choseContact.jsp页面
	// public List<TMbContact> getContactsOutGroupid(Page<TMbContact>
	// pageContact,
	// Map<String, Object> data) {
	// List<TMbContact> contacts = branchManagerDao.getContactsByGroupid(data);
	// pageContact.setResult(contacts);
	//		
	// int totalCount =
	// branchManagerDao.getContactsCountByGroupid(data).intValue();
	// pageContact.setTotalCount(totalCount);
	// return contacts;
	// }

	public boolean checkGroup(Integer groupid) {
		return branchManagerDao.checkGroup(groupid);
	}

	public List<TMbGroup> getGroupsToPublish(Page<TMbGroup> page,
			Map<String, Integer> data) {
		List<TMbGroup> groups = branchManagerDao.getGroupsToPublish(data);
		page.setResult(groups);

		int totalCount = branchManagerDao.getGroupsToPublishCount().intValue();
		page.setTotalCount(totalCount);
		return groups;
	}

	public List<String> getAllContactBygroupid(String groupids) {
		return branchManagerDao.getAllContactBygroupid(groupids);
	}

	public List<TMbGroup> getTreeByParentId(int parentId) {
		return branchManagerDao.getTreeByParentId(parentId);
	}

	public List<Integer> getAllGroupIdByGroupId(Integer groupid) {
		return branchManagerDao.getAllGroupIdByGroupId(groupid);
	}

	public List<TMbGroup> getAllGroup() {
		return branchManagerDao.getAllGroup();
	}

	public boolean checkParent(Integer groupid) {
		return branchManagerDao.checkParent(groupid);
	}

	public BranchManagerDao getBranchManagerDao() {
		return branchManagerDao;
	}

	public void setBranchManagerDao(BranchManagerDao branchManagerDao) {
		this.branchManagerDao = branchManagerDao;
	}

	// 获取分组
	public TMbGroup getGroup(Integer groupid) {
		return branchManagerDao.getGroup(groupid);
	}

	public List<TMbGroup> getAllOneGroup(Page<TMbGroup> page,
			Map<String, Object> data) {
		List<TMbGroup> groups = branchManagerDao.getAllOneGroup(data);
		page.setResult(groups);

		int totalCount = branchManagerDao.getAllOneGroupCount(data).intValue();
		page.setTotalCount(totalCount);

		return groups;
	}

	public Map<Integer, String> getAllGroupByGroupId(Integer groupid) {
		return branchManagerDao.getAllGroupByGroupId(groupid);
	}

	public boolean isParent(Integer groupid) {
		return branchManagerDao.isParent(groupid);
	}

	public List<Object> getWeixinUsersByGroupid(Integer groupid) {
		return branchManagerDao.getWeixinUsersByGroupid(groupid);
	}

	public List<Object> getWeixinCountByGroupid(Integer groupid) {
		return branchManagerDao.getWeixinCountByGroupid(groupid);
	}

	public TMbGroup getParentGroup(Integer groupid) {
		return branchManagerDao.getParentGroup(groupid);
	}

	public TMbGroup getBranchByGroupName(String groupname) {
		return branchManagerDao.getBranchByGroupName(groupname);
	}

	public List<TMbGroup> getTopBranch() {
		return branchManagerDao.getTopBranch();
	}

	public List<TMbGroup> getStoresByGroupid(Integer parentId) {
		return branchManagerDao.getStoresByGroupid(parentId);
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-5-14 下午02:56:19$
	 * 
	 * @param request
	 * @param string
	 */
	public static String list_sltFmt(final HttpServletRequest request,
			final String sltControlID) {
		// attilax 老哇的爪子 下午02:56:19 2014-5-14
		BranchManagerService c = (BranchManagerService) SpringUtil
				.getBean("branchManagerService");

		TUserUsers curLoginU = getLoginU(request);
		if (curLoginU.getDefaultGroup() == null) // suadm
		{

			List<TMbGroup> li = c.getAllGroup();
			String s = list_slktFmt(request, sltControlID, li);

			return s;
		} else {
			int groupid = curLoginU.getDefaultGroup();
			final TMbGroup grp = c.getGroup(groupid);
			// todox anonymose.. o5d
			List li = new ArrayList<TMbGroup>() {
				{
					add(grp);
				}
			};
			String s = list_slktFmt(request, sltControlID, li);

			return s;

			// return "";

		}

	}

	private static String list_slktFmt(final HttpServletRequest request,
			final String sltControlID, List<TMbGroup> li) {
		String s = listUtil.reduceO4d(li, "",
				new Fun_4reduce<TMbGroup, String>() {

					@Override
					public String $(TMbGroup o, String lastRetOBj) {
						// attilax 老哇的爪子 上午10:10:02 2014-5-15
						Option opt = new Option();
						opt.addAttribute("value", o.getGroupid());
						opt.setTagText(o.getGroupname());
						if (request.getParameter(sltControlID) != null)
							if (request.getParameter(sltControlID).equals(
									o.getGroupid().toString()))
								opt.addAttribute("selected", "selected");

						return lastRetOBj + opt.toString();

					}

				});
		return s;
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-5-15 上午11:51:59$
	 * 
	 * @param request
	 * @return
	 */
	private static TUserUsers getLoginU(HttpServletRequest request) {
		// attilax 老哇的爪子 上午11:51:59 2014-5-15
		TUserUsers user;
		// HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		Integer userID = (Integer) session.getAttribute(Constant.Login_UserID);// 从session里获取当前用户的userID

		user = (TUserUsers) session.getAttribute(session.getId());
		return user;
	}
}
