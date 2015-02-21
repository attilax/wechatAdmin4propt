package com.focusx.util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.tools.zip.ZipOutputStream;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class JxlUtils {
	
	public static void wirteData(OutputStream os,String [] headers,Object [] footer,List data) throws IOException, WriteException{
		wirteData(os,headers,footer,data,0);
	}
	
	public static void wirteData2(OutputStream os,String [] headers,Integer [] footer,
			int startrow,List data,int hfreeze) throws IOException, WriteException{
		int listSize = data.size();//获取的数据总数
		int totalCount = 1;//默认的Sheet的个数表单
		int pageSize = 60000;//设置个sheet表单的条数
		if (listSize % pageSize == 0) {
		  totalCount = listSize/pageSize;
		}else{
		  totalCount = listSize/pageSize + 1;
		} 
		WritableWorkbook workbook = createWorkbook(os);
		for(int k = 0; k < totalCount; k++){//循环执行次数 即需要建立多少个sheet表单
			WritableFont songti12 = new WritableFont(WritableFont.createFont("宋体"),12,WritableFont.NO_BOLD);
			WritableFont arial12 = new WritableFont(WritableFont.ARIAL,12,WritableFont.NO_BOLD);
			WritableFont arialRedBold12 = new WritableFont(WritableFont.ARIAL,12,WritableFont.NO_BOLD);
			arialRedBold12.setBoldStyle(WritableFont.BOLD);
			arialRedBold12.setColour(Colour.RED);
			WritableSheet s = null;
			if (k != 0) {// 表单名称每一页加一个页码数
				s = workbook.createSheet("sheet" + k, k);
			} else {
				s = workbook.createSheet("sheet", k);
			}
			s.getSettings().setVerticalFreeze(1);
			if(hfreeze>0){
				s.getSettings().setHorizontalFreeze(hfreeze);
			}
			WritableCellFormat cwt = new WritableCellFormat(songti12);
			WritableCellFormat wrappedText = new WritableCellFormat(songti12);
			WritableCellFormat cfipc = new WritableCellFormat(arial12,NumberFormats.PERCENT_INTEGER);
			WritableCellFormat cfiry = new WritableCellFormat(NumberFormats.ACCOUNTING_RED_FLOAT);
			wrappedText.setAlignment(Alignment.CENTRE);
		    wrappedText.setWrap(false);
			int i=0;
			ArrayList<Integer> percents = new ArrayList<Integer>();
			ArrayList<Integer> rys = new ArrayList<Integer>();
			for(i=0;i<headers.length;i++){
				String header = headers[i];
				boolean is = false;
				int m = header.indexOf("%");
				if(m>-1){
					percents.add(i);
					header = header.substring(0,m);
					is = true;
				}
				if(is == false){
					m = header.indexOf("￥");
					if(m>-1){
						rys.add(i);
						header = header.substring(0,m);
					}
				}
			    Label l = new Label(i,0,header, wrappedText);
			    s.addCell(l);
			}
			
			i=1;
			for(int n = k * pageSize; n < k * pageSize + pageSize && n < listSize; n++){
				Object []value = (Object [])data.get(n);
				for(int j=0;j<value.length;j++){
					if(value[j] instanceof Number){
						if(((Number)value[j]).doubleValue()!=0){
							WritableCellFormat cwt1=cwt;
							boolean is = false;
							if(percents.contains(j)){
								cwt1 = cfipc;
								is = true;
							}
							if(is == false){
								if(rys.contains(j)){
									cwt1 = cfiry;
									is = true;
								}
							}
							jxl.write.Number numCell = new jxl.write.Number(j,i,((Number)value[j]).doubleValue(),cwt1);
							s.addCell(numCell);
						}else{
							Label lblCell = new Label(j,i,"",cwt);
							s.addCell(lblCell);
						}
					}else if(value[j] instanceof String){
						Label l = new Label(j,i,(String)value[j],cwt);
						s.addCell(l);
					}else if (value[j] instanceof Date) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Label l = new Label(j,i,sdf.format(value[j]),cwt);
						s.addCell(l);
					}
				}
				i++;
			}			
		}
		closeWorkbook(workbook);
	}
	
	public static void wirteData(OutputStream os,String [] headers,Object [] footer,List data,int hfreeze) throws IOException, WriteException{
		Sender sender = new Sender();
		WritableWorkbook workbook = createWorkbook(sender);
		WritableFont songti12 = new WritableFont(WritableFont.createFont("宋体"),12,WritableFont.NO_BOLD);
		WritableFont arial12 = new WritableFont(WritableFont.ARIAL,12,WritableFont.NO_BOLD);		
		WritableSheet s = workbook.createSheet("统计", 0);
		s.getSettings().setVerticalFreeze(1);
		if(hfreeze>0){
			s.getSettings().setHorizontalFreeze(hfreeze);
		}
		WritableCellFormat cwt = new WritableCellFormat(songti12);
		WritableCellFormat wrappedText = new WritableCellFormat(songti12);
		WritableCellFormat cfipc = new WritableCellFormat(arial12,NumberFormats.PERCENT_INTEGER);
		WritableCellFormat cfiry = new WritableCellFormat(NumberFormats.ACCOUNTING_RED_FLOAT);
		wrappedText.setAlignment(Alignment.CENTRE);
	    wrappedText.setWrap(false);
		int i=0;
		ArrayList<Integer> percents = new ArrayList<Integer>();
		ArrayList<Integer> rys = new ArrayList<Integer>();
		for(i=0;i<headers.length;i++){
			String header = headers[i];
			boolean is = false;
			int m = header.indexOf("%");
			if(m>-1){
				percents.add(i);
				header = header.substring(0,m);
				is = true;
			}
			if(is == false){
				m = header.indexOf("￥");
				if(m>-1){
					rys.add(i);
					header = header.substring(0,m);
				}
			}
		    Label l = new Label(i,0,header, wrappedText);
		    s.addCell(l);
		}
		
		i=1;
		Iterator it = data.iterator();
		while(it.hasNext()){
			Object []value = (Object [])it.next();
			for(int j=0;j<value.length;j++){
				if(value[j] instanceof Number){
					if(((Number)value[j]).doubleValue()!=0){
						WritableCellFormat cwt1=cwt;
						boolean is = false;
						if(percents.contains(j)){
							cwt1 = cfipc;
							is = true;
						}
						if(is == false){
							if(rys.contains(j)){
								cwt1 = cfiry;
								is = true;
							}
						}
						jxl.write.Number numCell = new jxl.write.Number(j,i,((Number)value[j]).doubleValue(),cwt1);
						s.addCell(numCell);
					}else{
						Label lblCell = new Label(j,i,"",cwt);
						s.addCell(lblCell);
					}
				}else if(value[j] instanceof String){
					Label l = new Label(j,i,(String)value[j],cwt);
					s.addCell(l);
				}else if (value[j] instanceof Date) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Label l = new Label(j,i,sdf.format(value[j]),cwt);
					s.addCell(l);
				}
			}
			i++;
		}
		
		
		if(footer != null){
			for(int j=0;j<footer.length;j++){
				if(footer[j] instanceof Number){
					WritableCellFormat cwt1=cwt;
					if(rys.contains(j)){
						cwt1 = cfiry;
					}
					jxl.write.Number numCell = new jxl.write.Number(j,i,((Number)footer[j]).doubleValue(),cwt1);
					s.addCell(numCell);
				}else if(footer[j] instanceof String){
					Label l = new Label(j,i,(String)footer[j],cwt);
					s.addCell(l);
				}
			}
		}
		closeWorkbook(workbook,sender,os);
	}
	
	public static WritableWorkbook createWorkbook(Sender sender) throws IOException{
		WorkbookSettings ws = new WorkbookSettings();
		ws.setLocale(Locale.SIMPLIFIED_CHINESE);
		WritableWorkbook workbook = Workbook.createWorkbook(sender.getOut(), ws);
		sender.setWorkbook(workbook);
		return workbook;
	}
	
	public static WritableWorkbook createWorkbook(OutputStream os)throws IOException{
		WorkbookSettings ws = new WorkbookSettings();
		ws.setLocale(Locale.SIMPLIFIED_CHINESE);
		WritableWorkbook workbook = Workbook.createWorkbook(os, ws);
		return workbook;
	}
	
	public static void closeWorkbook(WritableWorkbook workbook,
			Sender sender,OutputStream os)throws IOException{
	    ZipOutputStream zipOut = new ZipOutputStream(os);
	    zipOut.putNextEntry(new org.apache.tools.zip.ZipEntry("统计结果.xls"));
	}
	public static void closeWorkbook(WritableWorkbook workbook)throws IOException{
		workbook.write();
		workbook.close();
	}	
}
