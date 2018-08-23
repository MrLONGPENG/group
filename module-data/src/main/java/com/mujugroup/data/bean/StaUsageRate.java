package com.mujugroup.data.bean;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.StringUtil;
import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.data.service.feign.ModuleWxService;
import java.io.Serializable;

/**
 * 统计使用情况
 */
public class StaUsageRate implements Serializable {

    private String refDate;

    @MergeField(defaultValue = Constant.DIGIT_ZERO, feign = ModuleWxService.class, method = "getUsageRate"
            , isValueNeedMerge = true)
    private String usageRate;


    public StaUsageRate(String refDate, int aid, int hid, int oid) {
        this.refDate = refDate;
        this.usageRate =  StringUtil.toLinkByComma(aid, hid, oid, refDate);
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
