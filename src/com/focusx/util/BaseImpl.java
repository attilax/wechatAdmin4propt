package com.focusx.util;

//com.focusx.util.BaseImpl

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.attilax.core;
import com.attilax.util.HibernateSessionFactory;

public class BaseImpl {
	
	/**
	 * 获取Hibernate的会话对象
	 * 
	 * @return Session
	 */
	public Session getSession() {
		Session ss=null;
		if (Constant.sessionFactory != null)
			ss= Constant.sessionFactory.openSession();
		
		//o4
		if(Constant.sessionFactory==null)
		{
			ss= HibernateSessionFactory.getSession();
		}
		
		for(int i=0;i<5;i++)
		{
			if(ConnOK(ss))break;
			else
			{
				ss.close();
				ss= HibernateSessionFactory.getSession();
				core.log("--conn is newopen ,ssid:"+String.valueOf(ss.hashCode()));
			}
			core.sleep(100);
		}
		
	//	Session session = getSession();
	
		 
		return ss;
	}

	/**
	@author attilax 老哇的爪子
	 * @param ss 
		@since  2014-6-6 下午05:59:28$
	
	 * @return
	 */
	private boolean ConnOK(Session ss) {
		// attilax 老哇的爪子  下午05:59:28   2014-6-6 
		try {
			String checkHql = "select top 1 *  from    t_mb_actaward";
			Query cq = ss.createSQLQuery(checkHql);
		 
			List li=  cq.list();
			return true;
		} catch (Exception e) {
			core.warn("--conn is close ,ssid:"+String.valueOf(ss.hashCode()));
			core.log(e);return false;
		}
		
	}

}
