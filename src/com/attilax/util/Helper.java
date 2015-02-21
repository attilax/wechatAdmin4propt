package com.attilax.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Helper {

	private static final SecureRandom  secureRandom = new SecureRandom();
	private static final Pattern emailPattern = Pattern.compile("[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?");;
	public static boolean isEmail(String value){
		if(value==null) return false;
		return emailPattern.matcher(value).matches();
	}
	
	private static final Pattern numPattern = Pattern.compile("\\d+(\\.\\d+)?");
	private static final Pattern intPattern = Pattern.compile("\\d+");
	
	public static boolean isInt(String value){
		if(value!=null && intPattern.matcher(value).matches()){
			return Long.parseLong(value)<=Integer.MAX_VALUE;
		}
		return false;
	}
	
	public static boolean isNum(String value){
		if(value==null) return false;
		return numPattern.matcher(value).matches();
	}
	
	
	public static int wordCounter(String src,boolean skipArabicNumber){
		int count = 0;
		if(src!=null){
			boolean hasEnglish = false;
			char[] array = src.toCharArray();
			for(char c : array){
				if(CharacterHelper.isEnglishLetter(c)){
					hasEnglish = true;
				}else{
					if(hasEnglish){
						hasEnglish = false;
						count++;
					}
				}
				
				if(CharacterHelper.isArabicNumber(c)){
					if(!skipArabicNumber) count++;
					continue;
				}
				if(CharacterHelper.isCJKCharacter(c)){
					count++;
					continue;
				}
			}
			if(hasEnglish) count++;
		}
		return count;
	}
	
	public static String base64encode(String str,String charset) {
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			return encoder.encode(str.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String base64encode(String str) {
		return base64encode(str,"UTF-8");
	}
	
	
	public static String base64decode(String str){
		return base64decode(str,"UTF-8");
	}
	
	public static String base64decode(String str,String charset){
		BASE64Decoder decoder = new BASE64Decoder();
		String result = null;
		try {
			result =  new String(decoder.decodeBuffer(str),charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/*public static String encodeIp(String ip){
		char[] cBuff = ip.toCharArray();
		for(int i=0;i<cBuff.length;i++){
			cBuff[i]=(char)(cBuff[i]+49+i);
		}
		return new String(cBuff);
	}
	
	public static String decodeIp(String ipCip){
		char[] cBuff = ipCip.toCharArray();
		for(int i=0;i<cBuff.length;i++){
			cBuff[i]=(char)(cBuff[i]-49-i);
		}
		return new String(cBuff);
	}*/
	
	public static String fakeAccessTokenTorealAccessToken(String fakeAccessToken){
		char[] buff = fakeAccessToken.toCharArray();
		char[] cBuff = new char[buff.length];
		int max = buff.length;
		int oddMax = buff.length/2;
		if(buff.length%2!=0) oddMax+=1;
		int pluralMax = buff.length-oddMax;
		int index = 0;
		for(int i=0;i<oddMax;i++){
			cBuff[index]=buff[i];
			index+=2;
		}
		index=1;
		for(int i=oddMax;i<buff.length;i++){
			cBuff[index]=buff[i];
			index+=2;
		}
		return new String(cBuff);
	}
	
	public static String fakeBase64AccessTokenTorealAccessToken(String fakeBase64AccessToken){
		char[] buff = fakeBase64AccessToken.toCharArray();
		char[] cBuff = new char[buff.length];
		int max = buff.length;
		int oddMax = buff.length/2;
		if(buff.length%2!=0) oddMax+=1;
		int pluralMax = buff.length-oddMax;
		int index = 0;
		for(int i=0;i<oddMax;i++){
			cBuff[index]=buff[i];
			index+=2;
		}
		index=1;
		for(int i=oddMax;i<buff.length;i++){
			cBuff[index]=buff[i];
			index+=2;
		}
		return  base64decode(new String(cBuff));
	}
	
	public static String realAccessTokenTofakeBase64AccessToken(String realAccessToken){
		realAccessToken = base64encode(realAccessToken);
		char[] buff = realAccessToken.toCharArray();
		char[] tokenCBuff = new char[buff.length];
		
		int index = 0;
		for(int i=0;i<buff.length;i+=2){
			tokenCBuff[index++]=buff[i];
			if(i==buff.length-1) break;
		}
		
		for(int i=1;i<buff.length;i+=2){
			tokenCBuff[index++]=buff[i];
			if(i==buff.length-1) break;
		}
		return new String(tokenCBuff);
	}
	
	public static String genUUID(){
		byte[] randomBytes = new byte[16];
		secureRandom.nextBytes(randomBytes);
        randomBytes[6]  &= 0x0f;  /* clear version        */
        randomBytes[6]  |= 0x40;  /* set to version 4     */
        randomBytes[8]  &= 0x3f;  /* clear variant        */
        randomBytes[8]  |= 0x80;  /* set to IETF variant  */
        
        long mostSigBits = 0;
        long leastSigBits = 0;
        for (int i=0; i<8; i++)
        	mostSigBits = (mostSigBits << 8) | (randomBytes[i] & 0xff);
        for (int i=8; i<16; i++)
        	leastSigBits = (leastSigBits << 8) | (randomBytes[i] & 0xff);
        return (digits(mostSigBits >> 32, 8) +
                digits(mostSigBits >> 16, 4) + 
                digits(mostSigBits, 4) +
                digits(leastSigBits >> 48, 4) + 
                digits(leastSigBits, 12));
	}
	
	private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
	}
	
	public static String genFakeAccessTokenByUUIDAndIp(String ip){
//		ip = encodeIp(ip);
		StringBuilder buff = new StringBuilder(genUUID());
		buff.append(ip);
		char[] tokenCBuff = new char[buff.length()];
		int index = 0;
		int size = buff.length();
		for(int i=0;i<size;i+=2){
			tokenCBuff[index++]=buff.charAt(i);
			if(i==size-1) break;
		}
		
		for(int i=1;i<size;i+=2){
			tokenCBuff[index++]=buff.charAt(i);
			if(i==size-1) break;
		}
		return new String(tokenCBuff);
	}
	
	public static String extractTokenFromAccessToken(String accessToken){
		return accessToken.substring(0,32);
	}
	
	public static String extractIpFromAccessToken(String accessToken){
		return accessToken.substring(32);
	}
	
	public static boolean checkUpgrade(String clientVersion,String serverVersion){
		return versionCompare(clientVersion,serverVersion)==-1;
	}
	
	public static boolean versionLessThan(String version,String targetVersion){
		return versionCompare(version,targetVersion)==-1;
	}
	
	public static boolean versionGreaterThan(String version,String targetVersion){
		return versionCompare(version,targetVersion)==1;
	}
	
	public static boolean versionEqual(String version,String targetVersion){
		return versionCompare(version,targetVersion)==0;
	}
	
	public static boolean versionGreaterThanOrEqual(String version,String targetVersion){
		int value = versionCompare(version,targetVersion);
		return value>=0;
	}
	
	public static boolean versionLessThanOrEqual(String version,String targetVersion){
		int value = versionCompare(version,targetVersion);
		return value<=0;
	}
	
	public static int versionCompare(String versionA,String versionB){
		while(versionA.endsWith(".0")){
			versionA = versionA.substring(0,versionA.lastIndexOf("."));
		}
		
		while(versionB.endsWith(".0")){
			versionB = versionB.substring(0,versionB.lastIndexOf("."));
		}
		
		String[] verA = versionA.split("\\.");
		String[] verB = versionB.split("\\.");
		int min = verA.length<verB.length?verA.length:verB.length;
		int distance = 0;
		int result = 0;
		for(int i=0;i<min;i++){
			distance = Integer.parseInt(verA[i])-Integer.parseInt(verB[i]);
			if(distance>0) return 1;
			else if(distance<0) return -1;
		}
		if(verA.length!=verB.length)
			if(verA.length>verB.length) return 1; else return -1;
		return result;
	}
	
	public static String MD5(String s) {
		  byte[] input=s.getBytes(); 
		  String output = null; 
		  char[] hexChar={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'}; 
		  try{ 
			  MessageDigest md=MessageDigest.getInstance("MD5"); 
			  md.update(input); 
			   
			  byte[] tmp = md.digest();//获得MD5的摘要结果 
			  char[] str = new char[32]; 
			  byte b=0; 
			  for(int i=0;i<16;i++){ 
				  b=tmp[i]; 
				  str[2*i] = hexChar[b>>>4 & 0xf];//取每一个字节的低四位换成16进制字母 
				  str[2*i+1] = hexChar[b & 0xf];//取每一个字节的高四位换成16进制字母 
			  } 
			  output = new String(str); 
		  }catch(NoSuchAlgorithmException e){ 
			  e.printStackTrace(); 
		  } 
		  return output; 
	}
	
	public static Connection getConnection(String url,String user,String password) throws SQLException{
		return DriverManager.getConnection(url,user,password);
	}
	
	public static void main(String[] args){
		System.out.println(Helper.versionGreaterThanOrEqual("1.0.5", "1.0.6"));
	}
	
}
