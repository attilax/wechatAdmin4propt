package com.attilax.util;

import java.util.ArrayList;
import java.util.List;

public class fenjyeWord {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String s = "v.追逐，从事，追求";
		fenjyeWord c = new fenjyeWord();
		List<String[]> li = getList(s);
		System.out.println(li);
		listUtil.print(li);

		fileC0 fc = new fileC0();
		List lix = fc.fileRead2list(
				"C:\\Users\\Administrator\\Desktop\\dic\\虚词1.txt", "gbk");
		List liOK = c.changeMuiltWord2signle(lix);
		listUtil.printListString(liOK);
		liOK=listUtil.deDulicate(liOK);
	//	List adjCoreList=getadjCoreList(liOK);
	//	liOK.addAll(adjCoreList);
		
		fc.saveList2file(liOK, "c:\\word_syvtsi.txt", "utf-8");
		System.out.println("------finish");

	}

	private static List getadjCoreList(List liOK) {
		List linew=new ArrayList();
		for (int i = 0; i < liOK.size(); i++) {
			String line=(String) liOK.get(i);
			String[] a=line.split("\\.");
			String word=a[1];
			if(word.length()==3&& word.contains("的"))
			{	
				word=word.substring(0, word.length()-1);
				linew.add(a[0]+"."+word);
			 
			}
			
		}
		return linew;
		
	}

	private List changeMuiltWord2signle(List lix) {
		List r = new ArrayList<String[]>();
		for (int i = 0; i < lix.size(); i++) {
			String line = (String) lix.get(i);
			List<String[]> lia = getList(line);
			if (lia != null)

				r.addAll(lia);
		}
		List r2 = new ArrayList<String>();
		for (int i = 0; i < r.size(); i++) {
			String[] a = (String[]) r.get(i);
			r2.add(a[0] + "." + a[1]);
		}
		return r2;
	}

	private static List<String[]> getList(String s) {
		List li2 = new ArrayList();
		s = s.trim();
		String[] a = s.split("\\.");
		if (a.length < 2)
			return null;
		String tsisin = a[0];
		String words = a[1];
		String[] a2 = words.split("，");
		for (int i = 0; i < a2.length; i++) {
			String[] a3 = new String[2];
			a3[0] = tsisin;
			String word = a2[i].trim();
			;
			if (word.length() > 0)
				a3[1] = a2[i].trim();
			else
				continue;
			li2.add(a3);

		}
		return li2;
	}

}
