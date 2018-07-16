package com.lveqia.cloud.mybatis.util;

import java.io.File;

/**
 * 路径工具类
 * @author leolaurel
 * 
 */
public class UtilPath {

	/*
	 * 获取classpath
	 */
	public static String getClasspath()
	{
		String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../")
				.replaceAll("file:/", "").replaceAll("%20", " ").trim();
		if (path.indexOf(":") != 1)
		{
			path = File.separator + path;
		}
		return path;
	}

	/*
	 * 获取classpath2
	 */
	public static String getClassResources() {
		String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")))
				.replaceAll("file:/", "").replaceAll("%20", " ").trim();
		if (path.indexOf(":") != 1) {
			path = File.separator + path;
		}
		return path;
	}


}
