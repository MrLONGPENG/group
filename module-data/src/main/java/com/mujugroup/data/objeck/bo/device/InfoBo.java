package com.mujugroup.data.objeck.bo.device;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.config.Constant;
import com.mujugroup.data.service.feign.ModuleWxService;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InfoBo implements Serializable {
    private String did;
    private String bid;
    private String bed;
    private String hospital;
    private String department;

    private Integer lockStatus;  //锁状态
    private Integer electric;    //充电状态
    private Integer battery;    //剩余电量百分比
    private Date lastRefresh;//最后上报时间

    @MergeField(feign = ModuleWxService.class, method = "getOrderEndTimeByDid"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String endTime;  //订单结束时间

}
