package com.attilax.util;

import java.io.IOException;

public class imgUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		  byte[] b=	imgUtil.toByteArr("d:\\t.jpg");
		  System.out.println(b.length);

	}

	public static byte[] toByteArr(String string) {
		 byte[] b = null;
		try {
			b = FileUtils.toByteArray(string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

}
