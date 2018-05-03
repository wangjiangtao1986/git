package com.wangjt.calendar.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtile {

	public static boolean isNull(String str) {
		if (null == str || "".equals(str) || "null".equals(str)) {
			return true;
		}
		return false;
	}

	public static String dateString(String datastr) {
		Date now = new Date();
		SimpleDateFormat f = new SimpleDateFormat("今天是" + "yyyy年MM月dd日 E kk点mm分");
		System.out.println(f.format(now));
		f = new SimpleDateFormat("a hh点mm分ss秒");
		System.out.println(f.format(now));
		return f.format(now);
	}

	public static String dateFmt(Date now, String dataFmt) {
		SimpleDateFormat f = new SimpleDateFormat(dataFmt);
		return f.format(now);
	}

	public static String nowDateFmt(String dataFmt) {
		SimpleDateFormat f = new SimpleDateFormat(dataFmt);
		return f.format(new Date());
	}

	public static String nowDateFmt() {
		return nowDateFmt("yyyy-MM-dd HH:mm:ss");
	}

	public static void main(String[] args) {
		nowDateFmt();
	}

}