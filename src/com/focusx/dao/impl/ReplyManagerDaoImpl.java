package com.focusx.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;

import com.focusx.dao.ReplyManargerDao;
import com.focusx.entity.TMbNews;
import com.focusx.entity.TMbReply;

public class ReplyManagerDaoImpl implements ReplyManargerDao{

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
	
	public List<TMbReply> getReplys(Map<String, Object> data) {
		//分页条件
		int pageNumber = (Integer) data.get("pageNumber");
		int pageSize = (Integer) data.get("pageSize");
		
		//查询条件
		String keyword = (String) data.get("keyword");
		String content = (String) data.get("content");
		Integer groupid = (Integer) data.get("groupid");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select row_number() over(order by d1.id desc) as rowNum,d1.* from t_mb_reply d1 where 1=1");
		
		if(keyword != null && !keyword.equals("")){
			sql.append(" and d1.keyword like '%"+keyword+"%'");
		}
		if(content != null && !content.equals("")){
			sql.append(" and d1.content like '%"+content+"%'");
		}
		if(groupid > 0){
			sql.append(" and d1.groupid="+groupid);
		}
		if(pageNumber!=-1 && pageSize!=-1){
			sql.append(") as reply where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));
		}else {
			sql.append(") as reply where rowNum between 1 and 10");
		}
		Query query = getSession().createSQLQuery(sql.toString()).addEntity(TMbReply.class);

		List<TMbReply> temp = query.list();
		List<TMbReply> list = new ArrayList<TMbReply>();
		for(TMbReply reply : temp){
			if(reply.getNewsId() != null && reply.getNewsId() > 0){
				String sql2 = "select title from t_mb_news where ID="+reply.getNewsId();
				String title = (String) getSession().createSQLQuery(sql2).uniqueResult();
				reply.setTitle(title);
			}
			list.add(reply);
		}
		return list;
	}

	public Integer getReplysCount(Map<String, Object> data) {
		//查询条件
		String keyword = (String) data.get("keyword");
		String content = (String) data.get("content");
		Integer groupid = (Integer) data.get("groupid");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from t_mb_reply d1 where 1=1");
		
		if(keyword != null && !keyword.equals("")){
			sql.append(" and d1.keyword like '%"+keyword+"%'");
		}
		if(content != null && !content.equals("")){
			sql.append(" and d1.content like '%"+content+"%'");
		}
		if(groupid > 0){
			sql.append(" and d1.groupid="+groupid);
		}
		Query query = getSession().createSQLQuery(sql.toString());
		return (Integer) query.uniqueResult();
	}

	public TMbReply getReply(Integer id) {
		String hql = "from TMbReply where id="+id;
		TMbReply reply = (TMbReply) getSession().createQuery(hql).uniqueResult();
		if(reply.getNewsId() != null && reply.getNewsId() > 0){
			String hql2 = "from TMbNews where id="+reply.getNewsId();
			TMbNews news = (TMbNews) getSession().createQuery(hql2).uniqueResult();
			if(news != null){
				reply.setTitle(news.getTitle());
			}
		}
		return reply;
	}

	public Integer save(TMbReply reply) {
		Session session = getSession();
		session.save(reply);
		session.flush();
		return reply.getId();
	}

	public boolean update(TMbReply reply) {
		boolean result = true;
		try {
			Session session = getSession();
			session.update(reply);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean checkKeyword(String keyword, Integer groupid) {
		String hql ="from TMbReply where keyword='"+keyword+"' and groupid="+groupid;
		Query query = getSession().createQuery(hql);
		if(query.uniqueResult() == null){
			return false;
		}else {
			return true;
		}
	}

	public boolean delete(String deleteList) {
		boolean result = true;
		try {
			final String[] ids = deleteList.split(",");
			getSession().doWork(new Work(){
				public void execute(Connection conn) throws SQLException {
					String sql = "delete from t_mb_reply where id = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					for(int i = 0;i < ids.length; i++){
						pstmt.setInt(1, Integer.parseInt(ids[i].trim()));
						pstmt.addBatch();
					}
					//执行批量操作
					pstmt.executeBatch();
				}
			});
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		} finally{
			return result;
		}
	}

	public TMbReply getReplyByNewsId(Integer newsId) {
		String hql = "from TMbReply where newsId="+newsId;
		Query query = getSession().createQuery(hql);
		return (TMbReply) query.uniqueResult();
	}

	public void deleteNews(Integer newsId) {
		String hql = "delete from TMbReply where newsId="+newsId;
		getSession().createQuery(hql).executeUpdate();
	}

}