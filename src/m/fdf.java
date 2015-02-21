//package com.demo.javamail.servlet;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.StringWriter;
//import java.util.ArrayList;
//import java.util.Properties;
//
//import javax.mail.Part;
//import javax.mail.Session;
//import javax.mail.internet.MimeMessage;
//import javax.servlet.ServletConfig;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.velocity.Template;
//import org.apache.velocity.app.Velocity;
//import org.apache.velocity.context.Context;
//import org.apache.velocity.servlet.VelocityServlet;
//
//import com.demo.javamail.mail.Attarchment;
//import com.demo.javamail.mail.PraseMimeMessage;
//import com.demo.javamail.util.CharConverter;
//import com.demo.javamail.util.StringUtil;
//
//@SuppressWarnings("deprecation")
//public class ModleServlet extends VelocityServlet {
//
// private static final long serialVersionUID = 1L;
//
// protected Properties loadConfiguration(ServletConfig config)
//   throws IOException, FileNotFoundException {
//
//  Properties p = new Properties();
//
//  String path = config.getServletContext().getRealPath("/");
//
//  if (path == null) {
//   System.out
//     .println(" SampleServlet.loadConfiguration() : unable to "
//       + "get the current webapp root.  Using '/'. Please fix.");
//   path = "/";
//  }
//
//  p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path);
//
//  p.setProperty("runtime.log", path + "velocity.log");
//  return p;
// }
//
// public Template handleRequest(HttpServletRequest request,
//   HttpServletResponse response, Context ctx) {
//  
//  System.out.println("Hello  everyone");
//  System.out.println("sfdsfsd");
//  System.out.println(ctx);
//  System.out.println(ctx.toString());
//  System.out.println(request.getContextPath());
//  
//  
//  Template template = null;
//        String subject = null;
//        String sentdate = null;
//        boolean replysign = false;
//        boolean hasRead = false;
//        boolean containAttachment = false;
//        String form = null;
//        String to = null;
//        String cc = null;
//        String bcc = null;
//        String messageID = null;
//        String bodycontent = null;
//        try {
//   
//   System.out.print("mailsend");
//   File emailFile = new File("E://calvinwei@olymtech.net.eml");
//   System.out.println("emailFile");
//   
//   InputStream is = new FileInputStream(emailFile);
//   Session mailSession = Session.getDefaultInstance(System
//     .getProperties(), null);
//   MimeMessage mm = new MimeMessage(mailSession, is);
//   System.out.println("Messages's length: " + mm);
//   PraseMimeMessage pmm = null;
//   pmm = new PraseMimeMessage((MimeMessage) mm);
//   System.out.println("Message " + " subject: " + pmm.getSubject());
//   System.out.println("Message " + " sentdate: " + pmm.getSentDate());
//   System.out.println("Message " + " replysign: " + pmm.getReplySign());
//   System.out.println("Message " + " hasRead: " + pmm.isNew());
//   System.out.println("Message " + " containAttachment: " + pmm.isContainAttach((Part) mm));
//   System.out.println("Message " + " form: " + pmm.getFrom());
//   System.out.println("Message " + " to: " + pmm.getMailAddress("to"));
//   System.out.println("Message " + " cc: " + pmm.getMailAddress("cc"));
//   System.out.println("Message " + " bcc: " + pmm.getMailAddress("bcc"));
//   pmm.setDateFormat("yy��MM��dd�� HH:mm");
//   System.out.println("Message " + " sentdate: " + pmm.getSentDate());
//   System.out.println("Message " + " Message-ID: "+ pmm.getMessageId());
//   pmm.getMailContent((Part) mm);
//   System.out.println("Message " + " bodycontent: \r\n"+ pmm.getBodyText());
//   pmm.attachName((Part)mm);
//   subject = pmm.getSubject();
//   sentdate = pmm.getSentDate();
//   replysign = pmm.getReplySign();
//   hasRead = pmm.isNew();
//   containAttachment = pmm.isContainAttach((Part) mm);
//   form = pmm.getFrom();
//   to = pmm.getMailAddress("to");
//   cc = pmm.getMailAddress("cc");
//   bcc = pmm.getMailAddress("bcc");
//   messageID = pmm.getMessageId();
//   bodycontent = pmm.getBodyText();
//   ArrayList attarchList = (ArrayList)pmm.attachName((Part) mm);
//   
//   
//   Velocity.init();
//   ctx.put("name", CharConverter.GBKtoISO("calvinwei"));
//   ctx.put("subject", CharConverter.GBKtoISO(subject));
//   ctx.put("replysign", replysign);
//   ctx.put("hasRead", hasRead);
//   ctx.put("containAttachment",containAttachment);
//   ctx.put("form", CharConverter.GBKtoISO(StringUtil.toHTMLString(form)));
//   ctx.put("to", CharConverter.GBKtoISO(StringUtil.toHTMLString(to)));
//   ctx.put("bcc", CharConverter.GBKtoISO(bcc));
//   ctx.put("sentdate", sentdate);
//   ctx.put("messageID", CharConverter.GBKtoISO(messageID));
//   ctx.put("bodycontent", CharConverter.GBKtoISO(StringUtil.toHTMLString(bodycontent)));
////   ctx.put("Attarchment", new Attarchment());
//   ctx.put("attarchList", attarchList);
//            //��Ӹ���
//   for(int i=0;i<attarchList.size();i++){
//    Attarchment attarchment= (Attarchment)attarchList.get(i);
//    System.out.println("attarchment.getFileName"+attarchment.getFileName());
////    ctx.put("attarchment" + i, attarchment);
//   }
//   ctx.put("attarcCount", attarchList.size());
//   // template = Velocity.getTemplate("/web/sample.html");
//   
//   // template = Velocity.getTemplate("/template/mailModle.html");
//   template = Velocity.getTemplate("/template/readmail.html");
//   
//   StringWriter writer = new StringWriter();
//   template.merge(ctx, writer);
//   System.out.println(writer.toString());
//
//  } catch (FileNotFoundException e) {
//   // TODO Auto-generated catch block
//   e.printStackTrace();
//  } catch (Exception e) {
//   // TODO Auto-generated catch block
//   e.printStackTrace();
//  }
//  return template;
//
// }
//}