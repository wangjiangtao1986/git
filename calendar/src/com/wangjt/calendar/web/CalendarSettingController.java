package com.wangjt.calendar.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wangjt.calendar.mysql.dao.model.CalendarEvent;
import com.wangjt.calendar.mysql.dao.model.CalendarSetting;
import com.wangjt.calendar.mysql.dao.model.CalendarType;
import com.wangjt.calendar.service.CalendarEventService;
import com.wangjt.calendar.service.CalendarSettingService;
import com.wangjt.calendar.service.CalendarTypeService;
import com.wangjt.calendar.util.Constants;
import com.wangjt.calendar.util.StringUtile;
import com.wangjt.calendar.util.SysUtile;

import net.sf.json.JSONArray;

@Controller
public class CalendarSettingController {

	@Autowired
	private CalendarSettingService calendarSettingService;

	@Autowired
	private CalendarTypeService calendarTypeService;

	@Autowired
	private CalendarEventService calendarEventService;

	

	@RequestMapping(value = "/calendarIndex.html")
	public String index(String mode) {
		
		if(StringUtile.isNull(mode)) {
			return "index";
		} else if("N".equals(mode)) {
			return "indexN";
		} else if("S".equals(mode)) {
			return "indexS";
		}
		
		return "index";
	}
	
	/**
	 * 用户配置-加载用户配置
	 * @param request
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/loadSetting.html")
	public String loadSetting(HttpServletRequest request, ModelMap mm) {
		System.out.println("---------loadSetting.html-------");

		int settingSize = calendarSettingService.countByUserId(SysUtile.getUserId(request));

		if(settingSize<1) {
//			为用户加载默认配置 default 
			calendarSettingService.initCalendarSetting(SysUtile.getUserId(request));
		}
		
		List<CalendarSetting> list = calendarSettingService.getSetting(SysUtile.getUserId(request));
		mm.addAttribute(Constants.SUCCESS, Constants.TRUE);
		mm.addAttribute("total", list.size());
		mm.addAttribute("results", JSONArray.fromObject(list));
		return "successJson";
	}

	/**
	 * 用户配置-更新用户配置
	 * @param request
	 * @param calendarSetting
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/updateSetting.html")
	public String updateSetting(HttpServletRequest request, CalendarSetting calendarSetting, ModelMap mm) {
		System.out.println("---------updateSetting.html-------");
		int i = calendarSettingService.updateByUserId(calendarSetting, SysUtile.getUserId(request));
		if (i > 0) {
			mm.addAttribute(Constants.SUCCESS, Constants.TRUE);
		} else {
			mm.addAttribute(Constants.SUCCESS, Constants.FALSE);
		}
		return "successJson";
	}

	/**
	 * 页面初始化-用户信息初始化
	 * @param request
	 * @param calendarSetting
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/initialLoad.html")
	public String initialLoad(HttpServletRequest request, ModelMap mm) {
		System.out.println("---------initialLoad.html-------");
		String userId = SysUtile.getUserId(request);
		mm.addAttribute("cs", getCalendatSettingList(userId));
		mm.addAttribute("owned", getCalendarTypeList(userId));
		mm.addAttribute("re", getCalendarEventList(userId));
		return "initialLoadJson";
	}

	/**
	 * 加载用户重复日程
	 * @param userId
	 * @return
	 */
	private List<CalendarEvent> getCalendarEventList(String userId) {
		CalendarEvent calendarEvent = new CalendarEvent();
		calendarEvent.setUserId(userId);
		return calendarEventService.loadRepeatEvent(calendarEvent);
	}

	/**
	 * 加载用户日程分类
	 * @param userId
	 * @return
	 */
	private List<CalendarType> getCalendarTypeList(String userId) {
		int typeSize = calendarTypeService.countCalendar(userId);
//		日程分类为空则创建新日程分类
		if (typeSize == 0) {
			CalendarType record = new CalendarType();
			record.setUserId(userId);
			record.setName("工作安排");
			record.setDescription("工作安排");
			record.setColor("blue");
			record.setHide1("0");
			calendarTypeService.saveCalendarType(record);
			
		}
		return calendarTypeService.getCalendar(userId);
	}

	/**
	 * 加载用户日程配置
	 * @param userId
	 * @return
	 */
	private List<CalendarSetting> getCalendatSettingList(String userId) {

		int settingSize = calendarSettingService.countByUserId(userId);

		if(settingSize<1) {
//			为用户加载默认配置 default 
			calendarSettingService.initCalendarSetting(userId);
		}

		return calendarSettingService.getSetting(userId);
	}
}