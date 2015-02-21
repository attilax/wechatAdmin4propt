package com.attilax.util;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;




public class MultiConnStatement implements Runnable {
	// public static Connection conn;
	private int num;
	static ExecutorService pool = Executors.newFixedThreadPool(5000);

	static AtomicInteger nowConnNum = new AtomicInteger(0);;
	static AtomicInteger nowConnerr = new AtomicInteger(0);
	static Connection connFirst;
	public static void main(String[] args) throws SQLException, InterruptedException {
		try {
			// Thread.sleep(15000);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		timeTester t = new timeTester(" cb1");

		// if (1 == 1) {
		// t1();
		// t.printUseTime();
		// return;
		// }

		// t1();
		 
		for (int j = 0; j < 50; j++) {
			System.out.println(j);
			
			Thread thd=	new Thread(new Runnable() {

				@Override
				public void run() {
					if (nowConnerr.get() == 1) {
						System.out.println(" err start :");
						return;
					}
					Dbcontroller dbc = null;
					try {
						if (nowConnerr.get() == 1) {
							System.out.println(" err start :");
							return;
						}
						// dbc= new Dbcontroller_local();
						dbc = new Dbcontroller();
					} catch (Exception e) {
						e.printStackTrace();
						nowConnerr.set(1);
						return;
					}
					if(connFirst==null)
						connFirst=dbc.conn;
					
					int n = nowConnNum.getAndIncrement();
					System.out.println("--conn num" + n);
					dbc.setAutoCommit(false);
					for (int i = 0; i < 2; i++) {
						String sql = "INSERT INTO mood4fulllog(mid,rectype,timex) VALUES ( 1, 'sincinlib', now())";
					//	sql = "INSERT INTO mood4fulllog(mid,rectype,timex) VALUES ( 1, 'sincinlib','2013-12-12 14:14:14')";
						// return ds;

						// int r = dbc.execCb2(sql);
						dbc.addBatch(sql);

					}
					// System.out.println(sm.get);
					dbc.executeBatch(3);
					dbc.commit();
				  //   dbc.close();
				}
 			});
			thd.setName("thrd_id_"+String.valueOf(j));
			thd.start();
 				 
//			pool.execute(
//
//				);
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
		while(true)
		{
			Thread.sleep(10000);
			if(connFirst!=null)
			{
		System.out.println("close is close"+connFirst.isClosed());	 
		System.out.println("close is valid"+connFirst.isValid(3));
	//	connFirst.
			}
		}

	}

	private static void t1() {
		ExecutorService es = Executors.newFixedThreadPool(50);
		exportTopic sqlC = new exportTopic(5);
		// conn = sqlC.getConnLocalhost();
		// conn.setAutoCommit(false);

		int allNum = 500;
		int threadNum = 20;
		int per = allNum / threadNum;
		for (int i = 0; i < threadNum; i++) {
			MultiConnStatement myc = new MultiConnStatement();
			myc.num = per;
			es.execute(myc);
		}

		// sqlC.exec("begin",conn);
		es.shutdown();
		System.out.println("---fca29");

		try {
			es.awaitTermination(3, TimeUnit.HOURS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		// Statement statement;
		// try {
		// statement = conn.createStatement();
		// } catch (SQLException e) {
		//
		// e.printStackTrace();
		// throw new RuntimeException(e);
		// }
		exportTopic sqlC = new exportTopic(5);

		// Connection conn = sqlC.getConnLocalhost();
		Connection conn = sqlC.getConn();
		try {
			// conn.setAutoCommit(true);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		for (int i = 0; i < this.num; i++) {
			String sql = "INSERT INTO mood4fulllog(mid,rectype,timex) VALUES ( 1, 'sincinlib', now());";

			// return ds;

			int r = sqlC.exec(sql, conn);
			// sqlC.close();
			// System.out.println("--exe int:" + r);
			// conn.commit();
		}
		// sqlC.exec("commit",conn);
		try {
			// conn.commit();
			// String s="";
		} catch (Exception e) {

			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
