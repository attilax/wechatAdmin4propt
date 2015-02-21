package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import net.sf.json.JSONArray;

import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.focusx.util.Constant;
import com.focusx.util.JsonUtil;

public class P {
	public static void main(String[] args) throws IOException {
		
		try {
			
			StringBuilder builder = new StringBuilder();
			builder.append(Constant.path).append("menu.properties");
			HashMap<String,String> map = new HashMap<String,String>();
			File f = new File(builder.toString());
			if(f.exists()){
				FileInputStream fis = new FileInputStream(f);
				Properties properties =  new Properties();
				properties.load(fis);
				Set set = properties.keySet();
				Iterator<String> it = set.iterator();  
				while (it.hasNext()) {
					String key = it.next();
					map.put(key, properties.getProperty(key));
				}
			}
			JSONArray jsonArray = JSONArray.fromObject(map);
			String json_data = jsonArray != null ? jsonArray.toString() : "";
			System.out.println(json_data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
