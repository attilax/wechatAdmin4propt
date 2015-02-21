package com.attilax.collection;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.ecs.xhtml.object;

import com.attilax.core;
import com.attilax.corePkg.Iadd;
import com.attilax.text.strUtil; //import com.attilax.util.DbNdsController;
//import com.attilax.util.Dbcontroller;
import com.attilax.util.Func_4SingleObj;
import com.attilax.util.Func_wzPre;
import com.attilax.util.Funcx;
import com.attilax.util.fileC0;
import com.attilax.util.resultSetImp;
import com.attilax.util.securyInt;
import com.attilax.util.tryX;
import com.focusx.entity.TMbGroup;
import com.focusx.service.impl.Fun_4reduce;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//@SuppressWarnings("all")
public class listUtil extends fileC0 {

	public static <atiType> Iterator<atiType> iterator(final atiType[] objs) {

		List<atiType> lstint = new ArrayList<atiType>() {

			{
				for (atiType o : objs) {
					add(o);
				}
			}
		}; // Iterator遍历一
		return lstint.iterator();
	}

	/**
	 * no return value
	 * 
	 * @param li
	 * @param function
	 */
	@Deprecated
	public static void map(Object li, Func function) {
		if (li instanceof List) {
			List objLi = (List) li;
			for (int i = 0; i < objLi.size(); i++) {
				Object o = function.invoke(objLi.get(i));
				objLi.remove(i);
				objLi.add(i, o);
			}
		} else {
			Object[] objLi = (Object[]) li;
			for (int i = 0; i < objLi.length; i++) {
				objLi[i] = function.invoke(objLi[i]);
			}
		}
	}

	public static void print(ArrayList li4insertPointList) {

		System.out.println("--list size:" + li4insertPointList.size());
		for (int i = 0; i < li4insertPointList.size(); i++) {
			String[] a = (String[]) li4insertPointList.get(i);
			for (int j = 0; j < a.length; j++) {
				System.out.print(a[j] + ",");
			}
			System.out.print("\r\n");
		}

	}

	public static void print(List<String[]> li) {

		System.out.println("--list size:" + li.size());
		for (int i = 0; i < li.size(); i++) {
			String[] a = (String[]) li.get(i);
			for (int j = 0; j < a.length; j++) {
				System.out.print(a[j] + ",");
			}
			System.out.print("\r\n");
		}
	}

	public static void printListString(List<String> li) {

		System.out.println("--list size:" + li.size());
		for (int i = 0; i < li.size(); i++) {
			String a = (String) li.get(i);

			System.out.print(a);

			System.out.print("\r\n");
		}
	}

	public static List deDulicate(List li) {
		List listr = new ArrayList<String>();
		Set set = new HashSet();
		for (int i = 0; i < li.size(); i++) {
			String line = (String) li.get(i);
			if (!set.contains(line)) {
				set.add(line);
				listr.add(line);
			}
		}
		return listr;

	}

	public static List<String> deDulicateXing(List<String> li) {
		List listr = new ArrayList<String>();
		Set set = new HashSet();
		for (int i = 0; i < li.size(); i++) {
			String line = (String) li.get(i);
			if (!set.contains(line)) {
				String lineFejwe = fejwe(line);
				if (!set.contains(lineFejwe)) {
					set.add(line);
					listr.add(line);
				}
			}
		}
		return listr;

	}

	public static String fejwe(String line) {
		String[] a = line.split(",");
		String[] a2 = new String[a.length];
		for (int i = 0; i < a2.length; i++) {
			a2[i] = a[a.length - i - 1];
		}
		String s = listUtil.join(a2);
		return s;
	}

	private static String join(String[] a2) {
		String s = "";
		for (int i = 0; i < a2.length; i++) {
			if (s.equals(""))
				s = a2[i];
			else
				s = s + "," + a2[i];
		}
		return s;
	}

	/**
	 * default spitor is deuhao (,)
	 * 
	 * @param tonitsi
	 * @return
	 */
	public static String deDulicate(String tonitsi) {
		List<String> li = strUtil.toList(tonitsi);
		li = listUtil.deDulicate(li);
		String s = listUtil.toString(li);
		return s;
	}

	public static String deDulicate(String tonitsi, String splitor) {
		List<String> li = strUtil.toList(tonitsi, splitor);
		li = listUtil.deDulicate(li);
		String s = listUtil.toString(li, splitor);
		return s;
	}

