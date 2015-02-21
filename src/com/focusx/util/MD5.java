package com.focusx.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	 // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    
    public MD5() {
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 返回形式只为数字
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
    
	public static String getMD5(String str) {
		try {
			return bytestoHex(MessageDigest("MD5", str.getBytes()));
		} catch (InvalidAlgorithmParameterException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}


	private static String bytestoHex(byte input[]) {
		String hs = "";
		String stmp = "";
		for (int i = 0; i < input.length; i++) {
			stmp = Integer.toHexString(input[i] & 255);
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (i < input.length - 1)
				hs = hs + "";
		}

		return hs.toUpperCase();
	}

	public static byte[] hextoBytes(String sHexString) {
		byte output[] = new byte[sHexString.length() / 2];
		int j = 0;
		for (int i = 0; i < sHexString.length(); i += 2) {
			output[j] = (byte) (Byte.parseByte(sHexString.substring(i, i + 1),
					16) << 4);
			output[j] = (byte) (output[j] | Byte.parseByte(sHexString
					.substring(i + 1, i + 2), 16));
			j++;
		}
		return output;
	}

	private static byte[] MessageDigest(String algorithm, byte input[])
			throws InvalidAlgorithmParameterException, Exception {
		if (algorithm == null || algorithm.length() == 0)
			algorithm = "MD5";
		MessageDigest alg = java.security.MessageDigest.getInstance(algorithm);
		alg.update(input);
		return alg.digest();
	}
    

    public static void main(String[] args) {
        MD5 getMD5 = new MD5();
        System.out.println(getMD5.GetMD5Code("中国"));
    }
}
