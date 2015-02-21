package test;

import com.alibaba.fastjson.JSONObject;
import com.focusx.entity.weixin.message.response.TextMessage;
import com.focusx.util.MessageUtil;

public class TestSendLocationXml {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TextMessage t = new TextMessage();
		//t.setMsgType(Constant.)
		System.out.println(JSONObject.toJSON(t));
		
		System.out.println(MessageUtil.textMessageToXml(t));

	}

}
