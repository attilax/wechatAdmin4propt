package com.attilax.util;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.attilax.core;
import com.mchange.v2.c3p0.ComboPooledDataSource;


public class DbNdsController extends Dbcontroller {
	public static ComboPooledDataSource dsx;

	public DbNdsController() {
		
		super(5);
		//nbq
		//o312  	proxoolController.ini();
		inixDataSource(); 
		
	

 
		
		
		
		// super.execCb2(sql)
		try {
			this.conn = dsx.getConnection();
			this.statement=getStatement();
		} catch (SQLException e) {

			System.out.println("----get conn err , retry start");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e2) {
			 
				e2.printStackTrace();
			}
			e.printStackTrace();
			try {
				this.conn = dsx.getConnection();
			} catch (SQLException e1) {
			 
				e1.printStackTrace();
				throw new RuntimeException(e);
			}
//			this.statement=getStatement();
//			
//			throw new RuntimeException(e);
		}

	}

	/**
	 * 	proxoolController.ini();
	 */
	public synchronized static void inixDataSource() {
		synchronized (DbNdsController.class) {
			if (dsx == null)
			{
				ComboPooledDataSource	dataSource = new ComboPooledDataSource();
				//nbp
				//rem o312  only read cfg from c3p0
			//	proxoolController.ini();
				 
			//	ComboPooledDataSource  dataSource=new   ComboPooledDataSource();       
			//	ComboPooledDataSource  dataSource=dsx; 
				
		//		rem o312  only read cfg from c3p0
// 		          dataSource.setUser( proxoolController.user);       
// 		          dataSource.setPassword( proxoolController.pwd);       
// 	          dataSource.setJdbcUrl( proxoolController.url );
				
				
				//o313  in linux cant get conn
				core.logger.info( "  dataSource.getJdbcUrl()"+ dataSource.getJdbcUrl());
		        core.logger.info( "--user:"+ dataSource.getUser());
		        core.logger.info( "--getPassword:"+ dataSource.getPassword());
//		        c3p0.jdbcUrl=
//		        c3p0.user=sysman
//		        c3p0.password=000000
		        if(dataSource.getJdbcUrl()==null)
		        {
				dataSource.setUser("sysman");       
		          dataSource.setPassword( "000000");       
	          dataSource.setJdbcUrl( "jdbc:oracle:thin:@192.168.1.206:1521:ipdbsid");
	          dataSource.setMaxConnectionAge(30);
	          dataSource.setMaxPoolSize(9000);
	          dataSource.setUnreturnedConnectionTimeout(30);
		        }
	        
		          dsx=dataSource;
			}
		}

	}

	public int execCb2(String sql) {
		// if (statement == null)
		try {
			statement = conn.createStatement();
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		int row = 0;
		try {
			row = statement.executeUpdate(sql);
			// this.close();
			return row;
		} catch (Exception e) {
			String message = "执行sql语句失败" + e.toString();
			// javax.swing.JOptionPane.showMessageDialog(null,message);
			e.printStackTrace();
			throw new RuntimeException(e);
			// this.close();

		}

	}

	public void setAutoCommit(boolean b) {
		 
		try {
			this.conn.setAutoCommit(b);
		} catch (SQLException e) {
			 
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public void commit() {
		
		try {
			this.conn.commit();
		} catch (SQLException e) {
			 
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void close() {
		try {
			conn.close();

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(" --cb91350");
		}

	}
	
	
	/**
	 * o12
	 */
	public void inisqlOnly() {
		DbNdsController dbc=new DbNdsController();
		this.conn=dbc.conn;
	}
//	public ResultSet getrs(String sql) {
//		if (conn == null )
//			inisqlOnly();
//		
//		//o12
//		if(  isClosed(conn))
//			inisqlOnly();
//		// 结果集
//		ResultSet rs;
//		 
//		if(statement==null)
//		{
//			statement=	getStatement();
//		}
//		//System.out.println();
//		try {
//			statement.setFetchSize(500);
//		} catch (SQLException e1) {
//			 
//			e1.printStackTrace();
//		}
//		try {
//			rs = statement.executeQuery(sql);
//		} catch (SQLException e) {e.printStackTrace();
//			throw new RuntimeException(e);
//			
//		}
//		return rs;
//
//	}

}
