package com.focusx.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.focusx.dao.ListenDao;
import com.focusx.entity.TMbListen;

public class ListenDaoImpl implements ListenDao{

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
	
	public Integer save(TMbListen listen) {
		Session session = getSession();
		session.save(listen);
		session.flush();
		return listen.getListenId();
	}

	public List<TMbListen> getListens(Map<String, Object> data) {
		//分页条件
		int pageNumber = (Integer) data.get("pageNumber");
		int pageSize = (Integer) data.get("pageSize");
		
		//查询条件
		String listenName = (String) data.get("listenName");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select row_number() over(order by d1.listenID desc) as rowNum,* from t_mb_listen d1 where 1=1");
		
		if(listenName != null && !listenName.equals("")){
			sql.append(" and d1.listenName like '%"+listenName+"%'");
		}
		
		if(pageNumber!=-1 && pageSize!=-1){
			sql.append(") as listen where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));
		}else {
			sql.append(") as listen where rowNum between 1 and 10");
		}
		Query query = getSession().createSQLQuery(sql.toString()).addEntity(TMbListen.class);
		return query.list();
	}

	public Integer getListensCount(Map<String, Object> data) {
		//查询条件
		String listenName = (String) data.get("listenName");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from t_mb_listen where 1=1");
		if(listenName != null && !listenName.equals("")){
			sql.append(" and listenName like '%"+listenName+"%'");
		}
		Query query = getSession().createSQLQuery(sql.toString());
		return (Integer) query.uniqueResult();
	}

	public TMbListen getListenByListenId(Integer listenId) {
		String hql = "from TMbListen where listenId="+listenId;
		Query query = getSession().createQuery(hql);
		return (TMbListen) query.uniqueResult();
	}

	public boolean update(TMbListen listen) {
		try {
			getSession().update(listen);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(String listenIds) {
		try {
			String hql = "delete from TMbListen where listenId in("+listenIds+")";
			Query query = getSession().createQuery(hql);
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
