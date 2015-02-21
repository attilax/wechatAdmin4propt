/**
 * @author attilax 老哇的爪子
	@since  2014-4-29 上午09:01:15$
 */
package com.attilax.util;

/**
 * @author attilax
 *
 */
public interface Func_wzPre<atiIptType,atiRetType,atiPreInType,atiPreRetType>  {
	
	public atiPreRetType $Pre(atiPreInType o);
	public atiRetType $(atiIptType o);
}


 