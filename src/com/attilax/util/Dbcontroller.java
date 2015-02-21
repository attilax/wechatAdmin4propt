package com.attilax.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import com.sun.rowset.JdbcRowSetImpl;


public class Dbcontroller extends exportTopic {

	public Dbcontroller() {
		super(5);
		this.conn = getConn();
		this.MySqlClassCb9 = new MySqlClass();
		try {
			this.statement = conn.createStatement();
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	public static int execx (String sql)
	{
		Dbcontroller dbc=new DbNdsController();
	int n=	dbc.execCb2(sql);
		dbc.close();
		return n;
	}
	public Dbcontroller(int none)
	{
		super(5);
	}
	public void commit() {
		
		try {
			this.conn.commit();
		} catch (SQLException e) {
			 
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public void commit(int closeConnTimeout_seconds) {
		
		try {
			this.conn.commit();
		} catch (SQLException e) {
			 
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		god.newThread(
				new Runnable(){

					public void run() {
						try {
							conn.close();
						} catch (SQLException e) {
							 
							e.printStackTrace();
						}
						
					}}				
				, "close_conn");
		 
		
	}
	public Map getMap(String sql, String id, String value) {
		 Map sincinSetFromProblmLib=new HashMap<String, String>();
		 ResultSet rs= getrs(sql);
		try {
			while (rs.next()) {
				sincinSetFromProblmLib.put(rs.getString(id),rs.getString(value));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			 throw new RuntimeException(e);			
		}
		return sincinSetFromProblmLib;
	}
 
	public Set getSet(String sql,String valueField) {
		Set set=new HashSet<String>();
		 ResultSet rs= getrs(sql);
			try {
				while (rs.next()) {
					set.add(rs.getString(valueField));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
				 throw new RuntimeException(e);			
			}
			return set;
	}
	
	public Set getSet_concurrent(String sql,String valueField) {
		Set set=new HashSet<String>();
		Statement stat=getStatement();
		 ResultSet rs= getrs(sql,stat);
			try {
				while (rs.next()) {
					set.add(rs.getString(1));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
				 throw new RuntimeException(e);			
			}
			return set;
	}
	
	public Set getSet_rowset(String sql){
		Set set=new HashSet<String>();
	RowSet rs = new JdbcRowSetImpl(); //也可以是CachedRowSetImpl，WebRowSetImpl，FilteredRowSetImpl，JoinRowSetImpl。 
	   try {
		rs.setUrl("jdbc:mysql://192.168.0.166:33060/homi");
	} catch (SQLException e) {
		 
		e.printStackTrace();
	} 
	   try {
		rs.setUsername("root");
	} catch (SQLException e) {
		 
		e.printStackTrace();
	} 
	   try {
		rs.setPassword("woshimijieIAMmijie");
	} catch (SQLException e) {
		 
		e.printStackTrace();
	} 
	   try {
		rs.setCommand(sql);
	} catch (SQLException e) {
		 
		e.printStackTrace();
	} 
	   try {
		rs.execute();
	} catch (SQLException e) {
		 
		e.printStackTrace();
	} 
	try {
		while (rs.next()) {
			set.add(rs.getString(1));
			
		}
	} catch (SQLException e) {
		 
		e.printStackTrace();
	}
	 
	return set;
	
	}
	   
	   
	   
	public void executeBatch() {
		 try {
			this.statement.executeBatch();
			//this.statement.setQueryTimeout(3);
		} catch (SQLException e) {
			 
			e.printStackTrace(); throw new RuntimeException(e);			
		}
		
	}
	
	public void executeBatch(int QueryTimeout_seconds ) {
		 try {
			this.statement.executeBatch();
			this.statement.setQueryTimeout(QueryTimeout_seconds);
		} catch (SQLException e) {
			 
			e.printStackTrace(); throw new RuntimeException(e);			
		}
		
	}
	/**
	 * ,String field
	 * @param sql
	 * @return 
	 */
	public String vals_str(String sql) {
		 ResultSet rs=this.getrs(sql);
		  List<String> li=new ArrayList<String>();
		  try{
		 while(rs.next())
		 {
			 li.add(rs.getString(1));
		 }
		  }catch(Exception e)
		  {
			   e.printStackTrace();
			  throw new RuntimeException(e);
		  }
		return listUtil.toString(li);
	}
	
	public static String getIdsX(String sql) {
		Dbcontroller dbc=new DbNdsController();
		 ResultSet rs=dbc.getrs(sql);
		  List<String> li=new ArrayList<String>();
		  try{
		 while(rs.next())
		 {
			 li.add(rs.getString(1));
		 }
		  }catch(Exception e)
		  {
			   e.printStackTrace();
			  throw new RuntimeException(e);
		  }
		  dbc.close();
		return listUtil.toString(li);
	}
	public String getIds(String sql) {
		 
		return vals_str(sql);
	}
//	public static ResultSet getrsx(String sql) {
//		Dbcontroller dbc=new DbNdsController();
//		ResultSet rs=	dbc.getrs(sql);
//			dbc.close();
//			return n;
//	 
//	}

}
