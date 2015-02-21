package com.attilax.util;

import static com.attilax.util.buildedVariable.*;

import java.util.Map;

import com.attilax.collection.listUtil;
import com.attilax.io.filex;
import com.attilax.text.strUtil;

public class py2atian {

	private static String dic;
	private static Map m;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		sourceFile = "D:\\Users\\attilax\\Desktop\\lost.txt";
		dic = "D:\\Users\\attilax\\Desktop\\map.py2atian.txt";
System.out.println( "匾	bian".replaceAll("ang", "eo"));
		s2 = strUtil.replaceBatch("匾	bian", dic);
		System.out.println("===final:"+s2);
		if (1 == 2)
			return;
		// m=mapUtil.toMap(dic);
		li = filex.read2list(sourceFile);
		li2 = listUtil.map_generic(li, new Funcx<Object, String>() {

			@Override
			public String invoke(Object... o) {
				String item = o[0].toString();
				item = strUtil.replaceBatch(item, dic);
				return item;
			}
		});

		filex.saveList2file(li2, "c:\\lost_2atian.txt");
		System.out.println("ff");

	}

}
