package com.attilax.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.attilax.core;
import com.attilax.io.filex;
import com.sun.xml.internal.messaging.saaj.packaging.mime.Header;

public class easyuiGener {
	static String module_name="knowLib";
	static	String heads_cn="ID,名称,描述";
	static	String heads_field="id,name,desc.txt";
	static	String templeteDir="E:\\im\\imui";
	static	String tmplt_list="templete_list.htm";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		String tmplt_edit="templete_edit.htm";
	
		
		String listTxtTmplt=filex.read(templeteDir+File.separator+tmplt_list, "utf-8");
		
		String prefix="colo3_"   ;
		
		
		String heads_html=getHeadsHTML(heads_cn,heads_field,prefix);
		System.out.println(heads_html);
		
		listTxtTmplt=listTxtTmplt.replaceAll("@@heads", heads_html);
		filex.write_utf8(templeteDir+File.separator+module_name+"_list.htm", listTxtTmplt);
		
		//gene knowlibItem
		module_name="knowlibItem";
	  heads_cn	="条目,类型,状态,问题,关键字,答案,操作:编辑|删除";
	  heads_field="item,type,state,question,keyword,answer";
	  gene(templeteDir+File.separator+module_name+"_list.htm");
	  
		
		System.out.println("--ff");

	}

	private static void gene(String string) {
		String prefix="colo3_"   ;
		String listTxtTmplt=filex.read(templeteDir+File.separator+tmplt_list, "utf-8");
		String heads_html=getHeadsHTML(heads_cn,heads_field,prefix);
		core.log("-- gene head html:"+heads_html);
		listTxtTmplt=listTxtTmplt.replaceAll("@@heads", heads_html);
		String fileName = templeteDir+File.separator+module_name+"_list.htm";
		filex.write_utf8(fileName, listTxtTmplt);
		core.log("-- gene   page path:"+fileName);
	}

	private static String getHeadsHTML(String heads_cn, String heads_field,
			String prefix) {
		List<Head> li=new ArrayList();
		String[] hd_cn_a=heads_cn.split(",");
		String[] a=heads_field.split(",");
		int n=0;
		for(String fld:a)
		{
			String[] a2=fld.split("\\.");
			 Head h=new Head();
			 h.cn=getHeadCn( hd_cn_a[n]);
			 h.field=a2[0];
			 if(a2.length>1)
				 h.type=getType(a2[1]);
			 li.add(h);
			 n++;
			 
		}
		if(hd_cn_a.length>a.length)
		{
			 Head h2=new Head();
			 h2.cn="操作";
			 h2.field="op";
			 li.add(h2);
			 
		}
		String r="";
		String temp_one_head="  <th data-options=\"field:'@fld' ,width:100\"  >@cn</th>";
		for(Head hd:li)
		{
			String s=temp_one_head.replaceAll("@fld", prefix+hd.field);
			s=s.replaceAll("@cn", hd.cn);
			r=r+s+"\r\n";
		}
		 
		return r;
	}

	private static String getHeadCn(String headCn) {
		if(headCn.startsWith("操作"))
		return "操作";
		else
			return headCn;
	}

	private static String getType(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
