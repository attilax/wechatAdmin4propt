package com.attilax.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.attilax.core;
import com.attilax.collection.listUtil;
import com.attilax.io.pathx;
import com.attilax.text.strUtil;

public class imgService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void outputImg(HttpServletRequest req,
			HttpServletResponse response) {
		
	String uri=	req.getRequestURI();
	core.log(" --get uri:"+uri);
	core.log(" --get url:"+req.getRequestURL());
	core.log(" --getQueryString:"+req.getQueryString());
    Map m=	urlUtil.Param2Map(req);
    core.log("--o4f1");
    listUtil.print(m);
    String jpgPart=m.get("urlCnChar".toLowerCase()).toString();
    String jpgPart_real=urlUtil.decodeByUtf8(jpgPart);
    jpgPart_real=jpgPart;
    String realPath=pathx.webAppPath(req)+"/img/QQ"+jpgPart_real+".jpg";
	core.log("--realpath:"+realPath);
		response.reset();
		 response.setHeader("Cache-Control", "no-cache");  
		    response.setDateHeader("Expires", 0);  
	 	response.setContentType("image/jpeg");
	//	response.setHeader("Content-Type", "image/jpeg4");
	 //	response.setHeader("Transfer-Encoding", "chunked");
	 		
	 
		
		ServletOutputStream sout = null;
		try {
			sout = response.getOutputStream();
		} catch (Exception e1) {
			core.log(e1);
		}
		// InputStream in = rs.getBinaryStream(1);
		// byte b[] = new byte[0x7a120];
		// in.read(b);
		byte[] b = imgUtil.toByteArr(realPath);
 	response.setContentLength(b.length);
		try {
			sout.write(b);
		 
			sout.flush(); 
		 
		//	response.flushBuffer();
			sout.close();
		} catch (Exception e) {
			core.log(e);
		}

	}

}
