package com.wangjt.calendar.model;

import java.math.BigDecimal;

import com.wangjt.calendar.mysql.dao.model.CalendarEvent;

public class CalendarEventPage {
	private String userId;
    private String description;
    private BigDecimal startNo;
    private BigDecimal endNo;
    private CalendarEvent calendarEvent;
    
    public CalendarEvent getCalendarEvent() {
		return calendarEvent;
	}
	public void setCalendarEvent(CalendarEvent calendarEvent) {
		this.calendarEvent = calendarEvent;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getStartNo() {
		return startNo;
	}
	public void setStartNo(BigDecimal startNo) {
		this.startNo = startNo;
	}
	public BigDecimal getEndNo() {
		return endNo;
	}
	public void setEndNo(BigDecimal endNo) {
		this.endNo = endNo;
	}
}