	/**
	 * join by comma
	 * 
	 * @param li
	 * @return
	 */
	public static String toString(List<String> li) {
		String s = "";
		for (String str : li) {
			s = s + "," + str;
		}
		s = strUtil.trimx(",", s);
		return s;
	}

	public static String toString(Set<String> set) {
		List<String> li = new ArrayList<String>();
		li.addAll(set);
		String s = "";
		for (Object str : li) {
			if (str instanceof String)
				s = s + "," + str;
			else
				s = s + "," + String.valueOf(str);
		}
		s = strUtil.trimx(",", s);
		return s;
	}

	public static String toString(List<String> li, String splitor) {
		String s = "";
		for (String str : li) {
			s = s + splitor + str;
		}
		s = strUtil.trimx(splitor, s);
		return s;
	}

	public static Set<String> list2set(List<String> li) {
		Set set = new HashSet();
		for (String str : li) {
			set.add(str);
		}
		return set;
	}

	/**
	 * add recomm in o4f default splitor is space
	 * 
	 * @param strx
	 * @return
	 */
	public static Set<String> toSet(String strx) {
		Set set = new HashSet();
		if (strx == null) // cb5
			return set;
		String[] a = strx.split(" ");
		for (String str : a) {
			if (str.trim().equals(""))
				continue;
			set.add(str);
		}
		return set;
	}

	public static Set<String> toSet_splitComma(String strx) {
		Set set = new HashSet();
		if (strx == null) // cb5
			return set;
		String[] a = strx.split(",");
		for (String str : a) {
			if (str.trim().equals(""))
				continue;
			set.add(str);
		}
		return set;
	}

	public static List<Map.Entry<String, String>> orderByValue(
			Map<String, String> mp) {
		List<Map.Entry<String, String>> mappingList = null;
		Map<String, String> map = new TreeMap<String, String>();
		map.put("aaaa", "month");
		map.put("bbbb", "bread");
		map.put("ccccc", "attack");
		map = mp;

		// 通过ArrayList构�?函数把map.entrySet()转换成list
		mappingList = new ArrayList<Map.Entry<String, String>>(map.entrySet());
		// 通过比较器实现比较排�?
		Collections.sort(mappingList,
				new Comparator<Map.Entry<String, String>>() {
					public int compare(Map.Entry<String, String> mapping1,
							Map.Entry<String, String> mapping2) {
						String int1str = mapping1.getValue();
						int int1 = Integer.parseInt(int1str);
						int int2 = Integer.parseInt(mapping2.getValue());
						if (int1 > int2)
							return -1;
						else if (int1 == int2)
							return 0;
						else
							return 1;
						// return
						// mapping1.getValue().compareTo(mapping2.getValue());
					}
				});

		return mappingList;
	}

	public static List<Map.Entry<String, Integer>> orderByValueIntx(
			Map<String, Integer> mp) {
		List<Map.Entry<String, Integer>> mappingList = null;
		Map<String, Integer> map = new TreeMap<String, Integer>();

		map = mp;

		// 通过ArrayList构�?函数把map.entrySet()转换成list
		mappingList = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		// 通过比较器实现比较排�?
		Collections.sort(mappingList,
				new Comparator<Map.Entry<String, Integer>>() {
					public int compare(Map.Entry<String, Integer> mapping1,
							Map.Entry<String, Integer> mapping2) {
						Integer int1str = mapping1.getValue();
						int int1 = (int1str);
						int int2 = (mapping2.getValue());
						if (int1 > int2)
							return -1;
						else if (int1 == int2)
							return 0;
						else
							return 1;
						// return
						// mapping1.getValue().compareTo(mapping2.getValue());
					}
				});

		return mappingList;
	}

	public static List<Map.Entry<String, String>> orderByValueFloat(
			Map<String, String> mp) {
		List<Map.Entry<String, String>> mappingList = null;
		Map<String, String> map = new TreeMap<String, String>();
		map.put("aaaa", "month");
		map.put("bbbb", "bread");
		map.put("ccccc", "attack");
		map = mp;

		// 通过ArrayList构�?函数把map.entrySet()转换成list
		mappingList = new ArrayList<Map.Entry<String, String>>(map.entrySet());
		// 通过比较器实现比较排�?
		Collections.sort(mappingList,
				new Comparator<Map.Entry<String, String>>() {
					public int compare(Map.Entry<String, String> mapping1,
							Map.Entry<String, String> mapping2) {
						String int1str = mapping1.getValue();
						Float int1 = Float.parseFloat(int1str);
						Float int2 = Float.parseFloat(mapping2.getValue());
						if (int1 > int2)
							return -1;
						else if (int1 == int2)
							return 0;
						else
							return 1;
						// return
						// mapping1.getValue().compareTo(mapping2.getValue());
					}
				});

		return mappingList;
	}

