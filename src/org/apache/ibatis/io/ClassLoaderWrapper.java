/*     */ package org.apache.ibatis.io;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ 
/*     */ public class ClassLoaderWrapper
/*     */ {
/*     */   ClassLoader defaultClassLoader;
/*     */   ClassLoader systemClassLoader;
/*     */ 
/*     */   ClassLoaderWrapper()
/*     */   {
/*     */     try
/*     */     {
/*  16 */       this.systemClassLoader = ClassLoader.getSystemClassLoader();
/*     */     }
/*     */     catch (SecurityException ignored)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public URL getResourceAsURL(String resource)
/*     */   {
/*  29 */     return getResourceAsURL(resource, getClassLoaders(null));
/*     */   }
/*     */ 
/*     */   public URL getResourceAsURL(String resource, ClassLoader classLoader)
/*     */   {
/*  40 */     return getResourceAsURL(resource, getClassLoaders(classLoader));
/*     */   }
/*     */ 
/*     */   public InputStream getResourceAsStream(String resource)
/*     */   {
/*  50 */     return getResourceAsStream(resource, getClassLoaders(null));
/*     */   }
/*     */ //todox ati
/*     */   public InputStream getResourceAsStream(String resource, ClassLoader classLoader)
/*     */   {
/*  61 */     return getResourceAsStream(resource, getClassLoaders(classLoader));
/*     */   }
/*     */ 
/*     */   public Class<?> classForName(String name)
/*     */     throws ClassNotFoundException
/*     */   {
/*  72 */     return classForName(name, getClassLoaders(null));
/*     */   }
/*     */ 
/*     */   public Class<?> classForName(String name, ClassLoader classLoader)
/*     */     throws ClassNotFoundException
/*     */   {
/*  84 */     return classForName(name, getClassLoaders(classLoader));
/*     */   }
/*     */ 
/*     */   InputStream getResourceAsStream(String resource, ClassLoader[] classLoader)
/*     */   {
/*  95 */     for (ClassLoader cl : classLoader) {
/*  96 */       if (null == cl) {
/*     */         continue;
/*     */       }
/*  99 */       InputStream returnValue = cl.getResourceAsStream(resource);
/*     */ 
/* 102 */       if (null == returnValue) returnValue = cl.getResourceAsStream("/" + resource);
/*     */ 
/* 104 */       if (null != returnValue) return returnValue;
/*     */     }
/*     */ 
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */   URL getResourceAsURL(String resource, ClassLoader[] classLoader)
/*     */   {
/* 121 */     for (ClassLoader cl : classLoader)
/*     */     {
/* 123 */       if (null == cl) {
/*     */         continue;
/*     */       }
/* 126 */       URL url = cl.getResource(resource);
/*     */ 
/* 130 */       if (null == url) url = cl.getResource("/" + resource);
/*     */ 
/* 134 */       if (null != url) return url;
/*     */ 
/*     */     }
/*     */ 
/* 141 */     return null;
/*     */   }
/*     */ 
/*     */   Class<?> classForName(String name, ClassLoader[] classLoader)
/*     */     throws ClassNotFoundException
/*     */   {
/* 155 */     for (ClassLoader cl : classLoader)
/*     */     {
/* 157 */       if (null == cl)
/*     */         continue;
/*     */       try
/*     */       {
/* 161 */         Class c = Class.forName(name, true, cl);
/*     */ 
/* 163 */         if (null != c) return c;
/*     */ 
/*     */       }
/*     */       catch (ClassNotFoundException e)
/*     */       {
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 173 */     throw new ClassNotFoundException("Cannot find class: " + name);
/*     */   }
/*     */ 
/*     */   ClassLoader[] getClassLoaders(ClassLoader classLoader)
/*     */   {
/* 178 */     return new ClassLoader[] { classLoader, this.defaultClassLoader, Thread.currentThread().getContextClassLoader(), super.getClass().getClassLoader(), this.systemClassLoader };
/*     */   }
/*     */ }

/* Location:           D:\mybatis-3.0.4.jar
 * Qualified Name:     org.apache.ibatis.io.ClassLoaderWrapper
 * JD-Core Version:    0.5.4
 */