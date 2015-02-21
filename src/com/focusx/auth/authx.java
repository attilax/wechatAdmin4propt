/**
 * @author attilax 老哇的爪子
	@since  2014-5-29 下午04:13:35$
 */
package com.focusx.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.attilax.util.tryX;
import com.focusx.entity.TUserUsers;
import com.focusx.util.Constant;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-5-29 下午04:13:35$
 */
public class authx {

	/**
	@author attilax 老哇的爪子
		@since  2014-5-29 下午04:13:35$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  下午04:13:35   2014-5-29 

	}
	
	public static boolean isSuperAdmin(final HttpServletRequest req)
	{
	 
		return new tryX<Boolean>(){

			@Override
			public Boolean item(Object t) throws Exception {
				// attilax 老哇的爪子  下午02:30:07   2014-5-30 
				TUserUsers user;
				// HttpServletRequest request = ServletActionContext.getRequest();
				HttpSession session = req.getSession();
				Integer userID = (Integer) session.getAttribute(Constant.Login_UserID);// 从session里获取当前用户的userID

				user = (TUserUsers) session.getAttribute(session.getId());
				 if(user.getIsSystem() == 0) return false;
				return true;
				//return null;
			}}.$(false);
			// attilax 老哇的爪子 上午11:51:59 2014-5-15
			
		 
	}
	//  attilax 老哇的爪子 下午04:13:35   2014-5-29   
}

//  attilax 老哇的爪子