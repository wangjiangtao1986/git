package com.wangjt.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.wangjt.calendar.util.StringUtile;
import com.wangjt.intercepter.PageContextHolder;
import com.wangjt.intercepter.PageInfo;

/**
 * 
 * @author wangjiangtao
 * 翻页设置
 *
 */

public class PageContexFilter implements Filter {
	
	public PageContexFilter() {}

	public void init(FilterConfig parm1) throws javax.servlet.ServletException {}

	public void doFilter(
			ServletRequest request, 
			ServletResponse response,
			FilterChain chain
		) throws java.io.IOException, javax.servlet.ServletException {


		String pageNo = request.getParameter("page");

		String pageSize = request.getParameter("limit");
		
		String totalRows = request.getParameter("totalRows");

		String refreshAll = request.getParameter("refreshAll");
		
//		if(!StringUtile.isNull(pageNo) && !StringUtile.isNull(pageSize)) {

			if(StringUtile.isNull(pageNo)){
				pageNo="1";
			}
	
			if(StringUtile.isNull(pageSize)){
				pageSize="10";
			}
			
			PageContextHolder.clearAll();
			PageContextHolder.setPageInfo(new PageInfo());

			PageContextHolder.getPageInfo().setPageNo(pageNo);
			
			PageContextHolder.getPageInfo().setPageSize(pageSize);
			
//			默认 刷新 设置为true 仅 设为 false 不刷新 所有结果集
			if("false".equals(refreshAll)) {
				PageContextHolder.getPageInfo().setRefreshAll("false");

				if(StringUtile.isNull(totalRows)) {
					PageContextHolder.getPageInfo().setTotalRows("0");
				} else {
					PageContextHolder.getPageInfo().setTotalRows(totalRows);
				}
			} else {
				PageContextHolder.getPageInfo().setRefreshAll("true");
			}
			
//		}
		
		chain.doFilter(request, response);
		
		PageContextHolder.clearAll();
		
	}

	public void destroy() {
		return;
	}

}
