package com.mujugroup.data.objeck.bo;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.StringUtil;
import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.data.service.feign.ModuleWxService;

import java.io.Serializable;

/**
 * Business Object
 * 概览-收益数据，不含今日
 * 累计收益、以及昨日收益
 */
public class ProfitBO implements Serializable {

    @MergeField(feign = ModuleWxService.class, method = "getTotalProfit"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String totalProfit;

    @MergeField(feign = ModuleWxService.class, method = "getTotalProfit"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String yesterdayProfit;


    public ProfitBO(int aid, long timestamp) {
        // 总收益与昨日收益，差异在于开始时间（即总收益无开始时间，默认O）
        this.totalProfit = StringUtil.toLinkByComma(aid, Constant.DIGIT_ZERO
                , Constant.DIGIT_ZERO, Constant.DIGIT_ZERO, timestamp);
        this.yesterdayProfit = StringUtil.toLinkByComma(aid, Constant.DIGIT_ZERO
                , Constant.DIGIT_ZERO, timestamp - Constant.TIMESTAMP_DAYS_1, timestamp);
    }

    public String getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(String totalProfit) {
        this.totalProfit = totalProfit;
    }

    public String getYesterdayProfit() {
        return yesterdayProfit;
    }

    public void setYesterdayProfit(String yesterdayProfit) {
        this.yesterdayProfit = yesterdayProfit;
    }
}
