package com.wangjt.calendar.service;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangjt.calendar.mysql.dao.mapper.CalendarTypeMapper;
import com.wangjt.calendar.mysql.dao.model.CalendarType;
import com.wangjt.calendar.mysql.dao.model.CalendarTypeExample;
import com.wangjt.calendar.mysql.dao.model.CalendarTypeExample.Criteria;
import com.wangjt.calendar.util.Constants;
import com.wangjt.calendar.util.StringUtile;

@Transactional
@Service
public class CalendarTypeService {

	@Autowired
	private CalendarTypeMapper calendarTypeMapper;
	
	public static Logger logger = Logger.getLogger(CalendarTypeService.class);


	public int createCalendarType(CalendarType record) {
		record.setId(UUID.randomUUID().toString());
		record.setInputDate(StringUtile.nowDateFmt());
		return calendarTypeMapper.insert(record);
	}

	public int updateByPrimaryKeySelective(CalendarType record) {
		return calendarTypeMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByExampleSelective(String userId,String id,CalendarType calendarType){
		CalendarTypeExample example = new CalendarTypeExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		if(null!=id){
			criteria.andIdEqualTo(id);
		}
		return calendarTypeMapper.updateByExampleSelective(calendarType,example);
	}

	public int showCalendarType(String userId,String id){
		CalendarTypeExample example = new CalendarTypeExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andIdNotEqualTo(id);
		
		CalendarType calendarType = new CalendarType();
		calendarType.setHide1("0");
		
		return calendarTypeMapper.updateByExampleSelective(calendarType,example);
	}

	public String[] saveCalendarType(CalendarType record) {
		
		int rows = 0;
		String[] returnStr = new String[2];
		if (StringUtile.isNull(record.getId())) {
			rows = createCalendarType(record);
		} else {
			rows = updateByPrimaryKeySelective(record);
		}

		if (rows > 0) {
			returnStr[0] = Constants.TRUE;
			returnStr[1] = record.getId().toString();
		} else {
			returnStr[0] = Constants.FALSE;
		}
		
		return returnStr;
	}

	public CalendarType getCalendarById(String id) {
		return calendarTypeMapper.selectByPrimaryKey(id);
	}
	
	public int deleteCalendarType(CalendarType record){
		return calendarTypeMapper.deleteByPrimaryKey(record.getId());
	}
	
	public List<CalendarType> getCalendar(String userId){
		CalendarTypeExample example = new CalendarTypeExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		return calendarTypeMapper.selectByExample(example);
	}
	
	/**
	 * TODO FENLEIDEFENLEI
	 * @param userId
	 * @return
	 */
	public int countCalendar(String userId){
		CalendarTypeExample example = new CalendarTypeExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		return calendarTypeMapper.countByExample(example);
	}
}
