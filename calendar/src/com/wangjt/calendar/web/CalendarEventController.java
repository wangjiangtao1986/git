package com.wangjt.calendar.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wangjt.calendar.model.CalendarEventPage;
import com.wangjt.calendar.mysql.dao.model.CalendarEvent;
import com.wangjt.calendar.mysql.dao.model.CalendarType;
import com.wangjt.calendar.service.CalendarEventReminderService;
import com.wangjt.calendar.service.CalendarEventService;
import com.wangjt.calendar.service.CalendarTypeService;
import com.wangjt.calendar.util.Constants;
import com.wangjt.calendar.util.StringUtile;
import com.wangjt.calendar.util.SysUtile;

@Controller
public class CalendarEventController {

	@Autowired
	private CalendarEventService calendarEventService;
	
	@Autowired
	private CalendarEventReminderService calendarEventReminderService;

	@Autowired
	private CalendarTypeService calendarTypeService;


	/**
	 * 日程信息数据删除通过假删除的方式处理
	 */
	
	@RequestMapping(value = "/deleteEventsByCalendar.html")
	public String deleteEventsByCalendar(HttpServletRequest request, CalendarType calendarType, ModelMap mm) {
		System.out.println("-------deleteEventsByCalendar.html-------");
		if (null==calendarType.getId()) {
			mm.addAttribute(Constants.SUCCESS, Constants.FALSE);
		} else {
			calendarEventService.deleteEventByCalendarType(calendarType.getId());
//			删除冗余的CalendarEventReminder，放到job中执行
//			calendarEventReminderService.deleteCalendarEventReminder(eventId);
			mm.addAttribute(Constants.SUCCESS, Constants.SUCCESS);
		}
		return "successJson";
	}

	@RequestMapping(value = "deleteCalendar.html")
	public String deleteCalendar(HttpServletRequest request, CalendarType calendarType, ModelMap mm) {
		System.out.println("-------deleteCalendar.html-------");
		if (null==calendarType.getId()) {
			mm.addAttribute(Constants.SUCCESS, Constants.SUCCESS);
		} else {
			calendarEventService.deleteEventByCalendarType(calendarType.getId());
			calendarTypeService.deleteCalendarType(calendarType);
			mm.addAttribute(Constants.SUCCESS, Constants.SUCCESS);
		}
		return "successJson";
	}


	@RequestMapping(value = "/createEditEvent.html")
	public String createEditEvent(HttpServletRequest request, CalendarEvent calendarEvent, String alertFlag, ModelMap mm) {
		System.out.println("-------createEditEvent.html-------");

		calendarEvent.setUserId(SysUtile.getUserId(request));

		String[] returnStr = calendarEventService.createEditEvent(calendarEvent);
		
//		创建提醒信息
		calendarEventReminderService.createEditCalendarEventReminder(calendarEvent);
		
		mm.addAttribute(Constants.SUCCESS, returnStr[0]);
		mm.addAttribute("id", returnStr[1]);
		return "successJson";
	}
	
	
	@RequestMapping(value = "deleteEvent.html")
	public String deleteEvent(HttpServletRequest request, String id, ModelMap mm) {
		System.out.println("-------deleteEvent.html-------");
		try {
			calendarEventService.deleteEvent(id);
		}catch (Exception e) {
			e.printStackTrace();
		}
		mm.addAttribute(Constants.SUCCESS, Constants.TRUE);
		mm.addAttribute("id", id);
		return "successJson";
	}
	
