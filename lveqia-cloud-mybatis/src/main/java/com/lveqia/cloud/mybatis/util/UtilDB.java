package com.lveqia.cloud.mybatis.util;

import com.lveqia.cloud.mybatis.Constants;
import com.lveqia.cloud.mybatis.sql.ColumnInfo;
import com.lveqia.cloud.mybatis.sql.TableInfo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * @author leolaurel
 */
public class UtilDB {
	private static Connection conn      = null;
	private static Statement st         = null;
	private static ResultSet rs         = null;
	private static DatabaseMetaData dmd = null;
	/**
	 * 链接数据库
	 * @return 数据库连接
	 */
	public static Connection connection() {
		try {
			Class.forName(Constants.DRIVER);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(Constants.URL, Constants.USER, Constants.PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	/**
	 * 关闭链接
	 * @param rs
	 * @param st
	 * @param conn
	 */
	public static void release(ResultSet rs, Statement st, Connection conn) {
		try {
			try {
				if (null != rs) {
					rs.close();
				}
			} catch (Exception e) {
				rs = null;
			}
			try {
				if (null != st) {
					st.close();
				}
			} catch (Exception e) {
				st = null;
			}
			try {
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {
				conn = null;
			}
		} finally {
			rs = null;
			st = null;
			conn = null;
		}
	}




	/**
	 * 得到数据库中所有表名
	 * @return List<String>
	 */
	public static List<String> getTableNames() {
		List<String> tableNames = new LinkedList<String>();
		connection();
		try {
			dmd = conn.getMetaData();
			rs = dmd.getTables(null, dmd.getUserName(), null, null);
			while (rs.next()) {
				tableNames.add(rs.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		release(rs, st, conn);
		return tableNames;
	}
	/**
	 * 得到数据库表基本信息
	 * @param tableName 表名
	 * @return 表基本信息
	 */
	public static TableInfo getTableInfoByTableName(String tableName) {
		TableInfo tableInfo = new TableInfo();
		tableInfo.setTableName(tableName);
		tableInfo.setFieldName(UtilString.getFieldName(tableName));
		tableInfo.setClassName(UtilString.capitalize(tableInfo.getFieldName()));

		connection();
		try {
			String sql = "SHOW TABLE STATUS WHERE NAME = '" + tableName + "'";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			boolean have = rs.next();
			if(have){
				tableInfo.setComment(rs.getString("Comment"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		release(rs, st, conn);
		return tableInfo;
	}

	/**
	 * 根据表名查询表中所有列信息
	 * @param tableName 表名
	 * @return List<ColumnInfo>
	 */
	public static List<ColumnInfo> getColumnInfoByTableName(String tableName) {
		List<ColumnInfo> columnDataList = new LinkedList<>();
		connection();
		try {
			dmd = conn.getMetaData();
			rs = dmd.getColumns(null, dmd.getUserName(), tableName, null);
			ColumnInfo data;
			while (rs.next()) {
				data = new ColumnInfo();
				data.setRemarks(rs.getString("REMARKS"));
				data.setColumnName(rs.getString("COLUMN_NAME"));
				data.setColumnType(rs.getString("TYPE_NAME"));
				data.setFiledName(UtilString.dbNameToVarName(data.getColumnName()));
				data.setFiledType(UtilString.dbTypeToJavaType(data.getColumnType()));
				columnDataList.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		release(rs, st, conn);
		return columnDataList;
	}
}
