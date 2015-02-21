/**
 * @author attilax 老哇的爪子
	@since  2014-5-26 下午01:17:40$
 */
package com.attilax.corePkg;

import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-5-26 下午01:17:40$
 */
public class afd {

	/**
	@author attilax 老哇的爪子
		@since  2014-5-26 下午01:17:42$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  下午01:17:42   2014-5-26 
		JSONObject o=new JSONObject();
		o.put("kk", "vvv");
		JSONArray ja=new JSONArray();
		ja.add(o);
		Iterator it=((List)ja).iterator();
		System.out.println(it.hasNext());

	}
	//  attilax 老哇的爪子 下午01:17:42   2014-5-26   
}

//  attilax 老哇的爪子