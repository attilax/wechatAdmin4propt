package com.focusx.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import net.sf.json.JSONObject;

public class MyHttpUtils {
	
	private static Logger logger = Logger.getLogger(MyHttpUtils.class);
	
	public static Integer httpRequest2(String url,String filename){
		Integer result = 0;
		System.setProperty ("jsse.enableSNIExtension", "false");
		try {
			StringBuffer buffer = new StringBuffer();
			SSLContext sslContext = SSLContext.getInstance("SSL");
			X509TrustManager tm = new X509TrustManager() {
	             public void checkClientTrusted(X509Certificate[] xcs,
	                     String string) {
	             }
	             public void checkServerTrusted(X509Certificate[] xcs,
	                     String string) {
	             }
	             public X509Certificate[] getAcceptedIssuers() {
	                 return null;
	             }
	         };
	         
	        sslContext.init(null, new TrustManager[] { tm }, null);
			
	        URL urlObj = new URL(url);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) urlObj.openConnection();
			httpUrlConn.setSSLSocketFactory(sslContext.getSocketFactory());
			 
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestMethod("GET");
			
			httpUrlConn.connect();
		
			InputStream inputStream = httpUrlConn.getInputStream();
			
			File qrFile = new File(Constant.uploadPathCode+"\\"+filename+".jpg");
			
			FileOutputStream fos = new FileOutputStream(qrFile);

			// 设置数据缓冲  
	        byte[] bs = new byte[1024 * 2];
	        // 读取到的数据长度  
	        int len; 
			while((len = inputStream.read(bs)) != -1){
				fos.write(bs, 0, len);
			}
			fos.flush();
			fos.close();
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			String uploadPath = ServletActionContext.getServletContext().getRealPath("/images");
			//result = pressImage(uploadPath+"\\logo.png", Constant.uploadPathCode+"\\"+filename+".jpg");
			result = 0;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logger.error("生成二维码图片出现异常", e);
			result = 1;
		} catch (KeyManagementException e) {
			e.printStackTrace();
			logger.error("生成二维码图片出现异常，文件名称是"+filename, e);
			result = 1;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("生成二维码图片出现异常，文件名称是"+filename, e);
			result = 1;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			logger.error("生成二维码图片出现异常，文件名称是"+filename, e);
			result = 1;
		}
		return result;
	}
	
	//生成水印的方法，加入的logo
	public static Integer pressImage(String pressImg, String targetImg) {
		int result = 0;
		try{
		    // 初始化被写水印的图片
		    File _file = new File(targetImg);
		    Image src = ImageIO.read(_file);
		    int width = src.getWidth(null);
		    int height = src.getHeight(null);
		    // 创建图片缓冲区
		    BufferedImage image = new BufferedImage(width, height,
		      BufferedImage.TYPE_INT_RGB);
		    Graphics2D g = image.createGraphics();
		    g.drawImage(src, 0, 0, width, height, null);

		    String uploadPath = ServletActionContext.getServletContext().getRealPath("/images");
		    File _filewhite = new File(uploadPath+"\\white.jpg");
		    Image src_white = ImageIO.read(_filewhite);
		    int width_white = src_white.getWidth(null);
		    int height_white = src_white.getHeight(null);
		    g.drawImage(src_white, (width - width_white) / 2,(height - height_white) / 2, width_white, height_white, null);
		    
		    // 初始化水印图片
		    File _filebiao = new File(pressImg);
		    Image src_biao = ImageIO.read(_filebiao);
		    int width_biao = src_biao.getWidth(null);
		    int height_biao = src_biao.getHeight(null);
		    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,1));
		    // 将水印图片设置在目标图片的中间
		    g.drawImage(src_biao, (width - width_biao) / 2,(height - height_biao) / 2, width_biao, height_biao, null);
		    // 水印操作结束
		    g.dispose();
		    // 将缓冲区中的信息写入图片
		    FileOutputStream out = new FileOutputStream(targetImg);
		    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		    encoder.encode(image);
		    out.close();
		}catch (Exception e) {
			e.printStackTrace();
			result = 1;
			logger.error("二维码图片加logos失败！");
		}
		return result;
    }
	
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		HttpsURLConnection httpUrlConn = null;
		BufferedReader bufferedReader = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			/*
			 * if ("GET".equalsIgnoreCase(requestMethod)){
			 * httpUrlConn.connect(); }
			 */
			httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			inputStream = httpUrlConn.getInputStream();
			
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			logger.error("Weixin server connection timed out.");
		} catch (Exception e) {
			logger.error("https request error:{}", e);
		}finally{
			
			try{
				if(httpUrlConn != null){
					httpUrlConn.disconnect();
				}
				if(inputStreamReader != null){
					inputStreamReader.close();
				}
				if(bufferedReader != null){
					bufferedReader.close();
				}
				if(inputStream != null){
					inputStream.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return jsonObject;
	}

}
