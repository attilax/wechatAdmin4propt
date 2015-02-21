package com.focusx.util;

import java.io.PipedOutputStream;
import jxl.write.WritableWorkbook;

public class Sender extends Thread{
	private PipedOutputStream out = new PipedOutputStream();
	private WritableWorkbook workbook;
	
	public PipedOutputStream getOut(){
		return out;
	}
	
	public void run(){
		try{
			workbook.write();
			workbook.close();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void setWorkbook(WritableWorkbook workbook) {
		this.workbook = workbook;
	}
}
