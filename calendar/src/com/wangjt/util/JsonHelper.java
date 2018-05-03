package com.wangjt.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonHelper {

	public static String ToJson(Object obj) throws Exception {
		// 根据返回对象的类型，采用不同的序列化策略
		String result;
		if (obj instanceof Collection) {
			JSONArray json = JSONArray.fromObject(obj);
			result = json.toString();
		} else if (obj instanceof String) {
			result = obj.toString();
		} else {
			JSONObject json = JSONObject.fromObject(obj);
			result = json.toString();
		}
		return result;
	}

	public static String ToPageJson(List<Object> list) throws Exception {
		// 根据返回对象的类型，采用不同的序列化策略
		JSONObject json = new JSONObject();
		json.put("totalProperty", list.size() + "");
		JSONArray arr = JSONArray.fromObject(list);
		json.put("root", arr);
		String result = json.toString();
		return result;
	}

	public static String ToSimpleJson(List<Object> list) throws Exception {
		// 根据返回对象的类型，采用不同的序列化策略
		JSONArray arr = JSONArray.fromObject(list);
		String result = arr.toString();
		result.replaceAll("\"address\":", "");
		return result;
	}

	public static Object Json2Bean(String jsonString, Class<?> beanClass) throws Exception {
		JSONObject jsonObject = null;
		try {
			jsonObject = JSONObject.fromObject(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toBean(jsonObject, beanClass);
	}

	public static List<Object> Json2List(String jsonString, Class<?> beanClass) {
		List<Object> list = new ArrayList<Object>();
		if (jsonString.length() > 0) {
			JSONArray array = JSONArray.fromObject(jsonString);
			for (@SuppressWarnings("unchecked")
			Iterator<Object> iter = array.iterator(); iter.hasNext();) {
				JSONObject jsonObject = (JSONObject) iter.next();
				list.add(JSONObject.toBean(jsonObject, beanClass));
			}
		}
		return list;
	}
}