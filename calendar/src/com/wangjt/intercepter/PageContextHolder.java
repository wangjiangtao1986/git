package com.wangjt.intercepter;

import com.wangjt.calendar.util.StringUtile;

/**
 * 
 * 利用本地化变量传递翻页信息
 * @author wangjiangtao
 *
 */
public class PageContextHolder {
	
	private static final ThreadLocal<PageInfo> pageInfo = new ThreadLocal<PageInfo>();// 总记录数
	
	public static void setPageInfo(PageInfo _pageInfo) {

		PageContextHolder.clearAll();
		if(!StringUtile.isNull(_pageInfo.getPageNo()) && !StringUtile.isNull(_pageInfo.getPageSize())) {

//			默认 刷新 设置为true 仅 设为 false 不刷新 所有结果集
			if("false".equals(_pageInfo.getRefreshAll())) {
				_pageInfo.setRefreshAll("false");

				if(StringUtile.isNull(_pageInfo.getTotalRows())) {
					_pageInfo.setTotalRows(_pageInfo.getPageSize());
				} else {
					_pageInfo.setTotalRows(_pageInfo.getTotalRows());
				}
			} else {
				_pageInfo.setRefreshAll("true");
			}
		}
		
		pageInfo.set(_pageInfo);
	}

	public static PageInfo getPageInfo() {
		return ((PageInfo) pageInfo.get());
	}
	
	public static void clearAll() {
		pageInfo.remove();
	}

	
}