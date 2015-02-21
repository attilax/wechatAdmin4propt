//package com.attilax.util;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//
//import net.sourceforge.pinyin4j.PinyinHelper;
//import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
//import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
//import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
//import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
//import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
//
//public class ChineseToPinYin {
//	public static String atianWordlib;
//
//	public static String getPingYin(String src) {
//		char[] t1 = null;
//		t1 = src.toCharArray();
//		String[] t2 = new String[t1.length];
//		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
//		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
//		String t4 = "";
//		int t0 = t1.length;
//		try {
//			for (int i = 0; i < t0; i++) {
//				// 判断是否为汉字字符
//				if (java.lang.Character.toString(t1[i]).matches(
//						"[\\u4E00-\\u9FA5]+")) {
//					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
//					t4 += t2[0];
//				} else
//					t4 += java.lang.Character.toString(t1[i]);
//			}
//			// System.out.println(t4);
//			return t4;
//		} catch (BadHanyuPinyinOutputFormatCombination e1) {
//			e1.printStackTrace();
//		}
//		return t4;
//	}
//
//	// 返回中文的首字母
//	public static String getPinYinHeadChar(String str) {
//		String convert = "";
//		for (int j = 0; j < str.length(); j++) {
//			char word = str.charAt(j);
//			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
//			if (pinyinArray != null) {
//				convert += pinyinArray[0].charAt(0);
//			} else {
//				convert += word;
//			}
//		}
//		return convert;
//	}
//
//	// 将字符串转移为ASCII码
//	public static String getCnASCII(String cnStr) {
//		StringBuffer strBuf = new StringBuffer();
//		byte[] bGBK = cnStr.getBytes();
//		for (int i = 0; i < bGBK.length; i++) {
//			// System.out.println(Integer.toHexString(bGBK[i]&0xff));
//			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
//		}
//		return strBuf.toString();
//	}
//
//	public static void convert(String source, String encode, String target) {
//		fileC0 fc = new fileC0();
//		List<String> lir = new ArrayList<String>();
//		List<String> li = fc.fileRead2list(source, encode);
//		for (String line : li) {
//			String line_pinyin = getPingYin(line);
//			lir.add(line_pinyin);
//		}
//		fc.saveList2file(lir, target, encode);
//
//	}
//
//	public static void convert2atian(String source, String encode,
//			String target) {
//		fileC0 fc = new fileC0();
//		List<String> lir = new ArrayList<String>();
//		List<String> li = fc.fileRead2list(source, encode);
//		for (String line : li) {
//			String line_pinyin = getAtian(line);
//			lir.add(line_pinyin);
//		}
//		fc.saveList2file(lir, target, encode);
//
//	}
//
//	private static String getAtian(String line) {
//		
//		String target ="";
//		if(atianWordlib==null)
//				target="c:\\deeng\\hezi_deeng_deduli.txt";
//				else
//					target=atianWordlib;
//		String path=target;
//	String encode="gbk";
//	Map map=	listUtil.fromFile(path,encode,"\t");
//		String r="";
//		char[] ca=line.toCharArray();
//		for(char c:ca)
//		{
//			String s=java.lang.Character.toString(c);
//			// 判断是否为汉字字符
//			if (s.matches("[\\u4E00-\\u9FA5]+"))
//			{
//				String atian=(String) map.get(s);
//				if(atian==null)
//					atian=s;
//				r=r+atian;
//				
//			}else
//				r=r+s;
//				
//		}
//		
//		return r;
//	}
//
//	public static void main(String[] args) {
//		String cnStr = "中华人民共和国";
//		System.out.println(getPingYin(cnStr));
//		System.out.println(getPinYinHeadChar(cnStr));
//		String s = "f:\\Contact_QQPhoneManager(2013-12-03).csv";
//	 	convert2atian(s, "gb2312", "f:\\Contact_QQPhoneManager_atian.csv");
//		String ss= getAtian("aa人民a");
//		System.out.println(ss);
//		
//		System.out.println("ok");
//		// convert();
//	}
//}