package com.focusx.util;

import java.io.IOException;
import java.security.MessageDigest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptUtil {
	public static String md5Encrypt(String s) {
		if ("".equals(s) || s == null) {
			return "";
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
      //将s字符串加密,结果为bys字节数组
			byte[] bys = md.digest(s.getBytes());
      //将bys采用Base64算法转换成字符串
			return Base64Encrypt(bys);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String Base64Encrypt(byte[] bys) {
		BASE64Encoder base = new BASE64Encoder();
		String s = base.encode(bys);
		return s;
	}

	public static byte[] Base64Decrypt(String s) throws IOException {
		BASE64Decoder base = new BASE64Decoder();
		byte[] bys = base.decodeBuffer(s);
		return bys;
	}
  
  public static void main(String[] args) throws IOException{
    String s = md5Encrypt("test20050528");
    System.out.println(s);
  }

}
