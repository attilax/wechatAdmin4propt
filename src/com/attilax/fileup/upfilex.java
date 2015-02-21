/**
 * @author attilax 老哇的爪子
	@since  2014-5-16 上午11:40:51$
 */
package com.attilax.fileup;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.json.simple.JSONObject;

import com.attilax.core;
import com.attilax.collection.listUtil;
import com.attilax.util.Func_4SingleObj;
import com.attilax.util.tryX;

import antlr.collections.List;

/**
 * @author attilax 老哇的爪子
 *@since 2014-5-16 上午11:40:51$
 */
public class upfilex {
	// attilax 老哇的爪子 上午11:40:51 2014-5-16

	@SuppressWarnings("unchecked")
	public static void save(PageContext pageContext, HttpServletRequest request,
			HttpServletResponse response, JspWriter out)   {
		/**
		 * KindEditor JSP
		 * 
		 * 本JSP程序是演示程序，建议不要直接在实际项目中使用。 如果您确定直接使用本程序，使用之前请仔细确认相关安全设置。
		 * 
		 */
		try{

		// 文件保存目录路径
		String savePath = pageContext.getServletContext().getRealPath("/")
				+ "attached/";

		// 文件保存目录URL
		String saveUrl = request.getContextPath() + "/attached/";

		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		// 最大文件大小
		long maxSize = 1000000;

		response.setContentType("text/html; charset=UTF-8");

		if (!ServletFileUpload.isMultipartContent(request)) {
			out.println(getError("请选择文件。"));
			return;
		}
		// 检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {
			out.println(getError("上传目录不存在。"));
			return;
		}
		 
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			out.println(getError("上传目录没有写权限。"));
			return;
		}

		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
			out.println(getError("目录名不正确。"));
			return;
		}
		// 创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		FileItemFactory factory = new DiskFileItemFactory();
		// factory = new DiskFileItemFactory(Constant.SIZE_THRESHOLD,new
		// File(tempDirectory));
		// 设置文件的缓存路径
		out.println("---o1");
		// factory.setRepository(new File("c:/tmp/"));
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(500 * 1024 * 1024);// 设置该次上传最大值为500M
		upload.setHeaderEncoding("UTF-8");
		// upload.parseRequest
		java.util.List items = upload.parseRequest(request);
		
		//ati
		if(items.size()==0)
			items=getUpfiles(request);
		/////end	
	Iterator itr = items.iterator();
		
		
		
	
	 

		
		
		out.println("---");
		out.println(itr.hasNext());

		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String fileName = item.getName();
			long fileSize = item.getSize();
			if (!item.isFormField()) {
				// 检查文件大小
//				if (item.getSize() > maxSize) {
//					out.println(getError("上传文件大小超过限制。"));
//					return;
//				}
				// 检查扩展名
 				String fileExt = fileName.substring(
 						fileName.lastIndexOf(".") + 1).toLowerCase();
//				if (!Arrays.<String> asList(extMap.get(dirName).split(","))
//						.contains(fileExt)) {
//					out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许"
//							+ extMap.get(dirName) + "格式。"));
//					return;
//				}

				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Date()) + "_"
						+ new Random().nextInt(1000) + "." + fileExt;
				try {
					File uploadedFile = new File(savePath, newFileName);
					item.write(uploadedFile);
				} catch (Exception e) {
					out.println(getError("上传文件失败。"));
					return;
				}

				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				obj.put("url", saveUrl + newFileName);
				out.println(obj.toJSONString());
			}
		}
		}catch (Exception e) {
			 
			core.log(e);
			throw new RuntimeException(e);
		}
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-16 下午03:18:54$
	
	 * @param request
	 * @return
	 */
	private static java.util.List getUpfiles(HttpServletRequest request) {
		// attilax 老哇的爪子  下午03:18:54   2014-5-16 
		//todox  iterators  o5h
		 MultiPartRequestWrapper rw=(MultiPartRequestWrapper) request;
		File[] fs= rw.getFiles("imgFile");
		java.util.List<FileItem> lix=listUtil.map_generic(fs, new Func_4SingleObj<File, FileItem>(){

			@Override
			public FileItem invoke(File o) {
				// attilax 老哇的爪子  下午02:38:25   2014-5-16 
				FileItemImp fii=	new FileItemImp();
				fii.setFormField(false);
				fii.name=o.getName();
				fii.Path=o.getPath();
				return fii;
			}
			
		});
		return lix;
	}

	private  static String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}
}

// attilax 老哇的爪子