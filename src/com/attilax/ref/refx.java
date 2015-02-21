package com.attilax.ref;

public class refx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println( curMethod());
//		String s="com.attilax.ref.refx";
//		refx c=(refx) newInstance(s);
//		c.t();
//		refx.t2();
//		refx.t3();
//		refx.t5();
//		refx.t6();
	}
	
	/**
	@author attilax 老哇的爪子
		@since  2014-4-18 下午04:07:08$
	
	 */
	private static void t6() {
		// 下午04:07:08   2014-4-18 
		
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-4-18 上午11:19:03$
	
	 */
	private static void t5() {
		// 上午11:19:03   2014-4-18 
		
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-4-18 上午11:14:43$
	
	 */
	private static void t4() {
		// 上午11:14:43   2014-4-18 
		
	}

	/**
	
	@author attilax 老哇的爪子
	@since  2014-2014-4-18 上午11:10:04
	 */
	private static void t3() {
	}

	/**
	 * 
	 */
	private static void t2() {
		// TODO Auto-generated method stub
		
	}

	public static String curMethod()
	{
		StackTraceElement[]	stes=Thread.currentThread().getStackTrace();
		 return Thread.currentThread().getStackTrace()[1].getMethodName(); 
	}
	public static StackTraceElement preMethod()
	{
		StackTraceElement[]	stes=Thread.currentThread().getStackTrace();
		StackTraceElement ste=stes[3];
		 return ste;
	}
	public static StackTraceElement preMethod(int level)
	{
		StackTraceElement[]	stes=Thread.currentThread().getStackTrace();
		StackTraceElement ste=stes[level];
		 return ste;
	}
	
	/**\
	 * 
	 * ，然后调用其newInstance()方法，相当于实例化（调用无参的构造函数,有参数的invoke method another ,bsi jeig..
	 * @param className
	 * @return
	 */
	public static Object newInstance(String className)
	{
	 
	  Object accpTeacher;
	try {
		accpTeacher = Class.forName(className).newInstance();
	} catch (Exception e) {
		throw new RuntimeException(e);
		 
	}
	  return accpTeacher;
	}
	
	public  void t()
	{
		System.out.println("ttt");
	}

}
