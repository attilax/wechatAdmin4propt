package com.focusx.util;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.focusx.entity.TMbEventHistory;
import com.focusx.entity.TMbGroup;
import com.focusx.entity.TMbSignHistory;
import com.focusx.entity.TMbWeixinUser;
import com.focusx.entity.weixin.pojo.AccessToken;

/**
 * 
 * @模块 系统变量
 */
public class Constant {

	/**
	 * 用户ID
	 */
	public final static String Login_UserID = "Login_UserID";
	/**
	 * 用户编号
	 */
	public final static String Login_WorkNO = "Login_WorkNO";
	/**
	 * 用户名称
	 */
	public final static String Login_NAME = "Login_NAME";
	/**
	 * 用户密码
	 */
	public final static String Login_PASSWORD = "Login_PASSWORD";
	/**
	 * 登录状态ID
	 */
	public final static String Login_StateValue = "Login_StateValue";
	/**
	 * 登录状态值
	 */
	public final static String Login_StateName = "Login_StateName";
	/**
	 * 用户签入信息
	 */
	public final static String Login_UserInfo = "Login_UserInfo";
	
	/**
	 *  token配置文件路径
	 */
	public final static String path = "D:\\config\\";
	
	/**
	 *  常用值 0
	 */
	public final static int ZERO = 0;
	
	/**
	 * 常用值 1
	 */
	public final static int ONE = 1;
	
	public final static int TWO = 2;
	
	public final static int NEGATIVE = -1;
	
	public final static int Three = 3;//常用值 3
	
	public final static int NEWS_NEWSALE = 1;//最新促销
	public final static int NEWS_HOTSALE = 2;//top热卖
	public final static int NEWS_NEWGOOD = 3;//新品上市
	
	public final static int NEWS_MEMBERDAY = 4; //会员日
	public final static int NEWS_SCANGIFT = 5;//扫码有礼
	public final static int NEWS_WXPRICE = 6; //微信价
	
	public final static int NEWS_MEMBERCREDIT = 7;//会员积分
	
	public final static int NEWS_MEMBERGOOD = 8;//会员积分兑换
	public final static int NEWS_STORESELECTION = 9;//门店精选
	public final static int NEWS_ALL  = 99;//群发消息
	
	public final static int NEWS_SUPERFUN = 10;//超级fun
	
    public final static int NEWS_FOCUS = 11;//关注时推送的图文
	
	public final static int NEWS_RULE = 12; // 抽奖的规则图文
	
	public final static int NEWS_KEYWORD = 13;//关键字类型图文
	
	public final static String uploadPathHtml = "D:\\gialen\\html.war\\news";//生成html文件的存放路径
	
	public final static String uploadPathImage = "D:\\gialen\\image.war\\news\\cover";//上传封面图片的存放路径
	
	public final static String uploadPathCode = "D:\\gialen\\image.war\\code";//生成二维码的存放路径
	
	public final static String uploadPathHtmlImage = "D:\\gialen\\image.war\\news";//正文中上传的图片的存放路径(完整路径后面有html文件夹)
	
	public final static String uploadPathDraw = "D:\\gialen\\image.war\\message";//购物小票存放路径
	
	public final static int ActAwardType_Entity = 1;//奖品类型：实物
	
	public final static int ActAwardType_Integral = 2;//奖品类型：积分
	
	public final static int ActAwardType_CashCoupon = 3;//奖品类型：现金劵
	
	public final static int ActAwardType_Coupon = 4;//奖品类型：优惠劵
	
	public final static int ActAwardType_Card = 5;//奖品类型：会员卡
	
	public final static Map<String, TMbGroup> SERVER_MAP = new HashMap<String, TMbGroup>();
	
	public final static Integer everyTimeSize = 500;
	
	/**
	 * SessionFactory工厂
	 */
	public static SessionFactory sessionFactory;
	
	public static AccessToken token;
	
	public static String WEB_URL = "";
	
	public  static String NEWS_WEB_SITE = "";
	public static String NEWS_HTML_PATH = "";
	public static String NEWS_IMAGE_PATH = "";
	
	//请求toekn的设置
	public static String APPID = "";
	public static String APPSECRET = "";
	
	//其他资源地址，csv
	public final static String otherPath = "D:\\";
	
	public static TMbWeixinUser weixinuser = null;        //第一个粉丝
	
	public static TMbWeixinUser members = null;           //第一个关注粉丝
	
	public static TMbSignHistory signHistory = null;      //第一个签到的记录
	
	public static TMbEventHistory eventHistory = null; //第一个点击记录

}
