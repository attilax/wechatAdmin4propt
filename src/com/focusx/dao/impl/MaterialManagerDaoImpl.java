package com.focusx.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import com.focusx.dao.MaterialManagerDao;
import com.focusx.entity.TMbNews;
import com.focusx.util.Constant;

public class MaterialManagerDaoImpl implements MaterialManagerDao{
	
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

	public List<TMbNews> getMaterials(Map<String, Object> data) {
		//查询条件
		String startTime = (String)data.get("startTime");
		String endTime = (String)data.get("endTime");
		String title = (String)data.get("title");
		String description = (String)data.get("description");
		Integer groupid = (Integer)data.get("groupid");
		Integer newsType = (Integer)data.get("newsType");
		
		//分页条件
		int pageNumber = (Integer)data.get("pageNumber");
		int pageSize = (Integer)data.get("pageSize");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ID,Title,Author,Description,convert(varchar, createTime, 120 ) as createTime,groupname,HtmlName,flag,newsType," +
				"parentId,actName from (select row_number() over(order by d1.ID desc) as rowNum, d1.ID,d1.Title," +
				"d1.Author,d1.Description,d1.createTime,d1.flag,d1.newsType,d1.parentId,(select d2.groupname from t_mb_group d2 where d2.groupid=d1.groupid" +
				")as groupname, d1.HtmlName,(select actName from t_mb_activity d2 where d2.id=d1.activityId)as actName from t_mb_news d1 where 1=1");
		if(title != null && !title.equals("")){//判断标题是否为空或空字符串
			sql.append(" and d1.title like '%"+title+"%'");
		}
		if(description != null && !description.equals("")){//判断摘要是否为空或空字符串
			sql.append(" and d1.description like '%"+description+"%'");
		}
		if(startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)){
			StringBuffer start = new StringBuffer();
			start.append(startTime+" 00:00:00");
			StringBuffer end = new StringBuffer();
			end.append(endTime+" 23:59:59");
			sql.append(" and d1.createTime between '"+start.toString()+"' and '"+end.toString()+"'");
		}
		if(groupid != null && groupid > 0){
			sql.append(" and d1.groupid="+groupid);
		}
		sql.append(" and d1.state=0");
		if(newsType != null && newsType > 0){
			sql.append(" and d1.newsType="+newsType);
		}
		sql.append(" and d1.parentId in(-1, 0)");
		//分页
		if(pageNumber!=-1 && pageSize!=-1){
			sql.append(") as news where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));	
		}else {
			sql.append(") as news where rowNum between 1 and 10");	
		}
		Query query = getSession().createSQLQuery(sql.toString()).addScalar("ID", StandardBasicTypes.INTEGER)
				.addScalar("Title", StandardBasicTypes.STRING).addScalar("Author", StandardBasicTypes.STRING)
				.addScalar("Description", StandardBasicTypes.STRING).addScalar("createTime", StandardBasicTypes.STRING)
				.addScalar("groupname", StandardBasicTypes.STRING).addScalar("HtmlName", StandardBasicTypes.STRING)
				.addScalar("flag", StandardBasicTypes.INTEGER).addScalar("newsType", StandardBasicTypes.INTEGER)
				.addScalar("parentId", StandardBasicTypes.INTEGER).addScalar("actName", StandardBasicTypes.STRING);
		List temp = query.list();
		List<TMbNews> newsList = new ArrayList<TMbNews>();
		if(temp != null && temp.size() > 0){
			for(int i =0; i < temp.size(); i++){
				Object[] object = (Object[])temp.get(i);
				TMbNews news = new TMbNews();
				news.setId((Integer)object[0]);
				news.setTitle((String)object[1]);
				news.setAuthor((String)object[2]);
				news.setDescription((String)object[3]);
				news.setMainText((String)object[4]);//本来是放创建时间的，但是获取出来的是字符串，用其他字段存放
				news.setGroupname((String)object[5]);
				news.setHtmlName((String)object[6]);
				news.setFlag((Integer)object[7]);
				news.setNewsType((Integer)object[8]);
				if(news.getNewsType() == Constant.NEWS_KEYWORD){
					String sql2 = "select keyword from t_mb_reply where newsId="+news.getId();
					String keyword = (String) getSession().createSQLQuery(sql2).uniqueResult();
					news.setKeyword(keyword);
				}
				news.setParentId((Integer)object[9]);
				news.setActName((String)object[10]);
				newsList.add(news);
			}
		}
		return newsList;
	}

	public Number getMaterialsCount(Map<String, Object> data) {
		//查询条件
		String startTime = (String)data.get("startTime");
		String endTime = (String)data.get("endTime");
		String title = (String)data.get("title");
		String description = (String)data.get("description");
		Integer groupid = (Integer)data.get("groupid");
		Integer newsType = (Integer)data.get("newsType");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(d1.ID) from t_mb_news d1 where 1=1");
		if(title != null && !title.equals("")){//判断标题是否为空或空字符串
			sql.append(" and d1.title like '%"+title+"%'");
		}
		if(description != null && !description.equals("")){//判断摘要是否为空或空字符串
			sql.append(" and d1.description like '%"+description+"%'");
		}
		if(startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)){
			StringBuffer start = new StringBuffer();
			start.append(startTime+" 00:00:00");
			StringBuffer end = new StringBuffer();
			end.append(endTime+" 23:59:59");
			sql.append(" and d1.createTime between '"+start.toString()+"' and '"+end.toString()+"'");
		}
		if(groupid != null && groupid > 0){
			sql.append(" and d1.groupid="+groupid);
		}
		sql.append(" and d1.state=0");
		if(newsType != null && newsType > 0){
			sql.append(" and d1.newsType="+newsType);
		}
		sql.append(" and d1.parentId in(-1, 0)");
		Query query = getSession().createSQLQuery(sql.toString());
		return (Number) query.uniqueResult();
	}

	public Integer saveMaterial(TMbNews news) {
		Session session = getSession();
		session.save(news);
		session.flush();
		return news.getId();
	}

	public TMbNews getTMbNewsById(Integer id) {
		String hql = "from TMbNews where id="+id;
		Query query = getSession().createQuery(hql);
		return (TMbNews) query.uniqueResult();
	}

	public Integer update(TMbNews news) {
		String sql = "update t_mb_news set author=?, coverPage=?, description=?,mainText=?,title=?, showimg=? where id= ?";
		Query query = getSession().createSQLQuery(sql);
		
		query.setParameter(0, news.getAuthor());
		query.setParameter(1, news.getCoverPage());
		query.setParameter(2, news.getDescription());
		query.setParameter(3, news.getMainText());
		query.setParameter(4, news.getTitle());
		query.setParameter(5, news.getShowimg());
		query.setParameter(6, news.getId());
		
		query.executeUpdate();
		return news.getId();
	}

	public void delete(Integer id) {
		String sql = "delete from TMbNews where id="+id;
		getSession().createQuery(sql).executeUpdate();
	}

	public void deleteMaterialByState(Integer id) {
		String sql = "update t_mb_news set state=1 where id="+id+" or parentId="+id;
		Query query = getSession().createSQLQuery(sql);
		query.executeUpdate();
	}

	public boolean checkNewsTypeCount(Integer groupid, Integer newsType) {
		String hql = "from TMbNews where newsType="+newsType+" and groupid="+groupid+" and flag=1";
		Query query = getSession().createQuery(hql);
		if(query.uniqueResult() == null){
			return false;
		}else {
			return true;
		}
	}

	public void updateFlag(Integer id, Integer type) {
		String sql = "";
		if(type == Constant.ZERO){
			sql = "update t_mb_news set flag=1 where ID="+id;
		}else if(type == Constant.ONE){
			sql = "update t_mb_news set flag=0 where ID="+id;
		}
		Query query = getSession().createSQLQuery(sql);
		query.executeUpdate();
		
	}

	public List<TMbNews> getMoreMaterial(Integer newsId) {
		String hql = "from TMbNews where parentId = "+newsId+" and state=0 or id="+newsId+" order by rank asc";
		Query query = getSession().createQuery(hql);
		return  query.list();
	}

	public boolean saveMoreMaterial(TMbNews news) {
		try {
			String covrPage = news.getCoverPage();
			String imgFileName = covrPage.substring(covrPage.lastIndexOf("/")+1, covrPage.length());
			String sql = "update t_mb_news set title=?,author=?,coverPage=?,mainText=?,showimg=? where id= ?";
			Query query = getSession().createSQLQuery(sql);
			
			query.setParameter(0, news.getTitle());
			query.setParameter(1, news.getAuthor());
			query.setParameter(2, imgFileName);
			//query.setParameter(2, news.getDescription());
			query.setParameter(3, news.getMainText());
			query.setParameter(4, news.getShowimg());
			query.setParameter(5, news.getId());
			
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateNewsType(Integer newsType, Integer groupid, Integer id, String keyword, Integer activityId) {
		try {
			if(id == Constant.ZERO){//更改以前图文
				String sql = null;
				sql = "update t_mb_news set newsType=0 where groupid="+groupid+" and newsType="+newsType+" and state=0";
				Query query = getSession().createSQLQuery(sql);
				query.executeUpdate();
			}else {//更改现在的图文
				String sql = null;
				if(newsType == Constant.NEWS_RULE && activityId > 0){
					sql = "update t_mb_news set newsType=12,activityId="+activityId+" where groupid="+groupid+" and " +
							"ID in(select ID from t_mb_news where parentId="+id+" and state=0 or ID="+id+")";
				}else if(newsType > 0){
					sql = "update t_mb_news set newsType="+newsType+" where groupid="+groupid+" and " +
							"ID in(select ID from t_mb_news where parentId="+id+" and state=0 or ID="+id+")";
				}else if(newsType == Constant.ZERO){
					sql = "update t_mb_news set newsType="+newsType+" where ID in(select ID from t_mb_news where parentId="+id+" and state=0 or ID="+id+")";
				}
				Query query = getSession().createSQLQuery(sql);
				query.executeUpdate();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean checkKeyword(String keyword) {
		String sql = "select count(*) from t_mb_news where state=0 and keyword='"+keyword+"'";
		Query query = getSession().createSQLQuery(sql);
		Integer num = (Integer) query.uniqueResult();
		if(num > 0){
			return true;
		}else {
			return false;
		}
	}

	public boolean checkActivityNewsType(Integer newsType, Integer activityId) {
		String sql = "select count(*) from t_mb_news where newsType="+newsType+" and activityId="+activityId;
		Query query = getSession().createSQLQuery(sql);
		Integer num = (Integer) query.uniqueResult();
		if(num > 0){
			return true;
		}else {
			return false;
		}
	}

	public List<TMbNews> getMaterialByGroupid(Integer groupid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,title from t_mb_news where parentId in(-1, 0) and state=0 and groupid="+groupid);
		Query query = getSession().createSQLQuery(sql.toString()).addScalar("id", StandardBasicTypes.INTEGER)
				.addScalar("Title", StandardBasicTypes.STRING).setResultTransformer(Transformers.aliasToBean(TMbNews.class));
		return query.list();
	}

	public List<TMbNews> getMaterialsByGroupid(Integer groupid) {
		String sql = "select id, newsType from t_mb_news where parentId in(-1, 0) and newsType > 0 and state=0 and groupid="+groupid;
		Query query = getSession().createSQLQuery(sql).addScalar("id", StandardBasicTypes.INTEGER)
				.addScalar("newsType", StandardBasicTypes.INTEGER).setResultTransformer(Transformers.aliasToBean(TMbNews.class));
		return query.list();
	}
}