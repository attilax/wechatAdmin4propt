package com.focusx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  时间工作类
 */
public class DateUtil {
	
	public static long CONST_WEEK = 3600 * 1000 * 24 * 7;
	
	public static SimpleDateFormat sdf_YM = new SimpleDateFormat("yyyy-MM");
    public static SimpleDateFormat sdf_YMd = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdf_YM_China = new SimpleDateFormat("yyyy年MM月"); 
    public static SimpleDateFormat sdf_YMd_China = new SimpleDateFormat("yyyy年MM月dd日"); 
	
    public static void main(String[] args) throws ParseException {
    	List<String> list = getMonth();
    	System.out.println(list.get(0));
    	System.out.println(list.get(1));
    	System.out.println(list.get(2));
    	System.out.println(list.get(3));

	}
    
    //比较日期大小，局限在年月日
    public static int compare_date(Date date1, Date date2) throws ParseException {
    	date1 = sdf_YMd.parse(sdf_YMd.format(date1));
    	date2 = sdf_YMd.parse(sdf_YMd.format(date2));
    	int num = 0;
    	if(date1.getTime() > date2.getTime()) {
			num = 1;
        }else if (date1.getTime() < date2.getTime()) {
        	num = 2;
        }else if(date1.getTime() == date2.getTime()){
        	num = 3;
        }
	   return num;
    }
    
    //根据日期和天数获取下一周的日期，天数是7的倍数
    public static String getLastWeek_Day(Date date, int num){
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。     
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, +num);//取当前日期的后num天.   
		
