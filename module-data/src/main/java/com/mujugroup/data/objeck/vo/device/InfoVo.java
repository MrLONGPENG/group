package com.mujugroup.data.objeck.vo.device;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.config.Constant;
import com.mujugroup.data.service.feign.ModuleWxService;
import lombok.Data;

import java.io.Serializable;

@Data
public class InfoVo implements Serializable {

    private String did;
    private String bid;
    private String bed;
    private String hospital;
    private String department;

    private String lockStatus;  //锁状态
    private String electric;    //充电状态
    private Integer battery;    //剩余电量百分比
    private String lastRefresh;//最后上报时间

    @MergeField(feign = ModuleWxService.class, method = "getOrderEndTimeByDid"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String endTime;  //订单结束时间

}
