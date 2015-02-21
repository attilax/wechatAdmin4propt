package com.attilax.util;

import java.util.ArrayList;
import java.util.List;

import com.attilax.core;
import com.attilax.collection.Func;
import com.attilax.collection.listUtil;
import com.attilax.io.filex;
import com.attilax.text.strUtil;
//import com.attilax.trans.FileX;

public class findNOatianChar {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "E:\\todo oac\\grejx_def.txt";
		// final String filte_char = "()[];,、的";

		String existChar =strUtil. getexistChar("c:\\nopy.txt");
		final String filte_char = "’…“”()[];,、（）［］；，"+existChar;
		
		int num_charsFilt = strUtil.getFiltCharExistNum(filte_char, "defile	(山间)小道	2516	v.弄污，弄脏；n.	nonexsejyenonexsyaodao");
		System.out.println("==num"+num_charsFilt);
		 if(1==2)return;
		
		List<String> li = filex.read2list_filtEmpty(s);
		li=listUtil.mapx(li, new Func(){

			@Override
			public Object invoke(Object... o) {
				String s=(String)o[0];
				String[] a=s.split("\\t");
				String r=a[1]+"\t"+listUtil.getIndexValue(a, 4);
				return r;
			}});
		
		
		core.log("--get size all:" + li.size());
		li = filte(li, new Func() {

			@Override
			public Object invoke(Object... o) {
				String s = (String) o[0];
				int num_charsFilt = strUtil.getFiltCharExistNum(filte_char, s);
				int num = getNonexNum(s);
				if (num_charsFilt == num)
					return true;
				else if (!s.contains("nonex"))
					return true;

				return false;
			}

			private int getNonexNum(String s) {
				String str = "nonex";

				return strUtil.count(s, str);
			}
		});

		// li = filte(li, new Func() {
		//
		// @Override
		// public Object invoke(Object... o) {
		// String s = (String) o[0];
		// int num_charsFilt = strUtil.getFiltCharExistNum(filte_char, s);
		// int num = getNonexNum(s);
		// if (num_charsFilt == num)
		// return true;
		// else if (!s.contains("nonex"))
		// return true;
		//
		// return false;
		// }
		// });
		core.log("--li.size:" + li.size());
		filex.saveList2file(li, "c:\\li.txt");
		// map(li, new Func() {
		// @Override
		// public Object invoke(Object... o) {
		// Object o2 = o[0];
		// return (Integer) o[0] * 2;
		// }
		// });
		// System.out.println(Arrays.toString(arr));//output [2, 4, 6, 8, 10]
	 

	
	

		// com.attilax.collection.listUtil.reduce(a, new Func() {
		//
		// @Override
		// public Object invoke(Object... o) {
		// //／／sum is last value
		// Integer sum = (Integer) o[0];
		// if (o[0] == null)
		// sum = 0;
		// String sub = (String) o[1];
		// String allStr=s;
		// int thisChar_count = strUtil.count(s, sub);
		//
		// return sum + thisChar_count;
		// }
		// });
		//return sum;
		//return null;
	}

	private static List<String> filte(List<String> objLi, Func func) {
		List li = new ArrayList();
		for (int i = 0; i < objLi.size(); i++) {
			String string = objLi.get(i);
			boolean o = (Boolean) func.invoke(string);
			if (!o)
				li.add(string);
		}
		return li;
	}

	public static void map(Object li, Func function) {
		if (li instanceof List) {
			List objLi = (List) li;
			for (int i = 0; i < objLi.size(); i++) {
				Object o = function.invoke(objLi.get(i));
				objLi.remove(i);
				objLi.add(i, o);
			}
		} else {
			Object[] objLi = (Object[]) li;
			for (int i = 0; i < objLi.length; i++) {
				objLi[i] = function.invoke(objLi[i]);
			}
		}
	}

}
