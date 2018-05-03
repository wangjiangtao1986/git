package com.wangjt.calendar.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wangjt.calendar.mysql.dao.model.CalendarType;
import com.wangjt.calendar.service.CalendarTypeService;
import com.wangjt.calendar.util.Constants;
import com.wangjt.calendar.util.SysUtile;

@Controller
public class CalendarTypeController {

	@Autowired
	private CalendarTypeService calendarTypeService;
	
	/***
	 * TODO : YONGHUYANZHENG
	 */
	@RequestMapping(value = "/saveCalendarType.html")
	public String saveCalendarType(HttpServletRequest request, CalendarType type, ModelMap mav) {
		System.out.println("-------saveCalendarType.html-------");
		type.setUserId(SysUtile.getUserId(request));
		String[] returnStr = calendarTypeService.saveCalendarType(type);
		mav.addAttribute(Constants.SUCCESS, returnStr[0]);
		mav.addAttribute("id", returnStr[1]);
		return "successJson";
	}

	@RequestMapping(value="/loadCalendar.html")
	public String loadCalendar(HttpServletRequest request,CalendarType type, ModelMap mav){
		System.out.println("-------loadCalendar.html-------");
		String userId = SysUtile.getUserId(request);
		List<CalendarType> typeList = calendarTypeService.getCalendar(userId);
		mav.addAttribute("success", Constants.TRUE);
		mav.addAttribute("total", typeList.size());
		mav.addAttribute("results", typeList);
		return "successJson";
	}

	@RequestMapping(value="showOnlyCalendar.html")
	public String showOnlyCalendar(HttpServletRequest request,String id,ModelMap mm){
		System.out.println("-------showOnlyCalendar.html-------");
		if(null != id){
			CalendarType calendarType = new CalendarType();
			calendarType.setHide1("0");
			calendarTypeService.updateByExampleSelective(SysUtile.getUserId(request),null,calendarType);
			
			calendarType.setHide1("1");
			calendarTypeService.updateByExampleSelective(SysUtile.getUserId(request),id,calendarType);
			mm.addAttribute("success", Constants.SUCCESS);
		} else {
			mm.addAttribute("success", Constants.FALSE);
		}
		return "successJson";
	}
	
