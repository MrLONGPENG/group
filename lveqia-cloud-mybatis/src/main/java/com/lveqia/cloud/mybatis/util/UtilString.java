package com.lveqia.cloud.mybatis.util;

import java.io.File;

/**
 * @author leolaurel
 */
public class UtilString {
	/**
	 * 首字母大写p
	 */
	static String capitalize(String str) {
		if (null == str) {
			return null;
		} else if ("".equals(str)) {
			return str;
		}
		return Character.toTitleCase(str.charAt(0)) + str.substring(1);
	}

	/**
	 * 将包名中的"."替换成"/"
	 * @param packageName 包名
	 */
	public static String packageToPath(String packageName) {
		if (packageName == null) {
			return null;
		}
		StringBuilder fieldName = new StringBuilder();
		for (int i = 0; i < packageName.length(); i++) {
			char ch = packageName.charAt(i);
			if (ch == '.') {
				fieldName.append(File.separatorChar);
			} else {
				fieldName.append(ch);
			}
		}
		return fieldName.toString();
	}

	/**
	 * 表面t_开头去掉，后面根据"_"字母大写，首字母小写
	 * @param tableName 表中列名
	 * @return java类对象名字，首字母小写的驼峰写法
	 */
	static String getFieldName(String tableName) {
		if (tableName == null) {
			return null;
		}
		if(tableName.startsWith("t_")) tableName = tableName.substring(2);
		return dbNameToVarName(tableName);
	}
	/**
	 * 将表中列名去下划线且下划线后首字母大写,其他字母小写
	 * @param columnName 表中列名
	 * @return java类属性名
	 */
	static String dbNameToVarName(String columnName) {
		if (columnName == null) {
			return null;
		}
		StringBuilder fieldName = new StringBuilder();
		boolean toUpper = false;
		for (int i = 0; i < columnName.length(); i++) {
			char ch = columnName.charAt(i);
			if (ch == '_') {
				toUpper = true;
			} else if (toUpper) {
				fieldName.append(Character.toUpperCase(ch));
				toUpper = false;
			} else {
				fieldName.append(ch);//fieldName.append(Character.toLowerCase(ch));
			}
		}
		return fieldName.toString();
	}



	/**
	 * 将数据库类型转换成java类型
	 * @param columnType 数据库类型
	 * @return java类型
	 */
	static String dbTypeToJavaType(String columnType) {
		String type;
		if (columnType == null || columnType.trim().equals("")) {
			return null;
		}
		switch (columnType){
			case "VARCHAR":case "CHAR":case "TEXT":case "LONGTEXT":
				type = "String"; break;
			case "TIMESTAMP":case "DATETIME":case "DATE":
				type = "Date"; break;
			case "INT":case "INT UNSIGNED":case "TINYINT": case "SMALLINT":
				type = "Integer"; break;
			case "BIT":
				type = "Boolean"; break;
			case "DOUBLE":
				type = "Double"; break;
			case "FLOAT":
				type = "Float"; break;
			case "BIGINT":
				type = "Long"; break;
			case "MEDIUMBLOB":case "VARBINARY":
				type = "byte[]"; break;
			case "DECIMAL":
				type = "java.math.BigDecimal"; break;
			default:System.out.println("columnType[" + columnType + "]");
				type = null; break;

		}
		return type;
	}
//	/**
//	 * 将数据库类型转换成mybatis配置文件类型
//	 * @param sqlTypeName 数据库类型
//	 * @return mybatis配置文件类型
//	 */
//	public static String mybatisType(String sqlTypeName) {
//		String type = "";
//		if (sqlTypeName == null || sqlTypeName.trim().equals("")) {
//			return null;
//		}
//		if (sqlTypeName.equals("VARCHAR") || sqlTypeName.equals("TEXT")) {
//			type = "VARCHAR";
//		} else if (sqlTypeName.equals("TINYBLOB") || sqlTypeName.equals("BLOB")
//				|| sqlTypeName.equals("MEDIUMBLOB")
//				|| sqlTypeName.equals("VARBINARY")) {
//			type = "BLOB";
//		} else if (sqlTypeName.equals("CHAR")) {
//			type = "CHAR";
//		} else if (sqlTypeName.equals("INT") || sqlTypeName.equals("INT UNSIGNED")) {
//			type = "INTEGER";
//		} else if (sqlTypeName.equals("DATETIME") || sqlTypeName.equals("DATE")) {
//			type = "TIMESTAMP";
//		} else if (sqlTypeName.equals("DECIMAL")) {
//			type = "DECIMAL";
//		} else {
//			System.out.println("sqlTypeName[" + sqlTypeName + "]");
//			type = null;
//		}
//		return type;
//	}
//	public static String getFileName(String tableName, String flag){
//		String retName;
//		switch(flag){
//		case "po"  : retName = tableName + "_po"; break;
//		case "vo"  : retName = tableName + "_vo"; break;
//		default : retName = null;
//		}
//		return retName;
//	}
}
