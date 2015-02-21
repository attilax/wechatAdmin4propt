/**
 * @author attilax 老哇的爪子
	@since  2014-6-13 上午10:22:37$
 */
package com.attilax.verifycode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-6-13 上午10:22:37$
 */
public class HttpServletResponseImp implements HttpServletResponse {

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#addCookie(javax.servlet.http.Cookie)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void addCookie(Cookie cookie) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#addDateHeader(java.lang.String, long)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void addDateHeader(String name, long date) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#addHeader(java.lang.String, java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void addHeader(String name, String value) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#addIntHeader(java.lang.String, int)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void addIntHeader(String name, int value) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#containsHeader(java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public boolean containsHeader(String name) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#encodeRedirectURL(java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public String encodeRedirectURL(String url) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#encodeRedirectUrl(java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public String encodeRedirectUrl(String url) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#encodeURL(java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public String encodeURL(String url) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#encodeUrl(java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public String encodeUrl(String url) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#sendError(int)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void sendError(int sc) throws IOException {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#sendError(int, java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void sendError(int sc, String msg) throws IOException {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#sendRedirect(java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void sendRedirect(String location) throws IOException {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#setDateHeader(java.lang.String, long)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void setDateHeader(String name, long date) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#setHeader(java.lang.String, java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void setHeader(String name, String value) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#setIntHeader(java.lang.String, int)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void setIntHeader(String name, int value) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#setStatus(int)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void setStatus(int sc) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponse#setStatus(int, java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void setStatus(int sc, String sm) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#flushBuffer()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void flushBuffer() throws IOException {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#getBufferSize()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public int getBufferSize() {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#getCharacterEncoding()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public String getCharacterEncoding() {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#getContentType()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public String getContentType() {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#getLocale()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public Locale getLocale() {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#getOutputStream()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#getWriter()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public PrintWriter getWriter() throws IOException {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#isCommitted()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public boolean isCommitted() {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#reset()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void reset() {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#resetBuffer()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void resetBuffer() {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#setBufferSize(int)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void setBufferSize(int arg0) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#setCharacterEncoding(java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void setCharacterEncoding(String arg0) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#setContentLength(int)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void setContentLength(int arg0) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#setContentType(java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void setContentType(String arg0) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#setLocale(java.util.Locale)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:22:37$
	 */
	@Override
	public void setLocale(Locale arg0) {
		// attilax 老哇的爪子  上午10:22:37   2014-6-13 

	}
	//  attilax 老哇的爪子 上午10:22:37   2014-6-13   

	@Override
	public String getHeader(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getHeaderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getHeaders(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		return 0;
	}
}

//  attilax 老哇的爪子