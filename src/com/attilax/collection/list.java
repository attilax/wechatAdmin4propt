/**
 * @author attilax 老哇的爪子
	@since  2014-6-11 下午03:04:03$
 */
package com.attilax.collection;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.focusx.entity.weixin.pojo.Button;


/**
 * @author  attilax 老哇的爪子
 *@since  2014-6-11 下午03:04:03$
 */
public class list<EE>  
{
	java.util.ArrayList<EE> al=new java.util.ArrayList<EE>();
	/**
	 * @param i
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-23 下午3:30:58$
	 */
	public list(Integer ix) {
		for(Integer i=1;i<=ix;i++)
		{
			this.add((EE) i);
		}
	}
	/**
	@author attilax 老哇的爪子
		@since  2014-7-2 上午2:12:29$
	
	 */
	public list() {
	//  attilax 老哇的爪子 上午2:12:29   2014-7-2   
	}
	/* (non-Javadoc)
	 * @see java.util.List#add(java.lang.Object)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-11 下午03:06:46$
	 */
	public list add(EE arg0) {
		// attilax 老哇的爪子  下午03:06:46   2014-6-11 
		al.add(arg0);
		return this;
	}
	/**
	@author attilax 老哇的爪子
		@since  2014-6-11 下午03:10:27$
	
	 * @return
	 */
	public List<EE> toList() {
		// attilax 老哇的爪子  下午03:10:27   2014-6-11 
		return al;
	}
	/**
	@author attilax 老哇的爪子
	 * @param <T>
		@since  2014-6-27 下午1:57:42$
	
	 * @param class1
	 * @return
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-23 下午3:32:08$
	 */
	public <attilax> attilax[] toArray(Class<attilax> class1) {
	// attilax 老哇的爪子  下午1:57:42   2014-6-27 
		//	new Button[li2.toList().size()]
		attilax[] array =		 (attilax[])Array.newInstance(class1,this.toList().size());  
				//(attilax[]) this.toList().toArray();
		//
		array=	this.toList().toArray(array);
	return array;
	 
	
	}

	 

 
}

//  attilax 老哇的爪子