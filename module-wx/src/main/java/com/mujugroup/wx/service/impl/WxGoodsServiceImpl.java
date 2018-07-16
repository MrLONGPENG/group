package com.mujugroup.wx.service.impl;

import com.mujugroup.wx.mapper.WxGoodsMapper;
import com.mujugroup.wx.model.WxGoods;
import com.mujugroup.wx.model.WxRelation;
import com.mujugroup.wx.service.WxGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("wxGoodsService")
public class WxGoodsServiceImpl implements WxGoodsService {

    private final Logger logger = LoggerFactory.getLogger(WxGoodsServiceImpl.class);
    private final WxGoodsMapper wxGoodsMapper;

    @Autowired
    public WxGoodsServiceImpl(WxGoodsMapper wxGoodsMapper) {
        this.wxGoodsMapper = wxGoodsMapper;
    }

    @Override
    public WxGoods findById(Integer id) {
        return wxGoodsMapper.findById(id);
    }

    @Override
    public List<WxGoods> findListByHospital(String hid) {
        if(hid == null) return getDefaultWxGoods();
        List<WxGoods> list = wxGoodsMapper.findListByRelation(WxRelation.KEY_HOSPITAL, Integer.valueOf(hid));
        if (list == null || list.size() ==0) {
            list = getDefaultWxGoods();
        }
        return list;
    }

    /**
     * 获取默认数据
     */
    private List<WxGoods> getDefaultWxGoods() {
        logger.info("未找到医院指定支付价格，采用默认支付价格");
        return  wxGoodsMapper.findListByRelation(WxRelation.KEY_DEFAULT, WxRelation.KID_DEFAULT);
    }
}
