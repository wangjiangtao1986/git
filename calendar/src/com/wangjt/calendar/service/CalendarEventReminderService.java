package com.wangjt.calendar.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangjt.calendar.mysql.dao.mapper.CalendarEventReminderMapper;
import com.wangjt.calendar.mysql.dao.model.CalendarEvent;
import com.wangjt.calendar.mysql.dao.model.CalendarEventReminder;
import com.wangjt.calendar.mysql.dao.model.CalendarEventReminderExample;
import com.wangjt.calendar.mysql.dao.model.CalendarEventReminderExample.Criteria;
import com.wangjt.calendar.util.Constants;
import com.wangjt.calendar.util.StringUtile;
import com.wangjt.util.JsonHelper;

@Transactional
@Service
public class CalendarEventReminderService {
	
	@Autowired
	private CalendarEventReminderMapper calendarEventReminderMapper;
	
	public void deleteCalendarEventReminder(String eventId) {
		if(eventId != null) {
			CalendarEventReminderExample example = new CalendarEventReminderExample();
			Criteria criteria = example.createCriteria();
			criteria.andEventIdEqualTo(eventId);
			calendarEventReminderMapper.deleteByExample(example);
		}
	}
	
	public String[] createEditCalendarEventReminder(CalendarEventReminder calendarEventReminder){
		int i = calendarEventReminderMapper.insert(calendarEventReminder);
		String[] returnStr = new String[2];
		if(i > 0){
			returnStr[0] = Constants.SUCCESS;
		}else{
			returnStr[0] = Constants.FALSE;
		}
		return returnStr;
	}

	/**
	 * 加载简单事件
	 * @param calendarEvent
	 * @return
	 */
	public String createEditCalendarEventReminder(CalendarEvent calendarEvent){

		try {

			deleteCalendarEventReminder(calendarEvent.getId());

			String alertFlag = calendarEvent.getAlertFlag();
			
			if(!StringUtile.isNull(alertFlag) && alertFlag.startsWith("[")){
				List<Object> jsonList = JsonHelper.Json2List(alertFlag,CalendarEventReminder.class);
				for(int j = 0; j < jsonList.size(); j ++){
					CalendarEventReminder calendarEventReminder = (CalendarEventReminder) jsonList.get(j);
					calendarEventReminder.setEventId(calendarEvent.getId());

			        calendarEventReminder.setId(UUID.randomUUID().toString());
			        calendarEventReminder.setAlerted("false");
			        
					createEditCalendarEventReminder(calendarEventReminder);
				}
			}

			return Constants.SUCCESS;
		} catch(Exception e) {
			e.printStackTrace();
			return Constants.FAILE;
		}
	}

	public List<CalendarEventReminder> loadCalendarEventReminder(String eventId){
		if(!StringUtile.isNull(eventId)){
			CalendarEventReminderExample example = new CalendarEventReminderExample();
			Criteria criteria = example.createCriteria();
			criteria.andEventIdEqualTo(eventId);
			return calendarEventReminderMapper.selectByExample(example);
		} else {
			return null;
		}
	}

}
