/**
 * @author attilax 老哇的爪子
	@since  2014-5-28 下午03:44:18$
 */
package com.attilax;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-5-28 下午03:44:18$
 */
public class ExceptFile4struts implements Filter {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-28 下午03:47:55$
	 */
	@Override
	public void destroy() {
		// attilax 老哇的爪子  下午03:47:55   2014-5-28 
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-28 下午03:47:55$
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// attilax 老哇的爪子  下午03:47:55   2014-5-28 
		
		   //把ServletRequest和ServletResponse转换成真正的类型
        HttpServletRequest req = (HttpServletRequest)request;
        HttpSession session = req.getSession();

        //由于web.xml中设置Filter过滤全部请求，可以排除不需要过滤的url
        String requestURI = req.getRequestURI();
        if(requestURI.endsWith("spr.jsp")){
         //  resume to access    chain.doFilter(request, response);
        	core.log("-- endsWith spr.jsp stop the chainfileter");
        	RequestDispatcher requestDispatcher = request.getRequestDispatcher("spr.jsp");
        	requestDispatcher.forward(request, response);//这两句怎么解释啊？
        	//   ((HttpServletResponse)response).flushBuffer()
            return;
        }
        
//        if(requestURI.endsWith("exp.jsp")){
//            //  resume to access    chain.doFilter(request, response);
//           	core.log("-- endsWith exp.jsp stop the chainfileter");
//           	RequestDispatcher requestDispatcher = request.getRequestDispatcher("exp.jsp");
//           	requestDispatcher.forward(request, response);//这两句怎么解释啊？
//           	//   ((HttpServletResponse)response).flushBuffer()
//               return;
//           }
        
        
        core.log("-- o528a filter is other url...");
        chain.doFilter(request, response);
//        //判断用户是否登录，进行页面的处理
//        if(null == session.getAttribute("user")){
//            //未登录用户，重定向到登录页面
//            ((HttpServletResponse)response).sendRedirect("login.jsp");
//            return;
//        } else {
//            //已登录用户，允许访问
//            chain.doFilter(request, response);
//        }
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-28 下午03:47:55$
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// attilax 老哇的爪子  下午03:47:55   2014-5-28 
		
	}
	//  attilax 老哇的爪子 下午03:44:24   2014-5-28   
	
}

//  attilax 老哇的爪子