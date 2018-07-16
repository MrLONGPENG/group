package com.lveqia.cloud.common;

import org.apache.commons.codec.binary.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



/**
 * 
 * @ClassName: AESUtil.java
 * @Title:AES加密处理核心文件，不需要修改
 * @Description:以下代码只是为了方便合作方测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码
 * @Copyright: Copyright (c) 2016
 * @Company: www.blueplus.cc
 * 
 * @author BluePlus
 * @date:2016年9月1日
 * @version 1.0
 */
public class AESUtil {
	public final static String SPLIT = "#W#X#";
	public final static String PASS = "cloud-module";

	public static String aesEncrypt(String content) {
		try {
			return aesEncrypt(content ,PASS);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * AES加密
	 * @param content 被加密内容
	 * @param password 秘钥
	 */
	public static String aesEncrypt(String content, String password) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(password.getBytes());
		kgen.init(128, random);

		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		byte[] byteContent = content.getBytes("utf-8");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] result = cipher.doFinal(byteContent);

		if ((result != null) && (result.length > 0)) {
			return Base64.encodeBase64URLSafeString(result);
		}
		return null;
	}

	public static String aesDecrypt(String content){
		try {
			return aesDecrypt(content, PASS);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * AES解密
	 * @param content 被加密内容
	 * @param password  秘钥
	 * @return
	 * @throws Exception
	 */
	public static String aesDecrypt(String content, String password) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(password.getBytes());
		kgen.init(128, random);

		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] result = cipher.doFinal(Base64.decodeBase64(content));

		if ((result != null) && (result.length > 0)) {
			return new String(result, "utf-8");
		}

		return null;
	}

	public static void main(String[] args) throws Exception {


		String content = "????test#W#X#test";
		String password = "cloud-module";
		System.out.println("encrptStr=" + content.length());
		String encrptStr = AESUtil.aesEncrypt(content, password);
		System.out.println("encrptStr=" + encrptStr);
		System.out.println("encrptStr=" + encrptStr.length());
		String decrptStr = AESUtil.aesDecrypt(encrptStr, password);

		System.out.println("decrptStr=" + decrptStr);
		long time = System.nanoTime();
		for (int i =0 ;i <100;i++){
			aesDecrypt("YSjJKy72oD69gmKDU9lVIVlmtiicUPJjtckIwvSrBofMlECezjhhKaZ3DUfRVZepbUsB-IJhj0uly_6B3E98Cw");
		}
		System.out.println((System.nanoTime() -time)/1000000);

		String aaa = "owod35O!@!fsdfsdfsdfsadfvasdf!@!sadhN";
		String[] arr = aaa.split("!@!");
		System.out.println(arr.length);

	}


}
