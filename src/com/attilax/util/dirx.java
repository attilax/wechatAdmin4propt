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
	@author attilax 老哇的爪子
		@since  2014-5-27 下午02:42:43$
	
	 * @param s
	 * @return
	 */
	public static String getParentPath(String s) {
		// attilax 老哇的爪子  下午02:42:43   2014-5-27 
		File f=new File(s); 
		return f.getParent();
	}

}
