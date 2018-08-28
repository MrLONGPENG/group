package com.lveqia.cloud.mybatis;

import com.lveqia.cloud.mybatis.sql.BaseInfo;
import com.lveqia.cloud.mybatis.sql.TableInfo;
import com.lveqia.cloud.mybatis.util.*;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author leolaurel
 */
public class Generate {
	private static final String rootPath = "D:\\Workspace\\Java\\muju-cloud\\lveqia-cloud-mybatis\\src\\main\\java";
	private static final char FS = File.separatorChar;
	public static void main(String[] args) {
		BaseInfo params = UtilProp.getParams();
		params.setProjectName("module-core");//params.setProjectName("lveqia-cloud-zuul");
		params.setPackageName("com.mujugroup.core");//params.setPackageName("com.lveqia.cloud.zuul");
		List<String> tables = getTables();UtilDB.getTableNames();
		String basePath = UtilString.packageToPath(params.getPackageName());
		Map<Object, Object> map = new HashMap<>();
		for (String table : tables) {
			map.clear();
			System.out.println(table);
			generate(params, basePath, map, table);
		}
	}

	private static List<String> getTables() {
		List<String> tableNames = new LinkedList<>();
		tableNames.add("t_sys_manager");
		tableNames.add("t_country_province_city");
		return tableNames.size()==0 ? UtilDB.getTableNames():tableNames;
	}

	private static void generate(BaseInfo params, String basePath, Map<Object, Object> map, String table) {
		TableInfo info;
		map.put("tables",	info = UtilDB.getTableInfoByTableName(table));
		map.put("columns",	UtilDB.getColumnInfoByTableName(table));
		map.put("baseInfo",	params);
		generateModel(basePath, map, info);
		generateSqlProvider(basePath, map, info);
		generateMapper(basePath, map, info);
		generateService(basePath, map, info);
		generateServiceImpl(basePath, map, info);
		generateController(basePath, map, info);
	}
	private static void generateModel(String basePath, Map<Object, Object> map, TableInfo info) {
		String modelName = rootPath + FS + basePath+ FS +"model" + FS + info.getClassName() + ".java";
		UtilFreemarker.generateFile(modelName, "ModelTemplate", map);
	}

	private static void generateSqlProvider(String basePath, Map<Object, Object> map, TableInfo info) {
		String modelName = rootPath + FS + basePath+ FS +"sql" + FS + info.getClassName() + "SqlProvider.java";
		UtilFreemarker.generateFile(modelName, "SqlProviderTemplate", map);
	}

	private static void generateMapper(String basePath, Map<Object, Object> map, TableInfo info) {
		String modelName = rootPath + FS + basePath+ FS +"mapper" + FS + info.getClassName() + "Mapper.java";
		UtilFreemarker.generateFile(modelName, "MapperTemplate", map);
	}

	private static void generateService(String basePath, Map<Object, Object> map, TableInfo info) {
		String modelName = rootPath + FS + basePath+ FS +"service" + FS + info.getClassName() + "Service.java";
		UtilFreemarker.generateFile(modelName, "ServiceTemplate", map);
	}

	private static void generateServiceImpl(String basePath, Map<Object, Object> map, TableInfo info) {
		String modelName = rootPath + FS + basePath+ FS +"service" + FS +"impl" + FS + info.getClassName() + "ServiceImpl.java";
		UtilFreemarker.generateFile(modelName, "ServiceImplTemplate", map);
	}

	private static void generateController(String basePath, Map<Object, Object> map, TableInfo info) {
		String modelName = rootPath + FS + basePath+ FS +"controller" + FS + info.getClassName() + "Controller.java";
		UtilFreemarker.generateFile(modelName, "ControllerTemplate", map);
	}

}
