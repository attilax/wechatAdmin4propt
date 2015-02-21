package com.attilax.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Levenshtein {

	public static void main(String[] args) {
		// 要比较的两个字符串
		String str1 = "今天星期四";
		String str2 = "今天是星期四";
		levenshtein(str1, str2);

		str1 = "今天星期四";
		str2 = "今天星期(四)";
		levenshtein(str1, str2);

		str1 = "今天星期四";
		str2 = "今天星期四223";
		levenshtein(str1, str2);

		str1 = "今天星期四";
		str2 = "今天新期四";
		levenshtein(str1, str2);
		
//		fentsiOr ftc=new fentsiOr();
//		String syvtsiLib = "c:\\word_syvtsi.txt";
//		
//		
//
//		List<String> li = new ArrayList<String>();
//		List<String> li2=new ArrayList();
//		String s = "你觉得|分手|后|能|重|新来|过|吗|";
//		s=ftc.filtSyvtsiSingleLine(s, "\\|", syvtsiLib);
//		fileC0 fc = new fileC0();
//		List<String> lif = fc.fileRead2list("C:\\exp\\fentsiOK_1.txt");
//		int n = 0;
//		Map mp = new HashMap<String, String>();
//		for (String linex : lif) {
//			
//			n++;
//			if (n % 300 == 0)
//				System.out.println("--now:" + n);
//			String line= ftc.filtSyvtsiSingleLine(linex, "\\|", syvtsiLib);
//			float syeosidu = levenshtein(s, line);
//		//	li.add(line);
//		//	li.add(String.valueOf(syeosidu));
//			
//			mp.put(line, String.valueOf( syeosidu));
//			
//
//		}
//		List lic=listUtil.orderByValueFloat(mp);
//		
//		listUtil.saveListMap (lic, "C:\\exp\\fentsiOK_1_sysosidu_deSyvtsi.txt");
		 
	}

	
	/**
	 * 字符串"今天星期四"与"今天是星期四"的比较；差异步骤：1；相似度：0.8333333
字符串"今天星期四"与"今天星期(四)"的比较；差异步骤：2；相似度：0.71428573
字符串"今天星期四"与"今天星期四223"的比较；差异步骤：3；相似度：0.625
字符串"今天星期四"与"今天新期四"的比较；差异步骤：1；相似度：0.8
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static float levenshtein(String str1, String str2) {
		// 计算两个字符串的长度。
		int len1 = str1.length();
		int len2 = str2.length();
		// 建立上面说的数组，比字符长度大一个空间
		int[][] dif = new int[len1 + 1][len2 + 1];
		// 赋初值，步骤B。
		for (int a = 0; a <= len1; a++) {
			dif[a][0] = a;
		}
		for (int a = 0; a <= len2; a++) {
			dif[0][a] = a;
		}
		// 计算两个字符是否一样，计算左上的值
		int temp;
		for (int i = 1; i <= len1; i++) {
			for (int j = 1; j <= len2; j++) {
				if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
					temp = 0;
				} else {
					temp = 1;
				}
				// 取三个值中最小的
				dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,
						dif[i - 1][j] + 1);
			}
		}

		// 计算相似度
		float similarity = 1 - (float) dif[len1][len2]
				/ Math.max(str1.length(), str2.length());

		System.out.println("字符串\"" + str1 + "\"与\"" + str2 + "\"的比较；"
				+ "差异步骤：" + dif[len1][len2] + "；相似度：" + similarity);
		return similarity;
	}

	// 得到最小值
	private static int min(int... is) {
		int min = Integer.MAX_VALUE;
		for (int i : is) {
			if (min > i) {
				min = i;
			}
		}
		return min;
	}
}