package com.test;

public class TestDouble {
	
	public static void main(String[] args) {
		
	}

	public static double getDouble(Integer dayBeforeYesterday, Integer yesterday){
		int p = (int) Math.pow(10, 1);
		double num_double = 0;
		if(dayBeforeYesterday == 0 && yesterday == 0){
			num_double = (double) 0;
		}else if(dayBeforeYesterday != 0 && yesterday == 0){
			num_double = -100;
		}else if(dayBeforeYesterday == 0 && yesterday != 0){
			num_double = 100;
		}else if(dayBeforeYesterday != 0 && yesterday != 0){
			if(dayBeforeYesterday > yesterday){
				num_double = - (double) ((int) ((double)(dayBeforeYesterday-yesterday)*100/dayBeforeYesterday * p)) / p;
			}else {
				num_double = (double) ((int) ((((double)yesterday-dayBeforeYesterday)*100/dayBeforeYesterday) * p)) / p;
			}
		}
		return num_double;
	}
}
