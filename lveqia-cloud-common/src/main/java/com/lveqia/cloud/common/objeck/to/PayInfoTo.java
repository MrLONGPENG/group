package com.lveqia.cloud.common.objeck.to;

import lombok.Data;

import java.util.Date;

@Data
public class PayInfoTo {
    //唯一业务ID
    private Long did;
    //套餐类型
    private Integer orderType;
    //开始时间
    private Date startTime;
    //结束时间
    private long endTime;
    //商品名称
    private String name;
    //价格
    private String price;
    //商品ID
    private Integer gid;
}