package com.wangjt.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
 * @author wangjiangtao
 * 字符集：全部使用UTF-8
 *
 */

public class CharacterEncodingFilter implements Filter {
	
	private String encoding = "";

	public CharacterEncodingFilter() {}

	public void init(FilterConfig parm1) throws javax.servlet.ServletException {
		
		String encodingParam = parm1.getInitParameter("encoding");
		if (null == encodingParam || encodingParam.equals("")) {
			this.encoding = "UTF-8";
		} else {
			this.encoding = encodingParam;
		}
	}

	public void doFilter(
			ServletRequest request, 
			ServletResponse response,
			FilterChain chain
		) throws java.io.IOException, javax.servlet.ServletException {
		
		request.setCharacterEncoding(this.encoding);
		chain.doFilter(request, response);
	}

	public void destroy() {
		return;
	}

}
