package com.attilax.util;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class exportTopic {

	public exportTopic() {
		inisqlOnly();
	}
	public exportTopic(int n){}
	public static ComboPooledDataSource dsx=null;//new ComboPooledDataSource();
	public exportTopic(ComboPooledDataSource comboPooledDataSource) {
		//DataSource  ds = ;
	//	sqlC.setDs(ds);
		dsx=comboPooledDataSource;
		 
	}
	static Logger logger = Logger.getLogger("full_cb20x");
	public Connection conn;

	public Connection getConn()
	{
		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名scutcs &    rewriteBatchedStatements=true 
		//socketTimeout 
		String url = "jdbc:mysql://192.168.0.132:3306/homi?allowMultiQueries=true&rewriteBatchedStatements=true&socketTimeout=1800000&autoReconnect=false&failOverReadOnly=false&connectTimeout=30000&useCompression=true&useNewIO=true";

		// MySQL配置时的用户名
		String user = "root";

		// MySQL配置时的密码
		String password = "woshimijieIAMmijie";
		
		Properties props = new Properties();
		InputStream in = exportTopic.class.getResourceAsStream("/db.properties");
		try {
//			props.load(in);
//			in.close();
//			url=props.getProperty("url");
//			user=props.getProperty("user");
//			password=props.getProperty("pwd");
			proxoolController.ini();
			url=proxoolController.url;
 		user=proxoolController.user;
 		password=proxoolController.pwd;
			
		} catch (Exception e1) {
			logger.info(" ----ini db.properties err "); 
			e1.printStackTrace();
		}
	
	//	System.out.println(props.getProperty("user"));

		try {
			// 加载驱动程序
			Class.forName(driver);

			// 连续数据库
			Connection	connx = DriverManager.getConnection(url, user, password);
			 
			return connx;
		}catch(Exception e)
		{
		//	e.printStackTrace();
			throw new RuntimeException(e);
		}
		//return null;
	}
	
	public Connection getConnLocalhost()
	{
		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://localhost:3306/homi?allowMultiQueries=true&rewriteBatchedStatements=true";

		// MySQL配置时的用户名
		String user = "root";

		// MySQL配置时的密码
		String password = "";

		try {
			// 加载驱动程序
			Class.forName(driver);

			// 连续数据库
			Connection	connx = DriverManager.getConnection(url, user, password);
			return connx;
		}catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		//return null;
	}
	public void inisqlOnly() {
		this.conn=getConn();
		//cb2
//		if(dsx==null)
//		{
//			 dsx=new ComboPooledDataSource();
//			 
//		 
//		}
//		if(dsx!=null)
//		{
//			
//			try {
//				conn=dsx.getConnection();
//			} catch (SQLException e1) {
//				 
//				e1.printStackTrace();
//				throw new RuntimeException(e1);
//			}
//			return;
//		} 
		
		////cb2
		
		// 驱动程序名
//		String driver = "com.mysql.jdbc.Driver";
//
//		// URL指向要访问的数据库名scutcs
//		String url = "jdbc:mysql://192.168." + "0.166:3306/homi";
//
//		// MySQL配置时的用户名
//		String user = "root";
//
//		// MySQL配置时的密码
//		String password = "woshimijieIAMmijie";
//
//		try {
//			// 加载驱动程序
//			Class.forName(driver);
//
//			// 连续数据库
//			conn = DriverManager.getConnection(url, user, password);
//
//			if (!conn.isClosed())
//				System.out.println("Succeeded connecting to the Database!");
//
//			// statement用来执行SQL语句
//			Statement statement = conn.createStatement();
//
//		} catch (ClassNotFoundException e) {
//
//			System.out.println("Sorry,can`t find the Driver!");
//			e.printStackTrace();
//
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//
//		}

	}
	
	private void inisqlOnlyConn() {
		 
		////cb2
		
		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://192.168." + "0.166:33060/homi";

		// MySQL配置时的用户名
		String user = "root";

		// MySQL配置时的密码
		String password = "woshimijieIAMmijie";

		try {
			// 加载驱动程序
			Class.forName(driver);

			// 连续数据库
			conn = DriverManager.getConnection(url, user, password);

			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");

			// statement用来执行SQL语句
			Statement statement = conn.createStatement();

		} catch (ClassNotFoundException e) {

			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public static void main(String[] args) {

		inisql();
	}

	private static void inisql() {
		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://192.168." + "0.166:33060/homi";

		// MySQL配置时的用户名
		String user = "root";

		// MySQL配置时的密码
		String password = "woshimijieIAMmijie";

		try {
			// 加载驱动程序
			Class.forName(driver);

			// 连续数据库
			Connection conn = DriverManager.getConnection(url, user, password);

			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");

			// statement用来执行SQL语句
			Statement statement = conn.createStatement();

			processForTrave(conn, statement);

		} catch (ClassNotFoundException e) {

			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	  protected Statement statement;

	public ResultSet getrs(String sql) {
		if (conn == null )
			inisqlOnly();
		
		//o12
		if(  isClosed(conn))
		{
			logger.warning(" check conn is close ,reconn o12");
			inisqlOnly();
		}
		// 结果集
		ResultSet rs;
		 
		//o12 recomm
//		if(statement==null)
//		{
			statement=	getStatement();
		//}
		//System.out.println();
		try {
			statement.setFetchSize(500);
		} catch (SQLException e1) {
			 
			e1.printStackTrace();
		}
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {e.printStackTrace();
			throw new RuntimeException(e);
			
		}
		return rs;

	}
	
	
	private boolean isClosed(Connection conn2) {
		
		
		try {
			if(conn2.isClosed())
				return true;
			else
				return false;
		} catch (SQLException e) {
			
			logger.warning(god.getTrace(e));
		//	e.printStackTrace();
			return true;
		}
		
	//	return false;
	}
	public ResultSet getrs(String sql,Statement statement) {
		 
		ResultSet rs;
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {e.printStackTrace();
			throw new RuntimeException(e);
			
		}
		return rs;

	}
	
	/**
	 * create new state
	 * @return
	 */
	protected Statement getStatement() {
		try {
			statement = conn.createStatement();
		 
		} catch (SQLException e) {
			
			logger.warning(god.getTrace(e));
			statement=getStatement_safe();
			
			
		//	e.printStackTrace();
			//throw new RuntimeException(e);
		}
		return statement;
	}

	private Statement getStatement_safe() {
		this.conn=new DbNdsController().conn;
		try {
			statement = conn.createStatement();
		} catch (SQLException e1) {
			logger.warning(god.getTrace(e1));
			throw new RuntimeException(e1);
		}
		return statement;
	}
	private static void processForTrave(Connection conn, Statement statement)
			throws SQLException {

		for (int i = 1; i < 13; i++) {
			String sql = "select title from topic where id in(select * from topicInType_@typeid )";
			sql = sql.replaceAll("@typeid", String.valueOf(i));
			// 要执行的SQL语句

			// 结果集
			ResultSet rs = statement.executeQuery(sql);

			System.out.println("-----------------");
			System.out.println("执行结果如下所示:");
			System.out.println("-----------------");
			System.out.println(" 学号" + "\t" + " 姓名");
			System.out.println("-----------------");

			String name = null;

			List<String> li = new ArrayList<String>();
			while (rs.next()) {

				// 首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
				// 然后使用GB2312字符集解码指定的字节数组
				// name = new String(name.getBytes("ISO-8859-1"),"GB2312");

				// 输出结果
				String string = rs.getString("title");
				System.out.println(string + "\t");
				li.add(string);
			}
			fileC0 fc = new fileC0();
			String savepath = "c:\\exp\\@typeid.txt";
			savepath = savepath.replaceAll("@typeid", String.valueOf(i));
			fc.saveList2file(li, savepath);

			rs.close();
		}

		conn.close();
	}

	public  int exec(String sql) {
		MySqlClass mc = new MySqlClass();
		
		if(this.AutoCommit==false)
		{
			if (conn == null)
			{
				inisqlOnlyConn();
			}
		}else
		{
			if (conn == null)
				inisqlOnly();
		}
	

		getStatement();
		mc.st = statement;

		int r=mc.query(sql);
	//	mc.close();
	 

		return r;
	}
	public MySqlClass  MySqlClassCb9;
	public int execCb2(String sql) {
		MySqlClass  mc = null;
		if (MySqlClassCb9 == null) {
			MySqlClassCb9 = new MySqlClass();
			mc = MySqlClassCb9;
		} else
			mc = MySqlClassCb9;
		
		 

		getStatement();
		mc.st = statement;

		int r=mc.query(sql);
	//	mc.close();
	 

		return r;
	}
	
	
	public void addBatch(String sql) {
		if(statement==null)
			statement=getStatement();
		try {
			statement.addBatch(sql);
		} catch (SQLException e) {
			 
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	 

		 return;
	}
	
	public int exec(String sql,Connection conn2) {
		if(1==1)
		{
			this.conn=conn2;
	return 	execCb2(sql);
		}
		MySqlClass mc = new MySqlClass();
	

		//if (statement == null)
			try {
				statement = conn2.createStatement();
			} catch (SQLException e) {

				e.printStackTrace();
				throw new RuntimeException(e);
			}
		mc.st = statement;

		int r=mc.query(sql);
	//	mc.close();
	 

		return r;
	}
	
	MySqlClass mccbb;
	public int exec(String sql,Statement Statement1) {
		if(mccbb==null)
			mccbb = new MySqlClass();
	
 
		mccbb.st = Statement1;

		int r=mccbb.query(sql);
	//	mc.close();
	 

		return r;
	}

	public void setDs(DataSource ds) {
		// TODO Auto-generated method stub
		
	}

	public void close() {
		try {
			if (conn != null)
				conn.close();

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(" --cb2a");
		}
		
	}
	boolean AutoCommit=true;
	public void setAutoCommit(boolean b) {
		AutoCommit=b;
		try {
			this.conn.setAutoCommit(b);
		} catch (SQLException e) {
			 
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
}