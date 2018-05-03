package com.wangjt.calendar.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;


public class CalendarCache {
	
	public static List<EamsCalendar> calendarListmap  = new ArrayList<EamsCalendar>();
	
	private static String timing = null;
	
	private static String mailtiming = null;
	/**
	 * 刷新缓存
	 */
	public static void refreshCache(JobExecutionContext arg0){

		System.err.println("刷新工作台日志开始。"); 
		Map dataMap = arg0.getJobDetail().getJobDataMap();
		
		ApplicationContext ctx = (ApplicationContext) dataMap.get("applicationContext-calendar");
		EamsCalendarMapper eamsCalendarMapper = (EamsCalendarMapper) ctx.getBean("eamsCalendarMapper");
		
	    try {
	    	Date dd = new Date();
	    	calendarListmap = eamsCalendarMapper.getPopList4Info(dd.getYear()+1900 + "-" + (dd.getMonth()+1) + "-" + dd.getDate() +" "+ dd.getHours() +":"+ dd.getMinutes()+":00");
		} catch (Exception e) {
			System.err.println("刷新工作台日志：失败。"); 
			e.printStackTrace();
		}
		System.err.println("刷新工作台日志结束。"); 
	}
	
	
	/**
	 * 获取用户未读消息
	 * @param user
	 * @return
	 */
	public synchronized static List<EamsCalendar> getNoticeCalendarByUser(String userId) {
		
		List<EamsCalendar> list=new ArrayList<EamsCalendar>();

		for(int i=0;i<calendarListmap.size();i++){
			EamsCalendar map=(EamsCalendar) calendarListmap.get(i);
			if(userId.equals((String)map.getUser_id())) {
				EamsCalendar a = new EamsCalendar();
				a=map;
//				System.out.println(a.getEnd_time());
//				System.out.println(a.getStart_time());
//				System.out.println(a.getSubject());
			    list.add(a);
			    try{
			    	
			    }catch (Exception e) {
					
				}
			}
		}
		return list;
	}	
//		public synchronized static List<EamsCalendarMailInfo> getNoticeMailByUser(String userId){
//		
//			
//			List<EamsCalendarMailInfo> list1=new ArrayList<EamsCalendarMailInfo>();
//			
//			
//			for(int i=0;i<calendarMailmap.size();i++){
//				Map map=(Map)calendarMailmap.get(i);
//				
//				if(userId.equals((String)map.get("user_id")))
//				
//				{
//
//					EamsCalendarMailInfo b = new EamsCalendarMailInfo();
//					
//	                Timestamp t3= new Timestamp(System.currentTimeMillis());
//					
//					Timestamp t4 = Timestamp.valueOf((String)map.get("notictime"));
//					
//					b.setTime(String.valueOf(t3.getTime()-t4.getTime()));
//					
//					if(i==0)
//					{
//					b.setTime(String.valueOf(t4.getTime()-t3.getTime()));
//					//上一次提醒时间
//
//					mailtiming = (String)map.get("notictime");
//					
//					}else{
//						Timestamp t6= Timestamp.valueOf(mailtiming);
//					    b.setTime(String.valueOf(t4.getTime()-t6.getTime()));	
//						
//					}
//					b.setNotes((String)map.get("notes"));
//					
//					b.setTitle((String)map.get("title"));
//					
//					b.setStart(((String)map.get("start_time")));
//					
//					b.setEnd(((String)map.get("end_time")));
//				   	
//					b.setNoteurl(((String)map.get("user_notes")));
//					
//					
//					list1.add(b);
//				}
//				
//			}
//             return list1;
//		
//	}
}
