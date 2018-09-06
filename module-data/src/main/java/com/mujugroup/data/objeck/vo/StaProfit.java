package com.mujugroup.data.objeck.vo;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.StringUtil;
import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.data.service.feign.ModuleWxService;

import java.io.Serializable;

/**
 * 图表-收益统计接口
 */
public class StaProfit implements Serializable {

    private String refDate;

    @MergeField(feign = ModuleWxService.class, method = "getTotalProfit"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String profit;


    public StaProfit(String refDate, int aid, int hid, int oid) {
        this.refDate = refDate;
        this.profit = StringUtil.toLinkByComma(aid, hid, oid, 0, 0, refDate);
    }

    public StaProfit(String refDate, String ids) {
        this.refDate = refDate;
        this.profit = StringUtil.toLinkByComma(0, ids, 0, 0, 0, refDate);
    }


    public String getRefDate() {
        return refDate;
    }

    public void setRefDate(String refDate) {
        this.refDate = refDate;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }
}
