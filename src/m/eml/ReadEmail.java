package m.eml;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;

import m.FileService;
//import m.HTMLParserByW3CDOM;
import m.PraseMimeMessage;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sun.swing.text.CountingPrintable;

public class ReadEmail {

	public static void main(String args[]) throws Exception {
		// display(new File("C:\\temp\\message.eml"));
		File f = new File("C:\\tmpx\\pnbnb.eml");
		display(f);
	}

	public static void display(File emlFile) throws Exception {
//		Properties props = System.getProperties();
//		props.put("mail.host", "smtp.dummydomain.com");
//		props.put("mail.transport.protocol", "smtp");
//
//		Session mailSession = Session.getDefaultInstance(props, null);
//		InputStream source = new FileInputStream(emlFile);
//		System.out.println(source.available());
//		MimeMessage message = new MimeMessage(mailSession, source);
//		System.out.println(message);
//
//		System.out.println("Subject : " + message.getSubject());
//		System.out.println("From : " + message.getFrom()[0]);
//		System.out.println("--------------");
//		System.out.println("Body : " + message.getContent());
//		//System.out.println("Bodys : " +  message.getContent().//
//
//		int i = 0;
//		PraseMimeMessage pmm = new PraseMimeMessage(message);
//		System.out.println("Message " + i + " subject: " + pmm.getSubject());
//		System.out.println("Message " + i + " sentdate: " + pmm.getSentDate());
//		System.out
//				.println("Message " + i + " replysign: " + pmm.getReplySign());
//		System.out.println("Message " + i + " hasRead: " + pmm.isNew());
//		System.out.println("Message " + i + " containAttachment: "
//				+ pmm.isContainAttach((Part) message));
//		System.out.println("Message " + i + " form: " + pmm.getFrom());
//		System.out.println("Message " + i + " to: " + pmm.getMailAddress("to"));
//		System.out.println("Message " + i + " cc: " + pmm.getMailAddress("cc"));
//		System.out.println("Message " + i + " bcc: "
//				+ pmm.getMailAddress("bcc"));
//		pmm.setDateFormat("yy��MM��dd�� HH:mm");
//		System.out.println("Message " + i + " sentdate: " + pmm.getSentDate());
//		System.out.println("Message " + i + " Message-ID: "
//				+ pmm.getMessageId());
//		pmm.getMailContent((Part) message);
//		String bodyText = pmm.getBodyText();
//		System.out.println("Message " + i + " bodycontent: \r\n" + bodyText);
//		pmm.setAttachPath("c:\\tmp\\coffeecat1124");
//		pmm.saveAttachMent((Part) message);
//
//		String html_file_name = "c:\\html.txt";
//		FileService.writeFile(html_file_name, bodyText);
//		Document docu = new HTMLParserByW3CDOM().HTMLToXML(html_file_name);
//		NodeList nodelist = docu.getElementsByTagName("table");
//		String[] a = { "������", "������", "��������" };
//		Node transTable = getTransTable(nodelist, a);
//
//		List table = new ArrayList();
//		NodeList trs = transTable.getChildNodes();
//		for (int k = 0; k < trs.getLength(); k++) {
//			List row = new ArrayList();
//			Node tr = trs.item(k);
//
//			NodeList tds = tr.getChildNodes();
//			for (int l = 0; l < tds.getLength(); l++) {
//				Node td = tds.item(l);
//				String tdcon = getTdcon(td);
//				//	if(tdcon.equals("")&& l==0)
//				// break;
//				row.add(tdcon);
//			}
//			if (row.size() != 0)
//				table.add(row);
//
//		}
//
//		printTable(table);

	}

	private static void printTable(List table) {
		for (int i = 0; i < table.size(); i++) {
			List row = (List) table.get(i);
			Boolean isNotTransLine = false;
			String out = "";
			String demo="";
			for (int j = 0; j < row.size(); j++) {
				String td = (String) row.get(j);
				td = td.replaceAll("RMB", "");
				td = td.replaceAll(",", "");
				td = td.trim();
				if (j == 0 && td.equals("")) {
					isNotTransLine = true;
					break;
				}
				if (j == 1)
					continue;
				// if(j==row.size()-1)
				//	 continue;

				if (i == 0 && j == 2) {
					System.out.print("time" + "," +"acc"+",");
					demo=td;
					continue;
				}
				if (i != 0 && j == 2) {
					System.out.print("01:01" + ","  + "jsb,");
					demo=td;
					continue;
				}
				
				//payment
				if (i == 0 && j == 3) {

					System.out.print( td + ",");
					continue;
				}
				// if(i!=0 && j==3)
				// System.out.print("mm"+",");
				if (i != 0 && j == 3) {
					if (td.contains("-")) {
						out = td.replace('-', ' ').trim();
						System.out.print("nopay" + ",");
						continue;
					} else {
						System.out.print(td + ",");
						continue;
					}
				}
				//income culume
				if (i == 0 && j == 4) {
					System.out.print("income,"+demo + ",");
					continue;
				}
				if (i != 0 && j == 4) {
					if (out.length() > 0) {
						System.out.print(out + ","+demo+",");
						continue;
					} else {
						System.out.print("noin,"+demo+",");
						continue;
					}
				}

				System.out.print(td + ",");
			}
			if (isNotTransLine == false)
				System.out.println("");
		}

	}

	private static Node getTransTable(NodeList nodelist, String[] a) {
		for (int i = 0; i < nodelist.getLength(); i++) {
			Node table = nodelist.item(i);
			NodeList trs = table.getChildNodes();
			for (int k = 0; k < trs.getLength(); k++) {
				Node tr = trs.item(k);
				List wordlist = getwordlist(tr);
				if (wordlist.contains(a[0]))
					return table;
			}
		}
		return null;
	}

	private static List getwordlist(Node tr) {

		List a = new ArrayList();
		NodeList tds = tr.getChildNodes();
		for (int i = 0; i < tds.getLength(); i++) {
			Node td = tds.item(i);
			String tdcon = getTdcon(td);
			a.add(tdcon);
		}
		return a;
	}

	private static String getTdcon(Node td) {
		NodeList nodes = td.getChildNodes();
		if (nodes != null && nodes.getLength() >= 1) {
			Node node = nodes.item(0);
			if (node.getNodeName().equals("pre")) {
				return getNodeValue(node);
			} else
				return node.getNodeValue();
		} else
			return "";

	}

	private static String getNodeValue(Node node) {

		String s = "";
		NodeList childNodes = node.getChildNodes();
		if (childNodes != null && childNodes.getLength() >= 1)
			s = childNodes.item(0).getNodeValue();
		return s;
	}

}