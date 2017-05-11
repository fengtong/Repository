package com.wangzhixuan.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESUtils {

	private static Key key;
	private static String KEY_STR = "myKey"; //秘钥
	
	static {
		
		try {
			//获取KeyGenerator类型的对象成为工厂类，方法getInstance（ ）的参数为字符串类型，指定加密算法的名称。
			//其中“DES”是目前最常用的对称加密算法，但安全性较差。
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			//初始化密钥生成器
			generator.init(new SecureRandom(KEY_STR.getBytes()));
			//生成密钥
			key = generator.generateKey();
			//释放
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getEncryptString(String str) {
		
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byte[] strBytes = str.getBytes("UTF8");
			//Cipher类是一个引擎类，它需要通过getInstance()工厂方法来实例化对象。
			//下述实例化操作是一种最为简单的实现，并没有考虑DES分组算法的工作模式和填充模式，可通过以下方式对其设定：
			//Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");
			Cipher cipher = Cipher.getInstance("DES");
			//初始化Cipher对象，用于包装
			cipher.init(Cipher.ENCRYPT_MODE, key);
			//包装秘密密钥
			byte[] encryptStrBytes = cipher.doFinal(strBytes);
			return base64en.encode(encryptStrBytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public static String getDecryptString(String str) {
		
		BASE64Decoder base64de = new BASE64Decoder();
		try {
			byte[] strBytes = base64de.decodeBuffer(str);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decryptStrBytes = cipher.doFinal(strBytes);
			return new String(decryptStrBytes,"UTF8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public static void main(String[] args) throws Exception{
		if(args == null || args.length < 1) {
			System.out.println("请输入要加密的字符，用空格分隔。");
		}else {
			for(String arg : args) {
				System.out.println(arg + ":" + getEncryptString(arg));
			}
		}
//		String string1 = DESUtils.getEncryptString("root");
//		String string2 = DESUtils.getEncryptString("123456");
//		System.out.println(string1);
//		System.out.println(string2);
	}
	
	
	
}
