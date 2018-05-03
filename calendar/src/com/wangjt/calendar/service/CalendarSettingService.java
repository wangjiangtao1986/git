package com.wangjt.calendar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangjt.calendar.mysql.dao.mapper.CalendarSettingMapper;
import com.wangjt.calendar.mysql.dao.model.CalendarSetting;
import com.wangjt.calendar.mysql.dao.model.CalendarSettingExample;
import com.wangjt.calendar.mysql.dao.model.CalendarSettingExample.Criteria;

@Transactional
@Service
public class CalendarSettingService {

	@Autowired
	private CalendarSettingMapper calendarSettingMapper;


	public CalendarSetting selectByPrimaryKey(String userId) {
		return calendarSettingMapper.selectByPrimaryKey(userId);
	}

	public int initCalendarSetting(String userId) {
//		super	24	l Y-m-d	Y-m-d D	m/d/Y	08:30	17:30	zh_CN	30	1	1	m/d	
		CalendarSetting calendarSetting = selectByPrimaryKey("default");
		calendarSetting.setUserId(userId);
		return insert(calendarSetting);
	}
	
	public int insert(CalendarSetting calendarSetting) {
		return calendarSettingMapper.insertSelective(calendarSetting);
	}

	public int updateCalendarSetting(CalendarSetting calendarSetting) {
		return calendarSettingMapper.updateByPrimaryKeySelective(calendarSetting);
	}

	public int updateByUserId(CalendarSetting calendarSetting,String userId) {
		calendarSetting.setUserId(userId);
		return updateCalendarSetting(calendarSetting);
	}

	public int countByUserId(String userId) {
		CalendarSettingExample example = new CalendarSettingExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		return calendarSettingMapper.countByExample(example);
	}

	public List<CalendarSetting> getSetting(String userId) {
		List<CalendarSetting> list = new ArrayList<CalendarSetting>();
		CalendarSetting calendarSetting =selectByPrimaryKey(userId);
		list.add(calendarSetting);
		return list;
	}

}
