package com.mujugroup.core.objeck.vo;

import java.io.Serializable;

/**
 * Value Object
 * 下拉框  - {id,name}
 */
public class SelectVo implements Serializable {

    private int id;
    private String name;

    public SelectVo(){}

    public SelectVo(int id, String name){
        this.id=id;
        this.name=name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
