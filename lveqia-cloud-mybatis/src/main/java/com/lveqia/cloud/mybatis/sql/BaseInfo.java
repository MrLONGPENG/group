package com.lveqia.cloud.mybatis.sql;

import com.lveqia.cloud.mybatis.Constants;
import com.lveqia.cloud.mybatis.util.UtilDate;

/**
 * @author leolaurel
 */
public class BaseInfo {
	public String author = Constants.DEFAULT_AUTHOR;
	public String projectName = Constants.DEFAULT_PROJECT;
	public String packageName = Constants.DEFAULT_PACKAGE;
	public String createTime = UtilDate.getToday();


	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
