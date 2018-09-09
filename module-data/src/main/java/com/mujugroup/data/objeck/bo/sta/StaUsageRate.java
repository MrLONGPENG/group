package com.mujugroup.data.objeck.bo.sta;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.StringUtil;
import com.lveqia.cloud.common.util.Constant;
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


    public StaUsageRate(String refDate, int aid, int hid, int oid) {
        this.refDate = refDate;
        this.usageRate =  StringUtil.toLinkByComma(aid, hid, oid, refDate);
    }

    public StaUsageRate(String refDate, String ids) {
        this.refDate = refDate;
        this.usageRate =  StringUtil.toLinkByComma(0, ids, 0, refDate);
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
