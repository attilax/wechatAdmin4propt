package com.focusx.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;

import com.focusx.dao.RoleManageDao;
import com.focusx.entity.TUserGrantPower;
import com.focusx.entity.TUserPower;
import com.focusx.entity.TUserRole;

public class RoleManagerDaoImpl implements RoleManageDao {
	
	protected static Logger logger = Logger.getLogger("RoleManagerDaoImpl");
	
	private SessionFactory sessionFactory;

	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public boolean deleteUserGrantpower(TUserGrantPower grantpower){
				
		boolean result = true;
		Session session = getSession();
		String hql = "delete from TUserGrantpower where OwnerType=? and OwnerId=? and IsOwn=?";
		
		try{
			Query query = session.createQuery(hql);
			query.setInteger(0, grantpower.getOwnerType());
			query.setInteger(1, grantpower.getOwnerId());
			query.setInteger(2, grantpower.getIsOwn());
			
			query.executeUpdate();
		}catch(Exception e){
			result = false;
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}finally{
			return result;
		}
	}
	
	
	public boolean deleteRolePower(final int id, final String[] powerId,final int ownerType) {
		boolean result = true;
		Session session = getSession();
		try{
			session.doWork(new Work(){
				public void execute(Connection conn) throws SQLException {
					String sql = "delete from t_user_grantpower where OwnerType=? and OwnerId=? and PowerId=? and IsOwn=?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					for(int i = 0;i<powerId.length;i++){
						pstmt.setInt(1, ownerType);//拥有者类型(1,2,3)角色,部门,人
						pstmt.setInt(2, id);
						pstmt.setInt(3, Integer.parseInt(powerId[i].trim()));
						pstmt.setInt(4, 1);//是否拥有此权限(0没有,1有)
						
						pstmt.addBatch();
						
						if(i%500==0 && i>0){//每500条处理一次
							pstmt.executeBatch();
							pstmt.clearBatch();
						}
					}

				}
			});
		}catch(Exception e){
			result = false;
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}finally{
			return result;
		}
		
	}

	public boolean insertGrantPower(final int id, final String[] powerId,final int ownerType) {
		
		boolean result = true;
		Session session = getSession();
		
		try{
			session.doWork(new Work(){
				public void execute(Connection conn) throws SQLException {
					String sql = "insert into t_user_grantpower(OwnerType,OwnerId,PowerId,IsOwn) values(?,?,?,?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					for(int i=0;i<powerId.length;i++){
						pstmt.setInt(1, ownerType);//拥有者类型(1,2,3)角色,部门,人
						pstmt.setInt(2, id);
						pstmt.setInt(3, Integer.parseInt(powerId[i].trim()));
						pstmt.setInt(4, 1);//是否拥有此权限(0没有,1有)
						
						pstmt.addBatch();
						
						if(i%500==0 && i>0){//每500条处理一次
							pstmt.executeBatch();
							pstmt.clearBatch();
						}
					}
					pstmt.executeBatch();//执行批处理
				}
			});
		}catch(Exception e){
			result = false;
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}finally{
			return result;
		}
		
	}
	
	
	public List<TUserGrantPower> getGrantpowerByOwner(int ownerId){
		//加 ownerType
		String hql = "from TUserGrantpower where ownerId ="+ownerId;
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	
	public List<TUserPower> getAllPower(){
		//加 SubSystemType int(11)系统类型(1ICIP,2ICIP报表,3工作流,4业务报表,300=BS工作流平台,310=GIS系统,311=微博微信系统,312=IM系统)
		String hql = "from TUserPower order by id";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	
	
	public List<TUserRole> getAllRoles(){
		StringBuffer hql = new StringBuffer();
		
		hql.append("from TUserRole ");
		hql.append("order by id asc");
		
		Query query = getSession().createQuery(hql.toString());

		return query.list();
	}
	public boolean deleteRoles(final String[] roleIds) {
		boolean result = true;
		Session session = getSession();
		
		try{
			session.doWork(new Work(){
				public void execute(Connection conn) throws SQLException {
					String sql = "delete from t_user_role where id = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					for(int i = 0;i<roleIds.length;i++){
						pstmt.setInt(1, Integer.parseInt(roleIds[i].trim()));
						pstmt.addBatch();
					}
					pstmt.executeBatch();
				}
			});
		}catch(Exception e){
			result = false;
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}finally{
			return result;
		}
	}

	public TUserRole getRoleById(int roleId) {
		String hql = "from TUserRole where id ="+roleId;
		Query query = getSession().createQuery(hql);
		TUserRole role = (TUserRole)query.uniqueResult();
		return role;
	}

	public Number getRoleNum(Map<String, Object> data) {
		//查询条件
		String name = (String) data.get("name");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(ID) from t_user_role ");
		
		if(name != null && !name.equals("")){
			sql.append(" where name like '%"+name+"%'");
		}
		
		Query query = getSession().createSQLQuery(sql.toString());
		Number num= (Number)query.uniqueResult();
		return num;
	}

	public List<TUserRole> getRoles(Map<String, Object> data) {
		//查询条件
		String name = (String) data.get("name");
		
		//分页条件
		int pageNumber = (Integer)data.get("pageNumber");//第几页
	    int pageSize = (Integer)data.get("pageSize");//每页记录数
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select row_number() over(order by ID desc) as rowNum,* from t_user_role ");
		
		if(name != null && !name.equals("")){
			sql.append(" where name like '%"+name+"%'");
		}
		if(pageNumber!=-1 && pageSize!=-1){
			sql.append(" )as roles where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));
		}else{
			sql.append(" )as roles where rowNum between 1 and 10");
		}
		Query query = getSession().createSQLQuery(sql.toString()).addEntity(TUserRole.class);
		
		return query.list();
	}

	public Number insertRole(TUserRole role) {
		Number id = -1;
		Session session = getSession();
		session.save(role);
		session.flush();
		id = role.getId();
		return id;
	}

	public boolean updateRole(TUserRole role) {
		boolean result = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			String hql = "update t_user_role set name='"+role.getName()+"',alterTime='"+format.format(role.getAlterTime())
					+"',remark='"+role.getRemark()+"' where id="+role.getId();
			Query query = getSession().createSQLQuery(hql);
			query.executeUpdate();
		}catch(Exception e){
			result = false;
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}finally{
			return result;
		}

	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	

}
