package m.datepkg;

import com.attilax.text.strUtilO36;

import m.numpkg.numUtil;

public class LOGGER {

	
	public static void main(String[] string) {
		String s2="   账单周期 Statement cycle 2013/11/15 - 2013/12/14 综合信用额度";
		
		String abcdef="abcdef";
		String fd="ab(.*?)ef";
		fd="账单周期(.*?)综合信用额度";
	String s=	strUtilO36.getMidtrings(s2, fd);
	String[] arr=s.split("-");
	String month=arr[1].trim();
	String monthx=getmonth(month);
     
	System.out.println(monthx);
		
	}
	private static String getmonth(String month) {
		 String[] a=month.split("/");
		 String y=a[0].trim();
		 y=numUtil.trim(y);
		return y+"-"+a[1].trim();
	}
	public static void info(String string) {
		// TODO Auto-generated method stub   账单周期 Statement cycle 2013/11/15 - 2013/12/14 综合信用额度
		
		String abcdef="abcdef";
		String fd="ab(.*?)ef";
	String s=	strUtilO36.getMidtrings(abcdef, fd);
	System.out.println(s);
		
	}

}
