/**
* @author attilax 1466519819@qq.com
* @version  c0
* Copyright 2013 attilax reserved.
* All content (including but not limited to text, pictures, etc.) have copyright restrictions, the note license.
* all the software source code  copyright belongs to the original owner.
* Creative Commons protocol use please follow the "sign for non-commercial use consistent"; we do not welcome the large-scale duplication, and all rights reserved.
* Commercial sites or unauthorized media may not copy software source code.
**/
 
 


 
package com.attilax.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 
 
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
public class classLineNum {
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public static void main(String[] args) {
       
		String path="c:\\users\\administrator\\workspaces\\myeclipse 8.5\\homisearchserver\\src\\com\\mijie\\homi\\search\\api\\topicsearchapi.java";
        readFile(path);
        
        classLineNum c=new classLineNum();
        
        ArrayList<String> li=c.readFile2list(path);
        String note=c.readFilex("c:\\note.txt");
        for(int i=0;i<li4insertPointList.size();i++)
        {
        	String[] sa=(String[]) li4insertPointList.get(i);
        	int linenum=Integer.parseInt(sa[0]);
        	c.insertStr2list(li, note, linenum+i-1);
        	
        	
        }
       
        String s=c.list2str(li);
        System.out.println(s);
        
         
    }
	public String encode;
	public String filenote;
	public String note;
	private int curFileWaitInsertNoteSize;
	
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public String getProcedTxt(String filepath)
	{
		 li4insertPointList = new ArrayList();
		 lineNum=0;
	//	String path="c:\\users\\administrator\\workspaces\\myeclipse 8.5\\homisearchserver\\src\\com\\mijie\\homi\\search\\api\\topicsearchapi.java";
        readFile(filepath);
        
      //  classLineNum c=new classLineNum();
        
        ArrayList<String> li=readFile2list(filepath);
        String note=readFilex("c:\\note.txt");
        if(li4insertPointList.size()==0)
        {
        	this.curFileWaitInsertNoteSize=0;
        	 
        }
        else
        	this.curFileWaitInsertNoteSize=li4insertPointList.size();
        for(int i=0;i<li4insertPointList.size();i++)
        {
        	String[] sa=(String[]) li4insertPointList.get(i);
        	int linenum=Integer.parseInt(sa[0]);
        	insertStr2list(li, note, linenum+i-1);
        	
        	
        }
       
        String s=list2str(li);
        return s;
		
	}
	static int lineNum;
	   private static ArrayList filelist = new ArrayList(); 
	   public static ArrayList li4insertPointList = new ArrayList(); 
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public static void readFile(String path) {
		lineNum=0;
		 BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(path));
		 
