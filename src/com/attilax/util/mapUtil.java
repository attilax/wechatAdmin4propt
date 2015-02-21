package com.attilax.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.attilax.collection.listUtil;
import com.attilax.io.filex;



public class mapUtil {

	public static void save2file(Map<String, String> mp, String target,
			String encode) {
		List<String> li=new ArrayList<String>();
		Set<String> key = mp.keySet();
		for (Iterator it = key.iterator(); it.hasNext();) {
			String key_single = (String) it.next();
			String value = mp.get(key_single);
			String line=key_single+"\t"+value;
			li.add(line);
		}
		fileC0 fc=new fileC0();
		fc.saveList2file(li, target, encode);
		
	}
	
	//��//�Ƚϸ��ӵ�һ�ֱ���������Ǻ�~~��ܱ�fŶ����������̫ǿ�ˣ���õ�ʲô���ܵõ�ʲô~~
    public static void workByEntry(Map<String, String> map) {
        Set<Map.Entry<String, String>> set = map.entrySet();
        for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            System.out.println(entry.getKey() + "--->" + entry.getValue());
        }
    }

	public static Map toMap(String dicFilePath) {
		List li=filex.read2list_filtEmptyNstartSpace(dicFilePath);
		Map m=listUtil.reduceO4d(li, new Funcx<Object, Map>(){

			@Override
			public Map invoke(Object... o) {
				Map last=(Map) o[0];
				String cur=(String)o[1];
				String[] a=cur.split(",");
				last.put(a[0].trim(), a[1].trim());
				 
				return last;
			}});
		return m;
	}

}
