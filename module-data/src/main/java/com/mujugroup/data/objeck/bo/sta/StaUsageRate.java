package com.mujugroup.data.objeck.bo.sta;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.config.Constant;
import com.mujugroup.data.service.feign.ModuleWxService;

import java.io.Serializable;

/**
 * 图表-统计使用率情况
 */
public class StaUsageRate implements Serializable {

    private String refDate;

    @MergeField(feign = ModuleWxService.class, method = "getUsageRate"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String usageRate;


    public StaUsageRate(String refDate, String[] ids) {
        this.refDate = refDate;
        this.usageRate =  StringUtil.toLinkByAnd(ids[0], ids[1], ids[2], refDate);
    }

    public String getRefDate() {
        return refDate;
    }

    public void setRefDate(String refDate) {
        this.refDate = refDate;
    }

    public String getUsageRate() {
        return usageRate;
    }

    public void setUsageRate(String usageRate) {
        this.usageRate = usageRate;
    }
}
