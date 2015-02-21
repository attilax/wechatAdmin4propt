package com.attilax.util;

import java.util.Random;

public class randomx {

	public randomx() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
	    Random random = new Random();
        for(int i = 0; i < 3;i++) {
        	//1820344736
        	System.out.println(random.nextInt());
            System.out.println(Math.abs(random.nextInt())%100);
        }
 
//获得的随机数有正有负的，用Math.abs使获取数据范围为非负数
	}

	public static int random(int min, int max) {
		 Random random = new Random();
		return Math.abs(random.nextInt())%100;
	}
	
	public static int random( int max) {
		 Random random = new Random();
		return Math.abs(random.nextInt())%(max+1);
	}

}
