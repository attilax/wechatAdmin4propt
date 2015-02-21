package com.attilax.net;

//import org.jsoup.Jsoup;

 


public class websitex {
	
	public static String WebpageContent(String url,String encode)
	{
		return  new GetDataByURL().downloadPage(url,encode);
		
	}
	
	 
	
	public static void main(String[] a) {
		String ip="183.2.49.184";
		 String add = addr(ip);
		System.out.println("---");
		System.out.println(add);
	}



	private static String addr(String ip) {
		String urlContent = WebpageContent("http://www.123cha.com/ip/?q="+ip,"utf-8");
		 String txt=html2txt(urlContent);
		 String left="参考数据一:";
		String add=com.attilax.text.strUtil.Mid(txt, left, "参考数据二:");
		return add;
	}

	public static String html2txt(String urlContent) {
//		 org.jsoup.nodes.Document doc = null;
//		//filex.write(path + ".htm", html);
//		doc = Jsoup.parse(urlContent);
//		
//		return  doc.text() ;
		return null;
	}



	/**
	@author attilax 老哇的爪子
		@since  2014-5-12 下午01:24:44$
	
	 * @param url
	 * @return
	 */
	public static String WebpageContent(String url) {
		// attilax 老哇的爪子  下午01:24:44   2014-5-12 
		return WebpageContent(url,"utf-8");
	}

}
