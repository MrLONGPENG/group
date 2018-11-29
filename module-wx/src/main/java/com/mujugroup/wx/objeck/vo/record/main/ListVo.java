package com.mujugroup.wx.objeck.vo.record.main;

import lombok.Data;

import java.io.Serializable;

@Data
public class ListVo implements Serializable {
    //唯一业务id
    private Long did;
    //代理商ID
    private Integer aid;
    //医院ID
    private Integer hid;
    //科室ID
    private Integer oid;

    //微信对外唯一ID
    private String openId;

    //内部订单号
    private String tradeNo;

    //微信订单号
    private String transactionId;
    //商品ID
    private Integer gid;
    //价格
    private Integer price;
    // 类型：1:押金 2:套餐 3:午休 4:被子
    private Integer type;
}
