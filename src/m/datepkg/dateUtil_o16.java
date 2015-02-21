package m.datepkg;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.attilax.util.utf8编码;

@utf8编码
@Deprecated
public class dateUtil_o16 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(today());
	
		
		System.out.println("aa start");
		System.out.println(convertDate_YYYY_MM_DD("2013-10-09"));
		System.out.println("aa");
		System.out.println(convertDate_YYYY_MM_DD("2013/10/09"));
		System.out.println("aacc");
		System.out.println(convertDate_YYYY_MM_DD("20131009"));

	}
	
	public static String today() {
		Date dt=new Date();
		return format(dt, "yyyy-MM-dd");
	}

	/**
	 * 取得指定月份的第一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public String getMonthBegin(String strdate) {
		java.util.Date date = parseFormatDate(strdate);
		return formatDateByFormat(date, "yyyy-MM") + "-01";
	}

	/**
	 * 取得指定月份的最后一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public String getMonthEnd(String strdate) {
		java.util.Date date = parseFormatDate(getMonthBegin(strdate));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return formatDate(calendar.getTime());
	}

	private Date parseFormatDate(String monthBegin) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 格式化日期
	 * 
	 * @param dateStr
	 *            字符型日期
	 * @param format
	 *            格式
	 * @return 返回日期
	 */
	public static java.util.Date parseDate(String dateStr, String format) {
		java.util.Date date = null;
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat(format);
			// String dt=Normal.parse(dateStr).replaceAll(
			// "-", "/");
			String dt = dateStr;
			if ((!dt.equals("")) && (dt.length() < format.length())) {
				dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]",
						"0");
			}
			date = (java.util.Date) df.parse(dt);
		} catch (Exception e) {
		}
		return date;
	}

	public static String convertDate_YYYY_MM_DD(String dateStr) {
		dateStr=dateStr.replaceAll("-", "");
		dateStr=dateStr.replaceAll("/", "");
		java.util.Date date = null;
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat(
					"yyyyMMdd");
			// String dt=Normal.parse(dateStr).replaceAll(
			// "-", "/");
		//	String dt = dateStr;
			// if((!dt.equals(""))&&(dt.length()<format.length())){
			// dt+=format.substring(dt.length()).replaceAll("[YyMmDdHhSs]","0");
			// }
			date = (java.util.Date) df.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return formatDate(date);
	}

	public static String convertDate_YYYY_MM_DD_safe(String dateStr) {
		dateStr=dateStr.replaceAll("-", "");
		dateStr=dateStr.replaceAll("/", "");
		java.util.Date date = null;
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat(
					"yyyyMMdd");
			// String dt=Normal.parse(dateStr).replaceAll(
			// "-", "/");
			String dt = dateStr;
			// if((!dt.equals(""))&&(dt.length()<format.length())){
			// dt+=format.substring(dt.length()).replaceAll("[YyMmDdHhSs]","0");
			// }
			date = (java.util.Date) df.parse(dt);
			return formatDate(date);
		} catch (Exception e) {
			e.printStackTrace();
			// return "2099-11-11";
		}

		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat(
					"yyyy/MM/DD");
			// String dt=Normal.parse(dateStr).replaceAll(
			// "-", "/");
			String dt = dateStr;
			// if((!dt.equals(""))&&(dt.length()<format.length())){
			// dt+=format.substring(dt.length()).replaceAll("[YyMmDdHhSs]","0");
			// }
			date = (java.util.Date) df.parse(dt);
			return formatDate(date);
		} catch (Exception e) {
			e.printStackTrace();
			return "2099-11-11";
		}

	//	return formatDate(date);

	}

	public static java.util.Date parseDate(String dateStr) {
		return parseDate(dateStr, "yyyy/MM/dd");
	}

	public static java.util.Date parseDate(java.sql.Date date) {
		return date;
	}

	public static java.sql.Date parseSqlDate(java.util.Date date) {
		if (date != null)
			return new java.sql.Date(date.getTime());
		else
			return null;
	}

	public static java.sql.Date parseSqlDate(String dateStr, String format) {
		java.util.Date date = parseDate(dateStr, format);
		return parseSqlDate(date);
	}

	public static java.sql.Date parseSqlDate(String dateStr) {
		return parseSqlDate(dateStr, "yyyy/MM/dd");
	}

	public static java.sql.Timestamp parseTimestamp(String dateStr,
			String format) {
		java.util.Date date = parseDate(dateStr, format);
		if (date != null) {
			long t = date.getTime();
			return new java.sql.Timestamp(t);
		} else
			return null;
	}

	public static java.sql.Timestamp parseTimestamp(String dateStr) {
		return parseTimestamp(dateStr, "yyyy/MM/dd  HH:mm:ss");
	}

	/**
	 * 格式化输出日期
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return 返回字符型日期
	 */
	public static String format(java.util.Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				java.text.DateFormat df = new java.text.SimpleDateFormat(
						format);
				result = df.format(date);
			}
		} catch (Exception e) {
		}
		return result;
	}

	public static String format(java.util.Date date) {
		return format(date, "yyyy/MM/dd");
	}

	/**
	 * 返回年份
	 * 
	 * @param date
	 *            日期
	 * @return 返回年份
	 */
	public static int getYear(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.YEAR);
	}

	/**
	 * 返回月份
	 * 
	 * @param date
	 *            日期
	 * @return 返回月份
	 */
	public static int getMonth(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MONTH) + 1;
	}

	/**
	 * 返回日份
	 * 
	 * @param date
	 *            日期
	 * @return 返回日份
	 */
	public static int getDay(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回小时
	 * 
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回分钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回秒钟
	 */
	public static int getSecond(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.SECOND);
	}

	/**
	 * 返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 返回字符型日期
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型日期
	 */
	public static String getDate(java.util.Date date) {
		return format(date, "yyyy/MM/dd");
	}

	/**
	 * 返回字符型时间
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型时间
	 */
	public static String getTime(java.util.Date date) {
		return format(date, "HH:mm:ss");
	}

	/**
	 * 返回字符型日期时间
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型日期时间
	 */
	public static String getDateTime(java.util.Date date) {
		return format(date, "yyyy/MM/dd  HH:mm:ss");
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            日期
	 * @param day
	 *            天数
	 * @return 返回相加后的日期
	 */
	public static java.util.Date addDate(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}
	
	public static java.util.Date addSecond(java.util.Date date, int second) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) second) * 1000);
		return c.getTime();
	}

	/**
	 * 日期相减
	 * 
	 * @param date
	 *            日期
	 * @param date1
	 *            日期
	 * @return 返回相减后的日期
	 */
	public static int diffDate(java.util.Date date, java.util.Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}

	/**
	 * 日期相减
	 * 
	 * @param date
	 *            日期
	 * @param date1
	 *            日期
	 * @return 返回相减后的毫秒
	 */
	public static int diffDate_millSecs(java.util.Date date,
			java.util.Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)));
	}

	/**
	 * 常用的格式化日期
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String formatDate(java.util.Date date) {
		return formatDateByFormat(date, "yyyy-MM-dd");
	}

	/**
	 * 以指定的格式来格式化日期
	 * 
	 * @param date
	 *            Date
	 * @param format
	 *            String
	 * @return String
	 */
	public static String formatDateByFormat(java.util.Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				LOGGER.info("date:" + date);
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String convertDate_YYYY_MM_DD_safe(String trim, String string) {
		String s = "";
		try {
			s = convertDate_YYYY_MM_DD(trim);
		} catch (Exception e) {
			return string;
		}
		return s;
	}

	/**
	 * 
	 * @param i
	 * @param shutdownTime  "HH:mm"
	 * @return
	 */
	public static boolean isBeforeMin(int i, String shutdownTime) {
		Date shut_date = dateUtil_o16.parseDate(shutdownTime, "HH:mm");
		int h = shut_date.getHours();
		int m = shut_date.getMinutes();
		Date date = new Date();
		int h1 = date.getHours();
		int m2 = date.getMinutes();
		final int s2 = date.getSeconds();
		if (h == h1 && m > m2 && m - m2 <= 5)
			return true;
		else
			return false;
	}

	public static boolean isJendye() {
		Date date = new Date();
		final int s2 = date.getSeconds();
		if (s2 > 58 || s2 <= 4)
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @param i
	 * @param shutdownTime  "HH:mm"
	 * @return
	 */
	public static boolean isNotGocyi(int i, String shutdownTime) {
		Date shut_date = dateUtil_o16.parseDate(shutdownTime, "HH:mm");
		int h = shut_date.getHours();
		int m = shut_date.getMinutes();
		Date date = new Date();
		int h1 = date.getHours();
		int m2 = date.getMinutes();
		final int s2 = date.getSeconds();
		int lastMin = m - m2;

		// shut down
		float abs = Math.abs(m2 - m);
		if (h == h1 && abs < 10)
			return true;
		else
			return false;
	}
	
		public static String convertDate_YYYY_MM_DD(String trim, String string) {
		String s = "";
		try {
			s = convertDate_YYYY_MM_DD(trim);
		} catch (Exception e) {
			return string;
		}
		return s;
	}

}
