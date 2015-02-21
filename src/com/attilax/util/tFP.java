package com.attilax.util;

import java.util.HashSet;
import java.util.Set;


public class tFP {

	/**
	 * @param args
	 */
	@SuppressWarnings("all")
	public static void main(String[] args) {

		Set primeSet = new HashSet() {
			{
				add(1);
				add(2);
			}
		};

		tryX22 t2 = new tryX22() {
			{
				this.setPara1("xxx");
			}

			public tryX22 setF() {
				// TODO Auto-generated method stub
				return null;
			}

			public tryX22 setF(Object println) {
				// TODO Auto-generated method stub
				return null;
			}

		}.setF(
			 ""
				);

	//	System.out.println(t2.para1);
		System.out.println(primeSet.size());
	//	MoodUserIndexService.logger.info("tt");

	}

}
