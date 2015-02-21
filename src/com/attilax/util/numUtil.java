package com.attilax.util;

import java.math.BigDecimal;

import com.attilax.collection.Ireduce;
import com.attilax.collection.list;
import com.attilax.collection.listUtil;

public class numUtil {
	
	public static void main(String[] args) {
		
	System.out.println(pad0(3, 2));	;
	}

	public static String trim(String text) {
		return	 text.trim().replaceAll("-", "").replaceAll(",", "").replaceAll("[^\\d\\.]", "").trim();
	//	 null;
	}
	
	public static double toPrice(Object f) {
		double   fx   = 0;
		if(f instanceof Float)
			fx=new Double(f.toString());
		
 
		BigDecimal   b   =   new   BigDecimal(fx);
		double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return f1;
	}
	
	public static float toPrice2(Object f) {
		double   fx   = 0;
		if(f instanceof Float)
			fx=new Double(f.toString());

		 java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
	String s=	df.format(f);
	return new Float(s);
	}
//	public static double toPrice2(Object f) {
//		double   fx   = 0;
//		if(f instanceof Float)
//			fx=new Double(f.toString());
//
//		 java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
//	String s=	df.format(fx);
//	return new Double(s);
//	}

	/**
	 * @param o
	 * @param i
	 * @return
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-23 下午4:33:17$
	 */
	public static String pad0(Integer o, int i) {
		// attilax 老哇的爪子  下午4:33:17   2014-6-23 
		int pad=i-o.toString().length();
		list<Integer> li=new list<Integer>(pad);
		return listUtil.reduceO6(li.toList(),o.toString(), new Ireduce<Integer, String>() {

			@Override
			public String $(Integer o, String lastRetOBj) {
				// attilax 老哇的爪子  下午4:38:44   2014-6-23 
				lastRetOBj="0"+lastRetOBj;
				return lastRetOBj;
			}
		});
				
				
				
//				new sam<String>(){
//
//			@Override
//			public String $(Object t) {
//				// attilax 老哇的爪子  下午4:36:39   2014-6-23 
//				for(int i=0;i<pad;i++){
//					
//				}
//				return null;
//			}}.$();
//		return null;
	}
	
	
	
	

}
