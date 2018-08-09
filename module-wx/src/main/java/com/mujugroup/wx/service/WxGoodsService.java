package com.mujugroup.wx.service;

import com.mujugroup.wx.exception.ParamException;
import com.mujugroup.wx.model.WxGoods;

import java.util.List;

public interface WxGoodsService {

    WxGoods findById(Integer id);

    WxGoods getDefaultGoods(int type);

    List<WxGoods> findListExcludeType(int type, String aid, String hid, String oid);

    List<WxGoods> findList(int type, String aid, String hid, String oid);

    List<WxGoods> findList(int type, int aid, int hid, int oid);

    List<WxGoods> queryList(int key, int kid, int type);

    List<WxGoods> findListByXid(int[] ints, int type);

    boolean update(int type, int key, int kid, int gid, String name, int price, int days, int state, String explain)
            throws ParamException;

    boolean delete(int type, int key, int kid, int gid) throws ParamException;
}
