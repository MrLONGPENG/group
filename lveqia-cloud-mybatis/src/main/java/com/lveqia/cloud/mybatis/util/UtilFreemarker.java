package com.lveqia.cloud.mybatis.util;

import java.io.*;
import java.util.*;

import com.lveqia.cloud.mybatis.Constants;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * @author leolaurel
 */
public class UtilFreemarker {

//	/**
//	 * 通过文件名加载模版
//	 *
//	 * @param ftlName
//	 */
//	public static Template getTemplate(String ftlName){
//		try{
//			if(!ftlName.endsWith(".ftl")) ftlName+=".ftl";
//			Configuration config = new Configuration(Configuration.VERSION_2_3_23);
//			config.setEncoding(Locale.CHINA, "utf-8");
//			System.out.println(UtilPath.getClassResources());
//			config.setDirectoryForTemplateLoading(new File(UtilPath.getClassResources(),"ftl"));
//			Template temp = config.getTemplate(ftlName); // 在模板文件目录中找到名称为name的文件
//			return temp;
//		} catch (IOException e){
//			e.printStackTrace();
//		}
//		return null;
//	}

	public static void generateFile(String fileName, String templateName, Map<Object, Object> map){
		if(!templateName.endsWith(".ftl")) templateName+=".ftl";
		Configuration config = new Configuration(Configuration.VERSION_2_3_23);
		config.setDefaultEncoding("UTF-8");
		config.setTemplateLoader(new ClassTemplateLoader(UtilFreemarker.class, "/ftl"));
		config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		try{
			Template template = config.getTemplate(templateName); // getTemplate(templateName);
			if(Constants.isDebug){ // Debug模式打印到控制台
				template.process(map, new PrintWriter(System.out));
			}else{
				File file = new File(fileName);
				if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()){
					System.out.println("创建父目录文件失败！");
				}
				if(file.exists() && file.delete()) System.out.println("删除已存在的文件");
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
				template.process(map, out); // 模版输出
				out.close();
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
