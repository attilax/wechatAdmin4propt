/**
 *           .--.
     .-._;.--.;_.-.
    (_.'_..--.._'._)
     /.' . 60 . '.\
    // .      / . \\
   |; .      /   . |;
   ||45    ()    15||
   |; .          . |;
    \\ .        . //
     \'._' 30 '_.'/
 jgs  '-._'--'_.-'
          `""` 
 */

package com.attilax.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

 

import org.apache.log4j.Logger;


@SuppressWarnings("all")
public class tTryx {
	public static Logger logger = Logger.getLogger(MoodUserIndexService.class);

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) {

		// @Cleanup
		// InputStream in = new FileInputStream(args[0]);
		// try {
		// @Cleanup OutputStream out = new FileOutputStream(args[1]);
		// } catch (FileNotFoundException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// write file code goes here

		// String r = (String) tryX.tryStart(new tryX() {
		//
		// // public Object defaultReturnValue=" def value ";
		//
		// @Override
		// public Object item(Object t) throws Exception {
		// // this.defaultReturnValue = " my def v ";
		// String s = "x";
		// String[] a = s.split(",");
		// String s2 = a[5];
		// return " xxok xx";
		// }
		//
		// public Object log(Exception e) {
		// logger.info(god.getTrace(e));
		// return null;
		// // this. ==tryx
		// }
		// });

		String rx = (String) (new tryX() {
			@Override
			public Object item(Object t) throws Exception {
				String s = "x";
				String[] a = s.split(",");
				String s2 = a[5];
				return " xxok xx";
			}
		}).$(" my def xx");
		
		
		;

		System.out.println("--re:" + rx);

	}

}
