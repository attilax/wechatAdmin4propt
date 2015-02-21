package com.focusx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;

import com.attilax.core;
import com.focusx.dao.AwardWeixinManagerDao;
import com.focusx.entity.TMbActAward;
import com.focusx.entity.TMbAwardWeixin;
import com.focusx.entity.TMbAwardWeixinResult;

public class AwardWeixinManagerDaoImpl implements AwardWeixinManagerDao{

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

	public Integer addAwardWeixin(TMbAwardWeixin awardweixin) {
		Session session = getSession();
		session.save(awardweixin);
		session.flush();
		return awardweixin.getId();
	}

	public List<TMbAwardWeixinResult> getAwardWeixinByActivity(
			Integer activityId) {
		String sql = "select CONVERT(varchar, d1.awardTime, 120 ) as awardTime,(select d2.awardName from t_mb_actaward d2 where d2.id=d1.awardId) as awardName," +
				"(select d3.nickname from t_mb_weixinuser d3 where d3.openid=d1.openId) as nickname," +
				"(select d2.remark from t_mb_actaward d2 where d2.id=d1.awardId) as remark,(select d3.UserID from t_mb_weixinuser d3 where d3.openid=d1.openId) as userId " +
				" 	, awardPhone, 	awardUserName,  	awardAddress, (SELECT d2.memberId     FROM [t_mb_weixinuser] d2     WHERE d2.openid=d1.openId) AS memberId  from t_mb_awardweixin d1 where activityId ="+activityId+" and awardId is not null and openId is not null order by d1.awardId";
		Query query = getSession().createSQLQuery(sql).addScalar("awardTime", StandardBasicTypes.STRING)
				.addScalar("awardName", StandardBasicTypes.STRING).addScalar("nickname", StandardBasicTypes.STRING)
				.addScalar("remark", StandardBasicTypes.STRING).addScalar("userId", StandardBasicTypes.INTEGER).addScalar("awardPhone", StandardBasicTypes.STRING).addScalar("awardUserName", StandardBasicTypes.STRING).addScalar("awardAddress", StandardBasicTypes.STRING).addScalar("memberId", StandardBasicTypes.STRING);
		core.log(sql);
		List temp = query.list();
		core.log("--query getAwardWeixinByActivity size:"+String.valueOf(temp.size()));
		List<TMbAwardWeixinResult> list = new ArrayList<TMbAwardWeixinResult>();
		if(temp != null && temp.size() > 0){
			for(int i =0; i < temp.size(); i++){
				Object[] object = (Object[])temp.get(i);
				TMbAwardWeixinResult result = new TMbAwardWeixinResult();
				result.setAwardTime((String)object[0]);
				result.setAwardName((String)object[1]);
				result.setNickname((String)object[2]);
				result.setAwardRemark((String)object[3]);
				result.setUserId((Integer)object[4]);
				result.setAwardPhone((String)object[5]);
				result.setAwardUserName((String)object[6]);
				result.setAwardAddress((String)object[7]);
				result.setMemcardno((String)object[8]);
				list.add(result);
			}
		}
		return list;
	}

	public void deleteAwardWeixinById(Integer id) {
		String sql = "delete from TMbAwardWeixin where id="+id;
		Query query = getSession().createQuery(sql);
		query.executeUpdate();
	}
	
	
}
