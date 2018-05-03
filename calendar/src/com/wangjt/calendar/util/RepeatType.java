package com.wangjt.calendar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sf.json.JSONObject;

/**
 * 重复事件解析
 * 
 * @author Administrator
 *
 */
public class RepeatType {

	public static void main(String[] args) throws Exception {

//		日 exceptions 测试
//		String jsonstr = "{\"rtype\":\"day\",\"intervalSlot\":1,\"dspan\":0,\"beginDay\":\"2018-04-23\",\"endDay\":\"no\",\"exceptions\":{\"2018-04-23\":true,\"2018-04-26\":true,\"2018-04-28\":true}}";
		
//		日 intervalSlot 测试
//		String jsonstr = "{\"rtype\":\"day\",\"intervalSlot\":5,\"dspan\":0,\"beginDay\":\"2018-04-23\",\"endDay\":\"no\"}";

//		日 rtime 测试
//		String jsonstr = "{\"rtype\":\"day\",\"intervalSlot\":5,\"dspan\":0,\"beginDay\":\"2018-04-23\",\"endDay\":\"no\",\"rtime\":3}";

//		日 endDay 测试
//		String jsonstr = "{\"rtype\":\"day\",\"intervalSlot\":5,\"dspan\":0,\"beginDay\":\"2018-04-23\",\"endDay\":\"2018-05-31\"}";

//		周测试
//		String jsonstr = "{\"rtype\":\"week\",\"intervalSlot\":3,\"dspan\":0,\"beginDay\":\"2018-05-08\",\"endDay\":\"no\",\"rtime\":3,\"rday\":{\"1\":true,\"2\":true,\"3\":true,\"4\":true,\"7\":true}}";
//		String jsonstr = "{\"rtype\":\"week\",\"intervalSlot\":3,\"dspan\":0,\"beginDay\":\"2018-05-08\",\"endDay\":\"2018-05-31\",\"rday\":{\"1\":true,\"2\":true,\"3\":true,\"4\":true,\"7\":true}}";
		
//		月测试
//		String jsonstr = "{\"rtype\":\"month\",\"intervalSlot\":1,\"dspan\":0,\"beginDay\":\"2018-05-14\",\"endDay\":\"no\",\"rby\":\"date\"}";
//		String jsonstr = "{\"rtype\":\"month\",\"intervalSlot\":3,\"dspan\":0,\"beginDay\":\"2018-08-15\",\"endDay\":\"no\",\"rby\":\"day\"}";

//		年测试
		String jsonstr = "{\"rtype\":\"year\",\"intervalSlot\":3,\"dspan\":0,\"beginDay\":\"2019-02-21\",\"endDay\":\"no\"}";
		
		
		String day = "2018-05-22";
		String startDay = "2018-05-01";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = format.parse(startDay);
		
		for(int i=0;i<15000;i++){

            beginDate = dateAdd(beginDate);
			
			day = getStringDate(beginDate);
			
			if("yes".equals(RepeatType.getRepeatTypeEvent(jsonstr, day, startDay))){
				System.out.println("解析结果 " + day + " : yes ");
			}

		}
	}

