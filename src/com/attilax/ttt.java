/**
 * 
 */
package com.attilax;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ASIMO
 * 
 */
public class ttt {

	public static void main(String[] args) {
		// java.sql.Timestamp time = new java.sql.Timestamp(new
		// java.util.Date().getTime());
		//
		// int sk = time.getSeconds();
		// if(sk<30)
		// return "e";
		// System.out.println(sk);//秒

		ExecutorService es = Executors.newFixedThreadPool(10);
		core.
		es.execute(new Runnable() {

			@Override
			public void run() {

			}
		});
		System.out.println("---");

	}
}
