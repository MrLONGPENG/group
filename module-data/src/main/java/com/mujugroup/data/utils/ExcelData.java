package com.mujugroup.data.utils;

import java.io.Serializable;
import java.util.List;

public class ExcelData implements Serializable {


    // 表头
    private String[] titles;

    // 数据
    private List<String[]> rows;

    // 页签名称
    private String name;

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public List<String[]> getRows() {
        return rows;
    }

    public void setRows(List<String[]> rows) {
        this.rows = rows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
