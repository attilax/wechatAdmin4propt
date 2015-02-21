package com.attilax;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.attilax.collection.listUtil;
import com.attilax.io.CopyFile;
import com.attilax.ref.refx;
import com.attilax.util.god;
import com.attilax.util.tryX;
import com.attilax.util.urlUtil;
import com.focusx.entity.TMbAwardWeixinResult;
import com.focusx.entity.weixin.pojo.AccessToken;

public class core {
	
	public static  Map obj2map(final Object o) {
		Map m = new BeanMap(o);
		return m;
	}
	public static   String basename_noext(String filepath) {
		   File tempFile =new File( filepath.trim());  
		//   tempFile.
	        String fileName = tempFile.getName();  
	        int lastDotPostion=fileName.lastIndexOf(".");
	        
		return fileName.substring(0,lastDotPostion);
	}


	// public static Object logger;
	public static Logger logger = Logger.getLogger("corex");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fName =" G:\\Java_Source\\navigation_tigra_menu\\demo1\\img\\lev1_arrow.gif ";  
	System.out.println(basename_noext(fName));
	
	
	}

	public static void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public static void openPort(final int port) {
		god.newThread(new Runnable() {

			public void run() {
				server( port);

			}
		}, "open port thrd");

	}

	// 服务端Socket
	public static void server(int port) {
		try {
			ServerSocket ss = new ServerSocket(port);
			// accept接受连接请求,接受到请求返回一个Socket套接字
			Socket s = ss.accept();
			OutputStream os = s.getOutputStream(); // 创建socket输出流
			InputStream is = s.getInputStream(); // 创建socket输入流
			os.write("The information comes from server!".getBytes());// 将字符串转为字节
			byte[] buf = new byte[100];

			while (true) {
				int len = is.read(buf);
				System.out.println(new String(buf, 0, len)); // 将字节数组转为字符串
				core.sleep(1000);
			}
			// os.close();
			// is.close();
			// s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static <atitype> atitype request2obj(HttpServletRequest request,
			final atitype obj) {
		// todox Apache的BeanUtils的使用入门 enhance effice
		Map parameterMap = request.getParameterMap();
		final Map mp = listUtil.caseIngor(parameterMap);
		// TProbeInvite o = new TProbeInvite();
		return new tryX<atitype>() {

			@Override
			public atitype item(Object t) throws Exception {
				// attilax 老哇的爪子 下午02:47:29 2014-5-28
				BeanUtils.copyProperties(obj, mp);
				return obj;
			}
		}.$(obj);

	}
/**
 * 	if(List) JSONArray.fromObject(obj)
@author attilax 老哇的爪子
	@since  2014-6-11 上午10:49:28$

 * @param obj
 * @return
 */
	public static String obj2jsonO5(Object obj)
	{		
		if(obj instanceof List)
			return	JSONArray.fromObject(obj).toString();  
		return	JSONObject.fromObject(obj).toString();
	}
	@Deprecated
	public static String obj2json(Object obj)
	{		
		//if(obj instanceof List)
		return	JSONObject.fromObject(obj).toString();
	}
	
	public static String requestx(final HttpServletRequest req,String para)
	{
		if( req.getParameter(para)==null)
			return "";
		return req.getParameter(para);
	}
	public static void print(Object Li) {
		System.out.println( listUtil.toString_jsonFmt(Li) );
	}

	public static void log(String emltxt) {
		StackTraceElement ste=refx.preMethod();
		
		core.logger.info("---"+emltxt+" ["+ste.getClassName()+"."+ste.getMethodName()+"():"+ste.getLineNumber()+"]");
		
	}
//o42
	public static void copy(String oldPath, String newPath) {
		CopyFile f=new CopyFile();
	 	f.copyFile(oldPath, newPath);
		
	}

	public static void debug(String string) {
		// TODO Auto-generated method stub
		
	}

	public static void log(Exception e1) {
		String emltxt=god.getTrace(e1);
	StackTraceElement ste=refx.preMethod(3);
		
		core.logger.info("---"+emltxt+" ["+ste.getClassName()+"."+ste.getMethodName()+"():"+ste.getLineNumber()+"]");

		
	}

	public static Object newx(Class class1) {
		 
		try {
			return class1.newInstance();
		} catch (Exception e) {
			core.log(e);
			return null;
		}
	}

	public static void err(RuntimeException re) {
		core.logger.error("---", re) ;
		
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-12 下午02:08:01$
	
	 * @param accTok
	 * @return
	 */
	public static String toJsonStr(AccessToken accTok) {
		// attilax 老哇的爪子  下午02:08:01   2014-5-12 
		String t = JSONArray.fromObject(accTok).toString(2);
		return t;
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-12 下午05:51:04$
	
	 * @param string
	 */
	public static void warn(String string ) {
		// attilax 老哇的爪子  下午05:51:04   2014-5-12 
		core.logger.warn("---"+string) ;
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-13 下午03:18:53$
	
	 * @param request
	 */
	public static void logurl(HttpServletRequest request) {
		// attilax 老哇的爪子  下午03:18:53   2014-5-13 
		core.log("-- this urlo5c:"+urlUtil.getUrl(request));
	}

}
