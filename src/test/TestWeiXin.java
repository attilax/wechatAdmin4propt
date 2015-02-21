package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.focusx.entity.weixin.message.response.TextMessage;
import com.focusx.util.MessageUtil;

/**
 * test TestWeiXin.java author:vincente 2013-11-5
 */
public class TestWeiXin extends TestCase {

	protected void setUp() throws Exception {

	}

	/*
	 * 回复文本消息 测试把java对象转换成xml格式的数据 用于回复微信的信息----这里是普通文本消息
	 */
	public void testTextMessageToXml() {

		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName("toUserName");
		textMessage.setFromUserName("fromUserName");
		textMessage.setContent("我的测试");
		textMessage.setCreateTime(1348831860);
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);

		String xml = MessageUtil.textMessageToXml(textMessage);
		System.out.println(xml);

	}

	/**
	 * 
	 * 这里解析xml文件 把发给公共帐号的微信消息(xml格式)解析后放入一个map中
	 */
	public void testParseXml() throws DocumentException, IOException {
		File file = new File("E:\\1.xml");
		// InputStream inputStream = System.in;

		FileInputStream inputStream = new FileInputStream(file);

		Map map = new HashMap();

		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}

		// 释放资源
		inputStream.close();
		inputStream = null;
	}

}
