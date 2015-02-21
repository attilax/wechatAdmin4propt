package com.focusx.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.attilax.promise;
import com.attilax.spri.SpringUtil;
import com.attilax.sso.loginUtil;
import com.focusx.entity.TUserUsers;
import com.focusx.service.UserInfoManagerService;
import com.focusx.util.Constant;
import com.focusx.util.OnLineCountListener;
import com.focusx.util.OnLineUser;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 *  拦截器，用来判断当前是否已登陆
 */
public class LoginInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = 1L;
	  TUserUsers user;
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();       
		
		    user = (TUserUsers) session.getAttribute(session.getId());
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getRequestURL().toString();
		url = url.substring(url.indexOf("!")+1, url.length());
		
		// o6g
		loginUtil.exTok(new promise() {

			@Override
		//	@
			public Boolean $0(Object t) {
				// attilax 老哇的爪子 上午10:08:30 2014-6-17
				return (user == null);

			}

			// localhost/Gialen/weixin/userManager!listUsers
			@Override
			public Object $1(Object t) {
				// attilax 老哇的爪子 上午10:08:30 2014-6-17
				String uid = loginUtil
						.getuid(ServletActionContext.getRequest());
				if (uid.length() == 0)
					return null;
				UserInfoManagerService userInfoManagerService = (UserInfoManagerService) SpringUtil
						.getBean("userInfoManagerService");
				user = userInfoManagerService
						.getUserById(Integer.parseInt(uid));

				setLoginTokOri();

				// o6g
				loginUtil.setToken(user.getName(), user.getId(),
						ServletActionContext.getResponse());

				System.out.println("");

				return null;
			}

			private void setLoginTokOri() {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute(Constant.Login_NAME, user.getName());
				session.setAttribute(Constant.Login_PASSWORD, user
						.getPassWord());
				session.setAttribute(Constant.Login_UserID, user.getId());
				session.setAttribute(Constant.Login_WorkNO, user.getWorkNo());
				session.setAttribute(Constant.Login_StateValue, 0);
				session.setAttribute(session.getId(), user);
				OnLineUser onLineUser = new OnLineUser();
				onLineUser.setSessionId(session.getId());
				onLineUser.setUserId(user.getId());
				onLineUser.setUserNo(user.getWorkNo());
				onLineUser.setState(0);
				onLineUser.setLoginTime();
				onLineUser.setUserName(user.getName());
				OnLineCountListener.addOnLineUser(onLineUser, session);
			}

		});

		if(url.equals("login")){
			return invocation.invoke();
		}else if(user == null){
	    	 return "login";
	     }else{
	         return invocation.invoke();
	     }
	}
}
