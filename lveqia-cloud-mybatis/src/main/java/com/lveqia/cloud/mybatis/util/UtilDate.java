package com.lveqia.cloud.mybatis.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author leolaurel
 */
public class UtilDate {
	public static String getToday(){
		String result;
		Date date = new Date();
		result = format(date);
		return result;
	}
	public static String format(Date date){
		String format = "yyyyMMdd";
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		return fmt.format(date);
	}
}
