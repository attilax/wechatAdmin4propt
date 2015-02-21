package m.util;

import java.lang.reflect.Method;

//@anno1(para = "val_123")
public class tAnno {
	
	
	@sql(  "select * form tt")
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {

//			Class[] parameterTypes = new Class[1];
//			parameterTypes[0] = String[].class;
			Class[] parameterTypes2={String[].class};
		Method m=	tAnno.class.getMethod(Thread.currentThread().getStackTrace()[1].getMethodName(), String[].class);
		sql a2	=m.getAnnotation(sql.class);
		System.out.println(a2.value());
	
		
		
		
//		anno1 a=	Thread.currentThread().getStackTrace()[1].getClass().getAnnotation(anno1.class);
//		System.out.println(a.para());
//		
		
		// tAnno.class.getAnnotations()
	 
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName(); 
		System.out.println(methodName);
	
		//m.util.tAnno
		System.out.println(	Thread.currentThread().getStackTrace()[1].getClassName());
		 

	}
	
	 

}
