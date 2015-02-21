package com.attilax.util;

import java.util.Map;


public abstract class IdefaultMethod_safe extends tryX implements IdefaultMethod {


	public Object itemWrap(Object t) {
		try {		
			return item(t);
		} catch (Exception e) {
			System.out.println("-----catch except la ..");
			log(e);
			return this.defaultReturnValue;

		}
		
	}
	
	public abstract Object item(Object t) throws Exception;
	
	
	/**
	 * default return null 
	 * @return
	 */
	public   Object $() {
		
		// tryImpt.itemWrap("");
		//return tryImpt;
		return itemWrap("");
	   
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		IdefaultMethod_safe dms=new IdefaultMethod_safe() {
			
			@Override
			public Object item(Object t) throws Exception {
				Map m=(Map) t;
				Object tmp=m.get("xx");
				return "rt";
			}
		};
	//	System.out.println( dms.$());
		System.out.println( dms.$(" my def" ));

	}

}
