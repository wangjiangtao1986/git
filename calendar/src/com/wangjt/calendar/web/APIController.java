package com.wangjt.calendar.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wangjt.calendar.mysql.dao.model.CalendarMd5;
import com.wangjt.calendar.mysql.dao.model.CalendarUser;
import com.wangjt.calendar.service.CalendarMd5Service;
import com.wangjt.calendar.service.CalendarUserService;
import com.wangjt.calendar.util.StringUtile;
import com.wangjt.util.HttpClientUtil;
import com.wangjt.util.RSAUtil;
import com.wangjt.util.TicketUtil;
import com.wangjt.util.User;

import net.sf.json.JSONObject;

@Controller
public class APIController {

	@Autowired
	private CalendarMd5Service calendarMd5Service;

//	欢迎使用当前表样，也欢迎通过跨库视图更新数据，同时欢迎ETL工具处理数据
	@Autowired
	private CalendarUserService calendarUserService;
	
	/**
	 * 客户端加密代码
	 * 
	 * 加密后发送相应签名信息到calendar服务端注册；
	 * 构造参数访问calendar创建session
	 * 并转向到calendar首页 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/jiami.html")
	public String jiami(HttpServletRequest request) {
		String appSecret = "appSecret";
		String appId = "appId";
		String userId = request.getParameter("userId");
		try {
			byte[] data = (appSecret + " " + appId + " " + userId).getBytes();
//			业务数据公钥加密
//			String datastr = new String(RSAUtil.encrypt(data));
//			签名文本组装
			CalendarMd5 signSource = TicketUtil.getSignature(appSecret, appId, RSAUtil.encrypt(data));
//			System.out.println("签名后："+datastr.length());
//			System.out.println("签名后："+JSONObject.fromObject(signSource).toString());
			signSource.setUserId(userId);
			
//			他如何吧数据存储到我的数据库呢？
			calendarMd5Service.createCalendarMd5(signSource);

			HttpClientUtil.sendRequestString("http://localhost:8090/calendar/regist.html", signSource);
			
		    return "redirect:/createCalendarMd5.html?" + 
	    	"signature=" + signSource.getSignature() + "&" + 
	    	"timestamp=" + signSource.getTimestamp() + "&" + 
	    	"appId=" + signSource.getAppId() + "&" + 
	    	"appSecret=" + signSource.getAppSecret() + "&" + 
	    	"noncestr=" + signSource.getNoncestr() + "&" + 
	    	"userId=" + signSource.getUserId()
		    ;  
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	    return "redirect:/regist.html";
	}

	/**
	 * http服务保存
	 * @param signSource
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/regist.html")
	public String regist(HttpServletRequest request) {
		try {

			String jsonStr = HttpClientUtil.readFromRequestBody(request);

			JSONObject jo = JSONObject.fromObject(jsonStr);
			CalendarMd5 calendarMd5 = (CalendarMd5)JSONObject.toBean(jo, CalendarMd5.class);  
			
			System.out.println("jsonStr = " + jsonStr);

		    return calendarMd5Service.createCalendarMd5(calendarMd5) + "";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	    return "redirect:/createCalendarMd5.html";
	}

	/**
	 * 注册用户信息
	 * @param signSource
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/createCalendarUser.html")
	public String createCalendarUser(HttpServletRequest request) {
		try {

			String jsonStr = HttpClientUtil.readFromRequestBody(request);

			JSONObject jo = JSONObject.fromObject(jsonStr);
			CalendarUser CalendarUser = (CalendarUser)JSONObject.toBean(jo, CalendarUser.class);  
			
			System.out.println("jsonStr = " + jsonStr);

		    return calendarUserService.createCalendarUser(CalendarUser) + "";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	    return "0";
	}
	
	@RequestMapping(value = "/createCalendarMd5.html")
	public String createCalendarMd5(HttpServletRequest request, CalendarMd5 record, ModelMap mm) {
		System.out.println("---------createCalendarMd5.html-------");

//		解密 appSecret;appId;userID
		if(null==record || StringUtile.isNull(record.getSignature())) {
//			缺乏必要的参数问题
			mm.addAttribute("obj", "错误，缺乏必要参数");
		} else {
			CalendarMd5 cm = calendarMd5Service.getCalendarMd5MapperById(record.getSignature());
			
			if(null == cm || StringUtile.isNull(cm.getSignature())){
//				数据库中未查到相应的数据
				mm.addAttribute("obj", "错误，缺乏必要参数");
			} else {
				record = TicketUtil.checkSignature(record);
				
//				数据已经处理过，数据已过期
				if(null == cm || StringUtile.isNull(cm.getSignature()) || "y".equals(cm.getFlag())) {
					mm.addAttribute("obj", "错误，缺乏必要参数");
					
				} else {
					
//					比较 userid
//					String datastr = cm.getDatabyte();
					try {
						String data = new String(RSAUtil.decrypt(cm.getDatabyte()));
						String[] arr = data.split(" ");

						String appSecret = arr[0];
						String appId = arr[1];
						String userId = arr[2];

						if( !StringUtile.isNull(appSecret) && !StringUtile.isNull(appId) && !StringUtile.isNull(userId) &&
								userId.equals(cm.getUserId()) && appId.equals(cm.getAppId()) && appSecret.equals(cm.getAppSecret())) {

							User user = (User) request.getSession().getAttribute("user");
							user = new User();
							user.setUserId(userId);
							request.getSession().setAttribute("user", user);
							
//							构造session
							cm.setFlag("y");
							calendarMd5Service.updateByPrimaryKeySelective(cm);
						} else {
//							数据校验失败
							mm.addAttribute("obj", "错误，缺乏必要参数");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	    return "redirect:/calendarIndex.html";  
	}
}