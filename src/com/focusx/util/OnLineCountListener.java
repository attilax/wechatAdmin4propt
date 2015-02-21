package com.focusx.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

/**
 * 
 * @author 张春雨
 * @模块 管理在线用户信息的监听器
 * @日期 2013-12-3 时间：下午04:40:02
 */
public class OnLineCountListener implements HttpSessionListener {
 
	private static Logger logger = Logger.getLogger("com.focusx.util.OnLineCountListener");
	// 存放在线用户的服务器内存变量
	private static Map<String, OnLineUser> ON_LINE_USER = Collections.synchronizedMap(new HashMap<String, OnLineUser>());
	private static Hashtable<String, HttpSession> SESSION_TABLE = new Hashtable<String, HttpSession>();
	// 存放签入用户的服务器内存变量
	private static Map<String, List<LoginUserInfo>> CHECKIN_USER = Collections.synchronizedMap(new HashMap<String, List<LoginUserInfo>>());
	private static Hashtable<String, HttpSession> CHECKIN_SESSION_TABLE = new Hashtable<String, HttpSession>();

	/**
	 * 创建Session时执行的方法
	 */
	public void sessionCreated(HttpSessionEvent se) {System.out.println("");
	}

	/**
	 * 浏览器关闭时，销毁Session时执行的方法，将当前用户从Session中删除
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		ON_LINE_USER.remove(session.getId());
		logger.info("登录用户session ID :" + session.getId());
		synchronized (SESSION_TABLE) {
			SESSION_TABLE.remove(session.getId());
		}
	}

	/**
	 * 将Session对象添加到在线用户集合中
	 * 
	 * @param onLineUser
	 *            封装后的用户信息
	 * @param session
	 *            Session对象
	 */
	public static void addOnLineUser(OnLineUser onLineUser, HttpSession session) {
		ON_LINE_USER.put(session.getId(), onLineUser);
		synchronized (SESSION_TABLE) {
			SESSION_TABLE.put(session.getId(), session);
		}
	}

	/**
	 * 修改用户的登录状态
	 * 
	 * @param userId
	 *            userId=0时，修改所有在线用户的状态，否则只修改指定用户的状态
	 * @param state
	 *            登录状态
	 */
	public static void editOnLineUserState(int userId, Integer state) {
		List<OnLineUser> list = (List<OnLineUser>) OnLineCountListener.getOnLineUsers();
		if (list != null && list.size() > 0) {
			Iterator<OnLineUser> it = list.iterator();
			while (it.hasNext()) {
				OnLineUser det = it.next();
				if (userId == 0)
					det.setState(state);
				else if (det.getUserId() == userId)
					det.setState(state);
			}
		}
	}

	/**
	 * 将Session对象从在线用户集合中删除
	 * 
	 * @param sessionId
	 */
	public static void stopSession(String sessionId) {
		try {
			HttpSession session = getSession(sessionId);
			if (session != null) {
				session.invalidate();
			}
			ON_LINE_USER.remove(sessionId);
			synchronized (SESSION_TABLE) {
				SESSION_TABLE.remove(session.getId());
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 获取在线用户列表
	 * 
	 * @return
	 */
	public static Collection<OnLineUser> getOnLineUsers() {
		Collection<OnLineUser> collection = (Collection<OnLineUser>) ON_LINE_USER.values();
		List<OnLineUser> list = new ArrayList<OnLineUser>();
		Iterator<OnLineUser> it = collection.iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}
		Collections.sort(list, new Comparator<OnLineUser>() {
			public int compare(OnLineUser o1, OnLineUser o2) {
				return (o1.getUserNo().compareToIgnoreCase(o2.getUserNo()));
			}
		});
		return list;
	}

	/**
	 * 获取某个Session对象里面存放的用户信息
	 * 
	 * @param session
	 * @return
	 */
	public static OnLineUser getOnLineUser(HttpSession session) {
		return ON_LINE_USER.get(session.getId());
	}

	public static Iterator getSet() {
		synchronized (SESSION_TABLE) {
			return SESSION_TABLE.values().iterator();
		}
	}

	/**
	 * 获取在线用户总数
	 * 
	 * @return
	 */
	public static int getUserCount() {
		return ON_LINE_USER.size();
	}

	public static HttpSession getSession(String sessionId) {
		synchronized (SESSION_TABLE) {
			return (HttpSession) SESSION_TABLE.get(sessionId);
		}
	}

	/**
	 * 将session用户添加到签入用户集合中
	 * 
	 * @param loginUserinfo
	 * @param session
	 */
	public static void addCheckInUser(List<LoginUserInfo> checkInList, HttpSession session) {
		CHECKIN_USER.put(session.getId(), checkInList);
		synchronized (CHECKIN_SESSION_TABLE) {
			CHECKIN_SESSION_TABLE.put(session.getId(), session);
		}
	}

	/**
	 * 将session对象从签入用户集合中移除
	 * 
	 * @param sessionId
	 */
	public static void stopCheckInSession(String sessionId) {
		try {
			HttpSession session = getSession(sessionId);
			CHECKIN_USER.remove(sessionId);
			synchronized (CHECKIN_SESSION_TABLE) {
				CHECKIN_SESSION_TABLE.remove(session.getId());
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 获取签入用户列表
	 * 
	 * @return
	 */
	public static Collection<LoginUserInfo> getCheckInUsersList() {
		Collection<List<LoginUserInfo>> collection = (Collection<List<LoginUserInfo>>) CHECKIN_USER.values();
		List<LoginUserInfo> checkInList = new ArrayList<LoginUserInfo>();
		Iterator<List<LoginUserInfo>> it = collection.iterator();
		while (it.hasNext()) {
			List<LoginUserInfo> list = it.next();
			if (list != null && list.size() > 0) {
				Iterator<LoginUserInfo> it2 = list.iterator();
				while (it2.hasNext()) {
					LoginUserInfo det = it2.next();
					checkInList.add(det);
				}
			}
		}
		Collections.sort(checkInList, new Comparator<LoginUserInfo>() {
			public int compare(LoginUserInfo o1, LoginUserInfo o2) {
				return (o1.getUserNo().compareToIgnoreCase(o2.getUserNo()));
			}
		});
		return checkInList;
	}
}
