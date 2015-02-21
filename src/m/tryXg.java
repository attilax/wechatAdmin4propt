package m;

 

 
import java.util.List;

//import m.cc.hsbc;

import org.apache.log4j.Logger;

import com.attilax.util.god;

 
public abstract class tryXg<参数返回类型> {

	tryX curTryx;
//	private Logger loger;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	 
		new tryXg<String>(){

			@Override
			public String item(Object t) throws Exception {
				
				return null;
			}
			
		}.$(null);
		
		
	}

	// public abstract void m() ;

	public 参数返回类型 itemWrap(Object t) {
		try {		
			return item(t);
		} catch (Exception e) {
			System.out.println("-----catch except la ..");
			log(e);
			return this.defaultReturnValue;

		}
		
	}

	public abstract 参数返回类型 item(Object t) throws Exception;

	/**
	 * @deprecated
	 * @param tryImpt
	 * @return
	 */
	public static Object tryStart(tryX tryImpt) {
	
		// tryImpt.itemWrap("");
		//tryImpt.curTryx = tryImpt;
		return tryImpt.itemWrap("");
	   //	return tryImpt;
	}
	
	/**
	 *  deprecated
	 * @param tryImpt
	 * @return
	 */
//	public static Object $(tryX tryImpt) {
//		
//		// tryImpt.itemWrap("");
//		tryImpt.curTryx = tryImpt;
//		return tryImpt.itemWrap("");
//	   //	return tryImpt;
//	}
	
	public 参数返回类型 $(参数返回类型 string) {
		this.defaultReturnValue=string;
	 
		return this.itemWrap("");
	} 

//	public tryX setLogger(Logger logger) {
//		this.loger = logger;
//		return this;
//	}

//	public Object end	() {
//		return this.itemWrap("");
//
//	}
	public Logger logger = Logger.getLogger(tryX.class.getName());
	
	public Object log(Exception e) {
		logger.error(god.getTrace(e));
		return e;
	}
public  参数返回类型 defaultReturnValue;
//	public Object $() {
//		return this.itemWrap("");
//	};

}
