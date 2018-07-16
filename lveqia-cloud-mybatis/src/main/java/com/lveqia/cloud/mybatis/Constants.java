package com.lveqia.cloud.mybatis;

import com.lveqia.cloud.mybatis.util.UtilProp;

/**
 * @author leolaurel
 */
public class Constants {
	public static boolean isDebug = false;
	public static final String DEFAULT_AUTHOR    = "leolaurel";
	public static final String DEFAULT_PROJECT   = "lveqia-cloud-mybatis";
	public static final String DEFAULT_PACKAGE   = "com.lveqia.cloud.demo";

	public static final String DRIVER   = UtilProp.dataSource.getDriver();
	public static final String URL      = UtilProp.dataSource.getUrl();
	public static final String USER     = UtilProp.dataSource.getUsername();
	public static final String PASSWORD = UtilProp.dataSource.getPassword();

}