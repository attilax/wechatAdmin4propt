package com.attilax.spri;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.attilax.core;
import com.attilax.io.pathx;


public class SpringUtil implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext applicationContext = null;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.applicationContext = applicationContext;
	}
	
	public void destroy() throws Exception {
		applicationContext = null;
	}

	/**
	 * 获取applicationContext
	 * 
	 * @return applicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 根据Bean名称获取实例
	 * 
	 * @param name
	 *            Bean注册名称
	 * 
	 * @return bean实例
	 * 
	 * @throws BeansException
	 */
	public  static Object getBean(String name) throws BeansException {
		
		iniAppContext();
		return applicationContext.getBean(name);
	}
	
public  static <ati> ati getBean(Class<ati>  cls) throws BeansException {
		
		iniAppContext();
		// applicationContext.getBean
		return applicationContext.getBean(cls);
	}

	private  synchronized static void iniAppContext() {
		if(applicationContext==null)
		{
			//applicationContext-*.xml
		 //	applicationContext = new ClassPathXmlApplicationContext("applicationContext-atti.xml");
			
			String p=pathx.classPath()+"/";
			 core.log("--o5d path:"+p);
			 //path:/d:/workspace/Gialen/WebRoot/WEB-INF/classes/
			   String[] locations = {p+"applicationContext-actions.xml",p+ "applicationContext-common.xml",p+ "applicationContext-daos.xml",p+"applicationContext-services.xml"};
			   applicationContext = new FileSystemXmlApplicationContext(locations );
		
		//	applicationContext= new FileSystemXmlApplicationContext(p);
		}
	}
	
	public static void main(String[] args) {
		// attilax 老哇的爪子  上午11:54:26   2014-5-14 
		System.out.println(getBean("branchManagerService"));
		System.out.println("--");
		//branchManagerService

	}

}