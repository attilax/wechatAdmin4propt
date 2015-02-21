/**

     ___   _____   _____   _   _           ___  __    __ 
    /   | |_   _| |_   _| | | | |         /   | \ \  / / 
   / /| |   | |     | |   | | | |        / /| |  \ \/ /  
  / / | |   | |     | |   | | | |       / / | |   }  {   
 / /  | |   | |     | |   | | | |___   / /  | |  / /\ \  
/_/   |_|   |_|     |_|   |_| |_____| /_/   |_| /_/  \_\ 

* @author attilax 1466519819@qq.com
* @version  c0
* Copyright 2013 attilax reserved.
* All content (including but not limited to text, pictures, etc.) have copyright restrictions, the note license.
* all the software source code  copyright belongs to the original owner.
* Creative Commons protocol use please follow the "sign for non-commercial use consistent"; we do not welcome the large-scale duplication, and all rights reserved.
* Commercial sites or unauthorized media may not copy software source code.
**/
 
 //盒子


 
package com.attilax.util;

import java.util.ArrayList;

/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
public class start {

	
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	 public static void main(String[] args) {
	        
		 String path="C:\\Users\\Administrator\\Workspaces\\MyEclipse 8.5\\homiSearchServer";
		 
		// path="c:\\testdir";
		 
		 classLineNum c=new classLineNum();
		 c.encode="utf-8";
		 c.filenote="c:\\fileHeadnote.txt";
		 c.note="c:\\note.txt";
		   c.addNote(path);
		 System.out.println("procedFilesNum---"+c.procedFilesNum);
		 String path2 = "C:\\testdir\\UserSearchApi.java";
	//	boolean isHaveNote=c.isHaveNote(path2,29);
		//  System.out.println("isHaveNote::"+isHaveNote);
		  
//		  classLineNum.readFile(path2);
//		  ArrayList li4insertPointList=classLineNum.  li4insertPointList; 
//		  print(li4insertPointList);
		 // String s=c.readLine(path2,29);
		//  System.out.println("@@"+s);
		  
//		 boolean b=c.isHaveFileHeadNote(path2);
//		 System.err.println(b);
//		 if(!b)
//				c.addFileHeadNote(path2);
	       
	    }

	@SuppressWarnings("unchecked")
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	private static void print(ArrayList li4insertPointList) {
		@SuppressWarnings("unused")
		Object obj = new Object(); String str = li4insertPointList.getClass().getSimpleName();
		System.out.println(str);
		System.out.println("--list size:"+li4insertPointList.size());
//		 for (Object stu : li4insertPointList) { 
//			 
//			 (String[])stu 
//            System.out.println();
//         }  
		
	}
	
 
 
}