		//通过格式化输出日期    
		String lastWeek_day = sdf_YMd.format(cal.getTime()); 
    	return lastWeek_day;
    }
    
	//根据两个时间统计月数
	public static Integer getMonthCount(String starTime, String endTime){
		int count = 0;
		try {
			count = getMonthDiff(starTime, endTime)+1;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	//根据两个时间统计中间相差的周数
	public static Integer getWeekCount(Date starTime, Date endTime){
		int interval = 0;
		try {
			Map<String, Date> map = getWeek_Monday_Sunday(starTime);
			Date sunday = map.get("Sunday");
			sunday = sdf_YMd.parse(sdf_YMd.format(sunday));
			map.clear();
			map = getWeek_Monday_Sunday(endTime);
			Date monday = map.get("Monday");
			monday = sdf_YMd.parse(sdf_YMd.format(monday));
			
			Calendar before = Calendar.getInstance();
			Calendar after = Calendar.getInstance();
			before.setTime(sunday);
			after.setTime(monday);
			int week = before.get(Calendar.DAY_OF_WEEK);
			before.add(Calendar.DATE, -week);
			week = after.get(Calendar.DAY_OF_WEEK);
			after.add(Calendar.DATE, 7 - week);
			interval = (int) ((after.getTimeInMillis() - before
					.getTimeInMillis()) / CONST_WEEK);
			interval = interval - 1;
			if(interval ==0){
				interval  = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return interval;
	}
	
	//根据两个时间、分页数获取月
	public static List<String> getMonths(String starTime, String endTime, int pageNumber, int pageSize){
        List<String> months = new ArrayList<String>();
        Map<String, String> months_map = new HashMap<String, String>();
        List<String> months_result = new ArrayList<String>();
		try {
			Date date = sdf_YM.parse(starTime);
	        int count_other = getMonthDiff(starTime, endTime);
	        int count = 0;
	        
	        Date startDate = sdf_YMd.parse(starTime);
	        Map<String, String> map = getFirstday_Lastday_Month(startDate);
	        months.add(starTime);
	        months_map.put(starTime, starTime+" 00:00:00"+"="+map.get("last"));
	        if(count_other == 0){//表示两个日期在同一个月中
	        	count = 1;
	        }else if(count_other > 0 && count_other == 1){//表示两个日期所在的月相邻
		        Map<String, String> map_end = getFirstday_Lastday_Month(new Date());
		        months_map.put(endTime, map_end.get("first")+"="+endTime+" 23:59:59");
		        months.add(endTime);
		        count = 2;
	        }else {
		        for(int i = 1 ; i < count_other; i++){
		        	Date date_other = getLastDate(date, i);
			        Map<String, String> map_other = getFirstday_Lastday_Month(date_other);
			        months.add(sdf_YM.format(date_other));
			        months_map.put(sdf_YM.format(date_other), map_other.get("first")+"="+map_other.get("last"));
		        }
		        Map<String, String> map_end = getFirstday_Lastday_Month(new Date());
		        months_map.put(endTime, map_end.get("first")+"="+endTime+" 23:59:59");
		        months.add(endTime);
		        count = count_other + 1;
	        }
	        
	        List<String> temp = new ArrayList<String>();
	        for(int i = months.size()-1; i >= 0 ; i--){
	        	temp.add(months.get(i));
	        }
	        
	        if(count > 0){
	        	if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
	        		for(int i = 0; i < count; i++){
			        	String values = months_map.get(temp.get(i));
			        	months_result.add(values);
	        		}
	        	}else if(count > (pageNumber+pageSize-1)){
	        		pageNumber = pageNumber -1;
		        	for(int i = pageNumber; i < (pageNumber+pageSize); i++){
			        	String values = months_map.get(temp.get(i));
			        	months_result.add(values);
		        	}
	        	}else if(count >= pageNumber && count <= (pageNumber+pageSize-1)){
	        		pageNumber = pageNumber -1;
		        	for(int i = pageNumber; i < count; i++){
			        	String values = months_map.get(temp.get(i));
			        	months_result.add(values);
		        	}
	        	}
	        }
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return months_result;
	}
	
	//根据两个时间、分页数获取周
	public static List<String> getWeeks(String lastDay , int pageNumber, int pageSize){
    	List<String> list = new ArrayList<String>();
    	List<String> result = new ArrayList<String>();
    	List<String> temp = new ArrayList<String>();
    	try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE,   -1);
    		Date date = cal.getTime();

    		//获取昨天所在周的星期一和星期日
        	Date mon1 = getWeek_Monday_Sunday(date).get("Monday");
        	
        	Date lastDay_Day = sdf_YMd.parse(lastDay);
        	
        	//获取最早的日期 所在周的星期一和星期日
        	Date mon2 = getWeek_Monday_Sunday(lastDay_Day).get("Monday");
        	Date sun2 = getWeek_Monday_Sunday(lastDay_Day).get("Sunday");
        	
        	if(compare_date(mon2,lastDay_Day)==2){//比较最早日期和所在周的星期一大小
        		list.add(lastDay+"="+sdf_YMd.format(sun2));
        	}else if(compare_date(mon2,lastDay_Day)==3){
        		list.add(sdf_YMd.format(mon2)+"="+sdf_YMd.format(sun2));
        	}
        	int count_other = getWeekCount(sun2,mon1);
        	
        	int count = count_other + 2;
        	for(int i = 1; i < count_other+1; i++){
            	list.add(getLastWeek_Day(mon2, i*7)+"="+getLastWeek_Day(sun2, i*7));
        	}
        	if(compare_date(mon1, date)==2){
        		list.add(sdf_YMd.format(mon1)+"="+sdf_YMd.format(date));
        	}else if(compare_date(mon1, date)==3){
        		list.add(sdf_YMd.format(mon1)+"="+sdf_YMd.format(mon1));
        	}
        	
        	for(int i = list.size() - 1; i >= 0; i--){
        		temp.add(list.get(i));
        	}
        	
	        if(count > 0){
	        	if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
	        		for(int i = 0; i < count; i++){
			        	result = temp;
	        		}
	        	}else if(count > (pageNumber+pageSize-1)){
	        		pageNumber = pageNumber -1;
		        	for(int i = pageNumber; i < (pageNumber+pageSize); i++){
		        		result.add(temp.get(i));
		        	}
	        	}else if(count >= pageNumber && count <= (pageNumber+pageSize-1)){
	        		pageNumber = pageNumber -1;
		        	for(int i = pageNumber; i < count; i++){
		        		result.add(temp.get(i));
		        	}
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//根据两个时间、分页数获取日
	public static List<String> getDays(String starTime, String endTime, int pageNumber, int pageSize){
		List<String> days = new ArrayList<String>();
		List<String> result = new ArrayList<String>();
		try {
			Date begin = sdf_YMd.parse(starTime);      
			Date end = sdf_YMd.parse(endTime);      
			double between = (end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒      
			double dayNum = between/(24*3600);
			for(int i = 0;i <= dayNum; i++){
				Calendar cdl = Calendar.getInstance();   
				cdl.setTime(sdf_YMd.parse(starTime));   
				cdl.add(Calendar.DATE, i);//增加一天   
				days.add(sdf_YMd.format(cdl.getTime()));
			}
			int count = days.size();
	        if(count > 0){
	        	if(pageNumber == Constant.ZERO && pageSize == Constant.ZERO){
	        		result = days;
	        	}else if(count > (pageNumber+pageSize-1)){
	        		pageNumber = pageNumber -1;
		        	for(int i = pageNumber; i < (pageNumber+pageSize); i++){
			        	result.add(days.get(i));
		        	}
	        	}else if(count >= pageNumber && count <= (pageNumber+pageSize-1)){
	        		pageNumber = pageNumber -1;
		        	for(int i = pageNumber; i < count; i++){
		        		result.add(days.get(i));
		        	}
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//获取两个时间之间的天数
	public static int getDayCount(String starTime, String endTime){
		int count = 0;
		try {
			Date begin = sdf_YMd.parse(starTime);      
			Date end = sdf_YMd.parse(endTime);      
			double between = (end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒      
			count = (int) (between/(24*3600));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ++count;
	}
	
    //获取两个时间相差几个月
    public static int getMonthDiff(String startDate, String endDate) throws ParseException {
        int monthday = 0;
        Date startDate1 = sdf_YMd.parse(startDate);

        Calendar starCal = Calendar.getInstance();
        starCal.setTime(startDate1);

        int sYear  = starCal.get(Calendar.YEAR);
        int sMonth = starCal.get(Calendar.MONTH);

        Date endDate1 = sdf_YMd.parse(endDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate1);
        int eYear  = endCal.get(Calendar.YEAR);
        int eMonth = endCal.get(Calendar.MONTH);

        monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));
        return monthday;
    }
    
    //获取某个时间所在的月的第一天和最后一天
	public static Map<String, String> getFirstday_Lastday_Month(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.MONTH, 0);
	    Date theDate = calendar.getTime();
	        
	    //月第一天
	    GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
	    gcLast.setTime(theDate);
	    gcLast.set(Calendar.DAY_OF_MONTH, 1);
	    String day_first = sdf_YMd.format(gcLast.getTime());
	    StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
	    day_first = str.toString();

	    //月最后一天
	    calendar.add(Calendar.MONTH, 1);    //加一个月
	    calendar.set(Calendar.DATE, 1);        //设置为该月第一天
	    calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
	    String day_last = sdf_YMd.format(calendar.getTime());
	    StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
	    day_last = endStr.toString();

	    Map<String, String> map = new HashMap<String, String>();
	    map.put("first", day_first);
	    map.put("last", day_last);
	    return map;
	}
	   
	//根据参数i和时间data获取对应日期
	private static Date getLastDate(Date date, int i) {
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.MONTH, i);
	    return cal.getTime();
	}
	
	//获取某个时间所在周的星期一和星期日
	public static Map<String, Date> getWeek_Monday_Sunday(Date date){
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);  
		
		//判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
		if(1 == dayWeek) {  
			cal.add(Calendar.DAY_OF_MONTH, -1);  
		}  

		cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
		int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值   
		Date imptimeBegin = cal.getTime();  
		cal.add(Calendar.DATE, 6);  
		Date imptimeEnd = cal.getTime();  
		
	    Map<String, Date> map = new HashMap<String, Date>();
	    map.put("Monday", imptimeBegin);
	    map.put("Sunday", imptimeEnd);
	    return map;
	}
	
	//获取当前日期的前30天日期
	public static List<String> getTimeToDay(){
		List<String> list = new ArrayList<String>();
		for(int i = 1; i <= 30; i++){
			long time = System.currentTimeMillis() - (1000L * 60 * 60 * 24 * i);
			Date date = new Date();
			if (time > 0) {
				date.setTime(time);
			}
			list.add(sdf_YMd.format(date));
		}
		return list;
	}
	
	//获取前i天日期
	public static String getDayBefore(int i){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,   -i);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
		return yesterday;
	}
	
	/**
	 *  获取当前系统时间的上周和上上周的周一和周日，数组顺序为上上一周周一、上上一周周日、上一周周一、上一周周日
	 */
	public static List<String> getWeekMonSun(){
		List<String> list = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, 1);
		String Sunday = sdf_YMd.format(cal.getTime())+" 23:59:59";//上一周周日
		cal.add(Calendar.WEEK_OF_MONTH, -1);
		cal.set(Calendar.DAY_OF_WEEK, 2);
		String Monday = sdf_YMd.format(cal.getTime())+" 00:00:00";//上一周周一
		
		cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_MONTH, -1);
		cal.set(Calendar.DAY_OF_WEEK, 1);
		String lastSunday = sdf_YMd.format(cal.getTime())+" 23:59:59";//上上一周周日
		cal.add(Calendar.WEEK_OF_MONTH, -1);
		cal.set(Calendar.DAY_OF_WEEK, 2);
		String lastMonday = sdf_YMd.format(cal.getTime())+" 00:00:00";//上上一周周一
		list.add(lastMonday);
		list.add(lastSunday);
		list.add(Monday);
		list.add(Sunday);
		return list;
	}
	
	/**
	 *  获取当前系统时间的上月和上上月的第一天和最后一天，数组顺序为上上月第一天、上上月的最后一天、上月的第一天、上月的最后一天
	 */
	public static List<String> getMonth(){
		List<String> list = new ArrayList<String>();
		for(int i = 1; i <= Constant.TWO; i++){
		    Calendar calendar = Calendar.getInstance();
		    calendar.add(Calendar.MONTH, -i);
		    Date theDate = calendar.getTime();
		    
		    //月第一天
		    GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		    gcLast.setTime(theDate);
		    gcLast.set(Calendar.DAY_OF_MONTH, 1);
		    String day_first = sdf_YMd.format(gcLast.getTime());
		    StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
		    day_first = str.toString();

		    //月最后一天
		    calendar.add(Calendar.MONTH, 1);    //加一个月
		    calendar.set(Calendar.DATE, 1);     //设置为该月第一天
		    calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
		    String day_last = sdf_YMd.format(calendar.getTime());
		    StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
		    day_last = endStr.toString();

		    list.add(day_first);
		    list.add(day_last);
		}
		return list;
	}
}
