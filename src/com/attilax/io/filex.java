package com.attilax.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.attilax.collection.Func;
import com.attilax.collection.listUtil;
import com.attilax.util.Func_4SingleObj;
import com.attilax.util.Funcx;
import com.attilax.util.fileC0;
import com.attilax.util.god;
import com.attilax.util.travDir;

import m.FileService;

public class filex {

	public static void main(String[] args) {
		
		rename_batch();
		System.out.println("--");

//		System.out.println(getExtName("c:\\haha.htm"));
//		List li = new ArrayList();
//		li.add("xx");
//		filex.saveList2file(li, "c:/ccx2/ka.log", "gbk");
//		
//		
//		String fName =" G:\\Java_Source\\navigation_tigra_menu\\demo1\\img\\lev1_arrow.gif ";  
//		  
////      方法一：  
//  
//        File tempFile =new File( fName.trim());  
//  
//        String fileName = tempFile.getName();  
//          
//        System.out.println("fileName = " + fileName);  
  
	}

	
	/**
	@author attilax 老哇的爪子
		@since  2014-6-19 下午03:24:42$
	todox 
	 */
	private static void rename_batch() {
		// attilax 老哇的爪子 下午03:24:42 2014-6-19
		String s = "E:\\workspace\\wechatdb\\WebRoot\\mobile\\gridimg";
		List l = travDir.getAllFileList(s, "png");
		listUtil.map_generic(l, new Func_4SingleObj<String, String>() {

			@Override
			public String invoke(String o) {
				// attilax 老哇的爪子 下午04:12:23 2014-6-19

				String ori = "方格抽奖";
				String newF = o.replaceAll(ori, "");
				rename(o, newF);
				return newF;
			}
		});

	}
	 public static void rename(String f, String target) {
		// attilax 老哇的爪子  下午04:15:25   2014-6-19 
		move(f, target);
	}


	// static 
	 public static final byte[] readImageData(String path){
		File file= new File(path);
		try{
			long length = file.length();
			byte tempData [] = new byte[(int)length];
			FileInputStream  fin = new FileInputStream(file);
			BufferedInputStream  bufIn=new BufferedInputStream(fin);
			bufIn.read(tempData);
			fin.close();
			return tempData;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("read image is  erro,please check path");
			return null;
		}
	}


	public static String getExtName(String fileName) {
		String name = "";
		String extention = "";
		if (fileName.length() > 0 && fileName != null) { // --截取文件名
			int i = fileName.lastIndexOf(".");
			if (i > -1 && i < fileName.length()) {
				name = fileName.substring(0, i); // --文件名
				extention = fileName.substring(i + 1); // --扩展名
			}
		}
		return extention;

	}
	
	public static String getFileName_noExtName(String fileName) {
		String name = "";
		String extention = "";
		int lastSpashIndex=fileName.lastIndexOf("\\");
		if (fileName.length() > 0 && fileName != null) { // --截取文件名
			int i = fileName.lastIndexOf(".");
			if (i > -1 && i < fileName.length()) {
				name = fileName.substring(lastSpashIndex+1, i); // --文件名
				extention = fileName.substring(i + 1); // --扩展名
			}
		}
		return name;

	}
	
	public static String getFileName(String fileName) {
		String name = "";
		String extention = "";
		int lastSpashIndex=fileName.lastIndexOf("\\");
		if(lastSpashIndex==-1)
			lastSpashIndex=fileName.lastIndexOf("/");
		if (fileName.length() > 0 && fileName != null) { // --截取文件名
		 
		 
				name = fileName.substring(lastSpashIndex+1); // --文件名
			 
		}
		return name;

	}

	public static void write(String fileName, String str) {
		FileService.writeFile(fileName, str);

	}

	public static void write_utf8(String fileName, String s) {
		fileC0.writeFile(fileName, s, "utf-8");

	}

	
	public static void write(String fileName, String s, String encode) {
		fileC0.writeFile(fileName, s, encode);

	}

