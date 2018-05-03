package com.wangjt.calendar.util;

import javax.servlet.http.HttpServletRequest;

import com.wangjt.util.User;

public class SysUtile {

	public static String getUserId(HttpServletRequest request) {
		return getUser(request).getUserId();
	}

	public static User getUser(HttpServletRequest request) {
		return (User)request.getSession().getAttribute("user");
	}
}