package com.attilax.util;


import java.io.StringReader;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

 


public class proxoolController {
	public static   String  url;
	public static   String  user;
	public static   String  pwd;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ini();
	}


	public static void ini() {
		if(url==null)
		{
		String s=god.getClassPath()+"/proxool.xml";
		String xml_str=fileC0.Read(s);
		System.out.println(getValue("",xml_str));
		}
		 
		
		 
		System.out.println("f" );
	}
	
	
	public static String getValue(String keyName,String xml_s)   {
		//1.获得DocumentBuilderFactory  对象
		DocumentBuilderFactory  builderFactory=DocumentBuilderFactory .newInstance();
		//2。获得DocumentBuilder对象
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = builderFactory.newDocumentBuilder();
		
		//3.使用documentBuilder来解析XML生成Document对象
		Document document=documentBuilder.parse(new InputSource( new StringReader( xml_s ) ) );
		document.normalize(); // 删除非XML数据
		NodeList nodeList=document.getElementsByTagName("driver-url");
		Node node=nodeList.item(0);
		
		
		NodeList li2=document.getElementsByTagName("property");
		for(int i=0;i<li2.getLength();i++)
		{
			Node nd=li2.item(i);
		 String name=   nd.getAttributes().getNamedItem("name").getTextContent();
		 String value=   nd.getAttributes().getNamedItem("value").getTextContent();
		 if(name.equals("user"))
			 user=value;
		 if(name.equals("password"))
			 pwd=value;
		}
		
		url=node.getTextContent();
		url=url.trim();
		url=url.replace('\n', ' ');
		url=url.replace('\t', ' ');url=url.trim();
		return node.getTextContent();
		
		
		} catch ( Exception e) {
			 
			e.printStackTrace();
		}
		return null;

	 }


	public static void getnew() {
		// TODO Auto-generated method stub
		
	}


}
