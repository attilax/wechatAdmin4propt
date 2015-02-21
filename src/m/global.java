package m;

import java.util.concurrent.Callable;

import net.sf.json.JSONArray;

//import  static m.global.*;
public class global {
	 

	public static void main(String[] args) {
		//$("aaa").isEmpty()

	}
	
	// uter:()
	public static <T> T $(T t) {
			 return t;

		}
	
	public static <T> T var_dump(T t) {
		String s = JSONArray.fromObject(t).toString(2);
		System.out.println(s);
		 return t;

	}
	
	public interface Icall {
	public <T> Object	callbackMethod(T obj);		
		 
	}
	
	public static void callbackTest(Icall t) {
		 t.callbackMethod("halo o129");

	}
	
	public <T> global multiMethod1(T t)
	{
		System.out.println( " mtd1 ");
		return this;
		
	}
	public <T> global multiMethod2(T t)
	{
		System.out.println( " mtd2 ");
		return this;
		
	}
	public <T> global multiMethod3(T t)
	{
		System.out.println( " mtd3 ");
		return this;
		
	}


}
