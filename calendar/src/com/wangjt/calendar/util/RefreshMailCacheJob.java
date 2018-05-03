package com.wangjt.calendar.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.wangjt.calendar.mysql.dao.model.CalendarEvent;
import com.wangjt.calendar.service.CalendarEventService;
import com.wangjt.util.SendEMail;

public class RefreshMailCacheJob implements Job {

	public static Logger logger = Logger.getLogger(RefreshMailCacheJob.class);

	/**
	 * 可以优化到视图数据库中
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		Map dataMap = arg0.getJobDetail().getJobDataMap();
		
		ApplicationContext ctx = (ApplicationContext) dataMap.get("applicationContext");
		CalendarEventService calendarEventService = (CalendarEventService) ctx.getBean("calendarEventService");

		List<CalendarEvent> simpleMailEvent = calendarEventService.loadSimpleMailEvent();
		List<CalendarEvent> repeatMailEvent = calendarEventService.loadRepeatMailEvent();
		
		for(int i=0;i<repeatMailEvent.size();i++) {
			CalendarEvent c = repeatMailEvent.get(i);

			if("yes".equals(RepeatType.getRepeatTypeEvent(c.getAlertFlag(), DateUtil.formatDate(new Date(),"yyyy-mm-dd"), c.getStartDay()))){
				System.out.println("解析结果 " + ": yes ");
//				发送邮件 数据库查询，用户信息表？
				List<String> tos = new ArrayList<String>();
				tos.add("wangjiangtao@freedomsoft.com.cn");
				
//				测试直接嵌入IFRAME 不行
				SendEMail.sendMessage(SendEMail.getTitle(c.getSubject(),c.getStartDay() + c.getStartTime()), SendEMail.getContent(c.getUserId(),c.getDescription(),c.getStartDay() + c.getStartTime(),c.getEndDay() + c.getEndTime()), tos);
			}
		}

		for(int i=0;i<simpleMailEvent.size();i++) {
			CalendarEvent c = repeatMailEvent.get(i);
//			发送邮件 数据库查询，用户信息表？
			List<String> tos = new ArrayList<String>();
			tos.add("wangjiangtao@freedomsoft.com.cn");
			
//			测试直接嵌入IFRAME 不行
			SendEMail.sendMessage(SendEMail.getTitle(c.getSubject(),c.getStartDay() + c.getStartTime()), SendEMail.getContent(c.getUserId(),c.getDescription(),c.getStartDay() + c.getStartTime(),c.getEndDay() + c.getEndTime()), tos);
		}
		
		System.err.println("刷新工作台日志结束。"); 
	}
}
