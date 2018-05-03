package com.wangjt.calendar.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangjt.calendar.mysql.dao.mapper.CalendarMd5Mapper;
import com.wangjt.calendar.mysql.dao.model.CalendarMd5;

@Service
public class CalendarMd5Service {

	@Autowired
	private CalendarMd5Mapper calendarMd5Mapper;
	
	public static Logger logger = Logger.getLogger(CalendarMd5Service.class);


	public int createCalendarMd5(CalendarMd5 record) {
		return calendarMd5Mapper.insert(record);
	}

	public int updateByPrimaryKeySelective(CalendarMd5 record) {
		return calendarMd5Mapper.updateByPrimaryKeySelective(record);
	}

	public CalendarMd5 getCalendarMd5MapperById(String signature) {
		return calendarMd5Mapper.selectByPrimaryKey(signature);
	}
	
	public int deleteCalendarMd5(CalendarMd5 record){
		return calendarMd5Mapper.deleteByPrimaryKey(record.getSignature());
	}
	
}
