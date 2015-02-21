package m.except;


import java.util.ArrayList;
import java.util.List;

import m.autoshutdown;

import org.apache.log4j.Logger;

import com.attilax.util.god;

 
@SuppressWarnings("all")
@Deprecated
public abstract class tryX<t> {

	tryX curTryx;
	// private Logger loger;
	private String para1;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String x = new tryX<String>() {

			@Override
			public String item(Object t) throws Exception {
				// int i=(Integer) t;
				return "my def";
			}
		}.$("my ");
		System.out.println(x);
	}

	// public abstract void m() ;

	public t itemWrap(Object t) {
		try {
			return item(t);
		} catch (Exception e) {
			System.out.println("-----catch except la ..");
			log(e);
			return this.defaultReturnValue;

		}

	}

	public void setPara1(String s) {
		this.para1 = s;
	}

	public abstract t item(Object t) throws Exception;

	/**
	 * @deprecated
	 * @param tryImpt
	 * @return
	 */
	public static Object tryStart(tryX tryImpt) {

		// tryImpt.itemWrap("");
		tryImpt.curTryx = tryImpt;
		return tryImpt.itemWrap("");
		// return tryImpt;
	}

	/**
	 * @deprecated
	 * @param tryImpt
	 * @return
	 */
	// public static Object $(tryX tryImpt) {
	//		
	// // tryImpt.itemWrap("");
	// tryImpt.curTryx = tryImpt;
	// return tryImpt.itemWrap("");
	// // return tryImpt;
	// }

	/**
	 * set default value
	 * @param  
	 * @return
	 */
	public t $(t defaultValue) {
		this.defaultReturnValue = defaultValue;

		return this.itemWrap("");
	}

//	 public t $() {
//			
//	 t o = (t)new Object();
////	 t tObj = o.newInstance();
//	 if( o instanceof List )
//	 this.defaultReturnValue= (t) new ArrayList();
//	 else if (o instanceof String)
//	 this.defaultReturnValue=(t) " raw str";
//		 
//	 return this.itemWrap("");
//	 }

	// public tryX setLogger(Logger logger) {
	// this.loger = logger;
	// return this;
	// }

	// public Object end () {
	// return this.itemWrap("");
	//
	// }

	private Object getObj() {
		// TODO Auto-generated method stub
		return null;
	}
	public static Logger logger = Logger.getLogger(tryX.class);
	public Object log(Exception e) {
		logger.error(god.getTrace(e));
		return e;
	}

	public t defaultReturnValue;
	// public Object $() {
	// return this.itemWrap("");
	// };

}
