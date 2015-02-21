package com.attilax.office;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.attilax.core;
import com.attilax.collection.listUtil;
import com.attilax.io.filex;
import com.attilax.util.Func_4SingleObj;
import com.attilax.util.Funcx;
import com.attilax.util.tryX;
import com.attilax.util.utf8编码;
import com.focusx.entity.TMbAwardWeixinResult;

//import tst.TMbAwardWeixinResult;
@utf8编码
public class excelUtil
{
	/**
	 * @功能：手工构建一个简单格式的Excel
	 */
	private static List<Map> getStudent() throws Exception
	{
		List list = new ArrayList();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");

		Map user1 = new HashMap();
		user1.put("awardName", "awardNameval11");
		user1.put("nickname", "nicknameval2");
		list.add(user1);
//		list.add(user2);
//		list.add(user3);

		return list;
	}
	@SuppressWarnings("all")
	public static void main(String[] args) throws Exception
	{
		List li=new ArrayList<String>();
 
		for(int i=0;i<5;i++)
		{
			TMbAwardWeixinResult o=new TMbAwardWeixinResult();
			o.setAwardName("awdnamex"+String.valueOf(i));
			li.add(o);
		}
		
		String titles="奖品名称,中奖粉丝昵称,中奖时间,奖品说明,中奖粉丝信息";
		String filds="awardName,nickname,awardTime,awardRemark,fansinfo";
		List list = listUtil.map_generic(li,
				new Func_4SingleObj<TMbAwardWeixinResult, Map>() {

					@Override
					public Map invoke(final TMbAwardWeixinResult o) {
						// attilax 老哇的爪子 下午03:32:08 2014-6-6
						Map mp = new tryX<Map>() {

						
							@Override
							public Map item(Object t) throws Exception {
								// attilax 老哇的爪子 下午02:47:29 2014-5-28
							//	core.print(o);
								Map m =core. obj2map(o);
								return m;
							}

						
						}.$(new HashMap());

						return mp;
					}

				});
		core.print(list);
	  	toExcel(titles, filds,list,"c:\\e.xls");
		System.out.println("---f");
		System.out.println("---aa");
		System.out.println("---b");
	}

	public   static <ati> void toExcel(String titles, String filds,List<ati> li,HttpServletResponse response )
	{
		List list = listUtil.map_generic(li, new Func_4SingleObj<ati, Map>() {

			@Override
			public Map invoke(final ati o) {
				// attilax 老哇的爪子 下午03:32:08 2014-6-6
				Map mp = new tryX<Map>() {

					@Override
					public Map item(Object t) throws Exception {
						// attilax 老哇的爪子 下午02:47:29 2014-5-28
						// core.print(o);
						if (o instanceof Map)
							return (Map) o;
						Map m = core.obj2map(o);
						return m;
					}

				}.$(new HashMap());

				return mp;
			}

		});
		core.log("--o6a wait exp list:"+core.obj2jsonO5(list));
		try { 
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("GB2312");
			String downFilename = new String((filex.getUUidName()+".xls").getBytes(), "iso-8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + downFilename);
			toExcel(titles,filds,list,response.getOutputStream());
		} catch (Exception e) {
			core.log(e);
		}
	}
	
	public   static void toExcelMap(String titles, String filds,List<Map> list,HttpServletResponse response )
	{
		try { 
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("GB2312");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String((filex.getUUidName()).getBytes(), "iso-8859-1"));
			toExcel(titles,filds,list,response.getOutputStream());
		} catch (Exception e) {
			core.log(e);
		}
	}
	
	private static void toExcel(String titles, String filds,List<Map> list,OutputStream outStrm) throws Exception {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("sheet1");
		//sheet.setColumnWidth(columnIndex, width)
	
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//	 style.set

		String[] tit_arr=titles.split(",");
		//column index form 0;
		 	for(int i=0;i<tit_arr.length;i++)
		 	{
		 		  sheet.setColumnWidth(i, 7 *2* 256);  
		 	}
		  sheet.setColumnWidth(6, 20*2 * 256);  
		  sheet.setColumnWidth(7, 7 *2* 256);  
	        ////这里你会发现一个有趣的现象，SetColumnWidth的第二个参数要乘以256，这是怎么回事呢？其实，这个参数的单位是1/256个字符宽度  

		int n=0;
		for(String tit:tit_arr)
		{
			HSSFCell cell = row.createCell((short) n);
			cell.setCellValue(tit);
			cell.setCellStyle(style);
			n++;
		}
	
		 

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
	

		for (int i = 0; i < list.size(); i++)
		{
			row = sheet.createRow((int) i + 1);
			Map stu = (Map) list.get(i);
			
			
			// 第四步，创建单元格，并设置值
			int n2 = 0;
			for (String tit : tit_arr) {
				String curField = getFild(filds, n2);
				String val = (String) stu.get(curField);
				if (val == null) {
					n2++;
					continue;
				}
				HSSFCell cell = row.createCell((short) n2);
				cell.setCellValue(val);
				// cell.setCellStyle(style);
				n2++;
			}
	 
		}
		// 第六步，将文件存到指定位置
		try
		{
			//String outputFilePath = "E:/students.xls";
			//FileOutputStream fout = new FileOutputStream(outputFilePath);
			wb.write(outStrm);
			outStrm.close();
		}
		catch (Exception e)
		{
			core.log(e);
			e.printStackTrace();
		}
	}

	
	private static void toExcel(String titles, String filds,List<Map> list,String outputFilePath) throws Exception {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("sheet1");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		String[] tit_arr=titles.split(",");
		int n=0;
		for(String tit:tit_arr)
		{
			HSSFCell cell = row.createCell((short) n);
			cell.setCellValue(tit);
			cell.setCellStyle(style);
			n++;
		}
	
		 

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
	

		for (int i = 0; i < list.size(); i++)
		{
			row = sheet.createRow((int) i + 1);
			Map stu = (Map) list.get(i);
			
			
			// 第四步，创建单元格，并设置值
			int n2=0;
			for(String tit:tit_arr)
			{
				String  curField=getFild(filds,n2);
				String val=(String) stu.get(curField);
				if(val==null)continue;
				HSSFCell cell = row.createCell((short) n2);
				cell.setCellValue(val);
			//	cell.setCellStyle(style);
				n2++;
			}
	 
		}
		// 第六步，将文件存到指定位置
		try
		{
			//String outputFilePath = "E:/students.xls";
			FileOutputStream fout = new FileOutputStream(outputFilePath);
			wb.write(fout);
			fout.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-6-6 下午02:58:45$
	
	 * @param filds
	 * @param n2
	 * @return
	 */
	private static String getFild(String filds, int n2) {
		// attilax 老哇的爪子  下午02:58:45   2014-6-6 
		String[] fs=filds.split(",");
		for(int n=0;n<fs.length;n++)
		{
			if(n2==n)
				return fs[n];
		}
		return "";
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-6-6 下午02:57:53$
	
	 * @param n2
	 * @return
	 */
	private static String getFild(int n2) {
		// attilax 老哇的爪子  下午02:57:53   2014-6-6 
		return null;
	}
}