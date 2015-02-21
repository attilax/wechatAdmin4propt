package com.focusx.action.materialManager;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.attilax.SafeVal;
import com.focusx.entity.TMbGroup;
import com.focusx.entity.TMbNews;
import com.focusx.entity.TMbWeixinUser;
import com.focusx.entity.TUserUsers;
import com.focusx.entity.weixin.sendMessage.Article;
import com.focusx.entity.weixin.sendMessage.News;
import com.focusx.entity.weixin.sendMessage.NewsMessage;
import com.focusx.service.BranchManagerService;
import com.focusx.service.MaterialManagerService;
import com.focusx.service.WeixinUserManagerService;
import com.focusx.util.Constant;
import com.focusx.util.JsonUtil;
import com.focusx.util.MD5;
import com.focusx.util.MessageUtil;
import com.focusx.util.MyCacher;
import com.focusx.util.OperLogUtil4gialen;
import com.focusx.util.Page;
import com.focusx.util.PageUtil;
import com.focusx.util.WeixinUtil;
import com.opensymphony.xwork2.ActionSupport;

public class MaterialManagerAction extends ActionSupport{
	
	protected static Logger logger = Logger.getLogger("MaterialManagerAction");
	private MaterialManagerService materialManagerService;
	private BranchManagerService branchManagerService;
	private WeixinUserManagerService weixinUserManagerService;
	private List<TMbNews> newslist;//图文对象数组
	private TMbNews news;//图文对象数组	
	private List<TMbGroup> groups;//分公司对象数组
    private Integer newsId;//主键ID
	private String title;//标题
	private String description;//摘要
    private String author;//作者
    private String coverPage;//封面
    private String mainText;//正文信息，存放HTML代码
    private String htmlName;//HTML文件名
    private Integer groupid;//分公司ID
    private Integer showimg;//封面图片是否显示在正文中，1 是 ,0 否
    private Integer newsType;//图文类型
    
    private String groupname;//分公司名称
    
	private Page page;// 分页对象
	private String p;
	private String ps;
	private String startTime;//开始时间
	private String endTime;//结束时间
	
	private File imgFile;//上传的文件
	 
	// excelFileContentType属性用来封装上传文件的类型  
	private String imgFileContentType;  
	  
	// excelFileFileName属性用来封装上传文件的文件名  
	private String imgFileFileName;  
	
	private String materials;//新建多图文的json格式字符串
	
	private String editMoreMaterial;//编辑多图文的json格式字符串
	
	private String deleteIdList;//要删除的图文对象Id
	
	private String keyword;//关键字
	private Integer activityId;//活动ID
	private String nickname;//昵称
	private Integer userId;//粉丝主键ID
	private List<TMbWeixinUser> weixinusers;//粉丝数组
	private Page<TMbWeixinUser> pageWeixin;// 分页对象
	private String html;
	
