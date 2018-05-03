package com.wangjt.intercepter;


/**
 * mybatis 分页对象
 * @author wangjiangtao
 *
 */
public class PageInfo {

	private String totalRows ;// 总记录数

	private String pageNo;// 当前页码

	private String pageSize;// 每页行数

	private String refreshAll;// 刷新全部数据

	public String getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(String totalRows) {
		this.totalRows = totalRows;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getRefreshAll() {
		return refreshAll;
	}

	public void setRefreshAll(String refreshAll) {
		this.refreshAll = refreshAll;
	}
}