/**
 * @author attilax 老哇的爪子
	@since  2014-6-27 下午3:02:48$
 */
package com.attilax.err;
import com.alibaba.fastjson.JSONObject;
import com.attilax.core;
 import static  com.attilax.core.*;
import java.util.*;
import java.net.*;
import java.io.*;
/**
 * @author  attilax 老哇的爪子
 *@since  2014-6-27 下午3:02:48$
 */
public class errx {

	/**
	@author attilax 老哇的爪子
		@since  2014-6-27 下午3:02:57$
	
	 * @return
	 */
	public static String defOK() {
	// attilax 老哇的爪子  下午3:02:57   2014-6-27 
	Map<Object, Object> retMap = new HashMap<Object, Object>();
	retMap.put("code", "ok");
	retMap.put("msg", "ok msg..");

	String jsonStr = JSONObject.toJSONString(retMap);
	return jsonStr;
	
	}
	//  attilax 老哇的爪子 下午3:02:48   2014-6-27   
}

//  attilax 老哇的爪子