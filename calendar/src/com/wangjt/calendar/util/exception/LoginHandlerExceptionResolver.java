package com.wangjt.calendar.util.exception;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class LoginHandlerExceptionResolver extends
		SimpleMappingExceptionResolver {
	protected ModelAndView doResolveException(
			javax.servlet.http.HttpServletRequest httpServletRequest,
			javax.servlet.http.HttpServletResponse httpServletResponse,
			java.lang.Object o, java.lang.Exception e) {
		httpServletRequest.setAttribute("ex", e);
		e.printStackTrace();
		return super.doResolveException(httpServletRequest,
				httpServletResponse, o, e);
	}
}
