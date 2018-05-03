package com.wangjt.calendar.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangjt.calendar.model.CalendarEventPage;
import com.wangjt.calendar.mysql.dao.mapper.CalendarEventMapper;
import com.wangjt.calendar.mysql.dao.model.CalendarEvent;
import com.wangjt.calendar.mysql.dao.model.CalendarEventExample;
import com.wangjt.calendar.mysql.dao.model.CalendarEventExample.Criteria;
import com.wangjt.calendar.util.Constants;
import com.wangjt.calendar.util.StringUtile;

@Transactional
@Service
public class CalendarEventService {
	
	@Autowired
	private CalendarEventMapper calendarEventMapper;
	
	/**
	 * 根据日程分类批量删除日程
	 * @param calendarId
	 */
	public void deleteEventByCalendarType(String calendarId){
		if(calendarId != null){
			CalendarEventExample example = new CalendarEventExample();
			Criteria criteria = example.createCriteria();
			criteria.andCalendarIdEqualTo(calendarId);
			calendarEventMapper.deleteByExample(example);
		}
	}
	
	/**
	 * 根据主键删除日程
	 * @param eventId
	 */
	public void deleteEvent(String eventId){
		if(eventId != null){
			calendarEventMapper.deleteByPrimaryKey(eventId);
		}
	}
	
	/**
	 * 根据主键动态跟新日程
	 * @param calendarEvent
	 * @return
	 */
	public int updateByPKSelective(CalendarEvent calendarEvent){
		return calendarEventMapper.updateByPrimaryKeySelective(calendarEvent);
	}

	/**
	 * 根据主键动态跟新日程
	 * @param calendarEvent
	 * @return
	 */
	public String[] updateEvent(CalendarEvent calendarEvent){
		int i = this.updateByPKSelective(calendarEvent);
		String[] returnStr = new String[3];
		if(i>0){
			returnStr[0] = Constants.SUCCESS;
			returnStr[1] = calendarEvent.getId();
			returnStr[2] = "";
		}else{
			returnStr[0] = Constants.FALSE;
		}
		return returnStr;
	}

