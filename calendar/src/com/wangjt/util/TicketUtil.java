package com.wangjt.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

import com.wangjt.calendar.mysql.dao.model.CalendarMd5;

public class TicketUtil {

	public static CalendarMd5 getSignature(String appSecret, String appId, byte[] databyte) {
		CalendarMd5 cm = new CalendarMd5(); 
		cm.setAppId(appId);
		cm.setAppSecret(appSecret);
		cm.setDatabyte(databyte);
		cm.setNoncestr(create_nonce_str());
		cm.setTimestamp(create_timestamp());
		String signature = "";
		// 注意这里参数名必须全部小写，且必须有序
		String string1 = "app_secret=" + cm.getAppSecret() + "&noncestr=" + cm.getNoncestr() + "&timestamp=" + cm.getTimestamp() + "&app_id=" + cm.getAppId();
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		cm.setSignature(signature);
		return cm;
	}

//	public static CalendarMd5 getSignature(CalendarMd5 cm) {
//		cm.setNoncestr(create_nonce_str());
//		cm.setTimestamp(create_timestamp());
//		String signature = "";
//		// 注意这里参数名必须全部小写，且必须有序
//		String string1 = "app_secret=" + cm.getAppSecret() + "&noncestr=" + cm.getNoncestr() + "&timestamp=" + cm.getTimestamp() + "&app_id=" + cm.getAppId();
//		try {
//			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
//			crypt.reset();
//			crypt.update(string1.getBytes("UTF-8"));
//			signature = byteToHex(crypt.digest());
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		cm.setSignature(signature);
//		return cm;
//	}
	public static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	public static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}
	public static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	public static CalendarMd5 checkSignature(CalendarMd5 cm) {
		String signature = "";
		// 注意这里参数名必须全部小写，且必须有序
		String string1 = "app_secret=" + cm.getAppSecret() + "&noncestr=" + cm.getNoncestr() + "&timestamp=" + cm.getTimestamp() + "&app_id=" + cm.getAppId();
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		cm.setSignature(signature);
		return cm;
	}
}
