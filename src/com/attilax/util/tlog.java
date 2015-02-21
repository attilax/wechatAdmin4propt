package com.attilax.util;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.attilax.core;

public class tlog {
	public static Logger log = Logger.getLogger(log4jloadAgain.class);

	public static void main(String[] args) {
//		core.logger.info("xxx");
//		org.apache.log4j.LogManager.resetConfiguration();
//		org.apache.log4j.PropertyConfigurator
//				.configure("D:\\workspace\\imServer\\src\\log4j.properties");
//		core.logger("xxxt");
//
//		// (2)重新设置属性
//
//		Properties log4jCfg = log4jloadAgain.getLog4jProperties();
//
//		PropertyConfigurator.configure(log4jCfg);
//
//		core.logger.info("xxxt3");
//		log.info("xx4");
		core.logger.info("xx");
	}

}
