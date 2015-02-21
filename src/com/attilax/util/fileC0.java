package com.attilax.util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class fileC0 {
	
	public   ArrayList<String> fileRead2list(String path) {
		classLineNum c=new classLineNum();
		return c.readFile2list(path);
	}
	
	public   ArrayList<String> fileRead2listFiltEmptyLine(String path) {
		classLineNum c=new classLineNum();
		ArrayList<String> r=c.readFile2list(path);
		ArrayList<String> al=new ArrayList<String>();
		for(String s:r)
		{
			if(s.trim().length()>0)
				al.add(s);
		}
		return al;
	}

	
	public static void main(String[] args) {
		String s="c:\\fentsiOK.txt";
	//	s="c:\\t.txt";
		fileC0 fc=new fileC0();
		String str=fc.fileRead(s);
		String[] arr=str.split(",");
		String r="";
		for(int i=0;i<arr.length;i++)
		{
			if(i%50==0)
				r=r+","+arr[i].trim()+"\r\n";
			else
				r=r+","+arr[i].trim();
			System.out.println("--cur inx:"+i);
				
		}
		fc.save(r, "c:\\fentsiOK_multiLine.txt");
		System.out.println("--fii");
		
		
		
	}
	public List fileRead2list(String path, String encode) {
		 
		if(path.equals(""))
		{
			System.out.println("--warnging :  path is empty cad");
			return new ArrayList<String>();
		}
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(new FileInputStream(path),encode);
		} catch ( Exception e1) {
		 
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
		 BufferedReader reader;
		 ArrayList li=new ArrayList<String>();
		try {
			reader = new BufferedReader(isr);
		 
		   // BufferedWriter writer  = new BufferedWriter(new FileWriter(dest));  
		    String line = reader.readLine();  
		    while(line!=null){  
		     //   writer.write(line);  
		    	li.add(line);
		        line = reader.readLine();  
		       
		      
		      
		    } 
		     
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return li;
	}
		
 



	public void saveList2file(List list,String path,String encode) {
		 String s="";
		 StringBuilder strApp=new StringBuilder();
		 List<String> li2=list;
		 
		 String sx="";
	int len=	 list.size();
	String enter=System.getProperty("line.separator");
		 for(int i=0;i<len;i++)
		 {
			 if(i%2000==0)
				 System.out.println("--saveList2file:"+i);
			   sx=  li2.get(i);
			 if(s.equals(""))
			 {
				 s=sx;
				 strApp.append(sx);
			 }
			 else
			   strApp.append(enter).append(sx);
 			//	 s=s+enter+sx;
		 }
		 
		 s=strApp.toString();
	    writeFile(path,s,encode);
	}
	
	
	public static void writeFile(String fileName, String fileContent,String encode)   
	{     
	    try   
	    {      
	        File f = new File(fileName);      
	        if (!f.exists())   
	        {       
	            f.createNewFile();      
	        }      
	        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),encode);      
	        BufferedWriter writer=new BufferedWriter(write);          
	        writer.write(fileContent);      
	        writer.close();     
	    } catch (Exception e)   
	    {      
	        e.printStackTrace();     
	    }  
	}

	public Set fileRead2Set(String path, String encode) {
		List<String> li = fileRead2list(path, encode);
		Set set = new HashSet<String>();
		for (int i = 0; i < li.size(); i++) {
			String word = (String) li.get(i);
			set.add(word);
		}
		return set;
	}

	public Set clearAdj(Set set) {
		Set setr=new HashSet<String>();
			 for (Object wordAndAdj : set) { 
				 String wordAdndAdjStrfmt=(String) wordAndAdj;
			     String[] arr=  wordAdndAdjStrfmt.split("\\.");
			     String word="";
			     if(arr.length==1)
			    	 word=arr[0];
			     else
			       word=arr[1];
			     setr.add(word);
			}  


		  
		return setr;
	}

	public String fileRead(String path) {
		// TODO Auto-generated method stub
		classLineNum c=new classLineNum();
		
		return c.readFilex(path);
	}
	public static String Read(String path) {
		// TODO Auto-generated method stub
		classLineNum c=new classLineNum();
		
		return c.readFilex(path);
	}

 

	public void save(String txt, String path) {
		fileC0.writeFile(path,txt,"utf-8");
		
	}


	public void saveList2file(List<String> liR, String target) {
	
		saveList2file(liR,target, "utf-8");
		
		
	}


	public Set fileRead2Set(String path) {
	 
		return fileRead2Set(path,"utf-8");
	}


	public static String getFilename(String path) {
		File f=new File(path);
	String	s=f.getName();
	
	
		return s.split("\\.")[0];
	}


	public static List<String> getFilepathsByPrefix(String dirpath,String Prefix) {
		
		List<String>  li =new ArrayList<String>();
		File dir = new File(dirpath);
		File file[] = dir.listFiles();
		for (int i = 0; i < file.length; i++) {
			// if (file[i].isDirectory())
			// list.add(file[i]);
			// else
			File file2 = file[i];
			String sourceTxt = (file2.getAbsolutePath());
			String filename=getFilename(sourceTxt);
			if(filename.startsWith(Prefix))
				li.add(sourceTxt);
		}
		return li;
		
	}


	public void saveSet2file(Set<String> set, String target) {
		 
		 
	//	List list = new ArrayList(set);
		 List<String> li2= new ArrayList( set);
	 saveList2file(li2, target);
	}  
	
}
