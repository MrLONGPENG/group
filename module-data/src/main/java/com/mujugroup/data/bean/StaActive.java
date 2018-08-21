package com.mujugroup.data.bean;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.DateUtil;
import com.lveqia.cloud.common.StringUtil;
import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.data.service.feign.ModuleCoreService;

import java.io.Serializable;

/**
 * 统计激活情况
 */
public class StaActive implements Serializable {

    private String refDate;

    @MergeField(defaultValue = Constant.DIGIT_ZERO, feign = ModuleCoreService.class, method = "getActiveCount"
            , isQueryByParam = true)
    private String active;

    @MergeField(defaultValue = Constant.DIGIT_ZERO, feign = ModuleCoreService.class, method = "getTotalActiveCount"
            , isValueNeedMerge = true)
    private String totalActive;

    public StaActive(int aid, int grain, String refDate) {
        this.refDate = refDate;
        this.active = refDate;
        this.totalActive = getKey(aid, grain, refDate);
    }

    private String getKey(int aid, int grain, String refDate) {
        return StringUtil.toParams(aid, getTimestamp(grain, refDate));
    }

    private long getTimestamp(int grain, String refDate) {
        switch (grain){
            case 1 : return DateUtil.toTimestamp(refDate, DateUtil.TYPE_DATE_08) + Constant.TIMESTAMP_DAYS_1;
            case 2 : return DateUtil.toTimestamp(refDate.substring(9), DateUtil.TYPE_DATE_08);
            case 3 : return DateUtil.getTimesEndMonth(refDate);
        }
        return 0;
    }

    public String getRefDate() {
        return refDate;
    }

    public void setRefDate(String refDate) {
        this.refDate = refDate;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getTotalActive() {
        return totalActive;
    }

    public void setTotalActive(String totalActive) {
        this.totalActive = totalActive;
    }
}
