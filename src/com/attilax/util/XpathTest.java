package com.attilax.util;

import java.io.IOException;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.attilax.core;

public class XpathTest {

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		DocumentBuilder builder = factory.newDocumentBuilder();
		String xmlfile = "E:\\workspace\\imServer\\src\\com\\FocusIM.cdm.xml";
		Document doc = builder.parse(xmlfile);
		System.out.println(doc.getChildNodes().getLength());
		XPathFactory xFactory = XPathFactory.newInstance();
		XPath xpath = xFactory.newXPath();

		XPathExpression expr = xpath.compile("//name/text()");
		expr = xpath
				.compile("/Model/o:RootObject/c:Children/o:Model/c:Entities");
		expr = xpath.compile("/Model/o:RootObject");
		expr = xpath.compile("//*[local-name()='Entity']");

		// Object result = expr.evaluate(doc, XPathConstants.NODE);
		// Node nd=(Node) result;
		// NodeList nodes=nd.getChildNodes();
		// for (int i = 0; i < nodes.getLength(); i++) {
		// Node item = nodes.item(i);
		// System.out.println(item.getNodeValue());
		// }

		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		System.out.println("--nodes.getLength::" + nodes.getLength());

		for (int i = 0; i < nodes.getLength(); i++) {
			// enty node
			Node item = nodes.item(i);
			String itemId = item.getAttributes().item(0).getNodeValue();
			if (itemId.trim().equals("o150")) {
				core.log(" --item id::" + itemId.toString());
				processEntity(item);

				// yeu 5-6g jeig l ...ziyao first
				break;
			}
			// Node namedItem = item.getAttributes().getNamedItem("Id");
//E:\im\doc\imdbstruts
			// System.out.println(item.getNodeValue());
		}
	}

	private static void processEntity(Node item) {
		//int indexLast = item.getChildNodes().getLength() - 1;
		//item.getChildNodes().getLength()
	//	item.
		Node atts = item.getLastChild();
		NodeList attLi = atts.getChildNodes();
		for (int j = 0; j < attLi.getLength(); j++) {
			Node att = attLi.item(j);
			Node dataitem = att.getLastChild();
			Node dataitemx = dataitem.getLastChild();
			String refId = dataitemx.getAttributes().item(0)
					.getNodeValue();
			core.log(" --refId id::" + refId.toString());
		}
	}

}