package com.attilax.jar;
//功能：显示出Jar文件中的所有目录名和文件名，同时也显示META-INF/Manifest.mf文件中的所有属性
//用法：提供Jar文件的路径信息
//例如：Java -cp . JarFileView JarFilePath
//eg:  Java -cp . JarFileView F:\JDK\1.4.2\lib\tools.jar

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import javax.swing.plaf.ListUI;

import com.attilax.util.listUtil;


public class JarFileView
{
    public JarFileView()
    {
    }

    public static void main(String[] args) throws Exception
    {
    	String s="D:\\workspace\\convert2atian4telbook\\lib\\aopalliance-1.0.jar";
     List li=   files( s);
     String ss=listUtil.toString_jsonFmt(li);
     System.out.println(ss);
    }

	public static List files(String args)   {
		List li=new ArrayList();
//		if(args.length != 1)
//        {
//            showHowToUsage();
//            return;
//        }

        JarFile jar = null;
        try
        {
         jar = new JarFile(args);
        }
        catch(Exception e)
        {
         System.out.println("Error:Can't Find the " + args + " File!");
       //  return;
        }

        Enumeration entries = jar.entries();

        //打印JAR文件中的所有目录名和文件名
        while(entries.hasMoreElements())
        {
            Object o = entries.nextElement();
            if(o.toString().contains(".class"))
            {
            	String s=o.toString();
            	s=s.replace(".class", "");
            	s=s.replace("/", ".");
            	li.add(s);
            }
            	
         //   System.out.println(o);
        }

        // 下面这段代码可以取得META-INF/MANIFEST.MF文件中的所有属性信息
        Manifest man = null;
		try {
			man = jar.getManifest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Attributes attrs = man.getMainAttributes();
        Set set = attrs.entrySet();
        Iterator i = set.iterator();
        while(i.hasNext())
        {
            Object o = i.next();
            // 打印属性信息
         //   System.out.println(o);
        }
		return li;
	}

    public static void showHowToUsage()
    {
        System.out.println("Usage: Java -cp . JarFileView <source files>");
        System.out.println("Usage: Java -classpath . JarFileView <source files>");
        System.out.println("");
        System.out.println("<source files>:\t\tJarFile(Or ZipFile) Path & Name");
    }
}