	/**
	 * 创建新日程
	 * @param calendarEvent
	 * @return
	 */
	public String[] createEditEvent(CalendarEvent calendarEvent){
		
        String eventId = UUID.randomUUID().toString(); 
        
		calendarEvent.setId(eventId);

		calendarEvent.setCreateDate(StringUtile.nowDateFmt());
		calendarEvent.setUpdateDate(StringUtile.nowDateFmt());
		
		int i = calendarEventMapper.insert(calendarEvent);
		String[] returnStr = new String[2];
		if(i > 0){
			returnStr[0] = Constants.SUCCESS;
			returnStr[1] = eventId;
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
	public List<CalendarEvent> loadSimpleEvent(CalendarEvent calendarEvent){

		List<String> list = new ArrayList<String>();
		list.add("no");
		list.add("exception");
		
		if(null!=calendarEvent && !StringUtile.isNull(calendarEvent.getUserId())){
			CalendarEventExample example = new CalendarEventExample();
			Criteria criteria = example.createCriteria();
			criteria.andUserIdEqualTo(calendarEvent.getUserId());
			if(!StringUtile.isNull(calendarEvent.getRepeatType())) {
				criteria.andRepeatTypeIn(list);
			}
			criteria.andStartDayGreaterThanOrEqualTo(calendarEvent.getStartDay());

//            + "AND (calendar_event.startTime >= '" + paramsx.Request["startDay"] + " 00:00' 
//					OR calendar_event.endTime <= '" + paramsx.Request["endDay"] + " 23:59' "
//            + "OR (calendar_event.startTime < '" + paramsx.Request["startDay"] + " 00:00' 
//			AND calendar_event.endTime > '" + paramsx.Request["endDay"] + " 23:59'))"

			Criteria criteria2 = example.createCriteria();
			criteria2.andUserIdEqualTo(calendarEvent.getUserId());
			if(!StringUtile.isNull(calendarEvent.getRepeatType())) {
				criteria2.andRepeatTypeIn(list);
			}
			criteria2.andEndDayLessThanOrEqualTo(calendarEvent.getEndDay());
			

			Criteria criteria3 = example.createCriteria();
			criteria3.andUserIdEqualTo(calendarEvent.getUserId());
			if(!StringUtile.isNull(calendarEvent.getRepeatType())) {
				criteria3.andRepeatTypeIn(list);
			}
			criteria3.andStartDayLessThanOrEqualTo(calendarEvent.getStartDay());
			criteria3.andEndDayGreaterThanOrEqualTo(calendarEvent.getEndDay());

			example.or(criteria2);
			example.or(criteria3);

			example.setOrderByClause(" START_TIME, END_TIME DESC");
			return calendarEventMapper.selectByExample(example);
		} else {
			return null;
		}
	}

	/**
	 * 加载重复事件
	 * @param calendarEvent
	 * @return
	 */
	public List<CalendarEvent> loadRepeatEvent(CalendarEvent calendarEvent){

		List<String> list = new ArrayList<String>();
		list.add("no");
		list.add("exception");
		
		if(null!=calendarEvent && !StringUtile.isNull(calendarEvent.getUserId())){
			CalendarEventExample example = new CalendarEventExample();
			Criteria criteria = example.createCriteria();
			criteria.andUserIdEqualTo(calendarEvent.getUserId());

			criteria.andRepeatTypeNotIn(list);
			example.setOrderByClause(" START_TIME, END_TIME DESC");
			return calendarEventMapper.selectByExample(example);
		} else {
			return null;
		}
	}

	/**
	 * 事件查询常规
	 * @param calendarEvent
	 * @return
	 */
	public List<CalendarEvent> search(CalendarEventPage calendarEventPage) {

		CalendarEvent calendarEvent = calendarEventPage.getCalendarEvent();
		
		if(null!=calendarEvent && !StringUtile.isNull(calendarEvent.getUserId())){
			CalendarEventExample example = new CalendarEventExample();
			Criteria criteria = example.createCriteria();
			criteria.andUserIdEqualTo(calendarEvent.getUserId());
			
			if(!StringUtile.isNull(calendarEvent.getCalendarId())) {
				criteria.andCalendarIdEqualTo(calendarEvent.getCalendarId());
			}
			if(!StringUtile.isNull(calendarEvent.getRepeatType())) {
				criteria.andRepeatTypeEqualTo(calendarEvent.getRepeatType());
			}
			if(!StringUtile.isNull(calendarEvent.getSubject())) {
				criteria.andSubjectLike("%" + calendarEvent.getSubject() + "%");
			}
			if(!StringUtile.isNull(calendarEvent.getDescription())) {
				criteria.andDescriptionLike("%" + calendarEvent.getDescription() + "%");
			}
			
			
			return calendarEventMapper.selectByExample(example);
		} else {
			return null;
		}
	}

	public List<CalendarEvent> loadSimpleMailEvent() {

		List<String> list = new ArrayList<String>();
		list.add("no");
		list.add("exception");
		
		CalendarEventExample example = new CalendarEventExample();
		Criteria criteria = example.createCriteria();
		
//			普通日程\非重复日程，需要邮件提醒，且日期为今天时间在范围内
		criteria.andAlertFlagLike("email");
		criteria.andRepeatTypeIn(list);
		criteria.andStartDayEqualTo(DateUtil.formatDate(new Date(),"yyyy-mm-dd"));
		criteria.andStartTimeEqualTo(DateUtil.formatDate(new Date(),"HH24:mi"));
		
		return calendarEventMapper.selectByExample(example);
	}

	public List<CalendarEvent> loadRepeatMailEvent() {

		List<String> list = new ArrayList<String>();
		list.add("no");
		list.add("exception");
		
		CalendarEventExample example = new CalendarEventExample();
		Criteria criteria = example.createCriteria();
		
//			普通日程\非重复日程，需要邮件提醒，且日期为今天时间在范围内
		criteria.andAlertFlagLike("email");
		criteria.andRepeatTypeNotIn(list);
		
		return calendarEventMapper.selectByExample(example);
	}


}
