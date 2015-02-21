package m;

import java.util.HashSet;
import java.util.Set;

public class t {

	

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String s = new HashSet() {
			{
				add(1);
				add(2);
			}

			public String mys() {
				return "halo world";
			}

		}.mys();
		System.out.println(s);

		Set set = new HashSet() {
			{
				add(1);
				add(2);
			}
		};
		String[] a = {};
		String[] b = {"aa","bb"}; 
		
		  String c=	  new HashSet() {
			  protected String retValue;
				{
				    //something
					this.retValue= "halo world v";
				}
			}.retValue;
			
			System.out.println(c);
			String  $s = new tryX<String>() {

				@Override
				public String item(Object t) throws Exception {
					// TODO Auto-generated method stub
					return null;
				}
			 
		 

		 
		}.$("");
		 

	}

}
