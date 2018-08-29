package com.mujugroup.data.utils;

import com.mujugroup.data.objeck.bo.ExcelBO;

import java.util.List;

public class ExcelData {


    // 表头
    private String[] titles;

    // 数据
    private List<ExcelBO> rows;

    // 页签名称
    private String name;

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public List<ExcelBO> getRows() {
        return rows;
    }

    public void setRows(List<ExcelBO> rows) {
        this.rows = rows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
