/**
 * @author attilax 老哇的爪子
	@since  2014-4-30 下午12:01:46$
 */
package test;

import java.util.Date;

import com.attilax.util.DateUtil;

/**
 * @author attilax
 *
 */
public class datex_del {

	/**
	@author attilax 老哇的爪子
		@since  2014-4-30 下午12:01:46$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// 下午12:01:46   2014-4-30 
		String dt_s=DateUtil.today();
		Date dt=DateUtil.str2date(dt_s, false);
		
		System.out.println( DateUtil.date2str(dt,true));

	}

}
