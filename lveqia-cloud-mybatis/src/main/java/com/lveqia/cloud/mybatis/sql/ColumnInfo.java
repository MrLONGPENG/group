package com.lveqia.cloud.mybatis.sql;
/**
 * @author leolaurel
 */
public class ColumnInfo {
    /** 列名称 */
    private String columnName;

    /** 列类型 */
    private String columnType;

    /** Java属性名称 */
    private String filedName;

    /** Java属性类型 */
    private String filedType;

    /** 列注释 */
    private String remarks;


    public String getColumnName() {
        return columnName;

    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public String getFiledType() {
        return filedType;
    }

    public void setFiledType(String filedType) {
        this.filedType = filedType;
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
