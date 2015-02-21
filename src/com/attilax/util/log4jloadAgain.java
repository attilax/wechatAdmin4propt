package com.attilax.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

//import com.mijie.homi.search.service.index.MoodUserIndexService;

public class log4jloadAgain {
	public static Logger log = Logger
	.getLogger(log4jloadAgain.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 
	//	(2)重新设置属性
		 
        Properties log4jCfg = getLog4jProperties();
        
        log.info("log4j config properties : "+log4jCfg);
        
    //    log4jCfg.setProperty("log4j.appender.TASKlog.File", logFile);
        
        log.info("log4j properties prpoperties is : " + log4jCfg);
        PropertyConfigurator.configure(log4jCfg);

	}
	
	
	//Log4j动态切换日志文件


	//(1)读出log4j文件到Properties对象
	    
	public  static Properties getLog4jProperties(){
	        Properties p = new Properties();
	        InputStream is =new log4jloadAgain().getClass().getClassLoader().getResourceAsStream("/log4j.properties");
	        if(is==null){
	            log.warn("read log4j properties file error ");
	            return p;
	        }
	        try {
	            p.load(is);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return p;
	    }
	    

	

//	(3)配置生效        
//	        
//	        PropertyConfigurator.configure(log4jCfg);
//
//	上面的属性设置里面，我把原来的日志文件换成了一个新的日志文件。
//	通过这种方法，就可以实现在任何时候切换到一个新的日志文件开始记录。

}
