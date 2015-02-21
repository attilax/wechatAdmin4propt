/*     */ package org.apache.ibatis.io;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class Resources
/*     */ {
/*  14 */   private static ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();
/*     */   private static Charset charset;
/*     */ 
/*     */   public static ClassLoader getDefaultClassLoader()
/*     */   {
/*  31 */     return classLoaderWrapper.defaultClassLoader;
/*     */   }
/*     */ 
/*     */   public static void setDefaultClassLoader(ClassLoader defaultClassLoader)
/*     */   {
/*  40 */     classLoaderWrapper.defaultClassLoader = defaultClassLoader;
/*     */   }
/*     */ 
/*     */   public static URL getResourceURL(String resource)
/*     */     throws IOException
/*     */   {
/*  51 */     return classLoaderWrapper.getResourceAsURL(resource);
/*     */   }
/*     */ 
/*     */   public static URL getResourceURL(ClassLoader loader, String resource)
/*     */     throws IOException
/*     */   {
/*  63 */     URL url = classLoaderWrapper.getResourceAsURL(resource, loader);
/*  64 */     if (url == null) throw new IOException("Could not find resource " + resource);
/*  65 */     return url;
/*     */   }
/*     */ 
/*     */   public static InputStream getResourceAsStream(String resource)
/*     */     throws IOException
/*     */   {
/*  76 */     return getResourceAsStream(null, resource);
/*     */   }
/*     */ 
/*     */   public static InputStream getResourceAsStream(ClassLoader loader, String resource)
/*     */     throws IOException
/*     */   {
/*  88 */     InputStream in = classLoaderWrapper.getResourceAsStream(resource, loader);
/*  89 */     if (in == null) throw new IOException("Could not find resource " + resource);
/*  90 */     return in;
/*     */   }
/*     */ 
/*     */   public static Properties getResourceAsProperties(String resource)
/*     */     throws IOException
/*     */   {
/* 101 */     Properties props = new Properties();
/* 102 */     InputStream in = getResourceAsStream(resource);
/* 103 */     props.load(in);
/* 104 */     in.close();
/* 105 */     return props;
/*     */   }
/*     */ 
/*     */   public static Properties getResourceAsProperties(ClassLoader loader, String resource)
/*     */     throws IOException
/*     */   {
/* 117 */     Properties props = new Properties();
/* 118 */     InputStream in = getResourceAsStream(loader, resource);
/* 119 */     props.load(in);
/* 120 */     in.close();
/* 121 */     return props;
/*     */   }
/*     */ 
/*     */   public static Reader getResourceAsReader(String resource)
/*     */     throws IOException
/*     */   {
/*     */     Reader reader;
/*     */  //   Reader reader;
/* 133 */     if (charset == null)
/* 134 */       reader = new InputStreamReader(getResourceAsStream(resource));
/*     */     else {
/* 136 */       reader = new InputStreamReader(getResourceAsStream(resource), charset);
/*     */     }
/* 138 */     return reader;
/*     */   }
/*     */ 
/*     */   public static Reader getResourceAsReader(ClassLoader loader, String resource)
/*     */     throws IOException
/*     */   {
/*     */     Reader reader;
/*     */   //  Reader reader;
/* 151 */     if (charset == null)
/* 152 */       reader = new InputStreamReader(getResourceAsStream(loader, resource));
/*     */     else {
/* 154 */       reader = new InputStreamReader(getResourceAsStream(loader, resource), charset);
/*     */     }
/* 156 */     return reader;
/*     */   }
/*     */ 
/*     */   public static File getResourceAsFile(String resource)
/*     */     throws IOException
/*     */   {
/* 167 */     return new File(getResourceURL(resource).getFile());
/*     */   }
/*     */ 
/*     */   public static File getResourceAsFile(ClassLoader loader, String resource)
/*     */     throws IOException
/*     */   {
/* 179 */     return new File(getResourceURL(loader, resource).getFile());
/*     */   }
/*     */ 
/*     */   public static InputStream getUrlAsStream(String urlString)
/*     */     throws IOException
/*     */   {
/* 190 */     URL url = new URL(urlString);
/* 191 */     URLConnection conn = url.openConnection();
/* 192 */     return conn.getInputStream();
/*     */   }
/*     */ 
/*     */   public static Reader getUrlAsReader(String urlString)
/*     */     throws IOException
/*     */   {
/*     */     Reader reader;
/*     */    // Reader reader;
/* 204 */     if (charset == null)
/* 205 */       reader = new InputStreamReader(getUrlAsStream(urlString));
/*     */     else {
/* 207 */       reader = new InputStreamReader(getUrlAsStream(urlString), charset);
/*     */     }
/* 209 */     return reader;
/*     */   }
/*     */ 
/*     */   public static Properties getUrlAsProperties(String urlString)
/*     */     throws IOException
/*     */   {
/* 220 */     Properties props = new Properties();
/* 221 */     InputStream in = getUrlAsStream(urlString);
/* 222 */     props.load(in);
/* 223 */     in.close();
/* 224 */     return props;
/*     */   }
/*     */ 
/*     */   public static Class<?> classForName(String className)
/*     */     throws ClassNotFoundException
/*     */   {
/* 235 */     return classLoaderWrapper.classForName(className);
/*     */   }
/*     */ 
/*     */   public static Charset getCharset() {
/* 239 */     return charset;
/*     */   }
/*     */ 
/*     */   public static void setCharset(Charset charset) {
/* 243 */     charset = charset;
/*     */   }
/*     */ }

/* Location:           D:\mybatis-3.0.4.jar
 * Qualified Name:     org.apache.ibatis.io.Resources
 * JD-Core Version:    0.5.4
 */