package com.lveqia.cloud.common.util;

import org.apache.commons.codec.binary.Base64;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



/**
 * 
 * @author leolaurel
 * @version 1.0
 */
public class AESUtil {
	public final static String SPLIT = "#W#X#";
	public final static String PASS = "cloud-module";

	/**
	 * AES加密
	 * @param content 被加密内容
	 * @param password 秘钥
	 */
	public static String aesEncrypt(String content, String password) throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(password.getBytes());
		keyGenerator.init(128, random);

		SecretKey secretKey = keyGenerator.generateKey();
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
	 */
	public static String aesDecrypt(String content, String password) throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(password.getBytes());
		keyGenerator.init(128, random);

		SecretKey secretKey = keyGenerator.generateKey();
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

	/**
	 * 对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充。
	 * 对称解密的目标密文为 Base64_Decode(encryptedData)。
	 * 对称解密秘钥 aeskey = Base64_Decode(session_key), aeskey 是16字节。
	 * 对称解密算法初始向量 为Base64_Decode(iv)，其中iv由数据接口返回。
	 */
	public static String wxDecrypt(String content, String key, String iv) throws Exception {
		// 让java支持PKCS7Padding
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
		Key sKeySpec = new SecretKeySpec(Base64.decodeBase64(key), "AES");
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(Base64.decodeBase64(iv)));// 初始化
		byte[] result = cipher.doFinal(Base64.decodeBase64(content));
		if(null != result && result.length > 0){
			return new String(result, "UTF-8");
		}
		return null;
	}

	//生成iv
	private static AlgorithmParameters generateIV(byte[] iv) throws Exception{
		AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
		params.init(new IvParameterSpec(iv));
		return params;
	}


	public static void main(String[] args) throws Exception {


		String key = "tiihtNczf5v6AKRyjwEUhQ==";

		String date = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";


		String iv = "r7BXXKkLb8qrSNn05n0qiA==";
		System.out.println(wxDecrypt(date, key, iv));

	}


}