	/**
	 * TODO: add alertFlag chuli 
	 * @param request
	 * @param record
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "loadEvent.html")
	public String loadEvent(HttpServletRequest request, CalendarEvent calendarEvent, ModelMap mm) {
		System.out.println("-------loadEvent.html-------");
		calendarEvent.setUserId(SysUtile.getUserId(request));
		List<CalendarEvent> list = calendarEventService.loadSimpleEvent(calendarEvent);
//				publicLoadCalendarEvent(calendarEventService.loadSimpleEvent(record));
//		success
		mm.addAttribute(Constants.SUCCESS, Constants.TRUE);
		mm.addAttribute("total", list.size());
		mm.addAttribute("rows", list);
		return "listJson";
	}

	@RequestMapping(value = "loadRepeatEvent.html")
	public String loadRepeatEvent(HttpServletRequest request, ModelMap mm) {
		System.out.println("-------loadRepeatEvent.html-------");
		CalendarEvent record = new CalendarEvent();
		record.setUserId(SysUtile.getUserId(request));
		List<CalendarEvent> list = calendarEventService.loadRepeatEvent(record);
//				publicLoadCalendarEvent(calendarEventService.loadRepeatEvent(record));
		mm.addAttribute(Constants.SUCCESS, Constants.TRUE);
		mm.addAttribute("total", list.size());
		mm.addAttribute("results", list);
		return "successJson";
	}
	
	public List<CalendarEvent> publicLoadCalendarEvent(List<CalendarEvent> list){
		return new ArrayList<CalendarEvent>();
	}

	@RequestMapping(value = "deleteRepeatEvent.html")
	public String deleteRepeatEvent(HttpServletRequest request, CalendarEvent calendarEvent, ModelMap mm) {
		System.out.println("-------deleteRepeatEvent.html-------");
		String makeException = (String) request.getParameter("makeException");
		String id = calendarEvent.getId();
		calendarEvent.setUserId(SysUtile.getUserId(request));
		if (makeException.equals("true")) {
			calendarEventService.updateByPKSelective(calendarEvent);
		} else {
			calendarEventService.deleteEvent(calendarEvent.getId());
		}
		mm.addAttribute(Constants.SUCCESS, Constants.TRUE);
		mm.addAttribute("id", id.toString());
		return "successJson";
	}
	
	/**
	 * 简单事件到重复事件
	 * 
	 * 重复事件到简单事件
	 * 
	 * 重复事件删除
	 * 
	 * 简单事件删除
	 * 
	 * @param request
	 * @param calendarEvent
	 * @param oldRepeatType
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/createUpdateRepeatEventURL.html")
	public String createUpdateRepeatEventURL(HttpServletRequest request, CalendarEvent calendarEvent, String oldRepeatType, ModelMap mm) {
		System.out.println("-------createUpdateRepeatEventURL.html-------");

		calendarEvent.setUserId(SysUtile.getUserId(request));
		
		String[] returnStr = new String[2];
		
		/**
		 * 普通事件改为重复事件
		 * 重复事件改为普通事件
		 * 重复事件部分改为普通事件和一个重复事件 
		 * 重复事件改为重复事件
		 */
		if (null != calendarEvent.getId()) {
//			 如果id不为空，更新事件
			boolean oflag = isRepeatEvent(oldRepeatType);
			boolean flag = isRepeatEvent(calendarEvent.getRepeatType());
			if (oflag && flag) {
//				重复事件改为重复事件
				calendarEventService.updateEvent(calendarEvent);
			} else if (oflag) {
//				重复事件改为普通事件 putong chuli 
//				重复事件部分改为普通事件和一个重复事件 
//				老类型不为空，新类型为空，更新回老的类型
				if("exception".equals(calendarEvent.getRepeatType())){
					CalendarEvent ce = new CalendarEvent();
					ce.setId(calendarEvent.getId());
					ce.setRepeatType(oldRepeatType);
					calendarEventService.updateByPKSelective(ce);
					returnStr = calendarEventService.createEditEvent(calendarEvent);
				} else {
					calendarEventService.updateEvent(calendarEvent);
				}
			} else if (flag) {
//				普通事件改为重复事件
//				 新类型不为空，老类型为空，删除原数据，重新插入
				calendarEventService.updateEvent(calendarEvent);
			} else {
				// 无操作
			}
		} else {
			returnStr = calendarEventService.createEditEvent(calendarEvent);
		}

//		创建提醒信息
		calendarEventReminderService.createEditCalendarEventReminder(calendarEvent);
		
