package com.mujugroup.wx.service.impl;

import com.mujugroup.wx.mapper.WxRecordMainMapper;
import com.mujugroup.wx.model.WxRecordAssist;
import com.mujugroup.wx.model.WxRecordMain;
import com.mujugroup.wx.service.WxRecordAssistService;
import com.mujugroup.wx.service.WxRecordMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author leolaurel
 */
@Service("wxRecordMainService")
public class WxRecordMainServiceImpl implements WxRecordMainService {


    private final WxRecordMainMapper wxRecordMainMapper;
    private final WxRecordAssistService wxRecordAssistService;
    @Autowired
    public WxRecordMainServiceImpl(WxRecordMainMapper wxRecordMainMapper, WxRecordAssistService wxRecordAssistService) {
        this.wxRecordMainMapper = wxRecordMainMapper;
        this.wxRecordAssistService = wxRecordAssistService;
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

    @Override
    @Transactional
    public void insertRecord(WxRecordMain wxRecordMain, List<WxRecordAssist> wxRecordAssists) {
        //将数据记录到支付主表
        boolean isInsert = wxRecordMainMapper.insert(wxRecordMain);
        if (isInsert) {
            for (WxRecordAssist assist : wxRecordAssists) {
                assist.setMid(wxRecordMain.getId());
                assist.setCrtTime(wxRecordMain.getCrtTime());
                wxRecordAssistService.insert(assist);
            }
        }
    }
}
