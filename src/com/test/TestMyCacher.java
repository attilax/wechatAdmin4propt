package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import com.focusx.entity.TMbGroup;
import com.focusx.entity.TMbNews;
import com.focusx.service.BranchManagerService;
import com.focusx.util.Constant;
import com.focusx.util.MD5;
import com.focusx.util.MyCacher;
import com.focusx.util.MyHttpUtils;
import com.focusx.util.WxQRCode;

public class TestMyCacher {
	public static void main(String[] args) throws IOException, InterruptedException {
		
		//String accessToken = "WW8WFrEL-otqxv0AI64xgUco57-iJGixuY1kUCRiamH4ZxV6JzTgeLYI212pBvozgJdowue_D8pscnT7EyDsNdVvIHIt8v_AElW1moBRaEqSLpE1ImR8NSm-N0DLe0zwXJVNbvS8MJbcnImifiIzvQ";
		
		//Map<String, Integer> groupidAndTicket = new HashMap<String, Integer>();
		
	    //根据groupid获取关联的所有分组ID和名称
//		Map<Integer, String> groupidMap = new HashMap<Integer, String>();
//		groupidMap.put(1, "广州分公司");
//		groupidMap.put(8, "上海分公司");
//		groupidMap.put(9, "北京分公司");
//		groupidMap.put(10, "西安分公司");
//		groupidMap.put(11, "武汉分公司");
//		groupidMap.put(12, "成都分公司");
//		groupidMap.put(13, "重庆分公司");
//		
//		List<Integer> groupidlist = new ArrayList<Integer>();
//
//		if(groupidMap != null && groupidMap.size() > 0){//判断该groupid下是否有分公司
//			Set<Integer> set = groupidMap.keySet();
//			Iterator<Integer> iterator = set.iterator();
//			while(iterator.hasNext()){
//				groupidlist.add(iterator.next());
//			}
//			
//		}else {
//			groupidMap = new HashMap<Integer, String>();
//		}
		
//		//创建二维码ticket
//		WxQRCode Code_TICKET = new WxQRCode(WxQRCode.QR_FOREVER);
//		List<String> tickets = new ArrayList<String>();
//		List<Integer> groupidErrorList = new ArrayList<Integer>();
//		
//		for(int i = 0; i < groupidlist.size(); i++){
//				Map<Object,Object> paramsMap = new TreeMap<Object,Object>();
//				paramsMap.put("action_name", "QR_LIMIT_SCENE");
//				Map<Object,Object> actionInfo = new TreeMap<Object,Object>();				
//				Map<String,Integer> sencesMap = new TreeMap<String,Integer>();
//				sencesMap.put("scene_id", groupidlist.get(i));					
//				actionInfo.put("scene", sencesMap);					
//				paramsMap.put("action_info", actionInfo);
//				JSONObject json = JSONObject.fromObject(paramsMap);
//				String jsonStr = json != null ? json.toString() : "";
//				
//				Code_TICKET.setAccessToken(accessToken);
//				JSONObject ticketJson = MyHttpUtils.httpsRequest(Code_TICKET.toString(), Code_TICKET.getReqMethod(), jsonStr);
//				if(ticketJson != null && null != ticketJson.get("ticket")){//判断返回的是否正确的信息
//					String ticket = ticketJson.getString("ticket");
//					if(ticket != null && ticket != ""){
//						groupidAndTicket.put(ticket, groupidlist.get(i));
//						tickets.add(ticket);
//					}
//				}else {
//					groupidErrorList.add(groupidlist.get(i));
//				}
//			Thread.sleep(500);//增大时间差，减少失败次数
//		}
//		
//		//通过ticket换取二维码
//		WxQRCode Code_SHOW = new WxQRCode(WxQRCode.QR_SHOW);
//		List<Integer> getTicketimgResult = new ArrayList<Integer>();
//		List<String> groupnamelist = new ArrayList<String>();
//		List<String> groupnamErrorList = new ArrayList<String>();
//		
//		if(tickets.size() > 0){
//			try {
//				for(int i = 0; i < tickets.size(); i++){
//					Code_SHOW.setTicket(tickets.get(i));
//					String groupname = groupidMap.get(groupidAndTicket.get(tickets.get(i)));
//					File codeImage = new File(Constant.uploadPathCode+"\\"+groupname+".jpg");
//					if(codeImage.exists() && tickets.get(i).equals("exist")){
//						groupnamelist.add(groupname);
//						getTicketimgResult.add(Constant.ZERO);
//					}else{
//						//MyHttpUtils.httpRequest2返回的值有0、1,0代表成功，1代表失败
//						Integer ticketResult = MyHttpUtils.httpRequest2(Code_SHOW.toString(), groupname);
//						groupnamelist.add(groupname);
//						getTicketimgResult.add(ticketResult);
//						if(ticketResult == 1){
//							groupnamErrorList.add(groupname);
//						}
//						Thread.sleep(500);//增大时间差，减少失败次数
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		String key = "123";
//		List<TMbNews> list = new ArrayList<TMbNews>();
//		TMbNews news = new TMbNews();
//		news.setActivityId(1);
//		news.setKeyword("321");
//		news.setActName("true");
//		list.add(news);
//		MyCacher.getInstance().putCache(key, list);
		
		StringBuilder rawKey = new StringBuilder();
		rawKey.append(12).append("_").append(1);
			
		//指纹
		String md5Key = MD5.getMD5(rawKey.toString());
		List<TMbNews> newsList = (List<TMbNews>)MyCacher.getInstance().getCache(md5Key);
		System.out.println(newsList.size());
		for(TMbNews news : newsList){
			System.out.println(news.getId());
			System.out.println(news.getTitle());
			System.out.println(news.getMainText());
		}
		
//		TMbNews newsList = (TMbNews) MyCacher.getInstance().getCache(md5Key);
//		System.out.println(newsList.getActName());
//		System.out.println(newsList.getActivityId());
//		System.out.println(newsList.getKeyword());
		
	}
}
