package com.attilax.util;

import java.io.File;
import java.util.List;

//import org.jfree.chart.ChartFactory;

import m.dirPkg.travDir;

public class dirx {

	public static List<String> getFiles(String strPath) {
		travDir.refreshFileList(strPath);
		List<String> li = travDir.filelist;
		
		return li;
	}

	/**
	@author attilax ���۵�צ��
		@since  2014-5-27 ����02:42:43$
	
	 * @param s
	 * @return
	 */
	public static String getParentPath(String s) {
		// attilax ���۵�צ��  ����02:42:43   2014-5-27 
		File f=new File(s); 
		return f.getParent();
	}

}
