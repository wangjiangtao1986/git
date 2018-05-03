package com.wangjt.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEMail {

//	public static void sendMessage(final String subject, final String content, final List<String> tos,
//			final Map<String, InputStreamSource> map, final SimpleMailMessage templateMailMessage,
//			final JavaMailSenderImpl javaMailSender) {
//		if (tos==null || tos.size()==0)
//			return;
//		new Thread() {
//			public void run() {
//					try {
////						发件人信息先行写死吧
//						String from = "375410692@163.com"; // 发件人邮箱地址
//						String user = "375410692@163.com"; // 发件人称号，同邮箱地址
//						String password = "wangjt19860828"; // 发件人邮箱客户端授权码
//						String smtp = "smtp.163.com";
//
//						Properties props = new Properties();
//						props.setProperty("mail.smtp.host", smtp); // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
//						props.put("mail.smtp.host", smtp); // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
//						props.put("mail.smtp.auth", "true"); // 用刚刚设置好的props对象构建一个session
//						Session session = Session.getDefaultInstance(props); // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
//																				// 用（你可以在控制台（console)上看到发送邮件的过程）
//						session.setDebug(true); // 用session为参数定义消息对象
//						MimeMessage message = new MimeMessage(session); // 加载发件人地址
//						message.setFrom(new InternetAddress(from));
//						Address[] as = new InternetAddress[tos.size()];
//						for (int i=0;i<tos.size();i++) {
//							String to = tos.get(i);
//							Address a = new InternetAddress(to);
//							as[i]=a;
////							Message.RecipientType.BCC 密送
////							Message.RecipientType.TO  发送
////							Message.RecipientType.CC  抄送
//						}
//						message.addRecipients(Message.RecipientType.TO, as); // 加载收件人地址
//						message.setSubject(subject); // 加载标题
//						Multipart multipart = new MimeMultipart(); // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
//						BodyPart contentPart = new MimeBodyPart(); // 设置邮件的文本内容
//						contentPart.setContent(content, "text/html;charset=utf-8");
//						multipart.addBodyPart(contentPart);
//						message.setContent(multipart);
//						message.saveChanges(); // 保存变化
//						Transport transport = session.getTransport("smtp"); // 连接服务器的邮箱
//						transport.connect(smtp, user, password); // 把邮件发送出去
//						transport.sendMessage(message, message.getAllRecipients());
//						transport.close();
//					} catch (MessagingException e) {
//						e.printStackTrace();
//					}
//				}
//			
//		}.start();
//	}

	public static void sendMessage(final String subject, final String content, final List<String> tos) {
		if (tos==null || tos.size()==0)
			return;
		new Thread() {
			public void run() {
					try {
//						发件人信息先行写死吧
						String from = "375410692@163.com"; // 发件人邮箱地址
						String user = "375410692@163.com"; // 发件人称号，同邮箱地址
						String password = "wangjt19860828"; // 发件人邮箱客户端授权码
						String smtp = "smtp.163.com";

						Properties props = new Properties();
						props.setProperty("mail.smtp.host", smtp); // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
						props.put("mail.smtp.host", smtp); // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
						props.put("mail.smtp.auth", "true"); // 用刚刚设置好的props对象构建一个session
						Session session = Session.getDefaultInstance(props); // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
																				// 用（你可以在控制台（console)上看到发送邮件的过程）
						session.setDebug(true); // 用session为参数定义消息对象
						MimeMessage message = new MimeMessage(session); // 加载发件人地址
						message.setFrom(new InternetAddress(from));
						Address[] as = new InternetAddress[tos.size()];
						for (int i=0;i<tos.size();i++) {
							String to = tos.get(i);
							Address a = new InternetAddress(to);
							as[i]=a;
//							Message.RecipientType.BCC 密送
//							Message.RecipientType.TO  发送
//							Message.RecipientType.CC  抄送
						}
						message.addRecipients(Message.RecipientType.TO, as); // 加载收件人地址
						message.setSubject(subject); // 加载标题
						Multipart multipart = new MimeMultipart(); // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
						BodyPart contentPart = new MimeBodyPart(); // 设置邮件的文本内容
						contentPart.setContent(content, "text/html;charset=utf-8");
						multipart.addBodyPart(contentPart);
						message.setContent(multipart);
						message.saveChanges(); // 保存变化
						Transport transport = session.getTransport("smtp"); // 连接服务器的邮箱
						transport.connect(smtp, user, password); // 把邮件发送出去
						transport.sendMessage(message, message.getAllRecipients());
						transport.close();
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}
			
		}.start();
	}


	public static void main(String[] args) { // 做测试用
		List<String> tos = new ArrayList<String>();
		tos.add("wangjiangtao@freedomsoft.com.cn");
//		tos.add("375410692@163.com");
		
//		测试直接嵌入IFRAME 不行
		SendEMail.sendMessage(getTitle("calendar","2018-05-05 06:30"), getContent("王江涛","测试一下是否可以","2018-05-05 06:30","2018-05-05 18:30"), tos
//				, null, null, null
				);
	}

	public static String getTitle(String title,String startTime) {
		return "您有待处理的日程：" + title + "，开始时间：" + startTime;
	}


	public static String getContent(String userName,String content,String startTime,String endTime) {
		return "<div style='width:100%'>" + userName + "，您好：</div>"
		+ "<div style='width:100%'>&nbsp;</div>"
		+ "<div style='width:100%'>&nbsp;&nbsp;&nbsp;&nbsp;<font  color=''>" + content + "</font>&nbsp;</div>"
		+ "<div style='width:100%'>&nbsp;&nbsp;&nbsp;&nbsp;<font  color=''>" + startTime + "至" + endTime + "</font>&nbsp;</div>"
		+ "<div style='width:100%'>&nbsp;</div>"
		+ "<div style='width:100%'>&nbsp;</div>"
		+ "<div style='width:100%'><font  color='red'>备注：邮件内容通过日程管理系统发送您，问题详情可登陆日程管理了解。</div>"
		+ "<div style='width:100%'><font  color='red'>http://39.105.28.243/calendar/</font>&nbsp;</div>";
	}
	

}
