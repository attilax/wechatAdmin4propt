/**
 * @author attilax 老哇的爪子
	@since  2014-6-9 下午03:35:36$
 */
package com.attilax;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.attilax.io.filex;
import com.attilax.util.randomx;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-6-9 下午03:35:36$
 */
public class retryT {

	/**
	@author attilax 老哇的爪子
		@since  2014-6-9 下午03:35:37$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子 下午03:35:37 2014-6-9

		List li=new ArrayList<Boolean>();
		String s = new retry<String>(3,li) {

			@Override
			public Boolean item(Object t) throws Exception {
				// attilax 老哇的爪子 下午03:43:07 2014-6-9
				int n = randomx.random(50);
				core.log("=="+String.valueOf(n));
				if (n < 40)
					return false;
				this.result="rezt";
				return true;
			}

		

		}.$();
		System.out.println(li.get(0));
		System.out.println(s);
		System.out.println(File.separator);
		System.out.println("fname:::"+filex.getFileName("c:\\hah\\kak.fil"));
		System.out.println(filex.getFileName("http://jmszcy99217.eicp.net/weixin//news/image/303a8a38-746c-46c0-9efe-68389985551e.jpg"));
		System.out.println("fname:::"+filex.getFileName("haha.jgp"));
	}
	//  attilax 老哇的爪子 下午03:35:37   2014-6-9   
}

//  attilax 老哇的爪子