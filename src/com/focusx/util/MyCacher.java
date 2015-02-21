package com.focusx.util;


import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;


public class MyCacher {
	private  static MyCacher instance = new MyCacher();
	private final static int  DEFAULT_CACHE_TIME = 1000 * 60 * 10; //默认缓存10分钟
	private static MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("127.0.0.1:11211"));
	private static MemcachedClient mem = null;
	private static boolean isInit = false;
	private Logger logger = Logger.getLogger(MyCacher.class);
	static{
		builder.setConnectionPoolSize(2);
		try {
			mem = builder.build();
			isInit = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private MyCacher(){
	}
	
	public static MyCacher getInstance(){
		return instance;
	}
	
	
	public boolean putCache(String key,Object value){
		
		boolean flag = false;
		try {
			if(isInit){
				flag = mem.set(key,DEFAULT_CACHE_TIME, value);
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean updateCache(String key){
		boolean flag = false;
		try {
			if(isInit){
				flag = mem.touch(key, DEFAULT_CACHE_TIME);
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean updateCache(String key,int time){
		boolean flag = false;
		if(time <= 0 || time > Integer.MAX_VALUE){
			time = DEFAULT_CACHE_TIME;
			logger.info(key+":最大缓存时间只能是 "+Integer.MAX_VALUE+" 秒");
		}
		try {
			if(isInit){
				flag = mem.touch(key, time);
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public Object getCache(String key){
		try {
			if(isInit)
				return mem.get(key);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean putCache(String key,Object value,int time){
		boolean flag = false;
		if(time <= 0 || time > Integer.MAX_VALUE){
			time = DEFAULT_CACHE_TIME;
			logger.info(key+":最大缓存时间只能是 "+Integer.MAX_VALUE+" 秒");
		}
			try {
				if(isInit){
					flag = mem.set(key,time,value);
				}
			} catch (TimeoutException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (MemcachedException e) {
				e.printStackTrace();
			}
		return flag;
	}
	
	public boolean removeCache(String key){
		boolean flag = false;
			try {
				if(isInit){
					flag = mem.delete(key);
				}
			} catch (TimeoutException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (MemcachedException e) {
				e.printStackTrace();
			}
		return flag;
	}

}
