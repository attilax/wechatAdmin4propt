/**
 * @author attilax 老哇的爪子
	@since  2014-5-19 下午05:07:34$
 */
package com.attilax.wechat;

import net.sf.json.JSONObject;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-5-19 下午05:07:34$
 */
public interface Icallback<callbackRetType> {

	/**
	@author attilax 老哇的爪子
		@since  2014-5-19 下午05:08:32$
	
	 * @param jsonObject
	 */
	void $(callbackRetType jsonObject);

}

//  attilax 老哇的爪子