package com.attilax.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import  m.datepkg.dateUtil_o16;
import static m.datepkg.dateUtil_o16.*;

public final class DateUtil extends dateUtil_o16  {

	private DateUtil() {
	}
	
	private static final String fullDateSdf = "yyyy-MM-dd HH:mm:ss";
	private static final String dateSdf = "yyyy-MM-dd";
	
	public static Date str2date(String source,boolean includeTime){
		
		Date date = null;
		SimpleDateFormat sdf = null;
		if(includeTime){
			sdf = new SimpleDateFormat(fullDateSdf);
		}else{
			sdf = new SimpleDateFormat(dateSdf);
		}
		try{
			date =  sdf.parse(source);
		}catch(Exception e){
			e.printStackTrace();
		}
		return date;
	}
	
	public static String date2str(Date d,boolean includeTime){
		SimpleDateFormat sdf = null;
		if(includeTime){
			sdf = new SimpleDateFormat(fullDateSdf);
		}else{
			sdf = new SimpleDateFormat(dateSdf);
		}
		return sdf.format(d);
	}
	/**
	 * not include time
	@author attilax 老哇的爪子
		@since  2014-4-30 下午12:48:42$
	
	 * @param d
	 * @return
	 */
	public static String date2str(Date d){
		return date2str(d,false);
	}
	
	/**
	 *  
	@author attilax 老哇的爪子
		@since  2014-5-12 下午04:27:23$
	
	 * @return  2014-05-12    fmt
	 */
	public static String getTodayStr(){
		return date2str(new Date());
	}
	
	public static Date getYesterday(){
	  return getYesterday(null);
	}
	
	public static Date getYesterday(Date startAt){
		Calendar cal = Calendar.getInstance();
		if(startAt!=null) cal.setTime(startAt);
		cal.add(Calendar.DAY_OF_MONTH, -1);
	  return cal.getTime();
	}
	
	public static String getYesterdayStr(){
		return date2str(getYesterday());
	}
	