	@RequestMapping(value="showAllCalendar.html")
	public String showAllCalendar(HttpServletRequest request, ModelMap mm) {
		System.out.println("-------showAllCalendar.html-------");
		CalendarType calendarType = new CalendarType();
		calendarType.setHide1("0");
		int returnflag = calendarTypeService.updateByExampleSelective(SysUtile.getUserId(request),null,calendarType);
		if (returnflag > 0) {
			mm.addAttribute("success", Constants.SUCCESS);
		} else {
			mm.addAttribute("success", Constants.FALSE);
		}
		return "successJson";
	}
	
//	/***
//	 * 更改左侧栏日历分类的状态（显示、影藏）
//	 * @param request
//	 * @param type
//	 * @param mav
//	 * @return
//	 */
//	@RequestMapping(value = "/checkSubSysCalendar.html")
//	public String checkSubSysCalendar(HttpServletRequest request,
//			CalendarType type, ModelMap mav) {
//		System.out.println("-------checkSubSysCalendar.html-------");
//		if(checkCalendarType(type.getId())){
//			CalendarType ct = calendarTypeService.getSubSysCalendarInfo(type);
//			System.out.println("ct = " + ct);
//			mav.addAttribute("subSysEventId", ct.getSub_eventid());
//			mav.addAttribute("subSysUrl", ct.getUrl());
//			mav.addAttribute("subsys", ct.getSubsys());
//			mav.addAttribute(Constants.SUCCESS, "true");
//		}else{
//			mav.addAttribute(Constants.SUCCESS, "false");
//		}
//		
//		return "successJson";
//	}
//	/***
//	 * 更改左侧栏日历分类的状态（显示、影藏）
//	 * @param request
//	 * @param type; hide\id\ctype\color\
//	 * @param mav
//	 * @return
//	 */
//	@RequestMapping(value = "/saveCalendarType2.html")
//	public String saveCalendarType2(HttpServletRequest request, CalendarType type, ModelMap mav) {
//		System.out.println("-------saveCalendarType2.html-------");
//		User user = (User) request.getSession().getAttribute("user");
//		type.setUserId(user.getUser_id());
//		
//		String hide1 = request.getParameter("hide");
//		
//		if (hide1.equals("false")) {
//			type.setHide("0");
//		} else {
//			type.setHide("1");
//		}
//		
//		String[] returnStr = calendarTypeService.saveCalendarType(type);
//		mav.addAttribute(Constants.SUCCESS, returnStr[0]);
//		mav.addAttribute("id", returnStr[1]);
//		
//		return "successJson";
//	}
	
//	/***
//	 * 根据事件ID 将待安排或受邀约隐藏状态改为显示//
//	 * @param request
//	 * @param type
//	 * @param mav
//	 * @return
//	 */
//	@RequestMapping(value = "/updateCalendarTypeByEventId.html")
//	public String updateCalendarTypeByEventId(HttpServletRequest request, String eventId, ModelMap mav) {
//		System.out.println("-------updateCalendarTypeByEventId.html-------");
//		calendarTypeService.updateCalendarTypeByEventId(eventId);
//		
//		return "successJson";
//	}
//	
	
//	private boolean checkCalendarType(BigDecimal id) {
//		//该分类 calendarType 表中存在 且 为 缓存中 数据，返回true
//		CalendarType ct = calendarTypeService.getCalendarById(id);
//		if(null==ct||null==ct.getSubsystypeid()){
//			return false;
//		}else{
//			String s = (String)SubSysTypeConfig.getSubSysType().get(ct.getSubsystypeid().toString());
//			System.out.println(ct.getSubsystypeid());
//			System.out.println(s);
//			if(null!=ct&&null!=s){
//				return true;
//			}else{
//				return false;
//			}
//		}
//	}
	
//	
//	/***
//	 * (将该方法迁移至CalendarEventController.java)
//	 * @param request
//	 * @param mm
//	 * @return
//	 */
//	@RequestMapping(value="changeDay.html")
//	public String changeDay(HttpServletRequest request, ModelMap mm){
//		
//		System.out.println("-------changeDay.html-------");
//		User user = (User)request.getSession().getAttribute("user");
//		String userId = user.getUser_id();
//		String keep = request.getParameter("keep");
//		String dragDay = request.getParameter("dragDay");
//		String startTime = dragDay+" 00:00";
//		String endTime = dragDay+" 23:59";
//		CalendarEvent calendarEvent = new CalendarEvent();
//		calendarEvent.setUserId(userId);
//		calendarEvent.setStartTime(startTime);
//		calendarEvent.setEndTime(endTime);
//		List<CalendarEvent> list= calendarEventMapper.loadDayEvent(calendarEvent);//将此方法封装到CalendarEventService.java类中
//		for(int i = 0; i < list.size(); i ++){
//			CalendarEvent event = list.get(i);
//			BigDecimal oldId = event.getId();
//			event.setStartTime(startTime);
//			event.setEndTime(endTime);
//			if(keep.equals("true")){
//				calendarEventMapper.insert(event);
//				calendarEventReminderService.copyEventReminders(oldId);
//			}else{
//				calendarEventMapper.updateByPKSelective(event);
//			}
//		}
//		if(list.size() > 0){
//			mm.addAttribute(Constants.SUCCESS, Constants.TRUE);
//			mm.addAttribute("backids","");
//		}else{
//			mm.addAttribute(Constants.SUCCESS,Constants.FALSE);
//		}
//		return "successJson";
//	}
//	
//	/***
//	 * (将该方法迁移至CalendarEventController.java)
//	 * @param request
//	 * @param mm
//	 * @return
//	 */
//	@RequestMapping(value="deleteDay.html")
//	public String deleteDay(HttpServletRequest request, ModelMap mm){
//		System.out.println("-------changeDay.html-------");
//		User user = (User)request.getSession().getAttribute("user");
//		String userId = user.getUser_id();
//		String startTime = request.getParameter("day") + " 00:00" ;
//		String endTime = request.getParameter("day") + " 23:59" ; 
//		CalendarEvent calendarEvent = new CalendarEvent();
//		calendarEvent.setUserId(userId);
//		calendarEvent.setStartTime(startTime);
//		calendarEvent.setEndTime(endTime);
//		int i = calendarEventMapper.deleteDay(calendarEvent);
//		i = calendarEventMapper.deleteDay2(calendarEvent);
//		if(i > 0){
//			mm.addAttribute(Constants.SUCCESS, Constants.TRUE);
//		}else{
//			mm.addAttribute(Constants.SUCCESS,Constants.FALSE);
//		}
//		return "successJson";
//	}
//
//	/***
//	 * (将该方法迁移至CalendarEventController.java)
//	 * @param request
//	 * @param mm
//	 * @return
//	 */
//	@RequestMapping(value="search.html")
//	public String search(HttpServletRequest request, ModelMap mm){
//		
//		System.out.println("-------search.html-------");
//		User user = (User)request.getSession().getAttribute("user");
//		String userId = user.getUser_id();
//		boolean userRealflag = false;
//		if(null!=user.getPerson_id()) {
//			userRealflag = true;
//		}
//		
//		
//		CalendarEvent calendarEvent = new CalendarEvent();
//		String description = request.getParameter("text");
//		if("cancelSearch".equals(description)){
//			request.getSession().setAttribute("description","");
//			description="";
//		}else{
//			if(null==description||"".equals(description)||"null".equals(description)){
//				description = (String)request.getSession().getAttribute("description");
//			}else{
//				request.getSession().setAttribute("description",description);
//			}
//			
//			if(description != null && !description.equals("")){
//				calendarEvent.setDescription(description);
//			}
//		}
////		calendarEvent.setDescription(description);
//		calendarEvent.setUserId(userId);
//
//		int start = Integer.parseInt(request.getParameter("start"));
//		int page = Integer.parseInt(request.getParameter("page"));
//		int limit = Integer.parseInt(request.getParameter("limit"));
//		BigDecimal startNo = new BigDecimal(start);
//		BigDecimal endNo = new BigDecimal(page * limit);
//		calendarEvent.setStartNo(startNo);
//		calendarEvent.setEndNo(endNo);
//		List<CalendarEvent> list = calendarEventService.search(calendarEvent);
//		int listsize = calendarEventService.searchTotal(calendarEvent);
//		
//
//		for(int i = 0; i< list.size(); i ++) {
//			CalendarEvent c = list.get(i);
//			if(("true").equals(c.getYaoqing())) {
//				c.setYaoqing1(true);
//			} else {
//				c.setYaoqing1(false);
//			}
//			
//			if(c.getYaoqingeventid()!= null && !"4".equals(c.getType()) ) {
//				CalendarEvent calendarEvent2 = calendarEventService.selectFaQiUserInfo(c);
////				发起人姓名、所在机构//
//
//				System.out.println(c.getYaoqingeventid());
//				System.out.println(c.getId());
//				System.out.println(c.getIsmanager());
//				if(null!=calendarEvent2&&null!=calendarEvent2.getUnitName()){
//					c.setUnitName(calendarEvent2.getUnitName());
//				}
//				if(null!=calendarEvent2&&null!=calendarEvent2.getXingmin()){
//					c.setXingmin(calendarEvent2.getXingmin());
//				}
//				
//				if(!"false".equals(c.getIsmanager())) {
//					CalendarEvent calendarEvent3 = calendarEventService.selectUserInfo(c);
////					当前人的机构
//					c.setUserDeptName(calendarEvent3.getUnitName());
//				}
//			}
//		}
//		
//		JSONArray arr = JSONArray.fromObject(list);
//		
//		System.out.println(arr);
//		
//		mm.addAttribute("success", Constants.TRUE);
//		mm.addAttribute("total", listsize);
//		mm.addAttribute("results", arr);
//		mm.addAttribute("info",userRealflag);
//		return "successJson";
//	}
}
