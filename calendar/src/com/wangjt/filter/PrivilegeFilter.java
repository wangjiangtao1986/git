package com.wangjt.filter;

import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.wangjt.util.User;


public class PrivilegeFilter implements Filter {
	
	FilterConfig config;

	private static Properties exceptUrlProp;
	
	private static Pattern pat;  
	
	public PrivilegeFilter() {}

	public void init(FilterConfig filterConfig) {
		
		config = filterConfig;
		
		initExceptUrl();

		pat = Pattern.compile(this.config.getInitParameter("_except_urlpattern"));  
		
	}
	
	/**
	 * 初始化 privilegeFilter.properties 中的链接
	 */
	private void initExceptUrl(){

		ClassLoader loader = PrivilegeFilter.class.getClassLoader();
		exceptUrlProp = new Properties();
		
		try {
			InputStream propInputStream = loader.getResourceAsStream("privilegeFilter.properties");
			exceptUrlProp.clear();
			exceptUrlProp.load(propInputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws java.io.IOException, javax.servlet.ServletException {
		
		HttpServletRequest hreq = (HttpServletRequest) request;
		 
		String requestUri = hreq.getRequestURI();
	
		String uri = null;
		
		int lastSlash = requestUri.lastIndexOf("/");
		
		uri = requestUri.substring(lastSlash + 1, requestUri.length());
		
		Matcher mat = pat.matcher(uri);  

		if (exceptUrlProp.containsValue(uri)||mat.find()) {
			
		} else {
			User user = (User) hreq.getSession().getAttribute("user");
			if (null == user) {
				user = new User();
				user.setUserId("super");
				hreq.getSession().setAttribute("user", user);
//				try {
//					PrintWriter out = response.getWriter();
//
//					out.println("<script language='javascript'>");
//
//					out.println("window.top.location.href='" + hreq.getContextPath() + "/loginPage.html'");
//
//					out.println("</script>");
//
//					if (out != null)  {
//						out.flush();
//						out = null;
//					}
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//				return;
			}
		}

		chain.doFilter(request, response);
		
	}

	public void destroy() {
		config = null;
	}
}
