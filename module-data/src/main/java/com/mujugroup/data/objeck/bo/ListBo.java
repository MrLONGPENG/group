package com.mujugroup.data.objeck.bo;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.util.DateUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.data.service.feign.ModuleCoreService;
import com.mujugroup.data.service.feign.ModuleWxService;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ListBo implements Serializable {

    private int id;

    private String name;

    @MergeField(feign = ModuleCoreService.class, method = "getTotalActiveCount"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String totalActive;

    @MergeField(feign = ModuleWxService.class, method = "getUsageCount"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String UsageCount;

    // 今日使用率
    private String UsageRate;

    public ListBo(int type, int id, String name) {
        this.id = id;
        this.name = name;
        long timeStamp = new Date().getTime() / 1000L;
        if (type == 0) {
            if (id == -1) this.name = "其他科室";
            this.totalActive = StringUtil.toLinkByAnd(Constant.DIGIT_ZERO, id, Constant.DIGIT_ZERO, timeStamp);
            this.UsageCount = StringUtil.toLinkByAnd(Constant.DIGIT_ZERO, id, Constant.DIGIT_ZERO, DateUtil.dateToString(new Date(), DateUtil.TYPE_DATE_08));
        } else {
            this.totalActive = StringUtil.toLinkByAnd(Constant.DIGIT_ZERO, Constant.DIGIT_ZERO, id, timeStamp);
            this.UsageCount = StringUtil.toLinkByAnd(Constant.DIGIT_ZERO, Constant.DIGIT_ZERO, id, DateUtil.dateToString(new Date(), DateUtil.TYPE_DATE_08));
        }
    }
}
