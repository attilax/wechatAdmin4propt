package com.attilax.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;




public class MaxConnOs implements Runnable {
	// public static Connection conn;
	private int num;
	static ExecutorService pool = Executors.newFixedThreadPool(5000);

	static AtomicInteger nowConnNum = new AtomicInteger(0);;
	static AtomicInteger nowConnerr = new AtomicInteger(0);

	public static void main(String[] args) throws SQLException {
		try {
			// Thread.sleep(15000);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		timeTester t = new timeTester(" cb1");
		
		
		for (int j = 0; j < 1000; j++) {
			System.out.println(j);
			pool.execute(new Thread(new Runnable() {

				@Override
				public void run() {

					int n = nowConnNum.getAndIncrement();
					System.out.println("--conn num" + n);

					// System.out.println(sm.get);

				}
			}) {
			});
		}
		
		
		
		
		
		pool.shutdown();
		try {
			pool.awaitTermination(3, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int n = nowConnNum.getAndIncrement();
		System.out.println("--conn numxxx" + n);
		t.printUseTime();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	 

 

}
