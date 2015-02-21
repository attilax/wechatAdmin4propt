package com.focusx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/** 
 * com.focusx.util
 * RandomNum.java 
 * author:vincente  2013-10-10 
 */
public class RandomNum {

	private static RandomNum instance ;
	
	private RandomNum(){
		
	}
	
	public synchronized static RandomNum getInstance(){
		if(instance == null){
			instance = new RandomNum();
		}
		return instance;
	}
	
	/**
	 * 产生固定长度的随机整数
	 * @param size 随机数的长度
	 * @return int
	 */
	public String getRandomNum(int size){
		int[] array = {0,1,2,3,4,5,6,7,8,9};
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
		    int index = rand.nextInt(i);
		    int tmp = array[index];
		    array[index] = array[i - 1];
		    array[i - 1] = tmp;
		}
		int result = 0;
		for(int i = 0; i < size; i++){
			result = result * 10 + array[i];
		}
		String randomNum = "" + result;
		if(randomNum.length()==5){
			randomNum = "0"+randomNum;
		}
		return randomNum;
	}
	
	/**
	 * 获取会话的编码   前缀+年+月+日+6位随机码
	 * @param prefix
	 * @param size
	 * @return String
	 */
	public String getSessionNum(String prefix,int size){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String strDate = format.format(calendar.getTime());
		String randomNum = getRandomNum(size);
		return prefix+strDate+randomNum;
	}
	public static void main(String[] args) throws ParseException{
/*		String s = RandomNum.getInstance().getSessionNum("WB", 6);
		System.out.println(s);*/
		//int l = 1377428630;
		/*int l = 1381215916;
		 * 		  1381215916000
		 * 		  1381375741671
		 * 		  1381356915890
		 * 		  1381403387906
		Date d = new Date(l);
		System.out.println(d);*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date currentTime=new Date();
		  //将截取到的时间字符串转化为时间格式的字符串
		  Date beginTime=sdf.parse("1970-01-01 00:00:00");
		  //默认为毫秒
		  long interval=currentTime.getTime();
		  long s = 1381215916L;
		  System.out.println(interval);
		  System.out.println(new Date(s*1000));
	}
}
