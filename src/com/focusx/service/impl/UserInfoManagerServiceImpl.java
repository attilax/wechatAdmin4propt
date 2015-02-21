package com.focusx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.attilax.spri.SpringUtil;
import com.focusx.dao.UserInfoManagerDao;
import com.focusx.dao.impl.springUtil4backend;
import com.focusx.entity.TUserUsers;
import com.focusx.service.UserInfoManagerService;
import com.focusx.util.Constant;
import com.focusx.util.Page;

public class UserInfoManagerServiceImpl implements UserInfoManagerService {
	
	public static void main(String[] args) {
		UserInfoManagerService u=	SpringUtil.getBean(UserInfoManagerService.class);
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", "admin");
		data.put("password", "ICy5YqxZB1uWSwcVLSNLcA==");
		//from TUserUsers where name='admin' and PassWord='ICy5YqxZB1uWSwcVLSNLcA=='
		TUserUsers usr=u.login(data);
		System.out.println(usr);
		System.out.println("--");
	}
	
	private UserInfoManagerDao userInfoManagerDao;
	
	public UserInfoManagerDao getUserInfoManagerDao() {
		return userInfoManagerDao;
	}

	public void setUserInfoManagerDao(UserInfoManagerDao userInfoManagerDao) {
		this.userInfoManagerDao = userInfoManagerDao;
	}
	
	public boolean deleteRoleFromUser(int roleId, String userIdsList) {
		boolean result = true;
		
		if(userIdsList.length()>0){
			String[] userIds=userIdsList.split(",");
			result = userInfoManagerDao.deleteRoleFromUser(roleId, userIds);
		}
		
		return result;
	}

	public boolean insertUserIntoRole(int roleId, String userIdsList) {
		boolean result = true;
		
		if(userIdsList.length()>0){
			String[] userIds=userIdsList.split(",");
			result = userInfoManagerDao.insertUserIntoRole(roleId, userIds);
		}
		
		return result;
	}
	
	public List<TUserUsers> getPagedUsers(Page page,int[] pageParams) {
		List<TUserUsers> lst = userInfoManagerDao.getPagedUsers(pageParams[0],pageParams[1]);
		//设置page中的对象
		page.setResult(lst);
		int totalCount = userInfoManagerDao.getUserTotalCount();
		//设置总记录数
		page.setTotalCount(totalCount);
		return lst;
	}
	
	public List<TUserUsers> getSearchUsers(String condition,Page page,int[] pageParams){
		List<TUserUsers> lst = userInfoManagerDao.getSearchUsers(condition, pageParams[0],pageParams[1]);
		//设置page中的对象
		page.setResult(lst);
		int totalCount = userInfoManagerDao.getSearchUsersCount(condition);
		//设置总记录数
		page.setTotalCount(totalCount);
		return lst;
	}

	public TUserUsers getUserById(int userId) {
		TUserUsers user = userInfoManagerDao.getUserById(userId);
		return user;
	}
	
	public TUserUsers getUserByWorkNo(String workNo){
		TUserUsers user = userInfoManagerDao.getUserByWorkNo(workNo);
		return user;
	}

	public boolean updateUserInfo(TUserUsers user) {
		return userInfoManagerDao.updateUserInfo(user);
	}

	public Number insertUserInfo(TUserUsers user) {
		return userInfoManagerDao.insertUserInfo(user);
	}
	
	public boolean deleteUsers(String deleteUsersList){
		boolean checked = true;
		if(deleteUsersList!=null && deleteUsersList.length()>0){
			String[] ids = deleteUsersList.split(",");
			checked = userInfoManagerDao.deleteUsersByIds(ids);
			
		}else{
			checked  = false;
		}
		return checked;
	}

	public TUserUsers login(Map<String, String> data){
		return userInfoManagerDao.login(data);
	}

	public TUserUsers getUserByName(String name) {
		return userInfoManagerDao.getUserByName(name);
	}
}
