package com.focusx.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.attilax.SafeVal;
import com.attilax.core;
import com.attilax.hb.HbX;
import com.attilax.util.tryX;
import com.focusx.dao.WeixinUserManagerDao;
import com.focusx.entity.TMbWeixinUser;
import com.focusx.util.Constant;

public class WeixinUserManagerDaoImpl implements WeixinUserManagerDao{
	
	public static String uid(final String openid)
	{
		if(SafeVal.val(openid).length()==0)
		{
			core.log("--o619:openid is empty");
			return "";
		}
		core.log("--o619a:openid is :"+SafeVal.val(openid));
		return new tryX<String>(){

			@Override
			public String item(Object t) throws Exception {
				// attilax 老哇的爪子  下午06:06:25   2014-6-19 
				WeixinUserManagerDaoImpl c=new WeixinUserManagerDaoImpl();
				c.sess=(springUtil4backend.getSession());
		 
				return c.getWeixinUserByOpenId(openid).getUserId().toString();
			}}.$("");
	}


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
	
	public List<TMbWeixinUser> getWeixinusersByGroupid(Map<String, Object> data) {
		//查询条件
		Integer groupid = (Integer)data.get("groupid");
		String inorout = (String)data.get("inorout");
		String nickname = (String)data.get("nickname");
		
		//分页条件
		Integer pageNumber = (Integer)data.get("pageNumber");
		Integer pageSize = (Integer)data.get("pageSize");
		
		if(groupid != null){
			StringBuffer sql = new StringBuffer();
			sql.append("select * from (select row_number() over(order by d1.UserID  desc) as rowNum, d1.* from t_mb_weixinuser d1 where 1=1 ");
			if(inorout != null && inorout.equals("in")){
				sql.append(" and d1.groupid="+groupid);
			}else if(inorout != null && inorout.equals("out")){
				sql.append(" and d1.groupid<>"+groupid);
			}
			if(nickname != null && !StringUtils.trim(nickname).equals("")){
				sql.append(" and d1.nickname like '%"+nickname+"%'");
			}
			//分页
			if(pageNumber!=-1 && pageSize!=-1){
				sql.append(") as users where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));	
			}else {
				sql.append(") as users where rowNum between 1 and 10");	
			}
			Query query = getSession().createSQLQuery(sql.toString()).addEntity(TMbWeixinUser.class);
			return query.list();
		}
		return null;
	}

	public Number getWeixinusersCountByGroupid(Map<String, Object> data) {
		//查询条件
		Integer groupid = (Integer)data.get("groupid");
		String inorout = (String)data.get("inorout");
		String nickname = (String)data.get("nickname");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(d1.UserID) from t_mb_weixinuser d1 where 1=1 ");
		if(inorout != null && inorout.equals("in")){
			sql.append(" and d1.groupid="+groupid);
		}else if(inorout != null && inorout.equals("out")){
			sql.append(" and d1.groupid<>"+groupid);
		}
		if(nickname != null && !StringUtils.trim(nickname).equals("")){
			sql.append(" and d1.nickname like '%"+nickname+"%'");
		}
		Query query = getSession().createSQLQuery(sql.toString());
		return (Number) query.uniqueResult();
	}

	public List<TMbWeixinUser> getWeixinUsers(Map<String, Object> data) {
		//查询条件
		String startTime = (String)data.get("startTime");
		String endTime = (String)data.get("endTime");
		String nickname = (String)data.get("nickname");
		Integer subscribe = (Integer)data.get("subscribe");
		Integer memberType = (Integer) data.get("memberType");
		Integer insertFlag = (Integer) data.get("insertFlag");
		
		String province = (String) data.get("province");
		String city = (String)data.get("city");
		
		//分页条件
		int pageNumber = (Integer)data.get("pageNumber");
		int pageSize = (Integer)data.get("pageSize");
		
		StringBuffer sql = new StringBuffer();
		//p2j
		if(HbX.ifMysql(getSession()))
		{
			sql.append("select * from (select  u.* from t_mb_weixinuser u where 1=1");
	
		}else
		{
		sql.append("select * from (select row_number() over(order by u.userId desc) as rowNum, u.* from t_mb_weixinuser u where 1=1");
		}
		if(nickname != null && !nickname.equals("")){
			sql.append(" and u.nickname like '%"+nickname+"%'");
		}
		
		if(province != null && !"".equals(province)){
			sql.append(" and u.province like '%"+province+"%'");
		}
		
		if(city != null && !"".equals(city)){
			sql.append(" and u.city like '%"+city+"%'");
		}
		
		if(startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)){
			StringBuffer start = new StringBuffer();
			start.append(startTime+" 00:00:00");
			StringBuffer end = new StringBuffer();
			end.append(endTime+" 23:59:59");
			sql.append(" and u.subscribe_time between '"+start.toString()+"' and '"+end.toString()+"'");
		}
		if(subscribe != null){
			if(subscribe == -1){//全部
			}else if(subscribe == 0){//已取消
				sql.append(" and u.subscribe=0");
			}else if(subscribe == 1){//已关注
				sql.append(" and u.subscribe=1");
			}
		}
		if(memberType != null){
			if(memberType == -1){
			}else if(memberType == 0){//会员
				sql.append(" and u.memberId is not null");
			}else if(memberType == 1){//非会员
				sql.append(" and u.memberId is null");
			}
		}
		if(insertFlag != null && insertFlag == 0){
			sql.append(" and u.insert_flag=0");
		}
		//分页
		if(pageNumber!=-1 && pageSize!=-1){
			//p2j
			if(HbX.ifMysql(getSession()))
			{
				sql.append(") as users where 1=1  limit "+(pageNumber-1)+" , "+(pageNumber+pageSize-1));	
				
			}else
			sql.append(") as users where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));	
		}else {
			//p2j
			if(HbX.ifMysql(getSession()))
				sql.append(") as users where 1=1 limit  0 , 10");	
			else
			sql.append(") as users where rowNum between 1 and 10");	
		}
		Query query = getSession().createSQLQuery(sql.toString()).addEntity(TMbWeixinUser.class);
		return query.list();
	}

	public Number getWeixinUsersCount(Map<String, Object> data) {
		//查询条件
		String startTime = (String)data.get("startTime");
		String endTime = (String)data.get("endTime");
		String nickname = (String)data.get("nickname");
		Integer subscribe = (Integer)data.get("subscribe");
		Integer memberType = (Integer) data.get("memberType");
		Integer insertFlag = (Integer) data.get("insertFlag");
		
		String province = (String) data.get("province");
		String city = (String)data.get("city");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from t_mb_weixinuser u where 1=1");
		
		if(nickname != null && !nickname.equals("")){
			sql.append(" and u.nickname like '%"+nickname+"%'");
		}
		
		if(province != null && !"".equals(province)){
			sql.append(" and u.province like '%"+province+"%'");
		}
		
		if(city != null && !"".equals(city)){
			sql.append(" and u.city like '%"+city+"%'");
		}
		
		if(startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)){
			StringBuffer start = new StringBuffer();
			start.append(startTime+" 00:00:00");
			StringBuffer end = new StringBuffer();
			end.append(endTime+" 23:59:59");
			sql.append(" and u.subscribe_time between '"+start.toString()+"' and '"+end.toString()+"'");
		}
		if(subscribe != null){
			if(subscribe == -1){//全部
			}else if(subscribe == 0){//已取消
				sql.append(" and u.subscribe=0");
			}else if(subscribe == 1){//已关注
				sql.append(" and u.subscribe=1");
			}
		}
		if(memberType != null){
			if(memberType == -1){
			}else if(memberType == 0){//会员
				sql.append(" and u.memberId is not null");
			}else if(memberType == 1){//非会员
				sql.append(" and u.memberId is null");
			}
		}
		if(insertFlag != null && insertFlag == 0){
			sql.append(" and u.insert_flag=0");
		}
		Query query = getSession().createSQLQuery(sql.toString());
		return (Number) query.uniqueResult();
	}

