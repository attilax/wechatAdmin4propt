package com.attilax.util;

import java.util.List;

import com.attilax.core;
import com.attilax.collection.Func;
import com.attilax.collection.listUtil;
import com.attilax.io.filex;

public class findSingleCharINexportIM {

	/**
	 * 户名 accname#序1024 o4a
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String f = "c:\\exportim.txt";
		List<String> li = filex.read2list(f);
		core.log("--get size all:" + li.size());
		li = listUtil.filter(li, new Func() {

			@Override
			public Object invoke(Object... o) {
				String s = (String) o[0];

				if (s.startsWith("--"))
					return true;

				return false;
			}
		});

		li = listUtil.mapx(li, new Func() {

			@Override
			public Object invoke(Object... o) {
				String s = (String) o[0];
				int Shap = s.indexOf("#");
				return s.subSequence(0, Shap);
			}

		});

		li = listUtil.filter(li, new Func() {

			@Override
			public Object invoke(Object... o) {
				String s = (String) o[0];
				String[] a = s.split("\\t");
				if (a[0].trim().length() > 1)
					return true;

				return false;
			}
		});

		filex.saveList2file(li, "c:\\filt_Sub.txt");

	}

}
