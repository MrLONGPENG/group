package com.mujugroup.data.bean;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.data.service.feign.ModuleWxService;

import java.io.Serializable;

/**
 * 统计使用情况
 */
public class StaUsage implements Serializable {

    private String refDate;

    @MergeField(defaultValue = Constant.DIGIT_ZERO, feign = ModuleWxService.class, method = "getUsageCount"
            , isQueryByParam = true)
    private String usage;


    public StaUsage(String refDate) {
        this.refDate = refDate;
        this.usage = refDate;
    }


    public String getRefDate() {
        return refDate;
    }

    public void setRefDate(String refDate) {
        this.refDate = refDate;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}
