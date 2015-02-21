/**
 * @author attilax 老哇的爪子
	@since  2014-5-16 下午03:59:43$
 */
package com.attilax.html;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ecs.html.Option;
import org.apache.ecs.html.Select;

import com.attilax.core;
import com.attilax.collection.listUtil;
import com.attilax.net.websitex;
import com.attilax.util.Func_4SingleObj;
import com.attilax.util.Func_wzPre;
import com.focusx.service.impl.Fun_4reduce;

/**
 * @author attilax 老哇的爪子
 *@since 2014-5-16 下午03:59:43$
 */
public class selectx {

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-5-16 下午03:59:55$
	 * 
	 * @param string
	 * @param ops_sFmt
	 * @return
	 */
	public static <atiIptType, atiRetType, atiPreInType, atiPreRetType> String gene(
			String selectID,
			String ops_jsonFmt,
			Func_wzPre<atiIptType, atiRetType, atiPreInType, atiPreRetType> func_4SingleObj) {
		// attilax 老哇的爪子 下午03:59:55 2014-5-16
		final Select sl = new Select();
		sl.addAttribute("id", selectID);	sl.addAttribute("name", selectID);
		List<atiRetType> li = listUtil.map_wzPre((atiPreInType) ops_jsonFmt,
				func_4SingleObj);
		for (atiRetType o : li) {
			sl.addElement((Option) o);
		}

		return sl.toString();

		// (r, "", new Fun_4reduce<JSONObject, String>(){
		//
		// @Override
		// public String $(JSONObject o, String lastRetOBj) {
		// // attilax 老哇的爪子 下午03:55:43 2014-5-16
		//			
		//				 
		// return lastRetOBj + opt.toString();
		//				 
		// }
		//			
		// });

		// return null;
	}

	public static String r_cache;
	
	// attilax 老哇的爪子 下午03:59:44 2014-5-16

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-5-16 下午04:10:36$
	 * 
	 * @param url
	 * @param func_4SingleObj
	 * @param string
	 * @return
	 */
	public synchronized  static <atiIptType, atiRetType> String fromUrl(String url,
			String selectID,
			Func_wzPre<JSONObject, Option, String, JSONArray> func_4SingleObj) {
		// attilax 老哇的爪子 下午04:10:36 2014-5-16
		String r ;
		 
		if(r_cache!=null)
		{
			r=r_cache;
			core.log("--cache is ok ..form cache");
		}
		else{
			r= websitex.WebpageContent(url);
			core.log("-- url return :" + r);
			JSONObject jo=JSONObject.fromObject(r);
			if(jo.get("errcode")==null)
				r_cache=r;
		}
		
		JSONObject jo=JSONObject.fromObject(r);
		if(jo.get("errcode")!=null)
			throw new RuntimeException("errO692:"+r);
		
		
		
		return gene(selectID, r, func_4SingleObj);
	}
}

// attilax 老哇的爪子