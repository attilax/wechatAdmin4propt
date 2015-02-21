package com.focusx.listener;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.focusx.entity.TMbEventHistory;
import com.focusx.entity.TMbSignHistory;
import com.focusx.entity.TMbWeixinUser;
import com.focusx.util.Constant;

public class InitdataAnalysisThread extends Thread {
	private static Logger logger = Logger.getLogger(InitdataAnalysisThread.class);
	
	public  void run() {
		 Constant.sessionFactory = (SessionFactory) ContextLoaderListener.getCurrentWebApplicationContext().getBean("sessionFactory");
		 try {
			 logger.info("报表对象初始化开始");
			 Constant.weixinuser = getWeixinUser();
			 Constant.members = getMember();
			 Constant.signHistory = getSignHistory();
			 Constant.eventHistory = getEventHistory();
			 logger.info("报表对象初始化结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static Session getSession() {
		if (Constant.sessionFactory != null){
			return Constant.sessionFactory.openSession();
		}else {
			return null;
		}
	}
	
	public static TMbWeixinUser getWeixinUser(){
		Session session = getSession();
		if(session != null){
			try {
				String sql = "select top 1 * from t_mb_weixinuser order by subscribe_time asc";
				Query query = session.createSQLQuery(sql).addEntity(TMbWeixinUser.class);
				return (TMbWeixinUser) query.uniqueResult();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				session.close();
			}
		}
		return null;
	}
	
	public static TMbWeixinUser getMember(){
		Session session = getSession();
		if(session != null){
			try {
				String sql = "select top 1 * from t_mb_weixinuser where bindDate is not null order by bindDate asc";
				Query query = session.createSQLQuery(sql).addEntity(TMbWeixinUser.class);
				return (TMbWeixinUser) query.uniqueResult();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				session.close();
			}
		}
		return null;
	}
	
	public static TMbSignHistory getSignHistory(){
		Session session = getSession();
		if(session != null){
			try {
		    	String sql = "select top 1 * from t_mb_sign_history order by signDate asc";
				Query query = session.createSQLQuery(sql).addEntity(TMbSignHistory.class);
				return (TMbSignHistory) query.uniqueResult();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				session.close();
			}
		}
		return null;
	}
	
	public static TMbEventHistory getEventHistory(){
		Session session = getSession();
		if(session != null){
			try {
		    	String sql = "select top 1 * from t_mb_event_history order by clickTime asc";
				Query query = session.createSQLQuery(sql).addEntity(TMbEventHistory.class);
				return (TMbEventHistory) query.uniqueResult();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				session.close();
			}
		}
		return null;
	}
}
