/**
 * @author attilax 老哇的爪子
	@since  2014-6-13 下午02:48:25$
 */
package com.focusx.oplog;

import org.apache.struts2.ServletActionContext;

import com.attilax.net.requestImp;

import cn.freeteam.action.OperLogAction;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-6-13 下午02:48:25$
 */
public class t {

	/**
	 * 
	@author attilax 老哇的爪子
		@since  2014-6-13 下午04:46:35$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  下午02:48:25   2014-6-13 
	//	ServletActionContext.setRequest(new requestImp());
		OperLogAction c=new OperLogAction();
		c.request4test=new requestImp();
		System.out.println(c.list());
		System.out.println("--f");

	}
	//  attilax 老哇的爪子 下午02:48:25   2014-6-13   
}

//  attilax 老哇的爪子