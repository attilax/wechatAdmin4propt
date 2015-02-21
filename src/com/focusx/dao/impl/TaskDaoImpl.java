package com.focusx.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.attilax.core;
import com.focusx.dao.TaskDao;
import com.focusx.entity.TMbTask;

public class TaskDaoImpl implements TaskDao{
	
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

	public List<TMbTask> getTasksByData(Map<String, Object> data) {
		//取出查询条件
		String contactname = (String)data.get("contactname");//名称
		String startTime = (String)data.get("startTime");//开始时间
		String endTime = (String)data.get("endTime");//结束时间
		//Integer source = (Integer)data.get("source");//来源
		String content = (String)data.get("content");//内容
		//Integer userID = (Integer)data.get("userID");//用户ID
		int pageNumber = (Integer)data.get("pageNumber");//第几页
	    int pageSize = (Integer)data.get("pageSize");//每页记录数
	    int state = (Integer) data.get("state");//消息状态
	    int groupid = (Integer)data.get("groupid");//所属分公司
		
		StringBuffer start = new StringBuffer();
		StringBuffer end = new StringBuffer();
		//List<TMbTask> list = new ArrayList<TMbTask>();
		List<Object[]>  list = new ArrayList<Object[]>();
		StringBuffer sql = new StringBuffer();
		if(groupid > 0){
			sql.append("select * from "+
			           "(select row_number() over(order by publishtime desc) as rowNum, "+
					   "t.* ,c.nickname,c.headimgurl  from t_mb_task t inner join t_mb_weixinuser c on t.openid=c.openid inner join t_mb_group d on c.groupid = d.groupid where (d.groupid="+groupid+" or d.parentId = "+groupid+")  and t.messagetype=0");
		}else {
			sql.append("select * from "+
			           "(select row_number() over(order by publishtime desc) as rowNum, "+
					   "t.* ,c.nickname,c.headimgurl  from t_mb_task t left join t_mb_weixinuser c on t.openid=c.openid left join t_mb_group d on c.groupid = d.groupid where t.messagetype=0");
		}

		if(!"".equals(contactname) && contactname != null){
			sql.append(" and c.nickname like '%"+contactname+"%'");
		}
		/*if(!source.equals("") && source != null && source != 0){
			sql.append(" and t.source="+source);
		}*/
		if(!content.equals("") && content != null){
			sql.append(" and t.msgContent like '%"+content+"%'");
		}
		if(!startTime.equals("") && startTime != null || !endTime.equals("") && endTime != null){
			start.append(startTime+" 00:00:00");
			end.append(endTime+" 23:59:59");
			sql.append(" and t.publishtime between '"+start.toString()+"' and '"+end.toString()+"'");
		}
		if(state == 0 || state == 1){
			sql.append(" and t.state="+state);
		}
		if(pageNumber!=-1 && pageSize!=-1 ){
			sql.append(") as task where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));
		}else {
			sql.append(") as task where rowNum between 1 and 10");
		}
		core.log("--o5h1: qeury wechat cuservice list sql:"+sql.toString());
		Query query = getSession().createSQLQuery(sql.toString());//.addEntity(TMbTask.class);
		list = query.list();
		
		List<TMbTask> taskList= new ArrayList<TMbTask>();
		
		for(Object[] obj:list){
			
			if(obj!=null){
				TMbTask nTask = new TMbTask();
				
				if(obj[1] != null && !"".equals(obj[1])){
					
					nTask.setId(Integer.parseInt(obj[1].toString()));
				}
				
				if(obj[2] != null && !"".equals(obj[2])){
					nTask.setOpenid(obj[2].toString());
				}
				
				if(obj[3] != null && !"".equals(obj[3])){
					nTask.setMsgContent(obj[3].toString());
				}
				
				if(obj[4] != null && !"".equals(obj[4])){
					nTask.setMediaUrl(obj[4].toString());
				}
				
				if(obj[5] != null && !"".equals(obj[5])){
					nTask.setState(Integer.parseInt(obj[5].toString()));
				}
				
				if(obj[6] != null && !"".equals(obj[6])){
					nTask.setPublishTime((Date)obj[6]);
				}
				
				if(obj[7] != null && !"".equals(obj[7])){
					nTask.setUserId(Integer.parseInt(obj[7].toString()));
				}
				
				if(obj[8] != null && !"".equals(obj[8])){
					nTask.setMsgType(Integer.parseInt(obj[8].toString()));
				}
				
				if(obj[10] != null && !"".equals(obj[10])){
					nTask.setMessagetype(Integer.parseInt(obj[10].toString()));
				}
				
				if(obj[11] != null && !"".equals(obj[11])){
					nTask.setAnswerTime((Date)obj[11]);
				}
				
				if(obj[12] != null && !"".equals(obj[12])){
					nTask.setNickname(obj[12].toString());
				}
				
				if(obj[13] != null && !"".equals(obj[13])){
					nTask.setHeadimgurl(obj[13].toString());
				}
				
				taskList.add(nTask);
			}
			
		}
		
		return taskList;
	}

	public boolean updateToStateById(String id,String openid,String replyTxt,int userId) {
		try {
			String hql = "update  TMbTask set state =1,userId=?,answerTime = ? where id="+Integer.parseInt(id);
			
			Session sess = getSession();
			
			Query query = sess.createQuery(hql);
			query.setParameter(0, userId);
			query.setTimestamp(1, new Date());
			query.executeUpdate();
			
			
			TMbTask replyTask = new TMbTask();
			replyTask.setOpenid(openid);
			replyTask.setMsgContent(replyTxt);
			replyTask.setAnswerTime(new Date());
			replyTask.setUserId(userId);
			replyTask.setState(TMbTask.STATE_FEEDBACK); //回复
			replyTask.setMessagetype(1);
			replyTask.setPublishTime(new Date());
			
			sess.save(replyTask);
			
			
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<TMbTask> getTasksByOpenId(Map<String, Object> data) {
		String openid = (String)data.get("openid");
		int pageNumber = (Integer)data.get("pageNumber");//第几页
	    int pageSize = (Integer)data.get("pageSize");//每页记录数
		
	    StringBuffer sql = new StringBuffer();
		sql.append("select * from (select row_number() over(order by answerTime desc) as rowNum,"+
				   "t.*,c.nickname,u.Name as clientName  from t_mb_task t left join t_mb_weixinuser c on t.openid=c.openid left join t_user_users u on t.userId = u.ID  where " +
				"t.openid='"+openid+"' and (t.state = 1 or t.state = 2)) as task");
		if(pageNumber!=-1 && pageSize!=-1 ){
			sql.append(" where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));
		}else {
			sql.append(" where rowNum between 1 and 20");
		}
		Query query = getSession().createSQLQuery(sql.toString());//.addEntity(TMbTask.class);
		
		List<TMbTask> taskList = new ArrayList<TMbTask>();
		
		List<Object[]> list = query.list();
		for(Object[] obj:list){
			if(obj!=null){
				TMbTask nTask = new TMbTask();
				
				if(obj[1] != null && !"".equals(obj[1])){
					
					nTask.setId(Integer.parseInt(obj[1].toString()));
				}
				
				if(obj[2] != null && !"".equals(obj[2])){
					nTask.setOpenid(obj[2].toString());
				}
				
				if(obj[3] != null && !"".equals(obj[3])){
					nTask.setMsgContent(obj[3].toString());
				}
				
				if(obj[4] != null && !"".equals(obj[4])){
					nTask.setMediaUrl(obj[4].toString());
				}
				
				if(obj[5] != null && !"".equals(obj[5])){
					nTask.setState(Integer.parseInt(obj[5].toString()));
				}
				
				if(obj[6] != null && !"".equals(obj[6])){
					nTask.setPublishTime((Date)obj[6]);
				}
				
				if(obj[7] != null && !"".equals(obj[7])){
					nTask.setUserId(Integer.parseInt(obj[7].toString()));
				}
				
				if(obj[8] != null && !"".equals(obj[8])){
					nTask.setMsgType(Integer.parseInt(obj[0].toString()));
				}
				
				if(obj[10] != null && !"".equals(obj[10])){
					nTask.setMessagetype(Integer.parseInt(obj[10].toString()));
				}
				
				if(obj[11] != null && !"".equals(obj[11])){
					nTask.setAnswerTime((Date)obj[11]);
				}
				
				if(obj[12] != null && !"".equals(obj[12])){
					nTask.setNickname(obj[12].toString());
				}
				
				if(obj[13] != null && !"".equals(obj[13])){
					nTask.setClientName(obj[13].toString());
				}
				
				taskList.add(nTask);
			}
		}
		
		return taskList;
	}
	
	public Number getTasksByOpenIdCount(Map<String, Object> data){
		String openid = (String)data.get("openid");
		
	    StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from(select row_number() over(order by answerTime desc) as rowNum"+
				   " from t_mb_task where (state = 1 or state = 2) and openid='"+openid+"') as task");
		Query query = getSession().createSQLQuery(sql.toString());
		return (Number)query.uniqueResult();
	}
	
	public void save(TMbTask task){
		/*String sql = "insert into t_mb_task(contactname,openid,source,content,state,publishtime,UserID,messagetype) " +
				"values(?,?,?,?,?,?,?,?)";
		Session session = getSession();
		Query query= session.createSQLQuery(sql);
		query.setParameter(0, task.getContactname());
		query.setParameter(1, task.getOpenid());
		query.setParameter(2, task.getSource());
		query.setParameter(3, task.getContent());
		query.setParameter(4, task.getState());
		query.setParameter(5, task.getPublishtime());
		query.setParameter(6, task.getUserID());
		query.setParameter(7, task.getMessagetype());
		query.executeUpdate();
		session.flush();*/
	}

	public TMbTask getTaskById(String id) {
		String sql = "select id as headAddress, * from t_mb_task where id="+id;
		Query query = getSession().createSQLQuery(sql).addEntity(TMbTask.class);
		return (TMbTask)query.uniqueResult();
	}
	
	public List<TMbTask> getTaskToHome(Integer userId){
		String sql = "select top 5 id as headAddress, * from t_mb_task  where state=0 and UserID="+userId+" and messagetype=0 order by publishtime desc";
		Query query = getSession().createSQLQuery(sql).addEntity(TMbTask.class);
		return query.list();
	}

	public Number getTasksByDataCount(Map<String, Object> data) {
		//取出查询条件
		String contactname = (String)data.get("contactname");//名称
		String startTime = (String)data.get("startTime");//开始时间
		String endTime = (String)data.get("endTime");//结束时间
		//Integer source = (Integer)data.get("source");//来源
		String content = (String)data.get("content");//内容
		//Integer userID = (Integer)data.get("userID");//用户ID
	    int state = (Integer)data.get("state");//消息状态
		
	    int groupid = (Integer)data.get("groupid");
	    
		StringBuffer start = new StringBuffer();
		StringBuffer end = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		if(groupid > 0){
			sql.append("select count(*) from "+
			           "(select row_number() over(order by publishtime desc) as rowNum, "+
					   "t.* from t_mb_task t left join t_mb_weixinuser c on t.openid=c.openid left join t_mb_group d on c.groupid = d.groupid where (d.groupid ="+groupid+" or d.parentId = "+groupid+")  and t.messagetype=0");
		}else {
			sql.append("select count(*) from "+
			           "(select row_number() over(order by publishtime desc) as rowNum, "+
					   "t.* from t_mb_task t left join t_mb_weixinuser c on t.openid=c.openid left join t_mb_group d on c.groupid = d.groupid where t.messagetype=0");
		}

		if(!contactname.equals("") && contactname != null){
			sql.append(" and c.nickname like '%"+contactname+"%'");
		}
		/*
		if(!source.equals("") && source != null && source != 0){
			sql.append(" and t.source="+source);
		}*/
		if(!content.equals("") && content != null){
			sql.append(" and t.msgContent like '%"+content+"%'");
		}
		if(!startTime.equals("") && startTime != null || !endTime.equals("") && endTime != null){
			start.append(startTime+" 00:00:00");
			end.append(endTime+" 23:59:59");
			sql.append(" and t.publishtime between '"+start.toString()+"' and '"+end.toString()+"'");
		}
		if(state == 0 || state == 1){
			sql.append(" and t.state="+state);
		}
		sql.append(") as task");
		
		
		Query query = getSession().createSQLQuery(sql.toString());
		return (Number)query.uniqueResult();
	}
}
