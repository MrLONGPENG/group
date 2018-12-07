package com.mujugroup.wx.service.impl;

import com.mujugroup.wx.mapper.WxDeductionRecordMapper;
import com.mujugroup.wx.model.WxDeductionRecord;
import com.mujugroup.wx.service.SessionService;
import com.mujugroup.wx.service.WxDeductionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author leolaurel
 */
@Service("wxDeductionRecordService")
public class WxDeductionRecordServiceImpl implements WxDeductionRecordService {


    private final WxDeductionRecordMapper wxDeductionRecordMapper;
    private final SessionService sessionService;

    @Autowired
    public WxDeductionRecordServiceImpl(WxDeductionRecordMapper wxDeductionRecordMapper, SessionService sessionService) {
        this.wxDeductionRecordMapper = wxDeductionRecordMapper;
        this.sessionService = sessionService;
    }

    @Override
    public WxDeductionRecord isExist(String date, String openId, long did) {
        return wxDeductionRecordMapper.isExist(date, openId, did);
    }

    @Override
    public List<WxDeductionRecord> getDeductionRecordList(String sessionThirdKey) {
        String openId = sessionService.getOpenId(sessionThirdKey);
        return wxDeductionRecordMapper.getDeductionRecordList(openId);
    }

    @Override
    public boolean update(WxDeductionRecord wxDeductionRecord) {
        return wxDeductionRecordMapper.update(wxDeductionRecord);
    }

    @Override
    public boolean insert(WxDeductionRecord wxDeductionRecord) {
        return wxDeductionRecordMapper.insert(wxDeductionRecord);
    }
}
