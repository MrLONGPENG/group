package com.mujugroup.wx.service.impl;

import com.mujugroup.wx.mapper.WxRecordAssistMapper;
import com.mujugroup.wx.model.WxRecordAssist;
import com.mujugroup.wx.service.WxRecordAssistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author leolaurel
 */
@Service("wxRecordAssistService")
public class WxRecordAssistServiceImpl implements WxRecordAssistService {


    private final WxRecordAssistMapper wxRecordAssistMapper;

    @Autowired
    public WxRecordAssistServiceImpl(WxRecordAssistMapper wxRecordAssistMapper) {
        this.wxRecordAssistMapper = wxRecordAssistMapper;
    }

    @Override
    public boolean insert(WxRecordAssist wxRecordAssist) {
        return wxRecordAssistMapper.insert(wxRecordAssist);
    }

    @Override
    public boolean update(WxRecordAssist wxRecordAssist) {
        return wxRecordAssistMapper.update(wxRecordAssist);
    }


}
