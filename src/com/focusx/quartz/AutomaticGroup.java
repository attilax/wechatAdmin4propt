package com.focusx.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.focusx.entity.TMbGroup;
import com.focusx.entity.TMbTag;
import com.focusx.entity.TMbWeixinUser;
import com.focusx.util.Constant;

/**
 *  微信粉丝自动分组类，通过spring的Quartz定时执行，设置每天早上1:00运行initialized()方法，实现自动分组
 *  @author lws
 */
public class AutomaticGroup {
	
	protected static Logger logger = Logger.getLogger("AutomaticGroup"); 

	//定时执行的方法
	public void initialized() {
		//把sessionFactory放进Constant.sessionFactory属性里，让其他方法调用，对数据库进行查、改操作
        Constant.sessionFactory = (SessionFactory) ContextLoaderListener.getCurrentWebApplicationContext().getBean("sessionFactory");
        
		if(Constant.SERVER_MAP.isEmpty()){
			//初始化数据，只运行一次
			this.initDate();
		}
        this.grouping();
	}
	
	/**
	 *  实际自动分组方法，分批获取数据进行判断分组，每次循环暂停1s
	 */
	public void grouping(){
		logger.info("微信粉丝自动分组方法启动");
		try {
			GroupDaoImpl groupDaoImpl = new GroupDaoImpl();
			int time = 0;//循环次数，为了减少因为获取大量数据引起内存溢出
			Map<Integer,Integer> data = new HashMap<Integer, Integer>();//存放要修改的粉丝ID和分公司ID集合
			
			//当前数据库存在的未分组的粉丝总数
			Integer count = groupDaoImpl.getWeixinUserWithNullBranchCount();
			logger.info("总共有"+count+"个微信粉丝需要分组");
			
			//计算循环次数，默认每次循环获取500条
			if(count > Constant.everyTimeSize){
				if(count%Constant.everyTimeSize > 0){
					time = count/Constant.everyTimeSize + 1;
				}else {
					time = count/Constant.everyTimeSize;
				}	
			}else if(count > 0){
				time = 1;
			}else {
				time = 0;
			}
			logger.info("总共要循环"+time+"次");
			
			TMbGroup chongqing = groupDaoImpl.getBranchByGroupName("重庆分公司");//这个比较特殊，所以分开处理
			
			//循环获取粉丝信息判断更新，每次循环获取最多500条
			if(time != 0){
				long start = System.currentTimeMillis();
				logger.info("循环开始时间："+start+"ms");
				for(int i = 0; i < time; i++){
					List<TMbWeixinUser> list = groupDaoImpl.getWeixinUserByTime(i);
					logger.info("第"+i+"次循环获得个数："+list.size());
					for(int j = 0; j < list.size(); j++){
						TMbWeixinUser weixinUser = list.get(j);
						
						TMbGroup group = checkProvinceAndCity(weixinUser.getProvince(), weixinUser.getCity(), chongqing);
						if(group != null){
							data.put(weixinUser.getUserId(), group.getGroupid());
						}
					}
					try {
						Thread.sleep(1000);//每次循环相隔1s
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if(data != null && data.size() > 0){
					logger.info("通过自动分组成功的有"+data.size()+"个粉丝，未能按照省市分组的有"+(count-data.size())+"个粉丝");
					groupDaoImpl.updateToGroupId(data);
					logger.info("更新数据成功！");
				}else {
					logger.info("获取的数据无法根据程序自动分组，需要手动分组");
				}
				long end = System.currentTimeMillis();
				logger.info("循环结束时间："+end+"ms");
				logger.info("循环运行所用时间："+(end-start)/1000+"s");
				logger.info("微信粉丝自动分组方法结束");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("微信粉丝自动分组方法出现异常，分组失败", e);
		}
	}
	
	/**
	 *  根据省份和城市判断所属分公司，因为重庆分公司所包含的区域比较特殊，所以先判断特殊区域再判断常规区域
	 *  @param province   省份
	 *  @param city       城市
	 *  @param chongqing  重庆分公司对象
	 *  @return TMbGroup  分公司对象
	 */
	public TMbGroup checkProvinceAndCity(String province, String city, TMbGroup chongqing){
		
		TMbGroup retGroup = null;
		
		if(province != null || city != null){
			String specialCity = "广安/南充/达州/岳池/邻水/阆中";//重庆分公司
			if(city != null && specialCity.contains(city)){
				retGroup = chongqing;
			}else {
				boolean isFound = false;
				for(Map.Entry<String,TMbGroup> oneEntry:Constant.SERVER_MAP.entrySet()){
	
					if(oneEntry.getKey() != null && oneEntry.getValue() != null){
						List<TMbTag> tagList = oneEntry.getValue().getTagList();
						if(tagList != null && tagList.size() > 0){
							for(TMbTag oneTag:tagList){
								//判断省
								if(province.indexOf(oneTag.getTagName()) > -1){
									retGroup = oneEntry.getValue();
									isFound = true;
									break;
								}
								//判断市
								if(city.indexOf(oneTag.getTagName()) > -1){
									retGroup = oneEntry.getValue();
									isFound = true;
									break;
								}
								if(isFound){
									isFound = false;
									break;
								}
							}
						}
					}
			    }
			}
		}
		return retGroup;
	}

	//初始化Constant.SERVER_MAP数据
	public void initDate(){
		try {
			GroupDaoImpl groupDaoImpl = new GroupDaoImpl();
			List<TMbGroup> list = groupDaoImpl.getTopBranch();
			if(list != null && list.size() > 0){
				StringBuffer bKey = new StringBuffer();
				for(TMbGroup one : list){
					List<TMbTag> tagList = groupDaoImpl.getTagByGroupid(one.getGroupid());
					one.setTagList(tagList);
					bKey.setLength(0);
					bKey.append("group_").append(one.getGroupid());
					Constant.SERVER_MAP.put(bKey.toString(),one);
				}
				logger.info("初始化Constant.SERVER_MAP数据成功");
			}else {
				logger.info("获取数据为空，初始化Constant.SERVER_MAP数据失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("运行发生异常，初始化Constant.SERVER_MAP数据失败");
		}
	}

}
