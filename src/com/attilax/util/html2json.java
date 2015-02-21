package com.attilax.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.attilax.io.filex;
import com.attilax.text.strUtil;

public class html2json {

	/**todox html2json
	 * todox list2json
	 * @param args
	 */
	public static void main(String[] args) {
		String regEx = "<img.*?/>";
		// regEx = "img.*alt";
		String str = filex
				.read("E:\\workspace\\imServer\\WebRoot\\Untitled-5.html");

			org.jsoup.nodes.Document doc = null;
		// filex.write(path + ".htm", html);
		doc = Jsoup.parse(str);
		Elements el = doc.getElementsByTag("img");
		int n=1;
		JSONArray ja=new JSONArray();
		for(Element e:el)
		{
			   String imagesPath = e.attr("src");
			   JSONObject o=new JSONObject();
			   o.accumulate("id", n);
			   n++;
			   o.accumulate("src", imagesPath);
			   ja.add(o);
			  //	            System.out.println("当前访问路径:"+imagesPath);
		}
		//todox  net.sf.json.JSONException: There is a cycle in the hierarchy!
		
//		JSONArray arr = JSONArray.fromObject(el);
//		String s = arr.toString(2);
		String s=ja.toString(2);
 		System.out.println(s);
 		filex.save(s, "c:\\defIcons.json");
		// List<String> li= strUtil.find(regEx, str);
		// for(String img:li)
		// {
		// int p1=img.indexOf("\"");
		// }

	}

}
