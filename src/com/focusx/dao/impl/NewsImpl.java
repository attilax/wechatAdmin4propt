package com.focusx.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.focusx.entity.TMbGroup;
import com.focusx.entity.TMbNews;
import com.focusx.entity.TMbReply;
import com.focusx.entity.TMbTag;
import com.focusx.util.BaseImpl;
import com.focusx.util.Constant;


/**
 * 获取数据库中的图文消息
 * @author Administrator
 *
 */

public class NewsImpl extends BaseImpl {
	
	
	private static Logger logger = Logger.getLogger(NewsImpl.class);
	
	/**
	 * @category 获取单条消息
	 * @param id
	 * @return
	 */
	public TMbNews getNewsById(int id){
		
		Session session = null;
		TMbNews news = null;
		try{
			
			String hql = "from TMbNews where id =  ? ";
			session = getSession();
			Query query = session.createQuery(hql);
			query.setParameter(0, id);
			news = (TMbNews) query.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return news;
	}
	
	//根据用户所在的省份或城市模糊返回相关的促销图文，如果用户设置的是外国，那就无法获取
	public List<TMbNews> getNewsList(int type,String province,String city){
		
		logger.info("getNewsList===>"+type+"  "+province+"   "+city);
		
		//根据省市，判断所以分公司
		TMbGroup group = checkProvinceAndCity(province,city);
		
		if(group != null){
			System.out.println(group.getGroupid()+"   "+group.getGroupname());
		}
		//String sql = "select t.ID,t.Title,t.Author,t.CoverPage,t.Description,t.MainText,t.HtmlName,t.groupid from t_mb_news t left join t_mb_group g on t.groupid = g.groupid where t.flag = 1 and t.state = 0 and t.newsType = ? and (g.groupname like ? or g.groupname like ? or g.remark like ? or g.remark like ?)";
		
		Session sess = getSession();
		
		if(sess != null){
			try{
				
				String hql = "";
				if(group == null){
					
					logger.info("找不到分公司 。。。。");
					
					//获取取广州分公司的
					hql = "from TMbNews where groupid = 1 and newsType = ? and state = 0 order by rank asc";
					Query q = sess.createQuery(hql);
					q.setParameter(0, type);
					
					return q.list();
					
				}else{
					
					logger.info("找到分公司 >>> " + group.getGroupname());
					
					hql = "from TMbNews where groupid = ? and newsType = ? and state = 0 order by rank asc";
					Query q = sess.createQuery(hql);
					q.setParameter(0, group.getGroupid());
					q.setParameter(1, type);
					
					return q.list();
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				sess.close();
			}
		}
		
		return null;
	}
	
	public List<TMbNews> getNewsListByNewsId(int newsId){
		
		Session sess = getSession();
		
		if(sess != null){
			try {
				String hql = "from TMbNews where parentId = ? or id = ? order by rank asc";
				
				Query q = sess.createQuery(hql);
				q.setParameter(0, newsId);
				q.setParameter(1, newsId);
				
				return q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				sess.close();
			}
		}
		
		return null;
	}
	
	//根据用户所在有分公司id获取相关的图文
	public List<TMbNews> getNewsList(int type,int groupid){
		
		logger.info("getNewsList===>"+type+"   "+groupid);
		
		Session session = null;
		try{
			
			session = getSession();
			
			List<TMbNews> newsList = new ArrayList<TMbNews>();
			if(groupid == 0){
				
					String hql = "from TMbNews where newsType = ? and state = 0 order by rank asc";
					Query q = session.createQuery(hql);
					q.setParameter(0, type);
					
					newsList =  q.list();
				
			}else{
					String sql = "select t.ID,t.Title,t.Author,t.CoverPage,t.Description,t.MainText,t.HtmlName,t.groupid from t_mb_news t where flag = 1 and state = 0 and  newsType = ? and groupid = ? order by rank asc";
					
					Query query = session.createSQLQuery(sql);
					query.setParameter(0, type);
					query.setParameter(1,groupid);
					
					List list = query.list();
					
					if (list != null && list.size() > 0) {
						
						Iterator<Object[]> iter = list.iterator();
						while(iter.hasNext()){
							Object[] obj = iter.next();
							TMbNews tmp= Convert2News(obj);
							if(tmp != null){
								newsList.add(tmp);
							}
						}
					}
			}
			return newsList;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return null;
	}
	
	
	public List<TMbNews> getNewsListForCache(){
		
		Session session = getSession();
		
		if(session != null){
			/**
			 * 获取所有启用的图文消息
			 */
			try{
				String hql = "from TMbNews tn where flag = 1 and state = 0 order by rank asc";
				Query q = session.createQuery(hql);
				List<TMbNews> newsList = q.list();
				
				return newsList;
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				session.close();
			}
		}
		return null;
	}
	
	/**
	 * @category 获取图文消息
	 * @return
	 */
	public List<TMbNews> getNewsList(){
		
		Session session = null;
		try{
			
			String sql = "select top 10 t.ID,t.Title,t.Author,t.CoverPage,t.Description,t.MainText,t.HtmlName,t.groupid from t_mb_news t order by t.rank asc";
			session = getSession();
			Query query = session.createSQLQuery(sql);
			List<TMbNews> newsList = new ArrayList<TMbNews>();
			List list = query.list();
			
			if (list != null && list.size() > 0) {
				
				Iterator<Object[]> iter = list.iterator();
				while(iter.hasNext()){
					Object[] obj = iter.next();
					TMbNews tmp= Convert2News(obj);
					if(tmp != null){
						newsList.add(tmp);
					}
				}
			}
			return newsList;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return null;
	}
	
	
	
	private static TMbNews Convert2News(Object[] obj){
		
		if(obj != null){
			
			try{
					TMbNews news = new TMbNews();
						//id
						news.setId(Integer.parseInt(String.valueOf(obj[0])));
						//title
						if(null != obj[1]){
							news.setTitle(obj[1].toString());
						}
						
						if(null != obj[2]){
							news.setAuthor(obj[2].toString());
						}
						
						if(null != obj[3]){
							news.setCoverPage(obj[3].toString());
						}
						
						if(null != obj[4]){
							news.setDescription(obj[4].toString());
						}
						
						if(null != obj[5]){
							news.setMainText(obj[5].toString());
						}
						
						if(null != obj[6]){
							news.setHtmlName(obj[6].toString());
						}
						
						if(null != obj[7]){
						//	news.setGroupId(Integer.parseInt(obj[7].toString()));
						}
					return news;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * 
	@author attilax 老哇的爪子
		@since  2014-6-11 下午01:48:45$
	
	 * @param province
	 * @param city
	 * @return
	 */
	public static TMbGroup checkProvinceAndCity(String province,String city){
		
		TMbGroup retGroup = null;
		
		if(province != null && city != null){
			boolean isProFound = false;
			boolean isCityFound = false;
			TMbGroup proGroup = null;
			TMbGroup cityGroup = null;
			
			if(Constant.SERVER_MAP != null && Constant.SERVER_MAP.size() > 0){
					for(Map.Entry<String,TMbGroup> oneEntry:Constant.SERVER_MAP.entrySet()){
						
						if(oneEntry.getKey() != null && oneEntry.getValue() != null){
							List<TMbTag> tagList = oneEntry.getValue().getTagList();
							if(tagList != null && tagList.size() > 0){
								for(TMbTag oneTag:tagList){
									//判断省
									logger.info(province +"   >>>> " + oneTag.getTagName());
									if(province.indexOf(oneTag.getTagName()) > -1){
										proGroup = oneEntry.getValue();
										logger.info("找到用户所在省的对应 分公司 ===>" + proGroup.getGroupname());
										isProFound = true;
									}
									if(city.indexOf(oneTag.getTagName()) > -1){
										cityGroup = oneEntry.getValue();
										logger.info("找到用户所在城市的对应 分公司 ===>" + cityGroup.getGroupname());
										isCityFound = true;
									}
									
									//其中一个条件为真，跳出循环
									if(isProFound || isCityFound){
										break;
									}
								}
							}
						}
						
						//如果两者为真，取城市优先
						if(isProFound && isCityFound){
							retGroup = cityGroup;
							break;
						}else if(isProFound && !isCityFound){
							//取省
							retGroup = proGroup;
							break;
						}else if(!isProFound && isCityFound){
							//取城市
							retGroup = cityGroup;
							break;
						}
					}
			}
		}
		
		return retGroup;
	}
	
	public TMbReply searchMD5Keyword(String md5keyword,int groupid){
		
		Session sess = getSession();
		
		if(sess != null){
			try{
				
				String hql = "from TMbReply where md5keyword = ? and groupid = ?";
				
				Query q = sess.createQuery(hql);
				
				q.setParameter(0, md5keyword);
				
				q.setParameter(1, groupid);
				
				q.setMaxResults(1);
				
				return (TMbReply)q.uniqueResult();
				
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				sess.close();
			}
		}
		
		return null;
	}

}
