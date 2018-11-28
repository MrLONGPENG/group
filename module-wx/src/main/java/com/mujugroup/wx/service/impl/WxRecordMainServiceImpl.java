package com.mujugroup.wx.service.impl;

import com.mujugroup.wx.mapper.WxRecordMainMapper;
import com.mujugroup.wx.model.WxRecordMain;
import com.mujugroup.wx.service.WxRecordMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author leolaurel
 */
@Service("wxRecordMainService")
public class WxRecordMainServiceImpl implements WxRecordMainService {


    private final WxRecordMainMapper wxRecordMainMapper;

    @Autowired
    public WxRecordMainServiceImpl(WxRecordMainMapper wxRecordMainMapper) {
        this.wxRecordMainMapper = wxRecordMainMapper;
    }

    @Override
    public WxRecordMain findMainRecordByNo(String orderNo) {
        return wxRecordMainMapper.findMainRecordByNo(orderNo);
    }

    @Override
    public boolean insert(WxRecordMain wxRecordMain) {
        return wxRecordMainMapper.insert(wxRecordMain);
    }

    @Override
    public boolean update(WxRecordMain wxRecordMain) {
        return wxRecordMainMapper.update(wxRecordMain);
    }
}
