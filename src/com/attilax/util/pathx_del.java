package com.attilax.util;

import javax.servlet.http.HttpServletRequest;

public class pathx_del {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	
	public  static String getRequestURLx(HttpServletRequest request) {
		
		  String urlx = request.getRequestURL().toString();
		  //http://localhost:10000/user/match.shtml  
		  //no catch param.
		//  logger.info(urlx);
  String   url=request.getScheme()+"://";   
  url+=request.getHeader("host");   
  
  url+=request.getRequestURI();   
  if(request.getQueryString()!=null)   
        url+="?"+request.getQueryString();  
  return url;
	}

}
