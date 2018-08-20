package com.mujugroup.data.bean;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.mujugroup.data.service.feign.ModuleCoreService;

import java.io.Serializable;

public class ActiveBean implements Serializable {

    private String refDate;

    @MergeField(defaultValue = "0",feign = ModuleCoreService.class, method = "getActiveValue", isQueryByParam = true)
    private String value;


    public ActiveBean(String refDate) {
        this.refDate = refDate;
        this.value = refDate;
    }


    public String getRefDate() {
        return refDate;
    }

    public void setRefDate(String refDate) {
        this.refDate = refDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
