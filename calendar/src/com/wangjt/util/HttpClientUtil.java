package com.wangjt.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import com.wangjt.calendar.mysql.dao.model.CalendarMd5;

import net.sf.json.JSONObject;

public class HttpClientUtil {
	


    /**
   	 * 解析请求的输入流 ，为String字符
   	 * @param request
   	 * @return
   	 * @throws Exception
   	 */
	public static String readFromRequestBody(HttpServletRequest request) throws Exception {
   		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(),"UTF-8"));
   		String line = null;
   		StringBuilder sb = new StringBuilder();
   		while ((line = br.readLine()) != null) {sb.append(line);}
   		br.close();
   		return sb.toString();
   	}

	public static String sendRequestString(String url,CalendarMd5 cm) {
		
		JSONObject json=JSONObject.fromObject(cm);
		String str= json.toString();
		String result = httpPostUrl(url, str);

		return result;
	}
 
	@SuppressWarnings("unused")
	private static String httpPostUrl(String url, String str) {
		String result = "";
		BufferedReader in = null;
		DataOutputStream out = null;
		System.out.println("request：" + str);

		try {

			URL realUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();

			connection.setDoInput(true);

			connection.setDoOutput(true);

			connection.setRequestMethod("POST");

			connection.setUseCaches(false);

			connection.setInstanceFollowRedirects(true);

			connection.setRequestProperty("Content-Type", "application/text; charset=UTF-8");

			connection.connect();

			out = new DataOutputStream(connection.getOutputStream());

			String token = "d5f224c9f83874da5b5025794c773e8e";

			out.write(str.getBytes("UTF-8"));

			out.flush();

			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			String lines;

			StringBuffer sbf = new StringBuffer();

			while ((lines = reader.readLine()) != null) {

				lines = new String(lines.getBytes(), "utf-8");

				sbf.append(lines);

			}

			// System.out.println(sbf);
			result = sbf.toString();
			reader.close();

			// 断开连接

			connection.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return result;
	}
}
