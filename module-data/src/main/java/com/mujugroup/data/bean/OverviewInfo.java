package com.mujugroup.data.bean;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.StringUtil;
import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.data.service.feign.ModuleCoreService;
import com.mujugroup.data.service.feign.ModuleWxService;

import java.io.Serializable;

/**
 * 概览接口返回数据
 */
public class OverviewInfo implements Serializable {

    @MergeField(feign = ModuleCoreService.class, method = "getTotalActiveCount"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String totalActive;


    @MergeField(feign = ModuleWxService.class, method = "getUserCount"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String totalUser;


    @MergeField(feign = ModuleWxService.class, method = "getUsageCount"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String yesterdayUsageCount;


    public OverviewInfo(int aid, long timestamp) {
        this.totalActive = StringUtil.toLinkByComma(aid, Constant.DIGIT_ZERO, Constant.DIGIT_ZERO, timestamp);
        this.totalUser = StringUtil.toLinkByComma(Constant.DIGIT_ZERO, timestamp);
        this.yesterdayUsageCount = getYesterdayUsageKey(aid, timestamp);
    }

    /**
     * 拼接昨天使用数的查询条件
     * @return  代理商ID,医院ID,科室ID,开始时间戳，结束时间戳
     */
    private String getYesterdayUsageKey(int aid, long timestamp) {
        long start = timestamp - Constant.TIMESTAMP_DAYS_1;
        return  StringUtil.toLinkByComma(aid, Constant.DIGIT_ZERO, Constant.DIGIT_ZERO, start, timestamp);
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
