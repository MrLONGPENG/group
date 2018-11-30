package com.mujugroup.wx.service.impl;

import com.mujugroup.wx.mapper.WxRefundRecordMapper;
import com.mujugroup.wx.model.WxRefundRecord;
import com.mujugroup.wx.service.WxRefundRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author leolaurel
 */
@Service("wxRefundRecordService")
public class WxRefundRecordServiceImpl implements WxRefundRecordService {


    private final WxRefundRecordMapper wxRefundRecordMapper;

    @Autowired
    public WxRefundRecordServiceImpl(WxRefundRecordMapper wxRefundRecordMapper) {
        this.wxRefundRecordMapper = wxRefundRecordMapper;
    }


    @Override
    public boolean insert(WxRefundRecord wxRefundRecord) {
        return wxRefundRecordMapper.insert(wxRefundRecord);
    }
}
