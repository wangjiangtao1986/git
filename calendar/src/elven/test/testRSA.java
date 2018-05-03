package elven.test;

import com.wangjt.calendar.mysql.dao.model.CalendarMd5;
import com.wangjt.util.RSAUtil;
import com.wangjt.util.TicketUtil;

import elven.encryption.MD5Util;
import net.sf.json.JSONObject;

public class testRSA {

	//待加密原文
	public static final String DATA = "hi, welcome to my git area!";
	
	public static void main(String[] args) throws Exception {
//		Map<String, Object> keyMap = RSAUtil.initKey();
////		RSAUtil.initKeyFile();
//		
//		RSAPublicKey rsaPublicKey = RSAUtil.getpublicKey(keyMap);
//		RSAPrivateKey rsaPrivateKey = RSAUtil.getPrivateKey(keyMap);
//		System.out.println("RSA PublicKey: " + rsaPublicKey);
//		System.out.println("RSA PrivateKey: " + rsaPrivateKey);
//		
//		byte[] rsaResult = RSAUtil.encrypt(DATA.getBytes(), rsaPublicKey);
//		System.out.println(DATA + "====>>>> RSA 加密>>>>====" + BytesToHex.fromBytesToHex(rsaResult));
//		
//		byte[] plainResult = RSAUtil.decrypt(rsaResult, rsaPrivateKey);
//		System.out.println(DATA + "====>>>> RSA 解密>>>>====" + new String(plainResult));
//		
//		System.out.println(DATA + "====>>>> DATA Bytes>>>>====" + new String(plainResult));

		jiami("appSecret", "appId", "userId");
		
	}
	

	public static void jiami(String appSecret, String appId, String userId) {

		try {

//			RSAUtil.initKeyFile();

			byte[] data = (appSecret + " " + appId + " " + userId).getBytes();
			
//			秘钥，公钥加密
			byte[] rsaResult = RSAUtil.encrypt(data);
//
//////		获取公钥？
//			String secret_key = new String(rsaResult);

//			业务数据加密 secret_key
			String datastr = new String(rsaResult);

//			签名文本组装
			CalendarMd5 signSource = TicketUtil.getSignature(appSecret, appId, datastr);

//			System.out.println("签名后："+MD5Util.MD5Encode(signSource.getSignature()));
			System.out.println("签名后："+JSONObject.fromObject(signSource).toString());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
