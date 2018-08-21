package com.mujugroup.data.bean;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.data.service.feign.ModuleCoreService;
import com.mujugroup.data.service.feign.ModuleWxService;

import java.io.Serializable;

/**
 * 概览接口返回数据
 */
public class OverviewInfo implements Serializable {

    @MergeField(feign = ModuleWxService.class, method = "getTotalUserCount"
            , isQueryByParam = true, isCache = true, defaultValue = Constant.DIGIT_ZERO)
    private String totalUser;

    @MergeField(feign = ModuleCoreService.class, method = "getTotalActiveCount"
            , isQueryByParam = true, isCache = true, defaultValue = Constant.DIGIT_ZERO)
    private String totalActive;
    
    @MergeField(feign = ModuleWxService.class, method = "getYesterdayUsageCount"
            , isQueryByParam = true, isCache = true, defaultValue = Constant.DIGIT_ZERO)
    private String yesterdayUsageCount;

    public OverviewInfo(String totalUser, String totalActive, String yesterdayUsageCount) {
        this.totalUser = totalUser;
        this.totalActive = totalActive;
        this.yesterdayUsageCount = yesterdayUsageCount;
    }

    public String getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(String totalUser) {
        this.totalUser = totalUser;
    }

    public String getTotalActive() {
        return totalActive;
    }

    public void setTotalActive(String totalActive) {
        this.totalActive = totalActive;
    }

    public String getYesterdayUsageCount() {
        return yesterdayUsageCount;
    }

    public void setYesterdayUsageCount(String yesterdayUsageCount) {
        this.yesterdayUsageCount = yesterdayUsageCount;
    }
}
