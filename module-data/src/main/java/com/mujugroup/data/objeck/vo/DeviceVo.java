package com.mujugroup.data.objeck.vo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DeviceVo implements Serializable {

    private String did;
    private String bid;
    private String aid;
    private String hid;
    private String oid;
    private String bed;
    private String hospital;
    private String department;
    private String address;
    private Integer run;       //是否商用
    private Integer bell;     //是否响铃


    private Integer csq;        //信号指标
    private String lockStatus;  //锁状态
    private String electric;    //充电状态
    private Integer battery;    //剩余电量百分比
    private Integer temp;       //温度
    private Integer charge;     //充电电压
    private Integer voltage;    //电池电压
    private String lastRefresh;       //最后上报时间
    private Integer hVersion;       //硬件版本
    private Integer fVersion;       //固件版本
    private List<String> dictName;    //故障名称

    //订单信息

    private String orderType;   //套餐类型
    private String payTime;  //开始时间
    private String endTime;  //结束时间
    private String name;     //商品名称
    private String price;    //价格
    private Integer gid;     //商品ID

    private Integer days;   //套餐天数
}
