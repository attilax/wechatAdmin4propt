/**
 * @author attilax 老哇的爪子
	@since  2014-6-19 下午06:00:17$
 */
package com.focusx.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.attilax.spri.SpringUtil;
import com.attilax.util.god;
import com.focusx.dao.WeixinUserManagerDao;

/**
 * @author attilax 老哇的爪子
 *@since 2014-6-19 下午06:00:17$
 */
public class springUtil4backend {
	public static SessionFactory sessfac;
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	public static void main(String[] args) {
		for(int i=0;i<20;i++){
			god.newThread(new Runnable(){

				@Override
				public void run() {
					// attilax 老哇的爪子  下午07:26:01   2014-6-19 
					System.out.println(getSession().hashCode());
				}}, "xx"+String.valueOf(i));
	
		}
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-6-19 下午06:00:57$
	 * 
	 * @return
	 */
	public  static Session getSession_del() {
		// attilax 老哇的爪子 下午06:00:57 2014-6-19

		iniSessfac();
	
		return getSessionSync();
		// return null;
	}
	
	/**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    public static Session getSession()   {
        Session session = (Session) threadLocal.get();
    
		if (session == null || !session.isOpen()) {
//			if (sessionFactory == null) {
//				rebuildSessionFactory();
//			}
			iniSessfac();
			session =getSessionSync();
			threadLocal.set(session);
		}

        return session;
    }
    
	/**
	@author attilax 老哇的爪子
		@since  2014-6-19 下午07:31:41$
	
	 * @return
	 */
	private synchronized static Session getSessionSync() {
		// attilax 老哇的爪子  下午07:31:41   2014-6-19 
		return  sessfac.openSession();
	}

	// attilax 老哇的爪子 下午06:00:17 2014-6-19

	/**
	@author attilax 老哇的爪子
		@since  2014-6-19 下午06:32:03$
	
	 */
	private synchronized static void iniSessfac() {
		// attilax 老哇的爪子  下午06:32:03   2014-6-19 
		if (sessfac == null) {
			WeixinUserManagerDao dso = (WeixinUserManagerDao) SpringUtil
					.getBean("weixinUserManagerDao");
			sessfac = dso.getSessionFactory();
		}
	}
}

// attilax 老哇的爪子