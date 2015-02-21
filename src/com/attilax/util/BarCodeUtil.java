package com.attilax.util;

import java.io.OutputStream;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class BarCodeUtil {
	
	public static void makeBarCoder(String content,int height,int width,OutputStream outputStream){
		
		int codeWidth = 3 + // start guard     
                (7 * 6) + // left bars     
                5 + // middle guard     
                (7 * 6) + // right bars     
                3; // end guard     
        codeWidth = Math.max(codeWidth, width);     
        try {     
            //BitMatrix bitMatrix = new MultiFormatWriter().encode(content,     
            //        BarcodeFormat.EAN_13, codeWidth, height, null);   
            MultiFormatWriter barcodeWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = barcodeWriter.encode(content, BarcodeFormat.CODE_128, codeWidth, height);
           // MatrixToImageWriter.writeToFile(bitMatrix, "png", new File("c://xx.png"));     
           MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
    
        } catch (Exception e) {     
            e.printStackTrace();     
        }
		
	}
	
	public static void makeBarCoder(String content,OutputStream outputStream){
		
		int width = 200;
		int height = 100;
		int codeWidth = 3 + // start guard     
                (7 * 6) + // left bars     
                5 + // middle guard     
                (7 * 6) + // right bars     
                3; // end guard     
        codeWidth = Math.max(codeWidth, width);     
        try {     
            //BitMatrix bitMatrix = new MultiFormatWriter().encode(content,     
            //        BarcodeFormat.EAN_13, codeWidth, height, null);   
            MultiFormatWriter barcodeWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = barcodeWriter.encode(content, BarcodeFormat.CODE_128, codeWidth, height);
           // MatrixToImageWriter.writeToFile(bitMatrix, "png", new File("c://xx.png"));     
           MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
    
        } catch (Exception e) {     
            e.printStackTrace();     
        }
		
	}
	
	public static void main(String[] args){
		//makeBarCoder("6951958000113");
	}

}