	public static String getYesterdayStr(Date startAt){
		return date2str(getYesterday(startAt));
	}
	
	
	public static boolean isSameDay(Date d1,Date d2){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(d1);
		cal2.setTime(d2);
		return cal1.get(Calendar.DAY_OF_MONTH)==cal2.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取两个时间相差的分钟数
	 * @param start	开始时间
	 * @param end	结束时间
	 * @return		分钟数
	 */
	public static int getMinuteInterval(Date start,Date end){
		try {
			long result=end.getTime()-start.getTime();
			result = result / (1000 * 60);//分钟数
			return (int) result;
		} catch (Exception e) {
			return 0;
		}
	}
	
	
	public static int getMinuteInterval(String start,Date end){
		return getMinuteInterval(str2date(start,true),end);
	}
	
	public static int getMinuteInterval(String start,String end){
		return getMinuteInterval(str2date(start,true),str2date(end,true));
	}
	
	public static int getSecondInterval(Date start,Date end){
		try{
			return (int) (end.getTime()-start.getTime()) / 1000;
		}catch(Exception e){
			return 0;
		}
	}
	
	public static int getSecondInterval(String start,Date end){
		return getSecondInterval(str2date(start,true),end);
	}
	
	public static int getSecondInterval(String start,String end){
		return getSecondInterval(str2date(start,true),str2date(end,true));
	}
	
	public static long getMillisInterval(Date start,Date end){
		try{
			long result = end.getTime()-start.getTime();
			return (int) result;
		}catch(Exception e){
			return 0;
		}
	}
	
	public static long getMillisInterval(String start,Date end){
		return getMillisInterval(str2date(start,true),end);
	}
	
	public static long getMillisInterval(String start,String end){
		return getMillisInterval(str2date(start,true),str2date(end,true));
	}
	
	/**
	 * 获取两个时间相差的天数
	 * @param start	开始时间
	 * @param end	结束时间
	 * @return		天数
	 */
	public static int getDayInterval(Date start,Date end){
		int minute = getMinuteInterval(start,end);
		if(minute>0){
			return minute/1440;
		}
		return 0;
	}
	
	public static String getFirstDayOfPreXWeek(int x){
		return getFirstDayOfPreXWeek(null,x);
	}
	
	public static String getFirstDayOfPreXWeek(Date startAt,int x){
		Calendar cal = Calendar.getInstance();
		if(startAt!=null) cal.setTime(startAt);
		cal.add(Calendar.WEEK_OF_MONTH, -1);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.add(Calendar.WEEK_OF_MONTH, (Math.abs(x)-1)*-1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return date2str(cal.getTime());
	}
	
	public static String getLastDayOfPreXWeek(int x){
		return getLastDayOfPreXWeek(null,x);
	}
	
	public static String getLastDayOfPreXWeek(Date startAt,int x){
		Calendar cal = Calendar.getInstance();
		if(startAt!=null) cal.setTime(startAt);
		cal.add(Calendar.WEEK_OF_MONTH, -1);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.add(Calendar.WEEK_OF_MONTH, (Math.abs(x)-1)*-1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return date2str(cal.getTime());
	}
	
	public static String getFirstDayOfLastWeek() {
		return getFirstDayOfPreXWeek(1);
	}
	
	public static String getFirstDayOfLastWeek(Date startAt) {
		return getFirstDayOfPreXWeek(startAt,1);
	}
	
	public static String getLastDayOfLastWeek() {
		return getLastDayOfPreXWeek(1);
	}
	
	public static String getLastDayOfLastWeek(Date startAt) {
		return getLastDayOfPreXWeek(startAt,1);
	}
	
	public static String getFirstDayOfWeek() {
		return getFirstDayOfPreXWeek(0);
	}
	
	public static String getFirstDayOfWeek(Date startAt) {
		return getFirstDayOfPreXWeek(startAt,0);
	}
	
	public static String getLastDayOfWeek() {
		return getLastDayOfPreXWeek(0);
	}
	
	public static String getLastDayOfWeek(Date startAt) {
		return getLastDayOfPreXWeek(startAt,0);
	}
	
	public static String getFirstDayOfMonth() {
		return getFirstDayOfPreXMonth(0);
	}
	
	public static String getFirstDayOfMonth(Date startAt) {
		return getFirstDayOfPreXMonth(startAt,0);
	}
	
	public static String getLastDayOfMonth() {
		return getLastDayOfPreXMonth(0);
	}
	
	public static String getLastDayOfMonth(Date startAt) {
		return getLastDayOfPreXMonth(startAt,0);
	}
	
	public static String getFirstDayOfLastMonth(){
		return getFirstDayOfPreXMonth(1);
	}
	
	public static String getFirstDayOfLastMonth(Date startAt){
		return getFirstDayOfPreXMonth(startAt,1);
	}
	
	
	public static String getLastDayOfLastMonth() {
		return getLastDayOfPreXMonth(1);
	}
	
	public static String getLastDayOfLastMonth(Date startAt) {
		return getLastDayOfPreXMonth(startAt,1);
	}
	
	public static String getFirstDayOfPreXMonth(int x){
		return getFirstDayOfPreXMonth(null,x);
	}
	
	public static String getFirstDayOfPreXMonth(Date startAt,int x){
		Calendar cal = Calendar.getInstance();
		if(startAt!=null) cal.setTime(startAt);
		cal.add(Calendar.MONTH, Math.abs(x)*-1);
		cal.set(Calendar.DATE, 1);// 设为当前月的1号
		return date2str(cal.getTime());
	}
	
	public static String getLastDayOfPreXMonth(int x) {
		return getLastDayOfPreXMonth(null,x);
	}
	
	public static String getLastDayOfPreXMonth(Date startAt,int x) {
		Calendar cal = Calendar.getInstance();
		if(startAt!=null) cal.setTime(startAt);
		if(x>1) cal.add(Calendar.MONTH, Math.abs(x-1)*-1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		if(x==0) cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return date2str(cal.getTime());
	}
	
	
	public static int getWeekOfMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}
	
	 
	public static String getPreXMonth(int x){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, x*-1);
		return date2str(cal.getTime());
	}
	
	public static String getPreDay(int x,boolean includeTime){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, x*-1);
		return date2str(cal.getTime(),includeTime);
	}
	
	public static void main(String[] args){
		
		
		System.out.println(timestamp());
		System.out.println(System.currentTimeMillis());
		Date date = DateUtil.str2date("2013-01-11", false);
		System.out.println("Yesterday:"+DateUtil.getYesterdayStr(date));
		System.out.println("LastWeek Range:"+DateUtil.getFirstDayOfLastWeek(date) +" - "+ DateUtil.getLastDayOfLastWeek(date));
		System.out.println("ThisWeek Range:"+DateUtil.getFirstDayOfWeek(date) +" - "+ DateUtil.getLastDayOfWeek(date));
		System.out.println("LastMonth Range:"+DateUtil.getFirstDayOfLastMonth(date)+" - "+DateUtil.getLastDayOfLastMonth(date));
		System.out.println("ThisMonth Range:"+DateUtil.getFirstDayOfMonth(date)+" - "+DateUtil.getLastDayOfMonth(date));
		
		System.out.println( getTodayStr());
	}

	/**
	 * get java timestamp
	 * o40
	 * @return
	 */
	public static long timestamp() {
		String startTime="2014-01-01 00:00:01";
		Date startDate=str2date(startTime, true);
	long span=	getMillisInterval(startDate,new Date());
		
		
		return span;
	}
	
	/**
	 * get java timestamp
	 * o40
	 * @return
	 */
	public static String timestamp_strFmt() {
		 
		
		
		return String.valueOf(timestamp());
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-4-30 下午12:50:19$
	
	 * @return
	 */
	public static Date today_notime() {
		// 下午12:50:19   2014-4-30 
		String dt_s=DateUtil.today();
		Date dt=DateUtil.str2date(dt_s, false);
		return dt;
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-12 下午04:39:49$
	
	 * @return
	 */
	public static String today_Start() {
		// attilax 老哇的爪子  下午04:39:49   2014-5-12 
		return  DateUtil.getTodayStr()+" 00:00:00";
	}
	
}