	public TMbWeixinUser getWeixinUserByUserId(Integer userId) {
		String hql = "from TMbWeixinUser where userId="+userId;
		Query query = getSession().createQuery(hql);
		return (TMbWeixinUser) query.uniqueResult();
	}
	
	/**
	 * 
	@author attilax 老哇的爪子
		@since  2014-6-19 下午05:33:43$
	todox hql get one obj o6f
	 * @param userId
	 * @return
	 */
	public TMbWeixinUser getWeixinUserByOpenId(String userId) {
		String hql = "from TMbWeixinUser where openId='"+userId+"'";
	
		 Session sess;
		 if(this.sess!=null)
			 sess=this.sess;
		 else
			 sess=getSession();
		Query query =sess .createQuery(hql);
		return (TMbWeixinUser) query.uniqueResult();
	}

	public Session sess;
	public Integer getWeixinUserWithNullBranchCount() {
		String sql = "select count(*) from t_mb_weixinuser where subscribe=1 and groupid is null or groupid=0";
		Query query = getSession().createSQLQuery(sql);
		return (Integer) query.uniqueResult();
	}

	public List<TMbWeixinUser> getWeixinUserByTime(Integer time) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select userId,province,city from " +
				"(select row_number() over(order by d1.UserID desc) as rowNum, d1.UserID,d1.province,d1.city from t_mb_weixinuser d1 where" +
				" d1.subscribe=1 and d1.groupid is null or d1.groupid=0) as weixinuser");
		if(time >= 0){
			sql.append(" where rowNum between "+(time*Constant.everyTimeSize)+" and "+((time+1)*Constant.everyTimeSize-1));
		}else {
			sql.append(" where rowNum between 1 and 500");
		}
		Query query = getSession().createSQLQuery(sql.toString()).addScalar("userId", StandardBasicTypes.INTEGER)
				.addScalar("province", StandardBasicTypes.STRING).addScalar("city", StandardBasicTypes.STRING)
				.setResultTransformer(Transformers.aliasToBean(TMbWeixinUser.class));
		return query.list();
	}

	public void updateToGroupId(final Map<Integer, Integer> data) {
		Session session = getSession();
		try{
			session.doWork(new Work(){
				String sql = "update t_mb_weixinuser set groupid =? where userId = ?";
				Set<Integer> keySet = data.keySet();
				public void execute(Connection conn) throws SQLException {
					PreparedStatement pstmt = conn.prepareStatement(sql);
					int i = 0;
					for(Integer userId : keySet){
						i++;
						pstmt.setInt(1, data.get(userId));
						pstmt.setInt(2, userId);
						
						pstmt.addBatch();
						if(i%500 == 0 && i > 0){
							pstmt.executeBatch();
							pstmt.clearBatch();
						}
					}
					pstmt.executeBatch();
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public List<TMbWeixinUser> getNullGroupList(Map<String, Object> data) {
		//查询条件
		String nickname = (String) data.get("nickname");
		
		//分页条件
		int pageNumber = (Integer)data.get("pageNumber");
		int pageSize = (Integer)data.get("pageSize");
		String province = (String)data.get("province");
		String city = (String)data.get("city");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select row_number() over(order by d1.userId desc) as rowNum, d1.* " +
				"from t_mb_weixinuser d1 where d1.subscribe=1");
		if(nickname != null && !nickname.equals("")){
			sql.append(" and d1.nickname like '%"+nickname+"%'");
		}
		
		if(province != null && !"".equals(province)){
			sql.append(" and d1.province like '%"+province+"%'");
		}
		
		if(city != null && !"".equals(city)){
			sql.append(" and d1.city like '%"+city+"%'");
		}
		
		sql.append(" and (d1.groupid is null or d1.groupid=0)");
		if(pageNumber != -1 && pageSize != -1){
			sql.append(") as users where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));	
		}else {
			sql.append(") as users where rowNum between 1 and 10");	
		}
		Query query = getSession().createSQLQuery(sql.toString()).addEntity(TMbWeixinUser.class);
		return query.list();
	}

	public Integer getNullGroupListCount(Map<String, Object> data) {
		//查询条件
		String nickname = (String) data.get("nickname");
		StringBuffer sql = new StringBuffer();
		
		String province = (String)data.get("province");
		String city = (String)data.get("city");
		
		sql.append("select count(*) from t_mb_weixinuser where subscribe=1");
		if(nickname !=null && !nickname.equals("")){
			sql.append(" and nickname like '%"+nickname+"%'");
		}
		

		if(province != null && !"".equals(province)){
			sql.append(" and province like '%"+province+"%'");
		}
		
		if(city != null && !"".equals(city)){
			sql.append(" and city like '%"+city+"%'");
		}
		
		sql.append(" and (groupid is null or groupid=0)");
		Query query = getSession().createSQLQuery(sql.toString());
		return (Integer) query.uniqueResult();
	}

	public void setGroupToWeixinUser(Integer groupid, String userIds) {
		String sql = "update t_mb_weixinuser set groupid="+groupid+" where userId in("+userIds+")";
		Query query = getSession().createSQLQuery(sql);
		query.executeUpdate();
	}

	public List<TMbWeixinUser> getWeixinUserByNickName(String nickname) {
		String hql = "from TMbWeixinUser where nickname='"+nickname+"'";
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	public List<TMbWeixinUser> getWeixinUsersByNickName(Map<String, Object> data) {
		//查询条件
		String nickname = (String) data.get("nickname");
		//分页条件
		int pageNumber = (Integer)data.get("pageNumber");
		int pageSize = (Integer)data.get("pageSize");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select row_number() over(order by d1.userId desc) as rowNum, d1.* from t_mb_weixinuser d1 where d1.nickname='"+nickname+"'");
		if(pageNumber != -1 && pageSize != -1){
			sql.append(") as users where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));	
		}else {
			sql.append(") as users where rowNum between 1 and 10");	
		}
		Query query = getSession().createSQLQuery(sql.toString()).addEntity(TMbWeixinUser.class);
		return query.list();
	}

	public Integer getWeixinUsersByNickNameCount(Map<String, Object> data) {
		//查询条件
		String nickname = (String) data.get("nickname");
		String sql = "select count(*) from t_mb_weixinuser where nickname='"+nickname+"'";
		Query query = getSession().createSQLQuery(sql);
		return (Integer) query.uniqueResult();
	}

	public boolean checkWeixinUser(String openid) {
		String hql = "from TMbWeixinUser where openid='"+openid+"'";
		Query query = getSession().createQuery(hql);
		TMbWeixinUser user = (TMbWeixinUser) query.uniqueResult();
		if(user == null){
			return true;
		}else {
			return false;
		}
	}

	public void save(TMbWeixinUser user) {
		Session session = getSession();
		session.save(user);
		session.flush();
	}
	
}