	public static String read(String path, String encode) {

		// if(path.equals(""))
		// {
		// System.out.println("--warnging :  path is empty cad");
		// return "";
		// }
		StringBuilder sb = new StringBuilder();
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(new FileInputStream(path), encode);
		} catch (Exception e1) {

			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
		BufferedReader reader;
		ArrayList li = new ArrayList<String>();
		try {
			reader = new BufferedReader(isr);

			// BufferedWriter writer = new BufferedWriter(new FileWriter(dest));
			String line = reader.readLine();
			while (line != null) {
				// writer.write(line);
				sb.append(line + "\r\n");
				line = reader.readLine();

			}

		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		//o4e  cancel occu  handel
		try {
			isr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb.toString();
	}

	/**
	 * o3i add mkdir code
	 * @param list
	 * @param path
	 * @param encode
	 */
	public static void saveList2file(List list, String path, String encode) {
		
		 createAllPath(path);
		     
		     
		String s = "";
		StringBuilder strApp = new StringBuilder();
		List<String> li2 = list;

		String sx = "";
		int len = list.size();
		String enter = System.getProperty("line.separator");
		for (int i = 0; i < len; i++) {
			if (i % 2000 == 0)
				System.out.println("--saveList2file:" + i);
			sx = li2.get(i);
			if (s.equals("")) {
				s = sx;
				strApp.append(sx);
			} else
				strApp.append(enter).append(sx);
			// s=s+enter+sx;
		}

		s = strApp.toString();
		fileC0.writeFile(path, s, encode);
	}

	/**
	 * o3i 
	 * @param path
	 */
	public static void createAllPath(String path) {
		File file = new File(path);
		 if (!file.getParentFile().exists()) {
		     System.out.println("目标文件所在路径不存在，准备创建。。。");
		     if (!file.getParentFile().mkdirs()) {
		      System.out.println("创建目录文件所在的目录失败！");
		   
		     }
		 }
	}

	/**
	 * default utf8 encode
	 * @param s
	 * @return
	 */
	public static List<String> read2list(String s) {
		 
		return read2list(s,"utf-8");
	}
	
	public static List<String> read2list(String files, String encode) {
		List<String> lst=new ArrayList<String>();
		 List<String> fs=listUtil.toList(files);
		
		for(String f:fs)
		{
			fileC0 fc = new fileC0();
			List<String> lir = new ArrayList<String>();
			List<String> li = fc.fileRead2list(f, encode);
			lst.addAll(li);
		}
	
		return lst;
	}

	public static void save(String txt, String target2) {
		fileC0 fc = new fileC0();
		fc.save(txt, target2);
		
	}

	public static void saveList2file(List<String> li, String path) {
		saveList2file(li, path, "utf-8");
		
	}

	public static List read2list_filtEmpty(String hasPhonie_file) {
		List li=read2list(hasPhonie_file);
		li=listUtil.filterO4(li, new Func(){

			@Override
			public Object invoke(Object... o) {
				String s=(String) o[0];
				if(s.trim().length()==0)
					return true;
				return false;
			}});
		return li;
	}

	public static String read(String string) {
		 
		return read(string,"utf-8");
	}

//	public static List read2list_filtEmptyNstartSpace(String dicFilePath) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public static List read2list_filtEmptyNstartSpace(String dicFilePath) {
		List li=read2list(dicFilePath);
		li=listUtil.filterO4(li, new Func(){

			@Override
			public Object invoke(Object... o) {
				String s=(String) o[0];
				if(s.trim().length()==0)
					return true;
				return false;
			}});
		li=listUtil.map_generic(li, new Funcx<Object, String>(){

			@Override
			public String invoke(Object... o) {
			 
				return o[0].toString().trim();
			}});
		return li;
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-8 上午09:37:40$
	
	 * @param f
	 * @param string
	 */
	public static void move(String f, String target) {
		// attilax 老哇的爪子  上午09:37:40   2014-5-8 
//		File f=new  File(target);
//		if(f.exists())
			createAllPath(target);
			
			File oldFile = new File(f);
			//filex.move(f,target);
//			
//			// 将文件移到新文件里
//			
 		File fnew = new File(target);
 		oldFile.renameTo(fnew);
		
		
	}

	/**
	@author attilax 老哇的爪子
		@since  2014年5月11日 下午5:37:56$
	
	 * @return
	 */
	public static String getUUidName() {
		// attilax 老哇的爪子  下午5:37:56   2014年5月11日 
		return god.getUUid();
	}

}