	/**
	 *  获取图文信息传给列表页
	 */
	public String listMaterial(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
		if(user.getIsSystem() != null && user.getIsSystem() == Constant.ONE){//管理员可以查询所有图文信息
			groupid = null;
		}else {
			if(user.getDefaultGroup() == null || user.getDefaultGroup() < 0){//判断当前用户是否有分配分公司
				page = new Page<TMbNews>(PageUtil.PAGE_SIZE);
				page.setResult(null);
				page.setTotalCount(0);
				return "listMaterial";
			}else {//其他分公司
				groupid = user.getDefaultGroup();
			}
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> data = new HashMap<String, Object>();
		
		page = new Page<TMbNews>(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(page, request);
		int pageNumber = pageParams[0];// 第几页
		int pageSize = pageParams[1];// 每页记录数
		// 分页条件
		data.put("pageNumber", pageNumber);
		data.put("pageSize", pageSize);
		
		// 查询条件
		data.put("startTime", startTime);
		data.put("endTime", endTime);
		data.put("title", StringUtils.trim(title));
		data.put("description", StringUtils.trim(description));
		data.put("groupid", groupid);
		data.put("newsType", newsType);
		try {
			newslist = materialManagerService.getMaterials(page, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	//	OperLogUtil4gialen.log(   "查询图文信息:"+SafeVal.val( title), ServletActionContext.getRequest());	
		OperLogUtil4gialen.log(   "查询图文信息:" , ServletActionContext.getRequest());	
		return "listMaterial";
	}
	
	/**
	 *  跳转到编辑界面
	 */
	public String edit(){
		try {
			if(newsId != null && newsId > 0){
				news = materialManagerService.getTMbNewsById(newsId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("跳转到编辑界面出现异常：MaterialManagerAction.edit()", e);
		}
		return "edit";
	}

	/**
	 *  保存编辑的图文信息
	 *  处理逻辑：先根据新数据创建一个临时html文件，再把旧的删除，临时文件名称改为旧的名称，成功后把新数据保存到数据库
	 */
	public void editMaterial(){
		HttpServletResponse response = ServletActionContext.getResponse();
		 OutputStreamWriter osw = null;
	     BufferedWriter out = null;;
		try {
			if(newsId != null && newsId > 0){
				news = materialManagerService.getTMbNewsById(newsId);
		        String uploadPathHtml = Constant.uploadPathHtml;
		        String uploadPathImage = Constant.uploadPathImage;
		        String imgFileName = coverPage.substring(coverPage.lastIndexOf("/")+1, coverPage.length());
		        
				Image imgSrc = javax.imageio.ImageIO.read(new File(uploadPathImage + "/" + imgFileName));
				int width = imgSrc.getWidth(null); 
				int height = imgSrc.getHeight(null); 
				
				logger.info("editMaterial===>" + coverPage);
				String htmlString = createHtml(title,mainText,coverPage,showimg,width,height);
				
		        String cont = htmlString;
		        String htmlName = "temporaryFile.html";//临时文件名
			       
		        try{
		        	if(formatUrl(uploadPathHtml)){
		        		osw = new OutputStreamWriter(new FileOutputStream(uploadPathHtml+"\\"+htmlName, true),"UTF-8");
		        		out = new BufferedWriter(osw);
		        		out.write(cont,0,cont.length());
		        		out.close();
		        		osw.close();
		        		logger.info("生成临时html文件成功！");
		        	}
		       }catch(IOException e){
		    	  e.printStackTrace();
		    	  logger.error("生成临时html文件出现异常！", e);
		       }
		       boolean result = false;
		       File newFile = new File(uploadPathHtml+"\\"+htmlName);
		       if(newFile.exists()){
			       File oldFile = new File(uploadPathHtml, news.getHtmlName());
			       boolean temp = false;
			       if(oldFile.exists()){
			    	   oldFile.delete();
			    	   temp = true;
			    	   logger.info("旧的html文件删除成功！");
			       }
			       if(temp == true){ //判断是否把旧的html文件删除
			    	   File tempFile = new File(uploadPathHtml, news.getHtmlName());
			    	   newFile.renameTo(tempFile); //修改文件名称，换回原本名称
			    	   result = true;
			    	   logger.info("修改临时html文件名称成功！");
			       }
		       }
		       if(result == true){//判断是否创建临时html成功、删除旧html文件和修改名称都成功
					news.setAuthor(author);
					news.setCoverPage(imgFileName);
					news.setDescription(description);
					news.setMainText(mainText);
					news.setTitle(title);
					news.setShowimg(showimg);
					newsId = materialManagerService.update(news);
					//更新缓存
					if(news.getNewsType() > 0 && news.getNewsType() != Constant.NEWS_KEYWORD){
						cacher(news.getGroupid(), Constant.ZERO, Constant.ZERO);
					}
					JsonUtil.write(response, "success");
					logger.info("数据库更新成功！");
					
					OperLogUtil4gialen.log(   "更新图文信息:"+SafeVal.val( title), ServletActionContext.getRequest());	
		       }else {
		    	   JsonUtil.write(response, "false");
		    	   logger.info("更新图文信息失败！");
		       }
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JsonUtil.write(response, "false");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			logger.error("更新图文信息出现异常：MaterialManagerAction.editMaterial()", e);
		}finally{
			try{
			if(out != null){
				out.close();
			}
			if(osw != null){
				osw.close();
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *  如果存放当前日期文件的目录结构不存在，则创建对应的目录结构 
	 *  @param path
	 */
	private boolean formatUrl(String path){ 
		boolean bool = false; 
		File file = new File(path);
		if(!file.exists()){ 
			file.mkdirs(); 
		} 
		if(file.isDirectory()){ 
			bool=true;
		}
		return bool; 
	}
	
	/**
	 *  根据信息创建html内容
	 *  @param title 标题
	 *  @param htmlText 正文内容
	 *  @param imgurl 封面图片完整路径
	 *  @param width 封面图片宽度
	 *  @param height 封面图片高度
	 *  @param showimg 是否显示封面图片
	 *  @return 生成的html内容字符串
	 */
	public static String createHtml(String title, String htmlText, String imgurl, int showimg, int width, int height){
        StringBuilder htmltxt = new StringBuilder(); 
        htmltxt.append("<!DOCTYPE html>"+"\r\n");
        htmltxt.append("<html>"+"\r\n");
        htmltxt.append("<head>"+"\r\n");
        htmltxt.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>"+"\r\n");
        htmltxt.append("<title>"+title+"</title>"+"\r\n");
        htmltxt.append("<meta http-equiv='X-UA-Compatible' content='IE=edge'>"+"\r\n");
        htmltxt.append("<meta name='viewport' content='width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.5'>"+"\r\n");
        htmltxt.append("<meta name='apple-mobile-web-app-capable' content='yes'>"+"\r\n");
        htmltxt.append("<meta name='apple-mobile-web-app-status-bar-style' content='black'>"+"\r\n");
        htmltxt.append("<link rel='stylesheet' type='text/css' href='http://211.147.235.195/Gialen/css/client-page1baa9e.css'/>"+"\r\n");
        htmltxt.append("<!--[if lt IE 9]>"+"\r\n");
        htmltxt.append("<link rel='stylesheet' type='text/css' href='http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/style/pc-page1b2f8d.css'/>"+"\r\n");
        htmltxt.append("<![endif]-->"+"\r\n");
        htmltxt.append("<link media='screen and (min-width:1000px)' rel='stylesheet' type='text/css' href='http://211.147.235.195/Gialen/css/pc-page1b2f8d.css'>"+"\r\n");
        htmltxt.append("<style>"+"\r\n");
        htmltxt.append("#nickname{overflow:hidden;white-space:nowrap;text-overflow:ellipsis;max-width:90%;}"+"\r\n");
        htmltxt.append("ol,ul{list-style-position:inside;}"+"\r\n");
        htmltxt.append("#activity-detail .page-content .text{font-size:16px;}"+"\r\n");
        htmltxt.append("</style>"+"\r\n");
        htmltxt.append("</head>"+"\r\n");
        htmltxt.append("<body id='activity-detail'>"+"\r\n");
        htmltxt.append("<div class='page-bizinfo'>"+"\r\n");
        htmltxt.append("<div class='header'>"+"\r\n");
        htmltxt.append("<h1 id='activity-name'>"+title+"</h1>"+"\r\n");
        htmltxt.append("<p class='activity-info'>"+"\r\n");
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(new Date());
        
        htmltxt.append("<span id='post-date' class='activity-meta no-extra'>"+dateString+"</span>"+"\r\n");
        htmltxt.append("<a href='#' id='post-user' class='activity-meta'><span class='text-ellipsis'>GIALEN</span><i class='icon_link_arrow'></i></a></p></div></div>"+"\r\n");
        htmltxt.append("<div id='page-content' class='page-content'>"+"\r\n");
        htmltxt.append("<div id='img-content'>"+"\r\n");
        htmltxt.append("<div class='text'>"+"\r\n");
        if(showimg == Constant.ONE){//判断封面图片是否显示在正文里，Constant.ONE 显示 ，Constant.ZERO 不显示
        	htmltxt.append("<div class='media' id='media'>"+"\r\n");
	        htmltxt.append("<img src="+imgurl+" style='width:"+width+"px;height:"+height+"px;'>"+"\r\n");
	        htmltxt.append("</div>"+"\r\n");
	        
	        logger.info("======>"+imgurl);
	        
        }
        htmltxt.append(htmlText);
        htmltxt.append("</div>"+"\r\n");
        htmltxt.append("</div>"+"\r\n");
        htmltxt.append("</div>"+"\r\n");
        htmltxt.append("</body>"+"\r\n");
        htmltxt.append("</html>");
        return htmltxt.toString();
	}
	
	//删除上传的图片
	public void deleteCoverPage(){
		if(coverPage != null && !StringUtils.trim(coverPage).equals("")){
			coverPage = StringUtils.trim(coverPage);
	        String uploadPathimg = Constant.uploadPathImage;
	        String filename = coverPage.substring(coverPage.lastIndexOf("/")+1, coverPage.length());
	        File file = new File(uploadPathimg+"/"+filename);
	        if(file.exists()){
	        	try {
		        	file.delete();
		        	logger.info("删除图片成功！图片名称是"+filename);
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("删除图片失败！图片名称是"+filename, e);
				}
	        }
		}
	}
	
	/**
	 *  跳转到新建图文信息界面
	 */
	public String addMaterial(){
		return "addMaterial";
	}
	
	/**
	 *  保存新的图文信息
	 */
	public void saveMaterial(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			
			if(user.getDefaultGroup() == null || user.getDefaultGroup() < 0)
			{//ati o63
				user.setDefaultGroup(0);
			}
			if(user.getDefaultGroup() == null || user.getDefaultGroup() < 0){
				//ati o61 yaos bdwei zi ret g str "nopower" ,then js redrkt to warngin.jap
				JsonUtil.write(response, "nopower");
			}else{
				groupid = user.getDefaultGroup();
				
				//获取数据，生成html页面，并保存到指定地方
		        String uuid = UUID.randomUUID().toString();//创建一个唯一的字符串当做html名称
		        htmlName = uuid+".html";
		        String uploadPathHtml = Constant.uploadPathHtml;
		        String uploadPathImage = Constant.uploadPathImage;
		        String filename = coverPage.substring(coverPage.lastIndexOf("/")+1, coverPage.length());
				
				Image imgSrc = javax.imageio.ImageIO.read(new File(uploadPathImage+"/"+filename));
				int width = imgSrc.getWidth(null); 
				int height = imgSrc.getHeight(null); 
				logger.info("saveMaterial===>"+coverPage);
				String htmlString = createHtml(title,mainText,coverPage,showimg,width,height);

		        String cont = htmlString;
		        String fileName=htmlName;
		        OutputStreamWriter osw;
		        BufferedWriter out;
		        try{
		        	if(formatUrl(uploadPathHtml)){
		        		osw = new OutputStreamWriter(new FileOutputStream(uploadPathHtml+"\\"+fileName, true),"UTF-8");
		        		out = new BufferedWriter(osw);
		        		out.write(cont,0,cont.length());
		        		out.close();
		        		osw.close();
		        	}
		       }catch(IOException ioe){
		    	  ioe.printStackTrace();
		      }
		    
			  //把数据插入到数据库
			  TMbNews news = new TMbNews();
			  news.setAuthor(author);
			  news.setCoverPage(filename);
			  news.setDescription(description);
			  news.setFlag(Constant.ONE);
			  news.setGroupid(groupid);
			  news.setHtmlName(htmlName);
			  news.setMainText(mainText);
			  news.setNewsType(Constant.ZERO);//默认为0，不属于任何类型
			  news.setTitle(title);
		      news.setCreateTime(new Date());
			  news.setShowimg(showimg);
			  news.setRank(Constant.ZERO);
			  news.setParentId(Constant.NEGATIVE);
			  newsId = materialManagerService.saveMaterial(news);
			
			  JsonUtil.write(response, "success");
			  logger.info("创建图文信息成功！图文标题是"+title);
			  
			   	 OperLogUtil4gialen.log(   "创建图文成功:"+SafeVal.val( title), ServletActionContext.getRequest());	
				    
			}
	  }catch (IOException e) {
		  e.printStackTrace();
		  try {
			JsonUtil.write(response, "error");
			logger.error("创建图文信息出现异常:MaterialManagerAction.saveMaterial()", e);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	  }
	}
	
	/** todox ati
	 *  上传图片
	 */
	public void uploadimg(){
		logger.info("上传图片开始，图片原名称是" + this.getImgFileFileName());
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("caChe-Control", "no-cache");
		Map<String, Object> map = new HashMap<String, Object>();
        //创建唯一的文件名称
        String uuid = UUID.randomUUID().toString();  
        String name = this.getImgFileFileName();
        String lastname = uuid+"."+name.substring(name.lastIndexOf(".")+1).toString();
        logger.info("图片修改名称后是"+lastname);
		try {
			PrintWriter writer = response.getWriter();
			String uploadPathImage = Constant.uploadPathImage;
			formatUrl(uploadPathImage);
	        String dstPath = uploadPathImage+"/"+lastname; 
			if (createImage(imgFile, dstPath)){ 
				File lastfile = new File(dstPath);
				
				//获取压缩后或不压缩后的宽和高，提供给页面缩小图展示（规则：宽不超过100，不变化，否则宽为100，高根据比较变化）
				Map<String, Integer> result = getWidthAndHeight(lastfile, 100);
				int width = result.get("width");
				int height = result.get("height");
				
				map.put("result", "success");
				map.put("filename", lastname);
				map.put("width", width);
				map.put("height", height);
				JSONObject jsonobj = JSONObject.fromObject(map);
				writer.print(jsonobj.toString());
				logger.info("图片上传成功！图片名称是" + lastname);
			}else{
				map.put("result", "false");
				JSONObject jsonobj = JSONObject.fromObject(map);
				writer.print(jsonobj.toString());
				logger.info("图片上传失败！图片原名称是" + this.getImgFileFileName());
			}				
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("图片上传失败！图片原名称是" + this.getImgFileFileName(),e);
			try {
				PrintWriter writer = response.getWriter();
				map.put("result", "false");
				JSONObject jsonobj = JSONObject.fromObject(map);
				writer.print(jsonobj.toString());
				writer.flush();
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 *  根据maxWidth获取图片收缩的宽和高
	 *  @param file 目标图片
	 *  @param maxWidth 最大宽长度
	 *  @return Map<Integer, Integer> 变化后的宽和高
	 */
	public static Map<String, Integer> getWidthAndHeight(File file, int maxWidth){
		Map<String, Integer> result = new HashMap<String, Integer>();
		try {
			Image imgSrc = javax.imageio.ImageIO.read(file);
			int widthtemp = imgSrc.getWidth(null); 
			int heighttemp = imgSrc.getHeight(null); 
			int width = 0;
			int height = 0;
			if(widthtemp > maxWidth){//如果图片宽超过额定数（maxWidth），宽变成maxWidth，高根据比例变化
				width = maxWidth;
				BigDecimal b1 = new BigDecimal(Double.toString(widthtemp));
				BigDecimal b2 = new BigDecimal(Double.toString(maxWidth));
				double scale = b1.divide(b2, 10, BigDecimal.ROUND_HALF_UP).doubleValue();
				height = (int) (heighttemp/scale);
			}else {
				width = widthtemp;
				height = heighttemp;
			}
			result.put("width", width);
			result.put("height", height);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("根据maxWidth获取图片收缩的宽和高失败！maxWidth是"+maxWidth, e);
		}
		return result;
	} 

	/**
	 *  生成图片文件的方法
	 *  @param fileSrc 源文件
	 *  @param pathDest 生成图片的路径 
	 *  @return true/fale 成功或失败
	 */
	public static boolean createImage(File fileSrc, String pathDest) { 
		boolean flag = false; 
		File out = null; 
		try { 
			//获取压缩后或不压缩后的宽和高
			Map<String, Integer> result = getWidthAndHeight(fileSrc, 640);
			int width = result.get("width");
			int height = result.get("height");
			
			Image imgSrc = javax.imageio.ImageIO.read(fileSrc);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
			tag.getGraphics().drawImage(imgSrc, 0, 0, width, height, null); 
			out = new File(pathDest); 
			flag = writeImageFile(out, tag, 80); 
		} catch (IOException e) { 
			flag = false; 
			e.printStackTrace(); 
			logger.error("生成图片过程中失败：MaterialManagerAction.createImage()", e);
		} finally { 
			try { 
				if (out != null) {
					out = null; 
				}
			} catch (Exception e1) {
				logger.error("设置file对象为空出现异常：MaterialManagerAction.createImage()", e1);
			} 
		}
		return flag; 
	} 
	
	/** 
	 *  将 BufferedImage 编码输出成硬盘上的图像文件 
	 *  @param file  编码输出的目标图像文件，文件名的后缀确定编码格式。 
	 *  @param image  待编码的图像对象 
	 *  @param quality  编码压缩的百分比 
	 *  @return 返回编码输出成功与否 
	*/ 
	private static boolean writeImageFile(File fileDest, BufferedImage imageSrc, int quality) { 
		try { 
			Iterator<ImageWriter> it = ImageIO.getImageWritersBySuffix("jpg"); 
			if (it.hasNext()) { 
				FileImageOutputStream fileImageOutputStream = new FileImageOutputStream(fileDest); 
				ImageWriter iw = (ImageWriter) it.next(); 
				ImageWriteParam iwp = iw.getDefaultWriteParam(); 
				iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); 
				iwp.setCompressionQuality(quality / 100.0f); 
				iw.setOutput(fileImageOutputStream); 
				iw.write(null, new javax.imageio.IIOImage(imageSrc, null, null), iwp); 
				iw.dispose(); 
				fileImageOutputStream.flush(); 
				fileImageOutputStream.close(); 
			} 
	    } catch (Exception e) {
	    	e.printStackTrace(); 
	    	logger.error("将 BufferedImage 编码输出成硬盘上的图像文件失败：MaterialManagerAction.writeImageFile()", e);
	    	return false; 
	    } 
		return true; 
	} 

	/**
	 *  删除图文信息，非物理删除,改变状态
	 */
	public void deleteMaterial(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			materialManagerService.deleteMaterialByState(newsId);
			JsonUtil.write(response, "success");
			//更新缓存
			TMbNews news = materialManagerService.getTMbNewsById(newsId);
			if(news != null && news.getNewsType() > 0 && news.getNewsType() != Constant.NEWS_KEYWORD){
				cacher(news.getGroupid(), news.getNewsType(), Constant.ONE);
			}
			OperLogUtil4gialen.log(   "删除图文信息:"+SafeVal.val( newsId), ServletActionContext.getRequest());	
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("删除图文失败！图文ID是"+newsId);
			try {
				JsonUtil.write(response, "error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//新建多图文跳转
	public String addMoreMaterial(){
		return "addMoreMaterial";
	}
	
	//多图文生成
	public void moreMaterial(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getDefaultGroup() == null || user.getDefaultGroup() < 0){
				JsonUtil.write(response, "nopower");
			}else{
				groupid = user.getDefaultGroup();
				
		        if(materials != null && !StringUtils.trim(materials).equals("")){
		        	JSONArray json = JSONArray.fromObject(materials);
		        	List<TMbNews> materialList = (List<TMbNews>) JSONArray.toCollection(json, TMbNews.class);
		        	int newsParentId = 0;
		        	for(int i=0; i < materialList.size(); i++){
				        TMbNews news_more = materialList.get(i);
		        		
				        String title_more = news_more.getTitle();
				        String coverPage_more = news_more.getCoverPage();
				        Integer showimg_more =  news_more.getShowimg();
				        String mainText_more = news_more.getMainText();
				        
						//获取数据，生成html页面，并保存到指定地方
				        String uuid = UUID.randomUUID().toString();//创建一个唯一的字符串当做html名称
				        String htmlName = uuid+".html";
				        String uploadPathHtml = Constant.uploadPathHtml;
				        String uploadPathImage = Constant.uploadPathImage;
				        String filename = coverPage_more.substring(coverPage_more.lastIndexOf("/")+1, coverPage_more.length());
						
						Image imgSrc = javax.imageio.ImageIO.read(new File(uploadPathImage+"/"+filename));
						int width = imgSrc.getWidth(null); 
						int height = imgSrc.getHeight(null); 
						
						 logger.info("多图文======>"+coverPage_more);
						String htmlString = createHtml(title_more,mainText_more,coverPage_more,showimg_more,width,height);

				        String cont = htmlString;
				        String fileName = htmlName;
				        OutputStreamWriter osw;
				        BufferedWriter out;
				        try{
				        	if(formatUrl(uploadPathHtml)){
				        		osw = new OutputStreamWriter(new FileOutputStream(uploadPathHtml+"\\"+fileName, true),"UTF-8");
				        		out = new BufferedWriter(osw);
				        		out.write(cont,0,cont.length());
				        		out.close();
				        		osw.close();
				        	}
				       }catch(IOException ioe){
				    	  ioe.printStackTrace();
				    	  JsonUtil.write(response, "error");
				    	  logger.error("创建多图文的第"+i+"个图文信息失败！图文标题是"+news_more.getTitle());
				      }
				      					      
				      news_more.setDescription("");
					  news_more.setCoverPage(filename);
					  news_more.setFlag(Constant.ONE);
					  news_more.setGroupid(groupid);
					  news_more.setHtmlName(htmlName);
					  news_more.setNewsType(Constant.ZERO);
					  news_more.setCreateTime(new Date());
				      
					  //把数据插入到数据库
					  if(i == 0){
						  news_more.setRank(Constant.ZERO);
						  news_more.setParentId(Constant.ZERO);
						  
						  newsParentId = materialManagerService.saveMaterial(news_more);
					  }else {
						  news_more.setRank(i);
						  news_more.setParentId(newsParentId);
						  
						  materialManagerService.saveMaterial(news_more);
					  }

					  JsonUtil.write(response, "success");
					  logger.info("创建多图文的第"+i+"个图文信息成功！图文标题是"+news_more.getTitle());
			        }
		        }
		   //     OperLogUtil4gialen.log(   "创建多图文信息:"+SafeVal.val( title), ServletActionContext.getRequest());	
		        OperLogUtil4gialen.log(   "创建多图文信息:", ServletActionContext.getRequest());	
		        JsonUtil.write(response, "null");
			}
	     }catch (IOException e) {
	    	 e.printStackTrace();
	         try {
	        	 JsonUtil.write(response, "error");
	             logger.error("创建多图文信息出现异常:MaterialManagerAction.moreMaterial()", e);
			 } catch (IOException e1) {
				 e1.printStackTrace();
			 }
		}
	}
	
	//编辑多图文跳转，获取数据后转为json格式字符串
	public String editMore(){
		try {
			if(newsId != null && newsId != 0){
				List<TMbNews> list = materialManagerService.getMoreMaterial(newsId);
				if(list != null && list.size() > 0){
					List<TMbNews> newsList = new ArrayList<TMbNews>();
					for(int i = 0; i < list.size(); i++){
						if(i == 0){
							html = list.get(i).getMainText();
						}
						newsList.add(list.get(i));
					}
					JSONArray json = JSONArray.fromObject(newsList);
					editMoreMaterial = json.toString();
				}else {
					
				}
			}else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "editMore";
	}
	
	//保存编辑的图文，三种情况：1、删除不用的；2、修改使用的；3、新增的
	public void savemoreMaterial(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getDefaultGroup() == null || user.getDefaultGroup() < 0){
				JsonUtil.write(response, "nopower");
			}else{
				groupid = user.getDefaultGroup();

		        if(materials != null && !StringUtils.trim(materials).equals("")){
		        	JSONArray json = JSONArray.fromObject(materials);
		        	List<TMbNews> materialList = (List<TMbNews>) JSONArray.toCollection(json, TMbNews.class);
		        	if(materialList != null && materialList.size() >= 2){
		        		//第一种情况
						if(deleteIdList != null && !StringUtils.trim(deleteIdList).equals("")){
							String[] ids = deleteIdList.split(",");
							for(int i = 0; i < ids.length; i++){
								
								Integer id = Integer.parseInt(StringUtils.trim(ids[i]));
						        //更改图文状态：删除
						        materialManagerService.deleteMaterialByState(id);
							}
						}
		        		
		        		//第一个图文
			        	TMbNews news_one = materialList.get(0);
			        	news_one = materialManagerService.getTMbNewsById(news_one.getId());
			        	int newType_parent = 0;
			        	if(news_one != null && news_one.getNewsType() != Constant.ZERO){
			        		newType_parent = news_one.getNewsType();
			        	}
			        	for(int i = 0; i < materialList.size(); i++){
			        		TMbNews news_more = materialList.get(i);
							//第二种情况
			        		if(news_more.getId() != 0){
			        			
			        			//修改存放正文的html文件
			        			String title_more = news_more.getTitle();
			    				String coverPage_more = news_more.getCoverPage();
			    				String mainText_more = news_more.getMainText();
			    				Integer showimg_more = news_more.getShowimg();
			    				String htmlName_more = news_more.getHtmlName();
			    				
			    		        String uploadPathHtml = Constant.uploadPathHtml;
			    		        String uploadPathImage = Constant.uploadPathImage;
			    		        String imgFileName = coverPage_more.substring(coverPage_more.lastIndexOf("/")+1, coverPage_more.length());
			    		        
			    				Image imgSrc = javax.imageio.ImageIO.read(new File(uploadPathImage + "/" + imgFileName));
			    				int width = imgSrc.getWidth(null); 
			    				int height = imgSrc.getHeight(null); 
			    				
			    				logger.info("savemoreMaterial===>"+coverPage_more);
			    				String htmlString = createHtml(title_more,mainText_more,coverPage_more,showimg_more,width,height);
			    				
			    		        String cont = htmlString;
			    		        String htmlName = "temporaryFile.html";//临时文件名
			    		        OutputStreamWriter osw;
			    		        BufferedWriter out;
			    		        try{
			    		        	if(formatUrl(uploadPathHtml)){
			    		        		osw = new OutputStreamWriter(new FileOutputStream(uploadPathHtml+"\\"+htmlName, true),"UTF-8");
			    		        		out = new BufferedWriter(osw);
			    		        		out.write(cont,0,cont.length());
			    		        		out.close();
			    		        		osw.close();
			    		        		logger.info("生成临时html文件成功！");
			    		        	}
			    		       }catch(IOException e){
			    		    	  e.printStackTrace();
			    		    	  logger.error("生成临时html文件出现异常！", e);
			    		       }
			    		       boolean result = false;
			    		       File newFile = new File(uploadPathHtml+"\\"+htmlName);
			    		       if(newFile.exists()){
			    			       File oldFile = new File(uploadPathHtml, htmlName_more);
			    			       boolean temp = false;
			    			       if(oldFile.exists()){
			    			    	   oldFile.delete();
			    			    	   temp = true;
			    			    	   logger.info("旧的html文件删除成功！");
			    			       }
			    			       if(temp == true){ //判断是否把旧的html文件删除
			    			    	   File tempFile = new File(uploadPathHtml, htmlName_more);
			    			    	   newFile.renameTo(tempFile); //修改文件名称，换回原本名称
			    			    	   result = true;
			    			    	   logger.info("修改临时html文件名称成功！");
			    			       }
			    		       }
			    		       if(result == true){//判断是否创建临时html成功、删除旧html文件和修改名称都成功
			    		    	   materialManagerService.saveMoreMaterial(news_more);//更改数据
			    				   JsonUtil.write(response, "success");
			    				   logger.info("数据库更新成功！");
			    		       }else {
			    		    	   JsonUtil.write(response, "false");
			    		    	   logger.info("更新图文信息失败！");
			    		       }		
			        			
			        		}else {//第三种情况
			        			logger.info("有需要新建的图文，标题是"+news_more.getTitle());
			        			String title_more = news_more.getTitle();
						        String coverPage_more = news_more.getCoverPage();
						        Integer showimg_more =  news_more.getShowimg();
						        String mainText_more = news_more.getMainText();
						        
								//获取数据，生成html页面，并保存到指定地方
						        String uuid = UUID.randomUUID().toString();//创建一个唯一的字符串当做html名称
						        String htmlName = uuid+".html";
						        String uploadPathHtml = Constant.uploadPathHtml;
						        String uploadPathImage = Constant.uploadPathImage;
						        String filename = coverPage_more.substring(coverPage_more.lastIndexOf("/")+1, coverPage_more.length());
								
						        
								Image imgSrc = javax.imageio.ImageIO.read(new File(uploadPathImage+"/"+filename));
								int width = imgSrc.getWidth(null); 
								int height = imgSrc.getHeight(null); 
								logger.info("savemoreMaterial222===>"+coverPage_more);
								String htmlString = createHtml(title_more,mainText_more,coverPage_more,showimg_more,width,height);

						        String cont = htmlString;
						        String fileName = htmlName;
						        OutputStreamWriter osw;
						        BufferedWriter out;
						        try{
						        	if(formatUrl(uploadPathHtml)){
						        		osw = new OutputStreamWriter(new FileOutputStream(uploadPathHtml+"\\"+fileName, true),"UTF-8");
						        		out = new BufferedWriter(osw);
						        		out.write(cont,0,cont.length());
						        		out.close();
						        		osw.close();
						        	}
						       }catch(IOException ioe){
						    	  ioe.printStackTrace();
						    	  JsonUtil.write(response, "error");
						    	  logger.error("创建多图文的第"+i+"个图文信息失败！图文标题是"+news_more.getTitle());
						      }
						      logger.info("新图文开始插入数据库"); 					      
						      news_more.setDescription("");
							  news_more.setCoverPage(filename);
							  news_more.setFlag(Constant.ONE);
							  news_more.setGroupid(groupid);
							  news_more.setHtmlName(htmlName);
							  
							  news_more.setCreateTime(new Date());
							  news_more.setParentId(news_one.getId());
							  if(newType_parent != Constant.ZERO){
								  news_more.setNewsType(newType_parent);
							  }else {
								  news_more.setNewsType(Constant.ZERO);
							  }
						      
							  int k = i;
							  news_more.setRank(materialList.get(k-1).getRank()+1);
							  materialManagerService.saveMaterial(news_more);

							  JsonUtil.write(response, "success");
							  logger.info("新增多图文的信息成功！图文标题是"+news_more.getTitle());
			        		}
			        	}
						//更新缓存
						if(news_one.getNewsType() > 0 && news_one.getNewsType() != Constant.NEWS_KEYWORD){
							cacher(news_one.getGroupid(), Constant.ZERO, Constant.ZERO);
						}
					//  	 OperLogUtil4gialen.log(   "保存图文成功:"+SafeVal.val( title), ServletActionContext.getRequest());	
					  	 OperLogUtil4gialen.log(   "保存图文成功:" , ServletActionContext.getRequest());	
		        	}else {
		        		JsonUtil.write(response, "objectnul");
						logger.info("编辑多图文失败！多图文内容为空");
		        	}
		        }
			}
	   }catch (IOException e) {
			   e.printStackTrace();
			   try {
				   JsonUtil.write(response, "error");
			       logger.error("修改多图文信息出现异常:MaterialManagerAction.savemoreMaterial()", e);
				} catch (IOException e1) {
					 e1.printStackTrace();
				}
		}
	}
	
	//设置图文类型，规则：设置当前图文为某个图文类型，设置之前的属于这个图文类型的图文改为暂无选择
	public void setMaterialType(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
			if(user.getDefaultGroup() == null || user.getDefaultGroup() < 0){
				JsonUtil.write(response, "nopower");
			}else{
				groupid = user.getDefaultGroup();
				
				if(newsId != null && newsId > 0 && newsType != null && newsType > 0){
					
					if(newsType == Constant.NEWS_KEYWORD && StringUtils.trim(keyword).equals("")){
						return ;
					}
					if(newsType == Constant.NEWS_RULE && (activityId == null || activityId <= 0)){
						return ;
					}
					
//					boolean result_activity = false;
//					//判断抽奖规则的是否已存在
//					if(newsType == Constant.NEWS_RULE){
//						result_activity = materialManagerService.checkActivityNewsType(newsType, activityId);
//					}
					
					//更改属于这个图文类型的图文数据
					boolean result_before = true;
					if(newsType != Constant.NEWS_KEYWORD){
						result_before = materialManagerService.updateNewsType(newsType, groupid, Constant.ZERO, "", activityId);
					}
					
					//更改当前图文的图文类型为这个图文类型
					boolean result_after = materialManagerService.updateNewsType(newsType, groupid, newsId, StringUtils.trim(keyword), activityId);
					if(result_before && result_after){
						cacher(groupid, Constant.ZERO, Constant.ZERO);//更新缓存
						JsonUtil.write(response, "success");
					}else {
						JsonUtil.write(response, "failure");
					}
				}else {
					JsonUtil.write(response, "null");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JsonUtil.write(response, "error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//改变图文类型，专为关键词用
	public void setMaterialTypeToKwyword(){
		try {
			materialManagerService.updateNewsType(Constant.ZERO, Constant.ZERO, newsId, null, Constant.ZERO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//检查关键字有没有存在
	public void checkKeyword(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(keyword != null && !StringUtils.trim(keyword).equals("")){
				boolean result = materialManagerService.checkKeyword(StringUtils.trim(keyword));
				if(result == true){
					JsonUtil.getInstance().write(response, "exist");
				}else {
					JsonUtil.getInstance().write(response, "notexist");
				}
			}else {
				JsonUtil.getInstance().write(response, "null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JsonUtil.getInstance().write(response, "error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//检测页面传送的昵称有多少个
	public void checkNickName(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(nickname != null && !StringUtils.trim(nickname).equals("")){
				weixinusers = weixinUserManagerService.getWeixinUserByNickName(StringUtils.trim(nickname));
				if(weixinusers != null && weixinusers.size()> 0){
					if(weixinusers.size() == 1){
						JsonUtil.getInstance().write(response, "one");
					}else {
						JsonUtil.getInstance().write(response, "more");
					}
				}else {
					JsonUtil.getInstance().write(response, "nulldata");
				}
			}else {
				JsonUtil.getInstance().write(response, "null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//获取相同昵称的粉丝信息
	public String getWeixinusers(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			if(nickname != null && !StringUtils.trim(nickname).equals("")){
				
				pageWeixin = new Page<TMbWeixinUser>(PageUtil.PAGE_SIZE);
				Map<String, Object> data = new HashMap<String, Object>();
				
				int[] pageParams = PageUtil.init(pageWeixin, request);
				int pageNumber = pageParams[0];// 第几页
				int pageSize = pageParams[1];// 每页记录数
				// 分页条件
				data.put("pageNumber", pageNumber);
				data.put("pageSize", pageSize);
				
				// 查询条件
				data.put("nickname", StringUtils.trim(nickname));
				
				weixinusers = weixinUserManagerService.getWeixinUsersByNickName(pageWeixin, data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "preview_more";
	}
	
	/**
	 *  预览图文消息
	 *  注意：前提是openid对应的微信号要关注服务号
	 */
	public void preview(){
		HttpServletResponse response = ServletActionContext.getResponse();
		Map<String, String> map = new HashMap<String, String>();
		String json_map = "";
		try {
			if(newsId != null && newsId > 0 && ( (nickname != null && !StringUtils.trim(nickname).equals("")) || (userId != null && userId > 0))){
				List<TMbWeixinUser> list = new ArrayList<TMbWeixinUser>();
				//获取粉丝信息（openid）
				if(userId == null && nickname != null){
					list = weixinUserManagerService.getWeixinUserByNickName(StringUtils.trim(nickname));
				}else if(userId != null && nickname == null){
					
					list.add(weixinUserManagerService.getWeixinUserByUserId(userId));
				}
				
				if(list != null && list.size() == 1){
					TMbWeixinUser weixinuser = list.get(0);
					
					//获取图文信息
					List<TMbNews> newsList = materialManagerService.getMoreMaterial(newsId);
					
					if(newsList.size() == 0){
						map.put("state", "false");
						map.put("reason", "不存在图文信息");
						JSONArray jsonArray = JSONArray.fromObject(map);
						json_map = jsonArray != null ? jsonArray.toString() : "";
						JsonUtil.getInstance().write(response, json_map);
					}else {
						//组装发送信息对象
						NewsMessage newsMessage = buildNewsMessage(weixinuser.getOpenid(), newsList);
						
						JSONObject json = JSONObject.fromObject(newsMessage);
						String sendJsonData = json.toString();
						JSONObject jsonObject = WeixinUtil.sendMessage(sendJsonData, Constant.token.getToken());
						
						//成功后返回的json数据
						//{"errcode":0,"errmsg":"ok"}
						if (null != jsonObject) {  
					        if (0 == jsonObject.getInt("errcode")) {  //发送成功 
								map.put("state", "success");
								map.put("reason", "");
								JSONArray jsonArray = JSONArray.fromObject(map);
								json_map = jsonArray != null ? jsonArray.toString() : "";
					        	logger.info("预览图文发送成功");
					        	
					        	 OperLogUtil4gialen.log(   "预览图文发送成功:"+SafeVal.val( sendJsonData), ServletActionContext.getRequest());	
					        	JsonUtil.write(response, json_map);
					        }else{//发送失败
								map.put("state", "false");
						    	Properties properties =  new Properties();
							    String errorPath = Constant.path + "weixincode.properties";
							    FileInputStream fileInputStream = new FileInputStream(errorPath);
						    	properties.load(fileInputStream);  
								String errorMessage = properties.getProperty(jsonObject.getString("errcode"));
								map.put("reason", errorMessage+"，请联系管理员");
								JSONArray jsonArray = JSONArray.fromObject(map);
								json_map = jsonArray != null ? jsonArray.toString() : "";
					        	logger.info("发送失败,openid是"+weixinuser.getOpenid()+",返回的信息是"+jsonObject.toString());
					        	JsonUtil.write(response, json_map);
					        }  
						}
					}
				}else {
					map.put("state", "false");
					map.put("reason", "不存在该粉丝信息");
					JSONArray jsonArray = JSONArray.fromObject(map);
					json_map = jsonArray != null ? jsonArray.toString() : "";
					JsonUtil.getInstance().write(response, json_map);
				}
			}else {
				map.put("state", "false");
				map.put("reason", "参数不合法");
				JSONArray jsonArray = JSONArray.fromObject(map);
				json_map = jsonArray != null ? jsonArray.toString() : "";
				JsonUtil.getInstance().write(response, json_map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				map.put("state", "false");
				map.put("reason", "方法运行出错");
				JSONArray jsonArray = JSONArray.fromObject(map);
				json_map = jsonArray != null ? jsonArray.toString() : "";
				JsonUtil.getInstance().write(response, json_map);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//构建图文消息
	public static NewsMessage buildNewsMessage(String openId,List<TMbNews> newsList){
		if(openId != null && !"".equals(openId) && newsList != null){
			//"https://open.weixin.qq.com/connect/oauth2/authorize?appid=xxxx&redirect_uri=&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
	        
			NewsMessage clientMsg = new NewsMessage();
			clientMsg.setTouser(openId);
			clientMsg.setMsgtype(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			News news = new News();
			List<Article> artList = new ArrayList<Article>();
			for(TMbNews oneNews:newsList){
				Article oneArt = new Article();
				oneArt.setDescription(oneNews.getDescription());
				oneArt.setTitle(oneNews.getTitle());
				//html路径
				StringBuilder url = new StringBuilder();
				//图片路径
				StringBuilder picUrl = new StringBuilder();
				
				url.append(Constant.WEB_URL).append("html/news/").append(oneNews.getHtmlName());
				
				logger.info(url.toString());
				
				picUrl.append(Constant.NEWS_WEB_SITE);
				picUrl.append(Constant.NEWS_IMAGE_PATH);
				picUrl.append(oneNews.getCoverPage());
				
				oneArt.setUrl(url.toString());
				oneArt.setPicurl(picUrl.toString());
				artList.add(oneArt);
			}
		    news.setArticles(artList);
		    clientMsg.setNews(news);
		    return clientMsg;
		}
		return null;
	}
	
	//当图文新增、修改、删除时调用该方法进行更新缓存
	public void cacher(Integer groupid, Integer newsType, Integer type){
		try {
			if(type == Constant.ZERO){//新增、更新缓存
				//获取groupid该分公司的所有设置有图文类型的图文
				List<TMbNews> list = materialManagerService.getMaterialsByGroupid(groupid);
				if(list != null && list.size() > Constant.ZERO){
					for(TMbNews news : list){
						if(news.getNewsType() != Constant.NEWS_KEYWORD){//因抽奖规则和关键词特殊，故这两种图文不缓存
							List<TMbNews> result = materialManagerService.getMoreMaterial(news.getId());
							//键值
							StringBuilder rawKey = new StringBuilder();
							rawKey.append(news.getNewsType()).append("_").append(groupid);
								
							//指纹
							String md5Key = MD5.getMD5(rawKey.toString());
							MyCacher.getInstance().putCache(md5Key, result);
							logger.info("更新缓存成功！当前图文ID是"+news.getId()+"，类型id是"+news.getNewsType());
						}
					}
				}else {
					logger.info("更新缓存取消！当前分公司没有设置图文类型的图文");
				}
			}else if(type == Constant.ONE){//删除缓存
				//键值
				StringBuilder rawKey = new StringBuilder();
				rawKey.append(newsType).append("_").append(groupid);
				
				//指纹
				String md5Key = MD5.getMD5(rawKey.toString());
				MyCacher.getInstance().removeCache(md5Key);
				logger.info("删除缓存成功！当前分公司ID是"+groupid+"，类型id是"+newsType);
			}
		} catch (Exception e) {
			 e.printStackTrace();
			 logger.error("更新缓存失败！方法运行出错，", e);
		}
	}
	
	public MaterialManagerService getMaterialManagerService() {
		return materialManagerService;
	}

	public void setMaterialManagerService(
			MaterialManagerService materialManagerService) {
		this.materialManagerService = materialManagerService;
	}

	public BranchManagerService getBranchManagerService() {
		return branchManagerService;
	}

	public void setBranchManagerService(BranchManagerService branchManagerService) {
		this.branchManagerService = branchManagerService;
	}

	public List<TMbGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<TMbGroup> groups) {
		this.groups = groups;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCoverPage() {
		return coverPage;
	}

	public void setCoverPage(String coverPage) {
		this.coverPage = coverPage;
	}

	public String getMainText() {
		return mainText;
	}

	public void setMainText(String mainText) {
		this.mainText = mainText;
	}

	public String getHtmlName() {
		return htmlName;
	}

	public void setHtmlName(String htmlName) {
		this.htmlName = htmlName;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getImgFileContentType() {
		return imgFileContentType;
	}

	public void setImgFileContentType(String imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}

	public String getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	public Integer getShowimg() {
		return showimg;
	}

	public void setShowimg(Integer showimg) {
		this.showimg = showimg;
	}

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public Integer getNewsType() {
		return newsType;
	}

	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}

	public List<TMbNews> getNewslist() {
		return newslist;
	}

	public void setNewslist(List<TMbNews> newslist) {
		this.newslist = newslist;
	}

	public TMbNews getNews() {
		return news;
	}

	public void setNews(TMbNews news) {
		this.news = news;
	}

	public String getMaterials() {
		return materials;
	}

	public void setMaterials(String materials) {
		this.materials = materials;
	}

	public String getEditMoreMaterial() {
		return editMoreMaterial;
	}

	public void setEditMoreMaterial(String editMoreMaterial) {
		this.editMoreMaterial = editMoreMaterial;
	}

	public String getDeleteIdList() {
		return deleteIdList;
	}

	public void setDeleteIdList(String deleteIdList) {
		this.deleteIdList = deleteIdList;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	
	public WeixinUserManagerService getWeixinUserManagerService() {
		return weixinUserManagerService;
	}

	public void setWeixinUserManagerService(
			WeixinUserManagerService weixinUserManagerService) {
		this.weixinUserManagerService = weixinUserManagerService;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Page<TMbWeixinUser> getPageWeixin() {
		return pageWeixin;
	}

	public void setPageWeixin(Page<TMbWeixinUser> pageWeixin) {
		this.pageWeixin = pageWeixin;
	}

	public void setWeixinusers(List<TMbWeixinUser> weixinusers) {
		this.weixinusers = weixinusers;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

}
