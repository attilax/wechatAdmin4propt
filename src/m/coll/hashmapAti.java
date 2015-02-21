package m.coll;

import java.util.HashMap;
import java.util.Map;

public class hashmapAti {

	Map map=new HashMap();
	public static void main(String[] args) {
		 String c="c:/aa";
		hashmapAti mp=new hashmapAti();
		mp.add("key=>val");
		System.out.println(mp.get("key"));
	}

	private Object get(String key) {
	 
		return map.get(key);
	}

	private void add(String item) {
		String[] a=item.split("=>");
		map.put(a[0], a[1]);
		
	}

}
