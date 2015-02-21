package com.focusx.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import com.focusx.dao.BranchManagerDao;
import com.focusx.entity.TMbGroup;

public class BranchManagerDaoImpl implements BranchManagerDao{
	
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
	
	//用于分组列表页，列表显示
	@SuppressWarnings("unchecked")
	public List<TMbGroup> getGroups(Map<String, Object> data) {
		Session session = getSession();
		//获取分页条件
		int pageNumber = (Integer)data.get("pageNumber");//第几页
	    int pageSize = (Integer)data.get("pageSize");//每页记录数
	    
	    //获取查询条件
	    String groupname = (String)data.get("groupname");//分组名称
	    Integer groupid = (Integer)data.get("groupid");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select groupid,groupname,convert(varchar, createtime, 120 ) as createtime,weixinuserCount,remark from (select row_number() over(order by createtime desc) as rowNum," +
				"d1.groupid,d1.groupname,d1.createtime,d1.remark," +
				"(select count(d2.UserID) from t_mb_weixinuser d2 where d2.groupid=d1.groupid)as weixinuserCount from t_mb_group d1 where 1=1 ");
	
		//加入查询条件
		if(!"".equals(groupname) && groupname!=null){
			sql.append(" and groupname like '%"+groupname+"%'");
		}
		if(groupid != null && groupid > 0){
			sql.append("  and parentId="+groupid+" or groupid="+groupid);
		}

		//加入分页
		if(pageNumber!=-1 && pageSize!=-1){
			sql.append(") as groups where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));
		}else {
			sql.append(") as groups where rowNum between 1 and 10");
		}
		Query query = session.createSQLQuery(sql.toString()).addScalar("groupid", StandardBasicTypes.INTEGER)
				.addScalar("groupname", StandardBasicTypes.STRING).addScalar("createtime", StandardBasicTypes.STRING)
				.addScalar("weixinuserCount", StandardBasicTypes.INTEGER).addScalar("remark", StandardBasicTypes.STRING);
		List temp = query.list();
		List<TMbGroup> groups = new ArrayList<TMbGroup>();
		if(temp != null && temp.size() > 0){
			for(int i =0; i < temp.size(); i++){
				Object[] object = (Object[])temp.get(i);
				TMbGroup group = new TMbGroup();
				group.setGroupid((Integer)object[0]);
				group.setGroupname((String)object[1]);
				group.setCreatetimeString((String)object[2]);
				group.setWeixinuserCount((Integer)object[3]);
				group.setRemark((String)object[4]);
				groups.add(group);
			}
		}
		return groups;
	}

	//获取分组总和
	public Number getGroupCount(Map<String, Object> data) {
		Session session = getSession();
	    
	    //获取查询条件
	    String groupname = (String)data.get("groupname");//分组名称
	    Integer groupid = (Integer)data.get("groupid");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select Count(groupid) from t_mb_group where 1=1 ");
		
		//加入查询条件
		if(!"".equals(groupname) && groupname!=null){
			sql.append(" and groupname like '%"+groupname+"%'");
		}
		if(groupid != null && groupid > 0){
			sql.append("  and parentId="+groupid+" or groupid="+groupid);
		}
		
		Query query = session.createSQLQuery(sql.toString());		
		return (Number)query.uniqueResult();
	}
	
	//新增分组
	public Number add(TMbGroup group){
		Session session = getSession();
		session.save(group);
		session.flush();
		return group.getGroupid();
	}
	
	//删除分组
	public void delete(Integer groupid){
		String hql = "delete from TMbGroup where groupid= " + groupid;
		getSession().createQuery(hql).executeUpdate();
	}
	
	//更新分组
	public void update(TMbGroup group){
		getSession().update(group);
	}
	
	//检查分组
	public String getGroupByGroupname(String groupname){
		String hql = "from TMbGroup where groupname = '"+groupname+"'";
		Query query = getSession().createQuery(hql);
		TMbGroup group = (TMbGroup) query.uniqueResult();
		if(group == null){
			return "true";
		}else{
			return "false";
		}
	}
	
	/**
	 *  删除已存在但取消的联系人关联到某个分组中
	 */
	public void deleteGTWeixinuser(String userids, Integer groupid){
		Session session = getSession();
		String hql = "delete from TMbGroupToWeixinuser where groupid="+groupid+" and userid in ("+userids+")";
		session.createQuery(hql).executeUpdate();
	}
	
	/**
	 *  判断分组下是否有联系人
	 */
	public boolean checkGroup(Integer groupid){
		String hql = "from TMbGroupToContact where groupid="+groupid;
		Query query = getSession().createQuery(hql);
		List list = query.list();
		if(list.size() == 0){
			return true;
		}else{
			return false;
		}
	}

	//统计有联系人的分组数量
	public Number getGroupsToPublishCount() {
		String sql = "select count(*) from (select count(*) as countids from t_mb_group g join t_mb_grouptoweixinuser c on g.groupid=c.groupid group by g.groupid)as TMbGroup";
		Query query = getSession().createSQLQuery(sql);
		return (Number)query.uniqueResult();
	}

	public List<String> getAllContactBygroupid(String groupids) {
		String[] ids = groupids.split(",");
		StringBuffer temp = new StringBuffer();
		for(String id : ids){
			if(!id.equals("")){
				temp.append(id+",");
			}
		}
		String list = "";
		Query query = null;
		List<String> openids = null;
		if(temp.lastIndexOf(",")>0){
			list = temp.toString().substring(0, temp.lastIndexOf(","));
			String sql = "select openid from t_mb_contact where contactId in (select contactId from t_mb_grouptoweixinuser where groupid in("+list+"))";
			query = getSession().createSQLQuery(sql);
			openids = query.list();
		}
		return openids;
	}
	
	public List<TMbGroup> getTreeByParentId(int parentId){
		String hql = "from TMbGroup where parentId="+parentId;
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	public List<Integer> getAllGroupIdByGroupId(Integer groupid) {
		String hql = "from TMbGroup where parentId="+groupid;
		Query query = getSession().createQuery(hql);
		List<TMbGroup> temp = query.list();
		if(temp != null && temp.size() > 0){
			List<Integer> list = new ArrayList<Integer>();
			for(TMbGroup group : temp){
				list.add(group.getGroupid());
			}
			return list;
		}else{
			return null;
		}

	}

	public List<TMbGroup> getAllGroup() {
		String hql = "from TMbGroup where 1=1";
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	public boolean checkParent(Integer groupid) {
		String hql = "from TMbGroup where parentId="+groupid;
		Query query = getSession().createQuery(hql);
		if(query.list().size() == 0){
			return false;
		}else{
			return true;
		}
		
	}

	public Number getContactsCountByGroupid(Map<String, Object> data) {
		return null;
	}

	public List<TMbGroup> getGroupsToPublish(Map<String, Integer> data) {
		return null;
	}
	
	//获取分组
	public TMbGroup getGroup(Integer groupid){
		String hql = "from TMbGroup where groupid="+groupid;
		Query query = getSession().createQuery(hql);
		TMbGroup group = (TMbGroup) query.uniqueResult();
		return group;
	}

	public List<TMbGroup> getAllOneGroup(Map<String, Object> data) {
		Session session = getSession();
		//获取分页条件
		int pageNumber = (Integer)data.get("pageNumber");//第几页
	    int pageSize = (Integer)data.get("pageSize");//每页记录数
	    
	    //获取查询条件
	    String groupname = (String)data.get("groupname");//分组名称
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select row_number() over(order by createtime desc) as rowNum,* from t_mb_group where parentId=0 ");
	
		//加入查询条件
		if(!"".equals(groupname) && groupname!=null){
			sql.append(" and groupname like '%"+groupname+"%'");
		}

		//加入分页
		if(pageNumber!=-1 && pageSize!=-1){
			sql.append(") as groups where rowNum between "+pageNumber+" and "+(pageNumber+pageSize-1));
		}else {
			sql.append(") as groups where rowNum between 1 and 10");
		}
		Query query = session.createSQLQuery(sql.toString()).addEntity(TMbGroup.class);
		return query.list();
	}

	public Number getAllOneGroupCount(Map<String, Object> data) {
		Session session = getSession();
	    
	    //获取查询条件
	    String groupname = (String)data.get("groupname");//分组名称
		
		StringBuffer sql = new StringBuffer();
		sql.append("select Count(groupid) from t_mb_group where parentId=0 ");
		
		//加入查询条件
		if(!"".equals(groupname) && groupname!=null){
			sql.append(" and groupname like '%"+groupname+"%'");
		}
		
		Query query = session.createSQLQuery(sql.toString());		
		return (Number)query.uniqueResult();
	}

	public Map<Integer, String> getAllGroupByGroupId(Integer groupid) {
		String hql = "from TMbGroup where parentId="+groupid;
		Query query = getSession().createQuery(hql);
		List<TMbGroup> temp = query.list();
		if(temp != null && temp.size() > 0){
			Map<Integer, String> data = new HashMap<Integer, String>();
			for(TMbGroup group : temp){
				data.put(group.getGroupid(),group.getGroupname());
			}
			return data;
		}else{
			return null;
		}
	}

	public boolean isParent(Integer groupid) {
		String sql = "select count(*) from t_mb_group where parentId="+groupid;
		Query query = getSession().createSQLQuery(sql);
		int num = (Integer) query.uniqueResult();
		if(num > 0){
			return true;
		}else {
			return false;
		}
	}

	public List<Object> getWeixinUsersByGroupid(Integer groupid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select " +
				"case when subscribe=0 then '否' when subscribe=1 then '是' end as subscribe," +
				"openid,nickname,CONVERT(varchar, subscribe_time, 120 ) as subscribetime," +
				"case when sex=1 then '男'  when sex=2 then '女' else '未知' end as sex," +
				"country,province,city,case when language='zh_CN' then '简体中文' else '其他' end as language," +
				"headimgurl from t_mb_weixinuser where 1=1");
		sql.append(" order by UserID desc");
		Query query = getSession().createSQLQuery(sql.toString()).addScalar("subscribe", StandardBasicTypes.STRING)
				           .addScalar("openid", StandardBasicTypes.STRING).addScalar("nickname", StandardBasicTypes.STRING)
				           .addScalar("subscribetime", StandardBasicTypes.STRING).addScalar("sex", StandardBasicTypes.STRING)
				           .addScalar("country", StandardBasicTypes.STRING).addScalar("province", StandardBasicTypes.STRING)
				           .addScalar("city", StandardBasicTypes.STRING).addScalar("language", StandardBasicTypes.STRING)
				           .addScalar("headimgurl", StandardBasicTypes.STRING);
		List temp = query.list();
		List<Object> list = new ArrayList<Object>();
		if(temp != null && temp.size() > 0){
			for(int i =0; i < temp.size(); i++){
				Object[] object = (Object[])temp.get(i);
				list.add(object);
			}					
		}
		return list;
	}

	public List<Object> getWeixinCountByGroupid(Integer groupid) {
		String sql = "select COUNT(*) from t_mb_weixinuser where groupid in(select d1.groupid from t_mb_group d1 where d1.parentId = "+groupid+" " +
				"union select d2.groupid from t_mb_group d2 where d2.groupid="+groupid+")";
		Query query = getSession().createSQLQuery(sql);
		String sql2 = "select groupname from t_mb_group where groupid="+groupid;
		Query query2 = getSession().createSQLQuery(sql2);
		List<Object> list = new ArrayList<Object>();
		list.add((Integer)query.uniqueResult());
		list.add((String)query2.uniqueResult());
		return list;
	}

	public TMbGroup getParentGroup(Integer groupid) {
		String hql = "from TMbGroup where groupid=(select parentId from TMbGroup where groupid="+groupid+")";
		Query query = getSession().createQuery(hql);
		return (TMbGroup) query.uniqueResult();
	}

	public TMbGroup getBranchByGroupName(String groupname) {
		String hql = "from TMbGroup where groupname='"+groupname+"'";
		Query query = getSession().createQuery(hql);
		return (TMbGroup) query.uniqueResult();
	}

	public List<TMbGroup> getTopBranch() {
		String sql = "select groupid,groupname from t_mb_group where parentId=0";
		Query query = getSession().createSQLQuery(sql).addScalar("groupid", StandardBasicTypes.INTEGER)
				.addScalar("groupname", StandardBasicTypes.STRING).setResultTransformer(Transformers.aliasToBean(TMbGroup.class));
		return query.list();
	}

	public List<TMbGroup> getStoresByGroupid(Integer parentId) {
		String sql = "select groupid, groupname from t_mb_group where parentId="+parentId;
		Query query = getSession().createSQLQuery(sql).addScalar("groupid", StandardBasicTypes.INTEGER)
				.addScalar("groupname", StandardBasicTypes.STRING).setResultTransformer(Transformers.aliasToBean(TMbGroup.class));
		return query.list();
	}

}