		   // BufferedWriter writer  = new BufferedWriter(new FileWriter(dest));  
		    String line = reader.readLine();  
		    while(line!=null){  
		     //   writer.write(line);  
		    	filelist.add(line);
		        line = reader.readLine();  
		        lineNum++;
		      
		       if( isClassLine(line))
		       {
		        System.out.println((lineNum+1)+"---"+line);
		        String[] sa=new String[3];
		        sa[0]=String.valueOf( lineNum+1);
		        sa[1]=line;
		        classLineNum c=new classLineNum();
		    if(!c. isHaveNote(path,lineNum+1))		    	 
		        li4insertPointList.add(sa);
		       }
		       else  if( isMethodLine(line))
		       {
		    	   String[] sa=new String[3];
		    	   sa[0]=String.valueOf( lineNum+1);
			        sa[1]=line;
			        classLineNum c=new classLineNum();
				    if(!c. isHaveNote(path,lineNum+1))	
			        li4insertPointList.add(sa);
			        System.out.println((lineNum+1)+"---"+line);
		       }
		      
		    }  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	private static boolean isMethodLine(String line) {
		//line="public @ResponseBody void RAMIndex(int topicId) throws Exception{";
		if(line==null)
			return false;
		line=line.trim();
		String s="^\\s*[^/]*(public|private)([^=])*\\(.*\\)[^;]*\\n";
		  s="^[^/]*(public|private)[^=]*\\([^;]*";
		Pattern p = Pattern.compile(s);  
		  
		Matcher m = p.matcher(line);  
		  
 //System.out.println(m.matches()+"---====="+line);  
		 boolean t= m.matches();  
		return t;
		  
	 
	}

/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	private static void refreshFileList(String path) {
		// TODO Auto-generated method stub
		
	}
 
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public static boolean isClassLine(String line){  
		if(line==null)
			return false;
		line=line.trim();
		Pattern p = Pattern.compile("public class.*\\{");  
		  
		Matcher m = p.matcher(line);  
		  
	//	System.out.println(m.matches()+"---");  
		  
		return m.matches();  
		  
		} 
	
	
	
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public   void insertStr2list(ArrayList list,String str,int index) {
		
		list.add(index, str);
		 
	}
	
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public   void saveList2file(ArrayList list,String path)
	{
		
		String s="";
		for(int i=0;i<list.size();i++)
		{
			s=s+list.get(i);
		}
		
		  //�
		try{
		         RandomAccessFile mm = new RandomAccessFile(path, "rw");
		          mm.writeBytes(s.toString());
		         mm.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		 
		 
	}
	
	
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public   void save2file(String str,String path)
	{
		
		String s=str;
		
		  //�
		try{ 
		         RandomAccessFile mm = new RandomAccessFile(path, "rw");
		       //   mm.writeBytes(s.toString());
		          mm.write(s.getBytes());
		         mm.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		 
		 
	}
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public   String list2str(ArrayList list )
	{
		String s="";
		for(int i=0;i<list.size();i++)
		{
			if(s.equals(""))
				
				s=s+list.get(i);
			else
				s=s+"\r\n"+list.get(i);
		}
		return s;
	}
	
	
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public   ArrayList<String> readFile2list(String path) {
		lineNum=0;
		 BufferedReader reader;
		 ArrayList li=new ArrayList<String>();
		 
		 InputStreamReader isr = null;
			try {
				isr = new InputStreamReader(new FileInputStream(path),"UTF-8");
			} catch ( Exception e1) {
			 
				e1.printStackTrace();
				throw new RuntimeException(e1);
			}
			
		try {
			//reader = new BufferedReader(isr);
			reader = new BufferedReader(isr);
		 
		   // BufferedWriter writer  = new BufferedWriter(new FileWriter(dest));  
		    String line = reader.readLine();  
		    while(line!=null){  
		     //   writer.write(line);  
		    	li.add(line);
		        line = reader.readLine();  
		        lineNum++;
		      
		      
		    } 
		     
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return li;
	}
	
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public   String  readFilex(String path) {
		lineNum=0;
		 BufferedReader reader;
		 ArrayList li=new ArrayList<String>();
		 String s="";
		 boolean isFistline=true;
		 StringBuilder sb=new StringBuilder();
		 String enter=System.getProperty("line.separator");
		try {
			reader = new BufferedReader(new FileReader(path));
		 
		   // BufferedWriter writer  = new BufferedWriter(new FileWriter(dest));  
		    String line = reader.readLine();  
		    int n=0;
		    while(line!=null){  
		     //   writer.write(line);  
		    	n++;
		    	if(n%300==0)
		    		System.out.println("--readFilex:-"+n);
		    	if(isFistline)
		    	{
		    		isFistline=false;
		    		sb.append(line);
		    		
		    	}
		    	else
		    	{
		    		sb.append(enter).append(line);
		    		 
		    		//s=s+"\r\n"+line;
		    	}
		    	 
		        line = reader.readLine();  
		        lineNum++;
		      
		      
		    } 
		     
		} catch (Exception e) {
			 
			e.printStackTrace();
		} 
		s=sb.toString();
		return s;
	}

/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public void addNote(String path) {
		procedFilesNum=0;
		 travDir.refreshFileList(path);
		 ArrayList filelist=travDir.filelist;
		 for(int i=0;i<filelist.size();i++)
		 {
			 this.curFileWaitInsertNoteSize=0;
			 String filepath=(String) filelist.get(i);
			String s= getProcedTxt(filepath);
			if(curFileWaitInsertNoteSize>0)
			{
				System.out.println("--add note: num="+curFileWaitInsertNoteSize +" , "+filepath);
				save2file(s,filepath);
				procedFilesNum++;
			}
			
			 boolean b=isHaveFileHeadNote(filepath);
			 if(b)
				 System.out.println("--hav headNote "+filepath);
			 else
				 System.out.println("--hav no  headNote,proceing... "+filepath);
			if(!isHaveFileHeadNote(filepath))
			addFileHeadNote(filepath);
		 }

		
	}
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public boolean isHaveFileHeadNote(String filepath) {
		 
		String s=readFilex(filepath);
		if(s.trim().startsWith("/**"))
			return true;
		return false;
	}

/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public void addFileHeadNote(String filepath) {
		 String s=readFilex(this.filenote);
		 String s2=readFilex(filepath);
			save2file(s+"\r\n"+s2,filepath);
		
	}
	int procedFilesNum=0;
/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public void addNote2file(String path) {
		procedFilesNum=0;
		  	 String filepath=path;
			String s= getProcedTxt(filepath);
			if(curFileWaitInsertNoteSize>0)
			{
				save2file(s,filepath);
		 
			procedFilesNum++;
			}
		
	}

/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	public boolean isHaveNote(String path, int lineNum) {
	 
		int noteLinenum=lineNum-1;
		String s=readLine(path,noteLinenum);
		if(s.trim().contains("**/"))
			return true;
		else
		    return false;
	}

	String readLine(String path, int lineNum2) {
	int 	lineNum=1;
		 BufferedReader reader;
		 ArrayList li=new ArrayList<String>();
		 String s="";
		try {
			reader = new BufferedReader(new FileReader(path));
		 
		   // BufferedWriter writer  = new BufferedWriter(new FileWriter(dest));  
		    String line = reader.readLine();  
		    while(line!=null){  
		     //   writer.write(line); 
		    	//qdebug(lineNum+":::"+line);
		    	 if(lineNum==lineNum2)
		    		 return line;
		        line = reader.readLine();  
		        lineNum++;
		      
		      
		    } 
		     
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
		
		 
	}

/**
* @author attilax 1466519819@qq.com
* @version  c0
**/
	private void qdebug(String string) {
	System.out.println(string);
		
	}
	
	
}