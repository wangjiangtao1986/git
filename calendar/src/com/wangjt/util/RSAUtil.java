package com.wangjt.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSAUtil {

	public static final String PUBLIC_KEY = "RSAPublicKey";
	public static final String PRIVATE_KEY = "RSAPrivateKey";
	private static String privateKeyFile = "privateKey.keystore";
	private static String publickeyFile = "publickey.keystore";
	
	/**
	 * 生成RSA的公钥和私钥
	 */
	public static Map<String, Object> initKey() throws Exception{
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);  //512-65536 & 64的倍数
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		
		return keyMap;
	}

	/**
	 * 生成RSA的公钥和私钥
	 */
	public static void initKeyFile() throws Exception{
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);  //512-65536 & 64的倍数
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(publickeyFile));
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
		oos1.writeObject(publicKey);
		oos2.writeObject(privateKey);
		/** 清空缓存，关闭文件输出流 */
		oos1.close();
		oos2.close();
	}
	
	
	/**
	 * 获得公钥
	 */
	public static RSAPublicKey getpublicKey(Map<String, Object> keyMap){
		RSAPublicKey publicKey = (RSAPublicKey) keyMap.get(PUBLIC_KEY);
		return publicKey;
	}
	
	/**
	 * 获得私钥
	 */
	public static RSAPrivateKey getPrivateKey(Map<String, Object> keyMap){
		RSAPrivateKey privateKey = (RSAPrivateKey) keyMap.get(PRIVATE_KEY);
		return privateKey;
	}
	
	/**
	 * 公钥加密
	 */
	public static byte[] encrypt(byte[] data, RSAPublicKey publicKey) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] cipherBytes = cipher.doFinal(data);
		return cipherBytes;
	}
	
	/**
	 * 公钥加密
	 */
	public static byte[] encrypt(byte[] data) throws Exception{
		RSAPublicKey publicKey = loadRSAPublicKey();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] cipherBytes = cipher.doFinal(data);
		return cipherBytes;
	}
	
	private static RSAPublicKey loadRSAPublicKey() {
		  /** 将文件中的公钥对象读出 */
        ObjectInputStream ois = null;
        RSAPublicKey key = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("D:\\wangjt\\Workspaces10\\calendar\\publickey.keystore"));
			key = (RSAPublicKey) ois.readObject();
		} catch (Exception e1) {
			e1.printStackTrace();
			try {
				if(null != ois){ois.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return key;
	}

	/**
	 * 私钥解密
	 */
	public static byte[] decrypt(byte[] data, RSAPrivateKey privateKey) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] plainBytes = cipher.doFinal(data);
		return plainBytes;
	}

	/**
	 * 私钥解密
	 */
	public static byte[] decrypt(byte[] data) throws Exception{
		RSAPrivateKey privateKey = loadRSAPrivateKey();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] plainBytes = cipher.doFinal(data);
		return plainBytes;
	}

	private static RSAPrivateKey loadRSAPrivateKey() {
		  /** 将文件中的公钥对象读出 */
        ObjectInputStream ois = null;
        RSAPrivateKey key = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("D:\\wangjt\\Workspaces10\\calendar\\privateKey.keystore"));
			key = (RSAPrivateKey) ois.readObject();
		} catch (Exception e1) {
			e1.printStackTrace();
			try {
				if(null != ois){ois.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return key;
	}

}
