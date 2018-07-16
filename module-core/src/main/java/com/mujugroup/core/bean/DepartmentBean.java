package com.mujugroup.core.bean;

import java.io.Serializable;

public class DepartmentBean implements Serializable {

    private Integer id;

    private Integer hospitalId;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
