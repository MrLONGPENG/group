package com.lveqia.cloud.common;

import java.security.MessageDigest;
import java.util.*;

/**
 *
 * @ClassName: MD5Util.java
 * @Title:MD5签名处理核心文件，不需要修改
 * @Description:以下代码只是为了方便合作方测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码
 * @Copyright: Copyright (c) 2016
 * @Company: www.blueplus.cc
 *
 * @author BluePlus
 * @date:2016年9月1日
 * @version 1.0
 */
public class MD5Util {

	/**
	 * MD5加密调用
	 *
	 * @param params
	 * @return
	 */
	public static String getSignAndMD5(String... params) {
		String sign = getSign(params);
		return strToMd5(sign, "utf-8");
	}

	/**
	 * MD5加密
	 *
	 * @param content
	 *            被加密字符串
	 * @param charSet
	 *            字符集
	 * @return
	 */
	private static String strToMd5(String content, String charSet) {
		String md5Str = null;
		if (content != null && content.length() != 0) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(content.getBytes(charSet));
				byte b[] = md.digest();
				int i;
				StringBuffer buf = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						buf.append("0");
					buf.append(Integer.toHexString(i));
				}
				md5Str = buf.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return md5Str;
	}

	/**
	 * 按照字典序逆序拼接参数
	 *
	 * @param params
	 * @return
	 */
	private static String getSign(String... params) {
		List<String> srcList = new ArrayList<String>();
		for (String param : params) {
			srcList.add(param);
		}
		// 按照字典序逆序拼接参数
		Arrays.sort(params);
		Collections.sort(srcList, String.CASE_INSENSITIVE_ORDER);
		Collections.reverse(srcList);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < srcList.size(); i++) {
			sb.append(srcList.get(i));
		}
		return sb.toString();
	}


	/**
	 * MD5编码
	 * @param origin 原始字符串
	 * @return 经过MD5加密之后的结果
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = origin;
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}


	private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
			"8", "9", "a", "b", "c", "d", "e", "f"};

	/**
	 * 转换字节数组为16进制字串
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuilder resultSb = new StringBuilder();
		for (byte aB : b) {
			resultSb.append(byteToHexString(aB));
		}
		return resultSb.toString();
	}

	/**
	 * 转换byte到16进制
	 * @param b 要转换的byte
	 * @return 16进制格式
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}


	@SuppressWarnings("rawtypes")
	public static String toParams(String characterEncoding,
								  SortedMap<Object, Object> parameters,String app_key) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + app_key);
		System.out.println("字符串拼接后是：" + sb.toString());
		String sign = MD5Util.getSignAndMD5(sb.toString()).toUpperCase();
		return sign;
	}
	@SuppressWarnings("rawtypes")
	public static String createSign(String characterEncoding,
									SortedMap<Object, Object> parameters,String app_key) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + app_key);
		System.out.println("字符串拼接后是：" + sb.toString());
		String sign = MD5Util.MD5Encode(sb.toString()).toUpperCase();
		return sign;
	}


	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 *
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}


	public static void main(String[] args) {
		//System.out.println("md5:" + getSignAndMD5("yaogang=yaogang&key=yaogang").toUpperCase());
		String params="accessKeyId=283C0661B1BA742&orderNo=1234567&orderOuterNo=987654&rechargeState=1&total_fee=2B90EA97CE1CC43B4FBD6AAFC213115";
		String sign = MD5Util.MD5Encode(params);
		System.out.println(sign);
	}
}