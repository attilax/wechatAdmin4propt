/**
 *  @author attilax
 *  file note c0
 */
package com.attilax.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


/**
 * 
 * @author attilax
 * 
@version c0
@since  c0 since
@date c0 date 
@see reference
 ckass note c0
 *
 */
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
public class ServerHelper {

	
	/**
	 * method note c0
	 * get connx
	 * @return
	 */
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public static Connection getConnection() {
		   Connection conn=null;
		   try{
			   conn = DriverManager.getConnection("proxool.maindb");
		   } catch (SQLException e) {
			   e.printStackTrace();
		   }
		   return conn;
	}
	
	public static Comparator<String> indexFileNameComparator = new Comparator<String>(){
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
		public int compare(String o1, String o2) {
			Long num1 = Long.parseLong(o1);
			Long num2 = Long.parseLong(o2);
			return num2.compareTo(num1);
		}
	};
	
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public static File getCurrentIndexFile(String indexDir){
		File dir = new File(indexDir);
		if(!dir.isDirectory()) return null;
		String[] fileNames = dir.list();
		if(fileNames==null){
			return null;
		}
		Arrays.sort(fileNames, ServerHelper.indexFileNameComparator);
		File file = null;
		
		for(String f : fileNames){
			file = new File(indexDir+f);
			if(file.isDirectory()){
				if(new File(file.getAbsolutePath()+"/done.txt").exists()){
					return file;
				}
			}
		}
		
		return null;
	}
	
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public static List<String> deleteRedundantIndexFile(String indexDir){
		List<String> deleteList = new ArrayList<String>();
		File dir = new File(indexDir);
		if(dir.isDirectory()){
			String[] fileNames = dir.list();
			if(fileNames.length>40){
				Arrays.sort(fileNames, ServerHelper.indexFileNameComparator);
				
				for(int i=10;i<fileNames.length;i++){
					FileUtil.deleteFile( new File(indexDir+fileNames[i]));
					deleteList.add(fileNames[i]);
				}
			}
		}
		return deleteList;
	}
}