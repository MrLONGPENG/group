package com.mujugroup.wx.model;


import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 商品套餐详情表
 * 类名:WxGoods
 * 创建人:leolaurel
 * 创建时间:20180712
 */
@SuppressWarnings("serial")
@Table(name = "t_wx_goods")
public class WxGoods implements Serializable {

    /**
     * 主键
     * 
     * 表字段 : t_wx_goods.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 商品套餐名字
     * 表字段 : t_wx_goods.name
     */
    @Column(name = "name")
    private String name;

    /**
     * 商品套餐价格
     * 表字段 : t_wx_goods.price
     */
    @Column(name = "price")
    private Integer price;

    /**
     * 标价币种
     * 表字段 : t_wx_goods.fee_type
     */
    @Column(name = "fee_type")
    private String feeType;

    /**
     * 商品套餐的使用天数
     * 表字段 : t_wx_goods.days
     */
    @Column(name = "days")
    private Integer days;

    /**
     * 商品类型(1:押金；2:套餐；)
     * 表字段 : t_wx_goods.type
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 套餐状态(1:可用；2:禁用；-1:删除)
     * 表字段 : t_wx_goods.state
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 此属性使用说明
     * 表字段 : t_wx_goods.explain
     */
    @Column(name = "explain")
    private String explain;



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