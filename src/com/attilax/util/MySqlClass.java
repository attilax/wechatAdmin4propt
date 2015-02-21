/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attilax.util;
import java.sql.*;
/**
 *
 * @author mystyle
 */
public class MySqlClass {
   
   public Connection conn=null;
   public Statement st=null;
   private ResultSet rs=null;
   
   public MySqlClass(){}
   /**
    * zafei
    * @param databaseName
    * @param userName
    * @param password
    */
   public MySqlClass(String databaseName,String userName,String password){
       try{
           //写入驱动所在处，打开驱动
           Class.forName("com.mysql.jdbc.Driver").newInstance();
	   //数据库，用户，密码，创建与具体数据库的连接
           conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+databaseName,userName,password);
	   //创建执行sql语句的对象
	   st=conn.createStatement();
          
       }catch(Exception e){
           javax.swing.JOptionPane.showMessageDialog(null,"连接失败"+e.toString());
          
       }
       
   }
   
   public String query(String sqlStatement,int n){
       String result="";
       try{
           rs=st.executeQuery(sqlStatement);
           while(rs!=null && rs.next()){
                result=rs.getString(n);
                //列的记数是从1开始的，这个适配器和C#的不同
           }
           rs.close();
           return result;
       }catch(Exception e){
           javax.swing.JOptionPane.showMessageDialog(null,"查询失败"+e.toString());
           
           return "";
       }
   }
   
   public int query(String sqlStatement){
       int row=0;
       try{
           row=st.executeUpdate(sqlStatement);
       //    this.close();
           return row;
       }catch(Exception e){
            String message = "执行sql语句失败"+e.toString();
		//	javax.swing.JOptionPane.showMessageDialog(null,message);
            e.printStackTrace();
        //   this.close();
           return row;
       }
   }
   
   public void close(){
      try{
          
          this.rs.close();
          this.st.close();
          this.conn.close();
          
      }catch(Exception e){
    	//  e.printStackTrace();
         // javax.swing.JOptionPane.showMessageDialog(null,"关闭数据库连接失败"+e.toString());
      }       
   }
}
