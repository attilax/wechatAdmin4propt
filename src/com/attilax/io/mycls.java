package com.attilax.io;

import java.net.URL;

//import com.chenlb.mmseg4j.Dictionary;

public class mycls {

	public static Object getpath() {
		 
		return  mycls.class.getClassLoader().getResource("datax").getFile();
	}

}
