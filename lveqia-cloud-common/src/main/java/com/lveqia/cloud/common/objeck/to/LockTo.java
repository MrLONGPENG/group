package com.lveqia.cloud.common.objeck.to;

import lombok.Data;

import java.util.Date;

/**
 * Service Transfer Object
 * 业务信息传输对象
 */
@Data
public class LockTo {
    //业务ID
    private Long did;
    //锁ID
    private Long bid;
    //信号指标
    private Integer csq;
    //锁状态 1:关 2:开 (助力车 3:中间态 ) 4代表开锁机械故障、5代表关锁机械故障，6代表锁端本地时间不在限制时间范围内
    private Integer lockStatus;
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
    //最后上报时间
    private Date lastRefresh;

}
