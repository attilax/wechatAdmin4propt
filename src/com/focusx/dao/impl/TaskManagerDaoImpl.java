package com.focusx.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;

import com.focusx.dao.TaskManagerDao;
import com.focusx.entity.TMbTask;

public class TaskManagerDaoImpl implements TaskManagerDao{

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
	
	public List<TMbTask> getTaskForMediaUrl(Map<String, Object> data) {
		//分页条件
		Integer pageNumber = (Integer) data.get("pageNumber");
		Integer pageSize = (Integer) data.get("pageSize");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select id, openid, mediaUrl,nickname,isLink from (select row_number() over(order by d1.publishtime desc) as rowNum," +
				"d1.*,(select top 1 d2.nickname from t_mb_weixinuser d2 where d2.openid=d1.openid)as nickname,(select COUNT(*) from t_mb_awardweixin d2 where d2.openId=d1.openid)as isLink from t_mb_task d1 where mediaUrl is not null");
		//加入分页
		if(pageNumber!=-1 && pageSize!=-1){
			sql.append(") as task where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));
		}else {
			sql.append(") as task where rowNum between 1 and 10");
		}
		Query query = getSession().createSQLQuery(sql.toString()).addScalar("id", StandardBasicTypes.INTEGER)
				.addScalar("openid", StandardBasicTypes.STRING).addScalar("mediaUrl", StandardBasicTypes.STRING)
				.addScalar("nickname", StandardBasicTypes.STRING).addScalar("isLink", StandardBasicTypes.INTEGER);
		List temp = query.list();
		List<TMbTask> tasks = new ArrayList<TMbTask>();
		if(temp != null && temp.size() > 0){
			for(int i =0; i < temp.size(); i++){
				Object[] object = (Object[])temp.get(i);
				TMbTask task = new TMbTask();
				task.setId((Integer)object[0]);
				task.setOpenid((String)object[1]);
				task.setMediaUrl((String)object[2]);
				task.setNickname((String)object[3]);
				task.setIsLink((Integer)object[4]);
				tasks.add(task);
			}
		}
		return tasks;
	}

	public Integer getTaskForMediaUrlCount() {
		String hql = "select count(*) from t_mb_task where mediaUrl is not null";
		Query query = getSession().createSQLQuery(hql);
		return (Integer) query.uniqueResult();
	}

	public void updateTaskForMediaUrl(Integer id) {
		String hql = "update t_mb_task set mediaUrl=null where id="+id;
		Query query = getSession().createSQLQuery(hql);
		query.executeUpdate();
	}

	public List<TMbTask> getTaskForListen(Map<String, Object> data) {
		//分页条件
		Integer pageNumber = (Integer) data.get("pageNumber");
		Integer pageSize = (Integer) data.get("pageSize");
		
		String listenKey = (String) data.get("listenKey");
		if(listenKey != null && !listenKey.equals("")){
			StringBuffer sql = new StringBuffer();
			sql.append("select msgContent,CONVERT(varchar, publishtime, 120 ) as publishtime,nickname from (select row_number() over(order by id desc) as rowNum,d1.msgContent,d1.publishtime," +
					"(select top 1 d2.nickname from t_mb_weixinuser d2 where d1.openid=d2.openid)as nickname from t_mb_task d1 where msgContent like '%"+listenKey+"%'");
			//加入分页
			if(pageNumber!=-1 && pageSize!=-1){
				sql.append(") as task where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));
			}else {
				sql.append(") as task where rowNum between 1 and 10");
			}
			Query query = getSession().createSQLQuery(sql.toString()).addScalar("msgContent", StandardBasicTypes.STRING)
					.addScalar("publishtime", StandardBasicTypes.STRING).addScalar("nickname", StandardBasicTypes.STRING);
			List temp = query.list();
			List<TMbTask> tasks = new ArrayList<TMbTask>();
			
			if(temp != null && temp.size() > 0){
				for(int i =0; i < temp.size(); i++){
					Object[] object = (Object[])temp.get(i);
					TMbTask task = new TMbTask();
					task.setMsgContent((String)object[0]);
					task.setPublishTimeString((String)object[1]);
					task.setNickname((String)object[2]);
					tasks.add(task);
				}
			}
			return tasks;
		}
		return null;
	}

	public Integer getTaskForListenCount(Map<String, Object> data) {
		String listenKey = (String) data.get("listenKey");
		if(listenKey != null && !listenKey.equals("")){
			String sql = "select count(*) from t_mb_task where msgContent like '%"+listenKey+"%'";
			Query query = getSession().createSQLQuery(sql);
			return (Integer) query.uniqueResult();
		}
		return 0;
	}

}
