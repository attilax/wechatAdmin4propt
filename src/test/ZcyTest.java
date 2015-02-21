package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import com.focusx.dao.impl.ListenerImpl;
import com.focusx.entity.weixin.message.request.TextMessage;
import com.focusx.util.Constant;
import com.focusx.util.WeixinUtil;
import com.focusx.webService.AuthInfo;
import com.focusx.webService.WeixinCheckImei;
import com.focusx.webService.WeixinCheckImeiImplService;
import com.focusx.webService.WeixinVO;

public class ZcyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ZcyTest test = new ZcyTest();
		test.sleepTest();

	}

	public void sleepTest() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Thread thread = Thread.currentThread();
			for (int i = 0; i < 100; i++) {
				System.out.println(sdf.format(new Date()) + "	------	" + i);
				thread.sleep(1000 * 2);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void threadTest() {
		DoSomething ds1 = new DoSomething();
		ds1.setName("阿三");
		DoSomething ds2 = new DoSomething();
		ds2.setName("李四");

		Thread t1 = new Thread(ds1);
		Thread t2 = new Thread(ds2);

		t1.start();
		t2.start();
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public void dateTest() {
		Date initDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String sinitDate = "2014-10-01 00:00";
		try {
			initDate = sdf.parse(sinitDate);
			System.out.println(initDate.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Date d2 = new Date(1388403569l * 1000);
		System.out.println(sdf.format(d2));
	}

	public void checkImei(String imei) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		WeixinCheckImei check = new WeixinCheckImeiImplService().getWeixinCheckImeiImplPort();
		AuthInfo authInfo = new AuthInfo();
		authInfo.setUserName("CHECKIMEI");
		authInfo.setPassword("888888");
		WeixinVO vo = new WeixinVO();
		vo.setImei(imei);
		String resultInfo = check.checkImei(authInfo, vo);
		JSONObject jsonObject = JSONObject.fromObject(resultInfo);
		JSONObject imeiObject = jsonObject.getJSONObject("imeiMst");
		long returnDate = imeiObject.getLong("source_date");
		long dateTime = jsonObject.getLong("datetime");
		System.out.println("----------------会员注册日期：" + sdf.format(new Date(returnDate)) + "---------------------");
		System.out.println("----------------易用汇日期：" + sdf.format(new Date(dateTime)) + "---------------------");
	}

	public static void readTxtFile(String name) {
		try {
			String title = "";
			String description = "";
			String picUrl = "";
			String url = "";
			String filePath = "D:\\workspace\\WeiXin\\src\\location.txt";
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				int xh = 0;
				boolean flag = false;
				name = "title=" + name;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					lineTxt = lineTxt.trim();
					int index = flag == false ? lineTxt.indexOf(name) : -1;
					if (index > -1) {
						flag = true;
					}
					if (flag == true) {
						if (xh == 4) {
							break;
						} else {
							xh++;
							System.out.println(lineTxt);
						}
					}
					switch (xh) {
					case 1:
						title = lineTxt.substring(6, lineTxt.length());
						break;
					case 2:
						description = lineTxt.substring(12, lineTxt.length());
						break;
					case 3:
						picUrl = lineTxt.substring(7, lineTxt.length());
						break;
					case 4:
						url = lineTxt.substring(4, lineTxt.length());
						break;
					}
				}
				System.out.println("title:		" + title);
				System.out.println("description:	" + description);
				System.out.println("picUrl:	" + picUrl);
				System.out.println("url:	" + url);
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}

	public void imeiTest() {
		String content = "IMEI#123456789QWERTY";
		int beginIndex = content.indexOf("IMEI#") + 5;
		int endIndex = content.length();
		String imei = content.substring(beginIndex, endIndex);
		System.out.println(content);
		System.out.println(imei);
	}

	public void propertiesTest() {
		Properties prop;
		String propertyFile = "location.properties"; // 配置文件
		try {
			ClassLoader classLoader = ZcyTest.class.getClassLoader();
			// 取得资源文件输入流
			InputStream is = classLoader.getResourceAsStream(propertyFile);
			prop = new Properties();
			prop.load(is);

			Enumeration en = prop.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = prop.getProperty(key);
				System.out.println(key + "		" + Property);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void createIMEIData() {
		
		try {
			System.out.println("系统正在将IMEI文件内容复制到数据库中");
			String toUserName = "";
			String fromUserName = "";
			long createTime = 0l;
			String msgType = "";
			int funcFlag = 0;
			String msgId = "";
			String imei = "";
			long buyDate = 0l;
			long returnDate = 0l;
			List<TextMessage> detail = new ArrayList();

			// 根据日期确实文件名称
			int xh = 0;
			String lineTxt = "";
			String path = "C:\\config\\imei\\";
			Date currentDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String filePath = path + sdf.format(currentDate) + ".txt";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((lineTxt = bufferedReader.readLine()) != null) {
					xh++;
					lineTxt = lineTxt.trim();
					switch (xh) {
					case 1:
						toUserName = lineTxt;
						break;
					case 2:
						fromUserName = lineTxt;
						break;
					case 3:
						if (lineTxt != null && !lineTxt.equals(""))
							createTime = Long.valueOf(lineTxt);
						break;
					case 4:
						msgType = lineTxt;
						break;
					case 5:
						if (lineTxt != null && !lineTxt.equals(""))
							funcFlag = Integer.valueOf(lineTxt);
						break;
					case 6:
						msgId = lineTxt;
						break;
					case 7:
						imei = lineTxt;
						break;
					case 8:
						if (lineTxt != null && !lineTxt.equals(""))
							buyDate = Long.valueOf(lineTxt);
						break;
					case 9:
						if (lineTxt != null && !lineTxt.equals(""))
							returnDate = Long.valueOf(lineTxt);
						break;
					}
					if (xh == 9) {
						xh = 0;
						TextMessage message = new TextMessage();
						message.setBuyDate(buyDate);
						message.setContent(imei);
						message.setCreateTime(createTime);
						message.setFromUserName(fromUserName);
						message.setFuncFlag(funcFlag);
						message.setImei(imei);
						message.setMsgId(msgId);
						message.setMsgType(msgType);
						message.setReturnDate(returnDate);
						message.setToUserName(toUserName);
						detail.add(message);
					}
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件 C:\\config\\imei\\");
			}
			// 获取数据层DAO类
			Class<?> classTypeDao = ListenerImpl.class;
			ListenerImpl listenerDao = (ListenerImpl) classTypeDao.newInstance();
			//listenerDao.insertWeiXinIMEI(detail);
		} catch (Exception e) {
			System.out.println("读取文件内容出错 C:\\config\\imei\\");
			e.printStackTrace();
		}
	}

	public void getAccess_token() {
		String appid = "wx94d1460e167fff4e";
		String appsecret = "60c45ce913d302a2030371d581215801";
		Constant.token = WeixinUtil.getAccessToken(appid, appsecret);
		String accessToken = Constant.token.getToken();
		System.out.println(accessToken);
	}

}
