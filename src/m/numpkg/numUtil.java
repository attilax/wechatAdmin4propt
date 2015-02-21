package m.numpkg;

import java.math.BigDecimal;

public class numUtil {

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
	
	
	
	

}
