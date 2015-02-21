package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.focusx.entity.TMbGroup;
import com.focusx.util.Constant;
import com.focusx.util.MyHttpUtils;

public class ImportExcelGroup {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//广州分公司、ok
		//int parentGroupId = 1;
		File gzFile = new File("E:\\环境\\全国各分公司门店地址20140210\\gz.xls");
		
		//北京ok
		//int parentGroupId = 9;
		File bjFile = new File("E:\\环境\\全国各分公司门店地址20140210\\bj.xls");
		
		//西安ok
		//int parentGroupId = 10;
		File xaFile = new File("E:\\环境\\全国各分公司门店地址20140210\\xa.xls");
		
		//武汉 ok
		//int parentGroupId = 11;
		File whFile = new File("E:\\环境\\全国各分公司门店地址20140210\\wh.xls");
		
		//上海 ok
		//int parentGroupId = 8;
		File ShFile = new File("E:\\环境\\全国各分公司门店地址20140210\\sh.xls");
		
		//成都
		int parentGroupId = 12;
		File cdFile = new File("E:\\环境\\全国各分公司门店地址20140210\\cd.xls");
		
		//重庆ok
		//int parentGroupId = 13;
		File cqFile = new File("E:\\环境\\全国各分公司门店地址20140210\\cq.xls");
		
		
		
		Configuration cfg = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		Constant.sessionFactory = cfg.buildSessionFactory(serviceRegistry);
		
		
		
		FileInputStream fis = null;
		Session sess = null;
		Transaction tx = null;
		try {
			
			File importFile = cdFile;
			
			fis = new FileInputStream(importFile);
			
			System.out.println(importFile.getAbsolutePath());
			
			HSSFWorkbook book = new HSSFWorkbook(new POIFSFileSystem(fis));
			
			HSSFSheet sheet = book.getSheetAt(0);
			
			List<TMbGroup> groupList = new ArrayList<TMbGroup>();
			
			if(sheet != null){
				int rowNum = sheet.getLastRowNum();
				HSSFRow row = null;
				for(int i = 0 ;i < rowNum; i ++){
					row = sheet.getRow(i);
					//System.out.println(row);
					//System.out.println(row.getCell(0).getCellType());
					//System.out.println(row.getCell(0).getNumericCellValue());
					//System.out.println(row.getCell(1).getStringCellValue());
					//System.out.println(row.getCell(2).getStringCellValue());
					String groupname = row.getCell(1).getStringCellValue();
					String groupremark = row.getCell(2).getStringCellValue();
					
					TMbGroup nGroup = new TMbGroup();
					
					TMbGroup parent = new TMbGroup();
					parent.setGroupid(parentGroupId);
					//nGroup.setParentId(parentGroupId);
					nGroup.setCreatetime(new Date());
					nGroup.setGroupname(groupname);
					nGroup.setRemark(groupremark);
					System.out.println(groupremark);
					
					double latitude = 0;
					double longitude = 0;
					nGroup.setLatitude(latitude);
					nGroup.setLongitude(longitude);
					groupList.add(nGroup);
				}
			}
			
			sess = Constant.sessionFactory.openSession();
			tx = sess.beginTransaction();
			
			for(TMbGroup oneGroup:groupList){
				sess.save(oneGroup);
			}
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally{
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}

}
