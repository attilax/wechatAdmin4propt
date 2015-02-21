package com.attilax.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import org.apache.commons.collections.map.LinkedMap;



public class deEng {

	public static void main(String[] args) {

		// deeng();
		deDuli();
		System.out.println("ok");
	}

	private static void deDuli() {
		Set set = new HashSet<String>();
		Map<String, String> mp = new LinkedHashMap<String, String>();
		String source = "c:\\deeng\\hezi_deeng.csv";
		String target = "c:\\deeng\\hezi_deeng_deduli.txt";
		fileC0 fc = new fileC0();
		String encode = "gbk";
		List<String> li = fc.fileRead2list(source, encode);

		for (String line : li) {
			if(line.trim().length()==0)
				continue;
			String[] a = line.split("\t");
			String hezi = a[0];
			String py = a[1];
			String pyFromMap = mp.get(hezi);
			if (pyFromMap == null) {
				mp.put(hezi, py);
			} else {
				if (py.length() < pyFromMap.length())
					mp.put(hezi, py);
			}
		}

		
		// List<String> li2= new ArrayList( mp);
		 mapUtil.save2file(mp, target, encode);
		 

	}

	private static void deeng() {
		fileC0 fc = new fileC0();
		List<String> lir = new ArrayList<String>();
		String source = "c:\\deeng\\hezi.txt";
		String encode = "gbk";
		String eng_dic = "c:\\deeng\\大学英语六级词汇.txt";

		List<String> li = fc.fileRead2list(source, encode);
		Set<String> set_eng_dic = fc.fileRead2Set(eng_dic, encode);

		Set set_eng2 = toset("c:\\deeng\\英汉词典TXT格式 (2).txt");
		for (String line : li) {
			String[] a = line.split("\t");
			String py = a[1];
			if (!set_eng_dic.contains(py))
				if (!set_eng2.contains(py))
					lir.add(line);
		}

		String target = "c:\\deeng\\hezi_deeng.csv";
		fc.saveList2file(lir, target, encode);
	}

	private static Set toset(String string) {
		Set set = new HashSet<String>();
		fileC0 fc = new fileC0();
		List<String> lir = new ArrayList<String>();
		List<String> li = fc.fileRead2list(string, "gbk");
		for (String line : li) {
			String[] a = line.split(" ");
			for (String word : a) {
				set.add(word);
			}

		}
		return set;
	}

}