	public List<Map.Entry<String, String>> orderByValueInt(
			Map<String, String> mp) {
		List<Map.Entry<String, String>> mappingList = null;
		Map<String, String> map = new TreeMap<String, String>();
		map.put("aaaa", "month");
		map.put("bbbb", "bread");
		map.put("ccccc", "attack");
		map = mp;

		// 通过ArrayList构�?函数把map.entrySet()转换成list
		mappingList = new ArrayList<Map.Entry<String, String>>(map.entrySet());
		// 通过比较器实现比较排�?
		Collections.sort(mappingList,
				new Comparator<Map.Entry<String, String>>() {
					public int compare(Map.Entry<String, String> mapping1,
							Map.Entry<String, String> mapping2) {
						String int1str = mapping1.getValue();
						Float int1 = Float.parseFloat(int1str);
						Float int2 = Float.parseFloat(mapping2.getValue());
						if (int1 > int2)
							return -1;
						else if (int1 == int2)
							return 0;
						else
							return 1;
						// return
						// mapping1.getValue().compareTo(mapping2.getValue());
					}
				});

		return mappingList;
	}

	public static String saveListMap(List<Map.Entry<String, String>> Listmp,
			String target) {

		String sx = "";

		for (Entry<String, String> mp : Listmp) {

			String s = mp.getKey();
			String val = mp.getValue();
			sx = sx + s + "\t" + val + "\r\n";

		}
		fileC0 fc = new fileC0();
		fc.save(sx, target);
		return sx;

	}

	public static String saveListMapStrInt(
			List<Map.Entry<String, Integer>> Listmp, String target) {

		String sx = "";

		for (Entry<String, Integer> mp : Listmp) {

			String s = mp.getKey();
			String val = mp.getValue().toString();
			sx = sx + s + "\t" + val + "\r\n";

		}
		fileC0 fc = new fileC0();
		fc.save(sx, target);
		return sx;

	}

	public static void isContain(String word, List<String> li) {
		for (String line : li) {

		}

	}

	public String orderByValueInt(List<Map<String, Integer>> liR) {

		String s = "";
		for (Map<String, Integer> mp : liR) {
			Set<String> set = mp.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String strx = it.next();
				String val = String.valueOf(mp.get(strx));

				s = s + "\r\n" + strx + "," + val;
			}
		}

		String[] arr = s.split("\r\n");
		List<String> li = Arrays.asList(arr);
		li = listUtil.delEmptyItem(li);

		// 通过比较器实现比较排�?
		Collections.sort(li, new Comparator<String>() {
			public int compare(String mapping1, String mapping2) {
				String[] a = mapping1.split(",");
				System.out.println("mapping1:" + mapping1 + "|" + mapping2);

				Float int1 = Float.parseFloat(a[1]);

				String[] a2 = mapping2.split(",");
				Float int2 = Float.parseFloat(a2[1]);
				if (int1 > int2)
					return -1;
				else if (int1 == int2)
					return 0;
				else
					return 1;
				// return mapping1.getValue().compareTo(mapping2.getValue());
			}
		});

