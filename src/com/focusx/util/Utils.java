package com.focusx.util;

/** 
 * com.focusx.util
 * Utils.java 
 * author:vincente  2013-10-11 
 */
public class Utils {
	public static String toUnicodeStr(String srcStr){
		        //StringBuilder支持可变字符串，是非线程安全类，速度较StringBuffer快
		        StringBuilder resultStr=new StringBuilder("");
		        for(int i=0;i<srcStr.length();i++){
		            char c=srcStr.charAt(i);
		            String hexCodeStr=Integer.toHexString((int)c);
		            int len=hexCodeStr.length();
		            resultStr.append("\\u");
		            //如果字符的unicode编码不足4位，前面补0
		            if(len<4){
		                switch(4-len){
		                case 1:resultStr.append("0");break;
		                case 2:resultStr.append("00");break;
		                case 3:resultStr.append("000");break;
		                case 4:resultStr.append("0000");break;
		                }
		            }
		            resultStr.append(hexCodeStr);
		        }
		        return resultStr.toString();
		    }
		}