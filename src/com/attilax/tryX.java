package com.attilax;

import java.util.ArrayList;
import java.util.List;

import org.apache.ecs.xhtml.object;
import org.apache.log4j.Logger;
//import org.springframework.aop.ThrowsAdvice;

import com.attilax.core;
import com.attilax.util.god;

@SuppressWarnings("all")
public abstract class tryX<atiType> {
public	Object para1x;
	public <atiType> tryX(Object para1_x)
	{
		  para1x=para1_x;
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-28 下午01:28:11$
	
	 */
	public tryX() {
		// TODO Auto-generated constructor stub
	}

	tryX curTryx;
	// private Logger loger;
	private String para1;
	private boolean log4err = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String x = new tryX<String>() {

			@Override
			public String $$(Object t) throws Exception {
				// int i=(Integer) t;
				return "my def";
			}
		}.$("my ");
		System.out.println(x);
	}
  public String errmsg="..";
  public boolean flagErr=false;
	// public abstract void m() ;
	@Deprecated
	public atiType itemWrap(Object t) {
		try {
			return $$(t);
		} catch (Exception e) {
			log(e);
			this.errmsg=god.getTrace(e);
			this.flagErr=true;
			return this.defaultReturnValue;

		}

	}
	
	@Deprecated
	public atiType itemWrap4retErrmsg(Object t) {
		try {
			return $$(t);
		} catch (Exception e) {
			log(e);
			this.errmsg=god.getTrace(e);
			this.flagErr=true;
			return (atiType)this.errmsg;

		}

	}
	
	/**
	 * yva error getTrace(e);
	@author attilax 老哇的爪子
		@since  2014-6-9 下午05:19:38$
	
	 * @return
	 */
	public atiType $defValIsErrmsg() {
		// attilax 老哇的爪子  上午11:02:11   2014-5-28 
		return this.itemWrap4retErrmsg("");
//		this.itemWrap("");
//		if(this.flagErr)
//		{
//		this.defaultReturnValue = (atiType) this.errmsg;
//		return  (atiType) this.errmsg;
//		}return this.defaultReturnValue;
	//	return null;
	}

	public atiType $defValIsErrmsgSmp() {
		// attilax 老哇的爪子  下午06:42:00   2014-6-9 
		  return this.itemWrap4retErrmsgSmp(""); 
	}
	
	/**
	@author attilax 老哇的爪子
		@since  2014-6-9 下午06:42:50$
	
	 * @param string
	 * @return
	 */
	private atiType itemWrap4retErrmsgSmp(Object t) {
		// attilax 老哇的爪子  下午06:42:50   2014-6-9 
		try {
			return $$(t);
		} catch (Exception e) {
			log(e);
			this.errmsg=e.getMessage();
			return (atiType)this.errmsg;

		}

	 
	}

	@Deprecated
	public void setPara1(String s) {
		this.para1 = s;
	}

	public abstract atiType $$(Object t) throws Exception;

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
	 * log err2warn and catch
	 * @param
	 * @return
	 */
	public atiType $(atiType defaultValue) {
		this.defaultReturnValue = defaultValue;

		return this.itemWrap("");
	}
	
	/**
	 * only log except and  rethrow
	@author attilax 老哇的爪子
		@since  2014-5-9 上午09:59:42$
	
	 * @return
	 */
	public atiType $() {
		// attilax 老哇的爪子  上午09:58:58   2014-5-9 
	
		try {
			return $$("");
		} catch (Exception e) {
			this.log4err = true;
			log(e);

			throw new RuntimeException(e);

		}
	}

	/**
	 * 
	 * @category   defaultValue_log4Err
	 * o3i invoke if ex show err log, other same Name Method is warn
	 * log err2err and catch
	 * @param defaultValue
	 * @return
	 */
	public atiType $(atiType defaultValue, int log4Err) {
		this.defaultReturnValue = defaultValue;
		this.log4err = true;
		return this.itemWrap("");
	}

	// public t $() {
	//			
	// t o = (t)new Object();
	// // t tObj = o.newInstance();
	// if( o instanceof List )
	// this.defaultReturnValue= (t) new ArrayList();
	// else if (o instanceof String)
	// this.defaultReturnValue=(t) " raw str";
	//		 
	// return this.itemWrap("");
	// }

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

	public Object log(Exception e) {
		// MoodUserIndexService.logger.error(god.getTrace(e));
		if (log4err) {
			core.logger.error(god.getTrace(e));
			return e;
		}
		core.logger.warn("-----catch except la ..");
		core.logger.warn(god.getTrace(e));
		return e;
	}

	public atiType defaultReturnValue;
	// public Object $() {
	// return this.itemWrap("");
	// };

}
