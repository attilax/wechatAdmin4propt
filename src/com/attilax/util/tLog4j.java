package com.attilax.util;




public class tLog4j {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		MoodUserIndexService.logger.info("aa");
//		indexControllor4auto.logger.info("aa");
//		System.out.println("");
//		double d=152.21525;
//		System.out.println(convert(d));
		
	//SearchResultProser.	logger_o1f.debug("tttt22");
	System.out.println("F");
	}
	
	
	
public	static   double   convert(double   value){   
        long   l1   =   Math.round(value*100);   //四舍五入   
        double   ret   =   l1/100.00;               //这句不明白，为什么要除100.0呢，而且一定要带上.0   
        return   ret;   
    }

}
