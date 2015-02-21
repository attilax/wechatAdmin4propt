package com.focusx.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.focusx.dao.TagDao;
import com.focusx.entity.TMbTag;

public class TagDaoImpl implements TagDao{

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
	
	public List<TMbTag> getTagByGroupid(Integer groupId) {
		String hql = "from TMbTag where groupid="+groupId;
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	
	
}
