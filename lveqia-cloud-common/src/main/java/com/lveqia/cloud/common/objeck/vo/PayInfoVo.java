package com.lveqia.cloud.common.objeck.vo;

import lombok.Data;

@Data
public class PayInfoVo {
    //唯一业务ID
    private Long did;
    //套餐类型
    private String orderType;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    //商品名称
    private String name;
    //价格
    private String price;
    //商品ID
    private Integer gid;
}
