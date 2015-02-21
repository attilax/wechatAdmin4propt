package m;

import java.io.Serializable;
import java.lang.ref.SoftReference;

import com.attilax.io.filex;

 
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@utf编码
public class tCache {

	public static void main(String[] args) throws CacheException {
		
		// 所以大概步骤为：
		// 第一步：生成CacheManager对象
		// 第二步：生成Cache对象
		// 第三步：向Cache对象里添加由key,value组成的键值对的Element元素
		CacheManager manager = CacheManager.create();
		// Cache cache = new Cache("test", 1, true, false, 5, 2);
		// manager.addCache(cache);
		//
		Cache cache = manager.getCache("mycache");

		// Element element = new Element("key1", "value1");
		// cache.put(element );
		// cache.put(new Element("key2", "v2") );
		for (int n = 0; n < 20; n++) {
			System.out.println(" now size:" + n);
			String f =  filex.read("c:\\main.dmg", "gbk");		
			String string = "v" + f + n;
			SoftReference sr=new SoftReference (string);
//			ObjectWrap ow=new ObjectWrap();
//			ow.item=sr;
			cache.put(new Element("key" + n, (Serializable) sr));
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("ff");
		// cache.flush();

		//System.out.println("---------" + cache.get("key2").getValue());

//		try {
//			Thread.sleep(1000 * 6);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		System.out.println("---------" + cache.get("key2").getValue());

		// Element element2 = cache.get("key1");
	}

}
