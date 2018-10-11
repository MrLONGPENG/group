package com.mujugroup.wx.objeck.vo;

import java.io.Serializable;

public class GoodsVo implements Serializable {

    /*** 排除晚上类型商品 */
    public static final int EXCLUDE_NIGHT = 2;
    /*** 排除午休类型商品 */
    public static final int EXCLUDE_MIDDAY = 3;

    /*** 类型-晚上套餐 */
    public static final int TYPE_NIGHT = 2;

    /*** 类型-午休套餐 */
    public static final int TYPE_MIDDAY = 3;

    /**
     * 主键
     * <p>
     * 表字段 : t_wx_goods.id
     */

    private Integer id;

    /**
     * 商品套餐名字
     * 表字段 : t_wx_goods.name
     */
    private String name;

    /**
     * 商品套餐价格
     * 表字段 : t_wx_goods.price
     */
    private Integer price;

    /**
     * 标价币种
     * 表字段 : t_wx_goods.fee_type
     */

    private String feeType;

    /**
     * 商品套餐的使用天数
     * 表字段 : t_wx_goods.days
     */

    private Integer days;

    /**
     * 商品类型(1:押金；2:套餐；3:午休 4:被子)
     * 其他说明：2和3不同时存在，套餐包含第二天午休时间, 单独午休不包含晚上的套餐
     * 表字段 : t_wx_goods.type
     */

    private Integer type;

    /**
     * 套餐状态(1:可用；2:禁用；-1:删除)
     * 表字段 : t_wx_goods.state
     */

    private Integer state;

    /**
     * 此属性使用说明
     * 表字段 : t_wx_goods.explain
     */

    private String explain;

    private Integer rid;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getKid() {
        return kid;
    }

    public void setKid(Integer kid) {
        this.kid = kid;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getRelationType() {
        return relationType;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }

    private Integer kid;
    private  Integer key;
    private Integer relationType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
