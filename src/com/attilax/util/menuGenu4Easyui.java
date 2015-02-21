package com.attilax.util;

import java.util.List;

import com.attilax.collection.listUtil;

public class menuGenu4Easyui {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s2 = "    <a href=\"#\" class=\"easyui-splitbutton\" data-options=\" iconCls:'icon-edit'\" onclick=\"javascript:\">@name</a>";
		String path = "C:\\Users\\Administrator\\Desktop\\new  3.txt";
		List<String> li = listUtil.file2List(path, "utf-8");
		for (String line : li) {
			int start = line.indexOf("、");
			int end = line.indexOf("\t");
			// String[] a=line.split("");
			String s = line.substring(start + 1, end);
			// System.out.println(s);
			String s3 = s2.replaceAll("@name", s);
			System.out.println(s3);

		}

		String s = "个人常用语,	部门常用语,	公司常用语,	子站点常用语";
		s = "类别,标题,内容,类型,快捷键,操作";
		s = "ID,IP地址,开始时间,结束时间,屏蔽原因,操作";
		geneMenuItem4html(s);

		s="ID,名称,类型,所属,默认代码,带有面板,带有监测,定制界面,操作";
		geneTableHeadColumn(s);

	}

	private static void geneTableHeadColumn(String strColumn) {
		String tmp = "<th data-options=\"field:'@idx'\">@name</th>";
		String[] a = strColumn.split(",");
		int n = 0;
		for (String s : a) {
			s = s.trim();
			System.out.println(tmp.replaceAll("@name", s).replaceAll("@idx",
					"idx"+String.valueOf(n)));
			n++;
		}
	}

	private static void geneMenuItem4html(String s2) {
		String[] a = s2.split(",");
		for (String s : a) {
			s = s.trim();
			System.out.println("  <option>" + s.trim() + "</option> ");
		}

	}

}
