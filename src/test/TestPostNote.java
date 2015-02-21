package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.focusx.entity.hd.HDNote;
import com.focusx.util.MyHttpUtils;


public class TestPostNote {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<HDNote> noteList = new ArrayList<HDNote>();
		
		HDNote note1 = new HDNote();
		note1.setMemberId("002000005533");
		note1.setCardNo("111111");
		note1.setShopName("中山路店");
		note1.setAddCredit(12);
		note1.setCreditTotal(123);
		note1.setOperType(1);
		note1.setReduceCredit(10);
		note1.setUseCredit(10);
		note1.setRemark("123");
		
		noteList.add(note1);
		
		
		HDNote note2 = new HDNote();
		note2.setMemberId("010100508084");
		note2.setCardNo("122222");
		note2.setShopName("天河路店");
		note2.setAddCredit(12);
		note2.setCreditTotal(123);
		note2.setOperType(1);
		note2.setReduceCredit(10);
		note2.setUseCredit(10);
		note2.setRemark("12322");
		
		noteList.add(note2);
		
		
		String jsonData = JSONObject.toJSONString(noteList);
		
		System.out.println(jsonData);
		
		MyHttpUtils http = new MyHttpUtils();
		String requestUrl = "http://localhost:8080/weixin/msgServlet";
		http.httpRequest(requestUrl, "POST", jsonData);

	}

}
