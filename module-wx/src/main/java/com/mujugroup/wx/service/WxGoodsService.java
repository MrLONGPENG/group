package com.mujugroup.wx.service;

import com.mujugroup.wx.model.WxGoods;

import java.util.List;

public interface WxGoodsService {

    WxGoods findById(Integer id);

    List<WxGoods> findListByHospital(String hid);
}
