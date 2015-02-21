/**
 * @author attilax 老哇的爪子
	@since  2014-5-19 上午11:27:54$
 */
package com.attilax.corePkg;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-5-19 上午11:27:54$
 */
public class JSONObject {
	//  attilax 老哇的爪子 上午11:27:54   2014-5-19   
	
	net.sf.json.JSONObject jo=new net.sf.json.JSONObject();

	public void put(String key, Object value) {
		// attilax 老哇的爪子  上午11:29:12   2014-5-19 
		if(value instanceof JSONArray)
		{
			JSONArray ja=(JSONArray) value;
			jo.put(key,ja.jo);
		}else
		jo.put(key, value);
	}
	
	public net.sf.json.JSONObject $() {
		// attilax 老哇的爪子  上午11:29:12   2014-5-19 
		return jo;
		
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-26 下午02:08:06$
	
	 * @param i
	 * @return
	 */
	public String toString(int i) {
		// attilax 老哇的爪子  下午02:08:06   2014-5-26 
		return jo.toString(i);
	}
}

//  attilax 老哇的爪子