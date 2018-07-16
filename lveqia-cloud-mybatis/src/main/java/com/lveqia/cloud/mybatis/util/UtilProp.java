package com.lveqia.cloud.mybatis.util;

import com.lveqia.cloud.mybatis.sql.BaseInfo;
import com.lveqia.cloud.mybatis.sql.DataSource;

import java.io.*;
import java.util.Properties;


/**
 * 解析config.xml
 * @author leolaurel
 */
public class UtilProp {
	public static BaseInfo params = new BaseInfo();
	public static DataSource dataSource = new DataSource();
	static{
		init();
	}
	private static void init(){
		Properties properties = new Properties();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(new File(UtilPath.getClassResources()
					,"application.properties")));
			properties.load(bufferedReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		dataSource.setDriver(properties.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(properties.getProperty("spring.datasource.url"));
		dataSource.setUsername(properties.getProperty("spring.datasource.username"));
		dataSource.setPassword(properties.getProperty("spring.datasource.password"));
	}
	public static BaseInfo getParams() {
		return params;
	}
	public static void setParams(BaseInfo params) {
		UtilProp.params = params;
	}
	public static DataSource getDataSource() {
		return dataSource;
	}
	public static void setDataSource(DataSource dataSource) {
		UtilProp.dataSource = dataSource;
	}
}
