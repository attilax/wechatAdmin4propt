/**
 * @author attilax 老哇的爪子
	@since  2014-6-13 上午10:21:54$
 */
package com.attilax.verifycode;

import java.io.IOException;
import java.util.Enumeration;

import javax.el.ELContext;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-6-13 上午10:21:54$
 */
public class PageContextImp extends PageContext {

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.PageContext#forward(java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public void forward(String relativeUrlPath) throws ServletException,
			IOException {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.PageContext#getException()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public Exception getException() {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.PageContext#getPage()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public Object getPage() {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.PageContext#getRequest()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public ServletRequest getRequest() {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.PageContext#getResponse()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public ServletResponse getResponse() {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.PageContext#getServletConfig()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public ServletConfig getServletConfig() {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.PageContext#getServletContext()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public ServletContext getServletContext() {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.PageContext#getSession()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public HttpSession getSession() {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.PageContext#handlePageException(java.lang.Exception)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public void handlePageException(Exception e) throws ServletException,
			IOException {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.PageContext#handlePageException(java.lang.Throwable)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public void handlePageException(Throwable t) throws ServletException,
			IOException {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.PageContext#include(java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public void include(String relativeUrlPath) throws ServletException,
			IOException {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.PageContext#include(java.lang.String, boolean)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public void include(String relativeUrlPath, boolean flush)
			throws ServletException, IOException {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.PageContext#initialize(javax.servlet.Servlet, javax.servlet.ServletRequest, javax.servlet.ServletResponse, java.lang.String, boolean, int, boolean)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public void initialize(Servlet servlet, ServletRequest request,
			ServletResponse response, String errorPageURL,
			boolean needsSession, int bufferSize, boolean autoFlush)
			throws IOException, IllegalStateException, IllegalArgumentException {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.PageContext#release()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public void release() {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspContext#findAttribute(java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public Object findAttribute(String arg0) {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspContext#getAttribute(java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public Object getAttribute(String arg0) {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspContext#getAttribute(java.lang.String, int)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public Object getAttribute(String arg0, int arg1) {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspContext#getAttributeNamesInScope(int)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public Enumeration<String> getAttributeNamesInScope(int arg0) {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspContext#getAttributesScope(java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public int getAttributesScope(String arg0) {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspContext#getELContext()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public ELContext getELContext() {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspContext#getExpressionEvaluator()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public ExpressionEvaluator getExpressionEvaluator() {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspContext#getOut()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public JspWriter getOut() {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspContext#getVariableResolver()
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public VariableResolver getVariableResolver() {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspContext#removeAttribute(java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public void removeAttribute(String arg0) {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspContext#removeAttribute(java.lang.String, int)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public void removeAttribute(String arg0, int arg1) {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspContext#setAttribute(java.lang.String, java.lang.Object)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public void setAttribute(String arg0, Object arg1) {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 

	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspContext#setAttribute(java.lang.String, java.lang.Object, int)
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-13 上午10:21:55$
	 */
	@Override
	public void setAttribute(String arg0, Object arg1, int arg2) {
		// attilax 老哇的爪子  上午10:21:55   2014-6-13 

	}
	//  attilax 老哇的爪子 上午10:21:55   2014-6-13   
}

//  attilax 老哇的爪子