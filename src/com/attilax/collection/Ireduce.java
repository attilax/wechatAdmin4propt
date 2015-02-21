/**
 * @author attilax 老哇的爪子
	@since  2014-5-15 上午10:07:02$
 */
package com.attilax.collection;

import com.attilax.util.Funcx;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-5-15 上午10:07:02$
 */
public interface Ireduce<curType, lastRetType>   {
	public lastRetType $(curType o,lastRetType lastRetOBj);
}

//  attilax 老哇的爪子