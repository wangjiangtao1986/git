package com.wangjt.calendar.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangjt.calendar.mysql.dao.mapper.CalendarUserMapper;
import com.wangjt.calendar.mysql.dao.model.CalendarUser;

@Service
public class CalendarUserService {

	@Autowired
	private CalendarUserMapper calendarUserMapper;
	
	public static Logger logger = Logger.getLogger(CalendarUserService.class);


	public int createCalendarUser(CalendarUser record) {
		return calendarUserMapper.insert(record);
	}

	public int updateByPrimaryKeySelective(CalendarUser record) {
		return calendarUserMapper.updateByPrimaryKeySelective(record);
	}

	public CalendarUser getCalendarUserMapperById(String userId) {
		return calendarUserMapper.selectByPrimaryKey(userId);
	}
}
