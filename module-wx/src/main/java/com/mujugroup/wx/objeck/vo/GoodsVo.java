package com.mujugroup.wx.objeck.vo;

import com.mujugroup.wx.model.WxGoods;

import java.io.Serializable;
import java.util.List;

public class GoodsVo implements Serializable {
    //午休类型 0：默认 1：自定义
    private int noon_type;
    //套餐类型 0：默认 1：自定义
    private int combo_type;
    //存放午休商品
    private GoodsItemVo goods;
    //存放套餐商品
    private List<GoodsItemVo> list;
    // 外键类型
    private int key;
    // 外键ID
    private int kid;
    //商品所属机构名称
    private String name;
    //科室商品集合
    private List<GoodsVo> children;

    public GoodsVo(int kid, int key) {
        this.kid = kid;
        this.key = key;
    }

    public int getNoon_type() {
        return noon_type;
    }

    public void setNoon_type(int noon_type) {
        this.noon_type = noon_type;
    }

    public int getCombo_type() {
        return combo_type;
    }

    public void setCombo_type(int combo_type) {
        this.combo_type = combo_type;
    }

    public GoodsItemVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsItemVo goods) {
        this.goods = goods;
    }

    public List<GoodsItemVo> getList() {
        return list;
    }

    public void setList(List<GoodsItemVo> list) {
        this.list = list;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GoodsVo> getChildren() {
        return children;
    }

    public void setChildren(List<GoodsVo> children) {
        this.children = children;
    }
}
