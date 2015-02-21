/**
 * @author attilax 老哇的爪子
	@since  2014-5-16 下午03:48:07$
 */
package com.foksda.mass;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ecs.html.Option;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.attilax.core;
import com.attilax.retry;

import static  com.attilax.core.*;

import com.attilax.collection.listUtil;
import com.attilax.io.GetFileSize;
import com.attilax.io.filex;
import com.attilax.io.pathx;
import com.attilax.net.HttpRequest;
import com.attilax.net.requestImp;
import com.attilax.net.websitex;
import com.attilax.spri.SpringUtil;
import com.attilax.util.Func_4SingleObj;
import com.attilax.util.Func_wzPre;
import com.attilax.util.randomx;
import com.attilax.util.tryX;
import com.attilax.wechat.WeixinUtil;
import com.attilax.wechat.wechatUtil;
import com.focusx.entity.TMbNews;
import com.focusx.entity.weixin.pojo.AccessToken;
import com.focusx.entity.weixin.sendMessage.Article;
import com.focusx.entity.weixin.sendMessage.News;
import com.focusx.entity.weixin.sendMessage.NewsMessage;
import com.focusx.listener.TokenThread;
import com.focusx.service.MaterialManagerService;
import com.focusx.service.impl.Fun_4reduce;
import com.focusx.util.ConfigService;
import com.focusx.util.Constant;
import com.focusx.util.MessageUtil;

/**
 * @author attilax 老哇的爪子
 *@since 2014-5-16 下午03:48:07$
 */
@SuppressWarnings("all")
public class groupsendSvs {

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-5-16 下午03:48:10$
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子 下午03:48:10 2014-5-16

		groupsendSvs c = new groupsendSvs();
		// System.out.println(ACCESS_TOKEN());
		// System.out.println(groupsendSvs.groups_sltFmt());
		// System.out.println(c.uploadImgtxtMsgThumib());
		// System.out.println(new groupsendSvs().uploadTxt());

	//	c.media_id = "tYLgVJTTwuBqz2o00k1wCEVPYiog_zisDHnOW-pV3s-TbaKZ3MpSDj0IWSXEzgte";
		// System.out.println(c.groupSend(c.media_id, "106"));