		mm.addAttribute(Constants.SUCCESS, returnStr[0]);
		mm.addAttribute("id", returnStr[1]);
//		mm.addAttribute("id2", id);
		return "successJson";
	}


	public boolean isRepeatEvent(String repeatType) {
		if ("no".equals(repeatType) || "exception".equals(repeatType)) {
			return false;
		}
		return true;
	}
	@RequestMapping(value = "/updateEvent.html")
	public String updateEvent(HttpServletRequest request, CalendarEvent calendarEvent, ModelMap mm) {
		System.out.println("-------updateEvent.html-------");
		try{
			calendarEventService.updateEvent(calendarEvent);
		}catch (Exception e) {
			e.printStackTrace();
		}

		mm.addAttribute(Constants.SUCCESS, Constants.SUCCESS);
		mm.addAttribute("id", calendarEvent.getId());
		mm.addAttribute("info", "");
		return "successJson";
	}
	

	@RequestMapping(value="/search.html")
	public String search(HttpServletRequest request, ModelMap mm){
		System.out.println("-------search.html-------");
		
		CalendarEventPage calendarEventPage = new CalendarEventPage();
		CalendarEvent calendarEvent = new CalendarEvent();
		calendarEvent.setUserId(SysUtile.getUserId(request));
		
		String description = request.getParameter("text");
		
		if("cancelSearch".equals(description)){
			request.getSession().setAttribute("description","");
			description="";
		}else{
			if(StringUtile.isNull(description)){
				description = (String)request.getSession().getAttribute("description");
			}else{
				request.getSession().setAttribute("description",description);
			}
			
			if(description != null && !description.equals("")){
				calendarEvent.setDescription(description);
				calendarEvent.setSubject(description);
			}
		}

		calendarEventPage.setCalendarEvent(calendarEvent);
//		int start = Integer.parseInt(request.getParameter("start"));
//		int page = Integer.parseInt(request.getParameter("page"));
//		int limit = Integer.parseInt(request.getParameter("limit"));
//		BigDecimal startNo = new BigDecimal(start);
//		BigDecimal endNo = new BigDecimal(page * limit);
//		calendarEvent.setStartNo(startNo);
//		calendarEvent.setEndNo(endNo);
		List<CalendarEvent> list = calendarEventService.search(calendarEventPage);
//		int listsize = calendarEventService.searchTotal(calendarEvent);
		
		mm.addAttribute("success", Constants.TRUE);
		mm.addAttribute("total", list.size());
		mm.addAttribute("results", list);
		return "successJson";
	}
	
	@RequestMapping(value="/listPage/search.html")
	public String listPageSearch(HttpServletRequest request, ModelMap mm){
		System.out.println("-------listPageSearch.html-------");
		
		CalendarEventPage calendarEventPage = new CalendarEventPage();
		CalendarEvent calendarEvent = new CalendarEvent();
		calendarEvent.setUserId(SysUtile.getUserId(request));
		
		String description = request.getParameter("text");
		
		if("cancelSearch".equals(description)){
			request.getSession().setAttribute("description","");
			description="";
		}else{
			if(StringUtile.isNull(description)){
				description = (String)request.getSession().getAttribute("description");
			}else{
				request.getSession().setAttribute("description",description);
			}
			
			if(description != null && !description.equals("")){
				calendarEvent.setDescription(description);
				calendarEvent.setSubject(description);
			}
		}

		calendarEventPage.setCalendarEvent(calendarEvent);
//		int start = Integer.parseInt(request.getParameter("start"));
//		int page = Integer.parseInt(request.getParameter("page"));
//		int limit = Integer.parseInt(request.getParameter("limit"));
//		BigDecimal startNo = new BigDecimal(start);
//		BigDecimal endNo = new BigDecimal(page * limit);
//		calendarEvent.setStartNo(startNo);
//		calendarEvent.setEndNo(endNo);
		List<CalendarEvent> list = calendarEventService.search(calendarEventPage);
//		int listsize = calendarEventService.searchTotal(calendarEvent);
		
		mm.addAttribute("success", Constants.TRUE);
		mm.addAttribute("total", list.size());
		mm.addAttribute("results", list);
		
		return "searchJson";
	}
}