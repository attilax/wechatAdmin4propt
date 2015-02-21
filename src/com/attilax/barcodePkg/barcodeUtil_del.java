package com.attilax.barcodePkg;
///**
// * @author attilax 老哇的爪子
//	@since  2014-4-24 上午10:17:17$
// */
//package com.attilax.barcodePkg;
//
//import java.io.OutputStream;
//
//import com.focusx.util.BarCodeUtil;
//
///**
// * @author attilax
// *
// */
//public class barcodeUtil {
//
//	/**
//	@author attilax 老哇的爪子
//		@since  2014-4-24 上午10:17:17$
//	
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// 上午10:17:17   2014-4-24 
//	String s="D:\\Users\\attilax\\Desktop\\barcode.txt";
//		
//	String barCode = request.getParameter("barCode");
//		
//		String strHeight = null;//request.getParameter("height");
//		String strWidth = null;//request.getParameter("width");
//		
////		response.setCharacterEncoding("UTF-8");
////		request.setCharacterEncoding("UTF-8");
//		
//		if(barCode != null && !"".equals(barCode)){
//			
//			
//		//	response.setContentType("image/png");
//			
//			OutputStream output = response.getOutputStream();
//			
//			try{
//				if(strHeight != null && !"".equals(strHeight)
//						&& strWidth != null && !"".equals(strWidth)){
//					BarCodeUtil.makeBarCoder(barCode,Integer.parseInt(strHeight),Integer.parseInt(strWidth),output);
//				}
//				else{
//					BarCodeUtil.makeBarCoder(barCode,output);
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			output.flush();
//			
//			output.close();
//
//	}
//
//}
