//package m;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.Result;
//import javax.xml.transform.Source;
//import javax.xml.transform.Templates;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import javax.xml.transform.stream.StreamSource;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.w3c.tidy.Configuration;
//import org.w3c.tidy.Tidy;
//import org.xml.sax.SAXException;
//public class HTMLParserByW3CDOM {
// private Templates template;
// /*
//  * 解析网页
//  * XSLTFileName 用于解析网页的样式表文件名
//  * HTMLFileName 待解析的网页文件名
//  * OutputFileName 输出文件名
//  */
// public void parser(String HTMLFileName, String OutputFileName)
// {
//  if( this.template != null){
//   Document doc =  this.HTMLToXML( HTMLFileName ); // 解析网页，返回 W3c Document 文档对象 
//   Transformer(doc, OutputFileName);    // 使用样式表转换 Document 为最终结果
//  }
// }
// 
// /**
//  * 解析网页，转换为 W3C Document 文档对象
//  * @param fileName HTML 网页的文件名
//  * @return   utf-8 W3C Document 文档对象
//  */
// public Document HTMLToXML(String fileName) {
//  Logger log = Logger.getLogger("HTMLToXML");
//  Document doc = null;
//  try{
//   FileInputStream in = new FileInputStream( fileName ); // 打开文件，转换为 UTF-8 编码  
//   InputStreamReader isr = new InputStreamReader(in, "GB2312"); // 源文件编码为 gb2312
//   
//   File tmpNewFile = File.createTempFile("GB2312",".html"); // 转换后的文件，设定编码为 utf-8
//   FileOutputStream out = new FileOutputStream( tmpNewFile ); // 需要将文件转换为字符流
//   OutputStreamWriter osw = new OutputStreamWriter( out , "UTF-8");// 指定目标编码为 utf-8
//   osw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
//   
//   char[] buffer = new char[10240];      // 文件缓冲区
//   int len = 0;           // 使用字符读取方式，循环读取源文件内容
//   while( (len = isr.read(buffer)) !=-1 )     // 转换后写入目标文件中
//   {
//    osw.write( buffer, 0, len);
//   }
//   osw.close();           // 转换完成
//   isr.close();
//   out.close();
//   in.close();
//   
//   if( log.isLoggable( Level.INFO)){
//    log.info("HTML 文档转 UTF-8 编码完成！");
//   }
//   
//   //  设置 tidy ，准备转换
//   Tidy tidy = new Tidy();
//   tidy.setXmlOut(true);    // 输出格式 xml
//   tidy.setDropFontTags(true);   // 删除字体节点
//   tidy.setDropEmptyParas(true);  // 删除空段落
//   tidy.setFixComments(true);   // 修复注释
//   tidy.setFixBackslash(true);   // 修复反斜杆
//   tidy.setMakeClean(true);   // 删除混乱的表示
//   tidy.setQuoteNbsp(false);   // 将空格输出为 &nbsp; 
//   tidy.setQuoteMarks(false);   // 将双引号输出为 &quot;
//   tidy.setQuoteAmpersand(true);  // 将 &amp; 输出为 &
//   tidy.setShowWarnings(false);  // 不显示警告信息
//   tidy.setCharEncoding(Configuration.UTF8); // 文件编码为 UTF8
//   
//   
//   FileInputStream src = new FileInputStream( tmpNewFile ); // 
//   System.out.println(tmpNewFile.getPath());
//   
//   FileOutputStream outXfile = new FileOutputStream( "c:/xml.txt" );
//   doc = tidy.parseDOM( src ,outXfile );
//  // doc = tidy.parseDOM( src ,null ); // 通过 JTidy 将 HTML 网页解析为
//   src.close();           // W3C 的 Document 对象
// //  tmpNewFile.delete();         // 删除临时文件
//   
//   NodeList list = doc.getChildNodes();     // 页面中 DOCTYPE 中可能问题
//   for(int i=0; i<list.getLength(); i++)     // 删除 DOCTYPE 元素
//   {
//    Node node = list.item(i);
//    if( node.getNodeType() == Node.DOCUMENT_TYPE_NODE) // 查找类型定义节点
//    {
//     node.getParentNode().removeChild( node );
//     if( log.isLoggable( Level.INFO)){
//      log.info("已经将文档定义节点删除！" );
//     }
//    }
//   }
//   
//   list = doc.getElementsByTagName("script");    // 脚本中的注释有时有问题
//   for(int i=0; i<list.getLength(); i++){     // 清理 script 元素
//    Element script = (Element) list.item(i);
//    if( script.getFirstChild() != null){
//     if( log.isLoggable( Level.FINEST)){
//      log.finest("删除脚本元素: " + script.getFirstChild().getNodeValue());
//     }
//     script.removeChild( script.getFirstChild());
//    }
//   }
//   
//   list = doc.getElementsByTagName("span");    // sina 中 span 元素有时有问题
//   for(int i=0; i<list.getLength(); i++){     // 清理 span 元素
//    Element span = (Element) list.item(i);
//    span.getParentNode().removeChild( span );
//    if( log.isLoggable( Level.FINEST)){
//     log.finest("删除 span 元素: " );
//    }
//    
//   }
//   
//   list = doc.getElementsByTagName("sohuadcode");   // 清除 sohuadcode 元素
//   for(int i=0; i<list.getLength(); i++){
//    Element sohuadcode = (Element) list.item(i);
//    sohuadcode.getParentNode().removeChild( sohuadcode );
//   }
//   
//   if( log.isLoggable( Level.INFO)){
//    log.info("HTML 文档解析 DOM 完成.");
//   }
//  }
//  catch(Exception e)
//  {
//   log.severe(e.getMessage());
//   e.printStackTrace();
//  }finally
//  {
//   
//  }
//  return doc;
// }
// 
// /**
//  * 解析转换的样式表，保存为模板
//  * @param xsltFileName  样式表文件名
//  * @return     样式表模板对象
//  */
// public Templates setXSLT(String xsltFileName)
// {
//  Logger log = Logger.getLogger( "setXSLT" );
//  File xsltFile = new File( xsltFileName );
//  StreamSource xsltSource = new StreamSource( xsltFile );  // 使用 JAXP 标准方法建立样式表的模板对象 
//  TransformerFactory tff = TransformerFactory.newInstance(); // 可以重复利用这个模板
//  Templates template = null;
//  try {
//   template = tff.newTemplates( xsltSource );
//   if( log.isLoggable( Level.INFO)){
//    log.info("样式表文件 " + xsltFileName + " 解析完成");
//   }
//  } catch (TransformerConfigurationException e) {
//   log.severe( e.getMessage() );
//  }
//  this.template = template;
//  return template;
// }
// 
// /**
//  * 使用样式表转换文档对象，得到最终的结果
//  * @param doc   文档对象
//  * @param outFileName 保存转换结果的文件名
//  */
// private void Transformer(Document doc , String outFileName )
// {
//  Logger log = Logger.getLogger( "Transformer" );
//  try {
//   Source source = new DOMSource( doc );
//   
//   File outFile = new File( outFileName );
//   Result result = new StreamResult( outFile );
//   
//   Transformer transformer = template.newTransformer(); // 使用保存的样式表模板对象
//   transformer.transform(source, result );     // 生成转换器，转换文档对象
//   if( log.isLoggable( Level.INFO)){
//    log.info("转换完成, 请查看 " + outFileName + " 文件。");
//   }
//  } catch (Exception e) {
//   log.severe( e.getMessage() );
//  } 
// }
//}
//