		return listUtil.toString(li, " | ");

	}

	public String List2String(List list) {
		String s = "";
		StringBuilder strApp = new StringBuilder();
		List<String> li2 = list;

		String sx = "";
		int len = list.size();
		String enter = System.getProperty("line.separator");
		for (int i = 0; i < len; i++) {
			if (i % 2000 == 0)
				System.out.println("--saveList2file:" + i);
			sx = li2.get(i);
			if (s.equals("")) {
				s = sx;
				strApp.append(sx);
			} else
				strApp.append(enter).append(sx);
			// s=s+enter+sx;
		}

		s = strApp.toString();
		// writeFile(path,s,encode);
		return s;
	}

	private static List<String> delEmptyItem(List<String> li) {
		List<String> lir = new ArrayList<String>();
		for (String s : li) {
			if (s.trim().length() > 0)
				lir.add(s);
		}
		return lir;
	}

	// public Set getSetFromdb(String sql) {
	//
	// Set set = new HashSet<String>();
	// Dbcontroller dbc = new DbNdsController();
	// ResultSet rs = dbc.getrs(sql);
	// try {
	// while (rs.next()) {
	//
	// // 首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
	// // 然后使用GB2312字符集解码指定的字节数组
	// // name = new String(name.getBytes("ISO-8859-1"),"GB2312");
	//
	// // 输出结果
	// String txt = rs.getString(1);
	//
	// if (!set.contains(txt)) {
	//
	// set.add(txt);
	//
	// }
	//
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// throw new RuntimeException(e);
	// }
	// dbc.close();
	// return set;
	// }

	public static String deDulicate_SplitorIsSpace(String r) {
		return deDulicate(r, " ");
	}

	public static Map fromStr(String s, String itemSpliter, String lineSplitor) {
		Map mp4conventProblemType2sincinRenvaRelation = null;
		// String s=" |家庭矛盾>夫妻关系 |长辈代沟>长辈关系|亲子教育>孩子教育| 事业前�?>职场烦恼| 社会热点>自身烦恼";
		if (mp4conventProblemType2sincinRenvaRelation == null) {
			mp4conventProblemType2sincinRenvaRelation = new HashMap<String, String>();
			String[] a = s.split(lineSplitor);
			for (String type1Andtype2 : a) {
				if (type1Andtype2.trim().length() == 0)
					continue;
				type1Andtype2 = type1Andtype2.trim();
				String[] a2 = type1Andtype2.split(itemSpliter);
				String type4prob = a2[0];
				String sincinType = a2[1];
				mp4conventProblemType2sincinRenvaRelation.put(type4prob,
						sincinType);
			}

		}
		// String sincinRenva=(String)
		// mp4conventProblemType2sincinRenvaRelation.get(ProblemType_hezifmt);
		// if(sincinRenva==null || sincinRenva.equals(""))
		// return ProblemType_hezifmt;
		// else
		return mp4conventProblemType2sincinRenvaRelation;

	}

	public static List<String> toList(String string, String splitor) {
		char[] splts = splitor.toCharArray();
		List<String> li = new ArrayList<String>();
		// Set set = new HashSet();
		// for(char ch:splts)
		// {
		// String sp
		String[] a = string.split(splitor);
		for (String str : a) {
			str = str.trim();
			li.add(str);
		}
		// }
		return li;
		// return null;
	}

	public static List orderByValueFloat(List<Map> li, final String orderField) {
		List<Map> mappingList = li;

		// 通过ArrayList构�?函数把map.entrySet()转换成list
		// mappingList = new ArrayList<Map<String,String>>(map.entrySet());
		// 通过比较器实现比较排�?
		Collections.sort(mappingList, new Comparator<Map>() {
			public int compare(Map mapping1, Map mapping2) {
				Float int1 = (Float) mapping1.get(orderField);

				Float int2 = Float.parseFloat(mapping2.get(orderField)
						.toString());
				if (int1 > int2)
					return -1;
				else if (int1 == int2)
					return 0;
				else
					return 1;
				// return mapping1.getValue().compareTo(mapping2.getValue());
			}
		});

		return mappingList;
	}

	public static List<String> file2List(String path, String encode) {

		fileC0 fc = new fileC0();
		List<String> li = fc.fileRead2list(path, encode);
		return li;

	}

	public static Map fromFile(String path, String encode, String splitor) {
		Map mp = new LinkedHashMap<String, String>();
		fileC0 fc = new fileC0();
		List<String> li = fc.fileRead2list(path, encode);
		for (String line : li) {
			if (line.trim().length() == 0)
				continue;
			String[] a = line.split(splitor);
			mp.put(a[0], a[1]);
		}
		return mp;
	}

	public static Set<String> toSet(String[] txtFiltArr) {
		Set set = new HashSet<String>();
		for (String mp : txtFiltArr) {
			set.add(mp);
		}
		return set;
	}

	public static Set<String> toSetFromJsonArray(String renvas, String value) {
		Set set2 = new HashSet<String>();
		JSONArray jsonArray = JSONArray.fromObject(renvas);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jo = (JSONObject) jsonArray.get(i);
			set2.add(jo.get(value));
		}
		return set2;
	}

	public static Set<String> toSet(List<Map> Li, String value) {
		Set set = new HashSet<String>();
		for (Map mp : Li) {
			set.add(mp.get(value));
		}
		return set;
	}

	public static String toString4liMap(List<Map> Li, String field) {
		Set set = new HashSet<String>();
		for (Map mp : Li) {
			String v = securyInt.getString(mp.get(field), "");
			if (v.length() > 0)
				set.add(v);
		}
		return toString(set);

	}

	/**
	 * o226
	 * 
	 * @param Li
	 * @return
	 */
	public static String toString_jsonFmt(Object Li) {
		String t = JSONArray.fromObject(Li).toString(2);
		return t;

	}

	/**
	 * for debug
	 * 
	 * @param Li
	 * @return
	 */
	public static void print(Object Li) {
		String t = JSONArray.fromObject(Li).toString(2);
		System.out.println(t);
		core.log(t);

	}

	public static List<String> toList(Set<String> set1) {
		List<String> li = new ArrayList<String>();
		Iterator i = set1.iterator();// 先迭代出�?

		while (i.hasNext()) {// 遍历
			String item = (String) i.next();
			li.add(item);
		}
		return li;
	}

	public static Set toSet(String string, String splitor) {
		Set<String> li = new HashSet<String>();
		// Set set = new HashSet();
		String[] a = string.split(splitor);
		for (String str : a) {
			str = str.trim();
			li.add(str);
		}
		return li;

	}

	public static String getVals(List<Map> li, String string) {
		List<String> lix = new ArrayList<String>();
		for (Map m : li) {
			String id = (String) m.get("id");
			lix.add(id);

		}
		return toString(lix);
	}

	/**
	 * split by comma
	 * 
	 * @param files
	 * @return
	 */
	public static List toList(String str) {

		return listUtil.toList(str, ",");
	}

	/**
	 * split by comma o4d
	 * 
	 * @param files
	 * @return
	 */
	public static List toList_byCharSet(String str) {
		String[] a = strUtil.SplitByNone(str);
		return listUtil.toList(a);
	}

	/**
	 * o4d defValue is empty
	 * 
	 * @param a
	 * @param i
	 * @return
	 */
	public static String getIndexValue(String[] a, int i) {

		try {
			return a[i];
		} catch (Exception e) {
			return "";
		}

	}

	public static ResultSet toResultSet(List li) {
		resultSetImp rsi = new resultSetImp(li);
		return rsi;
	}

	public static String toString_jsonFmt(List<Map> li3) {
		String s = JSONArray.fromObject(li3).toString(2);
		return s;
	}

	public static String[] deDulicate(String[] keywordArr) {
		Set set = listUtil.toSet(keywordArr);
		String[] a = listUtil.toStrArr(set);
		return a;
	}

	public static void main(String[] args) {
		// String[] temp= {"aa","bb" };
		// String sts[3] = {'业务进行中','通过','否决','退回'};
		java.lang.String[] a = { "aa", "bb", "aa" };
		String[] b = deDulicate(a);
		String s = JSONArray.fromObject(b).toString(2);
		System.out.println(s);

	}

	private static String[] toStrArr(Set set) {
		List li = listUtil.toList(set);

		return listUtil.toStrArr(li);
	}

	private static String[] toStrArr(List li) {
		String[] arr = (String[]) li.toArray(new String[li.size()]);
		return arr;
	}

	public static <T> T getIndexValue(List<T> wordSplitPointList, int index,
			T defValue) {

		try {
			T val = wordSplitPointList.get(index);
			return val;
		} catch (Exception e) {

		}

		return defValue;
	}

	public static List toList(String[] a) {
		List li = new ArrayList();
		for (String s : a) {
			li.add(s);
		}
		return li;
	}

	public static List<String> filter(List<String> objLi, Func func) {
		List li = new ArrayList();
		for (int i = 0; i < objLi.size(); i++) {
			String string = objLi.get(i);
			boolean o = (Boolean) func.invoke(string);
			if (!o)
				li.add(string);
		}
		return li;
	}

	/**
	 * enhance effice todox o4d
	 * 
	 * @param <atiType>
	 * @param arr
	 * @param func
	 * @return
	 */
	// Generic method
	public static <atiType> atiType filterO4(atiType arr, Func func) {
		// List li = new ArrayList();
		// for (int i = 0; i < objLi.size(); i++) {
		// String string = objLi.get(i);
		// boolean o = (Boolean) func.invoke(string);
		// if (!o)
		// li.add(string);
		// }

		List li = new ArrayList();
		if (arr instanceof String[]) {
			String[] sa = (String[]) arr;
			for (int i = 0; i < sa.length; i++) {
				String charx = sa[i];
				if (!(Boolean) func.invoke(charx))
					li.add(charx);

			}
			return (atiType) toStrArr(li);
		}
		if (arr instanceof List) {
			List li3 = (List) arr;
			for (int i = 0; i < li3.size(); i++) {
				String charx = (String) li3.get(i);
				if (!(Boolean) func.invoke(charx))
					li.add(charx);

			}
			return (atiType) (li);
		}

		return null;
	}

	public static List<String> filt(List<String> objLi, Func func) {
		List li = new ArrayList();
		for (int i = 0; i < objLi.size(); i++) {
			String string = objLi.get(i);
			boolean o = (Boolean) func.invoke(string);
			if (!o)
				li.add(string);
		}
		return li;
	}

	@Deprecated
	public static List<String> mapx(Object li, Func function) {
		List lir = new ArrayList();
		if (li instanceof List) {
			List objLi = (List) li;
			for (int i = 0; i < objLi.size(); i++) {
				Object o = function.invoke(objLi.get(i));

				lir.add(i, o);
			}
		} else {
			Object[] objLi = (Object[]) li;
			for (int i = 0; i < objLi.length; i++) {
				lir.add(function.invoke(objLi[i]));
			}
		}
		return lir;
	}

	// todox
	@Deprecated
	public static <atiType, retType> int reduce(atiType obj, Func function) {
		// Object sum = init;
		int sum = 0;
		// if (obj instanceof List) {
		// List objLi = (List) obj;
		// for (int i = 0; i < objLi.size(); i++) {
		// sum = (Integer) function.invoke(sum, objLi.get(i));
		//
		// }
		// return sum;
		// }

		if (obj instanceof String[]) {
			String[] str_a = (String[]) obj;
			for (int i = 0; i < str_a.length; i++) {
				sum = (Integer) function.invoke(sum, str_a[i]);

			}
			return sum;
		}

		return 0;
	}

	@Deprecated
	public static <atiType, retType> retType reduceO4(atiType obj, Func function) {
		String lastVal = "";
		if (obj instanceof List) {
			List objLi = (List) obj;
			for (int i = 0; i < objLi.size(); i++) {
				lastVal = (String) function.invoke(lastVal, objLi.get(i));

			}
			return (retType) lastVal;
		}
		return null;
	}

	@Deprecated
	public static <atiType, retType> retType reduceO4d(atiType obj,
			Func function) {

		retType lastVal = null;

		if (obj instanceof List) {
			if (lastVal == null)
				lastVal = (retType) new ArrayList();

			List objLi = (List) obj;
			for (int i = 0; i < objLi.size(); i++) {
				lastVal = (retType) function.invoke(lastVal, objLi.get(i));

			}
			return (retType) lastVal;
		}
		return null;
	}

	@Deprecated
	public static Object reduce(Object[] arr, Object init, Func f) {

		Object sum = init;
		for (int i = 0; i < arr.length; i++) {
			sum = f.invoke(sum, arr[i]);
		}
		return sum;
	}

	/**
	 * ]o4f notice:the map val obj is a objArr..maybe for checkbox
	 * 
	 * @param parameterMap
	 * @return
	 */
	public static Map caseIngor(Map<String, Object> parameterMap) {
		Map m = new HashMap();

		for (String key : parameterMap.keySet()) {
			Object obj = parameterMap.get(key);
			core.log("key:" + key);
			core.log("objtype:" + obj.getClass().getName());
			String[] a = (String[]) obj;
			if (a.length > 0) {
				// obj.
				m.put(key, a[0]);
				m.put(key.toLowerCase(), a[0]);
				// m.put(key, obj.toString());
				// m.put(key.toLowerCase(), obj.toString());
				// 设置参数
				// PropertyUtils.setProperty(user2, key,
				// ConvertUtils.convert(parameterMap.get(key), type));
			}
		}
		return m;
	}

	public static <atiType> atiType filterO4(atiType arr,
			Funcx<String, Boolean> func) {
		List li = new ArrayList();
		if (arr instanceof String[]) {
			String[] sa = (String[]) arr;
			for (int i = 0; i < sa.length; i++) {
				String charx = sa[i];
				if (!(Boolean) func.invoke(charx))
					li.add(charx);

			}
			return (atiType) toStrArr(li);
		}
		if (arr instanceof List) {
			List li3 = (List) arr;
			for (int i = 0; i < li3.size(); i++) {
				String charx = (String) li3.get(i);
				if (!(Boolean) func.invoke(charx))
					li.add(charx);

			}
			return (atiType) (li);
		}

		return null;
	}

	public static <aty> void xo44(aty e) {

	}

	@Deprecated
	// <String, String>
	public static <atiType, type1, funcType> atiType map_generic(atiType arr,
			Funcx function) {
		List lir = null;
		if (arr instanceof List) {
			if (lir == null)
				lir = (List) new ArrayList();
			List<type1> objLi = (List<type1>) arr;
			for (int i = 0; i < objLi.size(); i++) {
				core.debug(" type1 type:");
				type1 type1obj = (type1) objLi.get(i);
				core.debug(" type1 objtype:"
						+ type1obj.getClass().getCanonicalName());
				// Funcx<type1, type1> fc=(Funcx<type1, type1>)function;
				// Object o = null;
				// [Ljava.lang.Object; cannot be cast to [Ljava.lang.String;
				type1 o = (type1) function.invoke(type1obj);
				// System.out.println(o);
				lir.add(i, o);
			}
		} else {
			Object[] objLi = (Object[]) arr;
			for (int i = 0; i < objLi.length; i++) {
				// lir.add(function.invoke((type1)objLi[i]));
			}
		}
		return (atiType) lir;
		// return null;
	}

	public static <atiType, iptType, retType> List<retType> map_generic(
			atiType arr, Func_4SingleObj<iptType, retType> function) {
		List<retType> lir = null;
		if (lir == null)
			lir = (List) new ArrayList();
		if (arr instanceof List) {
			
			List<iptType> objLi = (List<iptType>) arr;
			for (int i = 0; i < objLi.size(); i++) {
				core.debug(" type1 type:");
				iptType type1obj = (iptType) objLi.get(i);
				core.debug(" type1 objtype:"
						+ type1obj.getClass().getCanonicalName());
				// Funcx<type1, type1> fc=(Funcx<type1, type1>)function;
				// Object o = null;
				// [Ljava.lang.Object; cannot be cast to [Ljava.lang.String;
				retType o = (retType) function.invoke(type1obj);
				// System.out.println(o);
				lir.add(i, o);
			}
		} else if(arr instanceof String)  //ops_jsonFmt
		{
			
		}
		else
		{
			iptType[] objLi = (iptType[]) arr;
			for (int i = 0; i < objLi.length; i++) {
				lir.add(function.invoke((iptType) objLi[i]));
			}
		}
		return lir;
		// return null;
	}
	
	/**
	 * 
	 */
	//todox jsonarray to iterator.
	/***
	 * 
	 */
	/**
	 *  jsonarray to iterator.
	 */
	public static <atiType, iptType, retType,retTypeFinnaly>  retTypeFinnaly  map_genericO5(
			atiType arr, Func_4SingleObj<iptType, retType> function,final Class<retTypeFinnaly> retTypeFinnalyClass) {
	
		
			retTypeFinnaly ja = new tryX<retTypeFinnaly>() {

				@Override
				public retTypeFinnaly item(Object t) throws Exception {
					// attilax 老哇的爪子 下午01:34:58 2014-5-26
					return retTypeFinnalyClass.newInstance();
				}
			}.$();
			List ia = (List) ja;
			// JSONArray arr1=(JSONArray)arr;
			List li = (List) arr;
			Iterator<iptType> it = li.iterator();
	 
			while (it.hasNext()) {
				iptType next = it.next();
				retType o = (retType) function.invoke(next);
				ia.add(o);
			}
			return   ja;
		
	
	}
	/**
	 * 
	@author attilax 老哇的爪子
		@since  2014-6-9 下午03:05:34$
	
	 * @param <atiType>
	 * @param <iptType>
	 * @param <retType>
	 * @param <atiPreInType>
	 * @param <atiPreRetType>
	 * @param arr
	 * @param function
	 * @return
	 */
	public static <atiType, iptType, retType,atiPreInType,atiPreRetType> List<retType> map_wzPre(
			atiPreInType arr, Func_wzPre<iptType,retType,atiPreInType,atiPreRetType>  function) {
		List<retType> lir = null;
		if (lir == null)
			lir = (List) new ArrayList();
		if (arr instanceof List) {
			
			List<iptType> objLi = (List<iptType>) arr;
			for (int i = 0; i < objLi.size(); i++) {
				core.debug(" type1 type:");
				iptType type1obj = (iptType) objLi.get(i);
				core.debug(" type1 objtype:"
						+ type1obj.getClass().getCanonicalName());
				// Funcx<type1, type1> fc=(Funcx<type1, type1>)function;
				// Object o = null;
				// [Ljava.lang.Object; cannot be cast to [Ljava.lang.String;
				retType o = (retType) function.$(type1obj);
				// System.out.println(o);
				lir.add(i, o);
			}
		} else if(arr instanceof String)  //ops_jsonFmt
		{
			JSONArray jsonArr=	(JSONArray) function.$Pre(arr);
		 
			for(Object o:jsonArr)
			{
				iptType o2=(iptType)o;
				retType o1 = (retType) function.$(o2);
				lir.add(o1);
			}
			
		}
		else
		{
			iptType[] objLi = (iptType[]) arr;
			for (int i = 0; i < objLi.length; i++) {
				lir.add(function.$((iptType) objLi[i]));
			}
		}
		return lir;
		// return null;
	}


	/**
	 * odf
	 * 
	 * @param <atiType>
	 * @param <retType>
	 * @param arr
	 * @param iniValue
	 * @param function
	 * @return
	 */
	@Deprecated
	public static <atiType, retType> retType reduceO4d(atiType arr,
			retType iniValue, Funcx function) {
		
		
		retType lastVal = null;

		if (iniValue != null)
			lastVal = iniValue;

		if (arr instanceof List) {
			if (lastVal == null)

				lastVal = (retType) new ArrayList();

			List objLi = (List) arr;
			for (int i = 0; i < objLi.size(); i++) {
				lastVal = (retType) function.invoke(lastVal, objLi.get(i));

			}
			return (retType) lastVal;
		}
		return null;
	}

	@Deprecated
	public static <atiType, retType> retType reduceO4d(atiType arr,
			Funcx function) {
		retType lastVal = null;

		if (arr instanceof List) {
			if (lastVal == null)

				lastVal = (retType) new ArrayList();

			List objLi = (List) arr;
			for (int i = 0; i < objLi.size(); i++) {
				lastVal = (retType) function.invoke(lastVal, objLi.get(i));

			}
			return (retType) lastVal;
		}
		return null;
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-5-14 下午03:17:16$
	 * 
	 * @param li2
	 * @return
	 */
	public static String join(List<String> li2) {
		// attilax 老哇的爪子 下午03:17:16 2014-5-14
		String s = "";
		for (String s2 : li2) {
			s += s2;
		}
		return s;
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-5-15 上午10:14:03$
	 * 
	 * @param li
	 * @param string
	 * @param fun_4reduce
	 * @return
	 */
	public static <curType, lastRetType, litype> lastRetType reduceO4d(
			litype li, lastRetType iniValue,
			Fun_4reduce<curType, lastRetType> fun_4reduce) {
		// attilax 老哇的爪子 上午10:14:03 2014-5-15
		lastRetType lastVal = null;

		if (iniValue != null)
			lastVal = iniValue;

		if (li instanceof List) {
			// if (lastVal == null)
			//				
			// lastVal = new ArrayList();

			List<curType> objLi = (List) li;
			for (int i = 0; i < objLi.size(); i++) {
				lastVal = fun_4reduce.$(objLi.get(i), lastVal);

			}
			return (lastRetType) lastVal;
		}

		return null;
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-26 下午12:50:14$
	
	 * @return
	 */
	public static com.attilax.corePkg.JSONArray mapxx() {
		// attilax 老哇的爪子  下午12:50:14   2014-5-26 
		return null;
	}

	// public static String reduceO4d(List li, Funcx<String, String> funcx) {
	// // TODO Auto-generated method stub
	// return null;
	// }

}