		requestImp ri = new requestImp();
		ri.setParam("groupid", "106");
		ri.setParam("Greetings", "test测试");
		//	System.out.println(c.groupSend(ri));
//		String groupid = req.getParameter("toGrpID");
//		String newsId = req.getParameter("newsId");
		ri.setParam("toGrpID", "106");//106
		ri.setParam("newsId", "204");//204 multiPictxt
		System.out.println(c.groupSend_MsgID_GrpID(ri));

	}

	/**
	 * 
	@author attilax 老哇的爪子
		@since  2014-5-28 下午12:03:32$
	
	 * @param req
	 * @return
	 */
	public String groupSend(final HttpServletRequest req) {
		
		
		return new tryX<String>(){
			
//			{
//				this.para1x
//			}

			@Override
			public String item(Object t) throws Exception {
				// attilax 老哇的爪子  上午11:55:44   2014-5-28 
			//	this.para1x
				return groupSendNosecurywrap(req);
			}}.$("");

	}

	/**
	 * 
	@author attilax 老哇的爪子
		@since  2014-5-28 下午12:03:29$
	
	 * @param req
	 * @return
	 */
	private String groupSendNosecurywrap(HttpServletRequest req) {
		String groupid = req.getParameter("groupid");
		String txt = req.getParameter("Greetings");
		core.log("--send groupid n txt::" + groupid + "---" + txt);
		Acontent = txt;
		// return "";
		uploadImgtxtMsgThumib();
		uploadTxt();

		String groupSendRzt = groupSend(this.media_id, groupid);
		core.log("--groupSendRzt:" + groupSendRzt);
		return groupSendRzt;
	}
	/**
	 * {
  "msgtype": "news",
  "news": {"articles": [  {
    "description": "新品上市--抢鲜购，4月初夏新品等你购！",
    "picurl": "b52aff51-4218-4e47-a78d-e6b4505917e6.jpg",
    "title": "新品上市",
    "url": "html/news/256379c9-361b-4bb2-b73a-f63239651ebe.html"
  }]},
  "touser": ""
}
	 * 
	 * 
	 * -ori json obj:{
  "msgtype": "news",
  "news": {"articles": [  {
    "description": "摘要摘要",
    "picurl": "0e5821f8-abbb-4980-8c3e-963eb8dcb613.jpg",
    "title": "标题标题",
    "url": "html/news/c846c2dd-fa16-4e9e-ba6f-95bac1d98200.html"
    
    
	@author attilax 老哇的爪子
		@since  2014-5-26 上午11:06:15$
	
	 * @param req
	 * @return
	 */
	public String groupSend_MsgID_GrpID(HttpServletRequest req) {
		// if(!req.equals(null))
		// {
		// log("--o528 tt test throw RuntimeException");
		// throw new RuntimeException("tt");
		// }
		String groupid = req.getParameter("toGrpID");
		String newsId = req.getParameter("newsId");
		core.log("--send groupid n newsId::" + groupid + "---" + newsId);

		// 获取图文信息 if singlePictxt newsList.size==0
		MaterialManagerService materialManagerService = (MaterialManagerService) SpringUtil
				.getBean("materialManagerService");
		List<TMbNews> newsList = materialManagerService.getMoreMaterial(Integer
				.parseInt(newsId));
		// o66
		final Map mp = listUtil.reduceO4d(newsList,
				new HashMap<String, String>(), new Fun_4reduce<TMbNews, Map>() {

					@Override
					public Map $(TMbNews o, Map lastRetOBj) {
						// attilax 老哇的爪子 上午10:02:35 2014-6-6
						lastRetOBj.put(o.getCoverPage(), o.getMainText());
						return lastRetOBj;
					}
				});

		// return "";o65 change
		//o69 send multi thumb
		final Map mp_thumb = listUtil.reduceO4d(newsList,
				new HashMap<String, String>(), new Fun_4reduce<TMbNews, Map>() {

				
					@Override
					public Map $(TMbNews o, Map lastRetOBj) {
						// attilax 老哇的爪子 上午10:02:35 2014-6-6
						// TMbNews tbnews = newsList.get(0);
						// String imgPath = pathx.webAppPath()
						// + "\\image/news/cover/" + o.getCoverPage();
						final String imgPath = "D:\\gialen\\image.war\\news\\cover\\"
								+ o.getCoverPage();
						log("--uppic :" + imgPath);
						checkImgSize(imgPath);
						
						
						List<Boolean> li=new ArrayList<Boolean>();
						String thumid = new retry<String>(3, li) {

							@Override
							public Boolean item(Object t) throws Exception {
								// attilax 老哇的爪子 下午03:43:07 2014-6-9
								try {
									this.result = uploadImgtxtMsgThumib(imgPath);
									return true;
								} catch (Exception e) {
									this.result = e.getMessage();
									return false;
								}
							
								// return true;
							}

						}.$();
						if(!li.get(0))
							throw new RuntimeException("errO69:up thumid err"+thumid);
//						String thumid = new tryX<String>() {
//
//							@Override
//							public String item(Object t) throws Exception {
//								// attilax 老哇的爪子 下午03:12:58 2014-6-9
//								return uploadImgtxtMsgThumib(imgPath);
//							}
//
//						}.$(thumb_media_id);
						if(thumid.length()==0)
							core.warn("--up thumb is empty:"+imgPath);

						lastRetOBj.put(o.getCoverPage(), thumid);
						return lastRetOBj;
					}

					private void checkImgSize(String imgPath) {
						// attilax 老哇的爪子 下午04:51:49 2014-6-9
						long l = GetFileSize.getFileSizeO69(imgPath);

						core.log("--filesize:" + String.valueOf(l) + ","
								+ imgPath);
						if (l > 64000)
							throw new RuntimeException(
									"errO691:图片太大超过64k,file size big than 64k,"
											+ String.valueOf(l) + "," + imgPath);
					}
				});
	

		// 组装发送信息对象
		NewsMessage newsMessage = buildNewsMessage(newsList);

		final JSONObject json = JSONObject.fromObject(newsMessage);
		String sendJsonData = json.toString(2);
		log("--ori json obj:" + sendJsonData);

		// String s=("aa"){
		// return "t";
		// }("");

		com.attilax.corePkg.JSONArray jarr;

		{
			// jarr= listUtil.mapxx();
			JSONArray a = json.getJSONObject("news").getJSONArray("articles");
			log(a.toString(2));
			jarr = listUtil
					.map_genericO5(
							a,
							new Func_4SingleObj<JSONObject, com.attilax.corePkg.JSONObject>() {

								@Override
								public com.attilax.corePkg.JSONObject invoke(
										JSONObject o2) {
									// attilax 老哇的爪子 下午12:12:00 2014-5-26
									com.attilax.corePkg.JSONObject o = new com.attilax.corePkg.JSONObject();
									String convertPicName =filex.getFileName( o2
											.getString("picurl"));
									o.put("thumb_media_id", mp_thumb
											.get(convertPicName));
									// put("author", "attilax");
									o.put("title", o2.getString("title"));

									o.put("content", mp.get(convertPicName));
									o
											.put("digest", o2
													.getString("description"));
									// o.put("content_source_url",o2.)
									core.log(o.toString(2));
									return o;
								}
							}, com.attilax.corePkg.JSONArray.class);

			log("--o5o myArr:" + jarr.toString(2));
		}

		// Acontent =sendJsonData;
		uploadTxt_editorVer(jarr);
		// if(sendJsonData!="a")return "";

		String groupSendRzt = "";
		 groupSendRzt = groupSend(this.media_id, groupid);
		core.log("--groupSendRzt:" + groupSendRzt);
		return groupSendRzt;

	}
	
	
	//构建图文消息
	public static NewsMessage buildNewsMessage(List<TMbNews> newsList){
	//	if(openId != null && !"".equals(openId) && newsList != null){
			//"https://open.weixin.qq.com/connect/oauth2/authorize?appid=xxxx&redirect_uri=&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
	        
			NewsMessage clientMsg = new NewsMessage();
			//clientMsg.setTouser(openId);
			clientMsg.setMsgtype(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			News news = new News();
			List<Article> artList = new ArrayList<Article>();
			for(TMbNews oneNews:newsList){
				Article oneArt = new Article();
				oneArt.setDescription(oneNews.getDescription());
				oneArt.setTitle(oneNews.getTitle());
				//html路径
				StringBuilder url = new StringBuilder();
				//图片路径
				StringBuilder picUrl = new StringBuilder();
				
				url.append(Constant.WEB_URL).append("html/news/").append(oneNews.getHtmlName());
				
				logger.info(url.toString());
				
				picUrl.append(Constant.NEWS_WEB_SITE);
				picUrl.append(Constant.NEWS_IMAGE_PATH);
				picUrl.append(oneNews.getCoverPage());
				
				oneArt.setUrl(url.toString());
				oneArt.setPicurl(picUrl.toString());
				artList.add(oneArt);
			}
		    news.setArticles(artList);
		    clientMsg.setNews(news);
		    return clientMsg;
		///}
	//	return null;
	}

	/**
	 * { "filter":{ "group_id":"2" }, "mpnews":{
	 * "media_id":"123dsdajkasd231jhksad" }, "msgtype":"mpnews" }
	 * 
	 * @author attilax 老哇的爪子
	 * @since 2014-5-19 下午02:42:44$
	 * 
	 * @return "errcode":0, "errmsg":"send job submission success",
	 *         "msg_id":34182 }
	 */
	public String groupSend(final String media_id, final String group_id) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="
				+ ACCESS_TOKENO69();

		final JSONObject o1 = new com.attilax.corePkg.JSONObject() {
			{
				put("filter", new com.attilax.corePkg.JSONObject() {
					{
						put("group_id", group_id);
					}
				}.$());
				put("mpnews", new com.attilax.corePkg.JSONObject() {
					{
						put("media_id", media_id);
					}
				}.$());

				put("msgtype", "mpnews");

			}

		}.$();
		core.log("----o5h2");
		core.log(url);
		String r = HttpRequest.sendPost(url, o1.toString());
		core.log(r);
		return r;
	}

	// attilax 老哇的爪子 下午03:48:08 2014-5-16

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-5-16 下午04:12:51$
	 * 
	 *        { "groups": [ { "id": 0, "name": "未分组", "count": 72596 }, { "id":
	 *        1, "name": "黑名单", "count": 36 },
	 * 
	 * 
	 *        <select id='groupid'><option value='0'>默认组</option><option
	 *        value='1'>屏蔽组</option><option value='2'>星标组</option><option
	 *        value='100'></option><option value='101'>客户朋友</option><option
	 *        value='102'>呼叫中心</option><option value='103'>群发1组</option><option
	 *        value='104'>群发2组</option><option value='105'>微友</option></select>
	 * 
	 * @return
	 */
	public static String groups_sltFmt() {

		return new tryX<String>() {

			@Override
			public String item(Object t) throws Exception {
				// attilax 老哇的爪子 上午11:34:34 2014-5-28
				// attilax 老哇的爪子 下午04:12:51 2014-5-16
				retryRzt rzt=new retryRzt();
				String access_TOKEN = new retry<String>(5, rzt) {

					@Override
					public Boolean item(Object t) throws Exception {
						// attilax 老哇的爪子 下午11:49:37 2014年6月9日
						String tk = ACCESS_TOKEN();
						;
						String r = WeixinUtil.queryMenu(tk);
						JSONObject jo = JSONObject.fromObject(r);

						if (jo.get("errcode") == null) {
							this.setResult(tk);
							return true;
						}
						// else
						tk = fromWeb();
						String r2 = WeixinUtil.queryMenu(tk);
						JSONObject jo2 = JSONObject.fromObject(r2);
						if (jo2.get("errcode") == null) {
							this.setResult(tk);
							return true;
						}

						{
							this.setResult(r2);

							return false;
						}

					}

					private String fromWeb() {
						// attilax 老哇的爪子 上午12:08:31 2014年6月10日
						// 第三方用户唯一凭证
						ConfigService configService = new ConfigService();
						String appId = configService.getWxProperty("APPID");
						// 第三方用户唯一凭证密钥
						String appSecret = configService
								.getWxProperty("APPSECRET");
						// 调用接口获取access_token
						AccessToken accessToken = WeixinUtil.getAccessToken(
								appId, appSecret);
						return accessToken.getToken();

					}

				}.$O69();
				core.log("--get acc token rzt:"+ rzt.rztFlag.toString());		
				core.log("--get acc token rzt:"+ String.valueOf( rzt.result));	
						
				String url = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token="
						+ access_TOKEN+"";
				core.log(url);

				String slt_s = com.attilax.html.selectx
						.fromUrl(
								url,
								"groupid",
								new Func_wzPre<JSONObject, Option, String, JSONArray>() {

									@Override
									public JSONArray $Pre(String o) {
										// attilax 老哇的爪子 下午04:51:47 2014-5-16
										JSONObject jo = JSONObject
												.fromObject(o);
								 //o69 
										if(jo.get("errcode")!=null)
											throw new RuntimeException("errO692:"+o);

										return jo.getJSONArray("groups");
									}

									@Override
									public Option $(JSONObject o) {
										// attilax 老哇的爪子 下午04:51:47 2014-5-16
										Option opt = new Option();
										opt.addAttribute("value", o
												.getString("id"));
										opt.setTagText(o.getString("name"));
										// sl.addElement(opt);
										return opt;
									}
								});
				return slt_s;

			}
		}.$defValIsErrmsg();
		
	
		
	}

	// protected Object thumb_media_id;
	public String thumb_media_id = "";
	private String media_id;
	private String Acontent = "";

	/**
	 * { "articles": [ { "thumb_media_id":
	 * "qI6_Ze_6PtV7svjolgs-rN6stStuHIjs9_DidOHaj0Q-mwvBelOXCFZiq2OsIU-p",
	 * "author":"xxx", "title":"Happy Day", "content_source_url":"www.qq.com",
	 * "content":"content", "digest":"digest"
	 * 
	 * @author attilax 老哇的爪子
	 * @since 2014-5-19 上午11:14:58$
	 * 
	 * @return "type":"news","media_id":
	 *         "-hLHVnKQnRbu_-MP3pgWh7y-20o5ZA8fg18MDhRH0DmVrpPCWD760Dh3_dd1n0FZ"
	 *         ,"created_at":1400481318}
	 */
	public String uploadTxt() {

		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token="
				+ ACCESS_TOKEN();
		final JSONObject o1 = new com.attilax.corePkg.JSONObject() {

			{
				put("thumb_media_id", thumb_media_id);
			//	put("author", "attilax");
				put("title", "...");
				put("content", Acontent);
			
			}

		}.$();

		JSONObject jo = new com.attilax.corePkg.JSONObject() {

			{
				put("articles", new com.attilax.corePkg.JSONArray() {
					{
						add(o1);
					}
				}.$());
			}
		}.$();

		String r = HttpRequest.sendPost(url, jo.toString());
		JSONObject jo2 = JSONObject.fromObject(r);
		media_id = jo2.getString("media_id");
		core.log("--o5h1 uplodad pictxt return:" + r);
		return r;

	}
	
	public String uploadTxt_editorVer(final com.attilax.corePkg.JSONArray jsonArr) {

		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token="
				+ ACCESS_TOKENO69();
//		final JSONObject o1 = new com.attilax.corePkg.JSONObject() {
//
//			{
//				put("thumb_media_id", thumb_media_id);
//			//	put("author", "attilax");
//				put("title", "...");
//				put("content", Acontent);
//			
//			}
//
//		}.$();

		JSONObject jo = new com.attilax.corePkg.JSONObject() {

			{
				put("articles",jsonArr);
			}
		}.$();
		log("--uploadTxt_editorVer send json:"+jo.toString(2));
	//	JSONObject jo2=	WeixinUtil.httpsRequest(url, "POST", jo.toString());
		
		String r = HttpRequest.sendPost(url, jo.toString());
		
	//	String r = jo2.toString();
		log("--receive jsoin:"+r);
	 
		JSONObject jo2=JSONObject.fromObject(r.trim());
		media_id = jo2.getString("media_id");
		core.log("--o5h1 uplodad pictxt return:" + r);
		return r;

	}

	/**图片（image）: 256K，支持JPG格式 
	 * {"type":"thumb","thumb_media_id":
	 * "vbuTsUzP2_48YWjhUFjjEca-cVn-tytZlqfSEdZxx3luEfxYKo5da_Ns0agWpE6Q"
	 * ,"created_at":1400480925}
	 * 
	 * @author attilax 老哇的爪子
	 * @since 2014-5-19 下午02:29:04$
	 * 
	 * @return
	 */
	public String uploadImgtxtMsgThumib() {

		String imgpath = pathx.webAppPath() + "\\groupSend\\cover.jpg";
		// imgpath = "c:/thumb.jpg";
		// byte[] img_b= filex.readImageData(imgpath); //
		// core.log("--read img size:"+img_b.length);
		File f=new File(imgpath);
		if(!f.exists())
			throw new RuntimeException("thund jpg is not exist");
		core.log("--thumb pic path:"+imgpath);

		String r = WeixinUtil.upload(ACCESS_TOKEN(), "thumb", imgpath);
		// public static String upload(String accessToken, String type, String
		// filePath) {
		// String url =
		// "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="
		// +ACCESS_TOKEN()+"&type=thumb";
		// core.log(url);
		// String r = HttpRequest.sendPost(url, img_b);
		core.log("--o5h");
		core.log(r);
		thumb_media_id = JSONObject.fromObject(r).getString("thumb_media_id");
		return r;

	}
	
	/**图片（image）: 256K，支持JPG格式 
	 * {"type":"thumb","thumb_media_id":
	 * "vbuTsUzP2_48YWjhUFjjEca-cVn-tytZlqfSEdZxx3luEfxYKo5da_Ns0agWpE6Q"
	 * ,"created_at":1400480925}
	 * 
	 * @author attilax 老哇的爪子
	 * @since 2014-5-19 下午02:29:04$
	 * 
	 * @return
	 */
	public String uploadImgtxtMsgThumib(String imgpath) {

	//	String imgpath = pathx.webAppPath() + "\\groupSend\\cover.jpg";
		// imgpath = "c:/thumb.jpg";
		// byte[] img_b= filex.readImageData(imgpath); //
		// core.log("--read img size:"+img_b.length);
		File f=new File(imgpath);
		if(!f.exists())
			throw new RuntimeException("thund jpg is not exist");
		core.log("--thumb pic path:"+imgpath);

		
		String r = WeixinUtil.upload(ACCESS_TOKENO69(), "thumb", imgpath);
		// public static String upload(String accessToken, String type, String
		// filePath) {
		// String url =
		// "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="
		// +ACCESS_TOKEN()+"&type=thumb";
		// core.log(url);
		// String r = HttpRequest.sendPost(url, img_b);
		core.log("--o5h");
		core.log("--upThumbRzt:"+r);
		JSONObject jo=JSONObject.fromObject(r.trim());
			if(jo.get("errmsg")!=null)
				throw new RuntimeException("--uploadImgtxtMsgThumib err,"+jo.toString());
		thumb_media_id = JSONObject.fromObject(r.trim()).getString("thumb_media_id");
		return thumb_media_id;

	}


	/**
	@author attilax 老哇的爪子
		@since  2014-6-10 上午09:25:14$
	
	 * @return
	 */
	private String ACCESS_TOKENO69() {
		 
		return wechatUtil.acc_token();
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-5-16 下午03:49:22$
	 * 
	 * @return
	 */
	private static String ACCESS_TOKEN() {
		// attilax 老哇的爪子 下午03:49:22 2014-5-16
		return new TokenThread().loadTokenByFile();
	}
}

// attilax 老哇的爪子