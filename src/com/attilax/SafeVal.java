/**
 * @author attilax 老哇的爪子
	@since  2014-4-28 下午01:11:57$
 */
package com.attilax;

import com.attilax.util.tryX;

/**
 * @author attilax
 *
 */
public class SafeVal {

	/**
	@author attilax 老哇的爪子
		@since  2014-4-28 下午01:12:18$
	
	 * @param awardCount
	 * @param i
	 * @return
	 */
	public static int val(Integer awardCount, int i) {
		// 下午01:12:18   2014-4-28 
		if(awardCount==null)return i;
		return awardCount;
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-13 下午09:46:49$
	
	 * @param string
	 * @param deftVal
	 */
	public static String val(String string, String deftVal) {
		if(string==null)return deftVal;
		return string;
		// attilax 老哇的爪子  下午09:46:49   2014-5-13 
		
	}
	
	/**
	 * 
	@author attilax 老哇的爪子
		@since  2014-6-3 下午06:09:18$
	
	 * @param string
	 * @return
	 */
	public static String val(String string) {
		if(string==null)return "";
		return string;
		// attilax 老哇的爪子  下午09:46:49   2014-5-13 
		
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-6-17 下午03:47:28$
	
	 * @param newsId
	 * @return
	 */
	public static String val(final Integer newsId) {
		// attilax 老哇的爪子  下午03:47:28   2014-6-17 
		return new tryX<String>(){

			@Override
			public String item(Object t) throws Exception {
				// attilax 老哇的爪子  下午03:48:32   2014-6-17 
				return newsId.toString();
			}}.$("");
	}

}
