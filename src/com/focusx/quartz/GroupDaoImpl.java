package com.focusx.quartz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import com.focusx.entity.TMbGroup;
import com.focusx.entity.TMbTag;
import com.focusx.entity.TMbWeixinUser;
import com.focusx.util.Constant;

public class GroupDaoImpl {
	
	public Session getSession() {
		if (Constant.sessionFactory != null)
			return Constant.sessionFactory.openSession();
		return null;
	}
	
	public List<TMbGroup> getTopBranch(){
		Session session = getSession();
		if(session != null){
			try {
				String sql = "select groupid,groupname from t_mb_group where parentId=0";
				Query query = session.createSQLQuery(sql).addScalar("groupid", StandardBasicTypes.INTEGER)
						.addScalar("groupname", StandardBasicTypes.STRING).setResultTransformer(Transformers.aliasToBean(TMbGroup.class));
				return query.list();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				session.close();
			}
		}
		return null;
	}
	
	public List<TMbTag> getTagByGroupid(Integer groupid){
		Session session = getSession();
		if(session != null){
			try {
				String hql = "from TMbTag where groupid="+groupid;
				Query query = session.createQuery(hql);
				return query.list();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				session.close();
			}
		}
		return null;
	}
	
	public Integer getWeixinUserWithNullBranchCount(){
		Session session = getSession();
		if(session != null){
			try {
				String sql = "select count(*) from t_mb_weixinuser where subscribe=1 and groupid is null or groupid=0";
				Query query = session.createSQLQuery(sql);
				return (Integer) query.uniqueResult();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				session.close();
			}
		}
		return 0;
	}
	
	public List<TMbWeixinUser> getWeixinUserByTime(Integer time) {
		Session session = getSession();
		if(session != null){
			try {
				StringBuffer sql = new StringBuffer();
				sql.append("select userId,province,city from " +
						"(select row_number() over(order by d1.UserID desc) as rowNum, d1.UserID,d1.province,d1.city from t_mb_weixinuser d1 where" +
						" d1.subscribe=1 and d1.groupid is null or d1.groupid=0) as weixinuser");
				if(time >= 0){
					sql.append(" where rowNum between "+(time*Constant.everyTimeSize)+" and "+((time+1)*Constant.everyTimeSize-1));
				}else {
					sql.append(" where rowNum between 1 and 500");
				}
				Query query = session.createSQLQuery(sql.toString()).addScalar("userId", StandardBasicTypes.INTEGER)
						.addScalar("province", StandardBasicTypes.STRING).addScalar("city", StandardBasicTypes.STRING)
						.setResultTransformer(Transformers.aliasToBean(TMbWeixinUser.class));
				return query.list();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				session.close();
			}
		}
		return null;
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
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			session.close();
		}
	}
	
	public TMbGroup getBranchByGroupName(String groupname) {
		Session session = getSession();
		if(session != null){
			try {
				String hql = "from TMbGroup where groupname='"+groupname+"'";
				Query query = getSession().createQuery(hql);
				return (TMbGroup) query.uniqueResult();
			} catch (Exception e) {
				e.printStackTrace();
			}  finally{
				session.close();
			}
		}
		return null;
	}

}
