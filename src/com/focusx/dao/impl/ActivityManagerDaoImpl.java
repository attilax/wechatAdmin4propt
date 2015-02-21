package com.focusx.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import com.focusx.dao.ActivityManagerDao;
import com.focusx.entity.TMbActivity;

public class ActivityManagerDaoImpl implements ActivityManagerDao {
	
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

	public List<TMbActivity> getActivitys(Map<String, Object> data) {
		//分页条件
		int pageNumber = (Integer) data.get("pageNumber");
		int pageSize = (Integer) data.get("pageSize");
		
		//查询条件
		String actName = (String) data.get("actName");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select row_number() over(order by d1.id desc) as rowNum, " +
				"d1.* from t_mb_activity d1 where 1=1");
		
		if(actName != null && !actName.equals("")){
			sql.append(" and d1.actName like '%"+actName+"%'");
		}
		
		if(pageNumber!=-1 && pageSize!=-1){
			sql.append(") as activity where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));
		}else {
			sql.append(") as activity where rowNum between 1 and 10");
		}
		Query query = getSession().createSQLQuery(sql.toString()).addEntity(TMbActivity.class);
		return query.list();
	}

	public Integer getActivitysCount(Map<String, Object> data) {
		//查询条件
		String actName = (String) data.get("actName");
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from t_mb_activity d1 where 1=1");
		
		if(actName != null && !actName.equals("")){
			sql.append(" and d1.actName like '%"+actName+"%'");
		}
		Query query = getSession().createSQLQuery(sql.toString());
		return (Integer) query.uniqueResult();
	}

	public Integer addActivity(TMbActivity activity) {
		Session session = getSession();
		session.save(activity);
		session.flush();
		return activity.getId();
	}

	public TMbActivity getActivityById(Integer id) {
		String hql = "from TMbActivity where id="+id;
		Query query = getSession().createQuery(hql);
		return (TMbActivity) query.uniqueResult();
	}

	public void saveActivity(TMbActivity activity) {
		getSession().update(activity);
	}

	public List<TMbActivity> getAllActivityNotFinish(String date) {
		String sql = "select id, actName from t_mb_activity where endTime>='"+date+"'";
		Query query = getSession().createSQLQuery(sql).addScalar("id", StandardBasicTypes.INTEGER)
				.addScalar("actName", StandardBasicTypes.STRING).setResultTransformer(Transformers.aliasToBean(TMbActivity.class));
		return query.list();
	}

	public void delete(Integer id) {
		String sql ="delete from t_mb_activity where id="+id;
		getSession().createSQLQuery(sql).executeUpdate();
	}

	public List<TMbActivity> getAllActivity(String date) {
		String sql = "select id, actName from t_mb_activity where beginTime <= '"+date+"' order by id asc";
		Query query = getSession().createSQLQuery(sql).addScalar("id", StandardBasicTypes.INTEGER)
				.addScalar("actName", StandardBasicTypes.STRING).setResultTransformer(Transformers.aliasToBean(TMbActivity.class));
		return query.list();
	}

	@Override
	public boolean updateActivity(TMbActivity activity) {
		boolean result = false;
		try{
		getSession().update(activity);
		result = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

}
