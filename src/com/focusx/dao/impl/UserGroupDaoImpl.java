package com.focusx.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.focusx.dao.UserGroupDao;
import com.focusx.entity.TUserGroup;
import com.focusx.entity.TUserUserToGroup;
import com.focusx.entity.TUserUsers;

public class UserGroupDaoImpl implements UserGroupDao{

	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public List<TUserGroup> getGroups(Map<String, Object> data) {
		Session session = getSession();
		
		//获取分页条件
		int pageNumber = (Integer)data.get("pageNumber");//第几页
	    int pageSize = (Integer)data.get("pageSize");//每页记录数
	    
	    //获取查询条件
	    String name = (String)data.get("name");//分组名称
		String description = (String)data.get("description");//描述
	    
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select row_number() over(order by ID desc) as rowNum,* from t_user_group where 1=1 ");
		
		//加入查询条件
		if(!"".equals(name) && name!=null){
			sql.append(" and name like '%"+name+"%'");
		}
		if(!"".equals(description) && description!=null){
			sql.append(" and description like '%"+description+"%'");
		}
		//加入分页
		if(pageNumber!=-1 && pageSize!=-1){
			sql.append(") as groups where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));
		}else{
			sql.append(") as groups where rowNum between 1 and 10");
		}
		Query query = session.createSQLQuery(sql.toString()).addEntity(TUserGroup.class);
		return query.list();
	}

	public Number getGroupCount(Map<String, Object> data) {
		Session session = getSession();
	    
	    //获取查询条件
	    String name = (String)data.get("name");
	    String description = (String)data.get("description");//描述
	    
		StringBuffer sql = new StringBuffer();
		sql.append("select Count(ID) from t_user_group where 1=1 ");
		
		//加入查询条件
		if(!"".equals(name) && name!=null){
			sql.append(" and name like '%"+name+"%'");
		}
		if(!"".equals(description) && description!=null){
			sql.append(" and description like '%"+description+"%'");
		}
		
		Query query = session.createSQLQuery(sql.toString());		
		return (Number)query.uniqueResult();
	}

	public String getGroupByName(String name) {
		String hql = "from TUserGroup where name = '"+name+"'";
		Query query = getSession().createQuery(hql);
		if((TUserGroup) query.uniqueResult() == null){
			return "true";
		}else{
			return "false";
		}
	}
	
	public Number save(TUserGroup userGroup){
		Session session = getSession();
		userGroup.setGatewayId(0);
		session.save(userGroup);
		session.flush();
		return userGroup.getId();
	}
	
	public TUserGroup getGroupById(Integer id){
		String hql = "from TUserGroup where id="+id;
		Query query = getSession().createQuery(hql);
		return (TUserGroup)query.uniqueResult();
	}
	
	public boolean checkGroup(Integer id){
		String hql = "from TUserGroup where id=" + id;
		Query query = getSession().createQuery(hql);
		List list = query.list();
		if(list.size() == 0){
			return true;
		}else{
			return false;
		}
	}

	public void delete(Integer id) {
		String hql = "delete from TUserGroup where id= " + id;
		getSession().createQuery(hql).executeUpdate();
	}
	 
	public void update(TUserGroup usergroup){
		getSession().update(usergroup);
	}
	
	@SuppressWarnings("unchecked")
	public List<TUserUsers> getUsersByGroupid(Map<String, Object> data) {
		//获取分页条件
		int pageNumber = (Integer)data.get("pageNumber");//第几页
	    int pageSize = (Integer)data.get("pageSize");//每页记录数
	    
	    //获取查询条件
	    Integer groupid = (Integer)data.get("groupid");
		String name = (String)data.get("name");
		String out = (String)data.get("out");
	    
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select row_number() over(order by ID desc) as rowNum,* from t_user_users where 1=1");
		if(out != null && !"".equals(out) || out.equals("out")){
			if(!"".equals(name) && name != null){
				sql.append(" and name like '%"+name+"%' and ID not in ( select g.userID from t_user_usertogroup g where g.groupID="+groupid+")");
			}else {
				sql.append(" and ID not in ( select g.userID from t_user_usertogroup g where g.groupID="+groupid+")");
			}
		}else {
			sql.append(" and ID in ( select g.userID from t_user_usertogroup g where g.groupID="+groupid+")");
		}
		//加入分页
		if(pageNumber!=-1 && pageSize!=-1){
			sql.append(") as users where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));
		}else{
			sql.append(")as users where rowNum between 1 and 10");
		}
		Query query = getSession().createSQLQuery(sql.toString()).addEntity(TUserUsers.class);
		return query.list();
	}
	
	/**
	 *  查询在某些条件下的用户总和
	 */
	public Number getUsersGroupCount(Map<String, Object> data) {    
	    //获取查询条件
	    Integer groupid = (Integer)data.get("groupid");
		String name = (String)data.get("name");
		String out = (String)data.get("out");
	    
		String sql = "";
		if(out != null && !"".equals(out) || out.equals("out")){
			if(!"".equals(name) && name != null){
				sql = "select count(u.ID) from t_user_users u where  u.name like '%"+name+"%' and u.ID not in ( select g.UserID from t_user_usertogroup g where g.GroupID="+groupid+")";
			}else{
				sql = "select count(u.ID) from t_user_users u where u.ID not in ( select g.UserID from t_user_usertogroup g where g.GroupID="+groupid+")";
			}
		}else{
			sql = "select count(u.ID) from t_user_users u where u.ID in ( select g.UserID from t_user_usertogroup g where g.GroupID="+groupid+")";
		}
		
		Query query = getSession().createSQLQuery(sql);
		
		return (Number)query.uniqueResult();
	}

	public void saveGroupToUser(TUserUserToGroup usergroup) {
//		Session session = getSession();
//		String hql = "from TUserUserToGroup where userID="+usergroup.getUserID()+" and groupID="+usergroup.getGroupID();
//		Query query = session.createQuery(hql);
//		if((TUserUserToGroup)query.uniqueResult() == null){
//			usergroup.setIfShow(1);//?
//			session.save(usergroup);
//			session.flush();
//		}
	}

	public void deleteGroupToUser(String ids, Integer id) {
		Session session = getSession();
		String hql = "delete from TUserUserToGroup where groupID="+id+" and userID in ("+ids+")";
		session.createQuery(hql).executeUpdate();
	}
}
