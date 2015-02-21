package com.focusx.action.branchManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.attilax.SafeVal;
import com.focusx.entity.TMbGroup;
import com.focusx.entity.TMbWeixinUser;
import com.focusx.entity.TUserUsers;
import com.focusx.entity.dataAnalysis.WeixinUser;
import com.focusx.service.BranchManagerService;
import com.focusx.service.WeixinUserManagerService;
import com.focusx.util.CSVUtils;
import com.focusx.util.Constant;
import com.focusx.util.DateUtil;
import com.focusx.util.JsonUtil;
import com.focusx.util.MyHttpUtils;
import com.focusx.util.OperLogUtil4gialen;
import com.focusx.util.Page;
import com.focusx.util.PageUtil;
import com.focusx.util.Tree;
import com.focusx.util.WxQRCode;
import com.opensymphony.xwork2.ActionSupport;

public class BranchManagerAciton extends ActionSupport{
	
	protected static Logger logger = Logger.getLogger("BranchManagerAciton");
	private BranchManagerService branchManagerService;
	private WeixinUserManagerService weixinUserManagerService;
	private TMbGroup group;
	private String groupname;//分组名称
	private Integer groupid;//分组ID
	private String remark;//地址
	private Double latitude;//经度
	private Double longitude;//纬度
	
	private Page page;// 分页对象
	private List<TMbGroup> groupList;//分组集合
	private List<TMbWeixinUser> weixinuserList;//微信粉丝集合
	
	private String p;
	private String ps;
	private List<TMbGroup> groups;
	private String tree;//所有分组组成的树的字符串
	private Integer parentId;
	private String warning;
	
