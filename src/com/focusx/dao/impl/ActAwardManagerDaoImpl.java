package com.focusx.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;

import com.focusx.dao.ActAwardManagerDao;
import com.focusx.entity.TMbActAward;

public class ActAwardManagerDaoImpl implements ActAwardManagerDao{
	
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

	public Integer addActAward(TMbActAward actaward) {
		Session session = getSession();
		session.save(actaward);
		session.flush();
		return actaward.getId();
	}

	public TMbActAward getActAwardById(Integer id) {
		String hql = "from TMbActAward where id="+id;
		Query query = getSession().createQuery(hql);
		return (TMbActAward) query.uniqueResult();
	}

	public List<TMbActAward> getActAwards(Map<String, Object> data) {
		//分页条件
		Integer pageNumber = (Integer) data.get("pageNumber");
		Integer pageSize = (Integer) data.get("pageSize");
		
		String awardName = (String) data.get("awardName");
		Integer type = (Integer) data.get("type");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select id,awardName,type,awardCount,actName from (select row_number() over(order by d1.id desc) as rowNum, " +
				"(select actName from t_mb_activity d2 where d2.id=d1.activityId)as actName,d1.awardName,d1.type,d1.awardCount,d1.id from t_mb_actaward d1 where 1=1");
		
		if(awardName != null && !awardName.equals("")){
			sql.append(" and awardName like '%"+awardName+"%'");
		}
		if(type != null && type > 0){
			sql.append(" and type="+type);
		}
		
		//加入分页
		if(pageNumber!=-1 && pageSize!=-1){
			sql.append(") as actaward where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));
		}else {
			sql.append(") as actaward where rowNum between 1 and 10");
		}
		Query query = getSession().createSQLQuery(sql.toString()).addScalar("id", StandardBasicTypes.INTEGER)
				.addScalar("awardName", StandardBasicTypes.STRING).addScalar("type", StandardBasicTypes.INTEGER)
				.addScalar("awardCount", StandardBasicTypes.INTEGER).addScalar("actName", StandardBasicTypes.STRING);		
		List temp = query.list();
		List<TMbActAward> list = new ArrayList<TMbActAward>();
		if(temp != null && temp.size() > 0){
			for(int i =0; i < temp.size(); i++){
				Object[] object = (Object[])temp.get(i);
				TMbActAward actaward = new TMbActAward();
				actaward.setId((Integer)object[0]);
				actaward.setAwardName((String)object[1]);
				actaward.setType((Integer)object[2]);
				actaward.setAwardCount((Integer)object[3]);
				actaward.setActName((String)object[4]);
				list.add(actaward);
			}
		}
		return list;
	}

	public Integer getActAwardsCount(Map<String, Object> data) {
		String awardName = (String) data.get("awardName");
		Integer type = (Integer) data.get("type");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from t_mb_actaward where 1=1");
		if(awardName != null && !awardName.equals("")){
			sql.append(" and awardName like '%"+awardName+"%'");
		}
		if(type != null && type > 0){
			sql.append(" and type="+type);
		}
		Query query = getSession().createSQLQuery(sql.toString());
		return (Integer) query.uniqueResult();
	}

	public void saveActAward(TMbActAward actaward) {
		getSession().update(actaward);
	}

	public List<TMbActAward> getActAwardsByActivityId(Integer activityId) {
		String hql = "from TMbActAward where activityId="+activityId;
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	public List<TMbActAward> getActAwardsOutAllActivity(Map<String, Object> data) {
		//分页条件
		Integer pageNumber = (Integer) data.get("pageNumber");
		Integer pageSize = (Integer) data.get("pageSize");
		
		String awardName = (String) data.get("awardName");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select row_number() over(order by d1.id desc) as rowNum, d1.* from t_mb_actaward d1 where 1=1");
		if(awardName != null && !awardName.equals("")){
			sql.append(" and d1.awardName like '%"+awardName+"%'");
		}
		sql.append(" and d1.activityId=0");
		//加入分页
		if(pageNumber!=-1 && pageSize!=-1){
			sql.append(") as actaward where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));
		}else {
			sql.append(") as actaward where rowNum between 1 and 10");
		}
		Query query = getSession().createSQLQuery(sql.toString()).addEntity(TMbActAward.class);
		return query.list();
	}

	public Integer getActAwardsOutAllActivityCount(Map<String, Object> data) {
		String awardName = (String) data.get("awardName");
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from t_mb_actaward where activityId=0");
		if(awardName != null && !awardName.equals("")){
			sql.append(" and awardName like '%"+awardName+"%'");
		}
		Query query = getSession().createSQLQuery(sql.toString());
		return (Integer) query.uniqueResult();
	}

	public void updateActAwardByActivityId(String actawards, Integer activityId) {
		String sql = "update TMbActAward set activityId="+activityId+" where id in("+actawards+")";
		Query query = getSession().createQuery(sql);
		query.executeUpdate();
	}

	public void deleteActAwardByActivityId(Integer actawardId) {
		String sql = "update TMbActAward set activityId=0 where id="+actawardId;
		Query query = getSession().createQuery(sql);
		query.executeUpdate();
	}

	public void deleteActAwardById(Integer id) {
		String sql = "delete from TMbActAward where id="+id;
		Query query = getSession().createQuery(sql);
		query.executeUpdate();
	}
	

}