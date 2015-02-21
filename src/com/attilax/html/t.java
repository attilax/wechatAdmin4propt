/**
 * @author attilax 老哇的爪子
	@since  2014-5-14 下午02:29:58$
 */
package com.attilax.html;

import javax.servlet.http.HttpServletRequest;

import org.apache.ecs.html.Input;
import org.apache.ecs.html.Select;

import com.attilax.net.requestImp;
import com.focusx.service.impl.BranchManagerServiceImpl;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-5-14 下午02:29:58$
 */
@utf8编码
public class t {

	/**
	@author attilax 老哇的爪子
		@since  2014-5-14 下午02:29:58$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  下午02:29:58   2014-5-14 
		  Input input = new Input();
		  Select slkt=new Select();
		  slkt.addAttribute("1", "1aa");
		  slkt.appendOption("2", "2txt");
		  System.out.println(slkt.toString());
		  //  <select 1='1aa'><option value='2txt' label='2'></option></select>
		  
		  HttpServletRequest request=new requestImp();
	String s=	  BranchManagerServiceImpl.list_sltFmt(request,"subcomid");
	System.out.println(s);

	}
	//  attilax 老哇的爪子 下午02:29:58   2014-5-14   
}

//  attilax 老哇的爪子