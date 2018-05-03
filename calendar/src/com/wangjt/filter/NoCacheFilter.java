package com.wangjt.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 清除缓存过滤器
 * @author wangjiangtao
 *
 */

public class NoCacheFilter implements Filter {
	
	public NoCacheFilter() {}

	public void init(FilterConfig parm1) throws javax.servlet.ServletException {
		return;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws java.io.IOException, javax.servlet.ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setHeader("Pragma", "No-cache");
		chain.doFilter(request, response);
	}

	public void destroy() {
		return;
	}
}
