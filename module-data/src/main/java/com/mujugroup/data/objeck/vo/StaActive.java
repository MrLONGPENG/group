package com.mujugroup.data.objeck.vo;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.StringUtil;
import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.data.service.feign.ModuleCoreService;

import java.io.Serializable;

/**
 * 统计激活情况
 */
public class StaActive implements Serializable {

    private String refDate;

    @MergeField(defaultValue = Constant.DIGIT_ZERO, feign = ModuleCoreService.class, method = "getNewlyActiveCount"
            , isQueryByParam = true)
    private String newlyActive;

    @MergeField(defaultValue = Constant.DIGIT_ZERO, feign = ModuleCoreService.class, method = "getTotalActiveCount"
            , isValueNeedMerge = true)
    private String totalActive;

    public StaActive(String refDate, int aid, int hid, int oid, long end) {
        this.refDate = refDate;
        this.newlyActive = refDate;
        this.totalActive = StringUtil.toLinkByComma(aid, hid, oid, end);
    }

    public String getRefDate() {
        return refDate;
    }

    public void setRefDate(String refDate) {
        this.refDate = refDate;
    }

    public String getNewlyActive() {
        return newlyActive;
    }

    public void setNewlyActive(String newlyActive) {
        this.newlyActive = newlyActive;
    }

    public String getTotalActive() {
        return totalActive;
    }

    public void setTotalActive(String totalActive) {
        this.totalActive = totalActive;
    }
}
