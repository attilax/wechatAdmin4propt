package com.attilax.jar;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.attilax.io.filex;
import com.attilax.io.pathx;
import com.attilax.util.listUtil;
import com.attilax.util.travDir;

import net.sf.json.JSONArray;

/**
 * o225
 * 
 * @author attilax
 * 
 */
public class jarX {
static	String prjDir;
	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		  prjDir="D:\\workspace\\barcodeo5\\lib - 副本";
		 iniJarKV(prjDir);
	
	//	iniJarKV_prj(prjDir);
 		String srcDir = "D:\\workspace\\barcodeo5\\src";
 		String trashDir = "c:\\CanDelJars\\";
	 	List<String> CanDelJars = moveNOmustJar(srcDir, trashDir);
	//	List<String> CanDelJars = moveNOmustJar_prj();

		String s2 = listUtil.toString_jsonFmt(CanDelJars);
		System.out.println(s2);

	}

	 
 

	/**
	@author attilax 老哇的爪子
		@since  2014-5-8 上午09:26:33$
	
	 * @return
	 */
	private static List<String> moveNOmustJar_prj() {
		// attilax 老哇的爪子  上午09:26:33   2014-5-8 
		String srcDir =prjDir+ File.separator+"src";
		String trashDir = prjDir+ File.separator+"CanDelJars"+ File.separator;
		
		return moveNOmustJar(srcDir,trashDir);
	}




	private static void iniJarKV_prj(String prjDir) {
		 iniJarKV(prjDir+File.separator+"lib");
		
	}

	private static List<String> moveNOmustJar(String dir, String trashDir) {
		List<String> files = travDir.getAllFileList(dir, "java");
		for (String file : files) {
			List<String> classes = IncludedClasses(file);
			for (String cls : classes) {
				String jar = getJar(cls);
				if (jar != null)
					if (!MustJar.contains(jar))
						MustJar.add(jar);
			}
		}

		// AllJar=travDir.getAllFileList(strPath, extName)
		AllJar.removeAll(MustJar);
		List<String> CanDelJars = AllJar;
		for (String f : CanDelJars) {
			File oldFile = new File(f);
			filex.move(f,trashDir + oldFile.getName());
//			
//			// 将文件移到新文件里
//			
//			File fnew = new File(trashDir + oldFile.getName());
//			oldFile.renameTo(fnew);
		}
		return CanDelJars;
	}

	static List AllJar = new ArrayList();
	static Set MustJar = new HashSet();
	static Map javKV = new HashMap();

	/**
	 * jar dir
	 * 
	 * @param dir
	 */
	private static void iniJarKV(String dir) {

		
		List<String> files = travDir.getAllFileList(dir, "jar");
		AllJar.addAll(files);
		for (String file : files) {
			List<String> clses = JarFileView.files(file);
			for (String cls : clses) {
				javKV.put(cls, file);
			}
		}

	}

	private static String getJar(String cls) {

		return (String) javKV.get(cls);
	}

	private static List IncludedClasses(String file) {
		List li = new ArrayList();
		List<String> txt = readFile2list(file);
		for (String line : txt) {
			String s = line.trim();
			if (s.startsWith("import ")) {
				s = s.replace("import ", "");
				s = s.replace(";", "");
				if (!s.startsWith("java."))
					li.add(s);
			}

		}
		return li;
	}

	/**
	 * @author attilax 1466519819@qq.com
	 * @version c0
	 **/
	public static ArrayList<String> readFile2list(String path) {
		// lineNum=0;
		BufferedReader reader;
		ArrayList li = new ArrayList<String>();

		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(new FileInputStream(path), "UTF-8");
		} catch (Exception e1) {

			e1.printStackTrace();
			throw new RuntimeException(e1);
		}

		try {
			// reader = new BufferedReader(isr);
			reader = new BufferedReader(isr);

			// BufferedWriter writer = new BufferedWriter(new FileWriter(dest));
			String line = reader.readLine();
			while (line != null) {
				// writer.write(line);
				li.add(line);
				line = reader.readLine();
				// lineNum++;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return li;
	}

}