	/**
	 *  获取所有一级分公司分组信息，并转化为json格式传到页面
	 */
	public String listBranch(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
		List<Tree> list = new ArrayList<Tree>();
		try {
			if(user != null){
				if(user.getIsSystem() != null && user.getIsSystem() == Constant.ONE){//管理员
					list = getTreejson(0);
				}else{
					if(user.getDefaultGroup() == null || user.getDefaultGroup() < 0){
						warning = "当前登陆用不属于任何一个分公司";
						return "warning";
					}else {
						list = getTreejsonOnlyOne(user.getDefaultGroup());
					}
				}
			}else {
				return "login";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray jsonArray = JSONArray.fromObject(list);
		tree = jsonArray.toString();
		 OperLogUtil4gialen.log(   "查询分公司分组信息:"+SafeVal.val(""), ServletActionContext.getRequest());
		return "list";
	}
	
	// 根据parentId获取所有子分组或子分公司
	public List<Tree> getTreejson(int parentId){
		List<Tree> list = new ArrayList<Tree>();
		try {
			List<TMbGroup> one = branchManagerService.getTreeByParentId(parentId);
			if(one != null && one.size() > 0){//一级
				for(int i = 0; i < one.size(); i++){
					Tree tree = new Tree();//树分组对象
					tree.setIsexpand("false");//设置是否展开，这里不展开
					tree.setText(one.get(i).getGroupname());//设置分组名称
					tree.setUrl("weixin/branchManager!branchs?groupid="+one.get(i).getGroupid());
					if(parentId != 0){//二级都是子分公司
						tree.setChildren(null);
					}else {
						boolean result = branchManagerService.isParent(one.get(i).getGroupid());
						if(result){//二级	
							List<Tree> twolist = new ArrayList<Tree>();
							tree.setChildren(twolist);
						}else{
							tree.setChildren(null);
						}
					}
					list.add(tree);
				}
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据parentId获取所有子分组或子分公司出现异常：BranchManagerAciton.getTreejson()，parentId为"+parentId, e);
		}
		return list;
	}
	
	//获取一个分公司分组信息
	public List<Tree> getTreejsonOnlyOne(int groupid){
		List<Tree> list = new ArrayList<Tree>();
		try {
			TMbGroup groupTree = branchManagerService.getGroup(groupid);
			if(groupTree != null){//一级
				Tree tree = new Tree();//树分组对象
				tree.setIsexpand("false");//设置是否展开，这里不展开
				tree.setText(groupTree.getGroupname());//设置分组名称
				tree.setUrl("weixin/branchManager!branchs?groupid="+groupid);
				boolean result = branchManagerService.isParent(groupid);
				if(result){//二级	
					List<Tree> twolist = new ArrayList<Tree>();
					tree.setChildren(twolist);
				}else{
					tree.setChildren(null);
				}
				list.add(tree);
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取一个分公司分组信息出现异常：BranchManagerAciton.getTreejsonOnlyOne()，groupid为"+groupid, e);
		}
		return list;
	}
	
	//根据parentId获取下一级分公司
	public void getTreejson(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");  
        response.setContentType("text/json;charset=UTF-8");  
		try {
			List<Tree> list = getTreejson(parentId);
			JSONArray jsonArray = JSONArray.fromObject(list);
			JsonUtil.write(response, jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JsonUtil.write(response, "error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 *  新增分组或分公司
	 */
 	public void save(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(groupname != null && !StringUtils.trim(groupname).equals("")){
				TMbGroup group = new TMbGroup();
				group.setGroupname(StringUtils.trim(groupname));
				group.setCreatetime(new Date());
				if(parentId > 0){
					group.setParentId(parentId);
				}else {
					group.setParentId(0);
				}
				group.setRemark(StringUtils.trim(remark));
				group.setLongitude(longitude);
				group.setLatitude(latitude);
				branchManagerService.add(group);
				groupid = group.getGroupid();
				
				OperLogUtil4gialen.log(   "新增分组或分公司"+groupname, ServletActionContext.getRequest());	
				JsonUtil.write(response, groupid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JsonUtil.write(response, "false");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 *  删除一个分组或分公司前检查是否符合要求，如果分组或分公司下有粉丝不能删除该分组
	 * @throws IOException 
	 */
	public void deleteCheck(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			boolean isuser = branchManagerService.checkGroup(groupid);//判断分组下是否有粉丝  true 没有/false 有
		    boolean isparent = branchManagerService.checkParent(groupid);//判断是否有子分组或子分公司  true 有/false 没有
		    if(isuser == false){ 
				JsonUtil.getInstance().write(response, "isuser");
		    }else if(isparent){
				JsonUtil.getInstance().write(response, "isparent");
		    }else {
		    	branchManagerService.delete(groupid);
				JsonUtil.getInstance().write(response, "success");
		    }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("检查分组或分公司出现异常：BranchManagerAciton.deleteCheck()", e);
		}
	}
	
	/**
	 *  删除一个分组
	 */
	public String deleteGroup(){
		try {
			branchManagerService.delete(groupid);
			OperLogUtil4gialen.log(   "删除分组或分公司"+groupname, ServletActionContext.getRequest());	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除分组或分公司出现异常：BranchManagerAciton.deleteGroup()，groupid为"+groupid, e);
		}
		return "listGroupAction";
	}
	
	/**
	 *  根据id获取分公司信息
	 */
	public void getGroupByGroupid(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");  
        response.setContentType("text/json;charset=UTF-8");  
		try {
			if(groupid > 0){
				group = branchManagerService.getGroup(groupid);
				JSONObject json = JSONObject.fromObject(group);
				JsonUtil.write(response, json);	
			}else {
				JsonUtil.write(response, "null");	
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
	
	/**
	 *  更新分组或分公司
	 */
	public void edit(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			group = branchManagerService.getGroup(groupid);
			if(groupname != null && !groupname.equals("")){
				group.setGroupname(groupname);
			}else {
				group.setGroupname(group.getGroupname());
			}
			group.setRemark(remark);
			group.setLatitude(latitude);
			group.setLongitude(longitude);
			branchManagerService.update(group);
			JsonUtil.write(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新分组或分公司出现异常：BranchManagerAciton.edit()，groupid为"+groupid, e);
		}
	}
	
	/**
	 *  检查是否存在该名称的分组或分公司
	 *  @throws IOException 
	 */
	public void checkAddGroup(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			String result = branchManagerService.getGroupByGroupname(groupname);
			JsonUtil.getInstance().write(response, result);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("检查是否存在该名称的分组或分公司出现异常：BranchManagerAciton.checkAddGroup()，groupname为"+groupname, e);
		}
	}
	
	/**
	 *  分组列表，包含查询条件
	 *  三种情况：1、全部查询；2、查询某个分组；3、查询单个（id，groupname）
	 */
	public String branchs(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Map<String, Object> data = new HashMap<String, Object>();

		page = new Page<TMbGroup>(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(page, request);
		int pageNumber = pageParams[0];// 第几页
		int pageSize = pageParams[1];// 每页记录数

		// 分页条件
		data.put("pageNumber", pageNumber);
		data.put("pageSize", pageSize);
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		TUserUsers user =  (TUserUsers) session.getAttribute(session.getId());
		
		try {
			if(groupid == null && user != null){
				if(user.getIsSystem() != null && user.getIsSystem() == Constant.ONE){//管理员
					groupid = 0;
				}else{
					if(user.getDefaultGroup() == null || user.getDefaultGroup() < 0){
						warning = "当前用户不属于任何一个分公司！";
						JsonUtil.write(response, warning);
						return "warning";
					}else {
						groupid = user.getDefaultGroup();
					}
				}
			}
			
			if(groupid == null){ 
				groupid = 0;
			}

			// 查询条件
			data.put("groupname", StringUtils.trim(groupname));
			data.put("groupid", groupid);
		    groupList = branchManagerService.getGroups(page, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("分组列表出现异常：BranchManagerAciton.groups()", e);
		}
		return "branchs";
	}

	//根据groupid获取该分公司下的分店
	public List<Integer> getAllGroupIdByGroupId(Integer groupid){
		List<Integer> list = null;
		try {
			list = branchManagerService.getAllGroupIdByGroupId(groupid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据groupid获取该分公司下的分店出现异常：BranchManagerAciton.getAllGroupIdByGroupId()，" +
					"groupid为"+groupid, e);
		}
		return list;
	}
	
	
	//根据groupid获取对应分组下的分公司ID和名称
	public Map<Integer, String> getAllGroupByGroupId(Integer groupid){
		Map<Integer, String> data = new HashMap<Integer, String>();
		try {
			data = branchManagerService.getAllGroupByGroupId(groupid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("根据groupid获取对应分组下的分公司ID和名称出现异常",e);
		}
		return data;
	}
	
	/**
	 *  生成二维码图片
	 */
	public String createTicket(){
		logger.info("开始执行生成二维码图片方法，分公司ID是"+groupid);
		String back = null;
		try {
			if(groupid != null && groupid != 0){
				TMbGroup group = branchManagerService.getGroup(groupid);
				if(group == null){//判断当前是否存在该groupid的数据
					logger.info("数据库不存在该groupid的分公司数据，groupid是"+groupid);
					warning = "下载二维码图片失败！请稍后再试！三秒后返回";
					return "error";
				}
				Integer groupid = group.getGroupid();
				String groupname = group.getGroupname();
				
				//获取accessToken
				String accessToken = "";
				try {
					accessToken = Constant.token.getToken();
					logger.info("获取的accessToken是"+accessToken);
					if(accessToken.equals("") || accessToken == null){
						return "error";
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("获取accessToken失败");
				}
				
			    logger.info("需要生成二维码图片的分公司ID为"+groupid);
				
				boolean exist = false;
				//创建二维码ticket
				String ticket = null;
				WxQRCode Code_TICKET = new WxQRCode(WxQRCode.QR_FOREVER);
				
				Integer ticketResult = 0; 
				List<String> groupnamelist = new ArrayList<String>();
				List<String> groupnamErrorList = new ArrayList<String>();
				formatUrl(Constant.uploadPathCode);//先判断本地硬盘是否存在该文件夹，否新建
				File codeImage = new File(Constant.uploadPathCode+"\\"+groupname+".jpg");
				if(codeImage.exists()){//判断对应文件夹内是否有已生成的二维码图片
					exist = true;
				}else {
					Map<Object,Object> paramsMap = new TreeMap<Object,Object>();
					paramsMap.put("action_name", "QR_LIMIT_SCENE");
					Map<Object,Object> actionInfo = new TreeMap<Object,Object>();				
					Map<String,Integer> sencesMap = new TreeMap<String,Integer>();
					sencesMap.put("scene_id", groupid);					
					actionInfo.put("scene", sencesMap);					
					paramsMap.put("action_info", actionInfo);
					
					JSONObject json = JSONObject.fromObject(paramsMap);
					String jsonStr = json != null ? json.toString() : "";
						
					Code_TICKET.setAccessToken(accessToken);
					JSONObject ticketJson = MyHttpUtils.httpsRequest(Code_TICKET.toString(), Code_TICKET.getReqMethod(), jsonStr);
					if(ticketJson != null && null != ticketJson.get("ticket")){//判断返回的是否正确的信息
						String temp = ticketJson.getString("ticket");
						if(temp != null && temp != ""){
							ticket = temp;
							logger.info("获取到的ticket和ID分别是"+temp+","+groupid);
						}
					}
					
					//通过ticket换取二维码
					WxQRCode Code_SHOW = new WxQRCode(WxQRCode.QR_SHOW);
					List<Integer> getTicketimgResult = new ArrayList<Integer>();
					
					if(ticket != null && !ticket.equals("")){
						Code_SHOW.setTicket(ticket);
						//MyHttpUtils.httpRequest2返回的值有0、1,0代表成功，1代表失败
						ticketResult = MyHttpUtils.httpRequest2(Code_SHOW.toString(), groupname);
						groupnamelist.add(groupname);
						getTicketimgResult.add(ticketResult);
						if(ticketResult == 1){
							groupnamErrorList.add(groupname);
						}
					}
				}
				
				if(exist == true || (ticket != null && !ticket.equals("") && ticketResult == 0)){
					HttpServletResponse response = ServletActionContext.getResponse();
					String zipFileName = groupname+".zip";
				    
				    //源文件
			        File sourceFile = new File(Constant.uploadPathCode+"\\"+groupname+".jpg");
			    
			        //创建一个临时压缩文件，我们会把文件流全部注入到这个文件中
			        File file = new File(Constant.uploadPathCode+"\\"+zipFileName);
			        if (!file.exists()){  
			        	file.createNewFile();  
			            logger.info("临时压缩包创建成功。");
			        }
			        response.reset();
			        //创建文件输出流
			        FileOutputStream fous = new FileOutputStream(file);  
			            
			        //打包的方法我们会用到ZipOutputStream这样一个输出流,所以这里我们把输出流转换一下
			        ZipOutputStream zipOut = new ZipOutputStream(fous);
			        zipOut.setEncoding("GBK");
					zipFile(sourceFile, zipOut);
					
			        
			        //如果生成过程中有失败的二维码，失败分公司名称写进一个txt文本，让他们自己单个生成
			        File txtFile = null ;
			        if(groupnamErrorList.size() > 0){
			        	txtFile = new File(Constant.uploadPathCode+"\\"+"生成失败的分公司.txt");
			            if(!txtFile.exists()){  
			            	txtFile.createNewFile(); 
			            }  
			            BufferedWriter writer = null;
			            try {
			            	writer = new BufferedWriter(new FileWriter(txtFile));
				            for(int i = 0; i < groupnamErrorList.size(); i++){
				            	writer.write(groupnamErrorList.get(i)+"\r\n");
				            }
				            writer.write("因网络或微信服务器影响导致生成失败，请根据分公司名称重新生成。");
			                writer.close();
			                zipFile(txtFile, zipOut);//加入到压缩包里
						} catch (Exception e) {
							writer.close();
							e.printStackTrace();
							logger.info("生成‘生成失败的分公司.txt’出现异常",e);
						}
			        }
					    
			        zipOut.close();
			        fous.close();
			            
			        // 以流的形式下载文件。
			        InputStream in = new BufferedInputStream(new FileInputStream(file.getPath()));
			        byte[] buffer = new byte[in.available()];
			        in.read(buffer);
			        in.close();
			        // 清空response
			        response.reset();

			        zipFileName = new String(zipFileName.getBytes("utf-8"), "ISO_8859_1");
			        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			        response.setContentType("application/octet-stream");
			        response.setHeader("Content-Disposition", "attachment;filename=" + zipFileName);
			        toClient.write(buffer);
			        toClient.flush();
			        toClient.close();
						
			        // 删除临时压缩包
			        if (file.isFile() && file.exists()) {
			        	file.delete();
						logger.info("删除临时压缩包成功。");
					}
			        if (txtFile != null && txtFile.isFile() && txtFile.exists()) {
			        	txtFile.delete();
						logger.info("删除”生成失败的分公司.txt“成功。");
					}
				}else {
					back = "error";
					logger.error("生成二维码图片失败，不存在生成ID，分公司ID为"+groupid);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("生成二维码图片失败，分公司ID为"+groupid, e);
			back = "error";
		}
		return back;
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
	
	public static void zipFile(File inputFile, ZipOutputStream ouputStream) {
        try {
            if(inputFile.exists()) {
                //如果是目录的话这里是不采取操作的，至于目录的打包正在研究中
                if (inputFile.isFile()) {
                    FileInputStream IN = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(IN, 512);
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    ouputStream.putNextEntry(entry);
                    // 向压缩文件中输出数据  
                    int nNumber;
                    byte[] buffer = new byte[1024];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        ouputStream.write(buffer, 0, nNumber);
                    }
                    // 关闭创建的流对象  
                    bins.close();
                    IN.close();
                } else {
                    try {
                        File[] files = inputFile.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            zipFile(files[i], ouputStream);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 *  根据groupid查找属于该分组的粉丝
	 */
	public String getWeixinuser(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Map<String, Object> data = new HashMap<String, Object>();

		page = new Page<TMbWeixinUser>(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(page, request);
		int pageNumber = pageParams[0];// 第几页
		int pageSize = pageParams[1];// 每页记录数

		// 分页条件
		data.put("pageNumber", pageNumber);
		data.put("pageSize", pageSize);

		TMbGroup parentGroup = null;
		if(groupid != null && groupid > 0){
			parentGroup = branchManagerService.getParentGroup(groupid);
		}
		if(parentGroup != null){
			groupid = parentGroup.getGroupid();
			groupname = parentGroup.getGroupname();
		}else {
			groupname = branchManagerService.getGroup(groupid).getGroupname();
		}
		
		// 查询条件
		data.put("groupid", groupid);
		data.put("inorout", "in");//这个条件是用来判断是否按groupid in查找
		
		weixinuserList = weixinUserManagerService.getWeixinusersByGroupid(page, data);
		return "listWeixinuser";
	}
	
	//修改跳转
	public String editJsp(){
		try {
			if(groupid > 0){
				group = branchManagerService.getGroup(groupid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "edit";
	}
	
	//判断该parentId是否是分店
	public void checkTwoBranch(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(parentId != null && parentId > 0){
				TMbGroup group = branchManagerService.getGroup(parentId);
				if(group.getParentId() == 0){
					JsonUtil.write(response, "success");
				}else {
					JsonUtil.write(response, "false");
				}
			}else {
				JsonUtil.write(response, "null");
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
	
	//获取所有分公司信息（不包括分店）
	public void getTopBranch(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			List<TMbGroup> branchs = branchManagerService.getTopBranch();
			JSONArray jsonArray = JSONArray.fromObject(branchs);			
			String json_data = jsonArray != null ? jsonArray.toString() : "";
			JsonUtil.getInstance().write(response, json_data);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//根据分公司ID获取其所有门店
	public void getStoresByGroupid(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(groupid != null && groupid > 0){
				List<TMbGroup> branchs = branchManagerService.getStoresByGroupid(groupid);
				JSONArray jsonArray = JSONArray.fromObject(branchs);			
				String json_data = jsonArray != null ? jsonArray.toString() : "";
				JsonUtil.getInstance().write(response, json_data);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//导出门店信息
	public void exportcsv(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(groupid != null && groupid > 0){
				TMbGroup group = branchManagerService.getGroup(groupid);
				List<TMbGroup> dataList = branchManagerService.getStoresByGroupid(groupid);
			    
				List<String> dataListString = new ArrayList<String>();
				dataListString.add("门店ID, 门店名称");
				
				for(TMbGroup g : dataList){
					StringBuffer buf = new StringBuffer();
					buf.append(g.getGroupid()+",").append(g.getGroupname());
					dataListString.add(buf.toString());
				}
				String fileName = group.getGroupname()+"门店信息.csv";
				
		        CSVUtils.exportCsv(new File(Constant.otherPath+fileName), dataListString);
		        
		        response.setContentType("application/csv");
		        response.setHeader("Location",fileName);
				response.setCharacterEncoding("UTF-8");
		        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO_8859_1")); 
		        OutputStream outputStream = response.getOutputStream();
		        InputStream inputStream = new FileInputStream(Constant.otherPath + fileName);
		        byte[] buffer = new byte[2048];
		        int i = -1;
		        while ((i = inputStream.read(buffer)) != -1) {
		        	outputStream.write(buffer, 0, i);
		        }
		        outputStream.flush();
		        outputStream.close();
		        inputStream.close();
		        File file = new File(Constant.otherPath + fileName);
		        if(file.exists()){
		        	file.delete();
		        }
			}else {
				logger.info("导出门店信息出错，原因是groupid是空或小于等于0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出门店方法发生异常", e);
		}
	}
	
	public BranchManagerService getBranchManagerService() {
		return branchManagerService;
	}

	public void setBranchManagerService(BranchManagerService branchManagerService) {
		this.branchManagerService = branchManagerService;
	}

	public TMbGroup getGroup() {
		return group;
	}

	public void setGroup(TMbGroup group) {
		this.group = group;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
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

	public List<TMbGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<TMbGroup> groups) {
		this.groups = groups;
	}

	public String getTree() {
		return tree;
	}

	public void setTree(String tree) {
		this.tree = tree;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<TMbGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<TMbGroup> groupList) {
		this.groupList = groupList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<TMbWeixinUser> getWeixinuserList() {
		return weixinuserList;
	}

	public void setWeixinuserList(List<TMbWeixinUser> weixinuserList) {
		this.weixinuserList = weixinuserList;
	}

	public WeixinUserManagerService getWeixinUserManagerService() {
		return weixinUserManagerService;
	}

	public void setWeixinUserManagerService(
			WeixinUserManagerService weixinUserManagerService) {
		this.weixinUserManagerService = weixinUserManagerService;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		BranchManagerAciton.logger = logger;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}
}
