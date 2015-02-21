//package com.attilax.util;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.attilax.collection.Func;
//import com.attilax.collection.listUtil;
//import com.attilax.io.filex;
//
//public class geneCharPhonieList {
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		String f="D:\\Users\\attilax\\Desktop\\3500.txt";
//		String s=filex.read(f,"utf-8");
//		String[] a=s.split(",");
//		Object obj=a;
//		List li = new ArrayList();
//		listUtil.map(a, new Func(){
//
//			@Override
//			public Object invoke(Object... o) {
//				String charx=(String) o[0];
//				String py = ChineseToPinYin.getPingYin(charx);
//				return charx+","+py;
//			}
//			
//		});
//		li=listUtil.toList(a);
//		filex.saveList2file(li, "c:\\chPy.txt");
//		System.out.println("--");
//		
//		System.out.println( ChineseToPinYin.getPingYin("以"));
//	//	return py;
//
//	}
//
//}
