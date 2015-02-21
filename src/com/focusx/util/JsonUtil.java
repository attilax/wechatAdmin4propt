package com.focusx.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * com.focusx.util JsonUtil.java author:vincente 2013-8-26
 */
public class JsonUtil {

	private static JsonUtil instance;

	private JsonUtil(){
		
	}
	
	public synchronized static JsonUtil getInstance() {
		if(instance==null){
			instance = new JsonUtil();
		}
		return instance;
	}

	/**
	 * @param response
	 * @param value 
	 * @throws IOException
	 */
	public static void write(HttpServletResponse response, Object value) throws IOException {
		// 设置编码格式,以及信息类型 及 是否有缓存的设置
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("caChe-Control", "no-cache");

		PrintWriter out = response.getWriter();
		out.print(value);

		// 清空缓存
		out.flush();
		// 关闭
		out.close();

	}
	public static void write(HttpServletResponse response, List values) throws IOException {
		// 设置编码格式,以及信息类型 及 是否有缓存的设置
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("caChe-Control", "no-cache");

		PrintWriter out = response.getWriter();
		
		for(int i=0;i<values.size();i++){
			out.print(values.get(i));
		}

		// 清空缓存
		out.flush();
		// 关闭
		out.close();

	}
}