	/**
	 * 重复模式规则，校验日期，起始日期
	 * @param jsonstr
	 * @param day
	 * @param startDay
	 * @return
	 */
	public static String getRepeatTypeEvent(String jsonstr, String day, String startDay) {

		JSONObject rt = JSONObject.fromObject(jsonstr);
		String rtype = (String) rt.get("rtype");

		// 转成map？
		JSONObject eps = (JSONObject) rt.get("exceptions");
		
		String beginDay = getBeginDay(startDay, rt);
//		String beginDay = (String) rt.get("beginDay");
		
		String endDay = (String) rt.get("endDay");


		// 今天是否移除？
		boolean epsflag = getBooleanParaFromJson(eps, day);

		// 已经被移除
		if (epsflag) {
			return "no";
		}

		// 未开始的 开始时间 beginDay > day
//		System.out.println("beginDay.compareTo(day)  =  " + beginDay.compareTo(day));
		if (beginDay.compareTo(day) > 0) {
			return "no";
		}

		// 已经结束的，结束时间，结束时间小于当前时间 endDay<day
		if (!"no".equals(endDay) && endDay.compareTo(day) < 0) {
			return "no";
		}

		String result = "";

		try {
			if ("day".equals(rtype)) {
				result = getRepeatDayEvent(day, rt);
			} else if ("week".equals(rtype)) {
				result = getRepeatWeekEvent(day, rt);
			} else if ("month".equals(rtype)) {
				result = getRepeatMonthEvent(day, rt);
			} else if ("year".equals(rtype)) {
				result = getRepeatYearEvent(day, rt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 年重复
	 * @param day
	 * @param rt
	 * @return
	 * @throws Exception
	 */
	private static String getRepeatYearEvent(String day, JSONObject rt) throws Exception {

		String beginDay = (String) rt.get("beginDay");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		// 起始时间
		Date beginDate = format.parse(beginDay);
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
		// 获取起始时间年、月、日
		int by = cal.get(Calendar.YEAR);// 获取年份
		int bm = cal.get(Calendar.MONTH)+1;// 获取月份
		int bd = cal.get(Calendar.DATE);// 获取日

		// 当前时间
		Date date = format.parse(day);
		Calendar calnow = Calendar.getInstance();
		calnow.setTime(date);
		// 获取当前时间年、月、日
		int y = calnow.get(Calendar.YEAR);// 获取年份
		int m = calnow.get(Calendar.MONTH)+1;// 获取月份
		int d = calnow.get(Calendar.DATE);// 获取日
		
		
		if(bm == m && bd == d){

			int intervalSlot = (int) rt.get("intervalSlot");
			// 逻辑未明确
//			int dspan = (int) rt.get("dspan");
			// 重复次数
			int rtime = getIntParaFromJson(rt);
			
//			int dnum = 12 * y + m - 12 * by - bm;
			int dnum = y-by;
			int r = dnum % intervalSlot;
			int t = dnum / intervalSlot;

			if (0 == r && (rtime == 0 || t < rtime)) {
				return "yes";
			}
		}
		
		return "no";
	}

	/**
	 * 月重复
	 * @param day
	 * @param rt
	 * @return
	 * @throws Exception
	 */
	private static String getRepeatMonthEvent(String day, JSONObject rt) throws Exception {
		/**
		 * {"rtype":"month","intervalSlot":1,"dspan":0,"beginDay":"2018-04-03",
		 * "endDay":"no","rby":"date"}
		 * 
		 */
		String beginDay = (String) rt.get("beginDay");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		// 起始时间
		Date beginDate = format.parse(beginDay);
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
		// 获取起始时间年、月、日
		int by = cal.get(Calendar.YEAR);// 获取年份
		int bm = cal.get(Calendar.MONTH);// 获取月份
		int bd = cal.get(Calendar.DATE);// 获取日

		// 当前时间
		Date date = format.parse(day);
		Calendar calnow = Calendar.getInstance();
		calnow.setTime(date);
		// 获取当前时间年、月、日
		int y = calnow.get(Calendar.YEAR);// 获取年份
		int m = calnow.get(Calendar.MONTH);// 获取月份
		int d = calnow.get(Calendar.DATE);// 获取日

		String rby = (String) rt.get("rby");

		int bn = getWeekOfDate(beginDate);

		int bw = bd / 7 + 1;

		int n = getWeekOfDate(date);
		int w = d / 7 + 1;

		if ("date".equals(rby) && bd == d || "day".equals(rby) && w == bw && n == bn) {

			int intervalSlot = (int) rt.get("intervalSlot");
			// 逻辑未明确
//			int dspan = (int) rt.get("dspan");
			// 重复次数
			int rtime = getIntParaFromJson(rt);
			int dnum = 12 * y + m - 12 * by - bm;
			int r = dnum % intervalSlot;
			int t = dnum / intervalSlot;

			if (0 == r && (rtime == 0 || t < rtime)) {
				return "yes";
			}
		}
		return "no";
	}

	/**
	 * 周重复
	 * @param day
	 * @param rt
	 * @return
	 * @throws ParseException
	 */
	private static String getRepeatWeekEvent(String day, JSONObject rt) throws ParseException {

		String beginDay = (String) rt.get("beginDay");
		int intervalSlot = (int) rt.get("intervalSlot");
		// 逻辑未明确
//		int dspan = (int) rt.get("dspan");
		// 重复次数
		int rtime = getIntParaFromJson(rt);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Date beginDate = format.parse(beginDay);

		// 起始日期的星期n
		int bn = getWeekOfDate(beginDate);

		Date date = format.parse(day);

		// 当前日期的星期n
		int n = getWeekOfDate(date);

		// "\"rday\":{\"1\":true,\"2\":true,\"3\":true}
		JSONObject rday = (JSONObject) rt.get("rday");
		if (null == rday || rday.isEmpty()) {
			rday.put(bn + "", true);
		}

		for (int i=1;i<=7;i++) {
			boolean str = getBooleanParaFromJson(rday, i+"");
//			System.out.println(i + "  ：   " + str);

			if (str && n==i) {
//				date-n 当前日期的 上一个周末
//				beginDate-bn 上一个 周末
//				int dnum = (getDateIntervalSlot(date, beginDate) - n + bn) / 7 + 1;

				int dnum = (getDateIntervalSlot(date, beginDate) - n + bn) / 7 ;

				int r = dnum % intervalSlot;
				int t = dnum / intervalSlot;

				if (0 == r && (rtime == 0 || t < rtime)) {
					return "yes";
				}
			}
		}

		return "no";
	}

	/**
	 * 日重复
	 * @param day
	 * @param rt
	 * @return
	 * @throws ParseException
	 */
	private static String getRepeatDayEvent(String day, JSONObject rt) throws ParseException {

		// {"rtype":"day","intervalSlot":3,"dspan":0,"beginDay":"2018-04-09","endDay":"no","rtime":10}

		String beginDay = (String) rt.get("beginDay");
		int intervalSlot = (int) rt.get("intervalSlot");
		// 逻辑未明确
//		int dspan = (int) rt.get("dspan");
		// 重复次数
		int rtime = getIntParaFromJson(rt);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = format.parse(day);
		Date date2 = format.parse(beginDay);

		int dnum = getDateIntervalSlot(date1, date2);

		int r = dnum % intervalSlot;
		int t = dnum / intervalSlot;

		// 间隔为步长整数倍 并且 间隔数为0-重复次数要求之间；
		if (0 == r && (rtime == 0 || t < rtime)) {
			return "yes";
		}
		return "no";
	}

	// ,
	//
	// getRepeatDayEvent:function(day, re){
	// var rt = re.repeatType;
	// var beginDay = rt.beginDay;
	// var intervalSlot = rt.intervalSlot;
	// var dspan = Number(rt.dspan);
	// var rtime = Number(rt.rtime);
	// var dnum = Ext.ux.calendar.Mask.getDayOffset(beginDay, day);
	// var r = dnum%intervalSlot;
	// var t = Math.floor(dnum/intervalSlot);
	// if(0 == r && (!rtime || t < rtime)){
	// var e = Ext.apply({}, re);
	// e.day = day;
	// var date = Ext.Date.parseDate(day, 'Y-m-d');
	// e.eday = Ext.Date.format((Ext.Date.add(date, Ext.Date.DAY,
	// dspan)),'Y-m-d');
	// delete(e.lflag);
	// delete(e.rflag);
	// return e;
	// }
	// },
	//
	// getRepeatWeekEvent:function(day, re){
	// var rt = re.repeatType;
	// var beginDay = rt.beginDay;
	// var vDate=Ext.Date;
	// var beginDate = vDate.parseDate(beginDay, 'Y-m-d');
	// var bn = vDate.format(beginDate,'N');
	// var date = vDate.parseDate(day, 'Y-m-d');
	// var n = vDate.format(date,'N');
	// var rday = rt.rday;
	// if('{}' == Ext.encode(rday)){
	// rday[bn] = true;
	// }
	// if(rday[n]){
	// var intervalSlot = rt.intervalSlot;
	// var dspan = Number(rt.dspan);
	// var rtime = Number(rt.rtime);
	// var dnum = Math.floor((Ext.ux.calendar.Mask.getDayOffset(beginDay, day)-n-bn)/7)+1;
	// var r = dnum%intervalSlot;
	// var t = Math.floor(dnum/intervalSlot);
	// if(0 == r && (!rtime || t < rtime)){
	// var e = Ext.apply({}, re);
	// e.day = day;
	// var date = vDate.parseDate(day, 'Y-m-d');
	// e.eday = vDate.format((vDate.add(date, vDate.DAY, dspan)),'Y-m-d');
	// delete(e.lflag);
	// delete(e.rflag);
	// return e;
	// }
	// }
	// },
	//
	// getRepeatMonthEvent:function(day, re){
	// var rt = re.repeatType;
	// var beginDay = rt.beginDay;
	// var vDate=Ext.Date;
	// var beginDate = vDate.parseDate(beginDay, 'Y-m-d');
	// var by = vDate.format(beginDate,'Y');
	// var bm = vDate.format(beginDate,'n');
	// var bd = vDate.format(beginDate,'j');
	//
	// var date = vDate.parseDate(day, 'Y-m-d');
	// var y = vDate.format(date,'Y');
	// var m = vDate.format(date,'n');
	// var d = vDate.format(date,'j');
	//
	// var rby = rt.rby;
	//
	// var bn = vDate.format(beginDate,'N');
	// var bw = Math.floor(bd/7)+1;
	//
	// var n = vDate.format(date,'N');
	// var w = Math.floor(d/7)+1;
	// if(('date' == rby && bd == d) || ('day' == rby && w == bw && n == bn)){
	// var intervalSlot = rt.intervalSlot;
	// var dspan = Number(rt.dspan);
	// var rtime = Number(rt.rtime);
	// var dnum = 12*y+m-12*by-bm;
	// var r = dnum%intervalSlot;
	// var t = Math.floor(dnum/intervalSlot);
	// if(0 == r && (!rtime || t < rtime)){
	// var e = Ext.apply({}, re);
	// e.day = day;
	// e.eday = vDate.format((vDate.add(date, vDate.DAY, dspan)),'Y-m-d');
	// delete(e.lflag);
	// delete(e.rflag);
	// return e;
	// }
	// }
	// },
	//
	// getRepeatYearEvent:function(day, re){
	// var vDate=Ext.Date;
	// var rt = re.repeatType;
	// var beginDay = rt.beginDay;
	// var beginDate = vDate.parseDate(beginDay, 'Y-m-d');
	// var by = vDate.format(beginDate,'Y');
	// var bm = vDate.format(beginDate,'n');
	// var bd = vDate.format(beginDate,'j');
	//
	// var date = vDate.parseDate(day, 'Y-m-d');
	// var y = vDate.format(date,'Y');
	// var m = vDate.format(date,'n');
	// var d = vDate.format(date,'j');
	//
	// if(bm == m && bd == d){
	// var intervalSlot = rt.intervalSlot;
	// var dspan = Number(rt.dspan);
	// var rtime = Number(rt.rtime);
	// var dnum = y-by;
	// var r = dnum%intervalSlot;
	// var t = Math.floor(dnum/intervalSlot);
	// if(0 == r && (!rtime || t < rtime)){
	// var e = Ext.apply({}, re);
	// e.day = day;
	// e.eday = vDate.format(vDate.add(date, vDate.DAY, dspan),'Y-m-d');
	// delete(e.lflag);
	// delete(e.rflag);
	// return e;
	// }
	// }
	// }

	// getDayOffset:function(sday, eday){
	// var vDate=Ext.Date;
	// if(!(sday instanceof Date)){
	// sday = vDate.parseDate(sday, 'Y-m-d');
	// }
	// if(!(eday instanceof Date)){
	// eday = vDate.parseDate(eday, 'Y-m-d');
	// }
	// var offset = vDate.getElapsed(sday,eday);
	// offset = Math.round(offset/(3600000*24));
	// return offset;
	// }

	public static int getWeekOfDate(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w <= 0)
			w = 7;

		return w;
	}
	public static int getCurrentWeekOfMonthIndex(Calendar calendar) {
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	private static int getDateIntervalSlot(Date date1, Date date2) {
		int dnum = (int) ((date1.getTime() - date2.getTime()) / (1000 * 3600 * 24));
		return dnum;
	}


	private static boolean getBooleanParaFromJson(JSONObject rt, String key) {
		boolean rtime = false;
		try {
			rtime = (boolean) rt.get(key);
		} catch(Exception e) {
//			System.out.println("数据为空。不予处理。");
//			e.printStackTrace();
		}
		return rtime;
	}
	private static int getIntParaFromJson(JSONObject rt) {
		int rtime = 0;
		try {
			rtime = (int) rt.get("rtime");
//			String srtime = (String) rt.get("rtime");
//			if(StringUtile.isNull(srtime)){
//				rtime=0;
//			} else {
//				rtime = Integer.parseInt(srtime);
//			}
		} catch(Exception e) {
//			System.out.println("数据为空。不予处理。");
//			e.printStackTrace();
		}
		return rtime;
	}

	private static String getBeginDay(String startDay, JSONObject rt) {
		String beginDay = (String) rt.get("beginDay");
		return beginDay.compareTo(startDay)>0 ? beginDay:startDay;
	}

	public static String getStringDate(Date currentTime) {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    String dateString = formatter.format(currentTime);
	    return dateString;
	 }
	private static Date dateAdd(Date beginDate) {
		Calendar c = Calendar.getInstance();  
		c.setTime(beginDate);  
		c.add(Calendar.DAY_OF_YEAR, 1);
		
		beginDate = c.getTime();
		return beginDate;
	}

}
