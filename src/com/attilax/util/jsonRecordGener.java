package com.attilax.util;

public class jsonRecordGener {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String tmp="{id:\"@idx\",tit:\"标题@idx\"}";
for(int i=0;i<120;i++)
{
	System.out.println(tmp.replaceAll("@idx", String.valueOf(i))+",");
	
	
}
	}

}
