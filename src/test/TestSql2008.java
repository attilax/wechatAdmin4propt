package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class TestSql2008 {
	protected static Logger logger = Logger.getLogger("TestSql2008");
	  public static void main(String args[]) {
		  
		    String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动  
	        String dbURL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=gialxWeiXin"; // 连接服务器和数据库                                                                              
	        Connection dbConn = null;  
	        try {  
	        	System.out.println("ddd"); 	
	            Class.forName(driverName);  
	            
	            dbConn = DriverManager.getConnection(dbURL,"sa","focusx");  
	            System.out.println("Connection Successful!");           
	            logger.info("Connection Successful!");
				dbConn.close();
	        } catch (Exception e) {  
	            e.printStackTrace(); 
	            logger.error("发生错误地方=", e);
	        }  

	    }
	    }

