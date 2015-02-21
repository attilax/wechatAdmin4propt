package m;

import java.io.File;
import java.net.URISyntaxException;

 

public class car {
	
	
	public static void main(String[] args)
	{	
		 System.out.println(filepath());
		
	}

	public  static      String   filepath() 
	{
		String s1 = null;
		try {
			s1 = car.class.getResource("/").toURI().getPath();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File f = new File(s1);
		s1 = f.getParent();
	//	s1 = s1 +File.separatorChar+ "中信银行信用卡电子帐单.eml";
		s1 = s1 +File.separatorChar+ "citiacc.eml";
		File b=new File(s1);
		System.out.println(b.exists());
		System.out.println(b.getPath());
	return s1;
	}
	
	public static void $(String s)
	{	
		 System.out.println("defalut method:"+ s);
		
	}

}
