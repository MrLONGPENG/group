package com.lveqia.cloud.common.objeck.to;

import lombok.Data;

@Data
public class PayInfoTo {
    //唯一业务ID
    private Long did;
    //套餐类型
    private Integer orderType;
    //开始时间
    private Long startTime;
    //结束时间
    private Long endTime;


}
