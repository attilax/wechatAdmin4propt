package com.attilax.util;

public class aaa {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String sql = "select * from mood where LENGTH(content)<12 and LENGTH(content)>0 and content like '@str%' order by id desc limit 20";
		String str="$";
		sql = sql.replaceAll("@str", str.trim());
		System.out.println(sql);

	}

}
