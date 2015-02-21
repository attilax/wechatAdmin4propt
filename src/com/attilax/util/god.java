package com.attilax.util;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.concurrent.FutureTask;

import com.attilax.util.Helper;


@SuppressWarnings("deprecation")
public class god  extends  god1{

//	public static Dbcontroller dbcWithDs;//=new DbNdsController();
	public static String sincinlib="sincinlib";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static boolean isInt(String filename) {

 
		return isNumeric(filename);
	}
	
	public static boolean isNumeric(String str){
		  for (int i = str.length();--i>=0;){   
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
		 }
	 public static String getTrace(Throwable t) {
	        StringWriter stringWriter= new StringWriter();
	        PrintWriter writer= new PrintWriter(stringWriter);
	        t.printStackTrace(writer);
	        StringBuffer buffer= stringWriter.getBuffer();
	        return buffer.toString();
	    }
	 public static String getUUid( ) {
		 java.util.Date   dt   =   new   java.util.Date(System.currentTimeMillis());  
	     SimpleDateFormat   fmt   =   new   SimpleDateFormat("MMdd_HHmmss_SSS");  
	     String   fileName=   fmt.format(dt);  
	     return fileName;
	 }
	 
	 public static String getUUid_MMdd_HHmmss( ) {
		 String month36atifmt="";
		 java.util.Date   dt   =   new   java.util.Date(System.currentTimeMillis());  
	if(	 dt.getMonth()==12)
		 month36atifmt="b";
	else  
		 month36atifmt=String.valueOf(dt.getMonth());
	
	 
		
		
	     SimpleDateFormat   fmt   =   new   SimpleDateFormat("MMdd_HHmm_ss");  
	     String   fileName=   fmt.format(dt);  
	     return fileName;
	 }

	public static Object getThreadResult(FutureTask futureTask) {
		try {
			Object	t =   futureTask.get();
			return t;
			// future.get(5000, TimeUnit.MILLISECONDS); //取得结果，同时设置超时执行时间为5秒。
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
		
		
	}

	public static Thread newThread(Runnable runnable, String threadName) {
		Thread Thread_ini_fentsiController = new Thread(runnable);
		Thread_ini_fentsiController.setName(threadName);
		Thread_ini_fentsiController.setPriority(Thread.MAX_PRIORITY);
		Thread_ini_fentsiController.start();
		return Thread_ini_fentsiController;
	}
	
	public static String getClassPath( ) {
		
		return   god.class.getResource("/").getPath().replaceAll("%20", " ");
	}

	public static String getUUid4msg(String userid, String msg,
			String datetime) {
		
		return Helper.MD5 (userid+msg+datetime);
	}
	
	public static String xx(String userid, String msg,
			String datetime) {
		
		return Helper.MD5 (userid+msg+datetime);
	}
	
		   
	

}
