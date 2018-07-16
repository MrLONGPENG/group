package com.lveqia.cloud.mybatis.sql;
/**
 * @author leolaurel
 */
public class TableInfo {
    /** 表名称 */
    private String tableName;

    /** 表转Java对象的类名 */
    private String className;

    /** 表转Java对象的属性名 */
    private String fieldName;

    /** 表注释 */
    private String comment;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
