package com.lveqia.cloud.common.objeck.to;

import lombok.Data;

@Data
public class PayInfoTo {
    /*** 支付类型-全部 */
    public static final Integer TYPE_ALL  = 0;
    /*** 支付类型-晚休-晚上套餐 */
    public static final Integer TYPE_NIGHT  = 1;
    /*** 支付类型-午休-中午套餐 */
    public static final Integer TYPE_MIDDAY = 2;
    //唯一业务ID
    private Long did;
    //套餐类型
    private Integer orderType;
    //支付时间
    private long payTime;
    //结束时间
    private long endTime;
    //商品名称
    private String name;
    //价格
    private String price;
    //套餐天数
    private Integer days;
    //商品ID
    private Integer gid;
}
