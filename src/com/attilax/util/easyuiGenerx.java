//package com.attilax.util;
//
//import java.io.File;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import m.FileService;
//import m.utf编码;
////import m.cc.item;
//import m.dataPkg.dateUtil;
//import m.datepkg.dateUtil_o16;
//import m.eml.emlC;
//import m.html.htmlx;
//import m.numpkg.numUtil;
//import net.sf.json.JSONArray;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import com.attilax.text.strUtil;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.attilax.core;
//import com.attilax.io.filex;
//import com.attilax.text.strUtil;
//import com.sun.xml.internal.messaging.saaj.packaging.mime.Header;
//
//public class easyuiGenerx {
//	static String targetDir = "E:\\workspace\\imServer\\WebRoot";
//	static String module_name = "knowLib";
//	static String heads_cn = "ID,名称,描述";
//	static String heads_field = "id,name,desc.txt";
//	static String templeteDir = targetDir;
//	static String tmplt_list = "templete_list.htm";
//	static String dataStrutsDir = "E:\\im\\doc\\imdbstruts";
//	static String opStr="<th data-options=\"field:'op',formatter:rowformater_1\">操作</th>";
//	static String geneModules = "phraseCate,quickQustion,dialogCate,visitorBuscard,webpageAlias";
//	/**
//	 * colo3_
//	 */
//	static String prefix = "";
//	private static boolean showOpColumn=false;
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//
//		// m();
//		List<String> files = dirx.getFiles(dataStrutsDir);
//		for (String filepath : files) {
//			core.log("--lookfor filepath:"+filepath);
//		
//			
//			showOpColumn=false;
//			String fname = core.basename_noext(filepath);
//			
//			// only zidin module gene
//			
//		
//			if(!isGeneModeul(fname,geneModules))
//				continue;
//			core.log("--gene filepath:"+filepath);
//			
//			
//			if(filepath.contains(".op."))
//				showOpColumn=true;
//			String[] a = fname.split("\\.");
//			module_name = a[0];
//			String body = filex.read(filepath, "utf-8");
//			final Document doc = Jsoup.parse(body);
//			Element table = doc.getElementsByTag("table").get(0);
//			List<Head> li = getHeads(table);
//			gene(li);
//		 	//core.copy(templeteDir+"\\aa","xx");
//			
//		}
//
//		System.out.println("--ff");
//
//	}
//
//	/**
//	 * o4a
//	 * @param fname
//	 * @param geneModules2
//	 * @return
//	 */
//	private static boolean isGeneModeul(String fname, String geneModules2) {
//		String[] mos=geneModules2.split(",");
//		for(String mo:mos)
//		{
//			if(fname.startsWith(mo.trim()))
//				return true;
//				 
//					
//		}
//		return false;
//	}
//
//	private static List<Head> getHeads(Element table) {
//		List<Head> li = new ArrayList();
//		Element tbdy = table.children().get(0);
//		Elements trs = tbdy.children();
//		int n = 0;
//		for (Element e : trs) {
//			n++;
//			// except line 1
//			if (n < 2)
//				continue;
//			Head hd = new Head();
//
//			Elements tds = e.children();
//
//			hd.cn = tds.get(0).text();
//			if(	hd.cn.trim().equals("主键"))
//				hd.cn="ID";
//			hd.field = tds.get(1).text().toLowerCase();
//			li.add(hd);
//		}
//		return li;
//	}
//
//	private static void m() {
//		String tmplt_edit = "templete_edit.htm";
//
//		String listTxtTmplt = filex.read(templeteDir + File.separator
//				+ tmplt_list, "utf-8");
//
//		String prefix = "colo3_";
//
//		String heads_html = getHeadsHTML(heads_cn, heads_field, prefix);
//		System.out.println(heads_html);
//
//		listTxtTmplt = listTxtTmplt.replaceAll("@@heads", heads_html);
//		filex.write_utf8(templeteDir + File.separator + module_name
//				+ "_list.htm", listTxtTmplt);
//
//		// gene knowlibItem
//		module_name = "knowlibItem";
//		heads_cn = "条目,类型,状态,问题,关键字,答案,操作:编辑|删除";
//		heads_field = "item,type,state,question,keyword,answer";
//		gene(templeteDir + File.separator + module_name + "_list.htm");
//	}
//
//	private static void gene(List li) {
//		
//		String listTxtTmplt = filex.read(templeteDir + File.separator
//				+ tmplt_list, "utf-8");
//		String heads_html = getHeadsHTML(li, prefix);
//		if(isHasOpColum())
//			heads_html=heads_html+opStr;
//		core.log("-- gene head html:" + heads_html);
//		
//			
//		
//		listTxtTmplt = listTxtTmplt.replaceAll("@@heads", heads_html);
//		listTxtTmplt = listTxtTmplt.replaceAll("@@module", module_name);
//		//listTxtTmplt = listTxtTmplt.replaceAll("@@globalop", "globalop");
//		 
//		
//		
//		String fileName = targetDir + File.separator + module_name
//				+ "_list.htm";
//		filex.write_utf8(fileName, listTxtTmplt);
//		core.copy(templeteDir + File.separator
//				+ "templete_list.js", targetDir + File.separator + module_name
//				+ "_list.js");
//		
//		core.log("-- gene   page path:" + fileName);
//		$.log("ok");
//	}
//
//	private static boolean isHasOpColum() {
//	 
//		return showOpColumn;
//	}
//
//	private static String getHeadsHTML(List<Head> li, String prefix) {
//		String r = "";
//		String temp_one_head = "  <th data-options=\"field:'@fld' ,width:100\"  >@cn</th>";
//		for (Head hd : li) {
//			String s = temp_one_head.replaceAll("@fld", prefix + hd.field);
//			s = s.replaceAll("@cn", hd.cn);
//			r = r + s + "\r\n";
//		}
//
//		return r;
//	}
//
//	private static void gene(String string) {
//		String prefix = "colo3_";
//		String listTxtTmplt = filex.read(templeteDir + File.separator
//				+ tmplt_list, "utf-8");
//		String heads_html = getHeadsHTML(heads_cn, heads_field, prefix);
//		core.log("-- gene head html:" + heads_html);
//		listTxtTmplt = listTxtTmplt.replaceAll("@@heads", heads_html);
//		String fileName = templeteDir + File.separator + module_name
//				+ "_list.htm";
//		filex.write_utf8(fileName, listTxtTmplt);
//		core.log("-- gene   page path:" + fileName);
//	}
//
//	private static String getHeadsHTML(String heads_cn, String heads_field,
//			String prefix) {
//		List<Head> li = new ArrayList();
//		String[] hd_cn_a = heads_cn.split(",");
//		String[] a = heads_field.split(",");
//		int n = 0;
//		for (String fld : a) {
//			String[] a2 = fld.split("\\.");
//			Head h = new Head();
//			h.cn = getHeadCn(hd_cn_a[n]);
//			h.field = a2[0];
//			if (a2.length > 1)
//				h.type = getType(a2[1]);
//			li.add(h);
//			n++;
//
//		}
//		if (hd_cn_a.length > a.length) {
//			Head h2 = new Head();
//			h2.cn = "操作";
//			h2.field = "op";
//			li.add(h2);
//
//		}
//		String r = "";
//		String temp_one_head = "  <th data-options=\"field:'@fld' ,width:100\"  >@cn</th>";
//		for (Head hd : li) {
//			String s = temp_one_head.replaceAll("@fld", prefix + hd.field);
//			s = s.replaceAll("@cn", hd.cn);
//			r = r + s + "\r\n";
//		}
//
//		return r;
//	}
//
//	private static String getHeadCn(String headCn) {
//		if (headCn.startsWith("操作"))
//			return "操作";
//		else
//			return headCn;
//	}
//
//	private static String getType(String string) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
