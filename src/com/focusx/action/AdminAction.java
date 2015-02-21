package com.focusx.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import m.secury.callbackItfs;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;

import cn.freeteam.util.OperLogUtil;
import cn.freeteam.util.ResponseUtil;

import com.attilax.net.cookieUtil;
import com.attilax.net.requestImp;
import com.attilax.sso.loginUtil;
import com.attilax.util.tryX;
 
import com.focusx.entity.TUserUsers;
import com.focusx.service.UserInfoManagerService;
import com.focusx.util.Constant;
import com.focusx.util.EncryptUtil;
import com.focusx.util.OnLineCountListener;
import com.focusx.util.OnLineUser;
import com.opensymphony.xwork2.ActionSupport;

public class AdminAction extends ActionSupport implements RequestAware{
	
	private static final long serialVersionUID = 1L;
	private Map<String, Object> request;
	private TUserUsers user;
	private String username = "";//用户名称
	private String password = "";//用户密码
	private String lr = ""; //验证码
	private OnLineUser lineUser;//在线用户
	
	private UserInfoManagerService userInfoManagerService;
	
	/**
	 *  检测用户名和密码是否存在，"是 "在Session中保存用户信息，并把Session保存在线用户集合中
	 */
	public String login(){
		
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			if(OnLineCountListener.getOnLineUser(session) != null){
				return "home";
			}
			if(username != null || password != null || !username.equals("") || !password.equals("")||
					lr != null || !"".equals(lr)){
				
				String mylr = (String)session.getAttribute("loginRandom");
				
				if(mylr != null && !"".equals(mylr) && mylr.equalsIgnoreCase(lr)){
				
						Map<String, String> data = new HashMap<String, String>();
						data.put("username", username);
						//密码加密处理
						password = EncryptUtil.md5Encrypt(password);
						
						data.put("password", password);
						TUserUsers user = userInfoManagerService.login(data);
					 
						if(user != null){
							session.setAttribute(Constant.Login_NAME, user.getName());
							session.setAttribute(Constant.Login_PASSWORD, user.getPassWord());
							session.setAttribute(Constant.Login_UserID, user.getId());
							session.setAttribute(Constant.Login_WorkNO, user.getWorkNo());
							session.setAttribute(Constant.Login_StateValue, 0);
							session.setAttribute(session.getId(), user);
							
							//o6g
							loginUtil.setToken(  user.getName(),user.getId(),ServletActionContext.getResponse());
							
							OnLineUser onLineUser = new OnLineUser();
							onLineUser.setSessionId(session.getId());
							onLineUser.setUserId(user.getId());
							onLineUser.setUserNo(user.getWorkNo());
							onLineUser.setState(0);
							onLineUser.setLoginTime();
							onLineUser.setUserName(user.getName());
							OnLineCountListener.addOnLineUser(onLineUser, session);
							//o6f
							try {
								OperLogUtil.log( user.getName(), "登录系统...", ServletActionContext.getRequest());	
							} catch (Exception e) {
								// TODO: handle exception
							}
							
							return "home";
						}
						request.put("user_error", "用户或者密码错误");
						try {
							OperLogUtil.log( user.getName(), "登录系统--用户或者密码错误...", ServletActionContext.getRequest());	
						} catch (Exception e) {
							// TODO: handle exception
						}
						
				}else{
					request.put("user_error", "验证码错误");
				}
				return "fail";
			}else{
				return "fail";
			}
		} catch (Throwable e) {
			logger.error("ep29a", e);
			ResponseUtil.writeGBK(ServletActionContext.getResponse(), "<script>alert('出现错误:"+e.toString()+"');history.back();</script>");
			return null;
		}
		
	
	}
	private static Logger logger = Logger.getLogger(AdminAction.class);
    /**
     * 退出系统，在SessionMap中删除用户，并将Session对象从在线用户集合中删除
     */
	public String loginOut(){
		
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			//o6f
			//String loginName = session.getAttribute(Constant.Login_NAME);
			try {
				//OperLogUtil.log(loginName(), "退出系统", ServletActionContext.getRequest());
			} catch (Throwable e) {
				// TODO: handle exception
			}
		
			//session.setAttribute(Constant.Login_NAME, user.getName());
			session.removeAttribute(Constant.Login_UserID);
			
			try {
				OnLineCountListener.stopSession(session.getId());
			} catch (Throwable e) {
				// TODO: handle exception
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	
		
		
		
		
		return "out";
	}
	
	/**
	@author attilax 老哇的爪子
		@since  2014-6-16 下午05:44:29$
	
	 * @return
	 */
	public String loginName() {
		// attilax 老哇的爪子  下午05:44:29   2014-6-16 
		return new tryX<String>(){

			@Override
			public String item(Object t) throws Exception {
				// attilax 老哇的爪子  下午05:45:06   2014-6-16 
				 HttpSession session = ServletActionContext.getRequest().getSession();
				//o6f
				String loginName = session.getAttribute(Constant.Login_NAME).toString();;
				return loginName;
			}}.$("");
	}

	//测试在线人数
	public void test(){
		List<OnLineUser> list  = (List<OnLineUser>) OnLineCountListener.getOnLineUsers();
		System.out.println("size="+list.size());
		System.out.println("online="+OnLineCountListener.getUserCount());
		for(OnLineUser on : list){
			System.out.println(on.getUserNo());
		}
	}

	/**
	 *  获取主页需要的数据
	 */
	public String main(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		lineUser = OnLineCountListener.getOnLineUser(session);
		return "success";
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserInfoManagerService getUserInfoManagerService() {
		return userInfoManagerService;
	}

	public void setUserInfoManagerService(
			UserInfoManagerService userInfoManagerService) {
		this.userInfoManagerService = userInfoManagerService;
	}

	public TUserUsers getUser() {
		return user;
	}

	public void setUser(TUserUsers user) {
		this.user = user;
	}
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public OnLineUser getLineUser() {
		return lineUser;
	}

	public void setLineUser(OnLineUser lineUser) {
		this.lineUser = lineUser;
	}

	public String getLr() {
		return lr;
	}

	public void setLr(String lr) {
		this.lr = lr;
	}
	
	
}