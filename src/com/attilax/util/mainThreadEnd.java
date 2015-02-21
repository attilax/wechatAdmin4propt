package com.attilax.util;

public class mainThreadEnd {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 
		Thread td_commit = new Thread(new Runnable() {
			public void run() {
				int n=0;
				 while(true){
					 n++;
					 try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						 
						e.printStackTrace();
					}
					 System.out.println("tttt");
					 if(n>3)break;
				 }
			}
		});
		td_commit.setPriority(Thread.MAX_PRIORITY);
		td_commit.setName("--thd td_commit  ");
		td_commit.start();
		
		
//		while(true)
//		{
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				 
//				e.printStackTrace();
//			}
//		}
		try {
			td_commit.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	System.out.println("--f");

	}

}
