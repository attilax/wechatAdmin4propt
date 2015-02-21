package com.attilax.util;

import java.util.List;
import java.util.Set;

import com.attilax.core;
import com.attilax.collection.Func;
import com.attilax.collection.listUtil;
import com.attilax.io.filex;

public class geneIndex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String oriTxtPath = "c:\\t";
		String path = "C:\\clip_addIndexed.txt";
		if(args.length>0)
			oriTxtPath=args[0].trim();
		
		String encode=CheckEncode. getFileEncode(oriTxtPath);
	
			if(!validEncodeChinese(encode))
			{
				core.log(" the enchode is not chinse :"+encode);return;
			}
		String indexPath = "c:\\clip_indexPart.txt";
		gene(oriTxtPath,indexPath,encode);
		
		String indexTxt=filex.read(indexPath);
		String finaltxt=indexTxt+filex.read(oriTxtPath,encode);
		filex.save(finaltxt, path);
		System.out.println("f");
		
		

	}

	private static boolean validEncodeChinese(String encode) {
		String s="gb2312,gb23180,gbk,utf-8,unicode";
		Set set=listUtil.toSet_splitComma(s);
		
	 
		return set.contains(encode.toLowerCase());
	}

	private static void gene(String oriTxtPath, String path, String encode) {
		String s = filex.read(oriTxtPath);
		List<String> li = filex.read2list(oriTxtPath,encode);
		li = listUtil.filterO4(li, new Funcx<String, Boolean>() {

			@Override
			public Boolean invoke(String... o) {
				String line = o[0].trim();
				if (line.startsWith("#"))
					return false;

				return true;
			}
		});
		// li = listUtil.<List,String>map_generic(li,new Funcx<atiIptType,
		// atiRetType>(){
		//
		// @Override
		// public atiRetType invoke(atiIptType... o) {
		// // TODO Auto-generated method stub
		// return null;
		// }});
		// map
		//todox  java.lang.String cannot be cast to [Ljava.lang.String; 
		// start L is the list array..
		li = listUtil
				.<List<String>, String, Funcx<String, String>> map_generic(li,
						new Funcx<Object, String>() {

						 
							public String invoke(Object... o) {
								core.log(" invoke start");
								String line = o[0].toString().trim();
								line = line.replaceAll("###", "---");
								line = line.replaceAll("##", "++");
								line = line.replaceAll("#", "*");

								return line;
							}

							 
						});

		
		filex.saveList2file(li, path);
	}
}
