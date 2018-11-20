package com.mujugroup.lock.objeck.vo.info;


import com.lveqia.cloud.common.objeck.vo.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ListVo", description = "展示设备info的Vo")
public class ListVo extends PageVo implements Serializable {

    private Integer id;
    private String did;
    @ApiModelProperty(value = "lockId", notes = "设备编号")
    private String lockId;
    //品牌
    private Integer brand;
    //固件版本
    private Integer fVersion;

    //硬件版本
    private Integer hVersion;
    // 信号指标
    private Integer csq;

    //温度
    private Integer temp;

    //充电电压(原值:vbus)
    private Integer charge;

    //电池电压(原值:vbattery)
    private Integer voltage;

    //充电电流(原值:iCharge)
    private Integer electric;
    //剩余电量
    private Integer batteryStat;

    //锁状态 1:关 2:开 (助力车 3:中间态 ) 4代表开锁机械故障、5代表关锁机械故障，6代表锁端本地时间不在限制时间范围内
    private Integer lockStatus;
    //最后上报时间
    private Date lastRefresh;
    @ApiModelProperty(value = "elecStatus", notes = "充电状态【0：未充电 1：充电中】,默认查全部，如果选择所有，传入-1)")
    private Integer elecStatus;
    @ApiModelProperty(value = "lineSatatus", notes = "充电状态【0：离线 1：在线】)")
    private Integer lineSatatus;
    private String csqStart;
    private String csqEnd;
    private String batteryStatStart;
    private String batteryStatEnd;
}
