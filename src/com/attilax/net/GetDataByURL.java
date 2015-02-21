package com.attilax.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GetDataByURL {

	public String downloadPage(String Url, String encode) {
		try {
			URL pageUrl = new URL(Url);
			// Open connection to URL for reading.
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					pageUrl.openStream(), encode));
			// BufferedReader br = new BufferedReader(new InputStreamReader(in,
			// "gbk"));
			// Read page into buffer.
			String line;
			StringBuffer pageBuffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				pageBuffer.append(line);
			}
			String s = pageBuffer.toString();
			return s;
			// System.out.println(s);
			// return pageBuffer.toString();
		} catch (Exception e) {
			// return null;
			throw new RuntimeException(e);
		}

	}

	// public String getUrlContent(String path){
	// String rtn = "";
	// int c;
	// try{
	// URL l_url = new URL(path);
	// HttpURLConnection l_connection = ( HttpURLConnection)
	// l_url.openConnection();
	// l_connection.setRequestProperty("User-agent","Mozilla/4.0");
	// nnect();
	// InputStream l_urlStream = l_connection.getInputStream();
	// while (( ( c= l_urlStream.read() )!=-1)){
	// int all=l_urlStream.available();
	// byte[] b =new byte[all];
	// l_urlStream.read(b);
	// rtn+= new String(b, "UTF-8");
	// }
	// //Thread.sleep(2000);
	// l_urlStream.close();
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// return rtn;
	// }
	public static String cc(String leibie, String num) {
		StringBuffer temp = new StringBuffer();
		try {
			System.out.println(leibie);
			System.out.println(num);
			String url = "http://www.baidu.com/jiaojing/ser.php";
			HttpURLConnection uc = (HttpURLConnection) new URL(url)
					.openConnection();
			uc.setConnectTimeout(10000);
			uc.setDoOutput(true);
			uc.setRequestMethod("GET");
			uc.setUseCaches(false);
			DataOutputStream out = new DataOutputStream(uc.getOutputStream());

			// 要传的参数
			String s = URLEncoder.encode("ra", "GB2312") + "="
					+ URLEncoder.encode(leibie, "GB2312");
			s += "&" + URLEncoder.encode("keyword", "GB2312") + "="
					+ URLEncoder.encode(num, "GB2312");
			// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
			out.writeBytes(s);
			out.flush();
			out.close();
			InputStream in = new BufferedInputStream(uc.getInputStream());
			Reader rd = new InputStreamReader(in, "Gb2312");
			int c = 0;
			while ((c = rd.read()) != -1) {
				temp.append((char) c);
			}
			System.out.println(temp.toString());
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp.toString();
	}

	public static void main(String[] a) {
		GetDataByURL.cc("1", "吉H");
	}

}