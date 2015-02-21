/**
 * @author attilax 1466519819@qq.com
 * @version  c0
 * Copyright 2013 attilax reserved.
 * All content (including but not limited to text, pictures, etc.) have copyright restrictions, the note license.
 * all the software source code  copyright belongs to the original owner.
 * Creative Commons protocol use please follow the "sign for non-commercial use consistent"; we do not welcome the large-scale duplication, and all rights reserved.
 * Commercial sites or unauthorized media may not copy software source code.
 **/

package com.attilax.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author attilax 1466519819@qq.com
 * @version c0
 **/
public class travDir {

	/**
	 * @author attilax 1466519819@qq.com
	 * @version c0
	 **/
	public static void main(String[] args) {

		long a = System.currentTimeMillis();
		refreshFileList("C:\\Users\\Administrator\\Workspaces\\MyEclipse 8.5\\homiSearchServer");// kkkkkkkkk
																									// kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk
		System.out.println(System.currentTimeMillis() - a);
		System.out.println(filelist.size());
	}

	public static ArrayList filelist = new ArrayList();

	/**
	 * @author attilax 1466519819@qq.com
	 * @version c0
	 **/
	public static void refreshFileList(String strPath) {
		File dir = new File(strPath);
		File[] files = dir.listFiles();

		if (files == null)
			return;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				refreshFileList(files[i].getAbsolutePath());
			} else {
				String strFileName = files[i].getAbsolutePath().toLowerCase();
				if (strFileName.contains(".java")) {
					System.out.println("---" + strFileName);
					filelist.add(files[i].getAbsolutePath());
				}
			}
		}
	}

	// public static
	/**
	 * o226
	 * 
	 * @param strPath
	 * @param ext
	 *            "java"
	 */
	public static List getAllFileList(String strPath, String extName) {
		List li = new ArrayList();
		File dir = new File(strPath);
		File[] files = dir.listFiles();

		if (files == null)
			return li;
		for (int i = 0; i < files.length; i++) {
			String absolutePath = files[i].getAbsolutePath();
			if (files[i].isDirectory()) {
				List li_t = getAllFileList(absolutePath, extName);
				li.addAll(li_t);
			} else {
				String strFileName = absolutePath.toLowerCase();
				if (strFileName.contains("."+extName)) {
					// System.out.println("---"+strFileName);
					// filelist.add(files[i].getAbsolutePath());
					li.add(absolutePath);
				}
			}
		}
		return li;
	}

}
