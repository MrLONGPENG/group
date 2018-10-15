package com.mujugroup.data.objeck.bo;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.config.Constant;
import com.mujugroup.data.service.feign.ModuleCoreService;
import com.mujugroup.data.service.feign.ModuleWxService;

import java.io.Serializable;

/**
 * Business Object
 * 概览-使用情况数据，不含今日
 * 总激活数、总用户数、昨日使用数以及使用率
 */
public class UsageBO implements Serializable {

    @MergeField(feign = ModuleCoreService.class, method = "getTotalActiveCount"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String totalActive;


    @MergeField(feign = ModuleWxService.class, method = "getUserCount"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String totalUser;


    @MergeField(feign = ModuleWxService.class, method = "getUsageCount"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String yesterdayUsageCount;

    // 昨日使用率
    private String yesterdayUsageRate;


    public UsageBO(String aid, String date, long timestamp) {
        // 拼接总用户数 ｛开始时间戳,结束时间戳｝
        this.totalUser = StringUtil.toLinkByAnd(Constant.DIGIT_ZERO, timestamp);
        // 拼接总激活数 ｛AID,HID,OID,,结束时间戳｝
        this.totalActive = StringUtil.toLinkByAnd(aid, Constant.DIGIT_ZERO, Constant.DIGIT_ZERO, timestamp);
        // 拼接昨天使用数的查询条件 ｛AID,HID,OID,开始时间戳,结束时间戳｝
        this.yesterdayUsageCount = StringUtil.toLinkByAnd(aid, Constant.DIGIT_ZERO, Constant.DIGIT_ZERO, date);
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

    public String getYesterdayUsageRate() {
        return yesterdayUsageRate;
    }

    public void setYesterdayUsageRate(String yesterdayUsageRate) {
        this.yesterdayUsageRate = yesterdayUsageRate;
    }
}
