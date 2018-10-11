package com.mujugroup.wx.service;

import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.wx.model.WxGoods;
import com.mujugroup.wx.objeck.vo.GoodsVo;

import java.util.List;

public interface WxGoodsService {

    WxGoods findById(Integer id);

    WxGoods getDefaultGoods(int type);

    List<WxGoods> findListExcludeType(int type, String aid, String hid, String oid);

    List<WxGoods> findList(int type, String aid, String hid, String oid);

    List<WxGoods> findList(int type, int aid, int hid, int oid);

    List<WxGoods> queryList(int key, int kid, int type);

    List<WxGoods> findListByXid(int[] ints, int type);

    List<GoodsVo> findRelationListByKid(int kid, int key);

    boolean update(int type, int key, int kid, int gid, String name, int price, int days, int state, String explain)
            throws ParamException;

    boolean delete(int type, int key, int kid, int gid) throws ParamException, BaseException;

    //商品添加
    boolean add(int type, String name, int price, int days, int state, String explain)
            throws ParamException;

    //商品修改
    boolean modify(int type, int key, int kid, int gid, String name, int price, int days, int state, String explain)
            throws ParamException;
}
